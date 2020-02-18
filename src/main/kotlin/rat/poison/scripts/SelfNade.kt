package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.setAngle
import rat.poison.robot

import rat.poison.utils.normalize
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

fun selfNade() {
    Thread(Runnable {
        val angle = clientState.angle()
        angle.apply {
            x = -89.0
            normalize()
        }
        clientState.setAngle(angle)
        Thread.sleep(50)
        robot.keyPress(curSettings["MENU_KEY"].toInt())
        Thread.sleep(50)
        robot.keyPress(KeyEvent.VK_Z)
        Thread.sleep(50)
        robot.keyRelease(KeyEvent.VK_Z)
        Thread.sleep(50)
        robot.keyPress(KeyEvent.VK_1)
        Thread.sleep(50)
        robot.keyRelease(KeyEvent.VK_1)
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
