package rat.poison.ui.uiElements.binds

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curSettings
import rat.poison.ui.uiTabs.VisLabelCustom
import rat.poison.utils.locale

class VisBindTableCustom(mainText: String, varName: String, keyWidth: Float = 200F, spaceRight: Float = 6F, buttonWidth: Float = 50F): VisTable(false) {
    private val textLabel = mainText
    private val variableName = varName
    private val keyLabel = VisLabelCustom("$textLabel:")
    private val button = InputBindBox(mainText, varName)
    private var hasTooltip = false

    init {
        update()

        if (keyWidth > 0F) {
            add(keyLabel).width(keyWidth)
        }
        add(button).spaceRight(spaceRight).width(buttonWidth)
    }

    fun update(neglect: Actor? = null) {
        if (neglect != this) {
            button.update()
        }

        keyLabel.setText("L_$variableName".locale(textLabel))

        updateTooltip()
    }

    private fun updateTooltip() {
        if (curSettings.bool["MENU_TOOLTIPS"]) {
            //TODO tooltippin
        } else {
            if (hasTooltip) {
                Tooltip.removeTooltip(this)
                hasTooltip = false
            }
        }
    }

    fun disable(bool: Boolean, col: Color) {
        keyLabel.color = col
        button.isDisabled = bool
    }
}