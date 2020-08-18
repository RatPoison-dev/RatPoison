package rat.poison.game.entity

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.Weapons
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.game.netvars.NetVarOffsets.bInReload
import rat.poison.game.netvars.NetVarOffsets.flNextPrimaryAttack
import rat.poison.game.netvars.NetVarOffsets.iClip1
import rat.poison.utils.extensions.uint

typealias Weapon = Long

internal fun Weapon.bullets() = csgoEXE.uint(this + iClip1)

internal fun Weapon.inReload() = csgoEXE.boolean(this + bInReload)

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

internal fun Weapon.index(): Int = csgoEXE.short(this + NetVarOffsets.iItemDefinitionIndex).toInt()