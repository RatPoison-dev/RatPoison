package rat.poison.utils

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.scripts.forcedUpdate
import rat.poison.scripts.selfNade
import rat.poison.ui.tabs.*
import rat.poison.ui.uiPanels.*
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
var chamsEspToggleKey = ObservableBoolean({keyPressed(1) })
var indicatorEspToggleKey = ObservableBoolean({keyPressed(1) })
var skeletonEspToggleKey = ObservableBoolean({keyPressed(1) })
var enableFootStepsToggleKey = ObservableBoolean({keyPressed(1) })
var enableSnaplinesToggleKey = ObservableBoolean({keyPressed(1) })
var hitMarkerToggleKey = ObservableBoolean({keyPressed(1) })
var enableSkinChangerToggleKey = ObservableBoolean({keyPressed(1) })
var throwSelfNadeKey = ObservableBoolean({ keyPressed(1)})
var manualForceUpdateKey = ObservableBoolean({ keyPressed(1)})
var autoForceUpdateToggleKey = ObservableBoolean({ keyPressed(1)})

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
    skeletonEspToggleKey = ObservableBoolean({ curSettings["SKELETON_ESP_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["SKELETON_ESP_SWITCH_KEY"].toInt()) })
    chamsEspToggleKey = ObservableBoolean({ curSettings["CHAMS_ESP_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["CHAMS_ESP_SWITCH_KEY"].toInt()) })
    indicatorEspToggleKey = ObservableBoolean({ curSettings["INDICATOR_ESP_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["INDICATOR_ESP_SWITCH_KEY"].toInt()) })
    enableFootStepsToggleKey = ObservableBoolean({ curSettings["ENABLE_FOOTSTEPS_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["ENABLE_FOOTSTEPS_SWITCH_KEY"].toInt()) })
    hitMarkerToggleKey = ObservableBoolean({ curSettings["ENABLE_HITMARKER_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["ENABLE_HITMARKER_SWITCH_KEY"].toInt()) })
    enableSnaplinesToggleKey = ObservableBoolean({ curSettings["ENABLE_SNAPLINES_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["ENABLE_SNAPLINES_SWITCH_KEY"].toInt()) })
    enableSkinChangerToggleKey = ObservableBoolean({ curSettings["SKINCHANGER_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["SKINCHANGER_SWITCH_KEY"].toInt()) })
    autoForceUpdateToggleKey = ObservableBoolean({ curSettings["FORCE_UPDATE_AUTO_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["FORCE_UPDATE_AUTO_SWITCH_KEY"].toInt()) })
    throwSelfNadeKey = ObservableBoolean({ keyPressed(curSettings["THROW_SELF_NADE_KEY"].toInt()) })
    manualForceUpdateKey = ObservableBoolean({ keyPressed(curSettings["MANUAL_FORCE_UPDATE_KEY"].toInt()) })
}
fun addListeners() {
    bunnyHopToggleKey.update()
    if (bunnyHopToggleKey.justBecameTrue) {
        miscTab.bunnyHop.isChecked = !miscTab.bunnyHop.isChecked
    }
    boxEspToggleKey.update()
    if (boxEspToggleKey.justBecameTrue) {
        boxEspTab.boxEsp.isChecked = !boxEspTab.boxEsp.isChecked
    }
    autoStrafeToggleKey.update()
    if (autoStrafeToggleKey.justBecameTrue) {
        miscTab.autoStrafe.isChecked = !miscTab.autoStrafe.isChecked
    }

    autoStrafeBHopOnlyToggleKey.update()
    if (autoStrafeBHopOnlyToggleKey.justBecameTrue) {
        miscTab.autoStrafeBHopOnly.isChecked = !miscTab.autoStrafeBHopOnly.isChecked
    }

    fastStopToggleKey.update()
    if (fastStopToggleKey.justBecameTrue) {
        miscTab.fastStop.isChecked = !miscTab.fastStop.isChecked
    }

    aimStraferToggleKey.update()
    if (aimStraferToggleKey.justBecameTrue) {
        miscTab.aimStrafer.isChecked = !miscTab.aimStrafer.isChecked
    }

    fovChangerToggleKey.update()
    if (fovChangerToggleKey.justBecameTrue) {
        miscTab.fovChanger.isChecked = !miscTab.fovChanger.isChecked
    }

    fovSmoothingToggleKey.update()
    if (fovSmoothingToggleKey.justBecameTrue) {
        miscTab.fovSmoothing.isChecked = !miscTab.fovSmoothing.isChecked
    }

    bombTimerToggleKey.update()
    if (bombTimerToggleKey.justBecameTrue) {
        miscTab.bombTimer.isChecked = !miscTab.bombTimer.isChecked
    }

    bombTimerEnableBarsToggleKey.update()
    if (bombTimerEnableBarsToggleKey.justBecameTrue) {
        miscTab.bombTimerEnableBars.isChecked = !miscTab.bombTimerEnableBars.isChecked
    }

    bombTimerEnableMenuToggleKey.update()
    if (bombTimerEnableMenuToggleKey.justBecameTrue) {
        miscTab.bombTimerEnableMenu.isChecked = !miscTab.bombTimerEnableMenu.isChecked
    }

    spectatorListToggleKey.update()
    if (spectatorListToggleKey.justBecameTrue) {
        miscTab.spectatorList.isChecked = !miscTab.spectatorList.isChecked
    }

    knifeBotToggleKey.update()
    if (knifeBotToggleKey.justBecameTrue) {
        miscTab.knifeBot.isChecked = !miscTab.knifeBot.isChecked
    }

    doorSpamToggleKey.update()
    if (doorSpamToggleKey.justBecameTrue) {
        miscTab.doorSpam.isChecked = !miscTab.doorSpam.isChecked
    }

    weaponSpamToggleKey.update()
    if (weaponSpamToggleKey.justBecameTrue) {
        miscTab.weaponSpam.isChecked = !miscTab.weaponSpam.isChecked
    }

    enableReducedFlashToggleKey.update()
    if (enableReducedFlashToggleKey.justBecameTrue) {
        miscTab.enableReducedFlash.isChecked = !miscTab.enableReducedFlash.isChecked
    }

    hitSoundCheckBoxToggleKey.update()
    if (hitSoundCheckBoxToggleKey.justBecameTrue) {
        miscTab.hitSoundCheckBox.isChecked = !miscTab.hitSoundCheckBox.isChecked
    }


    radarEspToggleKey.update()
    if (radarEspToggleKey.justBecameTrue) {
        visualsTab.radarEsp.isChecked = !visualsTab.radarEsp.isChecked
    }


    nightModeToggleKey.update()
    if (nightModeToggleKey.justBecameTrue) {
        visualsTab.nightMode.isChecked = !visualsTab.nightMode.isChecked
    }


    visAdrenalineToggleKey.update()
    if (visAdrenalineToggleKey.justBecameTrue) {
        visualsTab.visAdrenaline.isChecked = !visualsTab.visAdrenaline.isChecked
    }
    glowEspToggleKey.update()
    if (glowEspToggleKey.justBecameTrue) {
        glowEspTab.glowEsp.isChecked = !glowEspTab.glowEsp.isChecked
    }
    enableRCSToggleKey.update()
    if (enableRCSToggleKey.justBecameTrue) {
        rcsTab.enableRCS.isChecked = !rcsTab.enableRCS.isChecked
    }
    enableAimToggleKey.update()
    if (enableAimToggleKey.justBecameTrue) {
        aimTab.tAim.enableAim.isChecked = !aimTab.tAim.enableAim.isChecked
    }
    enableTrigToggleKey.update()
    if (enableTrigToggleKey.justBecameTrue) {
        aimTab.tTrig.enableTrig.isChecked = !aimTab.tTrig.enableTrig.isChecked
    }
    enableBacktrackToggleKey.update()
    if (enableBacktrackToggleKey.justBecameTrue) {
        aimTab.tBacktrack.enableBacktrack.isChecked = !aimTab.tBacktrack.enableBacktrack.isChecked
    }
    chamsEspToggleKey.update()
    if (chamsEspToggleKey.justBecameTrue) {
        chamsEspTab.chamsEsp.isChecked = !chamsEspTab.chamsEsp.isChecked
    }
    indicatorEspToggleKey.update()
    if (indicatorEspToggleKey.justBecameTrue) {
        indicatorEspTab.indicatorEsp.isChecked = !indicatorEspTab.indicatorEsp.isChecked
    }
    skeletonEspToggleKey.update()
    if (skeletonEspToggleKey.justBecameTrue) {
        boxEspTab.skeletonEsp.isChecked = !boxEspTab.skeletonEsp.isChecked
    }
    enableFootStepsToggleKey.update()
    if (enableFootStepsToggleKey.justBecameTrue) {
        footStepsEspTab.enableFootSteps.isChecked = !footStepsEspTab.enableFootSteps.isChecked
    }
    enableSnaplinesToggleKey.update()
    if (enableSnaplinesToggleKey.justBecameTrue) {
        snaplinesEspTab.enableSnaplines.isChecked = !snaplinesEspTab.enableSnaplines.isChecked
    }
    hitMarkerToggleKey.update()
    if (hitMarkerToggleKey.justBecameTrue) {
        hitMarkerTab.hitMarker.isChecked = !hitMarkerTab.hitMarker.isChecked
    }
    autoForceUpdateToggleKey.update()
    if (autoForceUpdateToggleKey.justBecameTrue) {
        skinChangerTab.autoForceUpdate.isChecked = !skinChangerTab.autoForceUpdate.isChecked
    }
    enableSkinChangerToggleKey.update()
    if (enableSkinChangerToggleKey.justBecameTrue) {
        skinChangerTab.enableSkinChanger.isChecked = !skinChangerTab.enableSkinChanger.isChecked
    }
    throwSelfNadeKey.update()
    if (throwSelfNadeKey.justBecameTrue) {
        selfNade()
    }
    manualForceUpdateKey.update()
    if (manualForceUpdateKey.justBecameTrue) {
        forcedUpdate()
    }
}