package rat.poison.game

import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.offsets.ClientOffsets.dwViewMatrix
import rat.poison.utils.Vector
import rat.poison.utils.extensions.getFloatArray
import rat.poison.utils.threadLocalPointer

val w2sViewMatrix = Array(4) { DoubleArray(4) }
private val DEFAULT_VECTOR = Vector()
fun worldToScreen(fromx: Float, fromy: Float, fromz: Float, vOut: Vector = DEFAULT_VECTOR): Boolean {
	if (!curSettings.bool["MENU"]) {
		updateViewMatrix()
	}
	vOut.x = (w2sViewMatrix[0][0] * fromx + w2sViewMatrix[0][1] * fromy + w2sViewMatrix[0][2] * fromz + w2sViewMatrix[0][3]).toFloat()
	vOut.y = (w2sViewMatrix[1][0] * fromx + w2sViewMatrix[1][1] * fromy + w2sViewMatrix[1][2] * fromz + w2sViewMatrix[1][3]).toFloat()

	val w = (w2sViewMatrix[3][0] * fromx + w2sViewMatrix[3][1] * fromy + w2sViewMatrix[3][2] * fromz + w2sViewMatrix[3][3]).toFloat()

	val width = gameWidth
	val height = gameHeight

	if (!w.isNaN() && w >= 0.01F) { //If infront (on screen)
		val invw = 1F / w
		vOut.x *= invw
		vOut.y *= invw

		var x = width / 2.0F
		var y = height / 2.0F

		x += 0.5F * vOut.x * width + 0.5F
		y += 0.5F * vOut.y * height + 0.5F

		vOut.x = x
		vOut.y = y

		return true
	} else if (!w.isNaN() && w < 0.01F) { //If behind
		val invw = -1F / w

		vOut.x *= invw
		vOut.y *= invw

		var x = width / 2F
		var y = height / 2F

		x += 0.5F * vOut.x * width + 0.5F
		y -= 0.5F * vOut.y * height + 0.5F

		vOut.x = x
		vOut.y = y

		return false
	} else return false

}
fun worldToScreen(from: Vector, vOut: Vector = DEFAULT_VECTOR): Boolean {
	return worldToScreen(from.x, from.y, from.z, vOut)
}
private const val viewMatrixMemorySize = 4*4*4
private val viewMatrixMemory = threadLocalPointer(viewMatrixMemorySize)
private val floatArray = ThreadLocal.withInitial { FloatArray(16) }
fun updateViewMatrix() { //Call before using multiple world to screens
	if (dwViewMatrix > 0L) {
		val mem = viewMatrixMemory.get()
		clientDLL.read(dwViewMatrix, mem, viewMatrixMemorySize)
		if (mem != null) {
			val floatArray = floatArray.get()
			if (mem.getFloatArray(0, 16, floatArray).all(Float::isFinite)) {
				var offset = 0
				for (row in 0..3) for (col in 0..3) {
					val value = mem.getFloat(offset.toLong())
					w2sViewMatrix[row][col] = value.toDouble()
					offset += 4 //Changed, error but not compd
				}
			}
		}
	}
}