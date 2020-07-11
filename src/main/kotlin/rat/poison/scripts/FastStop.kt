package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.dead
import rat.poison.game.entity.onGround
import rat.poison.game.entity.velocity
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.robot
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.notInGame
import java.awt.event.KeyEvent.*
import kotlin.math.cos
import kotlin.math.sin

internal fun fastStop() = every(4) {
    if (!curSettings["FAST_STOP"].strToBool() || notInGame) return@every

    if (!me.dead()) {
        val vel = me.velocity()
        val yaw = clientState.angle().y

        //Velocity relative to player direction
        val x = (vel.x * cos(yaw / 180 * Math.PI) + vel.y * sin(yaw / 180 * Math.PI))
        val y = (vel.y * cos(yaw / 180 * Math.PI) - vel.x * sin(yaw / 180 * Math.PI))

        if (!keyPressed(VK_SPACE) && me.onGround()) {
            if (x != 0.0 && y != 0.0) {
                updateCursorEnable()
                if (cursorEnable) return@every

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
}