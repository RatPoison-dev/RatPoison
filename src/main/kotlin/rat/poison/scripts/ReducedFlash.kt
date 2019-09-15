package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.dead
import rat.poison.game.hooks.onFlash
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.flFlashMaxAlpha
import rat.poison.strToBool

fun reducedFlash() = onFlash {
	if (!curSettings["ENABLE_REDUCED_FLASH"].strToBool()) return@onFlash

	if (me > 0 && !me.dead()) csgoEXE[me + flFlashMaxAlpha] = curSettings["FLASH_MAX_ALPHA"].toFloat()
}