package rat.poison.scripts.aim

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import rat.poison.DEFAULT_OWEAPON
import rat.poison.curSettings
import rat.poison.game.Weapons
import rat.poison.game.entity.weapon
import rat.poison.game.entity.weaponEntity
import rat.poison.game.me
import rat.poison.settings.*
import rat.poison.settingsLoaded
import rat.poison.utils.every
import rat.poison.utils.extensions.upper
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.stringToList
import rat.poison.utils.generalUtil.toWeaponClass

var meCurWep = Weapons.AK47
var meCurWepEnt = 0L
var curWepOverride = false
var curWepCategory = "PISTOL"
var curWepSettings = DEFAULT_OWEAPON
var haveAimSettings = false

private var stringBuilder = StringBuilder()
var curWepCategoryBacktrackMs = "${curWepCategory}_BACKTRACK_MS"
var curWepCategoryBacktrack= "${curWepCategory}_BACKTRACK"

fun setAim() = every(500, true, inGameCheck = true) {
    try {
        if (meDead) return@every

        meCurWepEnt = me.weaponEntity()
        meCurWep = me.weapon(meCurWepEnt)


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
        curWepCategoryBacktrackMs = stringBuilder.clear().append(curWepCategory).append("_BACKTRACK_MS").toString()
        curWepCategoryBacktrack = stringBuilder.clear().append(curWepCategory).append("_BACKTRACK").toString()

        if (settingsLoaded) { //If we have settings to read
            if (curSettings["ENABLE_OVERRIDE"].strToBool()) {
                //V--Update aim settings for current weapons--V\\
                if (meCurWep.rifle || meCurWep.smg || meCurWep.pistol || meCurWep.sniper || meCurWep.shotgun) {
                    curWepSettings = curSettings[meCurWep.name].toWeaponClass()

                    if (curWepSettings.tOverride) {
                        curSettings["FACTOR_RECOIL"] = curWepSettings.tFRecoil
                        curSettings["ENABLE_FLAT_AIM"] = curWepSettings.tFlatAim
                        curSettings["ENABLE_PATH_AIM"] = curWepSettings.tPathAim
                        curSettings["AIM_BONE"] = curWepSettings.tAimBone.toBoneList()
                        curSettings["FORCE_AIM_BONE"] = curWepSettings.tForceBone.toBoneList()
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

                        curSettings["TRIGGER_BOT"] = curWepSettings.tBoneTrig
                        curSettings["TRIGGER_INIT_SHOT_DELAY"] = curWepSettings.tBTrigInitDelay
                        curSettings["TRIGGER_PER_SHOT_DELAY"] = curWepSettings.tBTrigPerShotDelay
                        curSettings["TRIGGER_USE_FOV"] = curWepSettings.tBTrigInFov
                        curSettings["TRIGGER_USE_INCROSS"] = curWepSettings.tBTrigInCross
                        curSettings["TRIGGER_FOV"] = curWepSettings.tBTrigFov
                        curSettings["TRIGGER_USE_AIMBOT"] = curWepSettings.tBTrigAim
                        curSettings["TRIGGER_USE_BACKTRACK"] = curWepSettings.tBTrigBacktrack
                        curSettings["BACKTRACK"] = curWepSettings.tBacktrack
                        curSettings["BACKTRACK_MS"] = curWepSettings.tBTMS

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
                curSettings["FACTOR_RECOIL"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_FACTOR_RECOIL").toString()]
                curSettings["AIM_BONE"] = curSettings[stringBuilder.clear().append(curWepCategory).append("_AIM_BONE").toString()].stringToBoneList()
                curSettings["FORCE_AIM_BONE"] = curSettings[stringBuilder.clear().append(curWepCategory).append("_AIM_FORCE_BONE").toString()].stringToBoneList()
                curSettings["AIM_FOV"] = curSettings.float[stringBuilder.clear().append(curWepCategory).append("_AIM_FOV").toString()]
                curSettings["AIM_SPEED"] = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_AIM_SPEED").toString()]
                curSettings["AIM_SMOOTHNESS"] = curSettings.double[stringBuilder.clear().append(curWepCategory).append("_AIM_SMOOTHNESS").toString()]

                curSettings["PERFECT_AIM"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_PERFECT_AIM").toString()]
                curSettings["PERFECT_AIM_FOV"] = curSettings.float[stringBuilder.clear().append(curWepCategory).append("_PERFECT_AIM_FOV").toString()]
                curSettings["PERFECT_AIM_CHANCE"] = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_PERFECT_AIM_CHANCE").toString()]
                curSettings["ENABLE_FLAT_AIM"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_ENABLE_FLAT_AIM").toString()]
                curSettings["ENABLE_PATH_AIM"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_ENABLE_PATH_AIM").toString()]
                curSettings["ENABLE_SCOPED_ONLY"] = curSettings.bool["SNIPER_ENABLE_SCOPED_ONLY"]

                curSettings["AUTOMATIC_WEAPONS"] = curSettings.bool["GLOBAL_AUTOMATIC_WEAPONS"]
                curSettings["AUTO_WEP_DELAY"] = curSettings.int["GLOBAL_AUTO_WEP_DELAY"]

                curSettings["AIM_ONLY_ON_SHOT"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_AIM_ONLY_ON_SHOT").toString()]
                curSettings["AIM_AFTER_SHOTS"] = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_AIM_AFTER_SHOTS").toString()]

                curSettings["AIM_ADVANCED"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_ADVANCED_SETTINGS").toString()]
                curSettings["AIM_RCS_X"] = curSettings.double[stringBuilder.clear().append(curWepCategory).append("_AIM_RCS_X").toString()]
                curSettings["AIM_RCS_Y"] = curSettings.double[stringBuilder.clear().append(curWepCategory).append("_AIM_RCS_Y").toString()]
                curSettings["AIM_RCS_VARIATION"] = curSettings.double[stringBuilder.clear().append(curWepCategory).append("_AIM_RCS_VARIATION").toString()]
                curSettings["AIM_SPEED_DIVISOR"] = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_AIM_SPEED_DIVISOR").toString()]
                curSettings["AIM_RANDOM_X_VARIATION"] = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_RANDOM_X_VARIATION").toString()]
                curSettings["AIM_RANDOM_Y_VARIATION"] = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_RANDOM_Y_VARIATION").toString()]
                curSettings["AIM_VARIATION_DEADZONE"] = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_VARIATION_DEADZONE").toString()]

                curSettings["TRIGGER_BOT"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_TRIGGER").toString()]
                curSettings["TRIGGER_INIT_SHOT_DELAY"] = curSettings[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_INIT_SHOT_DELAY").toString()]
                curSettings["TRIGGER_PER_SHOT_DELAY"] = curSettings[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_PER_SHOT_DELAY").toString()]
                curSettings["TRIGGER_FOV"] = curSettings.float[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_FOV").toString()]
                curSettings["TRIGGER_USE_FOV"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_INFOV").toString()]
                curSettings["TRIGGER_USE_INCROSS"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_INCROSS").toString()]
                curSettings["TRIGGER_USE_AIMBOT"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_AIMBOT").toString()]
                curSettings["TRIGGER_USE_BACKTRACK"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_BACKTRACK").toString()] && curSettings.bool["ENABLE_BACKTRACK"]
                curSettings["BACKTRACK"] = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_BACKTRACK").toString()]
                curSettings["BACKTRACK_MS"] = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_BACKTRACK_MS").toString()]
                haveAimSettings = true
            }
        }
    } catch (e: Exception) { println("SetAim failure"); e.printStackTrace() } //Fix crashing
}

fun String.boneToNum(): Int {
    return when (this.upper()) {
        "HEAD" -> HEAD_BONE
        "NECK" -> NECK_BONE
        "CHEST" -> CHEST_BONE
        "STOMACH" -> STOMACH_BONE
        "NEAREST" -> NEAREST_BONE
        "PELVIS" -> PELVIS_BONE
        else -> RANDOM_BONE
    }
}

private val listStringToBoneListMap = Object2ObjectArrayMap<List<String>, List<Int>>()
fun List<String>.toBoneList(): List<Int> {
    val get = listStringToBoneListMap[this]
    return when (get == null) {
        true -> {
            val thisList = this.map { it.boneToNum() }
            listStringToBoneListMap[this] = thisList
            thisList
        }
        false -> get
    }
}

private val stringToBoneListMap = Object2ObjectArrayMap<String, List<Int>>()
fun String.stringToBoneList(separator: String = ","): List<Int> {
    val get = stringToBoneListMap[this]
    return when (get == null) {
        true -> {
            val thisList = this.stringToList(separator).toBoneList()
            stringToBoneListMap[this] = thisList
            thisList
        }
        else -> get
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