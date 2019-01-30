package rat.poison.scripts

import com.badlogic.gdx.graphics.Color as Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.App
import rat.poison.game.*
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.entity.*
import rat.poison.settings.*
import rat.poison.utils.Vector

private var lastpunch = Vector(0.0, 0.0, 0.0)

internal fun rcrosshair() = App { //Currently not completely centered
    if (!ENABLE_RECOIL_CROSSHAIR || MENUTOG || !ENABLE_ESP) return@App

    //Need offsets because draw is bottom left
    val originOffset = 2F
    val widthOffset = Math.floor(RCROSSHAIR_WIDTH/2.0).toFloat()

    val x = gameWidth/2 - ((gameWidth/95F)*lastpunch.y).toFloat()
    val y = gameHeight/2 - ((gameHeight/95F)*lastpunch.x).toFloat()

    lastpunch = me.punch()

    shapeRenderer.apply {
        begin()
        set(ShapeRenderer.ShapeType.Filled)
        color = Color(RCROSSHAIR_COLOR.red/255F, RCROSSHAIR_COLOR.green/255F, RCROSSHAIR_COLOR.blue/255F, RCROSSHAIR_ALPHA.toFloat())
        rect(x+3-RCROSSHAIR_LENGTH/2F, y-originOffset-widthOffset, RCROSSHAIR_LENGTH.toFloat(), RCROSSHAIR_WIDTH.toFloat())
        rect(x+originOffset-widthOffset, y-1-RCROSSHAIR_LENGTH/2F, RCROSSHAIR_WIDTH.toFloat(), RCROSSHAIR_LENGTH.toFloat())
        set(ShapeRenderer.ShapeType.Line)
        end()
    }
}