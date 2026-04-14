package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.input
import rat.poison.scripts.aim.meCurWep
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inBackground
import java.awt.event.MouseEvent

private fun disabled(): Boolean {
    return inBackground || !curSettings["AUTOMATIC_WEAPONS"].strToBool() || !meCurWep.gun || meCurWep.automatic
}

fun automaticWeaponsSafeMode() = Thread {
    while (true) {
        if (!input.keyPressed(1) || disabled()) {
            Thread.sleep(400)
            continue
        }
        input.mouseClick(MouseEvent.BUTTON1_DOWN_MASK, input.nextMouse)
        Thread.sleep(curSettings["AUTO_WEP_DELAY"].toLong())
    }
}
