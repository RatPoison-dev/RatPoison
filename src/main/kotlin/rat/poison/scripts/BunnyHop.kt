package rat.poison.scripts

import com.badlogic.gdx.math.MathUtils
import org.jire.arrowhead.keyPressed
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.game.entity.onGround
import rat.poison.game.hooks.cursorEnable
import rat.poison.game.hooks.updateCursorEnable
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceJump
import rat.poison.input
import rat.poison.jna.Interception
import rat.poison.safe_mode
import rat.poison.scripts.aim.meDead
import rat.poison.utils.every
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.inBackground
import rat.poison.utils.inGame
import rat.poison.utils.randInt

fun preCheck(): Boolean {
    updateCursorEnable()
    if (cursorEnable) return false
    return curSettings["ENABLE_BUNNY_HOP"].strToBool() && (me > 0 && !meDead && me.onGround()) && (randInt(0, 100) <= curSettings["BHOP_HITCHANCE"].toInt())
}

fun bunnyHop() = if (safe_mode && input is InterceptionInputThread && curSettings["BUNNY_HOP_TYPE"] == "SPACE") {
    var enabled = false
    Thread {
        while (true) {
            if (inGame && preCheck() && enabled) {
                input.keyClick(32, Interception.INTERCEPTION_KEYBOARD(1))
            }
            Thread.sleep(4)
        }
    }.start()
    InputThread.onKeyboard { device, key, press ->
        if (!inBackground && device == input.keyboardDevice && key == curSettings["ENABLE_BUNNY_HOP_KEY"].toInt()) {
            enabled = press
            return@onKeyboard !(preCheck() && inGame)
        }
        return@onKeyboard true
    }
} else every(4, inGameCheck = true) {
    if (input.keyPressed(curSettings["ENABLE_BUNNY_HOP_KEY"].toInt()) && curSettings["ENABLE_BUNNY_HOP"].strToBool() && input.keyPressed(curSettings["ENABLE_BUNNY_HOP_KEY"].toInt()) && preCheck()) {
        if (curSettings["BUNNY_HOP_SPACE"] == "SPACE") CSGO.clientDLL[dwForceJump] = 6 else input.mouseWheel(MathUtils.randomSign() * MathUtils.random(10, 70))
    }
}
