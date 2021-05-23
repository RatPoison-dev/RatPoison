package rat.poison.utils.generalUtil

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Matrix4
import rat.poison.dbg
import rat.poison.oWeapon
import rat.poison.oWeaponSize
import rat.poison.sWeapon
import rat.poison.utils.extensions.lower
import rat.poison.utils.maps.OneTimeMap
import java.util.regex.Pattern

fun Any.strToBool(): Boolean {
    val stringRepr = this.toString().lower()
    return stringRepr == "true" || this == true || this == 1.0 || this == 1 || this == 1F || stringRepr == "1"
}
fun Any.boolToStr() = this.toString()

fun Any.cToInt(): Int {
    val strRepr = this.toString()
    val dotIdx = strRepr.indexOf(".")
    if (dotIdx != -1) {
        return strRepr.subSequence(0, dotIdx).cToInt()
    }
    return strRepr.toInt()
}
fun Any.cToLong() = this.toString().toLong()
fun Any.strToColor() = convStrToColor(this.toString())
fun Any.strToColorGDX() = convStrToColorGDX(this.toString())
fun Any.cToDouble() = this.toString().toDouble()
fun Any.cToFloat() = this.toString().toFloat()

fun Boolean.toFloat() = if (this) 1F else 0F
fun Boolean.toDouble() = if (this) 1.0 else 0.0
fun Boolean.toInt() = if (this) 1 else 0

fun convStrToColor(input: String): rat.poison.game.Color { //Rat poison color
    try {
        var line = input
        line = line.replace("Color(", "").replace(")", "").replace(",", "")

        val arrayLine = line.trim().split(" ", ignoreCase = true, limit = 4)

        return rat.poison.game.Color(arrayLine[0].replace("red=", "").toInt(),
                arrayLine[1].replace("green=", "").toInt(),
                arrayLine[2].replace("blue=", "").toInt(),
                arrayLine[3].replace("alpha=", "").toDouble())
    } catch (e: Exception) {
        if (dbg) {
            println("[DEBUG] $input Color is invalid, using white")
        }

        return rat.poison.game.Color(255, 255, 255, 1.0)
    }
}

private val arrayLine = Array(4) { "" }
private val floatPatter = Pattern.compile("\\d+(\\.\\d+)?")
fun convStrToColorGDX(input: String): Color {
    val match = floatPatter.matcher(input)
    var idx = 0

    while (match.find()) {
        arrayLine[idx] = match.group()
        idx += 1
    }

    return if (arrayLine.size >= 4) Color(arrayLine[0].toFloat()/255F,
            arrayLine[1].toFloat()/255F,
            arrayLine[2].toFloat()/255F,
            arrayLine[3].toFloat()) else Color(1F, 1F, 1F, 1F)
}


private var stringToWeaponClassCache = OneTimeMap<String, oWeapon>()
fun String.toWeaponClass(): oWeapon {
    val get = stringToWeaponClassCache[this]
    return when (get == null) {
        true -> {
            var tStr = this
            tStr = tStr.replace("oWeapon(", "").replace(")", "")
            val tSA = tStr.split(", ") //temp String Array
            val size = tSA.size
            val finalOweapon = when (size < oWeaponSize) { //this should never happen?
                true -> oWeapon()
                false -> oWeapon(tOverride = tSA.pull(0).strToBool(), tFRecoil = tSA.pull(1).strToBool(), tOnShot = tSA.pull(2).strToBool(), tFlatAim = tSA.pull(3).strToBool(), tPathAim = tSA.pull(4).strToBool(), tAimBone = tSA.pull(5).stringToList(";"), tForceBone = tSA.pull(6).stringToList(";"), tAimFov = tSA.pull(7).toFloat(), tAimSmooth = tSA.pull(8).cToInt(), tPerfectAim = tSA.pull(9).strToBool(), tPAimFov = tSA.pull(10).toFloat(), tPAimChance = tSA.pull(11).toInt(), tScopedOnly = tSA.pull(12).strToBool(), tAimAfterShots = tSA.pull(13).toInt(), tBoneTrig = tSA.pull(14).strToBool(), tBTrigAim = tSA.pull(15).strToBool(), tBTrigInCross = tSA.pull(16).strToBool(), tBTrigInFov = tSA.pull(17).strToBool(), tBTrigBacktrack = tSA.pull(18).strToBool(), tBTrigFov = tSA.pull(19).toFloat(), tBTrigInitDelay = tSA.pull(20).toInt(), tBTrigPerShotDelay = tSA.pull(21).toInt(), tBacktrack = tSA.pull(22).strToBool(), tBTMS = tSA.pull(23).toInt(), tAutowep = tSA.pull(24).strToBool(), tAutowepDelay = tSA.pull(25).toInt())
            }
            stringToWeaponClassCache[this] = finalOweapon
            finalOweapon
        }
        false -> get
    }
}

