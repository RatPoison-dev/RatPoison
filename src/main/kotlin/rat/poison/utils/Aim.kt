package rat.poison.utils

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
import rat.poison.utils.extensions.set
import com.sun.jna.platform.win32.WinDef.POINT
import rat.poison.game.worldToScreen
import kotlin.math.round

private val mousePos = ThreadLocal.withInitial { POINT() }
private val target = ThreadLocal.withInitial { POINT() }

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

fun pathAim(currentAngle: Angle, destinationAngle: Angle, aimSpeed: Int,
			sensMultiplier: Double = 1.0, perfect: Boolean = false) {
	if (!destinationAngle.isValid()) { return }

	val delta = delta.get()

	var xFix = currentAngle.y - destinationAngle.y

	//Normalize, fixes flipping to 360/-360 instead of 180/-180 like normal
	while (xFix > 180) xFix -= 360
	while (xFix <= -180) xFix += 360

	if (xFix > 180) xFix = 180.0
	if (xFix < -180F) xFix = -180.0

	delta.set(xFix, currentAngle.x - destinationAngle.x, 0.0)


	var sens = GAME_SENSITIVITY * sensMultiplier
	if (sens < GAME_SENSITIVITY) sens = GAME_SENSITIVITY
	if (perfect) sens = 1.0

	val dx = round(delta.x / (sens * GAME_PITCH))
	val dy = round(-delta.y / (sens * GAME_YAW))

	val mousePos = mousePos.get().refresh()

	val target = target.get()

	target.set((mousePos.x + dx/2).toInt(), (mousePos.y + dy/2).toInt())

	if (target.x <= 0 || target.x >= gameX + gameWidth || target.y <= 0 || target.y >= gameY + gameHeight) { return }

	if (perfect) {
		writeAim(currentAngle, destinationAngle, 1.0)
		Thread.sleep(20)
	} else HumanMouse.fastSteps(mousePos, target) { steps, _ ->
		mousePos.refresh()

		val tx = target.x - mousePos.x
		val ty = target.y - mousePos.y

		var halfIndex = steps / 2
		if (halfIndex == 0) halfIndex = 1
		mouseMove(tx / halfIndex, ty / halfIndex)

		Thread.sleep(aimSpeed.toLong())
	}
}