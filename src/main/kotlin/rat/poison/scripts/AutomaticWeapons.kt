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

fun automaticWeapons(): Boolean {
    if (curSettings["AUTOMATIC_WEAPONS"].strToBool() && !MENUTOG) {
        updateCursorEnable()
        if (!cursorEnable && !me.dead()) {
            val meWep = me.weapon()
            if (!meWep.automatic && !meWep.grenade && !meWep.bomb && keyPressed(AIM_KEY)) {
                if (punchCheck >= curSettings["AUTO_WEP_DELAY"].toInt()) {
                    punchCheck = 0
                    return true
                } else {
                    punchCheck += 1
                }
            }
        }
    }

    return false
}