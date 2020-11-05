package rat.poison.ui.uiHelpers.binds

import com.kotcrab.vis.ui.widget.VisTextButton
import rat.poison.curSettings
import rat.poison.overlay.App.inputProcessor
import rat.poison.ui.changed
import rat.poison.utils.keycodesMap

class BindsButtonCustom(varName: String): VisTextButton("_") {
    private val variableName = varName

    init {
        update()
        changed { _, _ ->
            setText("_")
            inputProcessor.needKeyPress = true
            inputProcessor.callBack = {
                var newKey = it
                if (newKey != 46 && newKey != 27 && newKey != curSettings["MENU_KEY"].toInt()) {
                    curSettings[varName] = newKey
                    this@BindsButtonCustom.setText(keycodesMap[newKey])
                } else {
                    newKey = -1
                    curSettings[varName] = newKey
                    this@BindsButtonCustom.setText(keycodesMap[newKey])
                }
            }

            true
        }
    }

    fun update() {
        this.setText(keycodesMap[curSettings[variableName].toInt()])
    }
}