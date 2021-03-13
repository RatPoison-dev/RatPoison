package rat.poison.scripts.aim

import rat.poison.DEFAULT_OWEAPON
import rat.poison.curSettings
import rat.poison.game.Weapons
import rat.poison.game.entity.weapon
import rat.poison.game.entity.weaponEntity
import rat.poison.game.me
import rat.poison.settings.*
import rat.poison.settingsLoaded
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.stringToList
import rat.poison.utils.generalUtil.toWeaponClass

var meCurWep = Weapons.AK47
var meCurWepEnt = 0L
var curWepOverride = false
var curWepCategory = "PISTOL"
var curWepSettings = DEFAULT_OWEAPON
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
                        curSettings["AIM_BONE"] = curWepSettings.tAimBone.map { it.boneToNum() }
                        curSettings["FORCE_AIM_BONE"] = curWepSettings.tForceBone.map { it.boneToNum() }
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
                        curSettings["AUTO_WEP_DELAY"] = curWepSettings.tAutowepDelay
                        curSettings["AUTOMATIC_WEAPONS"] = curWepSettings.tAutowep

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
                curSettings["FACTOR_RECOIL"] = curSettings.bool[curWepCategory + "_FACTOR_RECOIL"]
                curSettings["AIM_BONE"] = curSettings[curWepCategory + "_AIM_BONE"].stringToList().map { it.boneToNum() }
                curSettings["FORCE_AIM_BONE"] = curSettings[curWepCategory + "_AIM_FORCE_BONE"].stringToList().map { it.boneToNum() }
                curSettings["AIM_FOV"] = curSettings.float[curWepCategory + "_AIM_FOV"]
                curSettings["AIM_SPEED"] = curSettings.int[curWepCategory + "_AIM_SPEED"]
                curSettings["AIM_SMOOTHNESS"] = curSettings.double[curWepCategory + "_AIM_SMOOTHNESS"]

                curSettings["PERFECT_AIM"] = curSettings.bool[curWepCategory + "_PERFECT_AIM"]
                curSettings["PERFECT_AIM_FOV"] = curSettings.float[curWepCategory + "_PERFECT_AIM_FOV"]
                curSettings["PERFECT_AIM_CHANCE"] = curSettings.int[curWepCategory + "_PERFECT_AIM_CHANCE"]
                curSettings["ENABLE_FLAT_AIM"] = curSettings.bool[curWepCategory + "_ENABLE_FLAT_AIM"]
                curSettings["ENABLE_PATH_AIM"] = curSettings.bool[curWepCategory + "_ENABLE_PATH_AIM"]
                curSettings["ENABLE_SCOPED_ONLY"] = curSettings.bool["SNIPER_ENABLE_SCOPED_ONLY"]

                curSettings["AUTOMATIC_WEAPONS"] = curSettings.bool["GLOBAL_AUTOMATIC_WEAPONS"]
                curSettings["AUTO_WEP_DELAY"] = curSettings.int["GLOBAL_AUTO_WEP_DELAY"]

                curSettings["AIM_ONLY_ON_SHOT"] = curSettings.bool[curWepCategory + "_AIM_ONLY_ON_SHOT"]
                curSettings["AIM_AFTER_SHOTS"] = curSettings.int[curWepCategory + "_AIM_AFTER_SHOTS"]

                curSettings["AIM_ADVANCED"] = curSettings.bool[curWepCategory + "_ADVANCED_SETTINGS"]
                curSettings["AIM_RCS_X"] = curSettings.double[curWepCategory + "_AIM_RCS_X"]
                curSettings["AIM_RCS_Y"] = curSettings.double[curWepCategory + "_AIM_RCS_Y"]
                curSettings["AIM_RCS_VARIATION"] = curSettings.double[curWepCategory + "_AIM_RCS_VARIATION"]
                curSettings["AIM_SPEED_DIVISOR"] = curSettings.int[curWepCategory + "_AIM_SPEED_DIVISOR"]
                curSettings["AIM_RANDOM_X_VARIATION"] = curSettings.int[curWepCategory + "_RANDOM_X_VARIATION"]
                curSettings["AIM_RANDOM_Y_VARIATION"] = curSettings.int[curWepCategory + "_RANDOM_Y_VARIATION"]
                curSettings["AIM_VARIATION_DEADZONE"] = curSettings.int[curWepCategory + "_VARIATION_DEADZONE"]

                curSettings["TRIGGER_FOV"] = curSettings.float[curWepCategory + "_TRIGGER_FOV"]
                curSettings["TRIGGER_USE_FOV"] = curSettings.bool[curWepCategory + "_TRIGGER_INFOV"]
                curSettings["TRIGGER_USE_INCROSS"] = curSettings.bool[curWepCategory + "_TRIGGER_INCROSS"]
                curSettings["TRIGGER_USE_AIMBOT"] = curSettings.bool[curWepCategory + "_TRIGGER_AIMBOT"]
                curSettings["TRIGGER_USE_BACKTRACK"] = curSettings.bool[curWepCategory + "_TRIGGER_BACKTRACK"] && curSettings.bool["ENABLE_BACKTRACK"]

                haveAimSettings = true
            }
        }
    } catch (e: Exception) { println("SetAim failure"); e.printStackTrace() } //Fix crashing
}

fun String.boneToNum(): Int {
    return when (this.toUpperCase()) {
        "HEAD" -> HEAD_BONE
        "NECK" -> NECK_BONE
        "CHEST" -> CHEST_BONE
        "STOMACH" -> STOMACH_BONE
        "NEAREST" -> NEAREST_BONE
        "PELVIS" -> PELVIS_BONE
        else -> RANDOM_BONE
    }
}

fun Int.numToBone(): String {
    return when (this) {
        HEAD_BONE -> "HEAD"
        NECK_BONE -> "NECK"
        CHEST_BONE -> "CHEST"
        STOMACH_BONE -> "STOMACH"
        NEAREST_BONE -> "NEAREST"
        PELVIS_BONE -> "PELVIS"
        else -> "RANDOM"
    }
}