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
import kotlin.math.floor

private var lastpunch = Vector(0.0, 0.0, 0.0)

//Shoot me, remove constants?
internal fun rcrosshair() = App { //Currently not completely centered
    if (!curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()) return@App

    //Crosshair
    val cL = curSettings["RCROSSHAIR_LENGTH"]!!.toFloat()
    val cW = curSettings["RCROSSHAIR_WIDTH"]!!.toFloat()

    //Need offsets because draw is bottom left
    val wO = floor(cW/2.0).toFloat()
    val lO = floor(cL/2.0).toFloat()

    //General crosshair x/y offset
    val rccXo = curSettings["RCROSSHAIR_XOFFSET"]!!.toFloat()
    val rccYo = curSettings["RCROSSHAIR_YOFFSET"]!!.toFloat()

    //Horizontal Bar
    val x = gameWidth/2 - ((gameWidth/95F)*lastpunch.y).toFloat() + rccXo
    val y = gameHeight/2 - ((gameHeight/95F)*lastpunch.x).toFloat() + rccYo

    lastpunch = me.punch()

    shapeRenderer.apply {
        begin()
        set(ShapeRenderer.ShapeType.Filled)
        val col = curSettings["RCROSSHAIR_COLOR"]!!.strToColor()
        color = Color(col.red/255F, col.green/255F, col.blue/255F, curSettings["RCROSSHAIR_ALPHA"]!!.toFloat())

        //Horizontal
        rect(x - lO, y - wO, cL, cW)

        //Vertical
        rect(x - wO, y - lO, cW, cL)

        set(ShapeRenderer.ShapeType.Line)
        end()
    }
}