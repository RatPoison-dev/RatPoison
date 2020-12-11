package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.ui.uiUpdate
import rat.poison.utils.generalUtil.strToBool

fun espToggleCallback() {
    curSettings["ENABLE_ESP"] = !curSettings["ENABLE_ESP"].strToBool()
    if (!curSettings["ENABLE_ESP"].strToBool()) {
        disableAllEsp()
    }

    //Thread.sleep(100)

    uiUpdate()
}