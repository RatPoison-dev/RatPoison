package rat.poison.utils

import org.jire.arrowhead.keyPressed
import rat.poison.checkFlags
import rat.poison.curSettings
import rat.poison.scripts.disableFovChanger
import rat.poison.scripts.disableReducedFlash
import rat.poison.scripts.esp.disableGlowAndChams
import rat.poison.scripts.forcedUpdate
import rat.poison.scripts.selfNade
import rat.poison.ui.tabs.*
import rat.poison.ui.uiPanels.*
import rat.poison.utils.varUtil.strToBool

var bunnyHopToggleKey = ObservableBoolean({ curSettings["ENABLE_BUNNY_HOP_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["ENABLE_BUNNY_HOP_KEY"].toInt()) })
var boxEspToggleKey = ObservableBoolean({ curSettings["ENABLE_BOX_ESP_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["ENABLE_BOX_ESP_KEY"].toInt()) } )
var autoStrafeToggleKey = ObservableBoolean({ curSettings["AUTO_STRAFE_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["AUTO_STRAFE_KEY"].toInt()) })
var autoStrafeBHopOnlyToggleKey = ObservableBoolean({ curSettings["STRAFE_BHOP_ONLY_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["STRAFE_BHOP_ONLY_KEY"].toInt()) })
var fastStopToggleKey = ObservableBoolean({ curSettings["FAST_STOP_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["FAST_STOP_KEY"].toInt()) })
var aimStraferToggleKey = ObservableBoolean({ curSettings["AIM_STRAFER_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["AIM_STRAFER_KEY"].toInt()) })
var fovChangerToggleKey = ObservableBoolean({ curSettings["ENABLE_FOV_CHANGER_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_FOV_CHANGER_KEY"].toInt()) })
var fovSmoothingToggleKey = ObservableBoolean({ curSettings["FOV_SMOOTH_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["FOV_SMOOTH_KEY"].toInt()) })
var bombTimerToggleKey = ObservableBoolean({ curSettings["ENABLE_BOMB_TIMER_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_BOMB_TIMER_KEY"].toInt()) })
var bombTimerEnableBarsToggleKey = ObservableBoolean({ curSettings["BOMB_TIMER_BARS_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["BOMB_TIMER_BARS_KEY"].toInt()) })
var bombTimerEnableMenuToggleKey = ObservableBoolean({ curSettings["BOMB_TIMER_MENU_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["BOMB_TIMER_MENU_KEY"].toInt()) })
var spectatorListToggleKey = ObservableBoolean({ curSettings["SPECTATOR_LIST_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["SPECTATOR_LIST_KEY"].toInt()) })
var knifeBotToggleKey = ObservableBoolean({ curSettings["ENABLE_AUTO_KNIFE_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_AUTO_KNIFE_KEY"].toInt()) })
var doorSpamToggleKey = ObservableBoolean({ curSettings["D_SPAM_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["D_SPAM_KEY"].toInt()) })
var weaponSpamToggleKey = ObservableBoolean({ curSettings["W_SPAM_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["W_SPAM_KEY"].toInt()) })
var enableReducedFlashToggleKey = ObservableBoolean({ curSettings["ENABLE_REDUCED_FLASH_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_REDUCED_FLASH_KEY"].toInt()) })
var hitSoundCheckBoxToggleKey = ObservableBoolean({ curSettings["ENABLE_HITSOUND_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_HITSOUND_KEY"].toInt()) })
var radarEspToggleKey = ObservableBoolean({ curSettings["RADAR_ESP_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["RADAR_ESP_KEY"].toInt()) })
var nightModeToggleKey = ObservableBoolean({ curSettings["ENABLE_NIGHTMODE_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_NIGHTMODE_KEY"].toInt()) })
var visAdrenalineToggleKey = ObservableBoolean({ curSettings["ENABLE_ADRENALINE_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_ADRENALINE_KEY"].toInt()) })
var glowEspToggleKey = ObservableBoolean({ curSettings["GLOW_ESP_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["GLOW_ESP_KEY"].toInt()) })
var enableRCSToggleKey = ObservableBoolean({ curSettings["ENABLE_RCS_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_RCS_KEY"].toInt()) })
var enableAimToggleKey = ObservableBoolean({ curSettings["ENABLE_AIM_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_AIM_KEY"].toInt()) })
var enableTrigToggleKey = ObservableBoolean({ curSettings["ENABLE_TRIGGER_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_TRIGGER_KEY"].toInt()) })
var enableBacktrackToggleKey = ObservableBoolean({ curSettings["ENABLE_BACKTRACK_KEY_TYPE"] == "SwitchKey" &&  keyPressed(curSettings["ENABLE_BACKTRACK_KEY"].toInt()) })
var skeletonEspToggleKey = ObservableBoolean({ curSettings["SKELETON_ESP_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["SKELETON_ESP_KEY"].toInt()) })
var chamsEspToggleKey = ObservableBoolean({ curSettings["CHAMS_ESP_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["CHAMS_ESP_KEY"].toInt()) })
var indicatorEspToggleKey = ObservableBoolean({ curSettings["INDICATOR_ESP_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["INDICATOR_ESP_KEY"].toInt()) })
var enableFootStepsToggleKey = ObservableBoolean({ curSettings["ENABLE_FOOTSTEPS_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["ENABLE_FOOTSTEPS_KEY"].toInt()) })
var hitMarkerToggleKey = ObservableBoolean({ curSettings["ENABLE_HITMARKER_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["ENABLE_HITMARKER_KEY"].toInt()) })
var enableSnaplinesToggleKey = ObservableBoolean({ curSettings["ENABLE_SNAPLINES_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["ENABLE_SNAPLINES_KEY"].toInt()) })
var enableSkinChangerToggleKey = ObservableBoolean({ curSettings["SKINCHANGER_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["SKINCHANGER_KEY"].toInt()) })
var autoForceUpdateToggleKey = ObservableBoolean({ curSettings["FORCE_UPDATE_AUTO_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["FORCE_UPDATE_AUTO_KEY"].toInt()) })
var enableThrowingHelperToggleKey = ObservableBoolean({ curSettings["ENABLE_NADE_THROWER_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["ENABLE_NADE_THROWER_KEY"].toInt()) })
var noSmokeToggleKey = ObservableBoolean({ curSettings["ENABLE_NO_SMOKE_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["ENABLE_NO_SMOKE_KEY"].toInt()) })
var headWalkToggleKey = ObservableBoolean({ curSettings["HEAD_WALK_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["HEAD_WALK_KEY"].toInt()) })
var throwSelfNadeKey = ObservableBoolean({ keyPressed(curSettings["THROW_SELF_NADE_KEY"].toInt()) })
var manualForceUpdateKey = ObservableBoolean({ keyPressed(curSettings["MANUAL_FORCE_UPDATE_KEY"].toInt()) })
var glowEspFlags = ObservableBoolean({curSettings["GLOW_ESP"].strToBool() && checkFlags("GLOW_ESP")})
var chamsEspFlags =  ObservableBoolean({curSettings["CHAMS_ESP"].strToBool() && checkFlags("CHAMS_ESP")})
var fovChangerFlags = ObservableBoolean({curSettings["ENABLE_FOV_CHANGER"].strToBool() && checkFlags("ENABLE_FOV_CHANGER")})
var reducedFlashFlags = ObservableBoolean({curSettings["ENABLE_REDUCED_FLASH"].strToBool() && checkFlags("ENABLE_REDUCED_FLASH")})

fun callUpdates() {
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

    noSmokeToggleKey.update()
    if (noSmokeToggleKey.justBecameTrue) {
        miscTab.noSmoke.isChecked = !miscTab.noSmoke.isChecked
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
    headWalkToggleKey.update()
    if (headWalkToggleKey.justBecameTrue) {
        miscTab.headWalk.isChecked = !miscTab.headWalk.isChecked
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
    glowEspFlags.update()
    if (glowEspFlags.justBecameFalse) {
        disableGlowAndChams()
    }
    chamsEspFlags.update()
    if (chamsEspFlags.justBecameFalse) {
        disableGlowAndChams()
    }
    fovChangerFlags.update()
    if (fovChangerFlags.justBecameFalse) {
        disableFovChanger()
    }
    reducedFlashFlags.update()
    if (reducedFlashFlags.justBecameFalse) {
        disableReducedFlash()
    }
    manualForceUpdateKey.update()
    if (manualForceUpdateKey.justBecameTrue) {
        forcedUpdate()
    }
    enableThrowingHelperToggleKey.update()
    if (enableThrowingHelperToggleKey.justBecameTrue) {
        nadeHelperTab.enableThrowingHelper.isChecked = !nadeHelperTab.enableThrowingHelper.isChecked
    }
}