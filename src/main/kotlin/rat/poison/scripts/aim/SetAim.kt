package rat.poison.scripts.aim

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import rat.poison.DEFAULT_OWEAPON
import rat.poison.curSettings
import rat.poison.game.Weapons
import rat.poison.game.entity.weapon
import rat.poison.game.entity.weaponEntity
import rat.poison.game.me
import rat.poison.scripts.userCmd.meDead
import rat.poison.settings.*
import rat.poison.settingsLoaded
import rat.poison.utils.common.every
import rat.poison.utils.extensions.upper
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.stringToList
import rat.poison.utils.generalUtil.toWeaponClass
import kotlin.math.floor

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
                        FACTOR_RECOIL = curWepSettings.tFRecoil
                        ENABLE_FLAT_AIM = curWepSettings.tFlatAim
                        ENABLE_PATH_AIM = curWepSettings.tPathAim
                        AIM_BONE = curWepSettings.tAimBone.toBoneList()
                        FORCE_AIM_BONE = curWepSettings.tForceBone.toBoneList()
                        AIM_FOV = curWepSettings.tAimFov
                        AIM_SMOOTHNESS = curWepSettings.tAimSmooth
                        PERFECT_AIM = curWepSettings.tPerfectAim
                        PERFECT_AIM_FOV = curWepSettings.tPAimFov
                        PERFECT_AIM_CHANCE = curWepSettings.tPAimChance
                        ENABLE_SCOPED_ONLY = curWepSettings.tScopedOnly

                        if (meCurWep.rifle || meCurWep.smg) {
                            AIM_AFTER_SHOTS = curWepSettings.tAimAfterShots
                        }

                        AUTO_WEP_DELAY = curWepSettings.tAutowepDelay
                        AUTOMATIC_WEAPONS = curWepSettings.tAutowep

                        TRIGGER_BOT = curWepSettings.tBoneTrig
                        TRIGGER_INIT_SHOT_DELAY = curWepSettings.tBTrigInitDelay
                        TRIGGER_PER_SHOT_DELAY = curWepSettings.tBTrigPerShotDelay
                        TRIGGER_USE_FOV = curWepSettings.tBTrigInFov
                        TRIGGER_USE_INCROSS = curWepSettings.tBTrigInCross
                        TRIGGER_FOV = curWepSettings.tBTrigFov
                        TRIGGER_USE_AIMBOT = curWepSettings.tBTrigAim
                        TRIGGER_USE_BACKTRACK = curWepSettings.tBTrigBacktrack
                        BACKTRACK = curWepSettings.tBacktrack
                        BACKTRACK_MS = curWepSettings.tBTMS

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
                FACTOR_RECOIL = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_FACTOR_RECOIL")]
                AIM_BONE = curSettings[stringBuilder.clear().append(curWepCategory).append("_AIM_BONE")].stringToBoneList()
                FORCE_AIM_BONE = curSettings[stringBuilder.clear().append(curWepCategory).append("_AIM_FORCE_BONE")].stringToBoneList()
                AIM_FOV = curSettings.float[stringBuilder.clear().append(curWepCategory).append("_AIM_FOV")]
                AIM_SMOOTHNESS = floor(curSettings.float[stringBuilder.clear().append(curWepCategory).append("_AIM_SMOOTHNESS")]).toInt()

                PERFECT_AIM = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_PERFECT_AIM")]
                PERFECT_AIM_FOV = curSettings.float[stringBuilder.clear().append(curWepCategory).append("_PERFECT_AIM_FOV")]
                PERFECT_AIM_CHANCE = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_PERFECT_AIM_CHANCE")]
                ENABLE_FLAT_AIM = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_ENABLE_FLAT_AIM")]
                ENABLE_PATH_AIM = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_ENABLE_PATH_AIM")]
                ENABLE_SCOPED_ONLY = curSettings.bool["SNIPER_ENABLE_SCOPED_ONLY"]

                AUTOMATIC_WEAPONS = curSettings.bool["GLOBAL_AUTOMATIC_WEAPONS"]
                AUTO_WEP_DELAY = curSettings.int["GLOBAL_AUTO_WEP_DELAY"]

                AIM_ONLY_ON_SHOT = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_AIM_ONLY_ON_SHOT")]
                AIM_AFTER_SHOTS = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_AIM_AFTER_SHOTS")]

                AIM_ADVANCED = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_ADVANCED_SETTINGS")]
                AIM_RCS_X = curSettings.float[stringBuilder.clear().append(curWepCategory).append("_AIM_RCS_X")]
                AIM_RCS_Y = curSettings.float[stringBuilder.clear().append(curWepCategory).append("_AIM_RCS_Y")]
                AIM_RCS_VARIATION = curSettings.double[stringBuilder.clear().append(curWepCategory).append("_AIM_RCS_VARIATION")]
                AIM_RANDOM_X_VARIATION = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_RANDOM_X_VARIATION")]
                AIM_RANDOM_Y_VARIATION = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_RANDOM_Y_VARIATION")]
                AIM_VARIATION_DEADZONE = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_VARIATION_DEADZONE")]

                TRIGGER_BOT = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_TRIGGER")]
                TRIGGER_INIT_SHOT_DELAY = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_INIT_SHOT_DELAY")]
                TRIGGER_PER_SHOT_DELAY = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_PER_SHOT_DELAY")]
                TRIGGER_FOV = curSettings.float[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_FOV")]
                TRIGGER_USE_FOV = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_INFOV")]
                TRIGGER_USE_INCROSS = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_INCROSS")]
                TRIGGER_USE_AIMBOT = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_AIMBOT")]
                TRIGGER_USE_BACKTRACK = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_TRIGGER_BACKTRACK")] && curSettings.bool["ENABLE_BACKTRACK"]
                BACKTRACK = curSettings.bool[stringBuilder.clear().append(curWepCategory).append("_BACKTRACK")]
                BACKTRACK_MS = curSettings.int[stringBuilder.clear().append(curWepCategory).append("_BACKTRACK_MS")]
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

private val listStringToBoneListMap = Object2ObjectOpenHashMap<List<String>, List<Int>>()
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

private val stringToBoneListMap = Object2ObjectOpenHashMap<String, List<Int>>()
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