package rat.poison.utils.index

/**
 * An index that does nothing.
 *
 * @author Jire
 */
class EmptyArrayIndex<T>(elementClass: Class<in T>) : FixedArrayIndex<T>(elementClass, 0) {
	
	override fun size(): Int = 0
	
	override fun add(value: T): Boolean = false
	
	override fun set(index: Int, value: T?): Boolean = false
	
	override fun indexOf(value: T?): Int = -1
	
	override fun clear() {
	}
	
}