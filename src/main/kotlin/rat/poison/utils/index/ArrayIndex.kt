package rat.poison.utils.index

import java.util.*

/**
 * An index that is backed by an array.
 *
 * @author Jire
 */
abstract class ArrayIndex<T>(var array: Array<T?>) : AbstractIndex<T>() {
	
	var size = 0
	
	override fun getValues(): Array<T?> = array
	
	override fun size(): Int {
		/*var realSize = 0
		for (value in array)
			if (value != null) realSize++
		if (realSize != size) IllegalStateException("WTF? Size mismatch $realSize vs $size...").printStackTrace()*/
		return size
	}
	
	override fun set(index: Int, value: T?): Boolean {
		val lastValue = array[index]
		array[index] = value
		if (value == null && lastValue != null)
			size--
		else if (value != null && lastValue == null)
			size++
		return lastValue != null
	}
	
	override fun indexOf(value: T?): Int {
		value ?: return -1 // fail-fast
		
		for (index in 0..array.lastIndex) {
			if (value == array[index]) {
				return index
			}
		}
		return -1
	}
	
	override fun clear() {
		if (iterating) IllegalStateException("Can't clear while iterating.").printStackTrace()
		//if (size < 1) return // fail-fast
		
		Arrays.fill(array, null)
		size = 0
	}
	
	open fun freeIndex(): Int {
		for (index in 0..array.lastIndex) {
			if (array[index] == null) {
				return index
			}
		}
		Exception("No free index... $size, ${array.size}").printStackTrace()
		return -1
	}
	
	override fun add(value: T): Boolean {
		if (iterating) IllegalStateException("Can't add while iterating.").printStackTrace()
		val index = freeIndex()
		if (index >= 0) {
			array[index] = value
			size++
			return true
		}
		return false
	}
	
}