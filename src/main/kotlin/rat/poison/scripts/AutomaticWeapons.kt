package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.dead
import rat.poison.game.entity.isScoped
import rat.poison.game.entity.weapon
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets
import rat.poison.settings.AIM_KEY
import rat.poison.settings.MENUTOG
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool

var punchCheck = 0

fun automaticWeapon() = every(10) {
    if (!curSettings["AUTOMATIC_WEAPONS"].strToBool() || MENUTOG) return@every

    updateCursorEnable()
    if (cursorEnable || me.dead()) return@every

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