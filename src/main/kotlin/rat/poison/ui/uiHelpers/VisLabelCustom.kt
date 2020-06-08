package rat.poison.ui.uiHelpers

import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTextButton
import rat.poison.curLocalization
import rat.poison.ui.changed

class VisLabelCustom(mainText: String, nameInLocalization: String = "") : VisLabel(mainText) {
    private val nameInLocalization = nameInLocalization
    init {
        update()
        changed { _, _ ->
            update()
            true
        }
    }

    fun update() {
        this.setText(curLocalization[nameInLocalization])
    }
}