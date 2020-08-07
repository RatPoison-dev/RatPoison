package rat.poison.scripts

import kotlinx.coroutines.Runnable
import rat.poison.curSettings
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.robot
import rat.poison.settings.MENUTOG
import rat.poison.utils.Angle
import rat.poison.utils.normalize
import rat.poison.utils.pathAim
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

fun selfNade() {
    Thread(Runnable {
        if (MENUTOG) {
            robot.keyPress(curSettings["MENU_KEY"].toInt())
        }
        Thread.sleep(50)

        val curAng = clientState.angle()
        val destAng = Angle() //= curAng doesnt work??
        destAng.set(-89F, curAng.y, curAng.z)
        destAng.normalize()

        pathAim(curAng, destAng, 10, false, checkOnScreen = false)
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
