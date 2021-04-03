package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curSettings
import rat.poison.ui.changed
import kotlin.math.pow
import kotlin.math.round

class VisSliderCustom(mainText: String, varName: String, varMin: Float, varMax: Float, stepSize: Float, intVal: Boolean, dec: Int = 2, labelWidth: Float = 225F, barWidth: Float = 225F) : VisTable(false) {
    private val labelText = mainText
    private val variableName = varName
    private var hasTooltip = false
    private val isInt = intVal
    private val rnd = 10.0.pow(dec)
    private val w1 = labelWidth
    private val w2 = barWidth

    private val sliderLabel = VisLabel("$labelText: $varMax")
    private val sliderBar = VisSlider(varMin, varMax, stepSize, false)

    init {
        update()

        sliderBar.changed { _, _ ->
            val sliderVal : Any = if (isInt) {
                sliderBar.value.toInt()
            } else {
                round(sliderBar.value * rnd)/rnd
            }

            curSettings[variableName] = sliderVal.toString()
            sliderLabel.setText("${labelText}: $sliderVal")
        }

        add(sliderLabel).width(w1).left()
        add(sliderBar).width(w2).left()
    }

    fun update() {
        sliderBar.value = curSettings.float[variableName]

        val sliderVal : Any = if (isInt) {
            sliderBar.value.toInt()
        } else {
            round(sliderBar.value * rnd)/rnd
        }


        sliderLabel.setText("$labelText: $sliderVal")

        updateTooltip()
    }

    private fun updateTooltip() {
        if (curSettings.bool["MENU_TOOLTIPS"]) {
            //TODO tooltippin
        } else {
            if (hasTooltip) {
                Tooltip.removeTooltip(this)
                hasTooltip = false
            }
        }
    }

    fun disable(bool: Boolean, col: Color) {
        sliderBar.isDisabled = bool
        sliderLabel.color = col
    }
}