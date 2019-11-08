package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.entity.isScoped
import rat.poison.game.entity.punch
import rat.poison.game.entity.weapon
import rat.poison.game.me
import rat.poison.settings.MENUTOG
import rat.poison.strToBool
import rat.poison.strToColor
import rat.poison.ui.mainTabbedPane
import rat.poison.ui.rcsTab
import kotlin.math.floor

internal fun rcrosshair() = App {
    val eRC = curSettings["ENABLE_RECOIL_CROSSHAIR"].strToBool()
    val eSC = curSettings["ENABLE_SNIPER_CROSSHAIR"].strToBool()

    if (!eRC && !eSC) return@App

    val x: Float
    val y: Float

    //Crosshair Length/Width
    val cL = curSettings["RCROSSHAIR_LENGTH"].toFloat()
    val cW = curSettings["RCROSSHAIR_WIDTH"].toFloat()

    //Crosshair X/Y offset
    val rccXo = curSettings["RCROSSHAIR_XOFFSET"].toFloat()
    val rccYo = curSettings["RCROSSHAIR_YOFFSET"].toFloat()

    //Center based on Length/Width
    val wO = floor(cW / 2.0).toFloat()
    val lO = floor(cL / 2.0).toFloat()

    if (eRC && !(eSC && me.weapon().sniper)) {
        val punch = me.punch()

        //Center
        x = gameWidth / 2 - ((gameWidth / 95F) * punch.y).toFloat() + rccXo
        y = gameHeight / 2 - ((gameHeight / 95F) * punch.x).toFloat() + rccYo
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
            set(ShapeRenderer.ShapeType.Filled)
            val col = curSettings["RCROSSHAIR_COLOR"].strToColor()
            color = Color(col.red / 255F, col.green / 255F, col.blue / 255F, curSettings["RCROSSHAIR_ALPHA"].toFloat())

            val hasSniper = me.weapon().scope

            if ((eSC && hasSniper && !me.isScoped()) || !eSC || (eRC && !hasSniper)) {
                //Horizontal
                rect(x - lO, y - wO, cL, cW)
                //Vertical
                rect(x - wO, y - lO, cW, cL)
            }

            set(ShapeRenderer.ShapeType.Line)
            end()
        }
    }
}