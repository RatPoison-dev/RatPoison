package rat.poison.game

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.offsets.EngineOffsets.dwViewAngles
import rat.poison.utils.Angle
import rat.poison.utils.readCached
import it.unimi.dsi.fastutil.longs.Long2ObjectMap
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap

typealias ClientState = Long

fun ClientState.angle(): Angle {
	val tmpAng = Angle()
	tmpAng.x = csgoEXE.float(this + dwViewAngles).toDouble()
	tmpAng.y = csgoEXE.float(this + dwViewAngles + 4).toDouble()
	tmpAng.z = csgoEXE.float(this + dwViewAngles + 8).toDouble()

	return tmpAng
}

fun ClientState.setAngle(angle: Angle) {
	if (angle.z != 0.0 || angle.x < -89 || angle.x > 180 || angle.y < -180 || angle.y > 180
			|| angle.x.isNaN() || angle.y.isNaN() || angle.z.isNaN()) return
	
	csgoEXE[this + dwViewAngles] = angle.x.toFloat() // pitch (up and down)
	csgoEXE[this + dwViewAngles + 4] = angle.y.toFloat() // yaw (side to side)
	// csgo[address + m_dwViewAngles + 8] = angle.z.toFloat() // roll (twist)
}