package rat.poison.scripts.esp

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.ui.UIUpdate
import rat.poison.utils.every

fun espToggle() = every(4) {
    if (keyPressed(VISUALS_TOGGLE_KEY)) {
        curSettings["ENABLE_ESP"] = !curSettings["ENABLE_ESP"]!!.strToBool()
        if (!curSettings["ENABLE_ESP"]!!.strToBool()) {
            disableEsp()
        }

        Thread.sleep(100)

        UIUpdate()
    }
}