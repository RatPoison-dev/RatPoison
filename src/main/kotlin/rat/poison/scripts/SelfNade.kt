package rat.poison.scripts

import kotlinx.coroutines.Runnable
import rat.poison.curSettings
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.robot
import rat.poison.utils.Angle
import rat.poison.utils.pathAim
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent
import rat.poison.game.CSGO
import rat.poison.game.offsets.ClientOffsets.dwForceJump

fun selfNade() {
    Thread(Runnable {
        robot.keyPress(curSettings["MENU_KEY"].toInt())
        Thread.sleep(50)

        val curAng = clientState.angle()
        val destAng = Angle() //= curAng doesnt work??
        destAng.set(curAng.x, curAng.y, curAng.z)
        destAng.apply {
            x = -89.0
        }
        pathAim(curAng, destAng, 10, false, checkOnScreen = false)
        Thread.sleep(50)
        robot.keyPress(KeyEvent.VK_Z)
        Thread.sleep(50)
        robot.keyRelease(KeyEvent.VK_Z)
        Thread.sleep(50)
        robot.keyPress(KeyEvent.VK_1)
        Thread.sleep(50)
        robot.keyRelease(KeyEvent.VK_1)
        Thread.sleep(50)
        CSGO.clientDLL[dwForceJump] = 6
        robot.keyPress(KeyEvent.VK_CONTROL)
        Thread.sleep(50)
        robot.mousePress(MouseEvent.BUTTON3_DOWN_MASK)
        Thread.sleep(50)
        robot.mouseRelease(MouseEvent.BUTTON3_DOWN_MASK)
        Thread.sleep(2000)
        robot.keyRelease(KeyEvent.VK_CONTROL)
        pathAim(clientState.angle(), curAng, 10, false, checkOnScreen = false )
    }).start()
}
