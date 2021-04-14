package rat.poison.utils.maps

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap
import net.openhft.hashing.LongHashFunction
import org.jetbrains.kotlin.fir.extensions.predicate.has
import java.lang.StringBuilder

@PublishedApi
internal val hashFunction = LongHashFunction.farmNa()

//hashing like crazy
//credits to Jire

class StringToAnyMap<V> {
    val longToStringMap = Long2ObjectOpenHashMap<String>()
    private val cachedValues = Long2ObjectOpenHashMap<V>()
    operator fun set(k: String, v: V) {
        put(k, v)
    }
    operator fun set(k: StringBuilder, v: V) {
        put(k, v)
    }
    fun put(k: String, v: V) {
        cachedValues[hashChars(k)] = v
    }
    fun put(k: StringBuilder, v: V) {
        cachedValues[hashChars(k)] = v
    }
    private fun hashChars(k: String): Long {
        val hashed = hashFunction.hashChars(k)
        if (!longToStringMap.containsKey(hashed)) longToStringMap[hashed] = k //fk u
        return hashed
    }
    private fun hashChars(k: StringBuilder): Long = hashFunction.hashChars(k)
    fun remove(k: String) {
        cachedValues.remove(hashChars(k))
    }
    fun remove(k: StringBuilder) {
        cachedValues.remove(hashChars(k))
    }
    val keys by lazy { cachedValues.keys }
    operator fun get(k: String): V? = cachedValues[hashChars(k)]
    operator fun get(k: StringBuilder): V? = cachedValues[hashChars(k)]
}