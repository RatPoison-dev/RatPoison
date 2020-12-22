package rat.poison.scripts

import org.jire.kna.int
import org.jire.kna.set
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.isScoped
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.m_iDefaultFov
import rat.poison.game.netvars.NetVarOffsets.m_zoomLevel
import rat.poison.overlay.App
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meCurWepEnt
import rat.poison.utils.inGame

internal fun fovChanger() = App {
    if (!inGame) return@App

    val curFov = csgoEXE.int(me + m_iDefaultFov)

    if (!curSettings.bool["ENABLE_FOV_CHANGER"]) {
        if (curFov != 90) {
            csgoEXE[me + m_iDefaultFov] = 90
        }

        return@App
    }
    val zLevel = csgoEXE.int(meCurWepEnt + m_zoomLevel)

    val targetFov: Int

    var instantSwap = false

    if (meCurWep.sniper) {
        if (me.isScoped()) {
            targetFov = when (zLevel) {
                1 -> {
                    instantSwap = true
                    curSettings.int["FOV_ZOOM_1"]
                }

                2 -> {
                    instantSwap = true
                    curSettings.int["FOV_ZOOM_2"]
                }

                else -> {
                    instantSwap = true
                    curSettings.int["FOV_SNIPER_DEFAULT"]
                }
            }
        } else {
            targetFov = curSettings.int["FOV_SNIPER_DEFAULT"]

            if (targetFov > curFov) {
                instantSwap = true
            }
        }
    } else {
        targetFov = curSettings.int["FOV_DEFAULT"]
    }

    if (targetFov != -1) {
        if (instantSwap) {
            csgoEXE[me + m_iDefaultFov] = targetFov
            return@App
        }

        if (curSettings.bool["FOV_SMOOTH"]) {

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