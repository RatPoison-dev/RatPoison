package rat.poison.ui.uiHelpers

import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisTextButton
import rat.poison.curLocalization
import rat.poison.ui.changed

class VisTextButtonCustom(mainText: String, nameInLocalization: String = "") : VisTextButton(mainText) {
    private val localeName = nameInLocalization
    init {
        update()
        if (curLocalization[nameInLocalization+"_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[nameInLocalization+"_TOOLTIP"]).target(this).build()
        }

        changed { _, _ ->
            update()
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