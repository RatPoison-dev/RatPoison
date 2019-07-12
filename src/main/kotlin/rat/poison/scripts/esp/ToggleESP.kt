package rat.poison.scripts.esp

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.uiUpdate
import rat.poison.utils.every

fun espToggle() = every(4) {
    if (keyPressed(curSettings["VISUALS_TOGGLE_KEY"]!!.toInt())) {
        curSettings["ENABLE_ESP"] = !curSettings["ENABLE_ESP"]!!.strToBool()
        if (!curSettings["ENABLE_ESP"]!!.strToBool()) {
            disableEsp()
        }

        Thread.sleep(100)

        uiUpdate()
    }
}