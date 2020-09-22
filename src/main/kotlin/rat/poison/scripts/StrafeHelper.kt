package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.onGround
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.robot
import rat.poison.settings.MENUTOG
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inBackground
import rat.poison.utils.inGame
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_SPACE

private var lastAngY = 0.0F

fun strafeHelper() = every(2) {
    if (MENUTOG || !inGame || inBackground) return@every

    val aStrafe = curSettings["AUTO_STRAFE"].strToBool()

    if (!aStrafe) {
        return@every
    } else if (aStrafe) {
        updateCursorEnable()
        if (cursorEnable) return@every
        val curAngY = clientState.angle().y
        val grounded = me.onGround()

        if (aStrafe) { //Auto Strafe
            if ((curSettings["STRAFE_BHOP_ONLY"].strToBool() && keyPressed(VK_SPACE)) || (!curSettings["STRAFE_BHOP_ONLY"].strToBool())) {
                if (!grounded) {
                    if (!keyPressed(KeyEvent.VK_A) && !keyPressed(KeyEvent.VK_D)) {
                        if (curAngY > lastAngY) {
                            robot.keyPress(KeyEvent.VK_A)
                            robot.keyRelease(KeyEvent.VK_A)
                        } else if (curAngY < lastAngY) {
                            robot.keyPress(KeyEvent.VK_D)
                            robot.keyRelease(KeyEvent.VK_D)
                        }
                    }
                }
            }
        }

        lastAngY = curAngY
    }
}