package rat.poison.ui.uiHelpers

import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import rat.poison.curLocalization
import rat.poison.ui.changed

class VisLabelCustom(mainText: String, nameInLocalization: String = "") : VisLabel(mainText) {
    private val localeName = nameInLocalization
    init {
        if (curLocalization[nameInLocalization+"_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[nameInLocalization+"_TOOLTIP"]).target(this).build()
        }
        update()
        changed { _, _ ->
            update()
            true
        }
    }

    fun update() {
        this.setText(curLocalization[localeName])
    }
}