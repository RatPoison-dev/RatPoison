package rat.poison.ui.uiHelpers.overrideWeaponsUI

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.oWeapon
import rat.poison.ui.changed
import rat.poison.ui.uiPanelTables.weaponOverrideSelected
import kotlin.math.pow
import kotlin.math.round

class OverrideVisSliderCustom(mainText: String, varName: String, varMin: Float, varMax: Float, stepSize: Float, intVal: Boolean, dec: Int = 2, width1: Float = 225F, width2: Float = 225F, nameInLocalization: String = varName) : VisTable() {
    private val labelText = mainText
    private val variableName = varName
    private val varIdx = getOverrideVarIndex(oWeapon().toString(), variableName)
    private val isInt = intVal
    private val rnd = 10.0.pow(dec)
    private val w1 = width1
    private val w2 = width2
    private val defaultText = mainText
    private val localeName = nameInLocalization

    private val sliderLabel = VisLabel("$labelText: " + getOverrideVar(weaponOverrideSelected, varIdx))
    private val sliderBar = VisSlider(varMin, varMax, stepSize, false)

    init {
        if (curLocalization[nameInLocalization+"_TOOLTIP"] != "") {
            Tooltip.Builder(curLocalization[nameInLocalization+"_TOOLTIP"]).target(this).build()
        }
        update()

        sliderBar.changed { _, _ ->
            val sliderVal : Any = if (isInt) {
                sliderBar.value.toInt()
            } else {
                round(sliderBar.value * rnd)/rnd
            }

            //curSettings[variableName] = sliderVal.toString()
            setOverrideVar(weaponOverrideSelected, varIdx, sliderVal)
            sliderLabel.setText("$labelText: $sliderVal")
        }

        add(sliderLabel).width(w1)
        add(sliderBar).width(w2)
    }

    fun update() {
        sliderBar.value = getOverrideVar(weaponOverrideSelected, varIdx).toString().toFloat()//curSettings[variableName].toFloat()

        val sliderVal : Any = if (isInt) {
            sliderBar.value.toInt()
        } else {
            round(sliderBar.value * rnd)/rnd
        }
        val tmpText = curLocalization[localeName]
        curSettings[variableName] = sliderVal.toString()
        sliderLabel.setText(if (tmpText.isBlank()) defaultText else tmpText )
    }

    fun disable(bool: Boolean, col: Color) {
        sliderBar.isDisabled = bool
        sliderLabel.color = col
    }
}