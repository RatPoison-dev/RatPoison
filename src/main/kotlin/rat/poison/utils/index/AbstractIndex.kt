package rat.poison.utils.index

/**
 * @author Jire
 */
abstract class AbstractIndex<T> : Index<T> {
	
	protected var iterating = false
	
	override fun beginIteration(): Index<T> = apply {
		if (iterating) {
			throw IllegalStateException("Can't start iteration because it was already started.")
		}
		iterating = true
	}
	
	override fun endIteration(): Index<T> = apply {
		if (!iterating) {
			throw IllegalStateException("Can't end iteration because it was already ended.")
		}
		iterating = false
	}
	
	override fun isIterating(): Boolean = iterating
	
}