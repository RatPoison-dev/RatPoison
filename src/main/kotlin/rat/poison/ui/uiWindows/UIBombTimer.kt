package rat.poison.ui.uiWindows

import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.curSettings
import rat.poison.overlay.App
import rat.poison.overlay.opened
import rat.poison.scripts.bombState
import rat.poison.ui.changed
import kotlin.math.round

lateinit var bombText : VisLabel

//Needs cleanup

class UIBombTimer : VisWindow("Bomb-Timer") {
    init {
        defaults().left()
        addCloseButton()

        bombText = VisLabel(bombState.getString())

        //Create UI_Alpha Slider
        val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, false)
        menuAlphaSlider.value = curSettings.float["BOMB_TIMER_ALPHA"]
        menuAlphaSlider.changed { _, _ ->
            val alp = (round(menuAlphaSlider.value * 100F) / 100F)
            changeAlpha(alp)

            true
        }

        add(bombText).growX().fillX().expandX().expandY().center().top().colspan(1).row()
        add(menuAlphaSlider).growX()

        pack()

        setSize(325F, 150F)
        setPosition(curSettings.float["BOMB_TIMER_X"], curSettings.float["BOMB_TIMER_Y"])
        updateAlpha()
        isResizable = false
    }

    override fun positionChanged() {
        if (opened) {
            curSettings["BOMB_TIMER_X"] = App.uiBombWindow.x
            curSettings["BOMB_TIMER_Y"] = App.uiBombWindow.y
            super.positionChanged()
        }
    }

    override fun close() {
        curSettings["BOMB_TIMER_MENU"] = "false"
    }

    fun updateAlpha() {
        changeAlpha(curSettings.float["BOMB_TIMER_ALPHA"])
    }

    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }

    fun updatePosition(x: Float, y: Float) {
        setPosition(x, y)
    }
}
