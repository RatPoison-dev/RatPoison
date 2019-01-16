package rat.poison.scripts.esp

import org.jire.arrowhead.keyPressed
import rat.poison.game.*
import rat.poison.loadSettings
import rat.poison.settings.*
import rat.poison.utils.every

fun espToggle() = every(4) {
    //println("menu tog: "+MENUTOG)
    if (keyPressed(VISUALS_TOGGLE_KEY) && !MENUTOG) {
        CHAMS_BRIGHTNESS = 0
        CHAMS_SHOW_HEALTH = false
        CHAMS_ESP_COLOR = Color(255, 255, 255, 1.0)

        Thread.sleep(50) //Wait to make sure settings loop

        ENABLE_ESP = !ENABLE_ESP

        if (ACTION_LOG) {
            //println("ESP toggled to " + ENABLE_ESP)
        }

        Thread.sleep(500)

        if (ENABLE_ESP) { //Could interfere with overlay, replace this later
            loadSettings() //Reload settings on enable since we had to override them
        }
        else {
            ENABLE_RECOIL_CROSSHAIR = false
        }
    }
}