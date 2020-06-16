package rat.poison.utils

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.ui.uiPanels.miscTab

var bunnyHopToggleKey = ObservableBoolean({ keyPressed(1) })

fun constructVars() {
    bunnyHopToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_BUNNY_HOP_SWITCH_KEY"].toInt()) })
}
fun addListeners() {
    bunnyHopToggleKey.update()
    if (bunnyHopToggleKey.justBecameTrue) {
        miscTab.bunnyHop.isChecked = !miscTab.bunnyHop.isChecked
    }
}