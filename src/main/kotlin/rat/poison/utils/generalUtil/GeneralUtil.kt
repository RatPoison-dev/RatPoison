package rat.poison.utils.generalUtil

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Matrix4
import rat.poison.dbg
import rat.poison.oWeapon
import rat.poison.sWeapon

fun Any.strToBool() = this.toString().toLowerCase() == "true" || this == true || this == 1.0 || this == 1 || this == 1F
fun Any.boolToStr() = this.toString()
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

        val arrayLine = line.trim().split(" ".toRegex(), 4)

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

fun convStrToColorGDX(input: String): Color {
    var line = input
    line = line.replace("Color(", "").replace(")", "").replace(",", "")

    val arrayLine = line.trim().split(" ".toRegex(), 4)

    return Color(arrayLine[0].replace("red=", "").toFloat()/255F,
            arrayLine[1].replace("green=", "").toFloat()/255F,
            arrayLine[2].replace("blue=", "").toFloat()/255F,
            arrayLine[3].replace("alpha=", "").toFloat())
}

fun String.toWeaponClass(): oWeapon {
    var tStr = this
    tStr = tStr.replace("oWeapon(", "").replace(")", "")
    val tSA = tStr.split(", ") //temp String Array
    return oWeapon(tOverride = tSA.pull(0).strToBool(), tFRecoil = tSA.pull(1).strToBool(), tFlatAim = tSA.pull(2).strToBool(), tPathAim = tSA.pull(3).strToBool(), tAimBone = tSA.pull(4).toInt(), tAimFov = tSA.pull(5).toFloat(), tAimSpeed = tSA.pull(6).toInt(), tAimSmooth = tSA.pull(7).toDouble(), tPerfectAim = tSA.pull(8).strToBool(), tPAimFov = tSA.pull(9).toInt(), tPAimChance = tSA.pull(10).toInt(), tScopedOnly = tSA.pull(11).strToBool())//, tBoneTrig = tSA.pull(13).strToBool(), tBTrigBone = tSA.pull(14).toInt(), tBTrigAim = tSA.pull(15).strToBool(), tBTrigDelay = tSA.pull(16).toInt())
}

fun String.toSkinWeaponClass(): sWeapon {
    var tStr = this
    tStr = tStr.replace("sWeapon(", "").replace(")", "")
    val tSA = tStr.split(", ")
    return sWeapon(tSkinID = tSA.pull(0).toInt(), tStatTrak = tSA.pull(1).toInt(), tWear = tSA.pull(2).toFloat(), tSeed = tSA.pull(3).toInt())
}

fun List<String>.pull(idx: Int): String {
    val tStr = this[idx].replace(" ", "") //Remove spaces
    val split = tStr.split("=")
    return split[1]
}

//Matrix 4 uses column-major order
fun Array<DoubleArray>.toMatrix4(): Matrix4 {
    val input = this
    val mat4 = Matrix4()
    val fArr = FloatArray(16)

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