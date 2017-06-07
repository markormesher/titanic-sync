package uk.co.markormesher.titanic.sync.helpers

private val BOLD = "\u001B[1m"
private val RESET = "\u001B[0m"

fun printTask(message: String)
		= println(" [ Task]  $message")

fun printInfo(message: String)
		= println(" [ Info]  $message")

fun printDone(message: String)
		= println(" $BOLD[ Done]$RESET  $message")

fun printError(message: String)
		= println(" $BOLD[Error]$RESET  $message")
