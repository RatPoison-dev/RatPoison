package rat.poison.ui.uiHelpers.binds

import com.kotcrab.vis.ui.widget.VisTextButton
import rat.poison.curSettings
import rat.poison.overlay.App.keyProcessor
import rat.poison.ui.changed
import rat.poison.utils.*

class PrivateVisBindsButtonCustom(varName: String): VisTextButton("_") {
    private val variableName = varName
    init {
        update()
        changed { _, _ ->
            setText("_")
            keyProcessor.removeCallback()
            keyProcessor.needKeyPress = true
            keyProcessor.callBack = { keycode, type ->
                var newKey = if (type == "button") gdxToVk[keycode]!! else gdxButtons[keycode]!!
                var text = keycodeToString(keycode, type)
                if (newKey != 46 && newKey != 27 && newKey != curSettings["MENU_KEY"].toInt()) {
                    curSettings[varName] = newKey
                    this.setText(text)
                } else {
                    newKey = -1
                    curSettings[varName] = newKey
                    this.setText(blankKey)
                }
            }

            true
        }
    }

    fun update() {
        this.setText(vkKeycodeToString(curSettings[variableName].toInt()))
    }
}