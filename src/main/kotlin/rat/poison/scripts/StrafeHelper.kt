package rat.poison.scripts

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
import rat.poison.utils.inBackground
import rat.poison.utils.keyPressed
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.VK_SPACE

private var lastAngY = 0.0F

fun strafeHelper() = every(2, inGameCheck = true) {
    if (MENUTOG || inBackground || meDead) return@every

    if (!curSettings.bool["AUTO_STRAFE"]) return@every

    updateCursorEnable()
    if (cursorEnable) return@every
    val curAngY = clientState.angle().y
    val grounded = me.onGround()

    if ((curSettings.bool["STRAFE_BHOP_ONLY"] && keyPressed(VK_SPACE)) || (!curSettings.bool["STRAFE_BHOP_ONLY"])) {
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