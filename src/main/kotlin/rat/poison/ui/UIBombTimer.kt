package rat.poison.ui

import com.kotcrab.vis.ui.widget.*
import rat.poison.scripts.bombState

lateinit var bombText : VisLabel

//Needs cleanup

class UIBombTimer : VisWindow("Bomb Timer") {
    init {
        defaults().left()

        bombText = VisLabel(bombState.toString())

        //Create UI_Alpha Slider
        val menuAlpha = VisTable()
        Tooltip.Builder("The alpha of the menu").target(menuAlpha).build()
        val menuAlphaLabel = VisLabel("Menu Alpha: " + 1F) //1F is default
        val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, false)
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            val alp = (Math.round(menuAlphaSlider.value * 100F) / 100F)
            menuAlpha.parent.color.a = alp //Set the top level parents alpha
            menuAlphaLabel.setText("Menu Alpha: " + alp.toString() + when(alp.toString().length) {4 -> "" 3->"  " 2->"    " else ->"      "}) //Same parent situation
        }
        menuAlpha.add(menuAlphaLabel).spaceRight(6F)
        menuAlpha.add(menuAlphaSlider)

        add(bombText).row()
        add(menuAlpha)

        pack()

        setSize(325F, 150F)

        setPosition(0F, 0F)
        isResizable = false
    }
}
