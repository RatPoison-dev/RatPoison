package rat.poison.scripts

import org.jire.arrowhead.keyPressed
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
import rat.poison.utils.generalUtil.strToBool

private var mPos = Vector()
private var calcRes = 0F
private var eyeAng = Angle(0F, 0F, 0F)

private var closestAngle = Angle(90F, 90F, 90F)
private var closestDistance = 100F
private var clfSpot = listOf<Any>()

fun autoThrowNade(fSpot: List<Any>, recoveredAngle: Angle) {
    eyeAng = me.eyeAngle()
    calcRes = eyeAng.distanceTo(recoveredAngle)
    writeAim(eyeAng, recoveredAngle, curSettings["NADE_THROWER_SMOOTHNESS"].cToFloat())
    if (calcRes < 0.1) {
        when (fSpot[5]) {
            "J+T" -> jumpAndThrow()
            "S+T" -> standAndThrow()
        }
        closestAngle = Angle(90F, 90F, 90F)
        closestDistance = 100F
        Thread.sleep(20)
    }
}

fun nadeThrower() = every(10, inGameCheck = true) {
    if (!curSettings["ENABLE_NADE_THROWER"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || me <= 0L || MENUTOG || meDead) return@every
    mPos = me.absPosition()

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
                    if (keyPressed(curSettings["NADE_THROWER_KEY"].toInt())) {
                        val hLVec = Vector(hLPos[0].cToFloat(), hLPos[1].cToFloat(), hLPos[2].cToFloat())
                        val recoveredAngle = realCalcAngle(me, hLVec)
                        val dist = me.eyeAngle().distanceTo(recoveredAngle)
                        if (dist < closestDistance) {
                            closestDistance = dist
                            closestAngle = recoveredAngle
                            clfSpot = fSpot
                        }
                    }
                    else {
                        closestAngle = Angle(90F, 90F, 90F)
                        closestDistance = 100F
                    }
                }
            }
        }
        if (closestAngle != Angle(90F, 90F, 90F)) {
            autoThrowNade(clfSpot, closestAngle)
        }
    }
}