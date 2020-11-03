package rat.poison.overlay

import com.badlogic.gdx.InputProcessor
import rat.poison.utils.gdxKeycodes

class Processor: InputProcessor {
    var shouldBound = false
    var callBack: (_: Int) -> Unit = {}
    override fun keyDown(keycode: Int): Boolean {
        if (shouldBound) {
            callBack(gdxKeycodes.advancedGet(keycode, false))
            shouldBound = false
        }
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        if (shouldBound) {
            callBack(gdxKeycodes.advancedGet(keycode, false))
            shouldBound = false
        }
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (shouldBound) {
            callBack(gdxKeycodes.advancedGet(button, true))
            shouldBound = false
        }
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (shouldBound) {
            callBack(gdxKeycodes.advancedGet(button, true))
            shouldBound = false
        }
        return true
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        return true
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return true
    }

    override fun scrolled(amount: Int): Boolean {
        return true
    }
}
