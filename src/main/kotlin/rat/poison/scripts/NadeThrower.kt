package rat.poison.scripts

import rat.poison.checkFlags
import rat.poison.curSettings
import rat.poison.game.entity.absPosition
import rat.poison.game.entity.eyeAngle
import rat.poison.game.entity.weapon
import rat.poison.game.me
import rat.poison.game.realCalcAngle
import rat.poison.settings.MENUTOG
import rat.poison.utils.*
import rat.poison.utils.varUtil.cToDouble
import rat.poison.utils.varUtil.strToBool

private var mPos = Vector()
private var calcRes = 0.0

private var closestAngle = Angle(90.0, 90.0, 90.0)
private var closestDistance = 100.0
private var clfSpot = listOf<Any>()

fun autoThrowNade(fSpot: List<Any>, recoveredAngle: Angle) {
    writeAim(me.eyeAngle(), recoveredAngle, curSettings["NADE_THROWER_SMOOTHNESS"].cToDouble())
    var eyeAng = me.eyeAngle()
    calcRes = eyeAng.distanceTo(recoveredAngle)
    if (calcRes < 0.9) {
        when (fSpot[5]) {
            "J+T" -> jumpAndThrow()
            "S+T" -> standAndThrow()
        }
        closestAngle = Angle(90.0, 90.0, 90.0)
        closestDistance = 100.0
    }
}

fun nadeThrower() = every(3) {
    if (!curSettings["ENABLE_NADE_HELPER"].strToBool() || !curSettings["ENABLE_NADE_THROWER"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || notInGame) return@every

    if (me <= 0L || MENUTOG) return@every

    mPos = me.absPosition()

    val myWep = me.weapon()
    val nadeToCheck : String
    nadeToCheck = when (myWep.name) {
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
                    if (checkFlags("ENABLE_NADE_THROWER")) {
                        var hLVec = Vector(hLPos[0].cToDouble(), hLPos[1].cToDouble(), hLPos[2].cToDouble())
                        val recoveredAngle = realCalcAngle(me, hLVec)
                        val dist = me.eyeAngle().distanceTo(recoveredAngle)
                        if (dist < closestDistance) {
                            closestDistance = dist
                            closestAngle = recoveredAngle
                            clfSpot = fSpot
                        }
                    }
                    else {
                        closestAngle = Angle(90.0, 90.0, 90.0)
                        closestDistance = 100.0
                    }
                }
            }
        }
        if (closestAngle != Angle(90.0, 90.0, 90.0)) {
            autoThrowNade(clfSpot, closestAngle)
        }
    }
}