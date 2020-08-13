package rat.poison.game.entity

import com.badlogic.gdx.math.Vector3
import com.sun.jna.Memory
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import org.jire.arrowhead.unsign
import rat.poison.game.*
import rat.poison.game.CSGO.ENTITY_SIZE
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.game.netvars.NetVarOffsets.ArmorValue
import rat.poison.game.netvars.NetVarOffsets.aimPunchAngle
import rat.poison.game.netvars.NetVarOffsets.angEyeAngles
import rat.poison.game.netvars.NetVarOffsets.bGunGameImmunity
import rat.poison.game.netvars.NetVarOffsets.bHasDefuser
import rat.poison.game.netvars.NetVarOffsets.bHasHelmet
import rat.poison.game.netvars.NetVarOffsets.bIsScoped
import rat.poison.game.netvars.NetVarOffsets.dwBoneMatrix
import rat.poison.game.netvars.NetVarOffsets.fFlags
import rat.poison.game.netvars.NetVarOffsets.flFlashDuration
import rat.poison.game.netvars.NetVarOffsets.hActiveWeapon
import rat.poison.game.netvars.NetVarOffsets.iCompetitiveRanking
import rat.poison.game.netvars.NetVarOffsets.iCompetitiveWins
import rat.poison.game.netvars.NetVarOffsets.iDeaths
import rat.poison.game.netvars.NetVarOffsets.iHealth
import rat.poison.game.netvars.NetVarOffsets.iKills
import rat.poison.game.netvars.NetVarOffsets.lifeState
import rat.poison.game.netvars.NetVarOffsets.m_iAccount
import rat.poison.game.netvars.NetVarOffsets.nTickBase
import rat.poison.game.netvars.NetVarOffsets.vecVelocity
import rat.poison.game.netvars.NetVarOffsets.vecViewOffset
import rat.poison.game.offsets.ClientOffsets.dwEntityList
import rat.poison.game.offsets.ClientOffsets.dwIndex
import rat.poison.game.offsets.ClientOffsets.dwPlayerResource
import rat.poison.game.offsets.EngineOffsets.dwClientState_PlayerInfo
import rat.poison.settings.HEAD_BONE
import rat.poison.settings.SERVER_TICK_RATE
import rat.poison.utils.Angle
import rat.poison.utils.Vector
import rat.poison.utils.extensions.uint
import rat.poison.utils.readCached
import rat.poison.utils.to

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
internal fun Memory.health(): Int = this.getInt(iHealth)

internal fun Player.money(): Int = csgoEXE.int(this + m_iAccount)
internal fun Memory.money(): Int = this.getInt(m_iAccount)

internal fun Player.armor(): Int = csgoEXE.int(this + ArmorValue)
internal fun Memory.armor(): Int = this.getInt(ArmorValue)

internal fun Player.hasHelmet(): Boolean = csgoEXE.int(this + bHasHelmet) > 0
internal fun Memory.hasHelmet(): Boolean = this.getInt(bHasHelmet) > 0

internal fun Player.flashed(): Boolean = csgoEXE.float(this + flFlashDuration) > 0.25f
internal fun Memory.flashed(): Boolean = this.getFloat(flFlashDuration) > 0.25f

internal fun Player.lifeState(): Int = csgoEXE.byte(this + lifeState).toInt()

internal fun Player.dead(): Boolean = (lifeState() != 0 || health() <= 0)


internal fun Player.punch(): Angle {
	val tmpAng = Angle()
	tmpAng.x = csgoEXE.float(this + aimPunchAngle)
	tmpAng.y = csgoEXE.float(this + aimPunchAngle + 4)
	tmpAng.z = 0F

	return tmpAng
}

internal fun Player.shotsFired(): Int = csgoEXE.int(this + NetVarOffsets.iShotsFired)

internal fun Player.viewOffset(): Angle = Vector(csgoEXE.float(this + vecViewOffset),
		csgoEXE.float(this + vecViewOffset + 4),
		csgoEXE.float(this + vecViewOffset + 8))

internal fun Player.velocity(): Angle = Vector(csgoEXE.float(this + vecVelocity),
		csgoEXE.float(this + vecVelocity + 4),
		csgoEXE.float(this + vecVelocity + 8))


private val angle2Vector: Long2ObjectMap<Vector> = Long2ObjectOpenHashMap()

internal fun Player.eyeAngle(): Angle =
		if (this == me) clientState.angle()
		else Angle(csgoEXE.float(this + angEyeAngles),
				csgoEXE.float(this + angEyeAngles + 4),
				csgoEXE.float(this + angEyeAngles + 8))


internal fun Player.direction(): Vector = readCached(angle2Vector) {
	eyeAngle().to(forward = this)
}

internal fun Player.boneMatrix() = csgoEXE.uint(this + dwBoneMatrix)

internal fun Player.bone(offset: Int, boneID: Int = HEAD_BONE, boneMatrix: Long = boneMatrix()) = csgoEXE.float(boneMatrix + ((0x30 * boneID) + offset))

internal fun Player.isScoped(): Boolean = csgoEXE.boolean(this + bIsScoped)
internal fun Memory.isScoped(): Boolean = this.getByte(bIsScoped) > 0

internal fun Player.hasDefuser(): Boolean = csgoEXE.boolean(this + bHasDefuser)

internal fun Player.time(): Double = csgoEXE.int(this + nTickBase) * (1.0 / SERVER_TICK_RATE)

internal fun Player.location(): String = csgoEXE.read(this + NetVarOffsets.szLastPlaceName, 32, true)?.getString(0)
		?: ""

