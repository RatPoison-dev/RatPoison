package rat.poison.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener

fun Actor.changed(callback: (ChangeListener.ChangeEvent, Actor) -> Any?): ChangeListener {
    val listener = object : ChangeListener() {
        override fun changed(event: ChangeEvent?, actor: Actor?) {
            callback.invoke(event!!, actor!!)
        }
    }
    this.addListener(listener)
    return listener
}

fun Actor.rightClicked(callback: (InputEvent?, x: Float, y: Float, pointer: Int, button: Int) -> Any?): InputListener {
    val listener = object : InputListener() {
        override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
            if (button == 1) {
                callback.invoke(event, x, y, pointer, button)
            }
            return super.touchDown(event, x, y, pointer, button)
        }
    }
    this.addListener(listener)
    return listener
}