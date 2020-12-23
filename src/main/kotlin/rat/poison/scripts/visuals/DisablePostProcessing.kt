package rat.poison.scripts.visuals

import org.jire.kna.boolean
import org.jire.kna.set
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.offsets.ClientOffsets.bOverridePostProcesing
import rat.poison.utils.every
import rat.poison.utils.shouldPostProcess

fun disablePostProcessing() = every(10000, true, inGameCheck = true) {
    if (!shouldPostProcess) return@every

    if (curSettings.bool["DISABLE_POST_PROCESSING"]) {
        csgoEXE[bOverridePostProcesing] = true
    } else if (csgoEXE.boolean(bOverridePostProcesing)) {
        csgoEXE[bOverridePostProcesing] = false
    }
}