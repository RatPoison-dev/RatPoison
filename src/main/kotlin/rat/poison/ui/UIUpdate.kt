package rat.poison.ui

import rat.poison.settings.*

//shoot me

fun UIUpdate() {
    //Aim Tab
    aimTab.apply { //CLEAN THIS BITCH UUUUUPPP
        activateFromFireKey.isChecked = ACTIVATE_FROM_FIRE_KEY
        teammatesAreEnemies.isChecked = TEAMMATES_ARE_ENEMIES
        forceAimKeyField.text = FORCE_AIM_KEY.toString()

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
                aimSpeedMinLabel.setText("Aim Speed Min: $PISTOL_AIM_SPEED_MIN" + when (PISTOL_AIM_SPEED_MIN.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedMinSlider.value = PISTOL_AIM_SPEED_MIN.toFloat()
                aimSpeedMaxLabel.setText("Aim Speed Max: $PISTOL_AIM_SPEED_MAX" + when (PISTOL_AIM_SPEED_MAX.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedMaxSlider.value = PISTOL_AIM_SPEED_MAX.toFloat()
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
                aimSpeedMinLabel.setText("Aim Speed Min: $RIFLE_AIM_SPEED_MIN" + when (RIFLE_AIM_SPEED_MIN.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedMinSlider.value = RIFLE_AIM_SPEED_MIN.toFloat()
                aimSpeedMaxLabel.setText("Aim Speed Max: $RIFLE_AIM_SPEED_MAX" + when (RIFLE_AIM_SPEED_MAX.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedMaxSlider.value = RIFLE_AIM_SPEED_MAX.toFloat()
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
                aimSpeedMinLabel.setText("Aim Speed Min: $SMG_AIM_SPEED_MIN" + when (SMG_AIM_SPEED_MIN.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedMinSlider.value = SMG_AIM_SPEED_MIN.toFloat()
                aimSpeedMaxLabel.setText("Aim Speed Max: $SMG_AIM_SPEED_MAX" + when (SMG_AIM_SPEED_MAX.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedMaxSlider.value = SMG_AIM_SPEED_MAX.toFloat()
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
                aimSpeedMinLabel.setText("Aim Speed Min: $SNIPER_AIM_SPEED_MIN" + when (SNIPER_AIM_SPEED_MIN.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedMinSlider.value = SNIPER_AIM_SPEED_MIN.toFloat()
                aimSpeedMaxLabel.setText("Aim Speed Max: $SNIPER_AIM_SPEED_MAX" + when (SNIPER_AIM_SPEED_MAX.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedMaxSlider.value = SNIPER_AIM_SPEED_MAX.toFloat()
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
                aimSpeedMinLabel.setText("Aim Speed Min: $SHOTGUN_AIM_SPEED_MIN" + when (SHOTGUN_AIM_SPEED_MIN.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedMinSlider.value = SHOTGUN_AIM_SPEED_MIN.toFloat()
                aimSpeedMaxLabel.setText("Aim Speed Max: $SHOTGUN_AIM_SPEED_MAX" + when (SHOTGUN_AIM_SPEED_MAX.toString().length) {
                    3 -> "  "
                    2 -> "    "
                    else -> "      "
                })
                aimSpeedMaxSlider.value = SHOTGUN_AIM_SPEED_MAX.toFloat()
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
        skeletonEsp.isChecked = SKELETON_ESP
        boxEsp.isChecked = BOX_ESP
        boxEspDetails.isChecked = BOX_ESP_DETAILS
        glowEsp.isChecked = GLOW_ESP
        invGlowEsp.isChecked = INV_GLOW_ESP
        modelEsp.isChecked = MODEL_ESP
        modelAndGlow.isChecked = MODEL_AND_GLOW
        enemyIndicator.isChecked = ENEMY_INDICATOR
        hitSound.isChecked = ENABLE_HITSOUND
        hitSoundVolumeLabel.setText("Hitsound Volume: $HITSOUND_VOLUME")
        hitSoundVolumeSlider.value = HITSOUND_VOLUME.toFloat()
        chamsEsp.isChecked = CHAMS_ESP
        chamsShowHealth.isChecked = CHAMS_SHOW_HEALTH
        chamsBrightnessLabel.setText("Chams Brightness: $CHAMS_BRIGHTNESS" + when (CHAMS_BRIGHTNESS.toString().length) {
            4 -> "  "
            3 -> "    "
            2 -> "      "
            else -> "        "
        })
        chamsBrightnessSlider.value = CHAMS_BRIGHTNESS.toFloat()
        showTeam.isChecked = SHOW_TEAM
        showEnemies.isChecked = SHOW_ENEMIES
        showDormant.isChecked = SHOW_DORMANT
        showBomb.isChecked = SHOW_BOMB
        showWeapons.isChecked = SHOW_WEAPONS
        showGrenades.isChecked = SHOW_GRENADES
        teamColorShow.setColor(TEAM_COLOR.red.toFloat(), TEAM_COLOR.green.toFloat(), TEAM_COLOR.blue.toFloat(), 1F)
        enemyColorShow.setColor(ENEMY_COLOR.red.toFloat(), ENEMY_COLOR.green.toFloat(), ENEMY_COLOR.blue.toFloat(), 1F)
        bombColorShow.setColor(BOMB_COLOR.red.toFloat(), BOMB_COLOR.green.toFloat(), BOMB_COLOR.blue.toFloat(), 1F)
        weaponColorShow.setColor(WEAPON_COLOR.red.toFloat(), WEAPON_COLOR.green.toFloat(), WEAPON_COLOR.blue.toFloat(), 1F)
        grenadeColor.setColor(GRENADE_COLOR.red.toFloat(), GRENADE_COLOR.green.toFloat(), GRENADE_COLOR.blue.toFloat(), 1F)
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
        boneTriggerBoneBox.selected = if (BONE_TRIGGER_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
        aimOnBoneTrigger.isChecked = AIM_ON_BONE_TRIGGER
        boneTriggerEnableKey.isChecked = BONE_TRIGGER_ENABLE_KEY
        boneTriggerKeyField.text = BONE_TRIGGER_KEY.toString()
    }

    //Misc Tab
    misc.apply {
        leagueMode.isChecked = LEAGUE_MODE
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