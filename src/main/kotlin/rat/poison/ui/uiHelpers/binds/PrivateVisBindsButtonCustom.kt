package rat.poison.ui.uiHelpers.binds

import com.kotcrab.vis.ui.widget.VisTextButton
import rat.poison.curSettings
import rat.poison.interfaces.IKeyProcessorListener
import rat.poison.overlay.App.keyProcessor
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.keybindsUpdate
import rat.poison.utils.gdxButtons
import rat.poison.utils.gdxToVk
import rat.poison.utils.vkKeycodeToString

lateinit var needKeyPressVar : String
lateinit var needKeyPressActor : PrivateVisBindsButtonCustom

class KeyProcessorListener : IKeyProcessorListener {
    override fun onPress(keycode: Int, type: String) {
        val newKey = if (type == "button") gdxToVk[keycode]!! else gdxButtons[keycode]!!
        curSettings[needKeyPressVar] = when (newKey != 46 && newKey != 27 && newKey != curSettings.int["MENU_KEY"]) {
            true -> newKey
            false -> -1
        }
        needKeyPressActor.update()
        keybindsUpdate(needKeyPressActor)
    }
}

class PrivateVisBindsButtonCustom(varName: String): VisTextButton("_") {
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