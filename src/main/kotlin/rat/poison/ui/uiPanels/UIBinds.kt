package rat.poison.ui.uiPanels

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.binds.BindsCheckBox
import rat.poison.ui.uiHelpers.binds.BindsInputField
import kotlin.math.round

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
        setSize(325F, 200F)
        val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, false)
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            val alp = (round(menuAlphaSlider.value * 100F) / 100F)
            changeAlpha(alp)

            true
        }
        color.a = curSettings["BINDS_ALPHA"].toFloat()
        setPosition(curSettings["BINDS_X"].toFloat(), curSettings["BINDS_Y"].toFloat())
        add(isOnKey).left().row()
        add(variableKey).left().row()
        add(switchKey).left().row()
        add(menuAlphaSlider).growX()
    }
    fun changeAlpha(alpha: Float) {
        color.a = alpha
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