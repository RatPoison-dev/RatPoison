package rat.poison.scripts

import com.badlogic.gdx.graphics.Color
import rat.poison.game.*
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.entity.*
import rat.poison.overlay.RatPoisonOverlay
import rat.poison.settings.*
import rat.poison.utils.Vector

private var lastpunch = Vector(0.0, 0.0, 0.0)

internal fun rcrosshair() = RatPoisonOverlay {
    if (!ENABLE_RECOIL_CROSSHAIR) return@RatPoisonOverlay

    val x = ((gameWidth/2).toFloat() - ((gameWidth/90).toFloat()*lastpunch.y.toFloat()))
    val y = ((gameHeight/2).toFloat() + ((gameHeight/90).toFloat()*lastpunch.x.toFloat()))

    lastpunch = me.punch() //Punch seems to be one shot ahead so update it for the next shot

    //Move to punch and vector eyes ?

    shapeRenderer.apply sR@ {
        begin()
        this@sR.color = Color.YELLOW
        rect(x-RCROSSHAIR_LENGTH/2, y, RCROSSHAIR_LENGTH.toFloat(), RCROSSHAIR_WIDTH.toFloat())
        rect(x, y-RCROSSHAIR_LENGTH/2, RCROSSHAIR_WIDTH.toFloat(), RCROSSHAIR_LENGTH.toFloat())
        end()
    }
}