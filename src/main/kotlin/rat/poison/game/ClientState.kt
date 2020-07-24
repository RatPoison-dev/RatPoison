package rat.poison.game

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.offsets.EngineOffsets.dwViewAngles
import rat.poison.utils.Angle

typealias ClientState = Long

fun ClientState.angle(): Angle {
	val tmpAng = Angle()
	tmpAng.x = csgoEXE.float(this + dwViewAngles)
	tmpAng.y = csgoEXE.float(this + dwViewAngles + 4)
	tmpAng.z = csgoEXE.float(this + dwViewAngles + 8)

	return tmpAng
}

fun ClientState.setAngle(angle: Angle) {
	if (angle.z != 0F || angle.x < -89 || angle.x > 180 || angle.y < -180 || angle.y > 180
			|| angle.x.isNaN() || angle.y.isNaN() || angle.z.isNaN()) return
	
	csgoEXE[this + dwViewAngles] = angle.x // pitch (up and down)
	csgoEXE[this + dwViewAngles + 4] = angle.y // yaw (side to side)
	// csgo[address + m_dwViewAngles + 8] = angle.z.toFloat() // roll (twist)
}