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
import java.awt.event.KeyEvent.*
import kotlin.math.cos
import kotlin.math.sin

internal fun fastStop() = every(4) {
    if (!curSettings["FAST_STOP"]!!.strToBool() || notInGame) {
        return@every
    }

    if (!me.dead()) {
        val vel = me.velocity()
        val yaw = clientState.angle().y

        //Velocity relative to player direction
        val x = (vel.x * cos(yaw / 180 * Math.PI) + vel.y * sin(yaw / 180 * Math.PI))
        val y = (vel.y * cos(yaw / 180 * Math.PI) - vel.x * sin(yaw / 180 * Math.PI))

        //Remove hard codes
        if (!keyPressed(VK_SPACE)) {
            if (!keyPressed(VK_W) && !keyPressed(VK_S)) {
                if (x > 30) {
                    CSGO.clientDLL[ClientOffsets.dwForceBackward] = 6
                } else if (x < -30) {
                    CSGO.clientDLL[ClientOffsets.dwForceForward] = 6
                }
            }

            if (!keyPressed(VK_A) && !keyPressed(VK_D)) {
                if (y > 30) {
                    CSGO.clientDLL[ClientOffsets.dwForceRight] = 6
                } else if (y < -30) {
                    CSGO.clientDLL[ClientOffsets.dwForceLeft] = 6
                }
            }
        }
    }
}