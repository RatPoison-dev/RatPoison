package rat.poison.ui.uiHelpers.overrideWeaponsUI

import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.oWeapon
import rat.poison.ui.changed
import rat.poison.ui.uiPanelTables.weaponOverrideSelected
import rat.poison.utils.generalUtil.strToBool

class OverrideVisInputFieldCustom (mainText: String, varName: String, addLink: Boolean = true, keyWidth: Float = 200F) : VisTable() {
    //a fucking class just for one thing wow incredible
    private val textLabel = mainText
    private val variableName = varName
    private var hasTooltip = false

    private var keyLabel = VisLabel("$textLabel:")
    private val keyField = VisValidatableTextField(Validators.INTEGERS)
    private val linkLabel = LinkLabel("?", "http://cherrytree.at/misc/vk.htm")
    private val varIdx = getOverrideVarIndex(oWeapon().toString(), variableName)
    var value = 0
    init {
        update()

        updateTooltip()

        changed {_, _ ->
            if (keyField.text.toIntOrNull() != null) {
                setOverrideVar(weaponOverrideSelected, varIdx, keyField.text.toInt())
            }
        }
        add(keyLabel).width(keyWidth)
        add(keyField).spaceRight(6F).width(50F)
        if (addLink) {
            add(linkLabel)
        }
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
    fun update(neglect: Actor? = null) {
        keyField.text = getOverrideVar(weaponOverrideSelected, varIdx).toString()
        if (neglect != this) {
            if (curSettings["CURRENT_LOCALE"] != "") { //Only update locale if we have one
                if (dbg) {
                    if (curLocale[variableName].isBlank()) {
                        println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableName is missing!")
                    }
                }
                keyLabel.setText("${curLocale[variableName]}:")
            }
        }

        updateTooltip()
    }
}