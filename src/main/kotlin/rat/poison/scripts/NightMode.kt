package rat.poison.scripts

import org.jire.arrowhead.get
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.hooks.toneMapController
import rat.poison.game.netvars.NetVarOffsets.m_bUseCustomAutoExposureMax
import rat.poison.game.netvars.NetVarOffsets.m_bUseCustomAutoExposureMin
import rat.poison.game.netvars.NetVarOffsets.m_flCustomAutoExposureMax
import rat.poison.game.netvars.NetVarOffsets.m_flCustomAutoExposureMin
import rat.poison.strToBool
import rat.poison.utils.every

fun nightMode() = every(1000) {
    if (!curSettings["ENABLE_ESP"].strToBool())
        return@every

    if (curSettings["ENABLE_NIGHTMODE"].strToBool()) {
        if (toneMapController != 0L) {
            csgoEXE[toneMapController + m_bUseCustomAutoExposureMin] = 1
            csgoEXE[toneMapController + m_bUseCustomAutoExposureMax] = 1

            csgoEXE[toneMapController + m_flCustomAutoExposureMin] = curSettings["NIGHTMODE_VALUE"].toFloat()
            csgoEXE[toneMapController + m_flCustomAutoExposureMax] = curSettings["NIGHTMODE_VALUE"].toFloat()
        }
    }
}