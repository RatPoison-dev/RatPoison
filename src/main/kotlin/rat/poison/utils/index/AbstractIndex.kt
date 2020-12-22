package rat.poison.utils.index

/**
 * @author Jire
 */
abstract class AbstractIndex<T> : Index<T> {
	
	@Volatile
	protected var iterating = false
	
	@Volatile
	protected lateinit var iteratingThrowable: Throwable
	
	override fun beginIteration(): Index<T> = apply {
		if (iterating) {
			throw IllegalStateException("Can't start iteration because it was already started.", iteratingThrowable)
		}
		iterating = true
		iteratingThrowable = Throwable()
	}
	
	override fun endIteration(): Index<T> = apply {
		if (!iterating) {
			throw IllegalStateException("Can't end iteration because it was already ended.", iteratingThrowable)
		}
		iterating = false
	}
	
	override fun isIterating(): Boolean = iterating
	
}