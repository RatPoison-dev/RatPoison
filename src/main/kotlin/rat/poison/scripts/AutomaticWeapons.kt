package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.isScoped
import rat.poison.game.entity.weapon
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets
import rat.poison.settings.AIM_KEY
import rat.poison.settings.MENUTOG
import rat.poison.strToBool
import rat.poison.utils.*

var punchCheck = 0

fun automaticWeapon() = every(10) {
    if (!curSettings["AUTOMATIC_WEAPONS"].strToBool() || MENUTOG) return@every

    if (me.weapon().sniper && curSettings["ENABLE_SCOPED_ONLY"].strToBool() && !me.isScoped()) return@every

    if (!me.weapon().automatic && !me.weapon().grenade && !me.weapon().bomb && keyPressed(AIM_KEY)) {
        if (punchCheck >= curSettings["AUTO_WEP_DELAY"].toInt())
        {
            CSGO.clientDLL[ClientOffsets.dwForceAttack] = 6.toByte()
            punchCheck = 0
        }
        else
        {
            punchCheck+=10
        }
    }
}