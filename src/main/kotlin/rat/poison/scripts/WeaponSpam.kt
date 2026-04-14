package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.input
import rat.poison.robot
import rat.poison.scripts.aim.meDead
import rat.poison.utils.ObservableBoolean
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import java.awt.event.MouseEvent

private var toggled = false
var weaponSpamToggleKey = ObservableBoolean({input.keyPressed(curSettings["W_SPAM_KEY"].toInt())})

fun weaponSpam() = every (20, inGameCheck = true) {
    if (!curSettings["W_SPAM"].strToBool()) return@every

    updateCursorEnable()
    if (cursorEnable || meDead) return@every

    weaponSpamToggleKey.update()
    if (weaponSpamToggleKey.justBecameTrue) {
        toggled = !toggled
    }

    if (toggled) {
        Thread {
            input.mousePress(MouseEvent.BUTTON3_DOWN_MASK)
            Thread.sleep(2)
            input.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK)
            Thread.sleep(2)
        }.start()
    }
}

