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
    return if (this != "") {
        var tStr = this
        tStr = tStr.replace("oWeapon(", "").replace(")", "")
        val tSA = tStr.split(", ") //temp String Array
        oWeapon(enableOverride = tSA.pull(0).strToBool(), factorRecoil = tSA.pull(1).strToBool(), onShot = tSA.pull(2).strToBool(), writeAngles = tSA.pull(3).strToBool(), mouseMovements = tSA.pull(4).strToBool(), aimBone = tSA.pull(5).toInt(), aimForceBone = tSA.pull(6).toInt(), aimFOV = tSA.pull(7).toFloat(), aimSpeed = tSA.pull(8).toInt(), aimSmoothness = tSA.pull(9).toFloat(), enablePerfectAim = tSA.pull(10).strToBool(), perfectAimFov = tSA.pull(11).toFloat(), perfectAimChance = tSA.pull(12).toInt(), aimScopedOnly = tSA.pull(13).strToBool(), aimAfterShoots = tSA.pull(14).toInt(), enableTriggerBot = tSA.pull(15).strToBool(), triggerAim = tSA.pull(16).strToBool(), triggerIsInCross = tSA.pull(17).strToBool(), triggerIsInFOV = tSA.pull(18).strToBool(), triggerShootBacktrack = tSA.pull(19).strToBool(), triggerFOV = tSA.pull(20).toFloat(), triggerInitDelay = tSA.pull(21).toInt(), triggerDelayBetweenShoots = tSA.pull(22).toInt(), enableBacktrack = tSA.pull(23).strToBool(), backtrackMS = tSA.pull(24).toInt(), autoWepDelay = tSA.pull(25).toInt(), enableAutomatic = tSA.pull(26).toBoolean())
    } else {
        oWeapon()
    }
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