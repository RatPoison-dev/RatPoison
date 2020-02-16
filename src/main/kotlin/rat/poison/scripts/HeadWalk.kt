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
import kotlin.math.*

private var onEnt = 0L

//Currently just a poc
////Only moves towards the center of the player
//////Keeps up with crouching just fine, but not normal running
////////Doesn't predict, isn't accurate

//Switch velocity to difference in current pos vs prev

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

            //Is this even needed?
            //val tmpX = (pos.x * cos(yaw / 180 * Math.PI)).toInt()
            //val tmpY = (pos.y * cos(yaw / 180 * Math.PI)).toInt()
            //val distX = tmpX + tmpY
            //val distY = tmpY - tmpX
            val distX = (pos.x * cos(yaw / 180 * Math.PI) + pos.y * sin(yaw / 180 * Math.PI)).toInt()
            val distY = (pos.y * cos(yaw / 180 * Math.PI) - pos.x * sin(yaw / 180 * Math.PI)).toInt()

            println (distX)
            println(distY)

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
    var entPos : Angle
    onEnt = 0L

    forEntities(ccsPlayer) {
        val entity = it.entity
        if (entity == me) return@forEntities false

        entPos = entity.absPosition()

        val xDist = abs(mePos.x - entPos.x)
        val yDist = abs(mePos.y - entPos.y)
        val zDist = abs(mePos.z - entPos.z)

        //Issue here
        if (xDist <= 30 && yDist <= 30 && zDist <= 30) { //Absolute wont give negative, if within 30
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