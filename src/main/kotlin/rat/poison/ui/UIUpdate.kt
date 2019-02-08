package rat.poison.ui

import rat.poison.settings.*
import rat.poison.ui.tabs.*

fun UIUpdate() {
    //Aim Tab
    aimTab.apply { //CLEAN THIS BITCH UUUUUPPP
        activateFromFireKey.isChecked = ACTIVATE_FROM_FIRE_KEY
        teammatesAreEnemies.isChecked = TEAMMATES_ARE_ENEMIES
        forceAimKeyField.text = FORCE_AIM_KEY.toString()
        automaticWeapons.isChecked = AUTOMATIC_WEAPONS
        maxPunchCheckLabel.setText("Max Punch Check: " + MAX_PUNCH_CHECK.toString() + when(MAX_PUNCH_CHECK.toString().length) {3->"" 2->"  " else ->"    "}) //Max_Punch_Check)
        maxPunchCheckSlider.value = MAX_PUNCH_CHECK.toFloat()

        when (categorySelected) {
            "PISTOL" -> {
                enableFlatAim.isChecked = PISTOL_ENABLE_FLAT_AIM
                enablePathAim.isChecked = PISTOL_ENABLE_PATH_AIM
                aimBoneBox.selected = if (PISTOL_AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
                aimFovLabel.setText("Aim Fov: $PISTOL_AIM_FOV" + when (PISTOL_AIM_FOV.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimFovSlider.value = PISTOL_AIM_FOV.toFloat()
                aimSpeedLabel.setText("Aim Speed: $PISTOL_AIM_SPEED" + when (PISTOL_AIM_SPEED.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedSlider.value = PISTOL_AIM_SPEED.toFloat()
                aimStrictnessLabel.setText("Aim Strictness: $PISTOL_AIM_STRICTNESS")
                aimStrictnessSlider.value = PISTOL_AIM_STRICTNESS.toFloat()
                perfectAimCheckBox.isChecked = PISTOL_PERFECT_AIM
                perfectAimCollapsible.isCollapsed = !PISTOL_PERFECT_AIM
                perfectAimFovLabel.setText("Perfect Aim Fov: $PISTOL_PERFECT_AIM_FOV" + when (PISTOL_PERFECT_AIM_FOV.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimFovSlider.value = PISTOL_PERFECT_AIM_FOV.toFloat()
                perfectAimChanceLabel.setText("Perfect Aim Chance: $PISTOL_PERFECT_AIM_CHANCE" + when (PISTOL_PERFECT_AIM_CHANCE.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimChanceSlider.value = PISTOL_PERFECT_AIM_CHANCE.toFloat()
                aimAssistCheckBox.isChecked = PISTOL_AIM_ASSIST_MODE
                aimAssistCollapsible.isCollapsed = !PISTOL_AIM_ASSIST_MODE
                aimAssistStrictnessLabel.setText("Aim Assist Strictness: $PISTOL_AIM_ASSIST_STRICTNESS" + when (PISTOL_AIM_ASSIST_STRICTNESS.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimAssistStrictnessSlider.value = PISTOL_AIM_ASSIST_STRICTNESS.toFloat()
            }

            "RIFLE" -> {
                enableFlatAim.isChecked = RIFLE_ENABLE_FLAT_AIM
                enablePathAim.isChecked = RIFLE_ENABLE_PATH_AIM
                aimBoneBox.selected = if (RIFLE_AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
                aimFovLabel.setText("Aim Fov: $RIFLE_AIM_FOV" + when (RIFLE_AIM_FOV.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimFovSlider.value = RIFLE_AIM_FOV.toFloat()
                aimSpeedLabel.setText("Aim Speed: $RIFLE_AIM_SPEED" + when (RIFLE_AIM_SPEED.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedSlider.value = RIFLE_AIM_SPEED.toFloat()
                aimStrictnessLabel.setText("Aim Strictness: $RIFLE_AIM_STRICTNESS")
                aimStrictnessSlider.value = RIFLE_AIM_STRICTNESS.toFloat()
                perfectAimCheckBox.isChecked = RIFLE_PERFECT_AIM
                perfectAimCollapsible.isCollapsed = !RIFLE_PERFECT_AIM
                perfectAimFovLabel.setText("Perfect Aim Fov: $RIFLE_PERFECT_AIM_FOV" + when (RIFLE_PERFECT_AIM_FOV.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimFovSlider.value = RIFLE_PERFECT_AIM_FOV.toFloat()
                perfectAimChanceLabel.setText("Perfect Aim Chance: $RIFLE_PERFECT_AIM_CHANCE" + when (RIFLE_PERFECT_AIM_CHANCE.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimChanceSlider.value = RIFLE_PERFECT_AIM_CHANCE.toFloat()
                aimAssistCheckBox.isChecked = RIFLE_AIM_ASSIST_MODE
                aimAssistCollapsible.isCollapsed = !RIFLE_AIM_ASSIST_MODE
                aimAssistStrictnessLabel.setText("Aim Assist Strictness: $RIFLE_AIM_ASSIST_STRICTNESS" + when (RIFLE_AIM_ASSIST_STRICTNESS.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimAssistStrictnessSlider.value = RIFLE_AIM_ASSIST_STRICTNESS.toFloat()
            }

            "SMG" -> {
                enableFlatAim.isChecked = SMG_ENABLE_FLAT_AIM
                enablePathAim.isChecked = SMG_ENABLE_PATH_AIM
                aimBoneBox.selected = if (SMG_AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
                aimFovLabel.setText("Aim Fov: $SMG_AIM_FOV" + when (SMG_AIM_FOV.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimFovSlider.value = SMG_AIM_FOV.toFloat()
                aimSpeedLabel.setText("Aim Speed: $SMG_AIM_SPEED" + when (SMG_AIM_SPEED.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedSlider.value = SMG_AIM_SPEED.toFloat()
                aimStrictnessLabel.setText("Aim Strictness: $SMG_AIM_STRICTNESS")
                aimStrictnessSlider.value = SMG_AIM_STRICTNESS.toFloat()
                perfectAimCheckBox.isChecked = SMG_PERFECT_AIM
                perfectAimCollapsible.isCollapsed = !SMG_PERFECT_AIM
                perfectAimFovLabel.setText("Perfect Aim Fov: $SMG_PERFECT_AIM_FOV" + when (SMG_PERFECT_AIM_FOV.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimFovSlider.value = SMG_PERFECT_AIM_FOV.toFloat()
                perfectAimChanceLabel.setText("Perfect Aim Chance: $SMG_PERFECT_AIM_CHANCE" + when (SMG_PERFECT_AIM_CHANCE.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimChanceSlider.value = SMG_PERFECT_AIM_CHANCE.toFloat()
                aimAssistCheckBox.isChecked = SMG_AIM_ASSIST_MODE
                aimAssistCollapsible.isCollapsed = !SMG_AIM_ASSIST_MODE
                aimAssistStrictnessLabel.setText("Aim Assist Strictness: $SMG_AIM_ASSIST_STRICTNESS" + when (SMG_AIM_ASSIST_STRICTNESS.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimAssistStrictnessSlider.value = SMG_AIM_ASSIST_STRICTNESS.toFloat()
            }

            "SNIPER" -> {
                enableFlatAim.isChecked = SNIPER_ENABLE_FLAT_AIM
                enablePathAim.isChecked = SNIPER_ENABLE_PATH_AIM
                aimBoneBox.selected = if (SNIPER_AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
                aimFovLabel.setText("Aim Fov: $SNIPER_AIM_FOV" + when (SNIPER_AIM_FOV.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimFovSlider.value = SNIPER_AIM_FOV.toFloat()
                aimSpeedLabel.setText("Aim Speed: $SNIPER_AIM_SPEED" + when (SNIPER_AIM_SPEED.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedSlider.value = SNIPER_AIM_SPEED.toFloat()
                aimStrictnessLabel.setText("Aim Strictness: $SNIPER_AIM_STRICTNESS")
                aimStrictnessSlider.value = SNIPER_AIM_STRICTNESS.toFloat()
                perfectAimCheckBox.isChecked = SNIPER_PERFECT_AIM
                perfectAimCollapsible.isCollapsed = !SNIPER_PERFECT_AIM
                perfectAimFovLabel.setText("Perfect Aim Fov: $SNIPER_PERFECT_AIM_FOV" + when (SNIPER_PERFECT_AIM_FOV.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimFovSlider.value = SNIPER_PERFECT_AIM_FOV.toFloat()
                perfectAimChanceLabel.setText("Perfect Aim Chance: $SNIPER_PERFECT_AIM_CHANCE" + when (SNIPER_PERFECT_AIM_CHANCE.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimChanceSlider.value = SNIPER_PERFECT_AIM_CHANCE.toFloat()
                aimAssistCheckBox.isChecked = SNIPER_AIM_ASSIST_MODE
                aimAssistCollapsible.isCollapsed = !SNIPER_AIM_ASSIST_MODE
                aimAssistStrictnessLabel.setText("Aim Assist Strictness: $SNIPER_AIM_ASSIST_STRICTNESS" + when (SNIPER_AIM_ASSIST_STRICTNESS.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimAssistStrictnessSlider.value = SNIPER_AIM_ASSIST_STRICTNESS.toFloat()
            }

            "SHOTGUN" -> {
                enableFlatAim.isChecked = SHOTGUN_ENABLE_FLAT_AIM
                enablePathAim.isChecked = SHOTGUN_ENABLE_PATH_AIM
                aimBoneBox.selected = if (SHOTGUN_AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
                aimFovLabel.setText("Aim Fov: $SHOTGUN_AIM_FOV" + when (SHOTGUN_AIM_FOV.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimFovSlider.value = SHOTGUN_AIM_FOV.toFloat()
                aimSpeedLabel.setText("Aim Speed: $SHOTGUN_AIM_SPEED" + when (SHOTGUN_AIM_SPEED.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedSlider.value = SHOTGUN_AIM_SPEED.toFloat()
                aimStrictnessLabel.setText("Aim Strictness: $SHOTGUN_AIM_STRICTNESS")
                aimStrictnessSlider.value = SHOTGUN_AIM_STRICTNESS.toFloat()
                perfectAimCheckBox.isChecked = SHOTGUN_PERFECT_AIM
                perfectAimCollapsible.isCollapsed = !SHOTGUN_PERFECT_AIM
                perfectAimFovLabel.setText("Perfect Aim Fov: $SHOTGUN_PERFECT_AIM_FOV" + when (SHOTGUN_PERFECT_AIM_FOV.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimFovSlider.value = SHOTGUN_PERFECT_AIM_FOV.toFloat()
                perfectAimChanceLabel.setText("Perfect Aim Chance: $SHOTGUN_PERFECT_AIM_CHANCE" + when (SHOTGUN_PERFECT_AIM_CHANCE.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                perfectAimChanceSlider.value = SHOTGUN_PERFECT_AIM_CHANCE.toFloat()
                aimAssistCheckBox.isChecked = SHOTGUN_AIM_ASSIST_MODE
                aimAssistCollapsible.isCollapsed = !SHOTGUN_AIM_ASSIST_MODE
                aimAssistStrictnessLabel.setText("Aim Assist Strictness: $SHOTGUN_AIM_ASSIST_STRICTNESS" + when (SHOTGUN_AIM_ASSIST_STRICTNESS.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimAssistStrictnessSlider.value = SHOTGUN_AIM_ASSIST_STRICTNESS.toFloat()
            }
        }
    }

    //Esp Tab
    visualsTab.apply {
        enableEsp.isChecked = ENABLE_ESP
        teamColorShow.setColor(TEAM_COLOR.red.toFloat(), TEAM_COLOR.green.toFloat(), TEAM_COLOR.blue.toFloat(), 1F)
        enemyColorShow.setColor(ENEMY_COLOR.red.toFloat(), ENEMY_COLOR.green.toFloat(), ENEMY_COLOR.blue.toFloat(), 1F)
        bombColorShow.setColor(BOMB_COLOR.red.toFloat(), BOMB_COLOR.green.toFloat(), BOMB_COLOR.blue.toFloat(), 1F)
        weaponColorShow.setColor(WEAPON_COLOR.red.toFloat(), WEAPON_COLOR.green.toFloat(), WEAPON_COLOR.blue.toFloat(), 1F)
        grenadeColorShow.setColor(GRENADE_COLOR.red.toFloat(), GRENADE_COLOR.green.toFloat(), GRENADE_COLOR.blue.toFloat(), 1F)
    }

    glowEspTab.apply {
        glowEsp.isChecked = GLOW_ESP
        invGlowEsp.isChecked = INV_GLOW_ESP
        modelEsp.isChecked = MODEL_ESP
        modelAndGlow.isChecked = MODEL_AND_GLOW
        showTeam.isChecked = GLOW_SHOW_TEAM
        showEnemies.isChecked = GLOW_SHOW_ENEMIES
        showDormant.isChecked = GLOW_SHOW_DORMANT
        showBomb.isChecked = GLOW_SHOW_BOMB
        showWeapons.isChecked = GLOW_SHOW_WEAPONS
        showGrenades.isChecked = GLOW_SHOW_GRENADES
    }

    chamsEspTab.apply {
        chamsEsp.isChecked = CHAMS_ESP
        chamsShowHealth.isChecked = CHAMS_SHOW_HEALTH
        chamsBrightnessLabel.setText("Chams Brightness: $CHAMS_BRIGHTNESS" + when (CHAMS_BRIGHTNESS.toString().length) {
            4 -> "  "
            3 -> "    "
            2 -> "      "
            else -> "        "
        })
        chamsBrightnessSlider.value = CHAMS_BRIGHTNESS.toFloat()
        showTeam.isChecked = CHAMS_SHOW_TEAM
        showEnemies.isChecked = CHAMS_SHOW_ENEMIES
    }

    indicatorEspTab.apply {
        indicatorEsp.isChecked = INDICATOR_ESP
        indicatorOnScreen.isChecked = INDICATOR_SHOW_ONSCREEN
        indicatorOval.isChecked = INDICATOR_OVAL
        indicatorDistanceLabel.setText("Indicator Distance: $INDICATOR_DISTANCE")
        indicatorDistanceSlider.value = INDICATOR_DISTANCE.toFloat()
        showTeam.isChecked = INDICATOR_SHOW_TEAM
        showEnemies.isChecked = INDICATOR_SHOW_ENEMIES
        showDormant.isChecked = INDICATOR_SHOW_DORMANT
        showBomb.isChecked = INDICATOR_SHOW_BOMB
        showWeapons.isChecked = INDICATOR_SHOW_WEAPONS
        showGrenades.isChecked = INDICATOR_SHOW_GRENADES
    }

    boxEspTab.apply {
        boxEsp.isChecked = BOX_ESP
        boxEspDetails.isChecked = BOX_ESP_DETAILS
        showTeam.isChecked = BOX_SHOW_TEAM
        showEnemies.isChecked = BOX_SHOW_ENEMIES
    }

    skeletonEspTab.apply {
        skeletonEsp.isChecked = SKELETON_ESP
        showTeam.isChecked = BOX_SHOW_TEAM
        showEnemies.isChecked = BOX_SHOW_ENEMIES
    }



    //Rcs Tab
    rcsTab.apply {
        rcsSmoothingLabel.setText("RCS Smoothing: $RCS_SMOOTHING")
        rcsSmoothingSlider.value = RCS_SMOOTHING.toFloat()
        rcsReturnAim.isChecked = RCS_RETURNAIM
    }

    //BTrig Tab
    bTrigTab.apply {
        enableBoneTrigger.isChecked = ENABLE_BONE_TRIGGER
        boneTriggerFovLabel.setText("Bone Trigger Fov: $BONE_TRIGGER_FOV" + when (BONE_TRIGGER_FOV.toString().length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        boneTriggerFovSlider.value = BONE_TRIGGER_FOV.toFloat()
        boneTriggerBoneBox.selected = if (BONE_TRIGGER_BONE == HEAD_BONE) "Head Bone" else "Body Bone"
        aimOnBoneTrigger.isChecked = AIM_ON_BONE_TRIGGER
        boneTriggerEnableKey.isChecked = BONE_TRIGGER_ENABLE_KEY
        boneTriggerKeyField.text = BONE_TRIGGER_KEY.toString()
    }

    //Misc Tab
    miscTab.apply {
        enableBunnyHop.isChecked = ENABLE_BUNNY_HOP
        enableBombTimer.isChecked = ENABLE_BOMB_TIMER
        fireKeyField.text = FIRE_KEY.toString()
        visualsToggleKeyField.text = VISUALS_TOGGLE_KEY.toString()
        menuKeyField.text = MENU_KEY.toString()
        enableReducedFlash.isChecked = ENABLE_REDUCED_FLASH
        flashMaxAlphaLabel.setText("Flash Max Alpha: $FLASH_MAX_ALPHA" + when (FLASH_MAX_ALPHA.toString().length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        flashMaxAlphaSlider.value = FLASH_MAX_ALPHA
        hitSound.isChecked = ENABLE_HITSOUND
        hitSoundVolumeLabel.setText("Hitsound Volume: $HITSOUND_VOLUME")
        hitSoundVolumeSlider.value = HITSOUND_VOLUME.toFloat()
    }

    //Custom disable items
    rcsTab.apply {
        enableRCS.isChecked = ENABLE_RCS
        rcsSmoothingSlider.isDisabled = !ENABLE_RCS
        rcsReturnAim.isDisabled = !ENABLE_RCS
        enableRCrosshair.isChecked = ENABLE_RECOIL_CROSSHAIR
        rCrosshairWidthSlider.isDisabled = !ENABLE_RECOIL_CROSSHAIR
        rcsCrosshairLengthSlider.isDisabled = !ENABLE_RECOIL_CROSSHAIR
        rCrosshairAlphaSlider.isDisabled = !ENABLE_RECOIL_CROSSHAIR
        rCrosshairColorShow.isDisabled = !ENABLE_RECOIL_CROSSHAIR
    }
}