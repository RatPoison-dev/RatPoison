package rat.poison.scripts

import com.badlogic.gdx.math.MathUtils.clamp
import rat.poison.curSettings
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meDead
import rat.poison.settings.MENUTOG

var punchCheck = 0

fun automaticWeapons(): Boolean {
    if (!MENUTOG) {
        updateCursorEnable()
        if (!cursorEnable && !meDead) {
            if (!meCurWep.automatic && !meCurWep.grenade && !meCurWep.bomb) {
                val autoWepDelay = clamp(curSettings["AUTO_WEP_DELAY"].toInt(), 1, 5000)

                if (punchCheck >= autoWepDelay) {
                    punchCheck = 0
                    return true
                } else {
                    punchCheck += 15
                }
            }
        }
    }

    return false
}