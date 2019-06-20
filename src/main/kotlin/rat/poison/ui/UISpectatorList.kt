package rat.poison.ui

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.*
import rat.poison.curSettings

//lateinit var specListText : VisTable

lateinit var specListText : VisLabel

//Needs cleanup

class UISpectatorList : VisWindow("Spectator List") {
    init {
        defaults().top()

        specListText = VisLabel()

        //Create UI_Alpha Slider
        val menuAlpha = VisTable()
        Tooltip.Builder("The alpha of the menu").target(menuAlpha).build()
        val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, true)
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            val alp = (Math.round(menuAlphaSlider.value * 100F) / 100F)
            menuAlpha.parent.color.a = alp //Set the top level parents alpha
            true
        }
        menuAlpha.add(menuAlphaSlider).growY()

        columnDefaults(2)

        add(specListText).growX().fillX().expandX().expandY().center().top().colspan(1)
        add(menuAlpha).colspan(1).growY()

        pack()

        align(Align.top)


        setSize(225F, 350F) //325

        setPosition(curSettings["OVERLAY_WIDTH"]!!.toFloat(), 0F)
        isResizable = false

    }
}
