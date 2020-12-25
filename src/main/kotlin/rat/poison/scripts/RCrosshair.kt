package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.clamp
import org.jire.kna.int
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.entity.isScoped
import rat.poison.game.entity.punch
import rat.poison.game.entity.shotsFired
import rat.poison.game.entity.velocity
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.overlay.App
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meCurWepEnt
import rat.poison.settings.MENUTOG
import rat.poison.ui.uiPanels.mainTabbedPane
import rat.poison.ui.uiPanels.rcsTab
import rat.poison.utils.inGame
import java.lang.Math.toRadians
import kotlin.math.*

internal fun rcrosshair() = App {
    if (!curSettings.bool["ENABLE_ESP"] || !inGame) return@App

    val eRC = curSettings.bool["ENABLE_RECOIL_CROSSHAIR"]
    val eSC = !curSettings.bool["ENABLE_SNIPER_CROSSHAIR"]

    if (!eRC) return@App

    val x: Float
    val y: Float

    //Crosshair Length/Width
    val cL = curSettings.float["RCROSSHAIR_LENGTH"]
    val cW = curSettings.float["RCROSSHAIR_WIDTH"]

    //Crosshair X/Y offset
    val rccXo = curSettings.float["RCROSSHAIR_XOFFSET"]
    val rccYo = curSettings.float["RCROSSHAIR_YOFFSET"]

    //Crosshair FOV modifier
    val curFov = csgoEXE.int(me + NetVarOffsets.m_iDefaultFov)
    val rccFov1 = atan((gameWidth.toFloat()/gameHeight.toFloat()) * 0.75 * tan(toRadians(curFov/2.0)))
    val rccFov2 = (gameWidth/2) / tan(rccFov1).toFloat()

    //Center based on Length/Width
    val wO = floor(cW / 2.0).toFloat()
    val lO = floor(cL / 2.0).toFloat()
    var gap = curSettings.float["RCROSSHAIR_GAP"]
    val outline = curSettings.float["RCROSSHAIR_OUTLINE_WIDTH"]
    val outlineEnabled = curSettings.bool["RCROSSHAIR_OUTLINE"]

    if (curSettings.bool["RCROSSHAIR_DYNAMIC"]) { //str8 up
        val vAbsVelocity = me.velocity()
        val flVelocity = sqrt(vAbsVelocity.x.pow(2F) + vAbsVelocity.y.pow(2F) + vAbsVelocity.z.pow(2F))

        val realInaccuracyFire: Float
        val realSpread: Float
        val realInaccuracyMove: Float

        if (csgoEXE.int(meCurWepEnt + NetVarOffsets.m_weaponMode) > 0) { //Silencer
            realInaccuracyFire = wepData.inaccuracyFireAlt
            realSpread = wepData.spreadAlt
            realInaccuracyMove = wepData.inaccuracyMoveAlt
        } else {
            realInaccuracyFire = wepData.inaccuracyFire
            realSpread = wepData.spread
            realInaccuracyMove = wepData.inaccuracyMove
        }

        var radius = realInaccuracyMove * (flVelocity / wepData.maxPlayerSpeed)
        radius += clamp(me.shotsFired() * realInaccuracyFire, 0f, realSpread * 100)
        //yo momma's so fat that objects 5 meters away accelerate at 1 m/s^2 toward her. What is yo momma's mass if G = 6.67x10^-11Nm^2/kg^2?
        radius = clamp(radius, 0F, curSettings.float["RCROSSHAIR_MAX_SPREAD"])
        if (!radius.isNaN()) gap += radius
    }

    if (eRC && !(eSC && meCurWep.sniper)) {
        val punch = me.punch()

        //Center
        x = (gameWidth / 2) - tan(toRadians(punch.y.toDouble())).toFloat() * rccFov2 + rccXo
        y = (gameHeight / 2) - tan(toRadians(punch.x.toDouble())).toFloat() * rccFov2 + rccYo
    } else {
        //Center
        x = gameWidth / 2 + rccXo
        y = gameHeight / 2 + rccYo
    }

    if (!MENUTOG || ((eRC || eSC) && mainTabbedPane.activeTab == rcsTab)) {
        shapeRenderer.apply {
            if (shapeRenderer.isDrawing) {
                end()
            }

            begin()
            val outlineColor = curSettings.colorGDX["RCROSSHAIR_OUTLINE_COLOR"]
            val realColor = curSettings.colorGDX["RCROSSHAIR_COLOR"]
            color = realColor

            val hasSniper = meCurWep.scope

            if ((eSC && hasSniper && !me.isScoped()) || !eSC || (eRC && !hasSniper)) {
                if (curSettings["RCROSSHAIR_TYPE"].toUpperCase() == "CROSSHAIR") {
                    set(ShapeRenderer.ShapeType.Filled)
                    //Horizontal
                    if (curSettings.bool["RCROSSHAIR_LEFT"]) {
                        rect(x - lO - gap, y - wO, lO, cW)
                        if (outlineEnabled) {
                            color =  outlineColor
                            rect(x - lO - gap - outline, y - wO, outline, cW) //outline left
                            rect(x - lO - gap - outline, y + wO, lO + outline*2, outline) //outline top
                            rect(x - lO - gap - outline, y - wO - outline, lO + outline, outline) //outline bottom
                            rect(x - gap, y - wO - outline, outline, cW + outline) //outline right
                            color = realColor
                        }
                    }

                    if (curSettings.bool["RCROSSHAIR_RIGHT"]) {
                        rect(x + gap, y - wO, lO, cW)
                        if (outlineEnabled) {
                            color =  outlineColor
                            rect(x + gap - outline, y - wO, outline, cW) //outline left
                            rect(x + gap - outline, y + wO, lO + outline*2, outline) //outline top
                            rect(x + gap - outline, y - wO - outline, lO + outline*2, outline) //outline bottom
                            rect(x + gap + lO, y - wO, outline, cW) //outline right
                            color = realColor
                        }
                    }

                    //Vertical
                    if (curSettings.bool["RCROSSHAIR_TOP"]) {
                        rect(x - wO, y + gap, cW, lO)
                        if (outlineEnabled) {
                            color = outlineColor
                            rect(x - wO - outline, y + gap, outline, lO) //outline left
                            rect(x + wO, y + gap, outline, lO) //outline right
                            rect(x - wO, y + gap + lO - outline, cW, outline) //outline top
                            rect(x - wO, y + gap, cW, outline) //outline bottom
                            color = realColor
                        }
                    }
                    if (curSettings.bool["RCROSSHAIR_BOTTOM"]) {
                        rect(x - wO, y - lO - gap, cW, lO)
                        if (outlineEnabled) {
                            color = outlineColor
                            rect(x - wO - outline, y - lO - gap, outline, lO) //outline left
                            rect(x + wO, y - lO - gap, outline, lO) //outline right
                            rect(x - wO, y - lO - gap, cW, outline) //outline top
                            rect(x - wO, y - gap - outline, cW, outline) //outline bottom
                            color = realColor
                        }
                    }

                    if (curSettings.bool["RCROSSHAIR_CENTER_DOT"]) {
                        if (outlineEnabled) {
                            color = outlineColor
                            circle(x, y, curSettings.float["RCROSSHAIR_DOT_RADIUS"]+outline)
                            color = realColor
                        }
                        circle(x, y, curSettings.float["RCROSSHAIR_DOT_RADIUS"])
                    }
                    set(ShapeRenderer.ShapeType.Line)
                } else {
                    circle(x, y, curSettings.float["RCROSSHAIR_RADIUS"])
                }
            }

            end()
        }
    }
}