package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.offsets.ClientOffsets.bOverridePostProcesing
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.shouldPostProcess

fun disablePostProcessing() = every(10000, true, inGameCheck = true) {
    if (!shouldPostProcess) return@every

    if (curSettings["DISABLE_POST_PROCESSING"].strToBool()) {
        csgoEXE[bOverridePostProcesing] = true
    } else if (csgoEXE.boolean(bOverridePostProcesing)) {
        csgoEXE[bOverridePostProcesing] = false
    }
}