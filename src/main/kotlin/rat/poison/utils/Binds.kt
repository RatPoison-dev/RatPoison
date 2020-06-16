package rat.poison.utils

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.ui.tabs.boxEspTab
import rat.poison.ui.tabs.glowEspTab
import rat.poison.ui.uiPanels.miscTab
import rat.poison.ui.uiPanels.visualsTab

var bunnyHopToggleKey = ObservableBoolean({ keyPressed(1) })
var boxEspToggleKey = ObservableBoolean({ keyPressed(1) })
var autoStrafeToggleKey = ObservableBoolean({keyPressed(1) })
var autoStrafeBHopOnlyToggleKey = ObservableBoolean({keyPressed(1) })
var fastStopToggleKey = ObservableBoolean({keyPressed(1) })
var aimStraferToggleKey = ObservableBoolean({keyPressed(1) })
var fovChangerToggleKey = ObservableBoolean({keyPressed(1) })
var fovSmoothingToggleKey = ObservableBoolean({keyPressed(1) })
var bombTimerToggleKey = ObservableBoolean({keyPressed(1) })
var bombTimerEnableBarsToggleKey = ObservableBoolean({keyPressed(1) })
var bombTimerEnableMenuToggleKey = ObservableBoolean({keyPressed(1) })
var spectatorListToggleKey = ObservableBoolean({keyPressed(1) })
var knifeBotToggleKey = ObservableBoolean({keyPressed(1) })
var doorSpamToggleKey = ObservableBoolean({keyPressed(1) })
var weaponSpamToggleKey = ObservableBoolean({keyPressed(1) })
var enableReducedFlashToggleKey = ObservableBoolean({keyPressed(1) })
var hitSoundCheckBoxToggleKey = ObservableBoolean({keyPressed(1) })
var enableEspToggleKey = ObservableBoolean({keyPressed(1) })
var radarEspToggleKey = ObservableBoolean({keyPressed(1) })
var nightModeToggleKey = ObservableBoolean({keyPressed(1) })
var visAdrenalineToggleKey = ObservableBoolean({keyPressed(1) })
var glowEspToggleKey = ObservableBoolean({keyPressed(1) })

