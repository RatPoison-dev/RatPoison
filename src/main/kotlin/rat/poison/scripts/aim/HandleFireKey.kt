package rat.poison.scripts.aim

import rat.poison.appless
import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.entity.*
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.scripts.*
import rat.poison.settings.AIM_KEY
import rat.poison.settings.AUTOMATIC_WEAPONS
import rat.poison.settings.BACKTRACK
import rat.poison.settings.MENUTOG
import rat.poison.utils.*
import rat.poison.utils.common.*

private var shouldShoot = false
var didShoot = false
var meDead = true

fun handleFireKey() = every(1, continuous = true) {
    if (inGame) {
        meDead = me.dead()
    } else {
        return@every
    }

    if (!inFullscreen && ((MENUTOG && !appless) || (me > 0L && meDead) || inBackground)) {
        if (clientDLL.int(dwForceAttack) == 5) {
            clientDLL[dwForceAttack] = 4
        }
        return@every
    }

    when {
        keyPressed(AIM_KEY) -> {
            boneTrig = false
            if (!shouldShoot) {
                punchCheck = 0
                shouldShoot = true
            }

            Thread.sleep(10)
            fireWeapon()
        }
        inTrigger -> {
            if (shouldShoot) { //Finish shooting...
                if (clientDLL.int(dwForceAttack) == 5) {
                    clientDLL[dwForceAttack] = 4
                }
                shouldShoot = false
                didShoot = false
            }
            punchCheck = 0
            //Let trigger handle the rest
        }
        else -> {
            clientDLL[dwForceAttack] = 4

            shouldShoot = false
            didShoot = false
            punchCheck = 0
            boneTrig = false
        }
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

    if (AUTOMATIC_WEAPONS && !meCurWep.automatic && meCurWep.gun) {
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

    val backtrackOnKey = curSettings.bool["ENABLE_BACKTRACK_ON_KEY"]
    val backtrackKeyPressed = keybindEval("BACKTRACK_KEY")

    if (BACKTRACK && ((!backtrackOnKey || (backtrackOnKey && backtrackKeyPressed)))) {
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