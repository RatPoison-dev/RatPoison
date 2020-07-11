package rat.poison.scripts.aim

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.entity.*
import rat.poison.game.entity.dead
import rat.poison.game.entity.isScoped
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.scripts.attemptBacktrack
import rat.poison.scripts.triggerInShot
import rat.poison.settings.MENUTOG
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inBackground
import rat.poison.utils.notInGame

fun handleFireKey() = every(1) {
    if (MENUTOG || (me <= 0L && !notInGame) || (me > 0L && me.dead())) return@every //Brain blast to the past

    if (notInGame || inBackground) {
        if (clientDLL.int(dwForceAttack) == 5) {
            clientDLL[dwForceAttack] = 4
            Thread.sleep(1)
        }
        return@every
    }

    if (triggerInShot) {
        //Let that bih handle shots
        return@every
    } else if (keyPressed(1)) {
        fireWeapon()
    } else {
        if (clientDLL.int(dwForceAttack) == 5) {
            clientDLL[dwForceAttack] = 4
        }
    }
}

fun fireWeapon() {
    if (!me.weaponEntity().canFire()) return

    updateCursorEnable()
    if (cursorEnable || me.dead()) return

    val meWep = me.weapon()

    if (meWep.sniper && curSettings["ENABLE_SCOPED_ONLY"].strToBool() && !me.isScoped()) return

    val backtrackOnKey = curSettings["ENABLE_BACKTRACK_ON_KEY"].strToBool()
    val backtrackKeyPressed = keyPressed(curSettings["BACKTRACK_KEY"].toInt())

    if (curSettings["ENABLE_BACKTRACK"].strToBool() && (!backtrackOnKey || (backtrackOnKey && backtrackKeyPressed))) {
        if (attemptBacktrack()) {
            return
        }
    }

    if (curSettings["AUTOMATIC_WEAPONS"].strToBool() && !meWep.automatic && !meWep.grenade && !meWep.bomb) {
        if (clientDLL.int(dwForceAttack) == 4) { //Dont bother?
            clientDLL[dwForceAttack] = 5
        }
        Thread.sleep(1)
        if (clientDLL.int(dwForceAttack) == 5) {
            clientDLL[dwForceAttack] = 4
        }
    } else {
        clientDLL[dwForceAttack] = 5
    }
}