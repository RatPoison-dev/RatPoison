package rat.poison.utils.index

/**
 * @author Jire
 */
abstract class ReflectionCreatedArrayIndex<T>(protected val elementClass: Class<in T>,
                                              protected val initialCapacity: Int)
	: ArrayIndex<T>(constructArray(elementClass, initialCapacity)) {
	
	protected companion object {
		
		@JvmStatic
		@Suppress("UNCHECKED_CAST")
		protected fun <T> constructArray(elementClass: Class<in T>, arraySize: Int): Array<T?> {
			return java.lang.reflect.Array.newInstance(elementClass, arraySize) as Array<T?>
		}
		
	}
	
}