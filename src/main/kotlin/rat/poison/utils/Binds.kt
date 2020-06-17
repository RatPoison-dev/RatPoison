package rat.poison.utils

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.ui.tabs.boxEspTab
import rat.poison.ui.tabs.glowEspTab
import rat.poison.ui.uiPanels.aimTab
import rat.poison.ui.uiPanels.miscTab
import rat.poison.ui.uiPanels.rcsTab
import rat.poison.ui.uiPanels.visualsTab
import rat.poison.utils.varUtil.strToBool

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
var radarEspToggleKey = ObservableBoolean({keyPressed(1) })
var nightModeToggleKey = ObservableBoolean({keyPressed(1) })
var visAdrenalineToggleKey = ObservableBoolean({keyPressed(1) })
var glowEspToggleKey = ObservableBoolean({keyPressed(1) })
var enableRCSToggleKey = ObservableBoolean({keyPressed(1) })
var enableAimToggleKey = ObservableBoolean({keyPressed(1) })
var enableTrigToggleKey = ObservableBoolean({keyPressed(1) })
var enableBacktrackToggleKey = ObservableBoolean({keyPressed(1) })

fun constructVars() {
    bunnyHopToggleKey = ObservableBoolean({ curSettings["ENABLE_BUNNY_HOP_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["ENABLE_BUNNY_HOP_SWITCH_KEY"].toInt()) })
    boxEspToggleKey = ObservableBoolean({ curSettings["ENABLE_BOX_ESP_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["ENABLE_BOX_ESP_SWITCH_KEY"].toInt()) } )
    autoStrafeToggleKey = ObservableBoolean({ curSettings["AUTO_STRAFE_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["AUTO_STRAFE_SWITCH_KEY"].toInt()) })
    autoStrafeBHopOnlyToggleKey = ObservableBoolean({ curSettings["STRAFE_BHOP_ONLY_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["STRAFE_BHOP_ONLY_SWITCH_KEY"].toInt()) })
    fastStopToggleKey = ObservableBoolean({ curSettings["FAST_STOP_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["FAST_STOP_SWITCH_KEY"].toInt()) })
    aimStraferToggleKey = ObservableBoolean({ curSettings["AIM_STRAFER_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["AIM_STRAFER_SWITCH_KEY"].toInt()) })
    fovChangerToggleKey = ObservableBoolean({ curSettings["ENABLE_FOV_CHANGER_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_FOV_CHANGER_SWITCH_KEY"].toInt()) })
    fovSmoothingToggleKey = ObservableBoolean({ curSettings["FOV_SMOOTH_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["FOV_SMOOTH_SWITCH_KEY"].toInt()) })
    bombTimerToggleKey = ObservableBoolean({ curSettings["ENABLE_BOMB_TIMER_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_BOMB_TIMER_SWITCH_KEY"].toInt()) })
    bombTimerEnableBarsToggleKey = ObservableBoolean({ curSettings["BOMB_TIMER_BARS_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["BOMB_TIMER_BARS_SWITCH_KEY"].toInt()) })
    bombTimerEnableMenuToggleKey = ObservableBoolean({ curSettings["BOMB_TIMER_MENU_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["BOMB_TIMER_MENU_SWITCH_KEY"].toInt()) })
    spectatorListToggleKey = ObservableBoolean({ curSettings["SPECTATOR_LIST_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["SPECTATOR_LIST_SWITCH_KEY"].toInt()) })
    knifeBotToggleKey = ObservableBoolean({ curSettings["ENABLE_AUTO_KNIFE_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_AUTO_KNIFE_SWITCH_KEY"].toInt()) })
    doorSpamToggleKey = ObservableBoolean({ curSettings["D_SPAM_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["D_SPAM_SWITCH_KEY"].toInt()) })
    weaponSpamToggleKey = ObservableBoolean({ curSettings["W_SPAM_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["W_SPAM_SWITCH_KEY"].toInt()) })
    enableReducedFlashToggleKey = ObservableBoolean({ curSettings["ENABLE_REDUCED_FLASH_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_REDUCED_FLASH_SWITCH_KEY"].toInt()) })
    hitSoundCheckBoxToggleKey = ObservableBoolean({ curSettings["ENABLE_HITSOUND_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_HITSOUND_SWITCH_KEY"].toInt()) })
    radarEspToggleKey = ObservableBoolean({ curSettings["RADAR_ESP_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["RADAR_ESP_SWITCH_KEY"].toInt()) })
    nightModeToggleKey = ObservableBoolean({ curSettings["ENABLE_NIGHTMODE_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_NIGHTMODE_SWITCH_KEY"].toInt()) })
    visAdrenalineToggleKey = ObservableBoolean({ curSettings["ENABLE_ADRENALINE_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_ADRENALINE_SWITCH_KEY"].toInt()) })
    glowEspToggleKey = ObservableBoolean({ curSettings["GLOW_ESP_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["GLOW_ESP_SWITCH_KEY"].toInt()) })
    enableRCSToggleKey = ObservableBoolean({ curSettings["ENABLE_RCS_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_RCS_SWITCH_KEY"].toInt()) })
    enableAimToggleKey = ObservableBoolean({ curSettings["ENABLE_AIM_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_AIM_SWITCH_KEY"].toInt()) })
    enableTrigToggleKey = ObservableBoolean({ curSettings["ENABLE_TRIGGER_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_TRIGGER_SWITCH_KEY"].toInt()) })
    enableBacktrackToggleKey = ObservableBoolean({ curSettings["ENABLE_BACKTRACK_SWITCH_ON_KEY"].strToBool() &&  keyPressed(curSettings["ENABLE_BACKTRACK_SWITCH_KEY"].toInt()) })
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
    enableRCSToggleKey.update()
    if (enableRCSToggleKey.justBecameTrue) {
        rcsTab.enableRCS.checkBox.isChecked = !rcsTab.enableRCS.checkBox.isChecked
    }
    enableAimToggleKey.update()
    if (enableAimToggleKey.justBecameTrue) {
        aimTab.tAim.enableAim.checkBox.isChecked = !aimTab.tAim.enableAim.checkBox.isChecked
    }
    enableTrigToggleKey.update()
    if (enableTrigToggleKey.justBecameTrue) {
        aimTab.tTrig.enableTrig.checkBox.isChecked = !aimTab.tTrig.enableTrig.checkBox.isChecked
    }
    enableBacktrackToggleKey.update()
    if (enableBacktrackToggleKey.justBecameTrue) {
        aimTab.tBacktrack.enableBacktrack.checkBox.isChecked = !aimTab.tBacktrack.enableBacktrack.checkBox.isChecked
    }
}