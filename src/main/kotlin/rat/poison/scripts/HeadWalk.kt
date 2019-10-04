package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.*
import rat.poison.game.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.entity.absPosition
import rat.poison.game.entity.dead
import rat.poison.game.entity.velocity
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.ui.miscTab
import rat.poison.utils.Angle
import rat.poison.utils.ObservableBoolean
import rat.poison.utils.Vector
import rat.poison.utils.every
import java.awt.Robot
import java.awt.event.KeyEvent
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

private var onEnt = 0L
var headWalkToggle = ObservableBoolean({onPlayerHead()})

//Currently just a poc
////Only moves towards the center of the player
//////Keeps up with crouching just fine, but not normal running
////////Doesn't predict, isn't accurate

//Switch velocity to difference in current pos vs prev

internal fun headWalk() = every(1) {
    if (!curSettings["HEAD_WALK"].strToBool()) return@every

    if (me.dead()) return@every

    if (!keyPressed(KeyEvent.VK_W) && !keyPressed(KeyEvent.VK_A) && !keyPressed(KeyEvent.VK_S) && !keyPressed(KeyEvent.VK_D)) {
        headWalkToggle.update()
        if (headWalkToggle.value) {
            updateCursorEnable()
            if (cursorEnable) return@every

            val mePos = me.absPosition()
            val entPos = onEnt.absPosition()
            val pos = Vector(mePos.x - entPos.x, mePos.y - entPos.y, mePos.z - entPos.z)
            val yaw = clientState.angle().y

            val distX = (pos.x * cos(yaw / 180 * Math.PI) + pos.y * sin(yaw / 180 * Math.PI)).toInt()
            val distY = (pos.y * cos(yaw / 180 * Math.PI) - pos.x * sin(yaw / 180 * Math.PI)).toInt()

            when {
                distX < 2 -> {
                    robot.keyPress(KeyEvent.VK_W)
                    robot.keyRelease(KeyEvent.VK_W)
                }
                distX > 2 -> {
                    robot.keyPress(KeyEvent.VK_S)
                    robot.keyRelease(KeyEvent.VK_S)
                }
            }

            when {
                distY < 2 -> {
                    robot.keyPress(KeyEvent.VK_A)
                    robot.keyRelease(KeyEvent.VK_A)
                }
                distY > 2 -> {
                    robot.keyPress(KeyEvent.VK_D)
                    robot.keyRelease(KeyEvent.VK_D)
                }
            }
        }
    }
}

internal fun onPlayerHead() : Boolean {
    val mePos = me.absPosition()
    var entPos : Angle
    onEnt = 0L

    forEntities(ccsPlayer) {
        val entity = it.entity
        if (entity == me) return@forEntities false

        entPos = entity.absPosition()

        val xDif = mePos.x - entPos.x
        val yDif = mePos.y - entPos.y
        val zDif = mePos.z - entPos.z

        if (xDif in -30.0..30.0 && yDif in -30.0..30.0 && zDif in 50.0..75.0) {
            onEnt = entity
        }

        false
    }

    return when (onEnt) {
        0L -> false
        else -> true
    }
}