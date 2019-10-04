package rat.poison.ui

import com.badlogic.gdx.graphics.Color
import rat.poison.*
import rat.poison.App.uiAimOverridenWeapons
import rat.poison.App.uiMenu
import rat.poison.settings.CHEST_BONE
import rat.poison.settings.HEAD_BONE
import rat.poison.settings.NECK_BONE
import rat.poison.settings.STOMACH_BONE
import rat.poison.ui.tabs.*
import rat.poison.ui.tabs.visualstabs.*

fun uiUpdate() {
    if (!opened) return

    //Aim Tab
//    aimTab.tAim.apply {
//        enableAim.update()
//        activateFromFireKey.update()
//        teammatesAreEnemies.update()
//        forceAimKey.update()
//        automaticWeaponsCheckBox.update()
//        automaticWeaponsInput.update()
//        targetSwapDelay.update()
//
//        enableFactorRecoil.update()
//        enableFlatAim.update()
//        enablePathAim.update()
//
//        enableScopedOnly.update()
//        if (categorySelected == "SNIPER") {
//            enableScopedOnly.color = Color(255F, 255F, 255F, 1F)
//            enableScopedOnly.isDisabled = false
//        } else {
//            enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
//            enableScopedOnly.isDisabled = true
//        }
//        aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"].toInt()) {
//            HEAD_BONE -> "HEAD"
//            NECK_BONE -> "NECK"
//            CHEST_BONE -> "CHEST"
//            STOMACH_BONE -> "STOMACH"
//            else -> "NEAREST"
//        }
//        aimFov.update()
//        aimSpeed.update()
//        aimSmooth.update()
//        aimStrict.update()
//        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
//        perfectAimCollapsible.isCollapsed = !curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
//        perfectAimFov.update()
//        perfectAimChance.update()
//
//        updateDisableAim()
//    }

//    aimTab.tTrig.apply {
//        enableTrig.update()
//        enableTrig.update()
//        boneTriggerEnableKey.update()
//        boneTriggerKey.update()
//        trigInCross.update()
//        trigInFov.update()
//        trigFov.update()
//        trigAimbot.update()
//        trigDelay.update()
//
//        updateDisableTrig()
//    }

    overridenWeapons.apply {
        val curWep = curSettings[overridenWeapons.weaponOverrideSelected].toWeaponClass()

        overridenWeapons.weaponOverrideEnableCheckBox.isChecked = curWep.tOverride
        enableFactorRecoil.isChecked = curWep.tFRecoil
        enableFlatAim.isChecked = curWep.tFlatAim
        enablePathAim.isChecked = curWep.tPathAim
        enableScopedOnly.isChecked = curWep.tScopedOnly
        if (categorySelected == "SNIPER") {
            enableScopedOnly.color = Color(255F, 255F, 255F, 1F)
            enableScopedOnly.isDisabled = false
        } else {
            enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
            enableScopedOnly.isDisabled = true
        }
        aimBoneBox.selected = when (curWep.tAimBone) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            else -> "NEAREST"
        }
        aimFovLabel.setText("FOV: " + curWep.tAimFov)
        aimFovSlider.value = curWep.tAimFov.toFloat()
        aimSpeedLabel.setText("Speed: " + curWep.tAimSpeed)
        aimSpeedSlider.value = curWep.tAimSpeed.toFloat()
        aimSmoothnessLabel.setText("Smooth: " + curWep.tAimSmooth)
        aimSmoothnessSlider.value = curWep.tAimSmooth.toFloat()
        aimStrictnessLabel.setText("Strictness: " + curWep.tAimStrict)
        aimStrictnessSlider.value = curWep.tAimStrict.toFloat()
        perfectAimCheckBox.isChecked = curWep.tPerfectAim
        perfectAimCollapsible.isCollapsed = !curWep.tPerfectAim
        perfectAimFovLabel.setText("FOV: " + curWep.tPAimFov)
        perfectAimFovSlider.value = curWep.tPAimFov.toFloat()
        perfectAimChanceLabel.setText("Chance: " + curWep.tPAimChance)
        perfectAimChanceSlider.value = curWep.tPAimChance.toFloat()
    }

    visualsTabUpdate()
    glowEspTabUpdate()
    chamsEspTabUpdate()
    indicatorEspTabUpdate()
    boxEspTabUpdate()
    skeletonEspTabUpdate()
    hitMarkerTabUpdate()
    nadesVTUpdate()
    miscTabUpdate()
    rcsTabUpdate()
    nadeHelperTabUpdate()
    updateTrig()
    updateAim()

    //Update windows
    uiAimOverridenWeapons.setPosition(uiMenu.x+uiMenu.width+4F, uiMenu.y)
}