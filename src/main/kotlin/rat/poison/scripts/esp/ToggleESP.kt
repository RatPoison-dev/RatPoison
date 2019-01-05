package rat.poison.scripts.esp

import rat.poison.settings.*
import rat.poison.utils.*
import org.jire.arrowhead.keyPressed

fun espToggle() = every(4) {
    if (keyPressed(VISUALS_TOGGLE_KEY) && !inBackground) {
        ENABLE_ESP = !ENABLE_ESP
        ENABLE_RECOIL_CROSSHAIR = !ENABLE_RECOIL_CROSSHAIR
        esp()
        if (ACTION_LOG) {
            println("ESP toggled to " + ENABLE_ESP)
        }
        Thread.sleep(200)
    }
}