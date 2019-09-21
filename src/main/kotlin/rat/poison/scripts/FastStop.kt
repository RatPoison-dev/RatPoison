package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.dead
import rat.poison.game.entity.onGround
import rat.poison.game.entity.velocity
import rat.poison.game.offsets.ClientOffsets
import rat.poison.strToBool
import rat.poison.utils.every
import rat.poison.utils.notInGame
import java.awt.Robot
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import kotlin.math.cos
import kotlin.math.sin

private val robot = Robot().apply { this.autoDelay = 0 }

internal fun fastStop() = every(4) {
    if (!curSettings["FAST_STOP"].strToBool() || notInGame) return@every

    if (!me.dead()) {
        val vel = me.velocity()
        val yaw = clientState.angle().y

        //Velocity relative to player direction
        val x = (vel.x * cos(yaw / 180 * Math.PI) + vel.y * sin(yaw / 180 * Math.PI))
        val y = (vel.y * cos(yaw / 180 * Math.PI) - vel.x * sin(yaw / 180 * Math.PI))

        if (!keyPressed(VK_SPACE) && me.onGround()) {
            if (!keyPressed(VK_W) && !keyPressed(VK_S)) {
                if (x > 30) {
                    robot.keyPress(VK_S)
                    robot.keyRelease(VK_S)
                } else if (x < -30) {
                    robot.keyPress(VK_W)
                    robot.keyRelease(VK_W)
                }
            }

            if (!keyPressed(VK_A) && !keyPressed(VK_D)) {
                if (y > 30) {
                    robot.keyPress(VK_D)
                    robot.keyRelease(VK_D)
                } else if (y < -30) {
                    robot.keyPress(VK_A)
                    robot.keyRelease(VK_A)
                }
            }
        }
    }
}