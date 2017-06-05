package uk.co.markormesher.titanic.sync

import com.beust.jcommander.JCommander
import com.beust.jcommander.MissingCommandException
import com.beust.jcommander.ParameterException

fun main(vararg argsInput: String) {
	val globalArgs = GlobalArgs()
	val initArgs = InitArgs()
	val setConfigArgs = SetConfigArgs()
	val syncArgs = SyncArgs()
	val argParser = JCommander.newBuilder()
			.addObject(globalArgs)
			.addCommand("init", initArgs)
			.addCommand("set", setConfigArgs)
			.addCommand("sync", syncArgs)
			.build()
	argParser.programName = "titanic"

	try {
		argParser.parse(*argsInput)
	} catch (e: MissingCommandException) {
		println("ERROR: Command not recognised: ${e.unknownCommand}")
		println()
		argParser.usage()
	} catch(e: ParameterException) {
		println("ERROR: ${e.message}")
		println()
		argParser.usage()
	}

	if (globalArgs.showHelp) {
		argParser.usage()
		return
	}

	val config: Config
	try {
		config = Config(globalArgs.titanicFolder)
	} catch (e: IllegalArgumentException) {
		println("ERROR: ${e.message}")
		return
	}

	val command = argParser.parsedCommand
	when (command) {
		"init" -> {

		}
		"set" -> {

		}
		"sync" -> {

		}
		else -> {
			argParser.usage()
		}
	}

}
