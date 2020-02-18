package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.dead
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets
import rat.poison.robot
import rat.poison.strToBool
import rat.poison.utils.ObservableBoolean
import rat.poison.utils.every
import java.awt.event.MouseEvent
import java.awt.event.KeyEvent

private var toggled = false
var weaponSpamToggleKey = ObservableBoolean({keyPressed(curSettings["W_SPAM_KEY"].toInt())})

fun weaponSpam() = every (20) {
    if (!curSettings["W_SPAM"].strToBool()) return@every

    updateCursorEnable()
    if (cursorEnable || me.dead()) return@every

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

