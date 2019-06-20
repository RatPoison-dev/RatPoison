package rat.poison.scripts

import com.badlogic.gdx.graphics.Color as Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.entity.*
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.strToColor
import rat.poison.utils.Vector

private var lastpunch = Vector(0.0, 0.0, 0.0)

internal fun rcrosshair() = App { //Currently not completely centered
    if (!curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool() || MENUTOG) return@App

    //Need offsets because draw is bottom left
    val originOffset = 2F
    val widthOffset = Math.floor(curSettings["RCROSSHAIR_WIDTH"]!!.toInt()/2.0).toFloat()

    val x = gameWidth/2 - ((gameWidth/95F)*lastpunch.y).toFloat()
    val y = gameHeight/2 - ((gameHeight/95F)*lastpunch.x).toFloat()

    lastpunch = me.punch()

    shapeRenderer.apply {
        begin()
        set(ShapeRenderer.ShapeType.Filled)
        val col = curSettings["RCROSSHAIR_COLOR"]!!.strToColor()
        color = Color(col.red/255F, col.green/255F, col.blue/255F, curSettings["RCROSSHAIR_ALPHA"]!!.toFloat())
        rect(x+3-curSettings["RCROSSHAIR_LENGTH"]!!.toInt()/2F, y-originOffset-widthOffset, curSettings["RCROSSHAIR_LENGTH"]!!.toFloat(), curSettings["RCROSSHAIR_WIDTH"]!!.toFloat())
        rect(x+originOffset-widthOffset, y-1-curSettings["RCROSSHAIR_LENGTH"]!!.toInt()/2F, curSettings["RCROSSHAIR_WIDTH"]!!.toFloat(), curSettings["RCROSSHAIR_LENGTH"]!!.toInt().toFloat())
        set(ShapeRenderer.ShapeType.Line)
        end()
    }
}