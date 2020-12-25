package rat.poison.ui.uiHelpers.binds

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg

class VisBindTableCustom(mainText: String, varName: String, keyWidth: Float = 200F, spaceRight: Float = 6F, buttonWidth: Float = 50F): VisTable() {
    private val textLabel = mainText
    private val variableName = varName
    private val keyLabel = VisLabel("$textLabel:")
    private val button = PrivateVisBindsButtonCustom(varName)
    private var hasTooltip = false

    init {
        update()

        add(keyLabel).width(keyWidth)
        add(button).spaceRight(spaceRight).width(buttonWidth)
    }

    fun update(neglect: Actor? = null) {
        if (neglect != this) {
            if (curSettings["CURRENT_LOCALE"] != "") { //Only update locale if we have one
                if (curLocale[variableName].isBlank()) {
                    if (dbg) println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableName is missing!")
                    keyLabel.setText("$textLabel:")
                }
                else {
                    keyLabel.setText("${curLocale[variableName]}:")
                }
            }
            button.update()
        }

        updateTooltip()
    }
    private fun updateTooltip() {
        if (curSettings.bool["MENU_TOOLTIPS"]) {
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
        button.isDisabled = bool
    }
}