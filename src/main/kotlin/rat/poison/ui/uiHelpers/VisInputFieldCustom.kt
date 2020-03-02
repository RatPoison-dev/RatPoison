package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.LinkLabel
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisValidatableTextField
import rat.poison.curSettings
import rat.poison.ui.changed

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
                curSettings[variableName] = keyField.text.toInt().toString()
                value = keyField.text.toInt()
            }
            false
        }

        add(keyLabel).width(200F)
        add(keyField).spaceRight(6F).width(50F)
        if (addLink) {
            add(linkLabel)
        }
    }

    fun update() {
        keyField.text = curSettings[variableName]
        value = keyField.text.toInt()
    }

    fun disable(bool: Boolean, col: Color) {
        keyLabel.color = col
        keyField.isDisabled = bool
    }
}