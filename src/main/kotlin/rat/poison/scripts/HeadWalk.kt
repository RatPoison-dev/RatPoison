package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.absPosition
import rat.poison.game.entity.onGround
import rat.poison.game.forEntities
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.robot
import rat.poison.scripts.aim.meDead
import rat.poison.utils.*
import rat.poison.utils.common.Vector
import java.awt.event.KeyEvent
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

private var onEnt = 0L

//Currently just a poc
////Only moves towards the center of the player
//////Keeps up with crouching just fine, but not normal running usually
////////Doesn't predict, isn't accurate

private var mePos = Vector()
private var onEntPos = Vector()

private val positionVector = Vector()
private val meAng = Vector()
internal fun headWalk() = every(2, inGameCheck = true) {
    if (!curSettings.bool["HEAD_WALK"] || meDead) return@every

    if (!keyPressed(KeyEvent.VK_W) && !keyPressed(KeyEvent.VK_A) && !keyPressed(KeyEvent.VK_S) && !keyPressed(KeyEvent.VK_D)) {
        val mePos = me.absPosition(mePos)
        if (onPlayerHead()) {
            updateCursorEnable()
            if (cursorEnable) return@every

            val pos = Vector(mePos.x - onEntPos.x, mePos.y - onEntPos.y, mePos.z - onEntPos.z)
            val yaw = clientState.angle(meAng).y
            val calcYaw = yaw/180*Math.PI

            //Is this even needed?
            val distX = (pos.x * cos(calcYaw) + pos.y * sin(calcYaw)).toInt()
            val distY = (pos.y * cos(calcYaw) - pos.x * sin(calcYaw)).toInt()

            //Gotta be a simpler way
            var wRelease = false
            var aRelease = false
            var sRelease = false
            var dRelease = false
            when {
                distX < 2 -> {
                    robot.keyPress(KeyEvent.VK_W)
                    wRelease = true
                }
                distX > 2 -> {
                    robot.keyPress(KeyEvent.VK_S)
                    sRelease = true
                }
            }

            when {
                distY < 2 -> {
                    robot.keyPress(KeyEvent.VK_A)
                    aRelease = true
                }
                distY > 2 -> {
                    robot.keyPress(KeyEvent.VK_D)
                    dRelease = true
                }
            }
            //Sleep once instead of up to twice
            Thread.sleep(1)
            if (wRelease) {
                robot.keyRelease(KeyEvent.VK_W)
            }
            if (aRelease) {
                robot.keyRelease(KeyEvent.VK_A)
            }
            if (sRelease) {
                robot.keyRelease(KeyEvent.VK_S)
            }
            if (dRelease) {
                robot.keyRelease(KeyEvent.VK_D)
            }
        }
    }
}

private const val id = "headwalk"
internal fun onPlayerHead() : Boolean {
    var entPos : Angle
    onEnt = 0L

    forEntities(EntityType.CCSPlayer, identifier = id) {
        val entity = it.entity
        if (entity == me || !entity.onGround()) return@forEntities

        entPos = entity.absPosition(positionVector)

        val xDist = abs(mePos.x - entPos.x)
        val yDist = abs(mePos.y - entPos.y)
        val zDif = mePos.z - entPos.z

        if (xDist <= 30 && yDist <= 30 && zDif in 50.0..75.0) {
            onEnt = entity
            onEntPos = entPos
        }

        if (onEnt != 0L) {
            return@forEntities
        }
    }

    return when (onEnt) {
        0L -> false
        else -> true
    }
}