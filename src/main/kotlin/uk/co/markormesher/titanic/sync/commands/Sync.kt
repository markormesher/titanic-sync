package uk.co.markormesher.titanic.sync.commands

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import uk.co.markormesher.titanic.sync.Config
import uk.co.markormesher.titanic.sync.SyncArgs
import uk.co.markormesher.titanic.sync.helpers.*
import java.io.IOException

private val httpClient by lazy {
	OkHttpClient()
}

fun runSync(config: Config, syncArgs: SyncArgs) {
	if (!config.requireIsInitialised()) return
	if (!config.requireKeyIsSet()) return
	if (!config.requireIdentityIsSet()) return

	waterfall(
			{ cb -> syncConnections(config, syncArgs, cb) },
			{ cb -> syncAliases(config, syncArgs, cb) },

			{ cb ->
				printDone("Sync complete")
				cb(true)
			},

			always = { httpClient.dispatcher().executorService().shutdown() }
	)
}

private fun syncConnections(config: Config, syncArgs: SyncArgs, callback: (success: Boolean) -> Unit) {
	if (syncArgs.excludeConnections) {
		printInfo("Skipping connections")
		callback(true)
	} else {
		printTask("Syncing connections...")
		val url = buildBaseUrl(config).addPathSegment("connected-devices").addQueryParameter("identity", config.identity).build()
		val request = buildBaseRequest(config, url).get().build()
		httpClient.newCall(request).enqueue(object: Callback {
			override fun onResponse(call: Call?, response: Response?) {
				val responseBody = getResponseBodyOrFail(response)
				if (responseBody == null) {
					callback(false)
				} else {
					val parsedBody = JsonParser().parse(responseBody) as JsonArray
					for (device in parsedBody) {
						if (device is JsonObject) {
							val name = device["name"].asString
							val ipAddress = device["ipAddress"].asString
							printInfo("$name = $ipAddress")
						}
					}
					callback(true)
				}
			}

			override fun onFailure(call: Call?, e: IOException?) {
				printError(e?.message ?: "Unknown API error")
				callback(false)
			}
		})
	}
}

private fun syncAliases(config: Config, syncArgs: SyncArgs, callback: (success: Boolean) -> Unit) {
	if (syncArgs.excludeAliases) {
		printInfo("Skipping aliases")
		callback(true)
	} else {
		printTask("Syncing aliases...")
		printInfo("Not implemented yet")
		printInfo("Aliases synced to <<<somewhere>>>")
		callback(true)
	}
}
