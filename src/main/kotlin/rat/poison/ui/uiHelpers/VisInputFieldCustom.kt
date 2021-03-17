package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.keybindsUpdate

class VisInputFieldCustom(mainText: String, varName: String, addLink: Boolean = true, keyWidth: Float = 200F) : VisTable() {
    private val textLabel = mainText
    private val variableName = varName
    private var hasTooltip = false

    private var keyLabel = VisLabel("$textLabel:")
    private val keyField = VisValidatableTextField(Validators.INTEGERS)
    private val linkLabel = LinkLabel("?", "http://cherrytree.at/misc/vk.htm")

    var value = 0

    init {
        update()

        updateTooltip()

        changed { _, _ ->
            if (keyField.text.toIntOrNull() != null) {
                if (keyField.text != curSettings[variableName]) { //If we need to change
                    curSettings[variableName] = keyField.text.toInt().toString()
                    keybindsUpdate(this)
                }
            }

            false
        }

        add(keyLabel).width(keyWidth)
        add(keyField).spaceRight(6F).width(50F)
        if (addLink) {
            add(linkLabel)
        }
    }

    fun update(neglect: Actor? = null) {
        if (neglect != this) {
            keyLabel.setText(textLabel)
            keyField.text = curSettings[variableName]
        }

        updateTooltip()
    }

    private fun updateTooltip() {
        if (curSettings.bool["MENU_TOOLTIPS"]) {
            //TODO tooltipin
        } else {
            if (hasTooltip) {
                Tooltip.removeTooltip(this)
                hasTooltip = false
            }
        }
    }

    fun disable(bool: Boolean, col: Color) {
        keyLabel.color = col
        keyField.isDisabled = bool
    }
}