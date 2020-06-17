package rat.poison.ui.uiPanels

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.*
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.binds.BindsInputField
import rat.poison.utils.varUtil.strToBool

class UIBinds : VisWindow(curLocalization["BINDS_PANEL_NAME"]) {
    private var localeName = ""
    private var varName = ""

    private val variableKey = BindsInputField("_KEY", varName)
    private val offKey = BindsInputField("_DISABLE_KEY", varName)
    private val switchKey = BindsInputField("_SWITCH_KEY", varName)
    private val selectBox = VisSelectBox<String>()
    init {
        this.selectBox.setItems(curLocalization["ALWAYS_ON"], curLocalization["TOGGLE_KEY"], curLocalization["ON_HOTKEY"], curLocalization["OFF_HOTKEY"])
        val switchKeyEnabled = curSettings[varName+"_SWITCH_ON_KEY"].strToBool()
        val onKeyEnabled = curSettings[varName+"_ON_KEY"].strToBool()
        val disableKeyEnabled = curSettings[varName+"_DISABLE_ON_KEY"].strToBool()
        if (!switchKeyEnabled && !onKeyEnabled && !disableKeyEnabled) {
            this.selectBox.selected = curLocalization["ALWAYS_ON"]
        }
        else if (switchKeyEnabled && !onKeyEnabled && !disableKeyEnabled) {
            this.selectBox.selected = curLocalization["TOGGLE_KEY"]
        }
        else if (!switchKeyEnabled && onKeyEnabled && !disableKeyEnabled) {
            this.selectBox.selected = curLocalization["ON_HOTKEY"]
        }
        else {
            this.selectBox.selected = curLocalization["OFF_HOTKEY"]
        }
        defaults().left()

        align(Align.left)
        this.selectBox.changed { _, _ ->
            removeActor(switchKey)
            removeActor(variableKey)
            removeActor(offKey)
            curSettings[varName+"_SWITCH_ON_KEY"] = "false"
            if (this.selectBox.selected == curLocalization["ALWAYS_ON"]) {
                curSettings[varName+"_SWITCH_ON_KEY"] = "false"
                curSettings[varName+"_ON_KEY"] = "false"
                curSettings[varName+"_DISABLE_ON_KEY"] = "false"
            }
            if (this.selectBox.selected == curLocalization["TOGGLE_KEY"]) {
                curSettings[varName+"_SWITCH_ON_KEY"] = "true"
                curSettings[varName+"_ON_KEY"] = "false"
                curSettings[varName+"_DISABLE_ON_KEY"] = "false"
                add(switchKey).left().row()
            }
            if (this.selectBox.selected == curLocalization["ON_HOTKEY"]) {
                curSettings[varName+"_ON_KEY"] = "true"
                curSettings[varName+"_SWITCH_ON_KEY"] = "false"
                curSettings[varName+"_DISABLE_ON_KEY"] = "false"
                add(variableKey).left().row()
            }
            if (this.selectBox.selected == curLocalization["OFF_HOTKEY"]) {
                curSettings[varName+"_SWITCH_ON_KEY"] = "false"
                curSettings[varName+"_ON_KEY"] = "false"
                curSettings[varName+"_DISABLE_ON_KEY"] = "true"
                add(offKey).left().row()
            }
        }

        padLeft(25F)
        padRight(25F)
        setSize(325F, 200F)
        setPosition(curSettings["BINDS_X"].toFloat(), curSettings["BINDS_Y"].toFloat())
        add(selectBox).left().row()
    }
    fun setBindOption(varName: String, localeName: String) {
        this.varName = varName
        val switchKeyEnabled = curSettings[varName+"_SWITCH_ON_KEY"].strToBool()
        val onKeyEnabled = curSettings[varName+"_ON_KEY"].strToBool()
        val disableKeyEnabled = curSettings[varName+"_DISABLE_ON_KEY"].strToBool()
        if (!switchKeyEnabled && !onKeyEnabled && !disableKeyEnabled) {
            this.selectBox.selected = curLocalization["ALWAYS_ON"]
        }
        else if (switchKeyEnabled && !onKeyEnabled && !disableKeyEnabled) {
            this.selectBox.selected = curLocalization["TOGGLE_KEY"]
        }
        else if (!switchKeyEnabled && onKeyEnabled && !disableKeyEnabled) {
            this.selectBox.selected = curLocalization["ON_HOTKEY"]
        }
        else {
            this.selectBox.selected = curLocalization["OFF_HOTKEY"]
        }
        this.titleLabel.setText(curLocalization[localeName] + " " + curLocalization["CONFIGURATION"])
        this.variableKey.variableName = varName+"_KEY"
        this.switchKey.variableName = varName+"_SWITCH_KEY"
        this.offKey.variableName = varName+"_DISABLE_KEY"
        this.localeName = localeName
        this.switchKey.update()
        this.variableKey.update()
        this.offKey.update()
    }
}