package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.hooks.toneMapController
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.scripts.esp.disableAllEsp
import rat.poison.ui.uiUpdate
import rat.poison.utils.ObservableBoolean
import rat.poison.utils.every
import rat.poison.utils.varUtil.strToBool

// Only important binds

var espToggleKey = ObservableBoolean({ curSettings["ENABLE_ESP_KEY_TYPE"] == "SwitchKey" && keyPressed(curSettings["ENABLE_ESP_KEY"].toInt()) })
fun toggleListeners() = every(50) {
    espToggleKey.update()
    if (espToggleKey.justBecameTrue) {
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
        uiUpdate()
    }
}