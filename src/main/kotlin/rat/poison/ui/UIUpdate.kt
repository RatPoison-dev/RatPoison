package rat.poison.ui

import com.badlogic.gdx.graphics.Color
import rat.poison.*
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
        aimKey.update()
        forceAimKey.update()
        automaticWeaponsCheckBox.update()
        automaticWeaponsInput.update()

        val curWep = convStrToArray(curSettings[weaponOverrideSelected])

        if (weaponOverride) {
            weaponOverrideEnableCheckBox.isChecked = curWep[0]!!.toBool()
            enableFactorRecoil.isChecked = curWep[1]!!.toBool()
            enableFlatAim.isChecked = curWep[2]!!.toBool()
            enablePathAim.isChecked = curWep[3]!!.toBool()
            enableScopedOnly.isChecked = curWep[12]!!.toBool()
            if (categorySelected == "SNIPER") {
                enableScopedOnly.color = Color(255F, 255F, 255F, 1F)
                enableScopedOnly.isDisabled = false
            } else {
                enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
                enableScopedOnly.isDisabled = true
            }
            aimBoneBox.selected = when (curWep[4]!!.toInt()) {
                HEAD_BONE -> "HEAD"
                NECK_BONE -> "NECK"
                CHEST_BONE -> "CHEST"
                STOMACH_BONE -> "STOMACH"
                else -> "NEAREST"
            }
            aimFovLabel.setText("FOV: " + curWep[5]!!.toInt() + when (curWep[5]!!.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            aimFovSlider.value = curWep[5]!!.toInt().toFloat()
            aimSpeedLabel.setText("Speed: " + curWep[6]!!.toInt() + when (curWep[6]!!.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            aimSpeedSlider.value = curWep[6]!!.toInt().toFloat()
            aimSmoothnessLabel.setText("Smoothness: " + curWep[7]!!.toFloat())
            aimSmoothnessSlider.value = curWep[7]!!.toFloat()
            aimStrictnessLabel.setText("Strictness: " + curWep[8]!!.toFloat())
            aimStrictnessSlider.value = curWep[8]!!.toFloat()
            perfectAimCheckBox.isChecked = curWep[9]!!.toBool()
            perfectAimCollapsible.isCollapsed = !curWep[9]!!.toBool()
            perfectAimFovLabel.setText("FOV: " + curWep[10]!!.toInt() + when (curWep[10]!!.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            perfectAimFovSlider.value = curWep[10]!!.toInt().toFloat()
            perfectAimChanceLabel.setText("Chance: " + curWep[11]!!.toInt() + when (curWep[11]!!.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            perfectAimChanceSlider.value = curWep[11]!!.toFloat()
        }
        else
        {
            weaponOverrideEnableCheckBox.isChecked = curWep[0]!!.toBool()
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
        }

        updateDisableAim()
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
}