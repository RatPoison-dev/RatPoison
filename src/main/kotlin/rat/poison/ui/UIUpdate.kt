package rat.poison.ui

import rat.poison.settings.*

//shoot me

fun UIUpdate() {
    //Aim Tab
    aimTab.apply {
        aimBoneBox.selected = if (AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
        activateFromFireKey.isChecked = ACTIVATE_FROM_FIRE_KEY //Don't know if works
        teammatesAreEnemies.isChecked = TEAMMATES_ARE_ENEMIES
        forceAimKeyField.text = FORCE_AIM_KEY.toString()
        aimFovLabel.setText("Aim Fov: $AIM_FOV" + when (AIM_FOV.toString().length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        aimFovSlider.value = AIM_FOV.toFloat()
        aimSpeedMinLabel.setText("Aim Speed Min: $AIM_SPEED_MIN" + when (AIM_SPEED_MIN.toString().length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        aimSpeedMinSlider.value = AIM_SPEED_MIN.toFloat()
        aimSpeedMaxLabel.setText("Aim Speed Max: $AIM_SPEED_MAX" + when (AIM_SPEED_MAX.toString().length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        aimSpeedMaxSlider.value = AIM_SPEED_MAX.toFloat()
        aimStrictnessLabel.setText("Aim Strictness: $AIM_STRICTNESS")
        aimStrictnessSlider.value = AIM_STRICTNESS.toFloat()
        perfectAimCheckBox.isChecked = PERFECT_AIM
        perfectAimCollapsible.isCollapsed = !PERFECT_AIM
        perfectAimFovLabel.setText("Perfect Aim Fov: $PERFECT_AIM_FOV" + when (PERFECT_AIM_FOV.toString().length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        perfectAimFovSlider.value = PERFECT_AIM_FOV.toFloat()
        perfectAimChanceLabel.setText("Perfect Aim Chance: $PERFECT_AIM_CHANCE" + when (PERFECT_AIM_CHANCE.toString().length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        perfectAimChanceSlider.value = PERFECT_AIM_CHANCE.toFloat()
        aimAssistCheckBox.isChecked = AIM_ASSIST_MODE
        aimAssistCollapsible.isCollapsed = !AIM_ASSIST_MODE
        aimAssistStrictnessLabel.setText("Aim Assist Strictness: $AIM_ASSIST_STRICTNESS" + when (AIM_ASSIST_STRICTNESS.toString().length) {
            3 -> "  "
            2 -> "    "
            else -> "      "
        })
        aimAssistStrictnessSlider.value = AIM_ASSIST_STRICTNESS.toFloat()
    }

    //Scripts Tab
    scriptsTab.apply {
        enableBunnyHopToggle.isChecked = ENABLE_BUNNY_HOP
        enableRCSToggle.isChecked = ENABLE_RCS
        enableRCrosshairToggle.isChecked = ENABLE_RECOIL_CROSSHAIR
        enableEspToggle.isChecked = ENABLE_ESP
        enableFlatAimToggle.isChecked = ENABLE_FLAT_AIM
        enablePathAimToggle.isChecked = ENABLE_PATH_AIM
        enableBoneTriggerToggle.isChecked = ENABLE_BONE_TRIGGER
        enableReducedFlashToggle.isChecked = ENABLE_REDUCED_FLASH
        enableBombTimerToggle.isChecked = ENABLE_BOMB_TIMER
    }

    //Esp Tab
    espTab.apply {
        enableSkeletonEspToggle.isChecked = SKELETON_ESP
        enableBoxEspToggle.isChecked = BOX_ESP
        enableBoxEspDetailsToggle.isChecked = BOX_ESP_DETAILS
        enableGlowEspToggle.isChecked = GLOW_ESP
        enableModelEspToggle.isChecked = MODEL_ESP
        enemyIndicator.isChecked = ENEMY_INDICATOR
        enableChamsEspToggle.isChecked = CHAMS_ESP
        enableChamsShowHealthToggle.isChecked = CHAMS_SHOW_HEALTH
        chamsBrightnessLabel.setText("Chams Brightness: $CHAMS_BRIGHTNESS" + when (CHAMS_BRIGHTNESS.toString().length) {
            4 -> "  "
            3 -> "    "
            2 -> "      "
            else -> "        "
        })
        chamsBrightnessSlider.value = CHAMS_BRIGHTNESS.toFloat()
        enableShowTeamToggle.isChecked = SHOW_TEAM
        enableShowEnemiesToggle.isChecked = SHOW_ENEMIES
        enableShowDormantToggle.isChecked = SHOW_DORMANT
        enableShowBombToggle.isChecked = SHOW_BOMB
        enableShowWeaponsToggle.isChecked = SHOW_WEAPONS
        enableShowGrenadesToggle.isChecked = SHOW_GRENADES
        teamColorShow.setColor(TEAM_COLOR.red.toFloat(), TEAM_COLOR.green.toFloat(), TEAM_COLOR.blue.toFloat(), TEAM_COLOR.alpha.toFloat())
        enemyColorShow.setColor(ENEMY_COLOR.red.toFloat(), ENEMY_COLOR.green.toFloat(), ENEMY_COLOR.blue.toFloat(), ENEMY_COLOR.alpha.toFloat())
        bombColorShow.setColor(BOMB_COLOR.red.toFloat(), BOMB_COLOR.green.toFloat(), BOMB_COLOR.blue.toFloat(), BOMB_COLOR.alpha.toFloat())
        weaponColorShow.setColor(WEAPON_COLOR.red.toFloat(), WEAPON_COLOR.green.toFloat(), WEAPON_COLOR.blue.toFloat(), WEAPON_COLOR.alpha.toFloat())
        chamsColorShow.setColor(CHAMS_ESP_COLOR.red.toFloat(), CHAMS_ESP_COLOR.green.toFloat(), CHAMS_ESP_COLOR.blue.toFloat(), CHAMS_ESP_COLOR.alpha.toFloat())
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
    misc.leagueModeToggle.isChecked = LEAGUE_MODE
    misc.fireKeyField.text = FIRE_KEY.toString()
    misc.visualsToggleKeyField.text = VISUALS_TOGGLE_KEY.toString()
    misc.menuKeyField.text = MENU_KEY.toString()
    misc.flashMaxAlphaLabel.setText("Flash Max Alpha: $FLASH_MAX_ALPHA" + when(FLASH_MAX_ALPHA.toString().length) {3->"  " 2->"    " else ->"      "})
    misc.flashMaxAlphaSlider.value = FLASH_MAX_ALPHA

    //Custom disable tabs
    tabbedPane.disableTab(bTrigTab, !ENABLE_BONE_TRIGGER)
    tabbedPane.disableTab(espTab, !ENABLE_ESP)
    tabbedPane.disableTab(rcsTab, !ENABLE_RCS)
}