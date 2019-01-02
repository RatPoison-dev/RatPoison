

package rat.plague.scripts

import rat.plague.game.CSGO.csgoEXE
import rat.plague.game.entity.dead
import rat.plague.game.hooks.onFlash
import rat.plague.game.me
import rat.plague.game.netvars.NetVarOffsets.flFlashMaxAlpha
import rat.plague.settings.ENABLE_REDUCED_FLASH
import rat.plague.settings.FLASH_MAX_ALPHA

fun reducedFlash() = onFlash {
	if (!ENABLE_REDUCED_FLASH) return@onFlash
	
	if (me > 0 && !me.dead()) csgoEXE[me + flFlashMaxAlpha] = FLASH_MAX_ALPHA
}