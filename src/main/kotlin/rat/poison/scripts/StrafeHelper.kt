package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.onGround
import rat.poison.game.entity.weapon
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets
import rat.poison.scripts.aim.target
import rat.poison.settings.MENUTOG
import rat.poison.strToBool
import rat.poison.utils.every
import rat.poison.utils.notInGame
import java.awt.Robot
import java.awt.event.KeyEvent

private var lastAngY = 0.0
private val robot = Robot().apply { this.autoDelay = 0 }

fun strafeHelper() = every(2) {
    if (MENUTOG || notInGame || cursorEnable) return@every

    val aStrafe = curSettings["AUTO_STRAFE"].strToBool()
    val aimStrafe = curSettings["AIM_STRAFER"].strToBool()

    if (!aStrafe && !aimStrafe) return@every

    if (aStrafe || aimStrafe) {
        updateCursorEnable()
        val curAngY = clientState.angle().y

        if (aStrafe) {
            if ((curSettings["STRAFE_BHOP_ONLY"].strToBool() && keyPressed(curSettings["BUNNY_HOP_KEY"].toInt())) || (!curSettings["STRAFE_BHOP_ONLY"].strToBool())) {

                if (!keyPressed(KeyEvent.VK_A) && !keyPressed(KeyEvent.VK_D)) {
                    if (curAngY > lastAngY) {
                        robot.keyPress(KeyEvent.VK_A)
                        robot.keyRelease(KeyEvent.VK_A)
                    } else if (curAngY < lastAngY) {
                        robot.keyPress(KeyEvent.VK_D)
                        robot.keyRelease(KeyEvent.VK_D)
                    }
                }
            }
        }

        if (aimStrafe) {
            val meWep = me.weapon()
            if (!meWep.knife && !meWep.grenade) {
                if (!keyPressed(KeyEvent.VK_A) && !keyPressed(KeyEvent.VK_D) && !keyPressed(KeyEvent.VK_W) && !keyPressed(KeyEvent.VK_S) && !keyPressed(KeyEvent.VK_SPACE)) {
                    val dZone = curSettings["AIM_STRAFER_STRICTNESS"].toDouble()
                    val pShift = curSettings["AIM_STRAFER_SHIFT"].toBoolean()
                    if (curAngY > lastAngY + dZone) {
                        if (pShift) {
                            robot.keyPress(KeyEvent.VK_SHIFT)
                        }
                        if (curSettings["AIM_STRAFER_TYPE"].toInt() == 1) {
                            robot.keyPress(KeyEvent.VK_A)
                            robot.keyRelease(KeyEvent.VK_A)
                        } else {
                            robot.keyPress(KeyEvent.VK_D)
                            robot.keyRelease(KeyEvent.VK_D)
                        }
                        if (pShift) {
                            robot.keyRelease(KeyEvent.VK_SHIFT)
                        }
                    } else if (curAngY < lastAngY - dZone) {
                        if (pShift) {
                            robot.keyPress(KeyEvent.VK_SHIFT)
                        }
                        if (curSettings["AIM_STRAFER_TYPE"].toInt() == 1) {
                            robot.keyPress(KeyEvent.VK_D)
                            robot.keyRelease(KeyEvent.VK_D)
                        } else {
                            robot.keyPress(KeyEvent.VK_A)
                            robot.keyRelease(KeyEvent.VK_A)
                        }
                        if (pShift) {
                            robot.keyRelease(KeyEvent.VK_SHIFT)
                        }
                    }
                }
            }
        }

        lastAngY = curAngY
    }
}