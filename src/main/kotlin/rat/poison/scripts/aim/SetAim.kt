package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.game.Weapons
import rat.poison.game.entity.weapon
import rat.poison.game.me
import rat.poison.settingsLoaded
import rat.poison.strToBool
import rat.poison.toWeaponClass
import rat.poison.utils.every

var override = false
var curWep = Weapons.AK47

fun setAim() = every(100, true) {
    try {
        override = false
        curWep = me.weapon()

        if (settingsLoaded) { //If we have settings to read
            if (curSettings["ENABLE_OVERRIDE"].strToBool()) {

                //V--Update aim settings for current weapons--V\\
                if (curWep.rifle || curWep.smg || curWep.pistol || curWep.sniper || curWep.shotgun) {
                    val curWepSettings = curSettings[curWep.name].toWeaponClass()

                    if (curWepSettings.tOverride) {
                        curSettings["FACTOR_RECOIL"] = curWepSettings.tFRecoil
                        curSettings["ENABLE_FLAT_AIM"] = curWepSettings.tFlatAim
                        curSettings["ENABLE_PATH_AIM"] = curWepSettings.tPathAim
                        curSettings["AIM_BONE"] = curWepSettings.tAimBone
                        curSettings["AIM_FOV"] = curWepSettings.tAimFov
                        curSettings["AIM_SPEED"] = curWepSettings.tAimSpeed
                        curSettings["AIM_SMOOTHNESS"] = curWepSettings.tAimSmooth
                        curSettings["PERFECT_AIM"] = curWepSettings.tPerfectAim
                        curSettings["PERFECT_AIM_FOV"] = curWepSettings.tPAimFov
                        curSettings["PERFECT_AIM_CHANCE"] = curWepSettings.tPAimChance
                        curSettings["ENABLE_SCOPED_ONLY"] = curWepSettings.tScopedOnly

                        if (curWep.rifle || curWep.smg) {
                            curSettings["AIM_AFTER_SHOTS"] = curWepSettings.tAimAfterShots
                        }

                        //Advanced advanced aim settings

                        override = true
                    }
                }
            }
        }

        if (!override) { //If the current weapon isn't checked to override
            var strPre = "" //Prefix for settings wep
            when { //Set the aim settings to the weapon's category settings
                curWep.rifle -> {
                    strPre = "RIFLE"
                }
                curWep.smg -> {
                    strPre = "SMG"
                }
                curWep.pistol -> {
                    strPre = "PISTOL"
                }
                curWep.sniper -> {
                    strPre = "SNIPER"
                }
                curWep.shotgun -> {
                    strPre = "SHOTGUN"
                }
            }

            if (strPre != "") {
                curSettings["FACTOR_RECOIL"] = curSettings[strPre + "_FACTOR_RECOIL"].strToBool()
                curSettings["AIM_BONE"] = curSettings[strPre + "_AIM_BONE"].toInt()
                curSettings["FORCE_AIM_BONE"] = curSettings[strPre + "_AIM_FORCE_BONE"].toInt()
                curSettings["AIM_FOV"] = curSettings[strPre + "_AIM_FOV"].toInt()
                curSettings["AIM_SPEED"] = curSettings[strPre + "_AIM_SPEED"].toInt()
                curSettings["AIM_SMOOTHNESS"] = curSettings[strPre + "_AIM_SMOOTHNESS"].toDouble()

                curSettings["PERFECT_AIM"] = curSettings[strPre + "_PERFECT_AIM"].strToBool()
                curSettings["PERFECT_AIM_FOV"] = curSettings[strPre + "_PERFECT_AIM_FOV"].toInt()
                curSettings["PERFECT_AIM_CHANCE"] = curSettings[strPre + "_PERFECT_AIM_CHANCE"].toInt()
                curSettings["ENABLE_FLAT_AIM"] = curSettings[strPre + "_ENABLE_FLAT_AIM"].strToBool()
                curSettings["ENABLE_PATH_AIM"] = curSettings[strPre + "_ENABLE_PATH_AIM"].strToBool()
                curSettings["ENABLE_SCOPED_ONLY"] = curSettings["SNIPER_ENABLE_SCOPED_ONLY"].strToBool()

                curSettings["AIM_ONLY_ON_SHOT"] = curSettings[strPre + "_AIM_ONLY_ON_SHOT"].strToBool()
                curSettings["AIM_AFTER_SHOTS"] = curSettings[strPre + "_AIM_AFTER_SHOTS"].toInt()

                curSettings["AIM_ADVANCED"] = curSettings[strPre + "_ADVANCED_SETTINGS"].strToBool()
                curSettings["AIM_RCS_X"] = curSettings[strPre + "_AIM_RCS_X"].toDouble()
                curSettings["AIM_RCS_Y"] = curSettings[strPre + "_AIM_RCS_Y"].toDouble()
                curSettings["AIM_RCS_VARIATION"] = curSettings[strPre + "_AIM_RCS_VARIATION"].toDouble()
                curSettings["AIM_SPEED_DIVISOR"] = curSettings[strPre + "_AIM_SPEED_DIVISOR"].toInt()
                curSettings["AIM_RANDOM_X_VARIATION"] = curSettings[strPre + "_RANDOM_X_VARIATION"].toInt()
                curSettings["AIM_RANDOM_Y_VARIATION"] = curSettings[strPre + "_RANDOM_Y_VARIATION"].toInt()
                curSettings["AIM_VARIATION_DEADZONE"] = curSettings[strPre + "_VARIATION_DEADZONE"].toInt()
            }
        }
    } catch (e: Exception) { println("SetAim failure") } //Fix crashing
}