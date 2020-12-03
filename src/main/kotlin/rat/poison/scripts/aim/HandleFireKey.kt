package rat.poison.scripts.aim

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.entity.*
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.scripts.*
import rat.poison.settings.MENUTOG
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inBackground
import rat.poison.utils.inGame

private var shouldShoot = false
var didShoot = false
var meDead = true

fun handleFireKey() = every(1, continuous = true) {
    if (inGame) {
        meDead = me.dead()
    } else {
        return@every
    }

    if (MENUTOG || (me > 0L && meDead) || inBackground) {
        if (clientDLL.int(dwForceAttack) == 5) {
            clientDLL[dwForceAttack] = 4
            Thread.sleep(1)
        }
        return@every
    }

    if (keyPressed(1)) {
        boneTrig = false
        if (!shouldShoot) {
            punchCheck = 0
            shouldShoot = true
        }

        Thread.sleep(10)
        fireWeapon()
    } else if (inTrigger) {
        if (shouldShoot) { //Finish shooting...
            if (clientDLL.int(dwForceAttack) == 5) {
                clientDLL[dwForceAttack] = 4
            }
            shouldShoot = false
            didShoot = false
        }
        punchCheck = 0
        //Let trigger handle the rest
    } else {
        clientDLL[dwForceAttack] = 4

        shouldShoot = false
        didShoot = false
        punchCheck = 0
        boneTrig = false
    }
}

fun fireWeapon() {
    val shotsFired = me.shotsFired()
    if (shotsFired > 0) {
        didShoot = true
    }

    updateCursorEnable()
    if (cursorEnable) return

    var shouldAuto = false

    if (curSettings["AUTOMATIC_WEAPONS"].strToBool() && !meCurWep.automatic && meCurWep.gun && curSettings["ENABLE_AIM"].strToBool()) {
        shouldAuto = automaticWeapons()

        if (!didShoot) { //Skip first delay
            shouldAuto = true
        }

        if (clientDLL.int(dwForceAttack) == 5) {
            clientDLL[dwForceAttack] = 4
        }

        if (!shouldAuto) {
            return
        }
    }

    val backtrackOnKey = curSettings["ENABLE_BACKTRACK_ON_KEY"].strToBool()
    val backtrackKeyPressed = keyPressed(curSettings["BACKTRACK_KEY"].toInt())

    if (((curSettings["ENABLE_BACKTRACK"].strToBool() && !curWepOverride) || (curWepOverride && curWepSettings.tBacktrack)) && ((!backtrackOnKey || (backtrackOnKey && backtrackKeyPressed)))) {
        if (shouldAuto || (!shouldAuto && !didShoot) || meCurWep.automatic) {
            if (attemptBacktrack()) {
                if (!shouldAuto) {
                    didShoot = true
                }
                return
            }
        }
    }

    if (shouldAuto) {
        clientDLL[dwForceAttack] = 6
    } else if (!didShoot || meCurWep.automatic) {
        if (!meCurWep.automatic && me.shotsFired() > 0) { //Dont use shotsFired var, this can be different
            return
        }

        didShoot = true
        clientDLL[dwForceAttack] = 5
    }
}