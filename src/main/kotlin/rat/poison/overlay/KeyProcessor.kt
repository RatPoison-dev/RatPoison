package rat.poison.overlay

import com.badlogic.gdx.InputProcessor
import rat.poison.interfaces.IKeyProcessorListener


class KeyProcessor: InputProcessor {
    var needKeyPress = false
    var listener: IKeyProcessorListener? = null

    override fun keyDown(keycode: Int): Boolean {
        if (needKeyPress) {
            listener?.onPress(keycode, "button")
            needKeyPress = false
        }
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (needKeyPress) {
            listener?.onPress(button, "mouse")
            needKeyPress = false
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean { return true }
    override fun keyTyped(character: Char): Boolean { return true }
    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return true }
    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean { return true }
    override fun mouseMoved(screenX: Int, screenY: Int): Boolean { return true }
    override fun scrolled(amountX: Float, amountY: Float): Boolean { return true }
}