package uk.co.markormesher.titanic.sync

import java.io.File

class Config(titanicFolder: File) {

	init {
		if (!titanicFolder.exists() || !titanicFolder.isDirectory) {
			throw IllegalArgumentException("${titanicFolder.absoluteFile} does not exist or is not a folder. Maybe you need to run the 'init' command?")
		}
	}

}
