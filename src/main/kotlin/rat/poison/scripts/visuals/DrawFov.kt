package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils.clamp
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.EntityType
import rat.poison.game.netvars.NetVarOffsets.m_iDefaultFov
import rat.poison.game.netvars.NetVarOffsets.m_iFOV
import rat.poison.overlay.App
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.userCmd.meDead
import rat.poison.settings.AIM_FOV
import rat.poison.settings.MENUTOG
import rat.poison.settings.TRIGGER_FOV
import rat.poison.settings.TRIGGER_USE_FOV
import rat.poison.utils.common.inGame
import java.lang.Math.toDegrees
import java.lang.Math.toRadians

private const val id = "drawfov"
private val forEnts = arrayOf(EntityType.CCSPlayer)

fun drawFov() = App {
//    forEntities(forEnts, identifier = id) {
//        if (it.entity == me) return@forEntities
//
//        val position = me.position()
//        val curAngle = clientState.angle()
//
//        val vec = Vector()
//        val ePos: Angle = it.entity.bones(8, vec)
//
//        val distance = position.distanceTo(ePos)
//
//        val calcAng = getCalculatedAngle(me, ePos)
//
//        val pitchDiff = abs(curAngle.x - calcAng.x)
//        var yawDiff = abs(curAngle.y - calcAng.y)
//
//        if (yawDiff > 180f) {
//            yawDiff = 360f - yawDiff
//        }
//
//        val fov = abs(sin(toRadians(yawDiff.toDouble())) * distance)
//        val delta = abs((sin(toRadians(pitchDiff.toDouble())) + sin(toRadians(yawDiff.toDouble()))) * distance).roundToInt()
//
//        val entPos = Vector()
//
//        if (worldToScreen(ePos, entPos)) {
//            if (!sb.isDrawing) {
//                sb.begin()
//            }
//
//            //draw details first
//            val detailTextColor = curSettings.colorGDX["BOX_DETAILS_TEXT_COLOR"]
//            textRenderer.color = detailTextColor
//
//            textRenderer.draw(sb, delta.toString(), entPos.x, entPos.y, 1F, Align.right, false)
//
//            sb.end()
//        }
//    }

    if (!curSettings.bool["ENABLE_ESP"] || MENUTOG || !inGame || meDead)
        return@App

    if (!curSettings.bool["DRAW_AIM_FOV"] && !curSettings.bool["DRAW_TRIGGER_FOV"])
        return@App

    if (curSettings["FOV_TYPE"].replace("\"", "") != "STATIC")
        return@App

    val defaultFov = csgoEXE.int(me + m_iDefaultFov)
    val iFov = csgoEXE.int(me + m_iFOV)

    val viewFov: Int = if (iFov == 0) {
        defaultFov
    } else {
        iFov
    }

    val bFOV: Float
    var bINFOV = false

    var triggerRadius = -1F

    if (meCurWep.gun) {
        bFOV = TRIGGER_FOV
        bINFOV = TRIGGER_USE_FOV
        triggerRadius = calcFovRadius(viewFov, bFOV)
    }

    val aimRadius = calcFovRadius(viewFov, AIM_FOV)

    val rccXo = curSettings.float["RCROSSHAIR_XOFFSET"]
    val rccYo = curSettings.float["RCROSSHAIR_YOFFSET"]
    val x = CSGO.gameWidth / 2 + rccXo
    val y = CSGO.gameHeight / 2 + rccYo

    shapeRenderer.apply {
        if (shapeRenderer.isDrawing) {
            end()
        }

        begin()

        if (curSettings.bool["DRAW_AIM_FOV"]) {
            val col = curSettings.color["DRAW_AIM_FOV_COLOR"]
            setColor(col.red / 255F, col.green / 255F, col.blue / 255F, 1F)
            circle(x, y, clamp(aimRadius, 10F, 1000F))
        }

        if (curSettings.bool["ENABLE_TRIGGER"] && curSettings.bool["DRAW_TRIGGER_FOV"] && triggerRadius != -1F && bINFOV) {
            val col = curSettings.color["DRAW_TRIGGER_FOV_COLOR"]
            setColor(col.red / 255F, col.green / 255F, col.blue / 255F, 1F)
            circle(x, y, clamp(triggerRadius, 10F, 1000F))
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