package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.offsets.EngineOffsets
import rat.poison.utils.generalUtil.strToBool

fun disablePostProcessing() {
    CSGO.csgoEXE[EngineOffsets.s_bOverridePostProcessing] = curSettings["DISABLE_POST_PROCESSING"].strToBool()
}
