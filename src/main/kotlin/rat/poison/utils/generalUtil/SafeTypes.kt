package rat.poison.utils.generalUtil

import rat.poison.dbg

fun String.safeToInt(identifier: String = "", defaultValue: Int = 1): Int {
    return try {
        this.toInt()
    } catch(e: Exception) {
        if (dbg) { println("[DEBUG] $identifier is invalid") }
        defaultValue
    }
}

fun String.safeToBool(identifier: String = "", defaultValue: Boolean = false): Boolean {
    return try {
        this.strToBool()
    } catch (e: Exception) {
        if (dbg && identifier != "") { println("[DEBUG] $identifier is invalid") }
        defaultValue
    }
}

fun String.safeToFloat(identifier: String = "", defaultValue: Float = 0F): Float {
    return try {
        this.toFloat()
    } catch (e: Exception) {
        if (dbg && identifier != "") { println("[DEBUG] $identifier is invalid") }
        defaultValue
    }
}