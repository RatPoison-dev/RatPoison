package rat.poison.utils.maps

class OneTimeMap<K, V> {
    private val cachedValues = mutableMapOf<K, V>()
    operator fun set(k: K, v: V) {
        if (!containsKey(k)) cachedValues[k] = v
    }
    fun containsKey(k: K): Boolean = cachedValues.containsKey(k)
    operator fun get(k: K): V? = cachedValues[k]
}