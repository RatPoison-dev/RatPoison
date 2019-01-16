package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import rat.poison.App
import rat.poison.game.*
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.entity.*
import rat.poison.settings.*
import rat.poison.utils.Vector

private var lastpunch = Vector(0.0, 0.0, 0.0)

internal fun rcrosshair() = App { //Currently not completely centered
    if (!ENABLE_RECOIL_CROSSHAIR || MENUTOG) return@App

    val x = ((gameWidth/2F+3F) - ((gameWidth/90F)*lastpunch.y.toFloat()))
    val y = ((gameHeight/2F-1F) - ((gameHeight/90F)*lastpunch.x.toFloat()))

    lastpunch = me.punch() //Punch is noticeable ahead (no traceray, isn't actually ahead), updating it one behind to make it easier to use and more reliable than before //remove this later

    shapeRenderer.apply {
        begin()
        this.color = Color.YELLOW
        rect(x-RCROSSHAIR_LENGTH/2F, y, RCROSSHAIR_LENGTH.toFloat(), RCROSSHAIR_WIDTH.toFloat())
        rect(x, y-RCROSSHAIR_LENGTH/2F, RCROSSHAIR_WIDTH.toFloat(), RCROSSHAIR_LENGTH.toFloat())
        end()
    }
}