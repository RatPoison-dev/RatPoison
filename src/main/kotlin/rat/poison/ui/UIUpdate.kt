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
    aimTab.apply {
        enableAim.update()
        activateFromFireKey.update()
        teammatesAreEnemies.update()
        forceAimKey.update()
        automaticWeaponsCheckBox.update()
        automaticWeaponsInput.update()

        enableFactorRecoil.isChecked = curSettings[categorySelected + "_FACTOR_RECOIL"].strToBool()
        enableFlatAim.isChecked = curSettings[categorySelected + "_ENABLE_FLAT_AIM"].strToBool()
        enablePathAim.isChecked = curSettings[categorySelected + "_ENABLE_PATH_AIM"].strToBool()
        enableScopedOnly.isChecked = curSettings["SNIPER_ENABLE_SCOPED_ONLY"].strToBool()
        if (categorySelected == "SNIPER") {
            enableScopedOnly.color = Color(255F, 255F, 255F, 1F)
            enableScopedOnly.isDisabled = false
        } else {
            enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
            enableScopedOnly.isDisabled = true
        }
        aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"].toInt()) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            else -> "NEAREST"
        }
        aimFovLabel.setText("FOV: " + curSettings[categorySelected + "_AIM_FOV"].toInt() + when (curSettings[categorySelected + "_AIM_FOV"].length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        aimFovSlider.value = curSettings[categorySelected + "_AIM_FOV"].toInt().toFloat()
        aimSpeedLabel.setText("Speed: " + curSettings[categorySelected + "_AIM_SPEED"].toInt() + when (curSettings[categorySelected + "_AIM_SPEED"].length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        aimSpeedSlider.value = curSettings[categorySelected + "_AIM_SPEED"].toInt().toFloat()
        aimSmoothnessLabel.setText("Smoothness: " + curSettings[categorySelected + "_AIM_SMOOTHNESS"].toFloat())
        aimSmoothnessSlider.value = curSettings[categorySelected + "_AIM_SMOOTHNESS"].toFloat()
        aimStrictnessLabel.setText("Strictness: " + curSettings[categorySelected + "_AIM_STRICTNESS"])
        aimStrictnessSlider.value = curSettings[categorySelected + "_AIM_STRICTNESS"].toFloat()
        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimCollapsible.isCollapsed = !curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimFovLabel.setText("FOV: " + curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt() + when (curSettings[categorySelected + "_PERFECT_AIM_FOV"].length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        perfectAimFovSlider.value = curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt().toFloat()
        perfectAimChanceLabel.setText("Chance: " + curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toInt() + when (curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        perfectAimChanceSlider.value = curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toInt().toFloat()

        updateDisableAim()
    }

    overridenWeapons.apply {
        val curWep = curSettings[overridenWeapons.weaponOverrideSelected].toWeaponClass()

        //if (curSettings["ENABLE_OVERRIDE"].strToBool()) {
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
        //}
    }

    visualsTabUpdate()
    glowEspTabUpdate()
    chamsEspTabUpdate()
    indicatorEspTabUpdate()
    boxEspTabUpdate()
    skeletonEspTabUpdate()
    hitMarkerTabUpdate()
    nadesVTUpdate()
    bTrigTabUpdate()
    miscTabUpdate()
    rcsTabUpdate()
    nadeHelperTabUpdate()

    //Update windows
    uiAimOverridenWeapons.setPosition(uiMenu.x+uiMenu.width+4F, uiMenu.y)
}