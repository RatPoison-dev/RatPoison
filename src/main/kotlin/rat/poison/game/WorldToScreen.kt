package rat.poison.game

import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.offsets.ClientOffsets.dwViewMatrix
import rat.poison.utils.Vector

private val viewMatrix = Array(4) { DoubleArray(4) }

fun worldToScreen(from: Vector, vOut: Vector) = try {
	val buffer = clientDLL.read(dwViewMatrix, 4 * 4 * 4)!!
	var offset = 0
	for (row in 0..3) for (col in 0..3) {
		val value = buffer.getFloat(offset.toLong())
		viewMatrix[row][col] = value.toDouble()
		offset += 4 //Changed, error but not compd
	}
	
	vOut.x = viewMatrix[0][0] * from.x + viewMatrix[0][1] * from.y + viewMatrix[0][2] * from.z + viewMatrix[0][3]
	vOut.y = viewMatrix[1][0] * from.x + viewMatrix[1][1] * from.y + viewMatrix[1][2] * from.z + viewMatrix[1][3]
	
	val w = viewMatrix[3][0] * from.x + viewMatrix[3][1] * from.y + viewMatrix[3][2] * from.z + viewMatrix[3][3]
	
	if (!w.isNaN() && w >= 0.01F) { //If infront (on screen)
		val invw = 1.0 / w
		vOut.x *= invw
		vOut.y *= invw
		
		val width = gameWidth
		val height = gameHeight

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

		val width = gameWidth
		val height = gameHeight

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