package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.*
import rat.poison.game.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.entity.absPosition
import rat.poison.game.entity.dead
import rat.poison.game.entity.onGround
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.utils.Angle
import rat.poison.utils.Vector
import rat.poison.utils.every
import java.awt.event.KeyEvent
import kotlin.math.*

private var onEnt = 0L

//Currently just a poc
////Only moves towards the center of the player
//////Keeps up with crouching just fine, but not normal running
////////Doesn't predict, isn't accurate

var mePos = Vector()
var onEntPos = Vector()

internal fun headWalk() = every(1) {
    if (!curSettings["HEAD_WALK"].strToBool()) return@every

    if (me.dead()) return@every

    if (!keyPressed(KeyEvent.VK_W) && !keyPressed(KeyEvent.VK_A) && !keyPressed(KeyEvent.VK_S) && !keyPressed(KeyEvent.VK_D)) {
        mePos = me.absPosition()
        if (onPlayerHead()) {
            updateCursorEnable()
            if (cursorEnable) return@every

            val pos = Vector(mePos.x - onEntPos.x, mePos.y - onEntPos.y, mePos.z - onEntPos.z)
            val yaw = clientState.angle().y
            val calcYaw = yaw/180*Math.PI

            //Is this even needed?
            val distX = (pos.x * cos(calcYaw) + pos.y * sin(calcYaw)).toInt()
            val distY = (pos.y * cos(calcYaw) - pos.x * sin(calcYaw)).toInt()

            println("we do be head walkin, pos: $pos -- distX: $distX -- distY: $distY ")

            when {
                distX < 2 -> {
                    robot.keyPress(KeyEvent.VK_W)
                    Thread.sleep(1)
                    robot.keyRelease(KeyEvent.VK_W)
                }
                distX > 2 -> {
                    robot.keyPress(KeyEvent.VK_S)
                    Thread.sleep(1)
                    robot.keyRelease(KeyEvent.VK_S)
                }
            }

            when {
                distY < 2 -> {
                    robot.keyPress(KeyEvent.VK_A)
                    Thread.sleep(1)
                    robot.keyRelease(KeyEvent.VK_A)
                }
                distY > 2 -> {
                    robot.keyPress(KeyEvent.VK_D)
                    Thread.sleep(1)
                    robot.keyRelease(KeyEvent.VK_D)
                }
            }
        }
    }
}

internal fun onPlayerHead() : Boolean {
    var entPos : Angle
    onEnt = 0L

    forEntities(ccsPlayer) {
        val entity = it.entity
        if (entity == me || !entity.onGround()) return@forEntities false

        entPos = entity.absPosition()

        val xDist = abs(mePos.x - entPos.x)
        val yDist = abs(mePos.y - entPos.y)
        val zDif = mePos.z - entPos.z

        if (xDist <= 30 && yDist <= 30 && zDif in 50.0..75.0) {
            onEnt = entity
            onEntPos = entPos
        }

        if (onEnt != 0L) {
            return@forEntities false
        }

        false
    }

    return when (onEnt) {
        0L -> false
        else -> true
    }
}