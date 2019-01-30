package rat.poison.ui

import rat.poison.settings.*

//shoot me

fun UIUpdate() {
    //General Aim Tab
    activateFromFireKey.isChecked = ACTIVATE_FROM_FIRE_KEY
    teammatesAreEnemies.isChecked = TEAMMATES_ARE_ENEMIES
    forceAimKeyField.text = FORCE_AIM_KEY.toString()

    //Rifle Aim Tab
    aimRifleTab.apply {
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

    //Pistol Aim Tab
    aimPistolTab.apply {
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

    //Sniper Aim Tab
    aimSniperTab.apply {
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

    //Shotgun Aim Tab
    aimShotgunTab.apply {
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

    //Scripts Tab
    scriptsTab.apply {
        enableBunnyHop.isChecked = ENABLE_BUNNY_HOP
        enableRCS.isChecked = ENABLE_RCS
        enableRCrosshair.isChecked = ENABLE_RECOIL_CROSSHAIR
        enableEsp.isChecked = ENABLE_ESP
        enableBoneTrigger.isChecked = ENABLE_BONE_TRIGGER
        enableReducedFlash.isChecked = ENABLE_REDUCED_FLASH
        enableBombTimer.isChecked = ENABLE_BOMB_TIMER
    }

    //Esp Tab
    espTab.apply {
        skeletonEsp.isChecked = SKELETON_ESP
        boxEsp.isChecked = BOX_ESP
        boxEspDetails.isChecked = BOX_ESP_DETAILS
        glowEsp.isChecked = GLOW_ESP
        invGlowEsp.isChecked = INV_GLOW_ESP
        modelEsp.isChecked = MODEL_ESP
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
        teamColorShow.setColor(TEAM_COLOR.red.toFloat(), TEAM_COLOR.green.toFloat(), TEAM_COLOR.blue.toFloat(), TEAM_COLOR.alpha.toFloat())
        enemyColorShow.setColor(ENEMY_COLOR.red.toFloat(), ENEMY_COLOR.green.toFloat(), ENEMY_COLOR.blue.toFloat(), ENEMY_COLOR.alpha.toFloat())
        bombColorShow.setColor(BOMB_COLOR.red.toFloat(), BOMB_COLOR.green.toFloat(), BOMB_COLOR.blue.toFloat(), BOMB_COLOR.alpha.toFloat())
        weaponColorShow.setColor(WEAPON_COLOR.red.toFloat(), WEAPON_COLOR.green.toFloat(), WEAPON_COLOR.blue.toFloat(), WEAPON_COLOR.alpha.toFloat())
        chamsColorShow.setColor(CHAMS_ESP_COLOR.red.toFloat(), CHAMS_ESP_COLOR.green.toFloat(), CHAMS_ESP_COLOR.blue.toFloat(), CHAMS_ESP_COLOR.alpha.toFloat())
    }

    //Rcs Tab
    rcsTab.apply {
        rcsSmoothingLabel.setText("RCS Smoothing: $RCS_SMOOTHING")
        rcsSmoothingSlider.value = RCS_SMOOTHING.toFloat()
        rcsReturnAim.isChecked = RCS_RETURNAIM
    }

    //BTrig Tab
    bTrigTab.apply {
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
    misc.leagueMode.isChecked = LEAGUE_MODE
    misc.fireKeyField.text = FIRE_KEY.toString()
    misc.visualsToggleKeyField.text = VISUALS_TOGGLE_KEY.toString()
    misc.menuKeyField.text = MENU_KEY.toString()
    misc.flashMaxAlphaLabel.setText("Flash Max Alpha: $FLASH_MAX_ALPHA" + when(FLASH_MAX_ALPHA.toString().length) {3->"  " 2->"    " else ->"      "})
    misc.flashMaxAlphaSlider.value = FLASH_MAX_ALPHA

    //Custom disable tabs
    mainTabbedPane.disableTab(bTrigTab, !ENABLE_BONE_TRIGGER)
    mainTabbedPane.disableTab(espTab, !ENABLE_ESP)
    mainTabbedPane.disableTab(rcsTab, (!ENABLE_RCS && !ENABLE_RECOIL_CROSSHAIR))

    //Custom disable items
    rcsTab.apply {
        rcsSmoothingSlider.isDisabled = !ENABLE_RCS
        rcsReturnAim.isDisabled = !ENABLE_RCS
        rCrosshairWidthSlider.isDisabled = !ENABLE_RECOIL_CROSSHAIR
        rcsCrosshairLengthSlider.isDisabled = !ENABLE_RECOIL_CROSSHAIR
        rCrosshairAlphaSlider.isDisabled = !ENABLE_RECOIL_CROSSHAIR
        rCrosshairColorShow.isDisabled = !ENABLE_RECOIL_CROSSHAIR
    }
}