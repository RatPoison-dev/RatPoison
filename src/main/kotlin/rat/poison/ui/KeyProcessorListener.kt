package rat.poison.ui

import rat.poison.curSettings
import rat.poison.interfaces.IKeyProcessorListener
import rat.poison.ui.uiElements.binds.InputBindBox
import rat.poison.ui.uiWindows.keybindsUpdate
import rat.poison.utils.gdxButtons
import rat.poison.utils.gdxToVk

lateinit var needKeyPressVar: String
lateinit var needKeyPressActor: InputBindBox

class KeyProcessorListener: IKeyProcessorListener {
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