package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.jire.kna.int
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.entity.isScoped
import rat.poison.game.entity.punch
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.overlay.App
import rat.poison.scripts.aim.meCurWep
import rat.poison.settings.MENUTOG
import rat.poison.ui.uiPanels.mainTabbedPane
import rat.poison.ui.uiPanels.rcsTab
import rat.poison.utils.inGame
import java.lang.Math.toRadians
import kotlin.math.atan
import kotlin.math.floor
import kotlin.math.tan

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

    if (eRC && !(eSC && meCurWep.sniper)) {
        val punch = me.punch()

        //Center
        x = (gameWidth / 2) - tan(toRadians(punch.y.toDouble())).toFloat() * rccFov2 + rccXo
        y = (gameHeight / 2) - tan(toRadians(punch.x.toDouble())).toFloat() * rccFov2 + rccYo
    
        punch.release()
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
            val col = curSettings.color["RCROSSHAIR_COLOR"]
            color = Color(col.red / 255F, col.green / 255F, col.blue / 255F, curSettings.float["RCROSSHAIR_ALPHA"])

            val hasSniper = meCurWep.scope

            if ((eSC && hasSniper && !me.isScoped()) || !eSC || (eRC && !hasSniper)) {
                if (curSettings["RCROSSHAIR_TYPE"].toUpperCase() == "CROSSHAIR") {
                    set(ShapeRenderer.ShapeType.Filled)
                    //Horizontal
                    rect(x - lO, y - wO, cL, cW)
                    //Vertical
                    rect(x - wO, y - lO, cW, cL)
                    set(ShapeRenderer.ShapeType.Line)
                } else {
                    circle(x, y, curSettings.float["RCROSSHAIR_RADIUS"])
                }
            }

            end()
        }
    }
}