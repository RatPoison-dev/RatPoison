package rat.poison.ui.uiHelpers.optionsTab

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import kotlin.math.pow
import kotlin.math.round

class MenuSliderCustom(mainText: String, varName: String, varMin: Float, varMax: Float, stepSize: Float, intVal: Boolean, dec: Int = 2, width1: Float = 225F, width2: Float = 225F, nameInLocalization: String = varName) : VisTable() {
    private val defaultText = mainText
    private val variableName = varName
    private val isInt = intVal
    private val rnd = 10.0.pow(dec)
    private val w1 = width1
    private val w2 = width2

    val sliderLabel = VisLabel("$defaultText: " + curSettings[variableName])
    val sliderBar = VisSlider(varMin, varMax, stepSize, false)
    private val localeName = nameInLocalization

    init {
        update()
        if (curLocalization[nameInLocalization+"_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[nameInLocalization+"_TOOLTIP"]).target(this).build()
        }
        sliderBar.changed { _, _ ->
            val sliderVal : Any = if (isInt) {
                sliderBar.value.toInt()
            } else {
                round(sliderBar.value * rnd)/rnd
            }

            curSettings[variableName] = sliderVal.toString()
            val tmpText = curLocalization[localeName]
            if (tmpText.isBlank()) "$defaultText: $sliderVal"
            sliderLabel.setText(if (tmpText.isBlank()) "$defaultText: $sliderVal" else "${curLocalization[localeName]}: $sliderVal")
            //sliderLabel.setText("${curLocalization[nameInLocalization]}: $sliderVal")
        }

        add(sliderLabel).width(w1)
        add(sliderBar).width(w2)
    }

    fun update() {
        sliderBar.value = curSettings[variableName].toFloat()

        val sliderVal : Any = if (isInt) {
            sliderBar.value.toInt()
        } else {
            round(sliderBar.value * rnd)/rnd
        }

        val tmpText = curLocalization[localeName]
        if (tmpText.isBlank()) "$defaultText: $sliderVal"
        sliderLabel.setText(if (tmpText.isBlank()) "$defaultText: $sliderVal" else "${curLocalization[localeName]}: $sliderVal")
    }

    fun disable(bool: Boolean, col: Color) {
        sliderBar.isDisabled = bool
        sliderLabel.color = col
    }
}