internal fun Player.observerMode(): Int = csgoEXE.int(this + NetVarOffsets.m_iObserverMode)

internal fun Player.isSpectating(): Boolean = observerMode() > 0

internal fun Player.isProtected(): Boolean = csgoEXE.boolean(this + bGunGameImmunity)

internal fun Player.nearestBone(): Int {
	val studioModel = csgoEXE.uint(studioHdr())
	val boneOffset = csgoEXE.uint(studioModel + 0xA0)
	val boneMatrix = boneMatrix()
	val numBones = csgoEXE.uint(studioModel + 0x9C).toInt()

	val w2sRetVec = Vector(0F, 0F, 0F)

	//Get actual size
	val modelMemory: Memory by lazy {
		Memory(25000) //Fuck you
	}
	val boneMemory: Memory by lazy {
		Memory(4032)
	}

	csgoEXE.read(studioModel + boneOffset, modelMemory)
	csgoEXE.read(boneMatrix, boneMemory)

	var closestDst2 = Float.MAX_VALUE
	var nearestBone = -999

	//Change to loop set amount of bones
	var offset = 0
	for (idx in 0 until numBones) {
		val parent = modelMemory.getInt(0x4L + offset)

		if (parent != -1) {
			val flags = modelMemory.getInt(0xA0L + offset).unsign() and 0x100
			if (flags != 0L) {
				val tPunch = me.punch()

				if (worldToScreen(boneMemory.vector(parent * 0x30L, 0x0C, 0x1C, 0x2C), w2sRetVec)) {
					val tempVec3 = Vector3(w2sRetVec.x, w2sRetVec.y, w2sRetVec.z)

					val tX = CSGO.gameWidth / 2 - ((CSGO.gameWidth / 95F) * tPunch.y)
					val tY = CSGO.gameHeight / 2 - ((CSGO.gameHeight / 95F) * tPunch.x)

					val dst2 = tempVec3.dst2(tX, tY, 0F)

					if (dst2 < closestDst2) {
						closestDst2 = dst2
						nearestBone = parent
					}
				}

				if (worldToScreen(boneMemory.vector(idx * 0x30L, 0x0C, 0x1C, 0x2C), w2sRetVec)) {
					val tempVec3 = Vector3(w2sRetVec.x, w2sRetVec.y, w2sRetVec.z)

					val tX = CSGO.gameWidth / 2 - ((CSGO.gameWidth / 95F) * tPunch.y)
					val tY = CSGO.gameHeight / 2 - ((CSGO.gameHeight / 95F) * tPunch.x)

					val dst2 = tempVec3.dst2(tX, tY, 0F)

					if (dst2 < closestDst2) {
						closestDst2 = dst2
						nearestBone = idx
					}
				}
			}
		}
		offset += 216
	}

	return nearestBone

}

internal fun Memory.vector(addy: Long, xOff: Long, yOff: Long, zOff: Long): Vector {
	val x = getFloat(addy + xOff)
	val y = getFloat(addy + yOff)
	val z = getFloat(addy + zOff)

	return Vector(x, y, z)
}

internal fun Player.name(): String {
	val nameMem: Memory by lazy {
		Memory(320)
	}

	val entID = csgoEXE.uint(this + dwIndex) - 1
	val a = csgoEXE.uint(clientState + dwClientState_PlayerInfo)
	val b = csgoEXE.uint(a + 0x40)
	val c = csgoEXE.uint(b + 0x0C)
	val d = csgoEXE.uint(c + 0x28 + entID * 0x34)

	csgoEXE.read(d, nameMem)

	val name = nameMem.getString(0x10)
	nameMem.clear()
	return name
}

internal fun Player.steamID(): String {
	val mem: Memory by lazy {
		Memory(0x140)
	}

	val entID = csgoEXE.uint(this + dwIndex) - 1

	val a = csgoEXE.uint(clientState + dwClientState_PlayerInfo)
	val b = csgoEXE.uint(a + 0x40)
	val c = csgoEXE.uint(b + 0x0C)
	val d = csgoEXE.uint(c + 0x28 + entID * 0x34)

	csgoEXE.read(d, mem)

	val sID = mem.getString(0x94) //0x90 is int of steamID
	mem.clear()
	return sID
}

internal fun Player.rank(): Int {
	val index = csgoEXE.uint(this + dwIndex)

	return (csgoEXE.int(clientDLL.uint(dwPlayerResource) + iCompetitiveRanking + index * 4))
}

internal fun Player.kills(): Int {
	val index = csgoEXE.uint(this + dwIndex)

	return (csgoEXE.int(clientDLL.uint(dwPlayerResource) + iKills + index * 4))
}

internal fun Player.deaths(): Int {
	val index = csgoEXE.uint(this + dwIndex)

	return (csgoEXE.int(clientDLL.uint(dwPlayerResource) + iDeaths + index * 4))
}

internal fun Player.wins(): Int {
	val index = csgoEXE.uint(this + dwIndex)

	return (csgoEXE.int(clientDLL.uint(dwPlayerResource) + iCompetitiveWins + index * 4))
}

internal fun Player.hltv(): Boolean {
	val mem: Memory by lazy {
		Memory(0x140)
	}

	val entID = csgoEXE.uint(this + dwIndex) - 1

	val a = csgoEXE.uint(clientState + dwClientState_PlayerInfo)
	val b = csgoEXE.uint(a + 0x40)
	val c = csgoEXE.uint(b + 0x0C)
	val d = csgoEXE.uint(c + 0x28 + entID * 0x34)

	csgoEXE.read(d, mem)

	val hltvB = mem.getByte(0x13D).unsign() > 0
	mem.clear()
	return hltvB
}