package rat.poison.scripts.esp

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.hooks.toneMapController
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.strToBool
import rat.poison.ui.uiUpdate
import rat.poison.utils.every

fun espToggle() = every(50) {
    if (keyPressed(curSettings["VISUALS_TOGGLE_KEY"].toInt())) {
        curSettings["ENABLE_ESP"] = !curSettings["ENABLE_ESP"].strToBool()
        if (!curSettings["ENABLE_ESP"].strToBool()) {
            disableAllEsp()

            if (curSettings["ENABLE_NIGHTMODE"].strToBool()) {
                if (toneMapController != 0L) {
                    CSGO.csgoEXE[toneMapController + NetVarOffsets.m_bUseCustomAutoExposureMin] = 1
                    CSGO.csgoEXE[toneMapController + NetVarOffsets.m_bUseCustomAutoExposureMax] = 1

                    CSGO.csgoEXE[toneMapController + NetVarOffsets.m_flCustomAutoExposureMin] = 1F
                    CSGO.csgoEXE[toneMapController + NetVarOffsets.m_flCustomAutoExposureMax] = 1F
                }
            }
        }

        Thread.sleep(100)

        uiUpdate()
    }
}