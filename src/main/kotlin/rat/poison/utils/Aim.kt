package rat.poison.utils

import com.sun.jna.platform.win32.WinDef.POINT
import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.game.CSGO.gameX
import rat.poison.game.CSGO.gameY
import rat.poison.game.clientState
import rat.poison.game.entity.EntityType.Companion.size
import rat.poison.game.offsets.ClientOffsets.dwInput
import rat.poison.game.offsets.EngineOffsets.dwClientState_LastOutgoingCommand
import rat.poison.game.setAngle
import rat.poison.scripts.Input
import rat.poison.scripts.MULTIPLAYER_BACKUP
import rat.poison.scripts.UserCmd
import rat.poison.scripts.sendPacket
import rat.poison.settings.GAME_PITCH
import rat.poison.settings.GAME_SENSITIVITY
import rat.poison.settings.GAME_YAW
import rat.poison.toInt
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

fun writeAim(currentAngle: Angle, destinationAngle: Angle, smoothing: Double, silent: Boolean = false) {
	if (!silent) {
		val dAng = applyFlatSmoothing(currentAngle, destinationAngle, smoothing)
		clientState.setAngle(dAng)
	} else { //Silent Aim
//		sendPacket(false)
//
//		//Probably not correct
//		val pCommands = csgoEXE.int(clientDLL.address + dwInput + 0xEC)
//
//		val iDesiredNumber = csgoEXE.int(clientState + dwClientState_LastOutgoingCommand) + 2
//
//		val pUserCmd = pCommands + (iDesiredNumber % MULTIPLAYER_BACKUP) * 0x64
//		val pOldUserCmd = pCommands + ((iDesiredNumber-1) % MULTIPLAYER_BACKUP) * 0x64
//		val pVerifiedOldUserCmd = pCommands + ((iDesiredNumber-1) % MULTIPLAYER_BACKUP) * 0x68
//
//		println("dwInput: $dwInput      desirednum: $iDesiredNumber    commands:${pCommands}     puserCmd: $pUserCmd         num " + csgoEXE.int(pUserCmd + 0x4))
//		//while (csgoEXE.int(pUserCmd + 0x4) < iDesiredNumber) {
//		//	Thread.sleep(1)
//		//}
//
//		val oldUserCmd = UserCmd()
//		csgoEXE.read(pOldUserCmd, oldUserCmd)
//		println(oldUserCmd.size())
//
//		val IN_ATTACK = 1 shl 0
//
//		oldUserCmd.vecViewAnglesX = destinationAngle.x
//
//		oldUserCmd.iButtons = oldUserCmd.iButtons or IN_ATTACK
//
//		csgoEXE.write(pOldUserCmd, oldUserCmd)
//		csgoEXE.write(pVerifiedOldUserCmd, oldUserCmd)
//		sendPacket(true)
	}
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

	var sens = GAME_SENSITIVITY + .5
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