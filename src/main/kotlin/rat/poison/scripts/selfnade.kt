package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.offsets.ClientOffsets
import rat.poison.game.setAngle
import rat.poison.overlayMenuKey
import rat.poison.robot
import rat.poison.strToBool
import rat.poison.toInt

import rat.poison.utils.normalize
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent


fun selfnade() {
    Thread(Runnable {
        val angle = clientState.angle()
        angle.apply {
            x = (-89).toDouble()
            normalize()
        }
        clientState.setAngle(angle)
        Thread.sleep(50)
        robot.keyPress(curSettings["MENU_KEY"].toInt())
        Thread.sleep(50)
        robot.keyPress(KeyEvent.VK_CONTROL)
        Thread.sleep(50)
        robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK)
        Thread.sleep(50)
        robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK)
        Thread.sleep(2000)
        robot.keyRelease(KeyEvent.VK_CONTROL)
    }).start()
    }