fun constructVars() {
    bunnyHopToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_BUNNY_HOP_SWITCH_KEY"].toInt()) })
    boxEspToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_BOX_ESP_SWITCH_KEY"].toInt()) } )
    autoStrafeToggleKey = ObservableBoolean({ keyPressed(curSettings["AUTO_STRAFE_SWITCH_KEY"].toInt()) })
    autoStrafeBHopOnlyToggleKey = ObservableBoolean({ keyPressed(curSettings["STRAFE_BHOP_ONLY_SWITCH_KEY"].toInt()) })
    fastStopToggleKey = ObservableBoolean({ keyPressed(curSettings["FAST_STOP_SWITCH_KEY"].toInt()) })
    aimStraferToggleKey = ObservableBoolean({ keyPressed(curSettings["AIM_STRAFER_SWITCH_KEY"].toInt()) })
    fovChangerToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_FOV_CHANGER_SWITCH_KEY"].toInt()) })
    fovSmoothingToggleKey = ObservableBoolean({ keyPressed(curSettings["FOV_SMOOTH_SWITCH_KEY"].toInt()) })
    bombTimerToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_BOMB_TIMER_SWITCH_KEY"].toInt()) })
    bombTimerEnableBarsToggleKey = ObservableBoolean({ keyPressed(curSettings["BOMB_TIMER_BARS_SWITCH_KEY"].toInt()) })
    bombTimerEnableMenuToggleKey = ObservableBoolean({ keyPressed(curSettings["BOMB_TIMER_MENU_SWITCH_KEY"].toInt()) })
    spectatorListToggleKey = ObservableBoolean({ keyPressed(curSettings["SPECTATOR_LIST_SWITCH_KEY"].toInt()) })
    knifeBotToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_AUTO_KNIFE_SWITCH_KEY"].toInt()) })
    doorSpamToggleKey = ObservableBoolean({ keyPressed(curSettings["D_SPAM_SWITCH_KEY"].toInt()) })
    weaponSpamToggleKey = ObservableBoolean({ keyPressed(curSettings["W_SPAM_SWITCH_KEY"].toInt()) })
    enableReducedFlashToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_REDUCED_FLASH_SWITCH_KEY"].toInt()) })
    hitSoundCheckBoxToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_HITSOUND_SWITCH_KEY"].toInt()) })
    enableEspToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_ESP_SWITCH_KEY"].toInt()) })
    radarEspToggleKey = ObservableBoolean({ keyPressed(curSettings["RADAR_ESP_SWITCH_KEY"].toInt()) })
    nightModeToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_NIGHTMODE_SWITCH_KEY"].toInt()) })
    visAdrenalineToggleKey = ObservableBoolean({ keyPressed(curSettings["ENABLE_ADRENALINE_SWITCH_KEY"].toInt()) })
    glowEspToggleKey = ObservableBoolean({ keyPressed(curSettings["GLOW_ESP_SWITCH_KEY"].toInt()) })
}
fun addListeners() {
    bunnyHopToggleKey.update()
    if (bunnyHopToggleKey.justBecameTrue) {
        miscTab.bunnyHop.checkBox.isChecked = !miscTab.bunnyHop.checkBox.isChecked
    }
    boxEspToggleKey.update()
    if (boxEspToggleKey.justBecameTrue) {
        boxEspTab.boxEsp.checkBox.isChecked = !boxEspTab.boxEsp.checkBox.isChecked
    }
    autoStrafeToggleKey.update()
    if (autoStrafeToggleKey.justBecameTrue) {
        miscTab.autoStrafe.checkBox.isChecked = !miscTab.autoStrafe.checkBox.isChecked
    }

    autoStrafeBHopOnlyToggleKey.update()
    if (autoStrafeBHopOnlyToggleKey.justBecameTrue) {
        miscTab.autoStrafeBHopOnly.checkBox.isChecked = !miscTab.autoStrafeBHopOnly.checkBox.isChecked
    }

    fastStopToggleKey.update()
    if (fastStopToggleKey.justBecameTrue) {
        miscTab.fastStop.checkBox.isChecked = !miscTab.fastStop.checkBox.isChecked
    }

    aimStraferToggleKey.update()
    if (aimStraferToggleKey.justBecameTrue) {
        miscTab.aimStrafer.checkBox.isChecked = !miscTab.aimStrafer.checkBox.isChecked
    }

    fovChangerToggleKey.update()
    if (fovChangerToggleKey.justBecameTrue) {
        miscTab.fovChanger.checkBox.isChecked = !miscTab.fovChanger.checkBox.isChecked
    }

    fovSmoothingToggleKey.update()
    if (fovSmoothingToggleKey.justBecameTrue) {
        miscTab.fovSmoothing.checkBox.isChecked = !miscTab.fovSmoothing.checkBox.isChecked
    }

    bombTimerToggleKey.update()
    if (bombTimerToggleKey.justBecameTrue) {
        miscTab.bombTimer.checkBox.isChecked = !miscTab.bombTimer.checkBox.isChecked
    }

    bombTimerEnableBarsToggleKey.update()
    if (bombTimerEnableBarsToggleKey.justBecameTrue) {
        miscTab.bombTimerEnableBars.checkBox.isChecked = !miscTab.bombTimerEnableBars.checkBox.isChecked
    }

    bombTimerEnableMenuToggleKey.update()
    if (bombTimerEnableMenuToggleKey.justBecameTrue) {
        miscTab.bombTimerEnableMenu.checkBox.isChecked = !miscTab.bombTimerEnableMenu.checkBox.isChecked
    }

    spectatorListToggleKey.update()
    if (spectatorListToggleKey.justBecameTrue) {
        miscTab.spectatorList.checkBox.isChecked = !miscTab.spectatorList.checkBox.isChecked
    }

    knifeBotToggleKey.update()
    if (knifeBotToggleKey.justBecameTrue) {
        miscTab.knifeBot.checkBox.isChecked = !miscTab.knifeBot.checkBox.isChecked
    }

    doorSpamToggleKey.update()
    if (doorSpamToggleKey.justBecameTrue) {
        miscTab.doorSpam.checkBox.isChecked = !miscTab.doorSpam.checkBox.isChecked
    }

    weaponSpamToggleKey.update()
    if (weaponSpamToggleKey.justBecameTrue) {
        miscTab.weaponSpam.checkBox.isChecked = !miscTab.weaponSpam.checkBox.isChecked
    }

    enableReducedFlashToggleKey.update()
    if (enableReducedFlashToggleKey.justBecameTrue) {
        miscTab.enableReducedFlash.checkBox.isChecked = !miscTab.enableReducedFlash.checkBox.isChecked
    }

    hitSoundCheckBoxToggleKey.update()
    if (hitSoundCheckBoxToggleKey.justBecameTrue) {
        miscTab.hitSoundCheckBox.checkBox.isChecked = !miscTab.hitSoundCheckBox.checkBox.isChecked
    }
    enableEspToggleKey.update()
    if (enableEspToggleKey.justBecameTrue) {
        visualsTab.enableEsp.checkBox.isChecked = !visualsTab.enableEsp.checkBox.isChecked
    }


    radarEspToggleKey.update()
    if (radarEspToggleKey.justBecameTrue) {
        visualsTab.radarEsp.checkBox.isChecked = !visualsTab.radarEsp.checkBox.isChecked
    }


    nightModeToggleKey.update()
    if (nightModeToggleKey.justBecameTrue) {
        visualsTab.nightMode.checkBox.isChecked = !visualsTab.nightMode.checkBox.isChecked
    }


    visAdrenalineToggleKey.update()
    if (visAdrenalineToggleKey.justBecameTrue) {
        visualsTab.visAdrenaline.checkBox.isChecked = !visualsTab.visAdrenaline.checkBox.isChecked
    }
    glowEspToggleKey.update()
    if (glowEspToggleKey.justBecameTrue) {
        glowEspTab.glowEsp.checkBox.isChecked = !glowEspTab.glowEsp.checkBox.isChecked
    }
}