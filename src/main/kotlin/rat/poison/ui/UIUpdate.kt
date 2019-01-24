package rat.poison.ui

import rat.poison.settings.*

//shoot me

fun UIUpdate() {
    //AimKts Tab
    aimKts.aimBoneBox.selected = if (AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
    aimKts.activateFromFireKey.isChecked = ACTIVATE_FROM_FIRE_KEY //Don't know if works
    aimKts.teammatesAreEnemies.isChecked = TEAMMATES_ARE_ENEMIES
    aimKts.forceAimKeyField.text = FORCE_AIM_KEY.toString()
    aimKts.aimFovLabel.setText("Aim Fov: " + AIM_FOV.toString() + when(AIM_FOV.toString().length) {3->"  " 2->"    " else->"      "})
    aimKts.aimFovSlider.value = AIM_FOV.toFloat()
    aimKts.aimSpeedMinLabel.setText("Aim Speed Min: " + AIM_SPEED_MIN.toString() + when(AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else->"      "})
    aimKts.aimSpeedMinSlider.value = AIM_SPEED_MIN.toFloat()
    aimKts.aimSpeedMaxLabel.setText("Aim Speed Max: " + AIM_SPEED_MAX.toString() + when(AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else->"      "})
    aimKts.aimSpeedMaxSlider.value = AIM_SPEED_MAX.toFloat()
    aimKts.aimStrictnessLabel.setText("Aim Strictness: " + AIM_STRICTNESS.toString())
    aimKts.aimStrictnessSlider.value = AIM_STRICTNESS.toFloat()
    aimKts.perfectAimCheckBox.isChecked = PERFECT_AIM
    aimKts.perfectAimCollapsible.isCollapsed = !PERFECT_AIM
    aimKts.perfectAimFovLabel.setText("Perfect Aim Fov: " + PERFECT_AIM_FOV.toString() + when(PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else->"      "})
    aimKts.perfectAimFovSlider.value = PERFECT_AIM_FOV.toFloat()
    aimKts.perfectAimChanceLabel.setText("Perfect Aim Chance: " + PERFECT_AIM_CHANCE.toString() + when(PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else->"      "})
    aimKts.perfectAimChanceSlider.value = PERFECT_AIM_CHANCE.toFloat()
    aimKts.aimAssistCheckBox.isChecked = AIM_ASSIST_MODE
    aimKts.aimAssistCollapsible.isCollapsed = !AIM_ASSIST_MODE
    aimKts.aimAssistStrictnessLabel.setText("Aim Assist Strictness: " + AIM_ASSIST_STRICTNESS.toString() + when(AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else->"      "})
    aimKts.aimAssistStrictnessSlider.value = AIM_ASSIST_STRICTNESS.toFloat()

    //GeneralKts Tab
    generalKts.leagueModeToggle.isChecked = LEAGUE_MODE
    generalKts.fireKeyField.text = FIRE_KEY.toString()
    generalKts.visualsToggleKeyField.text = VISUALS_TOGGLE_KEY.toString()
    generalKts.menuKeyField.text = MENU_KEY.toString()

    //ScriptsKts Tab
    scriptsKts.enableBunnyHopToggle.isChecked = ENABLE_BUNNY_HOP
    scriptsKts.enableRCSToggle.isChecked = ENABLE_RCS
    scriptsKts.enableRCrosshairToggle.isChecked = ENABLE_RECOIL_CROSSHAIR
    scriptsKts.enableEspToggle.isChecked = ENABLE_ESP
    scriptsKts.enableFlatAimToggle.isChecked = ENABLE_FLAT_AIM
    scriptsKts.enablePathAimToggle.isChecked = ENABLE_PATH_AIM
    scriptsKts.enableBoneTriggerToggle.isChecked = ENABLE_BONE_TRIGGER
    scriptsKts.enableReducedFlashToggle.isChecked = ENABLE_REDUCED_FLASH
    scriptsKts.enableBombTimerToggle.isChecked = ENABLE_BOMB_TIMER

    //EspKts Tab
    espKts.enableSkeletonEspToggle.isChecked = SKELETON_ESP
    espKts.enableBoxEspToggle.isChecked = BOX_ESP
    espKts.enableBoxEspDetailsToggle.isChecked = BOX_ESP_DETAILS
    espKts.enableGlowEspToggle.isChecked = GLOW_ESP
    espKts.enableModelEspToggle.isChecked = MODEL_ESP
    espKts.enableChamsEspToggle.isChecked = CHAMS_ESP
    espKts.enableChamsShowHealthToggle.isChecked = CHAMS_SHOW_HEALTH
    espKts.chamsBrightnessLabel.setText("Chams Brightness: " + CHAMS_BRIGHTNESS.toString() + when(CHAMS_BRIGHTNESS.toString().length) {4->"  " 3->"    " 2->"      " else->"        "})
    espKts.chamsBrightnessSlider.value = CHAMS_BRIGHTNESS.toFloat()
    espKts.enableShowTeamToggle.isChecked = SHOW_TEAM
    espKts.enableShowEnemiesToggle.isChecked = SHOW_ENEMIES
    espKts.enableShowDormantToggle.isChecked = SHOW_DORMANT
    espKts.enableShowBombToggle.isChecked = SHOW_BOMB
    espKts.enableShowWeaponsToggle.isChecked = SHOW_WEAPONS
    espKts.enableShowGrenadesToggle.isChecked = SHOW_GRENADES
    espKts.teamColorShow.setColor(TEAM_COLOR.red.toFloat(), TEAM_COLOR.green.toFloat(), TEAM_COLOR.blue.toFloat(), TEAM_COLOR.alpha.toFloat())
    espKts.enemyColorShow.setColor(ENEMY_COLOR.red.toFloat(), ENEMY_COLOR.green.toFloat(), ENEMY_COLOR.blue.toFloat(), ENEMY_COLOR.alpha.toFloat())
    espKts.bombColorShow.setColor(BOMB_COLOR.red.toFloat(), BOMB_COLOR.green.toFloat(), BOMB_COLOR.blue.toFloat(), BOMB_COLOR.alpha.toFloat())
    espKts.weaponColorShow.setColor(WEAPON_COLOR.red.toFloat(), WEAPON_COLOR.green.toFloat(), WEAPON_COLOR.blue.toFloat(), WEAPON_COLOR.alpha.toFloat())
    espKts.chamsColorShow.setColor(GRENADE_COLOR.red.toFloat(), GRENADE_COLOR.green.toFloat(), GRENADE_COLOR.blue.toFloat(), GRENADE_COLOR.alpha.toFloat())

    //Misc Tab
    misc.boneTriggerFovLabel.setText("Bone Trigger Fov: " + BONE_TRIGGER_FOV.toString() + when(BONE_TRIGGER_FOV.toString().length) {3->"  " 2->"    " else->"      "})
    misc.boneTriggerFovSlider.value = BONE_TRIGGER_FOV.toFloat()
    misc.boneTriggerBoneBox.selected = if (BONE_TRIGGER_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
    misc.aimOnBoneTrigger.isChecked = AIM_ON_BONE_TRIGGER
    misc.boneTriggerEnableKey.isChecked = BONE_TRIGGER_ENABLE_KEY
    misc.boneTriggerKeyField.text = BONE_TRIGGER_KEY.toString()
    misc.rcsMinLabel.setText("RCS Min: " + RCS_MIN.toString() + when(RCS_MIN.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "})
    misc.rcsMinSlider.value = RCS_MIN.toFloat()
    misc.rcsMaxLabel.setText("RCS Max: " + RCS_MAX.toString() + when(RCS_MAX.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "})
    misc.rcsMaxSlider.value = RCS_MAX.toFloat()
    misc.rcsMinDurationLabel.setText("RCS Min Duration: " + RCS_MIN_DURATION.toString() + when(RCS_MIN_DURATION.toString().length) {2->"  " else->"    "})
    misc.rcsMinDurationSlider.value = RCS_MIN_DURATION.toFloat()
    misc.rcsMaxDurationLabel.setText("RCS Max Duration: " + RCS_MAX_DURATION.toString() + when(RCS_MAX_DURATION.toString().length) {2->"  " else->"    "})
    misc.rcsMaxDurationSlider.value = RCS_MAX_DURATION.toFloat()
    misc.flashMaxAlphaLabel.setText("Flash Max Alpha: " + FLASH_MAX_ALPHA.toString() + when(FLASH_MAX_ALPHA.toString().length) {3->"  " 2->"    " else ->"      "})
    misc.flashMaxAlphaSlider.value = FLASH_MAX_ALPHA
    //misc.drawFov.isChecked = DRAW_FOV
    misc.enemyIndicator.isChecked = ENEMY_INDICATOR

}