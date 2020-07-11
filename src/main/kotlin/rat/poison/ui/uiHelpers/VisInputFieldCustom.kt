package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.LinkLabel
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisValidatableTextField
import rat.poison.CURRENT_LOCALE
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.keybindsUpdate

class VisInputFieldCustom(mainText: String, varName: String, addLink: Boolean = true) : VisTable() {
    private val textLabel = mainText
    private val variableName = varName

    //val globalTable = VisTable()
    private var keyLabel = VisLabel("$textLabel:")
    private val keyField = VisValidatableTextField(Validators.INTEGERS)
    private val linkLabel = LinkLabel("?", "http://cherrytree.at/misc/vk.htm")

    var value = 0

    init {
        update()
        changed { _, _ ->
            if (keyField.text.toIntOrNull() != null) {
                if (keyField.text != curSettings[variableName]) {
                    curSettings[variableName] = keyField.text.toInt().toString()
                    value = keyField.text.toInt()
                    keybindsUpdate(this)
                }
            }

            false
        }

        add(keyLabel).width(200F)
        add(keyField).spaceRight(6F).width(50F)
        if (addLink) {
            add(linkLabel)
        }
    }

    fun update(neglect: Actor? = null) {
        if (neglect != this) {
            if (CURRENT_LOCALE != "") { //Only update locale if we have one
                if (dbg) {
                    if (curLocale[variableName].isBlank()) {
                        println("[DEBUG] $CURRENT_LOCALE $variableName is missing!")
                    }
                }
                keyLabel.setText("${curLocale[variableName]}:")
            }

            keyField.text = curSettings[variableName]
            value = keyField.text.toInt()
        }
    }

    fun disable(bool: Boolean, col: Color) {
        keyLabel.color = col
        keyField.isDisabled = bool
    }
}