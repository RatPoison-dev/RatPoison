package rat.poison.ui

import rat.poison.settings.*

//shoot me

fun UIUpdate() {
    //AimKts Tab
    aimkts.aimBoneBox.selected = if (AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
    aimkts.activateFromFireKey.isChecked = ACTIVATE_FROM_FIRE_KEY //Don't know if works
    aimkts.teammatesAreEnemies.isChecked = TEAMMATES_ARE_ENEMIES
    aimkts.forceAimKeyField.text = FORCE_AIM_KEY.toString()
    aimkts.aimFovLabel.setText("Aim Fov: " + AIM_FOV.toString() + when(AIM_FOV.toString().length) {3->"  " 2->"    " else->"      "})
    aimkts.aimFovSlider.value = AIM_FOV.toFloat()
    aimkts.aimSpeedMinLabel.setText("Aim Speed Min: " + AIM_SPEED_MIN.toString() + when(AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else->"      "})
    aimkts.aimSpeedMinSlider.value = AIM_SPEED_MIN.toFloat()
    aimkts.aimSpeedMaxLabel.setText("Aim Speed Max: " + AIM_SPEED_MAX.toString() + when(AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else->"      "})
    aimkts.aimSpeedMaxSlider.value = AIM_SPEED_MAX.toFloat()
    aimkts.aimStrictnessLabel.setText("Aim Strictness: " + AIM_STRICTNESS.toString())
    aimkts.aimStrictnessSlider.value = AIM_STRICTNESS.toFloat()
    aimkts.perfectAimCheckBox.isChecked = PERFECT_AIM
    aimkts.perfectAimCollapsible.isCollapsed = !PERFECT_AIM
    aimkts.perfectAimFovLabel.setText("Perfect Aim Fov: " + PERFECT_AIM_FOV.toString() + when(PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else->"      "})
    aimkts.perfectAimFovSlider.value = PERFECT_AIM_FOV.toFloat()
    aimkts.perfectAimChanceLabel.setText("Perfect Aim Chance: " + PERFECT_AIM_CHANCE.toString() + when(PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else->"      "})
    aimkts.perfectAimChanceSlider.value = PERFECT_AIM_CHANCE.toFloat()
    aimkts.aimAssistCheckBox.isChecked = AIM_ASSIST_MODE
    aimkts.aimAssistCollapsible.isCollapsed = !AIM_ASSIST_MODE
    aimkts.aimAssistStrictnessLabel.setText("Aim Assist Strictness: " + AIM_ASSIST_STRICTNESS.toString() + when(AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else->"      "})
    aimkts.aimAssistStrictnessSlider.value = AIM_ASSIST_STRICTNESS.toFloat()

    //GeneralKts Tab
    generalkts.leagueModeToggle.isChecked = LEAGUE_MODE
    generalkts.fireKeyField.text = FIRE_KEY.toString()
    generalkts.visualsToggleKeyField.text = VISUALS_TOGGLE_KEY.toString()
    generalkts.menuKeyField.text = MENU_KEY.toString()

    //ScriptsKts Tab
    scriptskts.enableBunnyHopToggle.isChecked = ENABLE_BUNNY_HOP
    scriptskts.enableRCSToggle.isChecked = ENABLE_RCS
    scriptskts.enableRCrosshairToggle.isChecked = ENABLE_RECOIL_CROSSHAIR
    scriptskts.enableEspToggle.isChecked = ENABLE_ESP
    scriptskts.enableFlatAimToggle.isChecked = ENABLE_FLAT_AIM
    scriptskts.enablePathAimToggle.isChecked = ENABLE_PATH_AIM
    scriptskts.enableBoneTriggerToggle.isChecked = ENABLE_BONE_TRIGGER
    scriptskts.enableReducedFlashToggle.isChecked = ENABLE_REDUCED_FLASH
    scriptskts.enableBombTimerToggle.isChecked = ENABLE_BOMB_TIMER

    //EspKts Tab
    espkts.enableSkeletonEspToggle.isChecked = SKELETON_ESP
    espkts.enableBoxEspToggle.isChecked = BOX_ESP
    espkts.enableGlowEspToggle.isChecked = GLOW_ESP
    espkts.enableModelEspToggle.isChecked = MODEL_ESP
    espkts.enableChamsEspToggle.isChecked = CHAMS_ESP
    espkts.enableChamsShowHealthToggle.isChecked = CHAMS_SHOW_HEALTH
    espkts.chamsBrightnessLabel.setText("Chams Brightness: " + CHAMS_BRIGHTNESS.toString() + when(CHAMS_BRIGHTNESS.toString().length) {4->"  " 3->"    " 2->"      " else->"        "})
    espkts.chamsBrightnessSlider.value = CHAMS_BRIGHTNESS.toFloat()
    espkts.enableShowTeamToggle.isChecked = SHOW_TEAM
    espkts.enableShowEnemiesToggle.isChecked = SHOW_ENEMIES
    espkts.enableShowDormantToggle.isChecked = SHOW_DORMANT
    espkts.enableShowBombToggle.isChecked = SHOW_BOMB
    espkts.enableShowWeaponsToggle.isChecked = SHOW_WEAPONS
    espkts.enableShowGrenadesToggle.isChecked = SHOW_GRENADES
    espkts.teamColorShow.setColor(TEAM_COLOR.red.toFloat(), TEAM_COLOR.green.toFloat(), TEAM_COLOR.blue.toFloat(), TEAM_COLOR.alpha.toFloat())
    espkts.enemyColorShow.setColor(ENEMY_COLOR.red.toFloat(), ENEMY_COLOR.green.toFloat(), ENEMY_COLOR.blue.toFloat(), ENEMY_COLOR.alpha.toFloat())
    espkts.bombColorShow.setColor(BOMB_COLOR.red.toFloat(), BOMB_COLOR.green.toFloat(), BOMB_COLOR.blue.toFloat(), BOMB_COLOR.alpha.toFloat())
    espkts.weaponColorShow.setColor(WEAPON_COLOR.red.toFloat(), WEAPON_COLOR.green.toFloat(), WEAPON_COLOR.blue.toFloat(), WEAPON_COLOR.alpha.toFloat())
    espkts.chamsColorShow.setColor(GRENADE_COLOR.red.toFloat(), GRENADE_COLOR.green.toFloat(), GRENADE_COLOR.blue.toFloat(), GRENADE_COLOR.alpha.toFloat())

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