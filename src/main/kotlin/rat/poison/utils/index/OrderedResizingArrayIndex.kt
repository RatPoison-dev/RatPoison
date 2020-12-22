package rat.poison.utils.index

/**
 * An index that automatically grows when out of capacity
 * and which ensures that the order of insertion is kept.
 *
 * (This is less memory efficient than [ResizingArrayIndex])
 *
 * @author Jire
 */
class OrderedResizingArrayIndex<T>
@JvmOverloads constructor(elementClass: Class<in T>,
                          initialCapacity: Int = 8,
                          growthFactor: Double = 2.0)
	: ResizingArrayIndex<T>(elementClass, initialCapacity, growthFactor) {
	
	private var lastInsertionIndex = 0
	
	override fun freeIndex(): Int {
		for (index in lastInsertionIndex..array.lastIndex) {
			if (array[index] == null) {
				return index
			}
		}
		Exception("No free index... $size, ${array.size}").printStackTrace()
		return -1
	}
	
	override fun add(value: T): Boolean {
		if (iterating) IllegalStateException("Can't add while iterating.").printStackTrace()
		
		ensureCapacity()
		val index = freeIndex()
		if (index >= 0) {
			array[index] = value
			size++
			lastInsertionIndex = index
			return true
		}
		return false
	}
	
}