

package rat.plague.game

import rat.plague.game.CSGO.csgoEXE
import rat.plague.game.offsets.EngineOffsets.dwViewAngles
import rat.plague.utils.Angle
import rat.plague.utils.readCached
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap

typealias ClientState = Long

private val clientState2Angle: Long2ObjectMap<Angle> = Long2ObjectOpenHashMap()

fun ClientState.angle(): Angle = readCached(clientState2Angle) {
	x = csgoEXE.float(it + dwViewAngles).toDouble()
	y = csgoEXE.float(it + dwViewAngles + 4).toDouble()
	z = csgoEXE.float(it + dwViewAngles + 8).toDouble()
}

fun ClientState.setAngle(angle: Angle) {
	if (angle.z != 0.0 || angle.x < -89 || angle.x > 180 || angle.y < -180 || angle.y > 180
			|| angle.x.isNaN() || angle.y.isNaN() || angle.z.isNaN()) return
	
	csgoEXE[this + dwViewAngles] = angle.x.toFloat() // pitch (up and down)
	csgoEXE[this + dwViewAngles + 4] = angle.y.toFloat() // yaw (side to side)
	// csgo[address + m_dwViewAngles + 8] = angle.z.toFloat() // roll (twist)
}