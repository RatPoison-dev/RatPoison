package rat.poison.ui.uiPanels

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.boolToStr
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.changed
import rat.poison.ui.tabs.*
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.binds.BindsCheckBox
import rat.poison.ui.uiHelpers.binds.BindsInputField

class UIBinds : VisWindow(curLocalization["BINDS_PANEL_NAME"]) {
    private var localeName = ""
    private val varName = ""
    private val isOnKey = BindsCheckBox(varName)
    private val variableKey = BindsInputField("_KEY", varName)
    private val switchKey = BindsInputField("_SWITCH_KEY", varName)
    init {
        defaults().left()

        align(Align.left)

        padLeft(25F)
        padRight(25F)

        add(isOnKey).left().row()
        add(variableKey).left().row()
        add(switchKey).left().row()
        setSize(325F, 325F)
        setPosition(curSettings["KEYBINDS_X"].toFloat(), curSettings["KEYBINDS_Y"].toFloat())
    }

    fun setBindOption(varName: String, localeName: String) {
        this.titleLabel.setText(curLocalization[localeName])
        this.isOnKey.variableName = varName+"_ON_KEY"
        this.variableKey.variableName = varName+"_KEY"
        this.switchKey.variableName = varName+"_SWITCH_KEY"
        this.localeName = localeName
        this.isOnKey.update()
        this.switchKey.update()
        this.variableKey.update()
    }
}