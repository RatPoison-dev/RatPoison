package rat.poison.game

import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.offsets.ClientOffsets.dwViewMatrix
import rat.poison.utils.Vector
import rat.poison.utils.generalUtil.strToBool

val w2sViewMatrix = Array(4) { DoubleArray(4) }

fun worldToScreen(from: Vector, vOut: Vector) = try {
	if (!curSettings["MENU"].strToBool()) {
		updateViewMatrix()
	}
	vOut.x = w2sViewMatrix[0][0] * from.x + w2sViewMatrix[0][1] * from.y + w2sViewMatrix[0][2] * from.z + w2sViewMatrix[0][3]
	vOut.y = w2sViewMatrix[1][0] * from.x + w2sViewMatrix[1][1] * from.y + w2sViewMatrix[1][2] * from.z + w2sViewMatrix[1][3]

	val w = w2sViewMatrix[3][0] * from.x + w2sViewMatrix[3][1] * from.y + w2sViewMatrix[3][2] * from.z + w2sViewMatrix[3][3]

	val width = gameWidth
	val height = gameHeight

	if (!w.isNaN() && w >= 0.01F) { //If infront (on screen)
		val invw = 1.0 / w
		vOut.x *= invw
		vOut.y *= invw

		var x = width / 2.0
		var y = height / 2.0

		x += 0.5 * vOut.x * width + 0.5
		y += 0.5 * vOut.y * height + 0.5 //For future, -= was changed to +=, it was flipped

		vOut.x = x
		vOut.y = y

		true
	} else if (!w.isNaN() && w < 0.01F) { //If behind
		val invw = -1.0 / w

		vOut.x *= invw
		vOut.y *= invw

		var x = width / 2.0
		var y = height / 2.0

		x += 0.5 * vOut.x * width + 0.5
		y -= 0.5 * vOut.y * height + 0.5 //-?

		vOut.x = x
		vOut.y = y

		false
	} else false
} catch (e: Exception) {
	e.printStackTrace()
	false
}

fun updateViewMatrix() { //Call before using multiple world to screens
	if (dwViewMatrix > 0L) {
		val buffer = clientDLL.read(dwViewMatrix, 4 * 4 * 4)

		if (buffer != null) {
			if (buffer.getFloatArray(0, 16).all(Float::isFinite)) {
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