package rat.poison.ui.uiHelpers

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.Tooltip.removeTooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import com.kotcrab.vis.ui.widget.VisImageButton
import com.kotcrab.vis.ui.widget.VisTextButton
import rat.poison.crosshairArray
import rat.poison.curSettings
import rat.poison.scripts.visuals.disableAllEsp
import rat.poison.ui.changed
import rat.poison.ui.tabs.*
import rat.poison.utils.generalUtil.boolToStr

class VisRCSBoxCustom(row: Int, column: Int): VisTextButton("") {
    var bRow = row
    var bCol = column

    var buttonToggled = false

    init {
        update()

        changed { _, _ ->
            buttonToggled = !buttonToggled

            val builderRes = curSettings["RCROSSHAIR_BUILDER_RESOLUTION"].toInt()

            crosshairArray[(bRow - 1) * builderRes + bCol - 1] = buttonToggled

            update()

            true
        }
    }

    fun update() {
        val builderRes = curSettings["RCROSSHAIR_BUILDER_RESOLUTION"].toInt()
        buttonToggled = crosshairArray[(bRow - 1) * builderRes + bCol - 1]

        if (buttonToggled) {
            setColor(1F, 1F, 1F, 1F)
        } else {
            setColor(.2F, .2F, .2F, 1F)
        }
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}