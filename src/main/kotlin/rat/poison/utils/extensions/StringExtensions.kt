package rat.poison.utils.extensions

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap

private val upperCaseCache = Object2ObjectOpenHashMap<String, String>()
fun String.upper(): String {
    val get = upperCaseCache[this]
    return when (get == null) {
        true -> {
            val tmpStr = this.toUpperCase()
            upperCaseCache[this] = tmpStr
            tmpStr
        }
        else -> get
    }
}

private val lowerCaseCache = Object2ObjectOpenHashMap<String, String>()
fun String.lower(): String {
    val get = lowerCaseCache[this]
    return when (get == null) {
        true -> {
            val tmpStr = this.toLowerCase()
            lowerCaseCache[this] = tmpStr
            tmpStr
        }
        else -> get
    }
}

private val splitCache = Object2ObjectOpenHashMap<String, List<String>>()
fun String.splitCached(delimiter: String): List<String> {
    val get = splitCache[this]
    return when (get == null) {
        true -> {
            val tmpSplit = this.split(delimiter)
            splitCache[this] = tmpSplit
            tmpSplit
        }
        else -> get
    }
}