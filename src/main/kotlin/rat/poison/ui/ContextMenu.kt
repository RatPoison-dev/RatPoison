package rat.poison.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.overlay.App
import rat.poison.ui.uiElements.binds.InputBindBox

//TODO border style...
class ContextMenu(var actor: Actor, var table: VisTable, width: Float = 100F, height: Float = 100F, title: String): VisWindow(title) {
    private var active = false
    private val parentActor = actor

    init {
        addCloseButton()

        add(table).left().top()

        setSize(width, height)
        pack()

        isResizable = true
    }

    override fun close() {
        active = false

        super.close()
    }

    fun open(): Boolean {
        if (active) {
            remove()
        } else {
            val pos = parentActor.realPos()

            App.menuStage.addActor(this)
            setPosition(pos.x + parentActor.width, pos.y)
        }

        active = !active

        return active
    }
}