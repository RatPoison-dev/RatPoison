package rat.poison.ui.uiElements.override

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.DEFAULT_OWEAPON_STR
import rat.poison.ui.changed
import rat.poison.ui.getOverrideVar
import rat.poison.ui.getOverrideVarIndex
import rat.poison.ui.setOverrideVar
import rat.poison.ui.uiTabs.aimTables.weaponOverrideSelected
import rat.poison.utils.locale
import kotlin.math.pow
import kotlin.math.round

class OverrideVisSliderCustom(mainText: String, varName: String, varMin: Float, varMax: Float, stepSize: Float, intVal: Boolean, dec: Int = 2, labelWidth: Float = 225F, barWidth: Float = 225F) : VisTable(false) {
    private val labelText = mainText
    private val variableName = varName
    private val varIdx = getOverrideVarIndex(DEFAULT_OWEAPON_STR, variableName)
    private val isInt = intVal
    private val rnd = 10.0.pow(dec)
    private val w1 = labelWidth
    private val w2 = barWidth

    private val sliderLabel = VisLabel("$labelText: " + getOverrideVar(weaponOverrideSelected, varIdx))
    private val sliderBar = VisSlider(varMin, varMax, stepSize, false)

    init {
        update()

        sliderBar.changed { _, _ ->
            val sliderVal : Any = if (isInt) {
                sliderBar.value.toInt()
            } else {
                round(sliderBar.value * rnd)/rnd
            }

            setOverrideVar(weaponOverrideSelected, varIdx, sliderVal)

            sliderLabel.setText("$labelText: $sliderVal")
        }

        add(sliderLabel).width(w1)
        add(sliderBar).width(w2)
    }

    fun update() {
        sliderBar.value = getOverrideVar(weaponOverrideSelected, varIdx).toString().toFloat()

        val sliderVal : Any = if (isInt) {
            sliderBar.value.toInt()
        } else {
            round(sliderBar.value * rnd)/rnd
        }

        val sliderText = "L_$variableName".locale(labelText)

        sliderLabel.setText("$sliderText: $sliderVal")
    }

    fun disable(bool: Boolean, col: Color) {
        sliderBar.isDisabled = bool
        sliderLabel.color = col
    }
}