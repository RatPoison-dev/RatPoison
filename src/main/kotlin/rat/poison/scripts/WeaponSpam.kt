package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.dead
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets
import rat.poison.robot
import rat.poison.strToBool
import rat.poison.utils.every
import java.awt.event.MouseEvent
import java.awt.event.KeyEvent

fun weaponSpam() = every (50){
    if (curSettings["W_SPAM"].strToBool() && !me.dead() && keyPressed(curSettings["W_SPAM_KEY"].toInt())) Thread(Runnable {
        robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK)
        Thread.sleep(2)
        robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK)
        Thread.sleep(2)
    }).start()
}

