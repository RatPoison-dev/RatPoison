package rat.poison.ui.uiHelpers

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curSettings
import rat.poison.ui.changed
import kotlin.math.pow
import kotlin.math.round

class VisSliderCustom(mainText: String, varName: String, varMin: Float, varMax: Float, stepSize: Float, intVal: Boolean, dec: Int = 2, width: Float = 450F) : VisTable() {
    private val labelText = mainText
    private val variableName = varName
    private val isInt = intVal
    private val rnd = 10.0.pow(dec)
    private val cWidth = width

    private val sliderLabel = VisLabel("$labelText: " + curSettings[variableName])
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
            sliderLabel.setText("$labelText: $sliderVal")
        }

        add(sliderLabel).width(cWidth/2F)
        add(sliderBar).width(cWidth/2F)
    }

    fun update() {
        sliderBar.value = curSettings[variableName].toFloat()

        val sliderVal : Any = if (isInt) {
            sliderBar.value.toInt()
        } else {
            round(sliderBar.value * rnd)/rnd
        }

        sliderLabel.setText("$labelText: $sliderVal")
    }

    fun disable(bool: Boolean, col: Color) {
        sliderBar.isDisabled = bool
        sliderLabel.color = col
    }
}