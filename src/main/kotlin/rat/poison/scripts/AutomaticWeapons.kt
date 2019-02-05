package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.game.CSGO
import rat.poison.game.entity.punch
import rat.poison.game.entity.weapon
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets
import rat.poison.settings.AUTOMATIC_WEAPONS
import rat.poison.settings.FIRE_KEY
import rat.poison.settings.MAX_PUNCH_CHECK
import rat.poison.utils.*

var punchCheck = 0

fun automaticWeapon() = every(4) {
    if (!AUTOMATIC_WEAPONS) return@every

    if (!me.weapon().automatic && !me.weapon().bomb && keyPressed(FIRE_KEY)) {
        val p = me.punch()

        //val pp = me.punchtest()
        val check = (p.x == 0.0) && (p.y == 0.0)
        if (check && punchCheck < MAX_PUNCH_CHECK) { //If we have determined we have 0 punch
            punchCheck++
//        } else if (punchCheck >= punchCheckMax) {
//            punchCheck = punchCheckMax
        } else {
            punchCheck = 0
        }

        if (punchCheck >= MAX_PUNCH_CHECK) {
            CSGO.clientDLL[ClientOffsets.dwForceAttack] = 5.toByte() //Mouse press
            Thread.sleep(16)
            CSGO.clientDLL[ClientOffsets.dwForceAttack] = 4.toByte() //Mouse release
            punchCheck = 0
        }
    }
}