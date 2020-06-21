package rat.poison.scripts

import rat.poison.checkFlags
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.dead
import rat.poison.game.hooks.onFlash
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.flFlashMaxAlpha
import rat.poison.utils.notInGame
import rat.poison.utils.varUtil.strToBool

fun reducedFlash() = onFlash {
	if (!curSettings["ENABLE_REDUCED_FLASH"].strToBool() || !checkFlags("ENABLE_REDUCED_FLASH")) return@onFlash

	if (me > 0 && !me.dead()) {
		csgoEXE[me + flFlashMaxAlpha] = curSettings["FLASH_MAX_ALPHA"].toFloat()
	}
}

fun disableReducedFlash() {
	if (!me.dead() && !notInGame && me >= 0) {
		csgoEXE[me + flFlashMaxAlpha] = 255F
	}
}