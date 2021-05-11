package rat.poison.ui

import rat.poison.curSettings
import rat.poison.interfaces.IKeyProcessorListener
import rat.poison.ui.uiElements.binds.InputBindBox
import rat.poison.ui.uiWindows.keybindsUpdate
import rat.poison.utils.gdxButtons
import rat.poison.utils.gdxToVk
import rat.poison.utils.keybindRegister

lateinit var needKeyPressVar: String
lateinit var needKeyPressActor: InputBindBox

class KeyProcessorListener: IKeyProcessorListener {
    override fun onPress(keycode: Int, type: String) {
        var newKey = if (type == "button") gdxToVk[keycode]!! else gdxButtons[keycode]!!
        newKey = when (newKey != 46 && newKey != 27 && newKey != curSettings.int["MENU_KEY"]) {
            true -> newKey
            false -> -1
        }
        curSettings[needKeyPressVar] = newKey

        keybindRegister(needKeyPressVar, newKey, null)

        needKeyPressActor.update()
        keybindsUpdate(needKeyPressActor)
    }
}