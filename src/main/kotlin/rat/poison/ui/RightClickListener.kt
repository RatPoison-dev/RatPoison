package rat.poison.ui

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class RightClickListener(var callback: () -> Unit): ClickListener() {
    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
        if (button == Input.Buttons.RIGHT) {
            callback.invoke()
        }

        return super.touchDown(event, x, y, pointer, button)
    }
}