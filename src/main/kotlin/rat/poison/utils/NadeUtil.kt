package rat.poison.utils

import rat.poison.input
import java.awt.event.MouseEvent

fun jumpAndThrow() {
    Thread {
        input.mousePress(MouseEvent.BUTTON1_DOWN_MASK)
        Thread.sleep(12)
        input.keyPress(32)
        Thread.sleep(12)
        input.keyRelease(32)
        Thread.sleep(12)
        input.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK)
    }.start()
}

fun standAndThrow() {
    Thread {
        input.mousePress(MouseEvent.BUTTON1_DOWN_MASK)
        Thread.sleep(12)
        input.mouseRelease(MouseEvent.BUTTON1_DOWN_MASK)
    }.start()
}