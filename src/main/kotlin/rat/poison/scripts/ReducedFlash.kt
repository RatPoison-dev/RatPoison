package rat.poison.scripts

import org.jire.kna.set
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.hooks.onFlash
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.flFlashMaxAlpha

fun reducedFlash() = onFlash {
	if (!curSettings.bool["ENABLE_REDUCED_FLASH"]) return@onFlash

	csgoEXE[me + flFlashMaxAlpha] = curSettings.float["FLASH_MAX_ALPHA"]
}