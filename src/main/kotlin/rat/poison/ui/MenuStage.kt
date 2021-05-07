package rat.poison.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import rat.poison.overlay.App

class MenuStage(viewport: Viewport, batch: Batch): Stage(viewport, batch) {

    private val visWindows = arrayOf<Actor>(App.uiMenu, App.uiKeybinds, App.uiDebug, App.uiWarning, App.uiArrows)

    fun add(actor: Actor) {
        if (!actors.contains(actor)) {
            addActor(actor)
        }
    }

    fun remove(actor: Actor) {
        if (this.actors.contains(actor)) {
            root.removeActor(actor)
        }
    }

    fun removeVisWindows() {
        remove(visWindows)
    }

    fun remove(actors: Array<Actor>) {
        actors.forEach { actor ->
            if (this.actors.contains(actor)) {
                root.removeActor(actor)
            }
        }
    }
}