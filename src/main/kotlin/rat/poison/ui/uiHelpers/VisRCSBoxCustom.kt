package rat.poison.ui.uiHelpers

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

private val white = VisUI.getSkin().getDrawable("white")

class VisRCSBoxCustom(row: Int, column: Int): VisImageButton(white) {
    var bRow = row
    var bCol = column

    var buttonToggled = false

    private var imagePosSet = false
    private var imageX = 0F
    private var imageY = 0F

    init {
        update()

        changed { _, _ ->
            buttonToggled = !buttonToggled

            val builderRes = curSettings["RCROSSHAIR_BUILDER_RESOLUTION"].toInt()

            crosshairArray[(bRow - 1) * builderRes + bCol - 1] = buttonToggled

            update()

            true
        }

        image.setScale(25F)
        image.setPosition(image.imageX-12.5F, image.imageY-12.5F)
    }

    fun update() {
        val builderRes = curSettings["RCROSSHAIR_BUILDER_RESOLUTION"].toInt()
        buttonToggled = crosshairArray[(bRow - 1) * builderRes + bCol - 1]

        if (buttonToggled) {
            image.setColor(1F, 1F, 1F, 1F)
        } else {
            image.setColor(.2F, .2F, .2F, 1F)
        }

        if (!imagePosSet) {
            imageX = image.x
            imageY = image.y
            imagePosSet = true
        }

        image.setPosition(imageX, imageY)
    }

    fun disable(bool: Boolean) {
        isDisabled = bool
    }
}