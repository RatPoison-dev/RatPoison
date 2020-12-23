package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.robot
import rat.poison.scripts.aim.meDead
import rat.poison.utils.ObservableBoolean
import rat.poison.utils.every
import rat.poison.utils.keyPressed
import java.awt.event.MouseEvent

private var toggled = false
var weaponSpamToggleKey = ObservableBoolean({keyPressed(curSettings.int["W_SPAM_KEY"])})

fun weaponSpam() = every (20, inGameCheck = true) {
    if (!curSettings.bool["W_SPAM"]) return@every

    updateCursorEnable()
    if (cursorEnable || meDead) return@every

    weaponSpamToggleKey.update()
    if (weaponSpamToggleKey.justBecameTrue) {
        toggled = !toggled
    }

    if (toggled) {
        Thread(Runnable {
            robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK)
            Thread.sleep(2)
            robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK)
            Thread.sleep(2)
        }).start()
    }
}