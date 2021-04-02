package rat.poison.game

import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.offsets.EngineOffsets.dwViewAngles
import rat.poison.utils.Angle
import rat.poison.utils.Vector

typealias ClientState = Long

fun ClientState.angle(vOut: Vector = Vector()): Vector = vOut.apply {
	x = csgoEXE.float(this@angle + dwViewAngles)
	y = csgoEXE.float(this@angle + dwViewAngles + 4)
	z = csgoEXE.float(this@angle + dwViewAngles + 8)
}

fun ClientState.setAngle(angle: Angle) {
	if (angle.z != 0F || angle.x < -89 || angle.x > 180 || angle.y < -180 || angle.y > 180
			|| angle.x.isNaN() || angle.y.isNaN() || angle.z.isNaN()) return
	
	csgoEXE[this + dwViewAngles] = angle.x // pitch (up and down)
	csgoEXE[this + dwViewAngles + 4] = angle.y // yaw (side to side)
	// csgo[address + m_dwViewAngles + 8] = angle.z.toFloat() // roll (twist)
}