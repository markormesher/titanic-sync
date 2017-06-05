package uk.co.markormesher.titanic.sync

import com.beust.jcommander.Parameter
import com.beust.jcommander.Parameters
import java.io.File


open class GlobalArgs {

	@Parameter(names = arrayOf("-folder", "-f"), required = false, description = "Location of the folder containing the Titanic Sync config files")
	var titanicFolder = File(System.getProperty("user.home") + "/.titanic-sync")

	@Parameter(names = arrayOf("-help", "-?"), required = false, description = "Show this help")
	var showHelp = false

}

@Parameters(commandDescription = "Initialise this Titanic Sync installation")
open class InitArgs {

	@Parameter(names = arrayOf("-key"), required = false, description = "Your API key")
	var key = ""

	@Parameter(names = arrayOf("-identity"), required = false, description = "This machine's name (must match name entered on Titanic website)")
	var machineName = ""

}

@Parameters(commandDescription = "Set any configuration variable")
class SetConfigArgs: InitArgs()

@Parameters(commandDescription = "Sync Titanic content to this machine")
class SyncArgs {

	@Parameter(names = arrayOf("-no-connections"), required = false, description = "Exclude connections from the sync process")
	var excludeConnections = false

	@Parameter(names = arrayOf("-no-aliases"), required = false, description = "Exclude aliases from the sync process")
	var excludeAliases = false

}