private var stringToSkinWeaponClassCache = OneTimeMap<String, sWeapon>()
fun String.toSkinWeaponClass(): sWeapon {
    val get = stringToSkinWeaponClassCache[this]
    return when (get == null) {
        true -> {
            var tStr = this
            tStr = tStr.replace("sWeapon(", "").replace(")", "")
            val tSA = tStr.split(", ")

            val tmpWep = sWeapon(tSkinID = tSA.pull(0).toInt(), tStatTrak = tSA.pull(1).toInt(), tWear = tSA.pull(2).toFloat(), tSeed = tSA.pull(3).toInt())
            stringToSkinWeaponClassCache[this] = tmpWep
            tmpWep
        }
        false -> get
    }

}

fun List<String>.pull(idx: Int): String {
    val tStr = this[idx].replace(" ", "") //Remove spaces
    val split = tStr.split("=")
    return split[1]
}

fun List<Any>.has(predicate: (_: Any) -> Boolean): Boolean {
    var hasItem = false
    for (i in 0 until this.size) {
        val it = this[i]
        if (predicate(it) || hasItem) {
            hasItem = true
            break
        }
    }
    return hasItem
}

fun List<Any>.containsAny(lst: List<Any>): Boolean {
    for (i in 0 until lst.size) {
        val it = lst[i]
        if (!this.contains(it)) return false
    }
    return true
}

fun Array<String>.toGdxArray(): com.badlogic.gdx.utils.Array<String> {
    val itemsArray = com.badlogic.gdx.utils.Array<String>()
    this.forEach {
        itemsArray.add(it)
    }
    return itemsArray
}

private val intPattern = Pattern.compile("-?\\d+")
private val stringToIntListCache = OneTimeMap<String, List<Int>>()
fun String.stringToIntList(): List<Int> {
    val get = stringToIntListCache[this]
    return if (get == null) {
        val listOut = mutableListOf<Int>()
        val match = intPattern.matcher(this)
        while (match.find()) {
            listOut.add(match.group().toInt())
        }
        stringToIntListCache[this] = listOut
        return listOut
    }
    else {
        get
    }
}

private val DEFAULT_INVALID_LIST = listOf("")
private val stringToListCache = OneTimeMap<String, List<String>>()
fun String.stringToList(separator: String = ","): List<String> {
    val get = stringToListCache[this]
    return if (get == null) {
        val strList = this.replace("[", "").replace("]", "").replace(" ", "").split(separator)
        val listOut = mutableListOf<String>()
        if (strList != DEFAULT_INVALID_LIST) {
            for (i in strList) {
                listOut.add(i)
            }
        }
        listOut
    }
    else get
}

private val floatArray = ThreadLocal.withInitial { FloatArray(16) }
//Matrix 4 uses column-major order
fun Array<DoubleArray>.toMatrix4(mat4: Matrix4 = Matrix4()): Matrix4 {
    val input = this
    val fArr = floatArray.get()

    var itr = 0
    for (row in 0..3) {
        for (col in 0..3) {
            fArr[itr] = input[col][row].toFloat()
            itr++
        }
    }

    mat4.set(fArr)
    return mat4
}