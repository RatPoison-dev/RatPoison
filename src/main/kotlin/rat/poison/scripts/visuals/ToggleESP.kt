package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.ui.uiUpdate

fun espToggleCallback() {
    curSettings["ENABLE_VISUALS"] = !curSettings.bool["ENABLE_VISUALS"]
    if (!curSettings.bool["ENABLE_VISUALS"]) {
        disableAllEsp()
    }

    //Thread.sleep(100)

    uiUpdate()
}