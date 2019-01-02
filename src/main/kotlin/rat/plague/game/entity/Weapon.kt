

package rat.plague.game.entity

import rat.plague.game.CSGO.csgoEXE
import rat.plague.game.Weapons
import rat.plague.game.me
import rat.plague.game.netvars.NetVarOffsets
import rat.plague.game.netvars.NetVarOffsets.flNextPrimaryAttack
import rat.plague.game.netvars.NetVarOffsets.iClip1
import rat.plague.utils.extensions.uint

typealias Weapon = Long

internal fun Weapon.bullets() = csgoEXE.uint(this + iClip1)

internal fun Weapon.nextPrimaryAttack() = csgoEXE.float(this + flNextPrimaryAttack).toDouble()

internal fun Weapon.canFire(): Boolean = if (bullets() > 0) {
	val nextAttack = nextPrimaryAttack()
	nextAttack <= 0 || nextAttack < me.time()
} else false

internal fun Weapon.type(): Weapons {
	var id = 42
	if (this > 0)
		id = csgoEXE.short(this + NetVarOffsets.iItemDefinitionIndex).toInt()

	return Weapons[id]
}