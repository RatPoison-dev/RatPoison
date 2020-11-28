package rat.poison.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.scenes.scene2d.utils.DragListener

fun Actor.changed(callback: (ChangeListener.ChangeEvent, Actor) -> Any?): ChangeListener {
    val listener = object : ChangeListener() {
        override fun changed(event: ChangeEvent?, actor: Actor?) {
            callback.invoke(event!!, actor!!)
        }
    }
    this.addListener(listener)
    return listener
}

fun Actor.dragged(callback: (InputEvent, Float, Float, Int) -> Any?): DragListener {
    val listener = object : DragListener() {
        override fun drag(event: InputEvent?, x: Float, y: Float, pointer: Int) {
            super.drag(event, x, y, pointer)
            callback.invoke(event!!, x, y, pointer)
        }
    }
    this.addListener(listener)
    return listener
}