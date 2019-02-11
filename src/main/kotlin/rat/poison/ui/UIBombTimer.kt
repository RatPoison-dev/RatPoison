package rat.poison.ui

import com.kotcrab.vis.ui.widget.*
import rat.poison.scripts.bombState

lateinit var bombText : VisLabel

//Needs cleanup

class UIBombTimer : VisWindow("RatPoison UI") {
    init {
        defaults().left()

        bombText = VisLabel(bombState.toString())

        //Create UI_Alpha Slider
        val menuAlpha = VisTable()
        Tooltip.Builder("The alpha of the menu").target(menuAlpha).build()
        val menuAlphaLabel = VisLabel("Menu Alpha: " + 1F) //1F is default
        val menuAlphaSlider = VisSlider(0.1F, 1F, 0.1F, false)
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            menuAlpha.parent.color.a = (Math.round(menuAlphaSlider.value * 10F) / 10F) //Set the top level parents alpha (currently .parent.parent.parent.parent is the only way, instead of a way to find top most instantly
            menuAlphaLabel.setText("Menu Alpha: " + menuAlpha.parent.color.a.toString()) //Same parent situation
        }
        menuAlpha.add(menuAlphaLabel).spaceRight(6F)
        menuAlpha.add(menuAlphaSlider)

        add(bombText).row()
        add(menuAlpha)

        pack()

        setSize(300F, 150F)

        setPosition(0F, 0F)
        isResizable = true

        UIUpdate()
    }
}
