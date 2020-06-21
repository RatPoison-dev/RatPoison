package rat.poison.ui.uiHelpers.binds

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed

class BindsInputField(localeName: String, varName: String, addLink: Boolean = true, isInt: Boolean = true) : VisTable() {
    private val defaultText = curLocalization[localeName]
    var variableName = varName
    private val intVal = isInt

    //val globalTable = VisTable()
    private var keyLabel = VisLabel("$defaultText:")
    private val keyField = if (isInt) { VisValidatableTextField(Validators.INTEGERS) } else { VisValidatableTextField(Validators.FLOATS) }
    private val linkLabel = LinkLabel("?", "http://cherrytree.at/misc/vk.htm")

    var value: Any = 0

    init {
        update()

        changed { _, _ ->
            if (keyField.text.toIntOrNull() != null) {
                if (keyField.text != curSettings[variableName+"_KEY"]) {
                    if (intVal) {
                        curSettings[variableName+"_KEY"] = keyField.text.toInt().toString()
                        value = keyField.text.toInt()
                    } else {
                        curSettings[variableName+"_KEY"] = keyField.text.toDouble().toString()
                        value = keyField.text.toDouble()
                    }
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

            //val tmpText = curLocalization[localeName]
            when (curSettings[variableName+"_KEY_TYPE"]) {
                "OnKey" -> this.keyLabel.setText(curLocalization["_KEY"])
                "SwitchKey" -> this.keyLabel.setText(curLocalization["_SWITCH_KEY"])
                "OffKey" -> this.keyLabel.setText(curLocalization["_DISABLE_KEY"])
            }
            if (!curSettings[variableName+"_KEY"].isBlank()) {
                keyField.text = curSettings[variableName+"_KEY"]
                value = if (intVal) {
                    keyField.text.toInt()
                } else {
                    keyField.text.toDouble()
                }
            }
            else {
                keyField.text = "22"
            }
        }
    }

    fun disable(bool: Boolean, col: Color) {
        keyLabel.color = col
        keyField.isDisabled = bool
    }
}