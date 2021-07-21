package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
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
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.strToColor
import rat.poison.utils.inGame
import java.lang.Math.toRadians
import kotlin.math.*

internal fun rcrosshair() = App {
    if (!curSettings["ENABLE_ESP"].strToBool() || !inGame) return@App

    val eRC = curSettings["ENABLE_RECOIL_CROSSHAIR"].strToBool()
    val eSC = !curSettings["ENABLE_SNIPER_CROSSHAIR"].strToBool()

    if (!eRC) return@App

    val x: Float
    val y: Float

    //Crosshair Length/Width
    val cL = curSettings["RCROSSHAIR_LENGTH"].toFloat()
    val cW = curSettings["RCROSSHAIR_WIDTH"].toFloat()

    //Crosshair X/Y offset
    val rccXo = curSettings["RCROSSHAIR_XOFFSET"].toFloat()
    val rccYo = curSettings["RCROSSHAIR_YOFFSET"].toFloat()

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
            val col = curSettings["RCROSSHAIR_COLOR"].strToColor()
            color = Color(col.red / 255F, col.green / 255F, col.blue / 255F, curSettings["RCROSSHAIR_ALPHA"].toFloat())

            val hasSniper = meCurWep.scope

            if ((eSC && hasSniper && !me.isScoped()) || !eSC || (eRC && !hasSniper)) {
                if (curSettings["RCROSSHAIR_TYPE"].uppercase() == "CROSSHAIR") {
                    set(ShapeRenderer.ShapeType.Filled)
                    //Horizontal
                    rect(x - lO, y - wO, cL, cW)
                    //Vertical
                    rect(x - wO, y - lO, cW, cL)
                } else {
                    circle(x, y, curSettings["RCROSSHAIR_RADIUS"].toFloat())
                }
            }

            set(ShapeRenderer.ShapeType.Line)
            end()
        }
    }
}