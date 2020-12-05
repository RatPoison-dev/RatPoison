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

    return if (arrayLine.size >= 4) Color(arrayLine[0].replace("red=", "").toFloat()/255F,
            arrayLine[1].replace("green=", "").toFloat()/255F,
            arrayLine[2].replace("blue=", "").toFloat()/255F,
            arrayLine[3].replace("alpha=", "").toFloat()) else Color(255F, 255F, 255F, 1F)
}


// WE ARE SENDING OUR FIRST RAT TO THE MOON BOYS
fun String.toWeaponClass(): oWeapon {
    var tStr = this
    tStr = tStr.replace("oWeapon(", "").replace(")", "")
    val tSA = tStr.split(", ") //temp String Array
    val weapon = oWeapon()
    val size = tSA.size

    weapon.apply {
        tOverride = if (size > 1) tSA.pull(0).safeToBool(defaultValue = tOverride) else tOverride
        tFRecoil = if (size > 2) tSA.pull(1).safeToBool(defaultValue = tFRecoil) else tFRecoil
        tOnShot = if (size > 3) tSA.pull(2).safeToBool(defaultValue = tOnShot) else tOnShot
        tFlatAim = if (size > 4) tSA.pull(3).safeToBool(defaultValue = tFlatAim) else tFlatAim
        tPathAim = if (size > 5) tSA.pull(4).safeToBool(defaultValue = tPathAim) else tPathAim
        tAimBone = if (size > 6) tSA.pull(5).safeToInt(defaultValue = tAimBone) else tAimBone
        tForceBone = if (size > 7) tSA.pull(6).safeToInt(defaultValue = tForceBone) else tForceBone
        tAimFov = if (size > 8) tSA.pull(7).safeToFloat(defaultValue = tAimFov) else tAimFov
        tAimSpeed = if (size > 9) tSA.pull(8).safeToInt(defaultValue = tAimSpeed) else tAimSpeed
        tAimSmooth = if (size > 10) tSA.pull(9).safeToFloat(defaultValue = tAimSmooth) else tAimSmooth
        tPerfectAim = if (size > 11) tSA.pull(10).safeToBool(defaultValue = tPerfectAim) else tPerfectAim
        tPAimFov = if (size > 12) tSA.pull(11).safeToFloat(defaultValue = tPAimFov) else tPAimFov
        tPAimChance = if (size > 13) tSA.pull(12).safeToInt(defaultValue = tPAimChance) else tPAimChance
        tScopedOnly = if (size > 14) tSA.pull(13).safeToBool(defaultValue = tScopedOnly) else tScopedOnly
        tAimAfterShots = if (size > 15) tSA.pull(14).safeToInt(defaultValue = tAimAfterShots) else tAimAfterShots
        tBoneTrig = if (size > 16) tSA.pull(15).safeToBool(defaultValue = tBoneTrig) else tBoneTrig
        tBTrigAim = if (size > 17) tSA.pull(16).safeToBool(defaultValue = tBTrigAim) else tBTrigAim
        tBTrigInCross = if (size > 18) tSA.pull(17).safeToBool(defaultValue = tBTrigInCross) else tBTrigInCross
        tBTrigInFov = if (size > 19) tSA.pull(18).safeToBool(defaultValue = tBTrigInFov) else tBTrigInFov
        tBTrigBacktrack = if (size > 20) tSA.pull(19).safeToBool(defaultValue = tBTrigBacktrack) else tBTrigBacktrack
        tBTrigFov = if (size > 21) tSA.pull(20).safeToFloat(defaultValue = tBTrigFov) else tBTrigFov
        tBTrigInitDelay = if (size > 22) tSA.pull(21).safeToInt(defaultValue = tBTrigInitDelay) else tBTrigInitDelay
        tBTrigPerShotDelay = if (size > 23) tSA.pull(22).safeToInt(defaultValue = tBTrigPerShotDelay) else tBTrigPerShotDelay
        tBacktrack = if (size > 24) tSA.pull(23).safeToBool(defaultValue = tBacktrack) else tBacktrack
        tBTMS = if (size > 25) tSA.pull(24).safeToInt(defaultValue = tBTMS) else tBTMS
        tAutowep = if (size > 26) tSA.pull(25).safeToBool(defaultValue = tAutowep) else tAutowep
        tAutowepDelay = if (size >= 27) tSA.pull(26).safeToInt(defaultValue = tAutowepDelay) else tAutowepDelay
    }
    return weapon
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