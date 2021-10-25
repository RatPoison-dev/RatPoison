package rat.poison.utils.extensions

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap

private val upperCaseCache = Object2ObjectArrayMap<String, String>()
fun String.upper(): String {
    val get = upperCaseCache[this]
    return when (get == null) {
        true -> {
            val tmpStr = this.uppercase()
            upperCaseCache[this] = tmpStr
            tmpStr
        }
        else -> get
    }
}

private val lowerCaseCache = Object2ObjectArrayMap<String, String>()
fun String.lower(): String {
    val get = lowerCaseCache[this]
    return when (get == null) {
        true -> {
            val tmpStr = this.lowercase()
            lowerCaseCache[this] = tmpStr
            tmpStr
        }
        else -> get
    }
}