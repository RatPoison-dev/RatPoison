

package rat.poison.game.entity

import rat.poison.game.CSGO.ENTITY_SIZE
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.Weapons
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.game.netvars.NetVarOffsets.bHasDefuser
import rat.poison.game.netvars.NetVarOffsets.bIsScoped
import rat.poison.game.netvars.NetVarOffsets.dwBoneMatrix
import rat.poison.game.netvars.NetVarOffsets.fFlags
import rat.poison.game.netvars.NetVarOffsets.hActiveWeapon
import rat.poison.game.netvars.NetVarOffsets.iHealth
import rat.poison.game.netvars.NetVarOffsets.lifeState
import rat.poison.game.netvars.NetVarOffsets.nTickBase
import rat.poison.game.netvars.NetVarOffsets.vecPunch
import rat.poison.game.netvars.NetVarOffsets.vecVelocity
import rat.poison.game.netvars.NetVarOffsets.vecViewOffset
import rat.poison.game.offsets.ClientOffsets.dwEntityList
import rat.poison.settings.HEAD_BONE
import rat.poison.settings.SERVER_TICK_RATE
import rat.poison.utils.Angle
import rat.poison.utils.Vector
import rat.poison.utils.extensions.uint
import rat.poison.utils.readCached
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap

typealias Player = Long

fun Player.weaponIndex(): Int {
	return ((csgoEXE.uint(this + hActiveWeapon) and 0xFFF) - 1).toInt()
}

fun Player.weaponEntity(): Weapon {
	return clientDLL.uint(dwEntityList + weaponIndex() * ENTITY_SIZE)
}

fun Player.weapon(weaponEntity: Weapon = weaponEntity()): Weapons {
	return weaponEntity.type()
}

internal fun Player.flags(): Int = csgoEXE.int(this + fFlags)

internal fun Player.onGround() = flags() and 1 == 1

internal fun Player.health(): Int = csgoEXE.int(this + iHealth)

internal fun Player.lifeState(): Int = csgoEXE.byte(this + lifeState).toInt()

internal fun Player.dead() = try {
	lifeState() != 0 || health() <= 0
} catch (t: Throwable) {
	false
}

private val player2Punch: Long2ObjectMap<Angle> = Long2ObjectOpenHashMap(255)

internal fun Player.punch(): Angle = readCached(player2Punch) {
	x = csgoEXE.float(it + vecPunch).toDouble()
	y = csgoEXE.float(it + vecPunch + 4).toDouble()
	z = 0.0
}

internal fun Player.shotsFired(): Int = csgoEXE.int(this + NetVarOffsets.iShotsFired)

internal fun Player.viewOffset(): Angle = Vector(csgoEXE.float(this + vecViewOffset).toDouble(),
		csgoEXE.float(this + vecViewOffset + 4).toDouble(),
		csgoEXE.float(this + vecViewOffset + 8).toDouble())

internal fun Player.velocity(): Angle = Vector(csgoEXE.float(this + vecVelocity).toDouble(),
		csgoEXE.float(this + vecVelocity + 4).toDouble(),
		csgoEXE.float(this + vecVelocity + 8).toDouble())

internal fun Player.boneMatrix() = csgoEXE.uint(this + dwBoneMatrix)

internal fun Player.bone(offset: Int, boneID: Int = HEAD_BONE, boneMatrix: Long = boneMatrix()) = csgoEXE.float(boneMatrix + ((0x30 * boneID) + offset)).toDouble()

internal fun Player.isScoped(): Boolean = csgoEXE.boolean(this + bIsScoped)

internal fun Player.hasDefuser(): Boolean = csgoEXE.boolean(this + bHasDefuser)

internal fun Player.time(): Double = csgoEXE.int(this + nTickBase) * (1.0 / SERVER_TICK_RATE)

internal fun Player.location(): String = csgoEXE.read(this + NetVarOffsets.szLastPlaceName, 32, true)?.getString(0)
		?: ""