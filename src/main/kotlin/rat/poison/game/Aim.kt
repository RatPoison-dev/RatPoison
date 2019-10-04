package rat.poison.game

import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.netvars.NetVarOffsets.vecViewOffset
import rat.poison.strToBool
import rat.poison.utils.Angle
import rat.poison.utils.Vector
import rat.poison.utils.normalize
import java.lang.Math.atan
import java.lang.Math.toDegrees
import kotlin.math.sqrt

fun getCalculatedAngle(player: Player, dst: Vector): Angle {
	val ang = Angle()

	val myPunch = player.punch()
	val myPosition = player.position()

	val dX = myPosition.x - dst.x
	val dY = myPosition.y - dst.y
	val dZ = myPosition.z + csgoEXE.float(player + vecViewOffset) - dst.z

	val hyp = sqrt((dX * dX) + (dY * dY))

	if (curSettings["FACTOR_RECOIL"].strToBool()) {
		ang.x = toDegrees(atan(dZ / hyp)) - myPunch.x * 2.0
		ang.y = toDegrees(atan(dY / dX)) - myPunch.y * 2.0
	} else {
		ang.x = toDegrees(atan(dZ / hyp))
		ang.y = toDegrees(atan(dY / dX))
	}

	ang.z = 0.0
	if (dX >= 0.0) ang.y += 180

	ang.normalize()

	return ang
}