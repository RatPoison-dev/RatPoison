package rat.poison.utils.extensions

import com.badlogic.gdx.utils.IntMap

open class EnumLookUpWithDefault<T>(map: Map<Int, T>,
                                    private val defaultValue: T) {
    //to get rid of type casting
    private val valueMap: IntMap<T> = IntMap(map.size)

    init {
        map.forEach { (k, v) -> valueMap.put(k, v) }
    }

    operator fun get(id: Int) = valueMap[id] ?: defaultValue
}

open class EnumLookUp<T>(map: Map<Int, T>) {
    //to get rid of type casting
    private val valueMap: IntMap<T> = IntMap(map.size)

    init {
        map.forEach { (k, v) -> valueMap.put(k, v) }
    }

    operator fun get(id: Int): T? = valueMap[id]
    private fun getOrDefault(id: Int, default: T): T = valueMap.get(id, default)
    operator fun get(id: Int, default: T) = getOrDefault(id, default)
}
