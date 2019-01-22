package rat.poison.scripts.esp

import org.jire.arrowhead.keyPressed
import rat.poison.game.*
import rat.poison.game.offsets.ClientOffsets
import rat.poison.settings.*
import rat.poison.ui.UIUpdate
import rat.poison.utils.every

var prevchamsshowhealth = CHAMS_SHOW_HEALTH
var prevchamsbrightness = CHAMS_BRIGHTNESS
var prevchamsespcolor = CHAMS_ESP_COLOR

fun espToggle() = every(4) {
    if (keyPressed(VISUALS_TOGGLE_KEY) && !MENUTOG) {

        if (!ENABLE_ESP) {
            CHAMS_SHOW_HEALTH = prevchamsshowhealth
            CHAMS_BRIGHTNESS = prevchamsbrightness
            CHAMS_ESP_COLOR = prevchamsespcolor

            val write = (if (FLICKER_FREE_GLOW) 0xEB else 0x74).toByte()
            try { CSGO.clientDLL[ClientOffsets.dwGlowUpdate] = write } catch (e: Exception) {}
            try { CSGO.clientDLL[ClientOffsets.dwGlowUpdate2] = write } catch (e: Exception) {}
        }
        else {
            prevchamsshowhealth = CHAMS_SHOW_HEALTH
            prevchamsbrightness = CHAMS_BRIGHTNESS
            prevchamsespcolor = CHAMS_ESP_COLOR

            CHAMS_BRIGHTNESS = 0
            CHAMS_SHOW_HEALTH = false
            CHAMS_ESP_COLOR = Color(255, 255, 255, 1.0)

            try { CSGO.clientDLL[ClientOffsets.dwGlowUpdate] = 0x74.toByte() } catch (e: Exception) {}
            try { CSGO.clientDLL[ClientOffsets.dwGlowUpdate2] = 0x74.toByte() } catch (e: Exception) {}
        }

        ENABLE_ESP = !ENABLE_ESP
        Thread.sleep(50) //Wait to make sure settings loop

        UIUpdate()

        Thread.sleep(200)
    }
}