package rat.plague.scripts.esp

import rat.plague.settings.ACTION_LOG
import rat.plague.settings.ENABLE_ESP
import rat.plague.settings.ESP_TOGGLE_KEY
import rat.plague.utils.*
import org.jire.arrowhead.keyPressed

fun espToggle() = every(4) {
    if (keyPressed(ESP_TOGGLE_KEY) && !inBackground) {
        ENABLE_ESP = !ENABLE_ESP
        esp()
        if (ACTION_LOG) {
            println("ESP toggled to " + ENABLE_ESP)
        }
        Thread.sleep(500)
    }
}