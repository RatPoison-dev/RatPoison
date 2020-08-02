package rat.poison.scripts.esp

import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.offsets.ClientOffsets.bOverridePostProcesing
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool

fun disablePostProcessing() = every(10000) {
    if (curSettings["DISABLE_POST_PROCESSING"].strToBool()) {
        csgoEXE[bOverridePostProcesing] = curSettings["DISABLE_POST_PROCESSING"].strToBool()
    } else if (csgoEXE.boolean(bOverridePostProcesing)) {
        csgoEXE[bOverridePostProcesing] = false
    }
}