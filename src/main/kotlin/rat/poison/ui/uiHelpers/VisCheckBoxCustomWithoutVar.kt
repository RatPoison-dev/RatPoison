package rat.poison.ui.uiHelpers

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.boolToStr
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.changed
import rat.poison.ui.tabs.*

class VisCheckBoxCustomWithoutVar(mainText: String, nameInLocalization: String = "") : VisCheckBox(mainText) {
    private val nameInLocalization = nameInLocalization
    init {
        update()
        changed { _, _ ->
            true
        }
    }

    fun update() {
        this.setText(curLocalization[nameInLocalization])
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}