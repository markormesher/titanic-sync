package uk.co.markormesher.titanic.sync.helpers

import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response
import uk.co.markormesher.titanic.sync.Config

fun buildBaseUrl(config: Config) = HttpUrl.parse(config.apiUrl)!!.newBuilder().addPathSegment("api")!!

fun buildBaseRequest(config: Config, url: HttpUrl) = Request.Builder().url(url).addHeader("Authorization", config.apiKey)!!

fun getResponseBodyOrFail(response: Response?, rejectNull: Boolean = true): String? {
	val responseBody = response?.body()?.string()
	if (response?.isSuccessful ?: false) {
		if (rejectNull && responseBody == null) {
			printError("Null response from API")
		}
		return responseBody
	} else {
		printError(response?.message() ?: "Unknown API error")
		if (responseBody != null) {
			printError(responseBody)
		}
		return null
	}
}
