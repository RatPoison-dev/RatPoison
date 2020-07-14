package rat.poison.ui.uiHelpers.aimTab

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.ui.changed
import rat.poison.ui.tabs.categorySelected
import kotlin.math.pow
import kotlin.math.round

class ATabVisSlider(mainText: String, varExtension: String, varMin: Float, varMax: Float, stepSize: Float, intVal: Boolean, dec: Int = 2, width1: Float = 200F, width2: Float = 250F) : VisTable() {
    private val labelText = mainText
    private val variableExtension = varExtension
    private val isInt = intVal
    private val rnd = 10.0.pow(dec)

    private val w1 = width1
    private val w2 = width2

    private val sliderLabel = VisLabel("${curLocale[variableExtension]}: " + curSettings[categorySelected + variableExtension])
    private val sliderBar = VisSlider(varMin, varMax, stepSize, false)

    init {
        update()

        sliderBar.changed { _, _ ->
            val sliderVal : Any = if (isInt) {
                sliderBar.value.toInt()
            } else {
                round(sliderBar.value * rnd) /rnd
            }

            curSettings[categorySelected + variableExtension] = sliderVal.toString()
            sliderLabel.setText("${curLocale[variableExtension]}: ${curSettings[categorySelected + variableExtension]}")
        }

        add(sliderLabel).width(w1)
        add(sliderBar).width(w2)
    }

    fun update() {
        val tmp = curSettings[categorySelected + variableExtension]

        if (tmp.isNotEmpty()) {
            sliderBar.value = tmp.toFloat()

            val sliderVal: Any = if (isInt) {
                sliderBar.value.toInt()
            } else {
                round(sliderBar.value * rnd) / rnd
            }

            sliderLabel.setText("$labelText: $sliderVal")
        } else {
            println("[Error] $categorySelected$variableExtension is empty")
        }

        if (curSettings["CURRENT_LOCALE"] != "") { //Only update locale if we have one
            if (dbg && curLocale[variableExtension].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $variableExtension is missing!")
            }
            sliderLabel.setText("${curLocale[variableExtension]}: ${curSettings[categorySelected + variableExtension]}")
        }
    }

    fun disable(bool: Boolean, col: Color) {
        sliderBar.isDisabled = bool
        sliderBar.color = col
        sliderLabel.color = col
    }

    fun isDisabled(): Boolean {
        return sliderBar.isDisabled
    }
}