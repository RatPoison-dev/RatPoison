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
import rat.poison.settings.GAME_SENSITIVITY
import rat.poison.settings.GAME_YAW
import rat.poison.utils.extensions.refresh
import kotlin.math.round

fun applyFlatSmoothing(currentAngle: Vector, destinationAngle: Vector, smoothing: Float, divisor: Int) = destinationAngle.run {
	val delta = set(x - currentAngle.x, y - currentAngle.y).normalize()

	var smooth = smoothing

	if (smooth == 0F) {
		smooth = 1F
	}

	var randX = if (curSettings.int["AIM_RANDOM_X_VARIATION"] > 0) randInt(0, curSettings.int["AIM_RANDOM_X_VARIATION"]) * randSign() else 0
	var randY = if (curSettings.int["AIM_RANDOM_Y_VARIATION"] > 0) randInt(0, curSettings.int["AIM_RANDOM_Y_VARIATION"]) * randSign() else 0
	val randDZ = curSettings.int["AIM_VARIATION_DEADZONE"] / 100F

	if (delta.x in -randDZ..randDZ && delta.y in -randDZ..randDZ) {
		randX = 0
		randY = 0
	}

	vector(currentAngle.x + (delta.x + ((randX/10F) * (10F / smooth))) / 100F * (100F / smooth) / divisor,
		currentAngle.y + (delta.y + ((randY/10F) * (10F / smooth))) / 100F * (100F / smooth) / divisor).normalize()
}

fun writeAim(currentAngle: Vector, destinationAngle: Vector, smoothing: Float, divisor: Int = 1, silent: Boolean = false) {
	if (!silent) {
		val dAng = applyFlatSmoothing(currentAngle, destinationAngle, smoothing, divisor)
		clientState.setAngle(dAng)
	}
}

fun pathAim(currentAngle: Vector, destinationAngle: Vector, aimSpeed: Int, perfect: Boolean = false, checkOnScreen: Boolean = true, divisor: Int = 1) {
	if (!destinationAngle.isValid()) { return }

	var xFix = currentAngle.y - destinationAngle.y

	//Normalize, fixes flipping to 360/-360 instead of 180/-180 like normal
	while (xFix > 180) xFix -= 360F
	while (xFix <= -180) xFix += 360F

	if (xFix > 180) xFix = 180F
	if (xFix < -180F) xFix = -180F

	val delta = Vector(xFix, currentAngle.x - destinationAngle.x, 0F)

	var sens = GAME_SENSITIVITY + .5
	if (perfect) sens = 1.0

	val dx = round(delta.x / (sens * GAME_PITCH))
	val dy = round(-delta.y / (sens * GAME_YAW))

	var mousePos = POINT().refresh()

	val target = POINT()

	var randX = if (curSettings.int["AIM_RANDOM_X_VARIATION"] > 0) randInt(0, curSettings.int["AIM_RANDOM_X_VARIATION"]) * randSign() else 0
	var randY = if (curSettings.int["AIM_RANDOM_Y_VARIATION"] > 0) randInt(0, curSettings.int["AIM_RANDOM_Y_VARIATION"]) * randSign() else 0
	val randDZ = curSettings.int["AIM_VARIATION_DEADZONE"]

	if (dx.toInt() in -randDZ..randDZ && dy.toInt() in -randDZ..randDZ) {
		randX = 0
		randY = 0
	}

	target.x = (mousePos.x + dx / divisor).toInt() + randX
	target.y = (mousePos.y + dy / divisor).toInt() + randY

	if (checkOnScreen) {
		if (target.x <= 0 || target.x >= gameX + gameWidth || target.y <= 0 || target.y >= gameY + gameHeight) {
			return
		}
	}

	if (perfect) {
		writeAim(currentAngle, destinationAngle, 1F)
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