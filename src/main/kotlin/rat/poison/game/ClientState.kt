package rat.poison.game

import org.jire.kna.float
import org.jire.kna.set
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.offsets.EngineOffsets.dwViewAngles
import rat.poison.utils.Vector
import rat.poison.utils.vector

typealias ClientState = Long

fun ClientState.angle(): Vector = vector(
	csgoEXE.float(this + dwViewAngles),
	csgoEXE.float(this + dwViewAngles + 4),
	csgoEXE.float(this + dwViewAngles + 8)
)

fun ClientState.setAngle(angle: Vector) {
	if (angle.z != 0F || angle.x < -89 || angle.x > 180 || angle.y < -180 || angle.y > 180
			|| angle.x.isNaN() || angle.y.isNaN() || angle.z.isNaN()) return
	
	csgoEXE[this + dwViewAngles] = angle.x // pitch (up and down)
	csgoEXE[this + dwViewAngles + 4] = angle.y // yaw (side to side)
	// csgo[address + m_dwViewAngles + 8] = angle.z.toFloat() // roll (twist)
}