package rat.poison.utils.index

/**
 * An index that automatically grows when out of capacity.
 *
 * @author Jire
 */
open class ResizingArrayIndex<T>
@JvmOverloads constructor(elementClass: Class<in T>,
                          initialCapacity: Int = 8,
                          val growthFactor: Double = 2.0)
	: ReflectionCreatedArrayIndex<T>(elementClass, initialCapacity) {
	
	protected open fun ensureCapacity(oldCapacity: Int, newCapacity: Int) {
		val expandedArray = constructArray(elementClass, newCapacity)
		System.arraycopy(array, 0, expandedArray, 0, oldCapacity)
		this.array = expandedArray
	}
	
	protected open fun ensureCapacity() {
		val oldCapacity = array.size
		if (size >= oldCapacity) {
			val newCapacity = (oldCapacity * growthFactor).toInt()
			ensureCapacity(oldCapacity, newCapacity)
		}
	}
	
	override fun add(value: T): Boolean {
		ensureCapacity()
		return super.add(value)
	}
	
	override fun hashCode(): Int {
		return array.hashCode()
	}
	
	override fun equals(other: Any?): Boolean {
		return other is ResizingArrayIndex<*> && hashCode() == other.hashCode()
	}
	
}