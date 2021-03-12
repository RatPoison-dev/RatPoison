package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.ui.uiUpdate

fun espToggleCallback() {
    curSettings["ENABLE_ESP"] = !curSettings.bool["ENABLE_ESP"]
    if (!curSettings.bool["ENABLE_ESP"]) {
        disableAllEsp()
    }

    //Thread.sleep(100)

    uiUpdate()
}