package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.dead
import rat.poison.game.entity.velocity
import rat.poison.game.offsets.ClientOffsets
import rat.poison.strToBool
import rat.poison.utils.every
import rat.poison.utils.notInGame
import java.awt.Robot
import java.awt.event.KeyEvent.*

private val robot = Robot().apply { this.autoDelay = 0 } //Make public, remove from knifebot

internal fun fastStop() = every(4) {
    if (!curSettings["FAST_STOP"]!!.strToBool() || notInGame) {
        return@every
    }

    if (!me.dead()) {
        val vel = me.velocity()
        val yaw = clientState.angle().y

        //Velocity relative to player direction
        val x = (vel.x * Math.cos(yaw / 180 * Math.PI) + vel.y * Math.sin(yaw / 180 * Math.PI))
        val y = (vel.y * Math.cos(yaw / 180 * Math.PI) - vel.x * Math.sin(yaw / 180 * Math.PI))

        //Remove hard codes
        if (!keyPressed(VK_SPACE)) {
            if (!keyPressed(VK_W) && !keyPressed(VK_S)) {
                if (x > 30) {
                    CSGO.clientDLL[ClientOffsets.dwForceBackward] = 6
//                    robot.keyPress(VK_S)
//                    robot.keyRelease(VK_S)
                } else if (x < -30) {
                    CSGO.clientDLL[ClientOffsets.dwForceForward] = 6
//                    robot.keyPress(VK_W)
//                    robot.keyRelease(VK_W)
                }
            }

            if (!keyPressed(VK_A) && !keyPressed(VK_D)) {
                if (y > 30) {
                    CSGO.clientDLL[ClientOffsets.dwForceRight] = 6
//                    robot.keyPress(VK_D)
//                    robot.keyRelease(VK_D)
                } else if (y < -30) {
                    CSGO.clientDLL[ClientOffsets.dwForceLeft] = 6
//                    robot.keyPress(VK_A)
//                    robot.keyRelease(VK_A)
                }
            }
        }
    }
}