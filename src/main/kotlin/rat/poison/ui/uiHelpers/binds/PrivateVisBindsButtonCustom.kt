package rat.poison.ui.uiHelpers.binds

import com.kotcrab.vis.ui.widget.VisTextButton
import rat.poison.curSettings
import rat.poison.interfaces.IOKeyProcessorListener
import rat.poison.overlay.App.keyProcessor
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.keybindsUpdate
import rat.poison.utils.gdxButtons
import rat.poison.utils.gdxToVk
import rat.poison.utils.vkKeycodeToString
import kotlin.collections.set

lateinit var needKeyPressVar : String
lateinit var needKeyPressActor : PrivateVisBindsButtonCustom

class KeyProcessorListener : IOKeyProcessorListener {

    override fun onPress(keycode: Int, type: String) {
        var newKey = if (type == "button") gdxToVk[keycode]!! else gdxButtons[keycode]!!
        curSettings[needKeyPressVar] = when (newKey != 46 && newKey != 27 && newKey != curSettings["MENU_KEY"].toInt()) {
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
        this.setText(vkKeycodeToString(curSettings[variableName].toInt()))
    }
}