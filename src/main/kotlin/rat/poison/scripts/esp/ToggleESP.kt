package rat.poison.scripts.esp

import org.jire.arrowhead.keyPressed
import rat.poison.checkFlags
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.hooks.toneMapController
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.scripts.disableFovChanger
import rat.poison.scripts.disableReducedFlash
import rat.poison.ui.uiUpdate
import rat.poison.utils.ObservableBoolean
import rat.poison.utils.every
import rat.poison.utils.varUtil.strToBool

val glowEspFlags = ObservableBoolean({curSettings["GLOW_ESP"].strToBool() && checkFlags("GLOW_ESP")})
val chamsEspFlags =  ObservableBoolean({curSettings["CHAMS_ESP"].strToBool() && checkFlags("CHAMS_ESP")})
val fovChangerFlags = ObservableBoolean({curSettings["ENABLE_FOV_CHANGER"].strToBool() && !checkFlags("ENABLE_FOV_CHANGER")})
val reducedFlashFlags = ObservableBoolean({curSettings["ENABLE_REDUCED_FLASH"].strToBool() && !checkFlags("ENABLE_REDUCED_FLASH")})

fun espToggle() = every(50) {
    if ((curSettings["ENABLE_ESP_SWITCH_ON_KEY"].strToBool() && keyPressed(curSettings["ENABLE_ESP_SWITCH_KEY"].toInt()))) {
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
    glowEspFlags.update()
    if (glowEspFlags.justBecameFalse) {
        disableGlowAndChams()
    }
    chamsEspFlags.update()
    if (chamsEspFlags.justBecameFalse) {
        disableGlowAndChams()
    }
    fovChangerFlags.update()
    if (fovChangerFlags.justBecameFalse) {
        disableFovChanger()
    }
    reducedFlashFlags.update()
    if (reducedFlashFlags.justBecameFalse) {
        disableReducedFlash()
    }
}