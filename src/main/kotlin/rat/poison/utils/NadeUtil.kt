package rat.poison.utils

import rat.poison.robot
import java.awt.event.MouseEvent

fun jumpAndThrow() {
    Thread(Runnable {
        robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK)
        Thread.sleep(12)
        robot.keyPress(32)
        Thread.sleep(12)
        robot.keyRelease(32)
        Thread.sleep(12)
        robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK)
    }).start()
}

fun standAndThrow() {
    Thread(Runnable {
        robot.mousePress(MouseEvent.BUTTON1_DOWN_MASK)
        Thread.sleep(12)
        robot.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK)
    }).start()
}