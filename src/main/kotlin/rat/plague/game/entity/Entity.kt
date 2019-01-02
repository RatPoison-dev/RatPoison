

package rat.plague.game.entity

import rat.plague.game.CSGO.csgoEXE
import rat.plague.game.me
import rat.plague.game.netvars.NetVarOffsets.bSpottedByMask
import rat.plague.game.netvars.NetVarOffsets.dwModel
import rat.plague.game.netvars.NetVarOffsets.iTeamNum
import rat.plague.game.netvars.NetVarOffsets.nSurvivalTeam
import rat.plague.game.netvars.NetVarOffsets.vecOrigin
import rat.plague.game.netvars.NetVarOffsets.vecViewOffset
import rat.plague.game.offsets.ClientOffsets.bDormant
import rat.plague.game.offsets.ClientOffsets.dwIndex
import rat.plague.game.offsets.ClientOffsets.pStudioHdr
import rat.plague.utils.Angle
import rat.plague.utils.extensions.uint
import rat.plague.utils.readCached
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap

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
	false
}

internal fun Entity.team() = csgoEXE.uint(this + iTeamNum)

internal fun Entity.survivalTeam() = csgoEXE.uint(this + nSurvivalTeam)

internal fun Entity.model(): Long = csgoEXE.uint(this + dwModel)

internal fun Entity.studioHdr(): Long = csgoEXE.uint(this + pStudioHdr)

private val entity2Angle: Long2ObjectMap<Angle> = Long2ObjectOpenHashMap(255)

internal fun Entity.position(): Angle = readCached(entity2Angle) {
	x = csgoEXE.float(it + vecOrigin).toDouble()
	y = csgoEXE.float(it + vecOrigin + 4).toDouble()
	z = csgoEXE.float(it + vecOrigin + 8).toDouble() + csgoEXE.float(it + vecViewOffset + 8)
}