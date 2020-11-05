package rat.poison.overlay

import com.badlogic.gdx.InputProcessor
import rat.poison.utils.gdxToVk

class KeyProcessor: InputProcessor {
    var needKeyPress = false
    var callBack: (_: Int, __: String) -> Unit = { _: Int, _: String -> }

    override fun keyDown(keycode: Int): Boolean {
        if (needKeyPress) {
            callBack(keycode, "button")
            needKeyPress = false
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (needKeyPress) {
            callBack(button, "mouse")
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
