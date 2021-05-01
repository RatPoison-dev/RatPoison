package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.hooks.toneMapController
import rat.poison.game.netvars.NetVarOffsets.m_bUseCustomAutoExposureMax
import rat.poison.game.netvars.NetVarOffsets.m_bUseCustomAutoExposureMin
import rat.poison.game.netvars.NetVarOffsets.m_flCustomAutoExposureMax
import rat.poison.game.netvars.NetVarOffsets.m_flCustomAutoExposureMin
import rat.poison.utils.common.every

fun nightMode() = every(1000, inGameCheck = true) {
    if (!curSettings.bool["ENABLE_VISUALS"]) return@every

    if (curSettings.bool["ENABLE_NIGHTMODE"]) {
        if (toneMapController != 0L) {
            csgoEXE[toneMapController + m_bUseCustomAutoExposureMin] = 1
            csgoEXE[toneMapController + m_bUseCustomAutoExposureMax] = 1

            csgoEXE[toneMapController + m_flCustomAutoExposureMin] = curSettings.float["NIGHTMODE_VALUE"]
            csgoEXE[toneMapController + m_flCustomAutoExposureMax] = curSettings.float["NIGHTMODE_VALUE"]
        }
    }
}