package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.LinkLabel
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisValidatableTextField
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.keybindsUpdate
import rat.poison.ui.uiUpdate

class VisInputFieldCustom(mainText: String, varName: String, addLink: Boolean = true, isInt: Boolean = true) : VisTable() {
    private val textLabel = mainText
    private val variableName = varName
    private val intVal = isInt

    //val globalTable = VisTable()
    private var keyLabel = VisLabel("$textLabel:")
    private val keyField = if (isInt) { VisValidatableTextField(Validators.INTEGERS) } else { VisValidatableTextField(Validators.FLOATS) }
    private val linkLabel = LinkLabel("?", "http://cherrytree.at/misc/vk.htm")

    var value: Any = 0

    init {
        update()
        changed { _, _ ->
            if (keyField.text.toIntOrNull() != null) {
                if (keyField.text != curSettings[variableName]) {
                    if (intVal) {
                        curSettings[variableName] = keyField.text.toInt().toString()
                        value = keyField.text.toInt()
                    } else {
                        curSettings[variableName] = keyField.text.toDouble().toString()
                        value = keyField.text.toDouble()
                    }
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
            keyField.text = curSettings[variableName]
            if (intVal) {
                value = keyField.text.toInt()
            } else {
                value = keyField.text.toDouble()
            }
        }
    }

    fun disable(bool: Boolean, col: Color) {
        keyLabel.color = col
        keyField.isDisabled = bool
    }
}