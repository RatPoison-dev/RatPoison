package rat.poison.ui

import com.kotcrab.vis.ui.widget.*
import rat.poison.curSettings
import kotlin.math.round
import rat.poison.scripts.bombState

lateinit var bombText : VisLabel

//Needs cleanup

class UIBombTimer : VisWindow("Bomb Timer") {
    init {
        defaults().left()

        bombText = VisLabel(bombState.toString())

        //Create UI_Alpha Slider
        val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, false)
        Tooltip.Builder("The alpha of the menu").target(menuAlphaSlider).build()
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            val alp = (round(menuAlphaSlider.value * 100F) / 100F)
            changeAlpha(alp)

            true
        }

        add(bombText).growX().fillX().expandX().expandY().center().top().colspan(1).row()
        add(menuAlphaSlider).growX()

        pack()

        setSize(325F, 150F)
        setPosition(curSettings["BOMB_TIMER_X"].toFloat(), curSettings["BOMB_TIMER_Y"].toFloat())
        color.a = curSettings["BOMB_TIMER_ALPHA"].toFloat()
        isResizable = false
    }

    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }

    fun updatePosition(x: Float, y: Float) {
        setPosition(x, y)
    }
}
