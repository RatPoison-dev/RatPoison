package rat.poison.utils

import com.sun.jna.platform.win32.WinDef.POINT
import rat.poison.curSettings
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.CSGO.gameX
import rat.poison.game.CSGO.gameY
import rat.poison.game.clientState
import rat.poison.game.setAngle
import rat.poison.settings.GAME_PITCH
import rat.poison.settings.GAME_YAW
import rat.poison.utils.extensions.refresh
import kotlin.math.round

private val delta = ThreadLocal.withInitial { Vector() }

fun applyFlatSmoothing(currentAngle: Angle, destinationAngle: Angle, smoothing: Double) = destinationAngle.apply {
	x -= currentAngle.x
	y -= currentAngle.y
	z = 0.0
	normalize()

	var smooth = smoothing

	if (smooth == 0.0) {
		smooth = 1.0
	}

	x = currentAngle.x + x / 100 * (100 / smooth)
	y = currentAngle.y + y / 100 * (100 / smooth)

	normalize()
}

fun writeAim(currentAngle: Angle, destinationAngle: Angle, smoothing: Double) {
	val dAng = applyFlatSmoothing(currentAngle, destinationAngle, smoothing)
	clientState.setAngle(dAng)
}

fun pathAim(currentAngle: Angle, destinationAngle: Angle, aimSpeed: Int, perfect: Boolean = false, checkOnScreen: Boolean = true, divisor: Int = 1) {
	if (!destinationAngle.isValid()) { return }

	val delta = delta.get()

	var xFix = currentAngle.y - destinationAngle.y

	//Normalize, fixes flipping to 360/-360 instead of 180/-180 like normal
	while (xFix > 180) xFix -= 360
	while (xFix <= -180) xFix += 360

	if (xFix > 180) xFix = 180.0
	if (xFix < -180F) xFix = -180.0

	delta.set(xFix, currentAngle.x - destinationAngle.x, 0.0)

	var sens = curSettings["GAME_SENSITIVITY"].toDouble() + .5
	if (perfect) sens = 1.0

	val dx = round(delta.x / (sens * GAME_PITCH))
	val dy = round(-delta.y / (sens * GAME_YAW))

	var mousePos = POINT().refresh()

	val target = POINT()

	var randX = if (curSettings["AIM_RANDOM_X_VARIATION"].toInt() > 0) randInt(0, curSettings["AIM_RANDOM_X_VARIATION"].toInt()) * randSign() else 0
	var randY = if (curSettings["AIM_RANDOM_Y_VARIATION"].toInt() > 0) randInt(0, curSettings["AIM_RANDOM_Y_VARIATION"].toInt()) * randSign() else 0
	val randDZ = curSettings["AIM_VARIATION_DEADZONE"].toInt()

	if (dx.toInt() in -randDZ..randDZ) {
		randX = 0
	}

	if (dy.toInt() in -randDZ..randDZ) {
		randY = 0
	}

	target.x = (mousePos.x + dx / divisor).toInt() + randX //You do be testing some licks
	target.y = (mousePos.y + dy / divisor).toInt() + randY

	if (checkOnScreen) {
		if (target.x <= 0 || target.x >= gameX + gameWidth || target.y <= 0 || target.y >= gameY + gameHeight) {
			return
		}
	}

	if (perfect) {
		writeAim(currentAngle, destinationAngle, 1.0)
		Thread.sleep(50)
	} else HumanMouse.fastSteps(mousePos, target) { steps, _ ->
		mousePos = mousePos.refresh()

		val tx = target.x - mousePos.x
		val ty = target.y - mousePos.y

		var halfIndex = steps / 2
		if (halfIndex == 0) halfIndex = 1

		mouseMove(tx / halfIndex, ty / halfIndex)

		Thread.sleep(aimSpeed.toLong())
	}
}