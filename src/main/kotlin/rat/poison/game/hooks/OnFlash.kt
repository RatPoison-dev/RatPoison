

package rat.poison.game.hooks

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.dead
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.flFlashMaxAlpha
import rat.poison.scripts.aim.meDead
import rat.poison.utils.hook

val onFlash = hook(250) {
	if (me > 0 && !meDead) csgoEXE.float(me + flFlashMaxAlpha) > 0F
	else false
}