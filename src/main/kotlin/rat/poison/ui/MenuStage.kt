package rat.poison.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport

class MenuStage(viewport: Viewport, batch: Batch): Stage(viewport, batch) {
    fun add(actor: Actor) {
        if (!actors.contains(actor)) {
            addActor(actor)
        }
    }

    fun clear(actor: Actor) {
        if (actors.contains(actor)) {
            clear()
        }
    }
}