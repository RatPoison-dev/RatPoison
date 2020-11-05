package rat.poison.overlay

import com.badlogic.gdx.InputProcessor
import rat.poison.utils.gdxKeycodes

class KeyProcessor: InputProcessor {
    var needKeyPress = false
    var callBack: (_: Int) -> Unit = {}

    override fun keyDown(keycode: Int): Boolean {
        if (needKeyPress) {
            callBack(gdxKeycodes.advancedGet(keycode, false))
            needKeyPress = false
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (needKeyPress) {
            callBack(gdxKeycodes.advancedGet(button, true))
            needKeyPress = false
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean { return true }
    override fun keyTyped(character: Char): Boolean { return true }
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return true }
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean { return true }
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean { return true }
    override fun scrolled(amount: Int): Boolean { return true }
}
