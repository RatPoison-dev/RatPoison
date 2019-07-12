package rat.poison.ui

import com.kotcrab.vis.ui.widget.*
import rat.poison.curSettings
import kotlin.math.round

lateinit var specListText : VisLabel

//Needs cleanup

class UISpectatorList : VisWindow("Spectator List") {
    init {
        defaults().left()

        specListText = VisLabel()

        //Create UI_Alpha Slider
        val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, true)
        Tooltip.Builder("The alpha of the menu").target(menuAlphaSlider).build()
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            val alp = (round(menuAlphaSlider.value * 100F) / 100F)
            changeAlpha(alp)
            true
        }

        columnDefaults(2)

        add(specListText).growX().fillX().expandX().expandY().center().top().colspan(1)
        add(menuAlphaSlider).colspan(1).growY()

        pack()

        setSize(225F, 350F)

        setPosition(curSettings["OVERLAY_WIDTH"]!!.toFloat(), 0F)
        isResizable = false
    }

    private fun changeAlpha(alpha: Float) {
        color.a = alpha
    }
}
