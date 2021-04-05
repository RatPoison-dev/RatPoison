package rat.poison.utils.generalUtil

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Matrix4
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import rat.poison.*
import rat.poison.utils.extensions.lower
import java.util.regex.Pattern

fun Any.strToBool() = this.toString().lower() == "true" || this == true || this == 1.0 || this == 1 || this == 1F
fun Any.boolToStr() = this.toString()
fun Any.cToInt() = this.toString().toInt()
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


private var stringToWeaponClassCache = Object2ObjectArrayMap<String, oWeapon>()
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
                false -> oWeapon(tOverride = tSA.pull(0).strToBool(), tFRecoil = tSA.pull(1).strToBool(), tOnShot = tSA.pull(2).strToBool(), tFlatAim = tSA.pull(3).strToBool(), tPathAim = tSA.pull(4).strToBool(), tAimBone = tSA.pull(5).stringToList(";"), tForceBone = tSA.pull(6).stringToList(";"), tAimFov = tSA.pull(7).toFloat(), tAimSpeed = tSA.pull(8).toInt(), tAimSmooth = tSA.pull(9).toFloat(), tPerfectAim = tSA.pull(10).strToBool(), tPAimFov = tSA.pull(11).toFloat(), tPAimChance = tSA.pull(12).toInt(), tScopedOnly = tSA.pull(13).strToBool(), tAimAfterShots = tSA.pull(14).toInt(), tBoneTrig = tSA.pull(15).strToBool(), tBTrigAim = tSA.pull(16).strToBool(), tBTrigInCross = tSA.pull(17).strToBool(), tBTrigInFov = tSA.pull(18).strToBool(), tBTrigBacktrack = tSA.pull(19).strToBool(), tBTrigFov = tSA.pull(20).toFloat(), tBTrigInitDelay = tSA.pull(21).toInt(), tBTrigPerShotDelay = tSA.pull(22).toInt(), tBacktrack = tSA.pull(23).strToBool(), tBTMS = tSA.pull(24).toInt(), tAutowep = tSA.pull(25).strToBool(), tAutowepDelay = tSA.pull(26).toInt())
            }
            stringToWeaponClassCache[this] = finalOweapon
            finalOweapon
        }
        false -> get
    }
}

private var stringToSkinWeaponClassCache = Object2ObjectArrayMap<String, sWeapon>()
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
    this.forEach {
        if (predicate(it) || hasItem) {
            hasItem = true
            return@forEach
        }
    }
    return hasItem
}

fun List<Any>.containsAny(lst: List<Any>): Boolean {
    lst.forEach {
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
private val stringToIntListCache = Object2ObjectArrayMap<String, MutableList<Int>>()
fun String.stringToIntList(listOut: MutableList<Int> = mutableListOf()): MutableList<Int> {
    val get = stringToIntListCache[this]
    return if (get == null) {
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
private val stringToListCache = Object2ObjectArrayMap<String, List<String>>()
fun String.stringToList(separator: String = ",", listOut: MutableList<String> = mutableListOf()): List<String> {
    val get = stringToListCache[this]
    return if (get == null) {
        val strList = this.replace("[", "").replace("]", "").replace(" ", "").split(separator)

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