package rat.poison.ui

import rat.poison.opened
import rat.poison.settings.*
import rat.poison.ui.tabs.*
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.strToColor

fun UIUpdate() {
    if (!opened) return

    //Aim Tab
    aimTab.apply { //CLEAN THIS BITCH UUUUUPPP
        activateFromFireKey.isChecked = curSettings["ACTIVATE_FROM_FIRE_KEY"]!!.strToBool()
        teammatesAreEnemies.isChecked = curSettings["TEAMMATES_ARE_ENEMIES"]!!.strToBool()
        forceAimKeyField.text = curSettings["FORCE_AIM_KEY"].toString()
        automaticWeaponsCheckBox.isChecked = curSettings["AUTOMATIC_WEAPONS"]!!.strToBool()
        automaticWeaponsCollapsible.isCollapsed = !curSettings["AUTOMATIC_WEAPONS"]!!.strToBool()
        maxPunchCheckLabel.setText("Max Punch Check: " + curSettings["MAX_PUNCH_CHECK"].toString() + when(curSettings["MAX_PUNCH_CHECK"].toString().length) {3->"" 2->"  " else ->"    "}) //curSettings["MAX_PUNCH_CHECK"])
        maxPunchCheckSlider.value = curSettings["MAX_PUNCH_CHECK"].toString().toFloat()

        when (categorySelected) {
            "PISTOL" -> {
                enableFactorRecoil.isChecked = curSettings["PISTOL_FACTOR_RECOIL"]!!.strToBool()
                enableFlatAim.isChecked = curSettings["PISTOL_ENABLE_FLAT_AIM"]!!.strToBool()
                enablePathAim.isChecked = curSettings["PISTOL_ENABLE_PATH_AIM"]!!.strToBool()
                aimBoneBox.selected = if (curSettings["PISTOL_AIM_BONE"].toString().toInt() == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
                aimFovLabel.setText("Aim Fov: " + curSettings["PISTOL_AIM_FOV"].toString().toInt() + when (curSettings["PISTOL_AIM_FOV"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimFovSlider.value = curSettings["PISTOL_AIM_FOV"].toString().toInt().toFloat()
                aimSpeedLabel.setText("Aim Speed: " + curSettings["PISTOL_AIM_SPEED"].toString().toInt() + when (curSettings["PISTOL_AIM_SPEED"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedSlider.value = curSettings["PISTOL_AIM_SPEED"].toString().toInt().toFloat()
                aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings["PISTOL_AIM_SMOOTHNESS"].toString().toFloat())
                aimSmoothnessSlider.value = curSettings["PISTOL_AIM_SMOOTHNESS"].toString().toFloat()
                aimStrictnessLabel.setText("Aim Strictness: " + curSettings["PISTOL_AIM_STRICTNESS"])
                aimStrictnessSlider.value = curSettings["PISTOL_AIM_STRICTNESS"].toString().toFloat()
                perfectAimCheckBox.isChecked = curSettings["PISTOL_PERFECT_AIM"]!!.strToBool()
                perfectAimCollapsible.isCollapsed = !curSettings["PISTOL_PERFECT_AIM"]!!.strToBool()
                perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings["PISTOL_PERFECT_AIM_FOV"].toString().toInt() + when (curSettings["PISTOL_PERFECT_AIM_FOV"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimFovSlider.value = curSettings["PISTOL_PERFECT_AIM_FOV"].toString().toInt().toFloat()
                perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().toInt() + when (curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimChanceSlider.value = curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().toInt().toFloat()
            }

            "RIFLE" -> {
                enableFactorRecoil.isChecked = curSettings["RIFLE_FACTOR_RECOIL"]!!.strToBool()
                enableFlatAim.isChecked = curSettings["RIFLE_ENABLE_FLAT_AIM"]!!.strToBool()
                enablePathAim.isChecked = curSettings["RIFLE_ENABLE_PATH_AIM"]!!.strToBool()
                aimBoneBox.selected = if (curSettings["RIFLE_AIM_BONE"].toString().toInt() == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
                aimFovLabel.setText("Aim Fov: " + curSettings["RIFLE_AIM_FOV"].toString().toInt() + when (curSettings["RIFLE_AIM_FOV"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimFovSlider.value = curSettings["RIFLE_AIM_FOV"].toString().toInt().toFloat()
                aimSpeedLabel.setText("Aim Speed: " + curSettings["RIFLE_AIM_SPEED"].toString().toInt() + when (curSettings["RIFLE_AIM_SPEED"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedSlider.value = curSettings["RIFLE_AIM_SPEED"].toString().toInt().toFloat()
                aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings["RIFLE_AIM_SMOOTHNESS"].toString().toFloat())
                aimSmoothnessSlider.value = curSettings["RIFLE_AIM_SMOOTHNESS"].toString().toFloat()
                aimStrictnessLabel.setText("Aim Strictness: " + curSettings["RIFLE_AIM_STRICTNESS"])
                aimStrictnessSlider.value = curSettings["RIFLE_AIM_STRICTNESS"].toString().toFloat()
                perfectAimCheckBox.isChecked = curSettings["RIFLE_PERFECT_AIM"]!!.strToBool()
                perfectAimCollapsible.isCollapsed = !curSettings["RIFLE_PERFECT_AIM"]!!.strToBool()
                perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings["RIFLE_PERFECT_AIM_FOV"].toString().toInt() + when (curSettings["RIFLE_PERFECT_AIM_FOV"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimFovSlider.value = curSettings["RIFLE_PERFECT_AIM_FOV"].toString().toInt().toFloat()
                perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings["RIFLE_PERFECT_AIM_CHANCE"].toString().toInt() + when (curSettings["RIFLE_PERFECT_AIM_CHANCE"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimChanceSlider.value = curSettings["RIFLE_PERFECT_AIM_CHANCE"].toString().toInt().toFloat()
            }

            "SMG" -> {
                enableFactorRecoil.isChecked = curSettings["SMG_FACTOR_RECOIL"]!!.strToBool()
                enableFlatAim.isChecked = curSettings["SMG_ENABLE_FLAT_AIM"]!!.strToBool()
                enablePathAim.isChecked = curSettings["SMG_ENABLE_PATH_AIM"]!!.strToBool()
                aimBoneBox.selected = if (curSettings["SMG_AIM_BONE"].toString().toInt() == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
                aimFovLabel.setText("Aim Fov: " + curSettings["SMG_AIM_FOV"].toString().toInt() + when (curSettings["SMG_AIM_FOV"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimFovSlider.value = curSettings["SMG_AIM_FOV"].toString().toInt().toFloat()
                aimSpeedLabel.setText("Aim Speed: " + curSettings["SMG_AIM_SPEED"].toString().toInt() + when (curSettings["SMG_AIM_SPEED"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedSlider.value = curSettings["SMG_AIM_SPEED"].toString().toInt().toFloat()
                aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings["SMG_AIM_SMOOTHNESS"].toString().toFloat())
                aimSmoothnessSlider.value = curSettings["SMG_AIM_SMOOTHNESS"].toString().toFloat()
                aimStrictnessLabel.setText("Aim Strictness: " + curSettings["SMG_AIM_STRICTNESS"])
                aimStrictnessSlider.value = curSettings["SMG_AIM_STRICTNESS"].toString().toFloat()
                perfectAimCheckBox.isChecked = curSettings["SMG_PERFECT_AIM"]!!.strToBool()
                perfectAimCollapsible.isCollapsed = !curSettings["SMG_PERFECT_AIM"]!!.strToBool()
                perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings["SMG_PERFECT_AIM_FOV"].toString().toInt() + when (curSettings["SMG_PERFECT_AIM_FOV"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimFovSlider.value = curSettings["SMG_PERFECT_AIM_FOV"].toString().toInt().toFloat()
                perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings["SMG_PERFECT_AIM_CHANCE"].toString().toInt() + when (curSettings["SMG_PERFECT_AIM_CHANCE"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimChanceSlider.value = curSettings["SMG_PERFECT_AIM_CHANCE"].toString().toInt().toFloat()
            }

            "SNIPER" -> {
                enableFactorRecoil.isChecked = curSettings["SNIPER_FACTOR_RECOIL"]!!.strToBool()
                enableFlatAim.isChecked = curSettings["SNIPER_ENABLE_FLAT_AIM"]!!.strToBool()
                enablePathAim.isChecked = curSettings["SNIPER_ENABLE_PATH_AIM"]!!.strToBool()
                aimBoneBox.selected = if (curSettings["SNIPER_AIM_BONE"].toString().toInt() == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
                aimFovLabel.setText("Aim Fov: " + curSettings["SNIPER_AIM_FOV"].toString().toInt() + when (curSettings["SNIPER_AIM_FOV"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimFovSlider.value = curSettings["SNIPER_AIM_FOV"].toString().toInt().toFloat()
                aimSpeedLabel.setText("Aim Speed: " + curSettings["SNIPER_AIM_SPEED"].toString().toInt() + when (curSettings["SNIPER_AIM_SPEED"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedSlider.value = curSettings["SNIPER_AIM_SPEED"].toString().toInt().toFloat()
                aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings["SNIPER_AIM_SMOOTHNESS"].toString().toFloat())
                aimSmoothnessSlider.value = curSettings["SNIPER_AIM_SMOOTHNESS"].toString().toFloat()
                aimStrictnessLabel.setText("Aim Strictness: " + curSettings["SNIPER_AIM_STRICTNESS"])
                aimStrictnessSlider.value = curSettings["SNIPER_AIM_STRICTNESS"].toString().toFloat()
                perfectAimCheckBox.isChecked = curSettings["SNIPER_PERFECT_AIM"]!!.strToBool()
                perfectAimCollapsible.isCollapsed = !curSettings["SNIPER_PERFECT_AIM"]!!.strToBool()
                perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings["SNIPER_PERFECT_AIM_FOV"].toString().toInt() + when (curSettings["SNIPER_PERFECT_AIM_FOV"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimFovSlider.value = curSettings["SNIPER_PERFECT_AIM_FOV"].toString().toInt().toFloat()
                perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings["SNIPER_PERFECT_AIM_CHANCE"].toString().toInt() + when (curSettings["SNIPER_PERFECT_AIM_CHANCE"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimChanceSlider.value = curSettings["SNIPER_PERFECT_AIM_CHANCE"].toString().toInt().toFloat()
            }

            "SHOTGUN" -> {
                enableFactorRecoil.isChecked = curSettings["SHOTGUN_FACTOR_RECOIL"]!!.strToBool()
                enableFlatAim.isChecked = curSettings["SHOTGUN_ENABLE_FLAT_AIM"]!!.strToBool()
                enablePathAim.isChecked = curSettings["SHOTGUN_ENABLE_PATH_AIM"]!!.strToBool()
                aimBoneBox.selected = if (curSettings["SHOTGUN_AIM_BONE"].toString().toInt() == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
                aimFovLabel.setText("Aim Fov: " + curSettings["SHOTGUN_AIM_FOV"].toString().toInt() + when (curSettings["SHOTGUN_AIM_FOV"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimFovSlider.value = curSettings["SHOTGUN_AIM_FOV"].toString().toInt().toFloat()
                aimSpeedLabel.setText("Aim Speed: " + curSettings["SHOTGUN_AIM_SPEED"].toString().toInt() + when (curSettings["SHOTGUN_AIM_SPEED"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedSlider.value = curSettings["SHOTGUN_AIM_SPEED"].toString().toInt().toFloat()
                aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings["SHOTGUN_AIM_SMOOTHNESS"].toString().toFloat())
                aimSmoothnessSlider.value = curSettings["SHOTGUN_AIM_SMOOTHNESS"].toString().toFloat()
                aimStrictnessLabel.setText("Aim Strictness: " + curSettings["SHOTGUN_AIM_STRICTNESS"])
                aimStrictnessSlider.value = curSettings["SHOTGUN_AIM_STRICTNESS"].toString().toFloat()
                perfectAimCheckBox.isChecked = curSettings["SHOTGUN_PERFECT_AIM"]!!.strToBool()
                perfectAimCollapsible.isCollapsed = !curSettings["SHOTGUN_PERFECT_AIM"]!!.strToBool()
                perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings["SHOTGUN_PERFECT_AIM_FOV"].toString().toInt() + when (curSettings["SHOTGUN_PERFECT_AIM_FOV"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimFovSlider.value = curSettings["SHOTGUN_PERFECT_AIM_FOV"].toString().toInt().toFloat()
                perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings["SHOTGUN_PERFECT_AIM_CHANCE"].toString().toInt() + when (curSettings["SHOTGUN_PERFECT_AIM_CHANCE"].toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimChanceSlider.value = curSettings["SHOTGUN_PERFECT_AIM_CHANCE"].toString().toInt().toFloat()
            }
        }
        ////Custom Overrides
//        overrideEnableOverride.isChecked = curOverrideWep[1].toBool()
//        overrideEnableFactorRecoil.isChecked = curOverrideWep[2].toBool()
//        overrideEnableFlatAim.isChecked = curOverrideWep[3].toBool()
//        overrideEnablePathAim.isChecked = curOverrideWep[4].toBool()
//        overrideAimBoneBox.selected = if (curOverrideWep[5].toInt() == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
//        overrideAimFovLabel.setText("Aim Fov: " + curOverrideWep[6].toInt() + when (curOverrideWep[6].toInt().toString().length) {
//            3 -> "  "
//            2 -> "    "
//            else -> "      "
//        })
//        overrideAimFovSlider.value = curOverrideWep[6].toFloat()
//        overrideAimSpeedLabel.setText("Aim Speed: " + curOverrideWep[7].toInt() + when (curOverrideWep[7].toInt().toString().length) {
//            3 -> "  "
//            2 -> "    "
//            else -> "      "
//        })
//        overrideAimSpeedSlider.value = curOverrideWep[7].toFloat()
//        overrideAimSmoothnessLabel.setText("Aim Smoothness: " + curOverrideWep[8])
//        overrideAimSmoothnessSlider.value = curOverrideWep[8].toFloat()
//        overridePerfectAimCheckBox.isChecked = curOverrideWep[10].toBool()
//        overridePerfectAimCollapsible.isCollapsed = !curOverrideWep[10].toBool()
//        overridePerfectAimFovLabel.setText("Perfect Aim Fov: " + curOverrideWep[11].toInt() + when (curOverrideWep[11].toInt().toString().length) {
//            3 -> "  "
//            2 -> "    "
//            else -> "      "
//        })
//        overridePerfectAimFovSlider.value = curOverrideWep[11].toFloat()
//        overridePerfectAimChanceLabel.setText("Perfect Aim Chance: " + curOverrideWep[12].toInt() + when (curOverrideWep[12].toInt().toString().length) {
//            3 -> "  "
//            2 -> "    "
//            else -> "      "
//        })
//        overridePerfectAimChanceSlider.value = curOverrideWep[12].toFloat()
//        overrideAimAssistCheckBox.isChecked = curOverrideWep[13].toBool()
//        overrideAimAssistCollapsible.isCollapsed = !curOverrideWep[13].toBool()
//        overrideAimAssistStrictnessLabel.setText("Aim Assist Strictness: " + curOverrideWep[14].toInt() + when (curOverrideWep[14].toInt().toString().length) {
//            3 -> "  "
//            2 -> "    "
//            else -> "      "
//        })
//        overrideAimAssistStrictnessSlider.value = curOverrideWep[14].toFloat()

    }

    visualsTab.apply {
        enableEsp.isChecked = curSettings["ENABLE_ESP"]!!.strToBool()
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
        radarEsp.isChecked = curSettings["RADAR_ESP"]!!.strToBool()
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