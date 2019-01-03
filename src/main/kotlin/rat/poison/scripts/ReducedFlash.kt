

package rat.poison.scripts

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.dead
import rat.poison.game.hooks.onFlash
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.flFlashMaxAlpha
import rat.poison.settings.ENABLE_REDUCED_FLASH
import rat.poison.settings.FLASH_MAX_ALPHA

fun reducedFlash() = onFlash {
	if (!ENABLE_REDUCED_FLASH) return@onFlash
	
	if (me > 0 && !me.dead()) csgoEXE[me + flFlashMaxAlpha] = FLASH_MAX_ALPHA
}