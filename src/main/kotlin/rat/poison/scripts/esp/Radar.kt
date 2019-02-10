package rat.poison.scripts.esp

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.bSpotted
import rat.poison.game.offsets.ClientOffsets.bDormant
import rat.poison.settings.ENABLE_RADAR
import rat.poison.utils.every
import rat.poison.game.entity.EntityType.Companion.ccsPlayer

internal fun radar() = every(1) {
	if (!ENABLE_RADAR) return@every

	forEntities(ccsPlayer) {
		val entity = it.entity
		if (entity.dead() || entity.dormant()) return@forEntities false
			entity.show()

		false
	}
}

private fun Entity.show() {
	csgoEXE[this + bSpotted] = true
	csgoEXE[this + bDormant] = false
}