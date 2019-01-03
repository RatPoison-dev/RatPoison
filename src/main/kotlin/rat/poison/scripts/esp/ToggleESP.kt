package rat.poison.scripts.esp

import rat.poison.settings.ACTION_LOG
import rat.poison.settings.ENABLE_ESP
import rat.poison.settings.ESP_TOGGLE_KEY
import rat.poison.utils.*
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