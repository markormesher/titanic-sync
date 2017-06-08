package uk.co.markormesher.titanic.sync.commands

import uk.co.markormesher.titanic.sync.Config
import uk.co.markormesher.titanic.sync.InitArgs
import uk.co.markormesher.titanic.sync.helpers.printDone
import uk.co.markormesher.titanic.sync.helpers.printInfo
import uk.co.markormesher.titanic.sync.helpers.waterfall

fun runInit(config: Config, initArgs: InitArgs) {
	if (!config.requireIsNotInitialised()) return

	waterfall(
			{ cb -> createFolder(config, cb) },
			{ cb -> setApiKeyIfGiven(config, initArgs, cb) },
			{ cb -> setApiUrl(config, initArgs, cb) },
			{ cb -> setIdentityIfGiven(config, initArgs, cb) },

			{ cb ->
				printDone("Initialisation complete")
				cb(true)
			}
	)

}

private fun createFolder(config: Config, callback: (success: Boolean) -> Unit) {
	callback(config.initalise())
}

private fun setApiKeyIfGiven(config: Config, initArgs: InitArgs, callback: (success: Boolean) -> Unit) {
	if (initArgs.apiKey.isEmpty()) {
		printInfo("Not setting API key - none provided")
		callback(true)
	} else {
		config.setApiKey(initArgs.apiKey.trim())
		callback(true)
	}
}

private fun setApiUrl(config: Config, initArgs: InitArgs, callback: (success: Boolean) -> Unit) {
	config.setApiUrl(initArgs.apiUrl.trim())
	callback(true)
}

private fun setIdentityIfGiven(config: Config, initArgs: InitArgs, callback: (success: Boolean) -> Unit) {
	if (initArgs.identity.isEmpty()) {
		printInfo("Not machine identity - none provided")
		callback(true)
	} else {
		config.setIdentity(initArgs.identity.trim())
		callback(true)
	}
}
