package rat.poison.scripts.aim

import rat.poison.engine
import rat.poison.game.Weapons
import rat.poison.game.entity.weapon
import rat.poison.game.me
import rat.poison.settings.*
import rat.poison.utils.every

var override = false

fun setAim() = every(256){
    override = false
        //loadSettings()
    if (me.weapon().rifle || me.weapon().smg || me.weapon().pistol || me.weapon().sniper || me.weapon().shotgun) {
        val curWep: DoubleArray = engine.eval(me.weapon().name) as DoubleArray

        enumValues<Weapons>().forEach {
            if (it.id == me.weapon().id && curWep[1].toBool()) {
                FACTOR_RECOIL = curWep[2].toBool()
                AIM_BONE = curWep[5].toInt()
                AIM_FOV = curWep[6].toInt()
                AIM_SPEED = curWep[7].toInt()
                AIM_SMOOTHNESS = curWep[8]
                AIM_STRICTNESS = curWep[9]
                PERFECT_AIM = curWep[10].toBool()
                PERFECT_AIM_FOV = curWep[11].toInt()
                PERFECT_AIM_CHANCE = curWep[12].toInt()
                AIM_ASSIST_MODE = curWep[13].toBool()
                AIM_ASSIST_STRICTNESS = curWep[14].toInt()
                ENABLE_FLAT_AIM = curWep[3].toBool()
                ENABLE_PATH_AIM = curWep[4].toBool()
                override = true
            }
        }
    }


    if (!override) {
        if (me.weapon().rifle) {
            FACTOR_RECOIL = RIFLE_FACTOR_RECOIL
            AIM_BONE = RIFLE_AIM_BONE
            AIM_FOV = RIFLE_AIM_FOV
            AIM_SPEED = RIFLE_AIM_SPEED
            AIM_SMOOTHNESS = RIFLE_AIM_SMOOTHNESS
            AIM_STRICTNESS = RIFLE_AIM_STRICTNESS
            PERFECT_AIM = RIFLE_PERFECT_AIM
            PERFECT_AIM_FOV = RIFLE_PERFECT_AIM_FOV
            PERFECT_AIM_CHANCE = RIFLE_PERFECT_AIM_CHANCE
            AIM_ASSIST_MODE = RIFLE_AIM_ASSIST_MODE
            AIM_ASSIST_STRICTNESS = RIFLE_AIM_ASSIST_STRICTNESS
            ENABLE_FLAT_AIM = RIFLE_ENABLE_FLAT_AIM
            ENABLE_PATH_AIM = RIFLE_ENABLE_PATH_AIM
        } else if (me.weapon().smg) {
            FACTOR_RECOIL = SMG_FACTOR_RECOIL
            AIM_BONE = SMG_AIM_BONE
            AIM_FOV = SMG_AIM_FOV
            AIM_SPEED = SMG_AIM_SPEED
            AIM_SMOOTHNESS = SMG_AIM_SMOOTHNESS
            AIM_STRICTNESS = SMG_AIM_STRICTNESS
            PERFECT_AIM = SMG_PERFECT_AIM
            PERFECT_AIM_FOV = SMG_PERFECT_AIM_FOV
            PERFECT_AIM_CHANCE = SMG_PERFECT_AIM_CHANCE
            AIM_ASSIST_MODE = SMG_AIM_ASSIST_MODE
            AIM_ASSIST_STRICTNESS = SMG_AIM_ASSIST_STRICTNESS
            ENABLE_FLAT_AIM = SMG_ENABLE_FLAT_AIM
            ENABLE_PATH_AIM = SMG_ENABLE_PATH_AIM
        } else if (me.weapon().pistol) {
            FACTOR_RECOIL = PISTOL_FACTOR_RECOIL
            AIM_BONE = PISTOL_AIM_BONE
            AIM_FOV = PISTOL_AIM_FOV
            AIM_SPEED = PISTOL_AIM_SPEED
            AIM_SMOOTHNESS = PISTOL_AIM_SMOOTHNESS
            AIM_STRICTNESS = PISTOL_AIM_STRICTNESS
            PERFECT_AIM = PISTOL_PERFECT_AIM
            PERFECT_AIM_FOV = PISTOL_PERFECT_AIM_FOV
            PERFECT_AIM_CHANCE = PISTOL_PERFECT_AIM_CHANCE
            AIM_ASSIST_MODE = PISTOL_AIM_ASSIST_MODE
            AIM_ASSIST_STRICTNESS = PISTOL_AIM_ASSIST_STRICTNESS
            ENABLE_FLAT_AIM = PISTOL_ENABLE_FLAT_AIM
            ENABLE_PATH_AIM = PISTOL_ENABLE_PATH_AIM
        } else if (me.weapon().sniper) {
            FACTOR_RECOIL = SNIPER_FACTOR_RECOIL
            AIM_BONE = SNIPER_AIM_BONE
            AIM_FOV = SNIPER_AIM_FOV
            AIM_SPEED = SNIPER_AIM_SPEED
            AIM_SMOOTHNESS = SNIPER_AIM_STRICTNESS
            AIM_STRICTNESS = SNIPER_AIM_STRICTNESS
            PERFECT_AIM = SNIPER_PERFECT_AIM
            PERFECT_AIM_FOV = SNIPER_PERFECT_AIM_FOV
            PERFECT_AIM_CHANCE = SNIPER_PERFECT_AIM_CHANCE
            AIM_ASSIST_MODE = SNIPER_AIM_ASSIST_MODE
            AIM_ASSIST_STRICTNESS = SNIPER_AIM_ASSIST_STRICTNESS
            ENABLE_FLAT_AIM = SNIPER_ENABLE_FLAT_AIM
            ENABLE_PATH_AIM = SNIPER_ENABLE_PATH_AIM
        } else if (me.weapon().shotgun) {
            FACTOR_RECOIL = SHOTGUN_FACTOR_RECOIL
            AIM_BONE = SHOTGUN_AIM_BONE
            AIM_FOV = SHOTGUN_AIM_FOV
            AIM_SPEED = SHOTGUN_AIM_SPEED
            AIM_SMOOTHNESS = SHOTGUN_AIM_SMOOTHNESS
            AIM_STRICTNESS = SHOTGUN_AIM_STRICTNESS
            PERFECT_AIM = SHOTGUN_PERFECT_AIM
            PERFECT_AIM_FOV = SHOTGUN_PERFECT_AIM_FOV
            PERFECT_AIM_CHANCE = SHOTGUN_PERFECT_AIM_CHANCE
            AIM_ASSIST_MODE = SHOTGUN_AIM_ASSIST_MODE
            AIM_ASSIST_STRICTNESS = SHOTGUN_AIM_ASSIST_STRICTNESS
            ENABLE_FLAT_AIM = SHOTGUN_ENABLE_FLAT_AIM
            ENABLE_PATH_AIM = SHOTGUN_ENABLE_PATH_AIM
        }
    }
}

fun Double.toBool() = this == 1.0