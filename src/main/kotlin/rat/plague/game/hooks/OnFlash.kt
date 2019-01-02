

package rat.plague.game.hooks

import rat.plague.game.CSGO.csgoEXE
import rat.plague.game.entity.dead
import rat.plague.game.me
import rat.plague.game.netvars.NetVarOffsets.flFlashMaxAlpha
import rat.plague.utils.hook

val onFlash = hook(256) {
	if (me > 0 && !me.dead()) csgoEXE.float(me + flFlashMaxAlpha) > 0F
	else false
}