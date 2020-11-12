package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.hooks.onFlash
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.flFlashMaxAlpha
import rat.poison.utils.generalUtil.strToBool

fun reducedFlash() = onFlash {
	if (!curSettings["ENABLE_REDUCED_FLASH"].strToBool()) return@onFlash

	csgoEXE[me + flFlashMaxAlpha] = curSettings["FLASH_MAX_ALPHA"].toFloat()
}