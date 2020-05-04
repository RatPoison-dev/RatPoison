package rat.poison.scripts

import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.dead
import rat.poison.game.entity.isScoped
import rat.poison.game.entity.weapon
import rat.poison.game.entity.weaponEntity
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_iDefaultFov
import rat.poison.game.netvars.NetVarOffsets.m_zoomLevel
import rat.poison.strToBool

internal fun fovChanger() = App {
    if (!curSettings["ENABLE_FOV_CHANGER"].strToBool() || me.dead()) return@App
    val meWep = me.weaponEntity()

    val zLevel = csgoEXE.int(meWep + m_zoomLevel)

    val targetFov: Int

    var instantSwap = false

    val curFov = csgoEXE.int(me + m_iDefaultFov)

    if (me.weapon().sniper) {
        if (me.isScoped()) {
            targetFov = when (zLevel) {
                1 -> {
                    instantSwap = true
                    curSettings["FOV_ZOOM_1"].toInt()
                }

                2 -> {
                    instantSwap = true
                    curSettings["FOV_ZOOM_2"].toInt()
                }

                else -> {
                    instantSwap = true
                    curSettings["FOV_SNIPER_DEFAULT"].toInt()
                }
            }
        } else {
            targetFov = curSettings["FOV_SNIPER_DEFAULT"].toInt()

            if (targetFov > curFov) {
                instantSwap = true
            }
        }
    } else {
        targetFov = curSettings["FOV_DEFAULT"].toInt()
    }

    if (targetFov != -1) {
        if (instantSwap) {
            csgoEXE[me + m_iDefaultFov] = targetFov
            return@App
        }

        if (curSettings["FOV_SMOOTH"].strToBool()) {

            if (curFov < targetFov) {
                csgoEXE[me + m_iDefaultFov] = curFov + 1
            } else if (curFov > targetFov) {
                csgoEXE[me + m_iDefaultFov] = curFov - 1
            }
        } else {
            if (curFov != targetFov) {
                csgoEXE[me + m_iDefaultFov] = targetFov
            }
        }
    }
}