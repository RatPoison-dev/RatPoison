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
import rat.poison.scripts.aim.meDead
import rat.poison.settings.MENUTOG
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inBackground
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_SPACE

private var lastAngY = 0.0F

fun strafeHelper() = every(2, inGameCheck = true) {
    if (MENUTOG || inBackground || meDead) return@every

    if (!curSettings["AUTO_STRAFE"].strToBool()) return@every

    updateCursorEnable()
    if (cursorEnable) return@every
    val curAngY = clientState.angle().y
    val grounded = me.onGround()

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

    lastAngY = curAngY
}