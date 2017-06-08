package uk.co.markormesher.titanic.sync.helpers

/**
 * Execute a series of dependent tasks, each of which uses a callback to indicate whether the task was successful.
 */
fun waterfall(vararg tasks: ((success: Boolean) -> Unit) -> Unit, always: (() -> Unit)? = null) = execWaterfall(0, *tasks, always = always)

private fun execWaterfall(i: Int, vararg tasks: ((success: Boolean) -> Unit) -> Unit, always: (() -> Unit)? = null) {
	if (i < tasks.size) {
		tasks[i].invoke({ success ->
			if (success) {
				execWaterfall(i + 1, *tasks, always = always)
			} else {
				always?.invoke()
			}
		})
	} else {
		always?.invoke()
	}
}
