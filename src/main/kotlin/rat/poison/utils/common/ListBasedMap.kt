package rat.poison.utils.common

class ListBasedMap<T, R> {
    var keys = mutableListOf<T>()
    var values = mutableListOf<R>()
    operator fun set(key: T, value: R) {
        for (i in 0 until keys.size) {
            if (keys[i] == key) {
                values[i] = value
                return
            }
        }
        keys.add(key)
        values.add(value)
    }
    operator fun get(key: T): R? {
        for (i in 0 until keys.size) {
            if (keys[i] == key) return values[i]
        }
        return null
    }
    fun containsKey(key: T) = keys.contains(key)
}