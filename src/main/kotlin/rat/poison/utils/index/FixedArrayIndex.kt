package rat.poison.utils.index

/**
 * An array-backed index that stays fixed at the specified capacity.
 *
 * @author Jire
 */
open class FixedArrayIndex<T>(elementClass: Class<in T>,
                              val capacity: Int)
	: ReflectionCreatedArrayIndex<T>(elementClass, capacity)