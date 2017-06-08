package uk.co.markormesher.titanic.sync

import com.beust.jcommander.JCommander
import com.beust.jcommander.MissingCommandException
import com.beust.jcommander.ParameterException
import uk.co.markormesher.titanic.sync.commands.runInit
import uk.co.markormesher.titanic.sync.commands.runSetConfig
import uk.co.markormesher.titanic.sync.commands.runSync
import uk.co.markormesher.titanic.sync.helpers.printError

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
		printError("Command not recognised: ${e.unknownCommand}")
		println()
		argParser.usage()
		return
	} catch(e: ParameterException) {
		printError(e.message ?: "Unknown parameter error")
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
		"init" -> runInit(config, initArgs)
		"set" -> runSetConfig(config, setConfigArgs)
		"sync" -> runSync(config, syncArgs)
		else -> argParser.usage()
	}
}
