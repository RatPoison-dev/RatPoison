package rat.poison.scripts

import rat.poison.curSettings
import rat.poison.game.entity.dead
import rat.poison.game.entity.weapon
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.settings.MENUTOG

var punchCheck = 0

fun automaticWeapons(): Boolean {
    if (!MENUTOG) {
        updateCursorEnable()
        if (!cursorEnable && !me.dead()) {
            val meWep = me.weapon()
            if (!meWep.automatic && !meWep.grenade && !meWep.bomb) {
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