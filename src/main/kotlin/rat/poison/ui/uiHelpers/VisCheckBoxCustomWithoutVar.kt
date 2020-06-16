package rat.poison.ui.uiHelpers

import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.curLocalization
import rat.poison.ui.changed

class VisCheckBoxCustomWithoutVar(mainText: String, nameInLocalization: String = "") : VisCheckBox(mainText) {
    private val localeName = nameInLocalization
    init {
        if (curLocalization[nameInLocalization+"_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[nameInLocalization+"_TOOLTIP"]).target(this).build()
        }
        update()
        changed { _, _ ->
            true
        }
    }

    fun update() {
        this.setText(curLocalization[localeName])
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}