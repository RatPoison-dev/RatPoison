package rat.poison.game

import com.badlogic.gdx.math.Vector2
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.netvars.NetVarOffsets.vecViewOffset
import rat.poison.settings.FACTOR_RECOIL
import rat.poison.strToBool
import rat.poison.utils.Angle
import rat.poison.utils.Vector
import rat.poison.utils.normalize
import java.lang.Math.atan
import java.lang.Math.toDegrees

private val angles: ThreadLocal<Angle> = ThreadLocal.withInitial { Vector() }
private val lastPunch = Vector2()

fun calculateAngle(player: Player, dst: Vector): Angle = angles.get().apply {
	val myPunch = player.punch()
	val myPosition = player.position()

	val dX = myPosition.x - dst.x
	val dY = myPosition.y - dst.y
	val dZ = myPosition.z + csgoEXE.float(player + vecViewOffset) - dst.z

	val hyp = Math.sqrt((dX * dX) + (dY * dY))

	if (curSettings["FACTOR_RECOIL"]!!.strToBool()) {
		x = toDegrees(atan(dZ / hyp)) - myPunch.x * 2.0
		y = toDegrees(atan(dY / dX)) - myPunch.y * 2.0
	} else {
		x = toDegrees(atan(dZ / hyp))
		y = toDegrees(atan(dY / dX))
	}

	z = 0.0
	if (dX >= 0.0) y += 180

	normalize()
}