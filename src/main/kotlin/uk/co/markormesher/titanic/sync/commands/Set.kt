package uk.co.markormesher.titanic.sync.commands

import uk.co.markormesher.titanic.sync.Config
import uk.co.markormesher.titanic.sync.SetConfigArgs
import uk.co.markormesher.titanic.sync.helpers.printDone
import uk.co.markormesher.titanic.sync.helpers.printWarning

fun runSetConfig(config: Config, setConfigArgs: SetConfigArgs) {
	if (!config.requireIsInitialised()) return

	var workDone = false

	if (setConfigArgs.apiKey.isNotBlank()) {
		config.setApiKey(setConfigArgs.apiKey)
		workDone = true
	}

	if (setConfigArgs.apiUrl.isNotBlank()) {
		config.setApiUrl(setConfigArgs.apiUrl)
		workDone = true
	}

	if (setConfigArgs.identity.isNotBlank()) {
		config.setIdentity(setConfigArgs.identity)
		workDone = true
	}

	if (workDone) {
		printDone("Config arguments saved")
	} else {
		printWarning("No config arguments updated")
	}
}
