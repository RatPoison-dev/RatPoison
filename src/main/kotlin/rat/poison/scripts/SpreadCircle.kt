package rat.poison.scripts

import com.badlogic.gdx.math.MathUtils.clamp
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.overlay.App
import rat.poison.settings.MENUTOG
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColorGDX
import rat.poison.utils.notInGame
import java.io.File
import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import kotlin.math.atan
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.tan

private data class WeaponData(var maxPlayerSpeed: Int = 0, var spread: Float = 0f, var inaccuracyFire: Float = 0f,
                              var inaccuracyMove: Float = 0f, var inaccuracyFireAlt: Float = 0f, var inaccuracyMoveAlt: Float = 0f,
                              var maxPlayerSpeedAlt: Int = 0, var spreadAlt: Float = 0f)

private var wepData = WeaponData()

private fun refreshWepData() = every(100) {
    wepData = getWeaponData(me.weapon().name)
}

fun spreadCircle() {
    refreshWepData()

    App {
        if (me.dead() || MENUTOG || !curSettings["ENABLE_ESP"].strToBool() || !curSettings["SPREAD_CIRCLE"].strToBool() || notInGame) return@App

        val vAbsVelocity = me.velocity()
        val flVelocity = sqrt(vAbsVelocity.x.pow(2F) + vAbsVelocity.y.pow(2F) + vAbsVelocity.z.pow(2F))

        var radius = wepData.inaccuracyMove * (flVelocity / wepData.maxPlayerSpeed)
        radius += clamp(me.shotsFired() * wepData.inaccuracyFire, 0f, wepData.spreadAlt * 100)

        val defaultFov = csgoEXE.int(me + NetVarOffsets.m_iDefaultFov)
        val iFov = csgoEXE.int(me + NetVarOffsets.m_iFOV)
        val viewFov: Int

        viewFov = if (iFov == 0) {
            defaultFov
        } else {
            iFov
        }

        val actualRadius = toDegrees(atan(radius / 2560.0 * tan(toRadians((2.0 * toDegrees(atan((16.0 / 9.0) * 0.75 * tan(toRadians(90 / 2.0))))) / 2.0)))) * 2.0
        val realFov = calcFovRadius(viewFov, actualRadius.toFloat())

        val rccXo = curSettings["RCROSSHAIR_XOFFSET"].toFloat()
        val rccYo = curSettings["RCROSSHAIR_YOFFSET"].toFloat()
        val x = CSGO.gameWidth / 2 + rccXo
        val y = CSGO.gameHeight / 2 + rccYo

        shapeRenderer.apply {
            if (isDrawing) {
                end()
            }

            begin()

            color = curSettings["SPREAD_CIRCLE_COLOR"].strToColorGDX()
            circle(x, y, realFov)

            end()
        }
    }
}

private fun getWeaponData(wep: String): WeaponData {
    val wepData = WeaponData()
    var strList: List<String>

    File("$SETTINGS_DIRECTORY\\Data\\WeaponStats.txt").forEachLine { line->
        if (line.startsWith(wep)) {
            strList = line.split(" : ")

            try {
                wepData.maxPlayerSpeed = strList[1].toInt()
                wepData.spread = strList[2].toFloat()
                wepData.inaccuracyFire = strList[3].toFloat()
                wepData.inaccuracyMove = strList[4].toFloat()
                wepData.inaccuracyFireAlt = strList[5].toFloat()
                wepData.inaccuracyMoveAlt = strList[6].toFloat()
                wepData.maxPlayerSpeedAlt = strList[7].toInt()
                wepData.spreadAlt = strList[8].toFloat()
            } catch (e: Exception) {
                println("$strList is FUCKING WRONG BROOOOOOOOO FUCK")
            }
        }
    }

    return wepData
}


















//fun getSkinArray(wep: String): Array<String> {
//    val wepSkinArray = Array<String>()
//    var readingLines = false
//
//    File("$SETTINGS_DIRECTORY\\Data\\SkinInfo.txt").forEachLine { line->
//        if (readingLines) {
//            if (line.startsWith("}")) {
//                readingLines = false
//            } else if (!line.startsWith("{")) { //Store the bois
//                val tmpSplitLine = line.split(":")
//                wepSkinArray.add(tmpSplitLine[0].trim()) //Add name
//            }
//        }
//
//        if (line.startsWith(wep)) {
//            readingLines = true
//        }
//    }
//
//    return wepSkinArray
//}
//
//fun getSkinNameFromID(ID: Int): String {
//    var str = ""
//    var found = false
//    File("$SETTINGS_DIRECTORY\\Data\\SkinInfo.txt").forEachLine { line->
//        if (!found) {
//            if (line.contains(":")) {
//                val tmpSplitLine = line.split(":")
//                if (tmpSplitLine[1].trim().toInt() == ID) {
//                    str = tmpSplitLine[0].trim()
//                    found = true
//                }
//            }
//        }
//    }
//
//    return str
//}
//
//fun getSkinIDFromName(Name: String, Weapon: String): Int {
//    var id = 0
//    var inCategory = ""
//    var found = false
//    File("$SETTINGS_DIRECTORY\\Data\\SkinInfo.txt").forEachLine { line->
//        if (!found) {
//            if (line.contains("{")) {
//                inCategory = line.replace("{", "").trim()
//            }
//
//            if (line.contains(":")) {
//                val tmpSplitLine = line.split(":")
//                if (tmpSplitLine[0].trim() == Name && inCategory == Weapon) {
//                    id = tmpSplitLine[1].trim().toInt()
//                    found = true
//                }
//            }
//        }
//    }
//
//    return id
//}
//
//fun getMinValueFromID(ID: Int): Float {
//    var minValue = 0.0F
//    var found = false
//    File("$SETTINGS_DIRECTORY\\Data\\SkinInfo.txt").forEachLine { line->
//        if (!found) {
//            if (line.contains(":")) {
//                val tmpSplitLine = line.split(":")
//                if (tmpSplitLine[1].trim().toInt() == ID) {
//                    minValue = tmpSplitLine[2].trim().toFloat()
//                    found = true
//                }
//            }
//        }
//    }
//
//    return minValue
//}
//
//fun getMaxValueFromID(ID: Int): Float {
//    var minValue = 0.0F
//    var found = false
//    File("$SETTINGS_DIRECTORY\\Data\\SkinInfo.txt").forEachLine { line->
//        if (!found) {
//            if (line.contains(":")) {
//                val tmpSplitLine = line.split(":")
//                if (tmpSplitLine[1].trim().toInt() == ID) {
//                    minValue = tmpSplitLine[3].trim().toFloat()
//                    found = true
//                }
//            }
//        }
//    }
//
//    return minValue
//}
//
//fun skinChangerTabUpdate() {
//    skinChangerTab.apply {
//        enableSkinChanger.update()
//        autoForceUpdate.update()
//    }
//}