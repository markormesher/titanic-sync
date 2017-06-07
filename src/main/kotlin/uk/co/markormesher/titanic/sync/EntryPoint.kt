package uk.co.markormesher.titanic.sync

import com.beust.jcommander.JCommander
import com.beust.jcommander.MissingCommandException
import com.beust.jcommander.ParameterException
import uk.co.markormesher.titanic.sync.commands.initTitanic
import uk.co.markormesher.titanic.sync.commands.setConfig
import uk.co.markormesher.titanic.sync.helpers.printError
import uk.co.markormesher.titanic.sync.helpers.printInfo

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
		printError("ERROR: Command not recognised: ${e.unknownCommand}")
		println()
		argParser.usage()
		return
	} catch(e: ParameterException) {
		printError("ERROR: ${e.message}")
		println()
		argParser.usage()
		return
	}

	if (globalArgs.showHelp) {
		argParser.usage()
		return
	}

	val config = Config(globalArgs.titanicFolder)

	val command = argParser.parsedCommand
	when (command) {
		"init" -> initTitanic(config, initArgs)
		"set" -> {
			if (!config.requireIsInitialised()) return
			setConfig(config, setConfigArgs)
		}
		"sync" -> {
			if (!config.requireIsInitialised()) return
			if (!config.requireKeyIsSet()) return
			if (!config.requireIdentityIsSet()) return
			printInfo("Sync coming soon...")
		}
		else -> {
			argParser.usage()
		}
	}

}
