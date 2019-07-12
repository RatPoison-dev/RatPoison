package rat.poison.ui

import com.badlogic.gdx.graphics.Color
import rat.poison.*
import rat.poison.scripts.esp.disableEsp
import rat.poison.settings.*
import rat.poison.ui.tabs.*

fun uiUpdate() {
    if (!opened) return

    //Aim Tab
    aimTab.apply {
        enableAim.isChecked = curSettings["ENABLE_AIM"]!!.strToBool()
        activateFromFireKey.isChecked = curSettings["ACTIVATE_FROM_AIM_KEY"]!!.strToBool()
        teammatesAreEnemies.isChecked = curSettings["TEAMMATES_ARE_ENEMIES"]!!.strToBool()
        aimKeyField.text = curSettings["AIM_KEY"]
        forceAimKeyField.text = curSettings["FORCE_AIM_KEY"]
        automaticWeaponsCheckBox.isChecked = curSettings["AUTOMATIC_WEAPONS"]!!.strToBool()
        automaticWeaponsLabel.setText("MS Delay: ")
        automaticWeaponsField.text = curSettings["AUTO_WEP_DELAY"]

        val curWep = convStrToArray(curSettings[weaponOverrideSelected])

        if (weaponOverride) {
            weaponOverrideEnableCheckBox.isChecked = curWep[0]!!.toBool()
            enableFactorRecoil.isChecked = curWep[1]!!.toBool()
            enableFlatAim.isChecked = curWep[2]!!.toBool()
            enablePathAim.isChecked = curWep[3]!!.toBool()
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
            enableFactorRecoil.isChecked = curSettings[categorySelected + "_FACTOR_RECOIL"]!!.strToBool()
            enableFlatAim.isChecked = curSettings[categorySelected + "_ENABLE_FLAT_AIM"]!!.strToBool()
            enablePathAim.isChecked = curSettings[categorySelected + "_ENABLE_PATH_AIM"]!!.strToBool()
            aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"]!!.toInt()) {
                HEAD_BONE -> "HEAD"
                NECK_BONE -> "NECK"
                CHEST_BONE -> "CHEST"
                STOMACH_BONE -> "STOMACH"
                else -> "NEAREST"
            }
            aimFovLabel.setText("FOV: " + curSettings[categorySelected + "_AIM_FOV"]!!.toInt() + when (curSettings[categorySelected + "_AIM_FOV"]!!.length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            aimFovSlider.value = curSettings[categorySelected + "_AIM_FOV"]!!.toInt().toFloat()
            aimSpeedLabel.setText("Speed: " + curSettings[categorySelected + "_AIM_SPEED"]!!.toInt() + when (curSettings[categorySelected + "_AIM_SPEED"]!!.length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            aimSpeedSlider.value = curSettings[categorySelected + "_AIM_SPEED"]!!.toInt().toFloat()
            aimSmoothnessLabel.setText("Smoothness: " + curSettings[categorySelected + "_AIM_SMOOTHNESS"]!!.toFloat())
            aimSmoothnessSlider.value = curSettings[categorySelected + "_AIM_SMOOTHNESS"]!!.toFloat()
            aimStrictnessLabel.setText("Strictness: " + curSettings[categorySelected + "_AIM_STRICTNESS"])
            aimStrictnessSlider.value = curSettings[categorySelected + "_AIM_STRICTNESS"]!!.toFloat()
            perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"]!!.strToBool()
            perfectAimCollapsible.isCollapsed = !curSettings[categorySelected + "_PERFECT_AIM"]!!.strToBool()
            perfectAimFovLabel.setText("FOV: " + curSettings[categorySelected + "_PERFECT_AIM_FOV"]!!.toInt() + when (curSettings[categorySelected + "_PERFECT_AIM_FOV"]!!.length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            perfectAimFovSlider.value = curSettings[categorySelected + "_PERFECT_AIM_FOV"]!!.toInt().toFloat()
            perfectAimChanceLabel.setText("Chance: " + curSettings[categorySelected + "_PERFECT_AIM_CHANCE"]!!.toInt() + when (curSettings[categorySelected + "_PERFECT_AIM_CHANCE"]!!.length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            perfectAimChanceSlider.value = curSettings[categorySelected + "_PERFECT_AIM_CHANCE"]!!.toInt().toFloat()
        }

        //V--Disable/enable entire tab--V\\
        val bool = !enableAim.isChecked
        var color = Color(255F, 255F, 255F, 1F)
        if (bool) {
            color = Color(105F, 105F, 105F, .2F)
        }
        activateFromFireKey.isDisabled = bool
        teammatesAreEnemies.isDisabled = bool
        automaticWeaponsCheckBox.isDisabled = bool
        weaponOverrideCheckBox.isDisabled = bool
        automaticWeaponsCheckBox.isDisabled = bool
        automaticWeaponsLabel.color = color
        automaticWeaponsField.isDisabled = bool
        aimKeyLabel.color = color
        aimKeyField.isDisabled = bool
        forceAimKeyLabel.color = color
        forceAimKeyField.isDisabled = bool
        categorySelectLabel.color = color
        categorySelectionBox.isDisabled = bool
        weaponOverrideSelectionBox.isDisabled = bool
        if (!weaponOverride) {
            weaponOverrideEnableCheckBox.isDisabled = true
        } else {
            weaponOverrideEnableCheckBox.isDisabled = bool
        }
        enableFactorRecoil.isDisabled = bool
        enableFlatAim.isDisabled = bool
        enablePathAim.isDisabled = bool
        aimBoneLabel.color = color
        aimBoneBox.isDisabled = bool
        aimFovLabel.color = color
        aimFovSlider.isDisabled = bool
        aimSpeedLabel.color = color
        aimSpeedSlider.isDisabled = bool
        aimSmoothnessLabel.color = color
        aimSmoothnessSlider.isDisabled = bool
        aimStrictnessLabel.color = color
        aimStrictnessSlider.isDisabled = bool
        perfectAimCheckBox.isDisabled = bool
        perfectAimChanceLabel.color = color
        perfectAimChanceSlider.isDisabled = bool
        perfectAimFovLabel.color = color
        perfectAimFovSlider.isDisabled = bool
        bTrigTab.aimOnBoneTrigger.isDisabled = bool
    }

    visualsTab.apply {
        enableEsp.isChecked = curSettings["ENABLE_ESP"]!!.strToBool()
        radarEsp.isChecked = curSettings["RADAR_ESP"]!!.strToBool()
        visualsToggleKeyField.text = curSettings["VISUALS_TOGGLE_KEY"]
        var col = curSettings["TEAM_COLOR"]!!.strToColor()
        teamColorShow.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        col = curSettings["ENEMY_COLOR"]!!.strToColor()
        enemyColorShow.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        col = curSettings["BOMB_COLOR"]!!.strToColor()
        bombColorShow.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        col = curSettings["WEAPON_COLOR"]!!.strToColor()
        weaponColorShow.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        col = curSettings["GRENADE_COLOR"]!!.strToColor()
        grenadeColorShow.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)
        col = curSettings["HIGHLIGHT_COLOR"]!!.strToColor()
        highlightColorShow.setColor(col.red.toFloat(), col.green.toFloat(), col.blue.toFloat(), 1F)

        //V--Disable/enable entire tab--V\\
        val bool = !enableEsp.isChecked
        var color = Color(255F, 255F, 255F, 1F)
        if (bool) {
            color = Color(105F, 105F, 105F, .2F)
        }

        visualsToggleKeyLabel.color = color
        visualsToggleKeyField.isDisabled = bool
        radarEsp.isDisabled = bool

        val recTab = espTabbedPane.activeTab
        espTabbedPane.disableTab(glowEspTab, bool)
        espTabbedPane.disableTab(chamsEspTab, bool)
        espTabbedPane.disableTab(indicatorEspTab, bool)
        espTabbedPane.disableTab(boxEspTab, bool)
        espTabbedPane.disableTab(skeletonEspTab, bool)
        espTabbedPane.switchTab(recTab)

        glowEspTab.glowEsp.isDisabled = bool
        glowEspTab.invGlowEsp.isDisabled = bool
        glowEspTab.modelEsp.isDisabled = bool
        glowEspTab.modelAndGlow.isDisabled = bool

        glowEspTab.showTeam.isDisabled = bool
        glowEspTab.showEnemies.isDisabled = bool
        glowEspTab.showBomb.isDisabled = bool
        glowEspTab.showWeapons.isDisabled = bool
        glowEspTab.showGrenades.isDisabled = bool
        glowEspTab.showTarget.isDisabled = bool

        chamsEspTab.chamsEsp.isDisabled = bool
        chamsEspTab.chamsShowHealth.isDisabled = bool
        chamsEspTab.chamsBrightnessLabel.color = color
        chamsEspTab.chamsBrightnessSlider.isDisabled = bool

        chamsEspTab.showTeam.isDisabled = bool
        chamsEspTab.showEnemies.isDisabled = bool

        indicatorEspTab.indicatorEsp.isDisabled = bool
        indicatorEspTab.indicatorOnScreen.isDisabled = bool
        indicatorEspTab.indicatorOval.isDisabled = bool
        indicatorEspTab.indicatorDistanceLabel.color = color
        indicatorEspTab.indicatorDistanceSlider.isDisabled = bool

        indicatorEspTab.showTeam.isDisabled = bool
        indicatorEspTab.showEnemies.isDisabled = bool
        indicatorEspTab.showBomb.isDisabled = bool
        indicatorEspTab.showWeapons.isDisabled = bool
        indicatorEspTab.showGrenades.isDisabled = bool

        boxEspTab.boxEsp.isDisabled = bool
        boxEspTab.boxEspDetails.isDisabled = bool

        boxEspTab.showTeam.isDisabled = bool
        boxEspTab.showEnemies.isDisabled = bool

        skeletonEspTab.skeletonEsp.isDisabled = bool
        skeletonEspTab.showTeam.isDisabled = bool
        skeletonEspTab.showEnemies.isDisabled = bool

        teamColorShow.isDisabled = bool
        enemyColorShow.isDisabled = bool
        bombColorShow.isDisabled = bool
        weaponColorShow.isDisabled = bool
        grenadeColorShow.isDisabled = bool
        highlightColorShow.isDisabled = bool

        if (!enableEsp.isChecked)
        {
            disableEsp()
        }
    }

    glowEspTab.apply {
        glowEsp.isChecked = curSettings["GLOW_ESP"]!!.strToBool()
        invGlowEsp.isChecked = curSettings["INV_GLOW_ESP"]!!.strToBool()
        modelEsp.isChecked = curSettings["MODEL_ESP"]!!.strToBool()
        modelAndGlow.isChecked = curSettings["MODEL_AND_GLOW"]!!.strToBool()
        showTeam.isChecked = curSettings["GLOW_SHOW_TEAM"]!!.strToBool()
        showEnemies.isChecked = curSettings["GLOW_SHOW_ENEMIES"]!!.strToBool()
        showBomb.isChecked = curSettings["GLOW_SHOW_BOMB"]!!.strToBool()
        showWeapons.isChecked = curSettings["GLOW_SHOW_WEAPONS"]!!.strToBool()
        showGrenades.isChecked = curSettings["GLOW_SHOW_GRENADES"]!!.strToBool()
        showTarget.isChecked = curSettings["GLOW_SHOW_TARGET"]!!.strToBool()

        if (invGlowEsp.isChecked || modelEsp.isChecked) {
            glowEsp.isChecked = true
            glowEsp.isDisabled = true
        }

        if (modelAndGlow.isChecked) {
            modelEsp.isChecked = true
            modelEsp.isDisabled = true
        }
    }

    chamsEspTab.apply {
        chamsEsp.isChecked = curSettings["CHAMS_ESP"]!!.strToBool()
        chamsShowHealth.isChecked = curSettings["CHAMS_SHOW_HEALTH"]!!.strToBool()
        chamsBrightnessLabel.setText("Chams Brightness: " + curSettings["CHAMS_BRIGHTNESS"]!!.toInt() + when (curSettings["CHAMS_BRIGHTNESS"]!!.toInt().toString().length) {
            4 -> "  "
            3 -> "    "
            2 -> "      "
            else -> "        "
        })
        chamsBrightnessSlider.value = curSettings["CHAMS_BRIGHTNESS"]!!.toInt().toFloat()
        showTeam.isChecked = curSettings["CHAMS_SHOW_TEAM"]!!.strToBool()
        showEnemies.isChecked = curSettings["CHAMS_SHOW_ENEMIES"]!!.strToBool()
    }

    indicatorEspTab.apply {
        indicatorEsp.isChecked = curSettings["INDICATOR_ESP"]!!.strToBool()
        indicatorOnScreen.isChecked = curSettings["INDICATOR_SHOW_ONSCREEN"]!!.strToBool()
        indicatorOval.isChecked = curSettings["INDICATOR_OVAL"]!!.strToBool()
        indicatorDistanceLabel.setText("Indicator Distance: "  + curSettings["INDICATOR_DISTANCE"]!!.toDouble())
        indicatorDistanceSlider.value = curSettings["INDICATOR_DISTANCE"]!!.toDouble().toFloat()
        showTeam.isChecked = curSettings["INDICATOR_SHOW_TEAM"]!!.strToBool()
        showEnemies.isChecked = curSettings["INDICATOR_SHOW_ENEMIES"]!!.strToBool()
        showBomb.isChecked = curSettings["INDICATOR_SHOW_BOMB"]!!.strToBool()
        showWeapons.isChecked = curSettings["INDICATOR_SHOW_WEAPONS"]!!.strToBool()
        showGrenades.isChecked = curSettings["INDICATOR_SHOW_GRENADES"]!!.strToBool()
    }

    boxEspTab.apply {
        boxEsp.isChecked = curSettings["BOX_ESP"]!!.strToBool()
        boxEspDetails.isChecked = curSettings["BOX_ESP_DETAILS"]!!.strToBool()
        showTeam.isChecked = curSettings["BOX_SHOW_TEAM"]!!.strToBool()
        showEnemies.isChecked = curSettings["BOX_SHOW_ENEMIES"]!!.strToBool()
    }

    skeletonEspTab.apply {
        skeletonEsp.isChecked = curSettings["SKELETON_ESP"]!!.strToBool()
        showTeam.isChecked = curSettings["SKELETON_SHOW_TEAM"]!!.strToBool()
        showEnemies.isChecked = curSettings["SKELETON_SHOW_ENEMIES"]!!.strToBool()
    }

    //BTrig Tab
    bTrigTab.apply {
        enableAutoKnife.isChecked = curSettings["ENABLE_AUTO_KNIFE"]!!.strToBool()
        enableBoneTrigger.isChecked = curSettings["ENABLE_BONE_TRIGGER"]!!.strToBool()
        boneTriggerFovLabel.setText("FOV: " + curSettings["BONE_TRIGGER_FOV"] + when (curSettings["BONE_TRIGGER_FOV"]!!.length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        boneTriggerFovSlider.value = curSettings["BONE_TRIGGER_FOV"]!!.toFloat()
        boneTriggerDelayLabel.setText("Shot Delay: " + curSettings["BONE_TRIGGER_SHOT_DELAY"] + when (curSettings["BONE_TRIGGER_SHOT_DELAY"]!!.length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        boneTriggerDelaySlider.value = curSettings["BONE_TRIGGER_SHOT_DELAY"]!!.toFloat()
        boneTriggerCheckHead.isChecked = curSettings["BONE_TRIGGER_HB"]!!.strToBool()
        boneTriggerCheckBody.isChecked = curSettings["BONE_TRIGGER_BB"]!!.strToBool()
        aimOnBoneTrigger.isChecked = curSettings["AIM_ON_BONE_TRIGGER"]!!.strToBool()
        boneTriggerEnableKey.isChecked = curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool()
        boneTriggerKeyField.text = curSettings["BONE_TRIGGER_KEY"]
    }

    //Misc Tab
    miscTab.apply {
        bunnyHop.isChecked = curSettings["ENABLE_BUNNY_HOP"]!!.strToBool()
        autoStrafe.isChecked = curSettings["AUTO_STRAFE"]!!.strToBool()
        fastStop.isChecked = curSettings["FAST_STOP"]!!.strToBool()
        bombTimer.isChecked = curSettings["ENABLE_BOMB_TIMER"]!!.strToBool()
        spectatorList.isChecked = curSettings["SPECTATOR_LIST"]!!.strToBool()
        menuKeyField.text = curSettings["MENU_KEY"]
        enableReducedFlash.isChecked = curSettings["ENABLE_REDUCED_FLASH"]!!.strToBool()
        flashMaxAlphaLabel.setText("Flash Max Alpha: " + curSettings["FLASH_MAX_ALPHA"]!!.toFloat() + when (curSettings["FLASH_MAX_ALPHA"]!!.length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        flashMaxAlphaSlider.value = curSettings["FLASH_MAX_ALPHA"]!!.toFloat()
        hitSound.isChecked = curSettings["ENABLE_HITSOUND"]!!.strToBool()
        hitSoundVolumeLabel.setText("Hitsound Volume: "  + curSettings["HITSOUND_VOLUME"]!!.toDouble())
        hitSoundVolumeSlider.value = curSettings["HITSOUND_VOLUME"]!!.toFloat()
    }

    //Rcs Tab
    rcsTab.apply {
        enableRCS.isChecked = curSettings["ENABLE_RCS"]!!.strToBool()
        rcsSmoothingLabel.setText("RCS Smoothing: " + curSettings["RCS_SMOOTHING"] + when(curSettings["RCS_SMOOTHING"]!!.length) {3->"" 2->"  " else->"    "})
        rcsSmoothingSlider.isDisabled = !curSettings["ENABLE_RCS"]!!.strToBool()
        rcsReturnAim.isDisabled = !curSettings["ENABLE_RCS"]!!.strToBool()
        enableRCrosshair.isChecked = curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
        rCrosshairWidthLabel.setText("RCrosshair Width: " + curSettings["RCROSSHAIR_WIDTH"])
        rCrosshairWidthSlider.value = curSettings["RCROSSHAIR_WIDTH"]!!.toInt().toFloat()
        rCrosshairLengthLabel.setText("RCrosshair Length: " + curSettings["RCROSSHAIR_LENGTH"])
        rCrosshairLengthSlider.value = curSettings["RCROSSHAIR_LENGTH"]!!.toFloat()
        rCrosshairXOffsetLabel.setText("RCrosshair X Offset: " + curSettings["RCROSSHAIR_XOFFSET"])
        rCrosshairXOffsetSlider.value = curSettings["RCROSSHAIR_XOFFSET"]!!.toFloat()
        rCrosshairYOffsetLabel.setText("RCrosshair Y Offset: " + curSettings["RCROSSHAIR_YOFFSET"])
        rCrosshairYOffsetSlider.value = curSettings["RCROSSHAIR_YOFFSET"]!!.toFloat()
        rCrosshairAlphaLabel.setText("RCrosshair Alpha: " + curSettings["RCROSSHAIR_ALPHA"])
        rCrosshairAlphaSlider.value = curSettings["RCROSSHAIR_ALPHA"]!!.toFloat()





        var bool = !curSettings["ENABLE_RCS"]!!.strToBool()
        var color = Color(255F, 255F, 255F, 1F)
        if (bool) {
            color = Color(105F, 105F, 105F, .2F)
        }
        rcsSmoothingLabel.color = color
        rcsSmoothingSlider.isDisabled = bool
        rcsReturnAim.isDisabled = bool

        bool = !curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
        color = Color(255F, 255F, 255F, 1F)
        if (bool) {
            color = Color(105F, 105F, 105F, .2F)
        }

        rCrosshairWidthSlider.isDisabled = bool
        rCrosshairLengthSlider.isDisabled = bool
        rCrosshairAlphaSlider.isDisabled = bool
        rCrosshairXOffsetSlider.isDisabled = bool
        rCrosshairYOffsetSlider.isDisabled = bool
        rCrosshairColorShow.isDisabled = bool

        rCrosshairWidthLabel.color = color
        rCrosshairLengthLabel.color = color
        rCrosshairAlphaLabel.color = color
        rCrosshairXOffsetLabel.color = color
        rCrosshairYOffsetLabel.color = color
    }
}