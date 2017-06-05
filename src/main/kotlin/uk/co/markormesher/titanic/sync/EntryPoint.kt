package uk.co.markormesher.titanic.sync

import com.beust.jcommander.JCommander
import com.beust.jcommander.MissingCommandException
import com.beust.jcommander.ParameterException

fun main(vararg argsInput: String) {
	val globalArgs = GlobalArgs()
	val setApiKeyArgs = SetApiKeyArgs()
	val setIdentityArgs = SetIdentityArgs()
	val syncArgs = SyncArgs()
	val argParser = JCommander.newBuilder()
			.addObject(globalArgs)
			.addCommand("set-api-key", setApiKeyArgs)
			.addCommand("set-identity", setIdentityArgs)
			.addCommand("sync", syncArgs)
			.build()

	try {
		argParser.parse(*argsInput)
	} catch (e: MissingCommandException) {
		println("Command not recognised: ${e.unknownCommand}")
		println()
		argParser.usage()
	} catch(e: ParameterException) {
		println(e.message)
		println()
		argParser.usage()
	}

	if (globalArgs.showHelp) {
		argParser.usage()
		return
	}

	val command = argParser.parsedCommand
	when (command) {
		"set-api-key" -> {

		}
		"set-identity" -> {

		}
		"sync" -> {

		}
	}

}
