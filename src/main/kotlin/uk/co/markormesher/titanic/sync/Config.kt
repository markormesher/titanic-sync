package uk.co.markormesher.titanic.sync

import uk.co.markormesher.titanic.sync.helpers.printError
import uk.co.markormesher.titanic.sync.helpers.printInfo
import uk.co.markormesher.titanic.sync.helpers.printTask
import java.io.File

class Config(val titanicFolder: File) {

	private val commandPrefix = "titanic [ -d <config folder location> ]"

	private val apiKeyFile = File(titanicFolder, "api-key")
	private val identityFile = File(titanicFolder, "identity")

	/*
	Directory/file structure initialisation
	 */

	val isInitialised: Boolean
		get() = titanicFolder.exists() || titanicFolder.isDirectory

	fun requireIsInitialised(): Boolean {
		if (isInitialised) {
			return true
		} else {
			printError("Titanic has not been initialised in ${titanicFolder.absolutePath}")
			printInfo("To initialise, run:")
			printInfo("    $commandPrefix init [ --api-key <your API key> ] [ --identity <machine identity> ]")
			return false
		}
	}

	fun initalise(): Boolean {
		printTask("Initialising file structure...")

		val created = titanicFolder.mkdir()
		if (created) {
			printInfo("${titanicFolder.absolutePath} created successfully")
		} else {
			printError("${titanicFolder.absolutePath} could not be created")
		}
		return created
	}

	/*
	API key
	 */

	val apiKey: String
		get() = apiKeyFile.readText()

	val apiKeyIsSet: Boolean
		get() = apiKeyFile.exists() || apiKeyFile.isFile

	fun requireKeyIsSet(): Boolean {
		if (apiKeyIsSet) {
			return true
		} else {
			printError("No API key has been set")
			printInfo("To set an API key, run:")
			printInfo("    $commandPrefix set --api-key <your API apiKey>")
			return false
		}
	}

	fun setApiKey(apiKey: String) {
		printTask("Setting API key...")

		if (!apiKeyIsSet) {
			apiKeyFile.createNewFile()
		}
		apiKeyFile.writeText(apiKey)
		printInfo("API key set in ${apiKeyFile.absolutePath}")
	}

	/*
	Machine identity
	 */

	val identity: String
		get() = identityFile.readText()

	val identityIsSet: Boolean
		get() = identityFile.exists() || identityFile.isFile

	fun requireIdentityIsSet(): Boolean {
		if (identityIsSet) {
			return true
		} else {
			printError("No machine identity has been set")
			printInfo("To set an identity, run:")
			printInfo("    $commandPrefix set --identity <machine identity>")
			return false
		}
	}

	fun setIdentity(identity: String) {
		printTask("Setting identity...")

		if (!identityIsSet) {
			identityFile.createNewFile()
		}
		identityFile.writeText(identity)
		printInfo("Identity set in ${identityFile.absolutePath}")
	}

}
