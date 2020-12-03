package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.onGround
import rat.poison.game.entity.velocity
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceBackward
import rat.poison.game.offsets.ClientOffsets.dwForceForward
import rat.poison.game.offsets.ClientOffsets.dwForceLeft
import rat.poison.game.offsets.ClientOffsets.dwForceRight
import rat.poison.scripts.aim.meDead
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import java.awt.event.KeyEvent.*
import kotlin.math.cos
import kotlin.math.sin

internal fun fastStop() = every(4, inGameCheck = true) {
    if (!curSettings["FAST_STOP"].strToBool() || meDead) return@every

    updateCursorEnable()
    if (cursorEnable) return@every

    val vel = me.velocity()
    val yaw = clientState.angle().y

    //Velocity relative to player direction
    val x = (vel.x * cos(yaw / 180 * Math.PI) + vel.y * sin(yaw / 180 * Math.PI))
    val y = (vel.y * cos(yaw / 180 * Math.PI) - vel.x * sin(yaw / 180 * Math.PI))

    if (!keyPressed(VK_SPACE) && me.onGround()) {
        if (x != 0.0 && y != 0.0) {
            if (!keyPressed(VK_W) && !keyPressed(VK_S)) {
                if (x > 30) {
                    clientDLL[dwForceBackward] = 6
                    //robot.keyRelease(VK_S)
                } else if (x < -30) {
                    clientDLL[dwForceForward] = 6
                    //robot.keyRelease(VK_W)
                }
            }

            if (!keyPressed(VK_A) && !keyPressed(VK_D)) {
                if (y > 30) {
                    clientDLL[dwForceRight] = 6
                    //robot.keyRelease(VK_D)
                } else if (y < -30) {
                    clientDLL[dwForceLeft] = 6
                    //robot.keyRelease(VK_A)
                }
            }
        }
    }
}