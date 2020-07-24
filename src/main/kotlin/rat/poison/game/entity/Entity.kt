package rat.poison.game.entity

import com.sun.jna.Memory
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.bSpottedByMask
import rat.poison.game.netvars.NetVarOffsets.dwModel
import rat.poison.game.netvars.NetVarOffsets.iTeamNum
import rat.poison.game.netvars.NetVarOffsets.nSurvivalTeam
import rat.poison.game.netvars.NetVarOffsets.vecOrigin
import rat.poison.game.netvars.NetVarOffsets.vecViewOffset
import rat.poison.game.offsets.ClientOffsets.bDormant
import rat.poison.game.offsets.ClientOffsets.dwIndex
import rat.poison.game.offsets.ClientOffsets.pStudioHdr
import rat.poison.utils.Angle
import rat.poison.utils.extensions.uint

typealias Entity = Long

internal fun Entity.spotted(): Boolean {
	val meID = csgoEXE.int(me + dwIndex) - 1
	val spottedByMask = csgoEXE.uint(this + bSpottedByMask)
	val result = spottedByMask and (1 shl meID).toLong()
	return result != 0L
}

internal fun Entity.dormant(): Boolean = try {
	csgoEXE.boolean(this + bDormant)
} catch (t: Throwable) {
	t.printStackTrace()
	false
}

internal fun Entity.team() = csgoEXE.uint(this + iTeamNum)

internal fun Entity.survivalTeam() = csgoEXE.uint(this + nSurvivalTeam)

internal fun Entity.model(): Long = csgoEXE.uint(this + dwModel)

internal fun Entity.studioHdr(): Long = csgoEXE.uint(this + pStudioHdr)

private val entity2Angle: Long2ObjectMap<Angle> = Long2ObjectOpenHashMap(255)

internal fun Entity.position(): Angle {
	val ent = this
	val ang = Angle()
	ang.apply {
		x = csgoEXE.float(ent + vecOrigin)
		y = csgoEXE.float(ent + vecOrigin + 4)
		z = csgoEXE.float(ent + vecOrigin + 8) + csgoEXE.float(ent + vecViewOffset + 8)
	}
	return ang
}

fun Entity.absPosition(): Angle {
	val ent = this
	val ang = Angle()
	ang.apply {
		x = csgoEXE.float(ent + vecOrigin)
		y = csgoEXE.float(ent + vecOrigin + 4)
		z = csgoEXE.float(ent + vecOrigin + 8)
	}
	return ang
}

fun Entity.bones(boneID: Int): Angle {
	val ang = Angle()
	ang.apply {
		x = bone(0xC, boneID)
		y = bone(0x1C, boneID)
		z = bone(0x2C, boneID)
	}
	return ang
}

fun Memory.bones(boneID: Int): Angle {
	val ang = Angle()
	ang.apply {
		x = getFloat(((0x30L * boneID) + 0xC))
		y = getFloat(((0x30L * boneID) + 0x1C))
		z = getFloat(((0x30L * boneID) + 0x2C))
	}
	return ang
}