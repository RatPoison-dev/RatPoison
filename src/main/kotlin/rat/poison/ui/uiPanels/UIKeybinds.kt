package rat.poison.ui.uiPanels

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.App.uiKeybinds
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.binds.ConcatedInputField
import rat.poison.utils.Settings
import kotlin.math.round

val map = Settings()
fun addToCheck(localeName: String, varName: String) {
    map[varName] = localeName
}
class UIKeybinds : VisWindow(curLocalization["KEYBINDS_PANEL_NAME"]) {
    private val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, false)
    init {
        defaults().left()

        align(Align.left)

        padLeft(25F)
        padRight(25F)

        //Create UI_Alpha Slider
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            val alp = (round(menuAlphaSlider.value * 100F) / 100F)
            changeAlpha(alp)
            true
        }
        setSize(400f, 325F)
        setPosition(curSettings["KEYBINDS_X"].toFloat(), curSettings["KEYBINDS_Y"].toFloat())
        color.a = curSettings["KEYBINDS_ALPHA"].toFloat()
        isResizable = false
    }
    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }
    fun updateBindsList() {
        reset()
        map.savedValues.forEach { (varName, localeName) ->
            if (curSettings[varName+"_KEY"] != "22") {
                when (curSettings[varName + "_KEY_TYPE"]) {
                    "OnKey" -> add(ConcatedInputField(localeName, "KEYBINDS_ON_KEY", varName + "_KEY")).left().row()
                    "OffKey" -> add(ConcatedInputField(localeName, "KEYBINDS_OFF_KEY", varName + "_KEY")).left().row()
                    "SwitchKey" -> add(ConcatedInputField(localeName, "KEYBINDS_TOGGLE", varName + "_KEY")).left().row()
                }
            }
        }
        add(menuAlphaSlider).growX()
    }
    fun update() {
        this.titleLabel.setText(curLocalization["KEYBINDS_PANEL_NAME"])
    }
}

fun updateKeybinds() {
    uiKeybinds.apply {
        update()
    }
}