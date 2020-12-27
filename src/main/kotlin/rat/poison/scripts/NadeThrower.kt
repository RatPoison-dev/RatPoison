package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.entity.absPosition
import rat.poison.game.entity.eyeAngle
import rat.poison.game.me
import rat.poison.game.realCalcAngle
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meDead
import rat.poison.settings.MENUTOG
import rat.poison.utils.*
import rat.poison.utils.generalUtil.cToDouble
import rat.poison.utils.generalUtil.cToFloat

@Volatile
private var calcRes = 0F

@Volatile
private var closestAngleL: Long = vectorLong(90F, 90F, 90F)
@Volatile
private var closestDistance = 100F
@Volatile
private var clfSpot = listOf<Any>()

fun autoThrowNade(eyeAng: Vector, fSpot: List<Any>, recoveredAngle: Vector) {
    calcRes = eyeAng.distanceTo(recoveredAngle)
    writeAim(eyeAng, recoveredAngle, curSettings.float["NADE_THROWER_SMOOTHNESS"])
    //recoveredAngle.release()
    if (calcRes < 0.1) {
        when (fSpot[5]) {
            "J+T" -> jumpAndThrow()
            "S+T" -> standAndThrow()
        }
        closestAngleL = vectorLong(90F, 90F, 90F)
        closestDistance = 100F
        Thread.sleep(20)
    }
}

fun nadeThrower() = every(10, inGameCheck = true) {
    if (!curSettings.bool["ENABLE_NADE_THROWER"] || !curSettings.bool["ENABLE_ESP"] || me <= 0L || MENUTOG || meDead) return@every
    val mPos = me.absPosition()
    val eyeAngle = me.eyeAngle()

    val nadeToCheck : String = when (meCurWep.name) {
        "FLASH_GRENADE" -> "Flash"
        "SMOKE_GRENADE" -> "Smoke"
        "MOLOTOV" -> "Molly"
        "INCENDIARY_GRENADE" -> "Molly"
        "EXPLOSIVE_GRENADE" -> "Frag"
        "DECOY_GRENADE" -> "Decoy"
        else -> ""
    }
    if (nadeToCheck != "") {
        nadeHelperArrayList.forEach {
            val fSpot = it[0]
            val hLPos = it[2]
            if (fSpot[4] == nadeToCheck || nadeToCheck == "Decoy") {
                if ((mPos.x in fSpot[0].cToDouble() - 20..fSpot[0].cToDouble() + 20) && (mPos.y in fSpot[1].cToDouble() - 20..fSpot[1].cToDouble() + 20)) {
                    if (keyPressed(curSettings.int["NADE_THROWER_KEY"])) {
                        val hLVec = Vector(hLPos[0].cToFloat(), hLPos[1].cToFloat(), hLPos[2].cToFloat())
                        val recoveredAngle = realCalcAngle(me, hLVec)
                        val dist = eyeAngle.distanceTo(recoveredAngle)
                        if (dist < closestDistance) {
                            closestDistance = dist
                            closestAngleL = recoveredAngle.value
                            clfSpot = fSpot
                        }
                    }
                    else {
                        closestAngleL = vectorLong(90F, 90F, 90F)
                        closestDistance = 100F
                    }
                }
            }
        }
        val closestAngle = Vector(closestAngleL)
        if (closestAngle.x != 90F || closestAngle.y != 90F || closestAngle.z != 90F) {
            autoThrowNade(eyeAngle, clfSpot, closestAngle)
        }
    }
    mPos.release()
    eyeAngle.release()
}