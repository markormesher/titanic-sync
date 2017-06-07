package uk.co.markormesher.titanic.sync.commands

import uk.co.markormesher.titanic.sync.Config
import uk.co.markormesher.titanic.sync.SetConfigArgs
import uk.co.markormesher.titanic.sync.helpers.printDone
import uk.co.markormesher.titanic.sync.helpers.printInfo

fun setConfig(config: Config, setConfigArgs: SetConfigArgs) {
	var workDone = false

	if (setConfigArgs.key.isNotBlank()) {
		config.setApiKey(setConfigArgs.key)
		workDone = true
	}

	if (setConfigArgs.identity.isNotBlank()) {
		config.setIdentity(setConfigArgs.identity)
		workDone = true
	}

	if (workDone) {
		printDone("Config arguments saved")
	} else {
		printInfo("No config arguments updated")
	}
}
