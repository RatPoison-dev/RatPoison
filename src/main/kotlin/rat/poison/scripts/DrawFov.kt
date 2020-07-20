package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils.clamp
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.weapon
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_iDefaultFov
import rat.poison.game.netvars.NetVarOffsets.m_iFOV
import rat.poison.overlay.App
import rat.poison.settings.MENUTOG
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColor
import rat.poison.utils.notInGame
import java.lang.Math.toDegrees
import java.lang.Math.toRadians

fun drawFov() = App {
    if (!curSettings["ENABLE_ESP"].strToBool() || MENUTOG || notInGame)
        return@App

    if (!curSettings["DRAW_AIM_FOV"].strToBool() && !curSettings["DRAW_TRIGGER_FOV"].strToBool())
        return@App

    if (curSettings["FOV_TYPE"].replace("\"", "") != "STATIC")
        return@App

    val defaultFov = csgoEXE.int(me + m_iDefaultFov)
    val iFov = csgoEXE.int(me + m_iFOV)
    val viewFov: Int

    viewFov = if (iFov == 0) {
        defaultFov
    } else {
        iFov
    }

    val wep = me.weapon()
    var prefix = ""

    val bFOV: Float
    var bINFOV = false

    val backtrackFOV: Float
    var backtrackENABLED = false

    var triggerRadius = -1F
    var backtrackRadius = -1F

    when {
        wep.pistol -> { prefix = "PISTOL_" }
        wep.rifle -> { prefix = "RIFLE_" }
        wep.shotgun -> { prefix = "SHOTGUN_" }
        wep.sniper -> { prefix = "SNIPER_" }
        wep.smg -> { prefix = "SMG_" }
    }

    if (wep.gun) { //Not 100% this applies to every 'gun'
        bFOV = curSettings[prefix + "TRIGGER_FOV"].toFloat()
        bINFOV = curSettings[prefix + "TRIGGER_INFOV"].strToBool()
        triggerRadius = calcFovRadius(viewFov, bFOV)

        backtrackFOV = curSettings["BACKTRACK_FOV"].toFloat()
        backtrackENABLED = curSettings[prefix + "TRIGGER_BACKTRACK"].strToBool()
        backtrackRadius = calcFovRadius(viewFov, backtrackFOV)
    }

    val aimRadius = calcFovRadius(viewFov, curSettings["AIM_FOV"].toFloat())

    val rccXo = curSettings["RCROSSHAIR_XOFFSET"].toFloat()
    val rccYo = curSettings["RCROSSHAIR_YOFFSET"].toFloat()
    val x = CSGO.gameWidth / 2 + rccXo
    val y = CSGO.gameHeight / 2 + rccYo

    shapeRenderer.apply {
        if (shapeRenderer.isDrawing) {
            end()
        }

        begin()

        if (curSettings["DRAW_AIM_FOV"].strToBool()) {
            val col = curSettings["DRAW_AIM_FOV_COLOR"].strToColor()
            setColor(col.red / 255F, col.green / 255F, col.blue / 255F, 1F)
            circle(x, y, clamp(aimRadius, 10F, 1000F))
        }

        if (curSettings["ENABLE_TRIGGER"].strToBool() && curSettings["DRAW_TRIGGER_FOV"].strToBool() && triggerRadius != -1F && bINFOV) {
            val col = curSettings["DRAW_TRIGGER_FOV_COLOR"].strToColor()
            setColor(col.red / 255F, col.green / 255F, col.blue / 255F, 1F)
            circle(x, y, clamp(triggerRadius, 10F, 1000F))
        }

        if (curSettings["ENABLE_BACKTRACK"].strToBool() && curSettings["DRAW_BACKTRACK_FOV"].strToBool() && backtrackRadius != -1F && backtrackENABLED) {
            val col = curSettings["DRAW_BACKTRACK_FOV_COLOR"].strToColor()
            setColor(col.red / 255F, col.green / 255F, col.blue / 255F, 1F)
            circle(x, y, clamp(backtrackRadius, 10F, 1000F))
        }

        color = Color(1F, 1F, 1F, 1F)
        end()
    }
}

fun calcFovRadius(viewFov: Int, aimFov: Float): Float {
    var calcFov = 2.0 * toDegrees(kotlin.math.atan((CSGO.gameWidth.toDouble()/CSGO.gameHeight.toDouble()) * 0.75 * kotlin.math.tan(toRadians(viewFov/2.0))))
    calcFov = kotlin.math.tan(toRadians(aimFov/2.0)) / kotlin.math.tan(toRadians(calcFov/2.0)) * (CSGO.gameWidth.toDouble())

    return calcFov.toFloat()
}