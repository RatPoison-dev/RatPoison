package rat.poison.ui.uiHelpers.binds

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curSettings
import rat.poison.scripts.keybindNames

class VisBindTableCustom(mainText: String, varName: String, keyWidth: Float = 200F, spaceRight: Float = 6F, buttonWidth: Float = 50F): VisTable(false) {
    private val textLabel = mainText
    private val variableName = varName
    private val keyLabel = VisLabel("$textLabel:")
    private val button = PrivateVisBindsButtonCustom(varName)
    private var hasTooltip = false

    init {
        update()

        val pair = Pair(textLabel, variableName)
        if (!keybindNames.contains(pair)) {
            keybindNames.add(Pair(textLabel, variableName))
        }

        if (keyWidth > 0F) {
            add(keyLabel).width(keyWidth)
        }
        add(button).spaceRight(spaceRight).width(buttonWidth)
    }

    fun update(neglect: Actor? = null) {
        if (neglect != this) {
            button.update()
        }

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