package rat.poison.scripts.esp

import org.jire.arrowhead.keyPressed
import rat.poison.game.*
import rat.poison.game.offsets.ClientOffsets
import rat.poison.settings.*
import rat.poison.utils.every

fun espToggle() = every(4) {
    if (keyPressed(VISUALS_TOGGLE_KEY) && !MENUTOG) {
        val prevboxesp = BOX_ESP
        val prevglowesp = GLOW_ESP
        val prevmodelesp = MODEL_ESP
        val prevchamsesp = CHAMS_ESP
        val prevchamsshowhealth = CHAMS_SHOW_HEALTH
        val prevskeletonesp = SKELETON_ESP
        val prevchamsbrightness = CHAMS_BRIGHTNESS
        val prevchamsespcolor = CHAMS_ESP_COLOR
        val prevrcrosshair = ENABLE_RECOIL_CROSSHAIR

        CHAMS_BRIGHTNESS = 0
        CHAMS_SHOW_HEALTH = false
        CHAMS_ESP_COLOR = Color(255, 255, 255, 1.0)

        Thread.sleep(50) //Wait to make sure settings loop

        ENABLE_ESP = !ENABLE_ESP

        //if (ACTION_LOG) { //Removed for now
            //println("ESP toggled to " + ENABLE_ESP)
        //}

        if (ENABLE_ESP) { //Could interfere with overlay, replace this later
            val write = (if (FLICKER_FREE_GLOW) 0xEB else 0x74).toByte()
            try { CSGO.clientDLL[ClientOffsets.dwGlowUpdate] = write } catch (e: Exception) {}
            try { CSGO.clientDLL[ClientOffsets.dwGlowUpdate2] = write } catch (e: Exception) {}
            BOX_ESP = prevboxesp
            GLOW_ESP = prevglowesp
            MODEL_ESP = prevmodelesp
            CHAMS_ESP = prevchamsesp
            CHAMS_SHOW_HEALTH = prevchamsshowhealth
            SKELETON_ESP = prevskeletonesp
            CHAMS_BRIGHTNESS = prevchamsbrightness
            CHAMS_ESP_COLOR = prevchamsespcolor
            ENABLE_RECOIL_CROSSHAIR = prevrcrosshair
        }
        else {
            ENABLE_RECOIL_CROSSHAIR = false
            try { CSGO.clientDLL[ClientOffsets.dwGlowUpdate] = 0x74.toByte() } catch (e: Exception) {}
            try { CSGO.clientDLL[ClientOffsets.dwGlowUpdate2] = 0x74.toByte() } catch (e: Exception) {}
        }

        Thread.sleep(200)
    }
}