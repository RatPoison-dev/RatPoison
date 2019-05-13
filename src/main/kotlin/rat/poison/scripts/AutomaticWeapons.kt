package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.punch
import rat.poison.game.entity.weapon
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets
import rat.poison.settings.FIRE_KEY
import rat.poison.settings.MENUTOG
import rat.poison.strToBool
import rat.poison.utils.*

var punchCheck = 0

fun automaticWeapon() = every(4) {
    if (!curSettings["AUTOMATIC_WEAPONS"]!!.strToBool() || MENUTOG) return@every

    if (!me.weapon().automatic && !me.weapon().grenade && !me.weapon().bomb && keyPressed(FIRE_KEY)) {
        if (punchCheck >= curSettings["AUTO_WEP_DELAY"].toString().toInt())
        {
            CSGO.clientDLL[ClientOffsets.dwForceAttack] = 5.toByte() //Mouse press
            Thread.sleep(16)
            CSGO.clientDLL[ClientOffsets.dwForceAttack] = 4.toByte() //Mouse release
            punchCheck = 0
        }
        else
        {
            punchCheck+=4 //Add duration wait
        }
    }
}