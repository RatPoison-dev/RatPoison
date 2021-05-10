package rat.poison.utils

import com.sun.jna.platform.win32.WinDef.POINT
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.CSGO.gameX
import rat.poison.game.CSGO.gameY
import rat.poison.game.clientState
import rat.poison.game.setAngle
import rat.poison.settings.*
import rat.poison.utils.common.*
import rat.poison.utils.extensions.refresh
import kotlin.math.*

private val delta = ThreadLocal.withInitial { Vector() }

fun applyFlatSmoothing(currentAngle: Angle, destinationAngle: Angle, smoothing: Int) = destinationAngle.apply {
	var smooth = smoothing.toFloat()

	if (smooth < 1) {
		smooth = 1F
	} else if (smooth > 100) {
		smooth = 100F
	}

	x -= currentAngle.x
	y -= currentAngle.y
	z = 0F
	normalize()

	x = currentAngle.x + x / 100 * (100 / smooth)
	y = currentAngle.y + y / 100 * (100 / smooth)
	normalize()
}

fun writeAim(currentAngle: Angle, destinationAngle: Angle, smoothing: Int) {
	val dAng = applyFlatSmoothing(currentAngle, destinationAngle, smoothing)
	clientState.setAngle(dAng)
}

private val point1 = ThreadLocal.withInitial { POINT() }
private val point2 = ThreadLocal.withInitial { VectorInt() }
fun pathAim(currentAngle: Angle, destinationAngle: Angle, smoothing: Int, checkOnScreen: Boolean = true) {
	if (!destinationAngle.isValid()) { return }

	val perfect = smoothing <= 0

	var xFix = currentAngle.y - destinationAngle.y

	//Normalize, fixes flipping to 360/-360 instead of 180/-180 like normal
	while (xFix > 180) xFix -= 360F
	while (xFix <= -180) xFix += 360F

	if (xFix > 180) xFix = 180F
	if (xFix < -180F) xFix = -180F

	val delta = delta.get()
	delta.set(xFix, currentAngle.x - destinationAngle.x, 0F)

	var sens = GAME_SENSITIVITY
	if (perfect) sens = 1.0

	val dx = round(delta.x / (sens * GAME_PITCH))
	val dy = round(-delta.y / (sens * GAME_YAW))

	val mousePos = point1.get().refresh()

	val target = point2.get()
	target.reset()

	target.x = (mousePos.x + dx).toInt()
	target.y = (mousePos.y + dy).toInt()

	if (checkOnScreen) {
		if (target.x <= 0 || target.x >= gameX + gameWidth || target.y <= 0 || target.y >= gameY + gameHeight) {
			return
		}
	}

	mousePos.refresh()

	val deadzone = 2F

	val distX: Float = (target.x - mousePos.x).toFloat()
	val distY: Float = (target.y - mousePos.y).toFloat()

	var totalX: Float = abs(distX)
	var totalY: Float = abs(distY)

	val fracX: Float = (totalX / smoothing) * sign(distX)
	val fracY: Float = (totalY / smoothing) * sign(distY)

	var toAddX = 0F
	var toAddY = 0F

	for (step in 1..smoothing) {
		var tx = fracX
		var ty = fracY

		if (totalX <= deadzone) {
			totalX = 0F
			tx = 0F
		}

		if (totalY <= deadzone) {
			totalY = 0F
			ty = 0F
		}

		var setX = 0F
		var setY = 0F

		toAddX += tx
		toAddY += ty

		if (abs(toAddX) >= 1F) {
			setX = if (toAddX > 0) {
				ceil(toAddX)
			} else {
				floor(toAddX)
			}
			totalX -= abs(setX)
			toAddX -= setX
		}

		if (abs(toAddY) >= 1F) {
			setY = if (toAddY > 0) {
				ceil(toAddY)
			} else {
				floor(toAddY)
			}
			totalY -= abs(setY)
			toAddY -= setY
		}

		mouseMove(setX.toInt(), setY.toInt())
		Thread.sleep(1)
	}
}