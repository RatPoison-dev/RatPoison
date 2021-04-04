package rat.poison.ui.uiElements.binds

import com.kotcrab.vis.ui.widget.VisTextButton
import rat.poison.curSettings
import rat.poison.interfaces.IKeyProcessorListener
import rat.poison.overlay.App.keyProcessor
import rat.poison.ui.KeyProcessorListener
import rat.poison.ui.changed
import rat.poison.ui.needKeyPressActor
import rat.poison.ui.needKeyPressVar
import rat.poison.ui.uiWindows.keybindsUpdate
import rat.poison.utils.gdxButtons
import rat.poison.utils.gdxToVk
import rat.poison.utils.vkKeycodeToString

class InputBindBox(varName: String): VisTextButton("_") {
    private val variableName = varName
    private val keyListener = KeyProcessorListener()

    init {
        keyProcessor.listener = keyListener
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