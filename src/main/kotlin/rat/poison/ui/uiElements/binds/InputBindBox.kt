package rat.poison.ui.uiElements.binds

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.curSettings
import rat.poison.overlay.App.keyProcessor
import rat.poison.overlay.App.menuStage
import rat.poison.ui.KeyProcessorListener
import rat.poison.ui.changed
import rat.poison.ui.needKeyPressActor
import rat.poison.ui.needKeyPressVar
import rat.poison.utils.vkKeycodeToString

class InputBindBox(varName: String): VisTextButton("_") {
    private val variableName = varName
    private val keyListener = KeyProcessorListener()

    var contextMenuOpen = false
    val contextMenu = ContextMenu(this)

    init {
        keyProcessor.listener = keyListener

        addListener(TestListener(this))

        update()
        changed { _, _ ->
            setText("_")
            needKeyPressVar = varName
            needKeyPressActor = this
            keyProcessor.needKeyPress = true

            true
        }
    }

    fun update() {
        setText(vkKeycodeToString(curSettings.int[variableName]))
    }
}

private class TestListener(var the: InputBindBox): ClickListener() {
    val actor = the

    override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
        if (button == Input.Buttons.RIGHT) {
            if (!actor.contextMenuOpen) {
                val pos = actor.realPos()

                menuStage.addActor(actor.contextMenu)
                actor.contextMenu.setPosition(pos.x + actor.width, pos.y)

                actor.contextMenuOpen = true
            } else {
                actor.contextMenu.remove()
                actor.contextMenuOpen = false
            }
        }

        return super.touchDown(event, x, y, pointer, button)
    }
}

//TODO border style...
class ContextMenu(var actor: Actor): VisWindow("Context") {
    private val parentActor = actor as InputBindBox

    init {
        addCloseButton()

        add(VisLabel("the"))
    }

    override fun close() {
        parentActor.contextMenuOpen = false

        super.close()
    }
}

fun Actor.realPos(): Vector2 { //fuck nigga gon add me up
    val v2 = Vector2()

    var parent: Actor = this

    while (parent.parent != null) {
        v2.x += parent.x
        v2.y += parent.y

        parent = parent.parent
    }

    return v2
}