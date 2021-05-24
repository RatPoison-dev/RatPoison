package rat.poison.ui.uiWindows

import com.badlogic.gdx.graphics.g2d.Batch
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.curSettings
import rat.poison.overlay.App
import rat.poison.overlay.opened
import rat.poison.scripts.bombState
import rat.poison.ui.changed
import rat.poison.ui.uiElements.VisSliderCustom
import rat.poison.ui.uiUpdate
import kotlin.math.round

lateinit var bombText : VisLabel

//Needs cleanup

class UIBombTimer: VisWindow("Bomb Timer") {
    private val bombTimerAlpha = VisSlider(0.05F, 1F, 0.05F, false)

    init {
        defaults().left()
        addCloseButton()

        bombText = VisLabel(bombState.getString())

        //Create UI_Alpha Slider
        bombTimerAlpha.value = curSettings.float["BOMB_TIMER_ALPHA"]
        updateAlpha()
        bombTimerAlpha.changed { _, _ ->
            val alp = (round(bombTimerAlpha.value * 100F) / 100F)
            curSettings["BOMB_TIMER_ALPHA"] = alp
            updateAlpha()

            true
        }

        add(bombText).growX().fillX().expandX().expandY().center().top().colspan(1).row()
        add(bombTimerAlpha).growX()

        pack()

        setSize(325F, 150F)
        setPosition(curSettings.float["BOMB_TIMER_X"], curSettings.float["BOMB_TIMER_Y"])
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
        uiUpdate()
    }

    fun updateAlpha() {
        color.a = curSettings.float["BOMB_TIMER_ALPHA"]
        bombTimerAlpha.color.a = color.a
    }

    override fun drawChildren(batch: Batch?, parentAlpha: Float) {
        super.drawChildren(batch, 1/color.a)
    }

    fun updatePosition(x: Float, y: Float) {
        setPosition(x, y)
    }
}
