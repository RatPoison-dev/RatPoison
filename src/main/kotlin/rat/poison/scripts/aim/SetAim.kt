package rat.poison.scripts.aim

import rat.poison.curSettings
import rat.poison.game.Weapons
import rat.poison.game.entity.weapon
import rat.poison.game.entity.weaponEntity
import rat.poison.game.me
import rat.poison.oWeapon
import rat.poison.settings.*
import rat.poison.settingsLoaded
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.toWeaponClass

var meCurWep = Weapons.AK47
var meCurWepEnt = 0L
var curWepOverride = false
var curWepCategory = "PISTOL"
var curWepSettings = oWeapon()
var haveAimSettings = false

fun setAim() = every(500, true, inGameCheck = true) {
    try {
        if (meDead) return@every

        meCurWep = me.weapon()
        meCurWepEnt = me.weaponEntity()

        if (meCurWep.grenade || meCurWep.knife || meCurWep.miscEnt || meCurWep == Weapons.ZEUS_X27 || meCurWep.bomb) {
            return@every
        }

        curWepCategory = when { //Set the aim settings to the weapon's category settings
            meCurWep.rifle -> "RIFLE"
            meCurWep.smg -> "SMG"
            meCurWep.pistol -> "PISTOL"
            meCurWep.sniper -> "SNIPER"
            meCurWep.shotgun -> "SHOTGUN"
            else -> ""
        }

        if (settingsLoaded) { //If we have settings to read
            if (curSettings["ENABLE_OVERRIDE"].strToBool()) {
                //V--Update aim settings for current weapons--V\\
                if (meCurWep.rifle || meCurWep.smg || meCurWep.pistol || meCurWep.sniper || meCurWep.shotgun) {
                    curWepSettings = curSettings[meCurWep.name].toWeaponClass()

                    if (curWepSettings.tOverride) {
                        curSettings["FACTOR_RECOIL"] = curWepSettings.tFRecoil
                        curSettings["ENABLE_FLAT_AIM"] = curWepSettings.tFlatAim
                        curSettings["ENABLE_PATH_AIM"] = curWepSettings.tPathAim
                        curSettings["AIM_BONE"] = curWepSettings.tAimBone
                        curSettings["FORCE_AIM_BONE"] = curWepSettings.tForceBone
                        curSettings["AIM_FOV"] = curWepSettings.tAimFov
                        curSettings["AIM_SPEED"] = curWepSettings.tAimSpeed
                        curSettings["AIM_SMOOTHNESS"] = curWepSettings.tAimSmooth
                        curSettings["PERFECT_AIM"] = curWepSettings.tPerfectAim
                        curSettings["PERFECT_AIM_FOV"] = curWepSettings.tPAimFov
                        curSettings["PERFECT_AIM_CHANCE"] = curWepSettings.tPAimChance
                        curSettings["ENABLE_SCOPED_ONLY"] = curWepSettings.tScopedOnly

                        if (meCurWep.rifle || meCurWep.smg) {
                            curSettings["AIM_AFTER_SHOTS"] = curWepSettings.tAimAfterShots
                        }

                        curSettings["TRIGGER_USE_FOV"] = curWepSettings.tBTrigInFov
                        curSettings["TRIGGER_USE_INCROSS"] = curWepSettings.tBTrigInCross
                        curSettings["TRIGGER_FOV"] = curWepSettings.tBTrigFov
                        curSettings["TRIGGER_USE_AIMBOT"] = curWepSettings.tBTrigAim
                        curSettings["TRIGGER_USE_BACKTRACK"] = curWepSettings.tBTrigBacktrack

                        //Advanced advanced aim settings
                        curWepOverride = true
                        haveAimSettings = true
                    } else {
                        curWepOverride = false
                    }
                }
            }
        }

        if (!curWepOverride) { //If the current weapon isn't checked to override
            if (curWepCategory != "") {
                curSettings["FACTOR_RECOIL"] = curSettings[curWepCategory + "_FACTOR_RECOIL"].strToBool()
                curSettings["AIM_BONE"] = curSettings[curWepCategory + "_AIM_BONE"].boneToNum()
                curSettings["FORCE_AIM_BONE"] = curSettings[curWepCategory + "_AIM_FORCE_BONE"].boneToNum()
                curSettings["AIM_FOV"] = curSettings[curWepCategory + "_AIM_FOV"].toFloat()
                curSettings["AIM_SPEED"] = curSettings[curWepCategory + "_AIM_SPEED"].toInt()
                curSettings["AIM_SMOOTHNESS"] = curSettings[curWepCategory + "_AIM_SMOOTHNESS"].toDouble()

                curSettings["PERFECT_AIM"] = curSettings[curWepCategory + "_PERFECT_AIM"].strToBool()
                curSettings["PERFECT_AIM_FOV"] = curSettings[curWepCategory + "_PERFECT_AIM_FOV"].toDouble()
                curSettings["PERFECT_AIM_CHANCE"] = curSettings[curWepCategory + "_PERFECT_AIM_CHANCE"].toInt()
                curSettings["ENABLE_FLAT_AIM"] = curSettings[curWepCategory + "_ENABLE_FLAT_AIM"].strToBool()
                curSettings["ENABLE_PATH_AIM"] = curSettings[curWepCategory + "_ENABLE_PATH_AIM"].strToBool()
                curSettings["ENABLE_SCOPED_ONLY"] = curSettings["SNIPER_ENABLE_SCOPED_ONLY"].strToBool()

                curSettings["AIM_ONLY_ON_SHOT"] = curSettings[curWepCategory + "_AIM_ONLY_ON_SHOT"].strToBool()
                curSettings["AIM_AFTER_SHOTS"] = curSettings[curWepCategory + "_AIM_AFTER_SHOTS"].toInt()

                curSettings["AIM_ADVANCED"] = curSettings[curWepCategory + "_ADVANCED_SETTINGS"].strToBool()
                curSettings["AIM_RCS_X"] = curSettings[curWepCategory + "_AIM_RCS_X"].toDouble()
                curSettings["AIM_RCS_Y"] = curSettings[curWepCategory + "_AIM_RCS_Y"].toDouble()
                curSettings["AIM_RCS_VARIATION"] = curSettings[curWepCategory + "_AIM_RCS_VARIATION"].toDouble()
                curSettings["AIM_SPEED_DIVISOR"] = curSettings[curWepCategory + "_AIM_SPEED_DIVISOR"].toInt()
                curSettings["AIM_RANDOM_X_VARIATION"] = curSettings[curWepCategory + "_RANDOM_X_VARIATION"].toInt()
                curSettings["AIM_RANDOM_Y_VARIATION"] = curSettings[curWepCategory + "_RANDOM_Y_VARIATION"].toInt()
                curSettings["AIM_VARIATION_DEADZONE"] = curSettings[curWepCategory + "_VARIATION_DEADZONE"].toInt()

                curSettings["TRIGGER_FOV"] = curSettings[curWepCategory + "_TRIGGER_FOV"].toFloat()
                curSettings["TRIGGER_USE_FOV"] = curSettings[curWepCategory + "_TRIGGER_INFOV"].strToBool()
                curSettings["TRIGGER_USE_INCROSS"] = curSettings[curWepCategory + "_TRIGGER_INCROSS"].strToBool()
                curSettings["TRIGGER_USE_AIMBOT"] = curSettings[curWepCategory + "_TRIGGER_AIMBOT"].strToBool()
                curSettings["TRIGGER_USE_BACKTRACK"] = curSettings[curWepCategory + "_TRIGGER_BACKTRACK"].strToBool() && curSettings["ENABLE_BACKTRACK"].strToBool()

                haveAimSettings = true
            }
        }
    } catch (e: Exception) { println("SetAim failure"); e.printStackTrace() } //Fix crashing
}

fun String.boneToNum(): Int {
    return when (this.uppercase()) {
        "HEAD" -> HEAD_BONE
        "NECK" -> NECK_BONE
        "CHEST" -> CHEST_BONE
        "STOMACH" -> STOMACH_BONE
        "NEAREST" -> NEAREST_BONE
        else -> RANDOM_BONE
    }
}