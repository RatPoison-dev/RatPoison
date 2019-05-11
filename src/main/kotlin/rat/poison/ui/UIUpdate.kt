package rat.poison.ui

import rat.poison.*
import rat.poison.settings.*
import rat.poison.ui.tabs.*

fun UIUpdate() {
    if (!opened) return

    //Aim Tab
    aimTab.apply {
        activateFromFireKey.isChecked = curSettings["ACTIVATE_FROM_FIRE_KEY"]!!.strToBool()
        teammatesAreEnemies.isChecked = curSettings["TEAMMATES_ARE_ENEMIES"]!!.strToBool()
        forceAimKeyField.text = curSettings["FORCE_AIM_KEY"].toString()
        automaticWeaponsCheckBox.isChecked = curSettings["AUTOMATIC_WEAPONS"]!!.strToBool()
        automaticWeaponsCollapsible.isCollapsed = !curSettings["AUTOMATIC_WEAPONS"]!!.strToBool()
        maxPunchCheckLabel.setText("Max Punch Check: " + curSettings["MAX_PUNCH_CHECK"].toString() + when(curSettings["MAX_PUNCH_CHECK"].toString().length) {3->"" 2->"  " else ->"    "})
        maxPunchCheckSlider.value = curSettings["MAX_PUNCH_CHECK"].toString().toFloat()

        val curWep = convStrToArray(curSettings[weaponOverrideSelected].toString())

        if (weaponOverride) {
            weaponOverrideEnableCheckBox.isChecked = curWep[0]!!.toBool()
            enableFactorRecoil.isChecked = curWep[1]!!.toBool()
            enableFlatAim.isChecked = curWep[2]!!.toBool()
            enablePathAim.isChecked = curWep[3]!!.toBool()
            aimBoneBox.selected = if (curWep[4]!!.toInt() == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
            aimFovLabel.setText("Aim Fov: " + curWep[5]!!.toInt() + when (curWep[5]!!.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            aimFovSlider.value = curWep[5]!!.toInt().toFloat()
            aimSpeedLabel.setText("Aim Speed: " + curWep[6]!!.toInt() + when (curWep[6]!!.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            aimSpeedSlider.value = curWep[6]!!.toInt().toFloat()
            aimSmoothnessLabel.setText("Aim Smoothness: " + curWep[7]!!.toFloat())
            aimSmoothnessSlider.value = curWep[7]!!.toFloat()
            aimStrictnessLabel.setText("Aim Strictness: " + curWep[8]!!.toFloat())
            aimStrictnessSlider.value = curWep[8]!!.toFloat()
            perfectAimCheckBox.isChecked = curWep[9]!!.toBool()
            perfectAimCollapsible.isCollapsed = !curWep[9]!!.toBool()
            perfectAimFovLabel.setText("Perfect Aim Fov: " + curWep[10]!!.toInt() + when (curWep[10]!!.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            perfectAimFovSlider.value = curWep[10]!!.toInt().toFloat()
            perfectAimChanceLabel.setText("Perfect Aim Chance: " + curWep[11]!!.toInt() + when (curWep[11]!!.toInt().toString().length) {
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
            aimBoneBox.selected = if (curSettings[categorySelected + "_AIM_BONE"].toString().toInt() == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
            aimFovLabel.setText("Aim Fov: " + curSettings[categorySelected + "_AIM_FOV"].toString().toInt() + when (curSettings[categorySelected + "_AIM_FOV"].toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            aimFovSlider.value = curSettings[categorySelected + "_AIM_FOV"].toString().toInt().toFloat()
            aimSpeedLabel.setText("Aim Speed: " + curSettings[categorySelected + "_AIM_SPEED"].toString().toInt() + when (curSettings[categorySelected + "_AIM_SPEED"].toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            aimSpeedSlider.value = curSettings[categorySelected + "_AIM_SPEED"].toString().toInt().toFloat()
            aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings[categorySelected + "_AIM_SMOOTHNESS"].toString().toFloat())
            aimSmoothnessSlider.value = curSettings[categorySelected + "_AIM_SMOOTHNESS"].toString().toFloat()
            aimStrictnessLabel.setText("Aim Strictness: " + curSettings[categorySelected + "_AIM_STRICTNESS"])
            aimStrictnessSlider.value = curSettings[categorySelected + "_AIM_STRICTNESS"].toString().toFloat()
            perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"]!!.strToBool()
            perfectAimCollapsible.isCollapsed = !curSettings[categorySelected + "_PERFECT_AIM"]!!.strToBool()
            perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings[categorySelected + "_PERFECT_AIM_FOV"].toString().toInt() + when (curSettings[categorySelected + "_PERFECT_AIM_FOV"].toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            perfectAimFovSlider.value = curSettings[categorySelected + "_PERFECT_AIM_FOV"].toString().toInt().toFloat()
            perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toString().toInt() + when (curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
            perfectAimChanceSlider.value = curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toString().toInt().toFloat()
        }
    }

    visualsTab.apply {
        enableEsp.isChecked = curSettings["ENABLE_ESP"]!!.strToBool()
        radarEsp.isChecked = curSettings["RADAR_ESP"]!!.strToBool()
        visualsToggleKeyField.text = curSettings["VISUALS_TOGGLE_KEY"].toString()
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
    }

    glowEspTab.apply {
        glowEsp.isChecked = curSettings["GLOW_ESP"]!!.strToBool()
        invGlowEsp.isChecked = curSettings["INV_GLOW_ESP"]!!.strToBool()
        modelEsp.isChecked = curSettings["MODEL_ESP"]!!.strToBool()
        modelAndGlow.isChecked = curSettings["MODEL_AND_GLOW"]!!.strToBool()
        showTeam.isChecked = curSettings["GLOW_SHOW_TEAM"]!!.strToBool()
        showEnemies.isChecked = curSettings["GLOW_SHOW_ENEMIES"]!!.strToBool()
        showDormant.isChecked = curSettings["GLOW_SHOW_DORMANT"]!!.strToBool()
        showBomb.isChecked = curSettings["GLOW_SHOW_BOMB"]!!.strToBool()
        showWeapons.isChecked = curSettings["GLOW_SHOW_WEAPONS"]!!.strToBool()
        showGrenades.isChecked = curSettings["GLOW_SHOW_GRENADES"]!!.strToBool()
        showTarget.isChecked = curSettings["GLOW_SHOW_TARGET"]!!.strToBool()
    }

    chamsEspTab.apply {
        chamsEsp.isChecked = curSettings["CHAMS_ESP"]!!.strToBool()
        chamsShowHealth.isChecked = curSettings["CHAMS_SHOW_HEALTH"]!!.strToBool()
        chamsBrightnessLabel.setText("Chams Brightness: " + curSettings["CHAMS_BRIGHTNESS"].toString().toInt() + when (curSettings["CHAMS_BRIGHTNESS"].toString().toInt().toString().length) {
            4 -> "  "
            3 -> "    "
            2 -> "      "
            else -> "        "
        })
        chamsBrightnessSlider.value = curSettings["CHAMS_BRIGHTNESS"].toString().toInt().toFloat()
        showTeam.isChecked = curSettings["CHAMS_SHOW_TEAM"]!!.strToBool()
        showEnemies.isChecked = curSettings["CHAMS_SHOW_ENEMIES"]!!.strToBool()
    }

    indicatorEspTab.apply {
        indicatorEsp.isChecked = curSettings["INDICATOR_ESP"]!!.strToBool()
        indicatorOnScreen.isChecked = curSettings["INDICATOR_SHOW_ONSCREEN"]!!.strToBool()
        indicatorOval.isChecked = curSettings["INDICATOR_OVAL"]!!.strToBool()
        indicatorDistanceLabel.setText("Indicator Distance: "  + curSettings["INDICATOR_DISTANCE"].toString().toDouble())
        indicatorDistanceSlider.value = curSettings["INDICATOR_DISTANCE"].toString().toDouble().toFloat()
        showTeam.isChecked = curSettings["INDICATOR_SHOW_TEAM"]!!.strToBool()
        showEnemies.isChecked = curSettings["INDICATOR_SHOW_ENEMIES"]!!.strToBool()
        showDormant.isChecked = curSettings["INDICATOR_SHOW_DORMANT"]!!.strToBool()
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
        enableBoneTrigger.isChecked = curSettings["ENABLE_BONE_TRIGGER"]!!.strToBool()
        boneTriggerFovLabel.setText("Bone Trigger Fov: " + curSettings["BONE_TRIGGER_FOV"].toString() + when (curSettings["BONE_TRIGGER_FOV"].toString().length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        boneTriggerFovSlider.value = curSettings["BONE_TRIGGER_FOV"].toString().toFloat()
        boneTriggerCheckHead.isChecked = curSettings["BONE_TRIGGER_HB"]!!.strToBool()
        boneTriggerCheckBody.isChecked = curSettings["BONE_TRIGGER_BB"]!!.strToBool()
        aimOnBoneTrigger.isChecked = curSettings["AIM_ON_BONE_TRIGGER"]!!.strToBool()
        boneTriggerEnableKey.isChecked = curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool()
        boneTriggerKeyField.text = curSettings["BONE_TRIGGER_KEY"].toString()
    }

    //Misc Tab
    miscTab.apply {
        bunnyHop.isChecked = curSettings["ENABLE_BUNNY_HOP"]!!.strToBool()
        bombTimer.isChecked = curSettings["ENABLE_BOMB_TIMER"]!!.strToBool()
        fireKeyField.text = curSettings["FIRE_KEY"].toString()
        menuKeyField.text = curSettings["MENU_KEY"].toString()
        enableReducedFlash.isChecked = curSettings["ENABLE_REDUCED_FLASH"]!!.strToBool()
        flashMaxAlphaLabel.setText("Flash Max Alpha: " + curSettings["FLASH_MAX_ALPHA"].toString().toFloat() + when (curSettings["FLASH_MAX_ALPHA"].toString().length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        flashMaxAlphaSlider.value = curSettings["FLASH_MAX_ALPHA"].toString().toFloat()
        hitSound.isChecked = curSettings["ENABLE_HITSOUND"]!!.strToBool()
        hitSoundVolumeLabel.setText("Hitsound Volume: "  + curSettings["HITSOUND_VOLUME"].toString().toDouble())
        hitSoundVolumeSlider.value = curSettings["HITSOUND_VOLUME"].toString().toFloat()
    }

    //Custom disable items
    rcsTab.apply {
        enableRCS.isChecked = curSettings["ENABLE_RCS"]!!.strToBool()
        rcsSmoothingSlider.isDisabled = !curSettings["ENABLE_RCS"]!!.strToBool()
        rcsReturnAim.isDisabled = !curSettings["ENABLE_RCS"]!!.strToBool()
        enableRCrosshair.isChecked = curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
        rCrosshairWidthSlider.isDisabled = !curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
        rcsCrosshairLengthSlider.isDisabled = !curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
        rCrosshairAlphaSlider.isDisabled = !curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
        rCrosshairColorShow.isDisabled = !curSettings["ENABLE_RECOIL_CROSSHAIR"]!!.strToBool()
    }
}