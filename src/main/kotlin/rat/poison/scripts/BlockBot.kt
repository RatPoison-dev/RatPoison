package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.robot
import rat.poison.utils.Angle
import rat.poison.utils.distanceTo
import rat.poison.utils.every
import rat.poison.utils.keyPressed
import java.awt.event.KeyEvent
import kotlin.math.atan2

fun yaw3(v: Angle): Double {
    return atan2(v.y, v.x) * 180 / Math.PI
}

fun sub3(a: Angle, b: Angle): Angle {
    return Angle(a.x - b.x, a.y - b.y, a.z - b.z)
}

private var pressingA = false
private var pressingD = false

private fun unPressA() {
    if (pressingA) {
        robot.keyRelease(KeyEvent.VK_A)
        pressingA = false
    }
}

private fun unPressD() {
    if (pressingD) {
        robot.keyRelease(KeyEvent.VK_D)
        pressingD = false
    }
}

fun unPress() {
    unPressA()
    unPressD()
}

fun blockBot() = every(2, inGameCheck = true) {
    if (!curSettings.bool["BLOCK_BOT"] || !keyPressed(curSettings.int["BLOCK_BOT_KEY"])) {
        unPress()
        return@every
    }

    val localAngles = me.eyeAngle()
    var myPosition = me.position()

    var closestDist = -1F
    var closestTarget = -1L
    val maxDist = curSettings.int["BLOCK_BOT_DISTANCE"]

    forEntities(EntityType.CCSPlayer) {
        val entity = it.entity
        if (entity == me || entity.dead() || entity.dead() || entity <= 0) return@forEntities

        var myDistance = myPosition.distanceTo(entity.position())
        if ((myDistance < closestDist || closestDist == -1F) && myDistance <= maxDist) {
            closestDist = myDistance
            closestTarget = entity
        }
    }

    if (closestTarget == -1L) return@every

    val vecForward = sub3(closestTarget.position(), myPosition)
    val otherYaw = yaw3(vecForward)
    //val targetVelocity = closestTarget.velocity()
    //val targetSpeed = sqrt(targetVelocity.x * targetVelocity.x + targetVelocity.y * targetVelocity.y)

    var diffYaw = otherYaw - localAngles.y


    if (diffYaw > 180) {
        diffYaw -= 360
    }
    else if (diffYaw < -180) {
        diffYaw += 360
    }

    when {
        diffYaw > 0.25 -> {
            unPressD()
            robot.keyPress(KeyEvent.VK_A)
            pressingA = true
        }
        diffYaw < -0.25 -> {
            unPressA()
            robot.keyPress(KeyEvent.VK_D)
            pressingD = true
        }
        else -> {
            unPress()
        }
    }
}