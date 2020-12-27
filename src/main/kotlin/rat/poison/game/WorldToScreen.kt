package rat.poison.game

import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.offsets.ClientOffsets.dwViewMatrix
import rat.poison.utils.Vector
import rat.poison.utils.threadLocalMemory

val w2sViewMatrix = Array(4) { DoubleArray(4) }

const val W2S_FAILED = -1F

fun Vector.w2s() = z != W2S_FAILED

fun worldToScreen(from: Vector, vOut: Vector): Boolean {
	if (!curSettings.bool["MENU"]) {
		updateViewMatrix()
	}
	
	vOut.x = (w2sViewMatrix[0][0] * from.x + w2sViewMatrix[0][1] * from.y + w2sViewMatrix[0][2] * from.z + w2sViewMatrix[0][3]).toFloat()
	vOut.y = (w2sViewMatrix[1][0] * from.x + w2sViewMatrix[1][1] * from.y + w2sViewMatrix[1][2] * from.z + w2sViewMatrix[1][3]).toFloat()
	
	val w = (w2sViewMatrix[3][0] * from.x + w2sViewMatrix[3][1] * from.y + w2sViewMatrix[3][2] * from.z + w2sViewMatrix[3][3]).toFloat()
	
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

fun worldToScreen(from: Vector): Vector {
	updateViewMatrix()
	
	val w0 = w2sViewMatrix[0]
	val w1 = w2sViewMatrix[1]
	var vOut = Vector(
		(w0[0] * from.x + w0[1] * from.y + w0[2] * from.z + w0[3]).toFloat(),
		(w1[0] * from.x + w1[1] * from.y + w1[2] * from.z + w1[3]).toFloat()
	)
	
	val w3 = w2sViewMatrix[3]
	val w = (w3[0] * from.x + w3[1] * from.y + w3[2] * from.z + w3[3]).toFloat()
	
	val width = gameWidth
	val height = gameHeight
	
	if (!w.isNaN() && w >= 0.01F) { //If infront (on screen)
		val invw = 1F / w
		vOut = vOut.set(vOut.x * invw, vOut.y * invw)
		
		var x = width / 2.0F
		var y = height / 2.0F
		
		x += 0.5F * vOut.x * width + 0.5F
		y += 0.5F * vOut.y * height + 0.5F
		
		return Vector(x, y, 0F)
	} else if (!w.isNaN() && w < 0.01F) { //If behind
		val invw = -1F / w
		
		vOut = vOut.set(vOut.x * invw, vOut.y * invw)
		
		var x = width / 2F
		var y = height / 2F
		
		x += 0.5F * vOut.x * width + 0.5F
		y -= 0.5F * vOut.y * height + 0.5F
		
		return Vector(x, y, W2S_FAILED)
	} else return Vector(z = W2S_FAILED)
}

private val viewMatrixMemory = threadLocalMemory(4 * 4 * 4)
private val floatArray = ThreadLocal.withInitial { FloatArray(16) }

fun updateViewMatrix() { //Call before using multiple world to screens
	if (dwViewMatrix > 0L) {
		val buffer = viewMatrixMemory.get()
		if (clientDLL.read(dwViewMatrix, buffer)) {
			val floatArray = floatArray.get()
			buffer.read(0, floatArray, 0, 16)
			if (floatArray.all(Float::isFinite)) {
				var offset = 0
				for (row in 0..3) for (col in 0..3) {
					val value = buffer.getFloat(offset.toLong())
					w2sViewMatrix[row][col] = value.toDouble()
					offset += 4 //Changed, error but not compd
				}
			}
		}
	}
}