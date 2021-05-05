package rat.poison.game

import com.badlogic.gdx.math.MathUtils.clamp
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.Player
import rat.poison.game.entity.position
import rat.poison.game.entity.punch
import rat.poison.game.netvars.NetVarOffsets.vecViewOffset
import rat.poison.settings.*
import rat.poison.utils.common.Angle
import rat.poison.utils.common.Vector
import rat.poison.utils.common.normalize
import rat.poison.utils.generalUtil.toInt
import rat.poison.utils.randBoolean
import rat.poison.utils.randDouble
import java.lang.Math.toDegrees
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.sqrt

private val positionVector = ThreadLocal.withInitial { Vector() }
private val punchVector = ThreadLocal.withInitial { Vector() }
private val ang2 = ThreadLocal.withInitial { Vector() }

//TODO smoke this func prolly
fun getCalculatedAngle(player: Player, dst: Vector): Angle {
	val ang = ang2.get()
	val punchVector = punchVector.get()
	val positionVector = positionVector.get()

	val myPunch = player.punch(punchVector)
	val myPosition = player.position(positionVector)

	val dX = myPosition.x - dst.x
	val dY = myPosition.y - dst.y
	val dZ = myPosition.z + csgoEXE.float(player + vecViewOffset) - dst.z

	val hyp = sqrt((dX * dX) + (dY * dY))


	if (FACTOR_RECOIL) {
		if (AIM_ADVANCED) {
			val calcX = toDegrees(atan(dZ / hyp).toDouble()) - myPunch.x * clamp(1F + AIM_RCS_Y, 1F, 2F)
			val calcY = toDegrees(atan(dY / dX).toDouble()) - myPunch.y * clamp(1F + AIM_RCS_X, 1F, 2F)
			ang.x = calcX.toFloat()
			ang.y = calcY.toFloat()
		} else {
			ang.x = (toDegrees(atan(dZ / hyp).toDouble()) - myPunch.x * 2.0).toFloat() //Move these above IFs
			ang.y = (toDegrees(atan(dY / dX).toDouble()) - myPunch.y * 2.0).toFloat()
		}
	} else {
		ang.x = toDegrees(atan(dZ / hyp).toDouble()).toFloat()//Move these above IFs
		ang.y = toDegrees(atan(dY / dX).toDouble()).toFloat()
	}

	ang.z = 0F
	if (dX >= 0.0) ang.y += 180

	ang.normalize()

	return ang
}
private val positionVector1 = ThreadLocal.withInitial { Vector() }
private val playerPunch = ThreadLocal.withInitial { Vector() }
private val deltaVec = ThreadLocal.withInitial { Vector() }
private val ang = ThreadLocal.withInitial { Vector() }
fun realCalcAngle(player: Player, dst: Vector): Angle {
	val positionVector1 = positionVector1.get()
	val ang = ang.get()
	val playerPos = player.position(positionVector1)
	val deltaVec = deltaVec.get()
	val playerPunch = playerPunch.get()
	val delta = deltaVec.set(dst.x - playerPos.x, dst.y - playerPos.y, dst.z - playerPos.z + csgoEXE.float(player + vecViewOffset))
	val myPunch = player.punch(playerPunch)

	var aX = toDegrees(atan2(-delta.z, sqrt(delta.x*delta.x + delta.y*delta.y)).toDouble())
	var aY = toDegrees(atan2(delta.y, delta.x).toDouble())

	if (FACTOR_RECOIL) {
		if (AIM_ADVANCED) {
			val calcX = myPunch.x * clamp(1.0 + AIM_RCS_Y, 1.0, 2.0)
			val calcY = myPunch.y * clamp(1.0 + AIM_RCS_X, 1.0, 2.0)
			aX -= calcX
			aY -= calcY
		} else {
			aX -= myPunch.x * 2F
			aY -= myPunch.y * 2F
		}
	} //else don't factor

	ang.set(aX.toFloat(), aY.toFloat(), 0F)
	ang.normalize()

	return ang
}