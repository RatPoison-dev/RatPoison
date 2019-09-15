package rat.poison.ui.uiHelpers

import com.kotcrab.vis.ui.util.Validators
import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.*
import rat.poison.curSettings
import rat.poison.ui.changed

class VisInputFieldCustom(mainText: String, varName: String, addLink: Boolean = true) : VisTable() {
    private val variableName = varName

    //val globalTable = VisTable()
    private val keyLabel = VisLabel("$mainText:")
    private val keyField = VisValidatableTextField(Validators.INTEGERS)
    private val linkLabel = LinkLabel("?", "http://cherrytree.at/misc/vk.htm")

    init {
        update()
        changed { _, _ ->
            if (keyField.text.toIntOrNull() != null) {
                curSettings[variableName] = keyField.text.toInt().toString()
            }
            true
        }

        add(keyLabel).width(200F)
        add(keyField).spaceRight(6F).width(40F)
        if (addLink) {
            add(linkLabel)
        }
    }

    fun update() {
        keyField.text = curSettings[variableName]
    }

    fun disable(bool: Boolean, col: Color) {
        keyLabel.color = col
        keyField.isDisabled = bool
    }
}