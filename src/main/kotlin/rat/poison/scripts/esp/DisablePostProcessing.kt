package rat.poison.scripts.esp

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.offsets.ClientOffsets.bOverridePostProcesing
import rat.poison.utils.generalUtil.strToBool

fun disablePostProcessing() {
    CSGO.csgoEXE[bOverridePostProcesing] = curSettings["DISABLE_POST_PROCESSING"].strToBool()
}