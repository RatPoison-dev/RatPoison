package rat.poison.utils.index

/**
 * @author Jire
 */
interface Index<T> {
	
	/**
	 * Retrieves the backing array of this index.
	 *
	 * WARNING: This is NOT a copy of the backing array. Modifications to it can cause issues.
	 */
	fun getValues(): Array<T?>
	
	/**
	 * Retrieves the size, or "count" of the index; referring to the number of non-null elements.
	 */
	fun size(): Int
	
	/**
	 * Checks whether or not the index is empty.
	 * This is functionally equivalent to ```size() <= 0```
	 */
	fun isEmpty(): Boolean = size() <= 0
	
	/**
	 * Attempts to add the specified value to the index.
	 *
	 * @param value The value to attempt to add.
	 *
	 * @return Whether or not the value was successfully added to the index.
	 */
	fun add(value: T): Boolean
	
	/**
	 * Gets an element at the specified index.
	 *
	 * @param index The index to attempt to retrieve at.
	 *
	 * @return ```null``` if the index was out of bounds, otherwise the value of the array.
	 */
	operator fun get(index: Int): T? {
		if (index < 0) return null
		val values = getValues()
		if (index >= values.size) return null
		return values[index]
	}
	
	/**
	 * Sets an element at the specified index to the specified value.
	 *
	 * @param index The index to set at.
	 * @param value The value to set the index to.
	 *
	 * @return Whether or not a previous value was replaced.
	 */
	operator fun set(index: Int, value: T?): Boolean
	
	/**
	 * Retrieves the index of the specified value, otherwise -1.
	 *
	 * @param value The value to check.
	 *
	 * @return The index of the specified value, otherwise -1.
	 */
	fun indexOf(value: T?): Int
	
	/**
	 * Checks if this index contains the specified value.
	 * This is functionally equivalent to ```indexOf(value) >= 0```
	 *
	 * @param value The value to check.
	 */
	fun contains(value: T?) = indexOf(value) >= 0
	
	/**
	 * Empties the index so that its size will be 0.
	 */
	fun clear()
	
	/**
	 * @param index The index to attempt to remove at.
	 * This is functionally equivalent to ```set(index, null)```
	 *
	 * @return Whether or not a value was removed.
	 */
	fun removeAt(index: Int) = set(index, null)
	
	/**
	 * Attempts to remove the specified value.
	 *
	 * @param value The value to attempt to remove.
	 *
	 * @return Whether or not the value was successfully removed.
	 */
	fun remove(value: T?): Boolean {
		val index = indexOf(value)
		if (index >= 0) {
			return removeAt(index)
		}
		return false
	}
	
	/**
	 * Adds all the values from the specified index to this index.
	 */
	fun addAll(sourceIndex: Index<T>) {
		val values = sourceIndex.getValues()
		for (i in 0..values.lastIndex) {
			val value = values[i] ?: continue
			add(value)
		}
	}
	
	fun beginIteration(): Index<T>
	
	fun endIteration(): Index<T>
	
	fun isIterating(): Boolean
	
	fun firstOrNull(): T? {
		beginIteration()
		try {
			val values = getValues()
			for (i in 0..values.lastIndex) {
				val value = values[i]
				if (value != null) {
					return value
				}
			}
		} finally {
			endIteration()
		}
		return null
	}
	
}