package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.keybindsUpdate
import rat.poison.utils.generalUtil.strToBool

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
            if (curSettings["CURRENT_LOCALE"] != "") { //Only update locale if we have one
                if (dbg) {
                    if (curLocale[variableName].isBlank()) {
                        println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableName is missing!")
                    }
                }
                keyLabel.setText("${curLocale[variableName]}:")
            }

            keyField.text = curSettings[variableName]
        }

        updateTooltip()
    }

    private fun updateTooltip() {
        if (curSettings["MENU_TOOLTIPS"].strToBool()) {
            if (curLocale["${variableName}_TOOLTIP"] != "") {
                if (!hasTooltip) {
                    Tooltip.Builder(curLocale["${variableName}_TOOLTIP"]).target(this).build()
                    hasTooltip = true
                    if (dbg) {
                        println("[DEBUG] Added tooltip to $variableName")
                    }
                }
            }
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