package rat.poison.utils

import it.unimi.dsi.fastutil.longs.Long2ObjectMap

inline fun <T, R> Long.readCached(cache: Long2ObjectMap<T>, crossinline construct: () -> T,
                                  crossinline read: T.(Long) -> R): T {
	val t: T =
			if (cache.containsKey(this))
				cache.get(this)
			else {
				val tt = construct()
				cache[this] = tt
				tt
			}
	t.read(this)
	return t
}