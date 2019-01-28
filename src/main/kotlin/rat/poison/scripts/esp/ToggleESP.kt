package rat.poison.scripts.esp

import org.jire.arrowhead.keyPressed
import rat.poison.game.*
import rat.poison.game.offsets.ClientOffsets
import rat.poison.settings.*
import rat.poison.ui.UIUpdate
import rat.poison.utils.every

fun espToggle() = every(4) {
    if (keyPressed(VISUALS_TOGGLE_KEY) && !MENUTOG) {
        ENABLE_ESP = !ENABLE_ESP
        if (!ENABLE_ESP) {
            disableEsp()
        }
        Thread.sleep(100) //Wait to make sure settings loop

        UIUpdate()
    }
}