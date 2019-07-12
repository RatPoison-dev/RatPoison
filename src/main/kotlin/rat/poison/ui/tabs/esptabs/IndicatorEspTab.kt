package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.changed
import kotlin.math.round

class IndicatorEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val indicatorEsp = VisCheckBox("Entity Indicator")
    val indicatorOnScreen = VisCheckBox("Show Onscreen")
    val indicatorOval = VisCheckBox("Oval")
    val indicatorDistanceLabel = VisLabel("Indicator Distance: " + curSettings["INDICATOR_DISTANCE"]!!.toDouble())
    val indicatorDistanceSlider = VisSlider(2F, 25F, 0.1F, false)

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")
    val showBomb = VisCheckBox("Show Bomb")
    val showWeapons = VisCheckBox("Show Weapons")
    val showGrenades = VisCheckBox("Show Grenades")

    init {
        //Create Indicator Esp Toggle
        Tooltip.Builder("Whether or not to enable entity indicator esp").target(indicatorEsp).build()
        indicatorEsp.isChecked = curSettings["INDICATOR_ESP"]!!.strToBool()
        indicatorEsp.changed { _, _ ->
            curSettings["INDICATOR_ESP"] = indicatorEsp.isChecked.boolToStr()
            true
        }

        //Create Onscreen Toggle
        Tooltip.Builder("Whether or not to indicate entities onscreen").target(indicatorOnScreen).build()
        indicatorOnScreen.isChecked = curSettings["INDICATOR_SHOW_ONSCREEN"]!!.strToBool()
        indicatorOnScreen.changed { _, _ ->
            curSettings["INDICATOR_SHOW_ONSCREEN"] = indicatorOnScreen.isChecked.boolToStr()
            true
        }

        //Create Oval Toggle
        Tooltip.Builder("Whether or not to display entities offscreen in an oval shape").target(indicatorOval).build()
        indicatorOval.isChecked = curSettings["INDICATOR_OVAL"]!!.strToBool()
        indicatorOval.changed { _, _ ->
            curSettings["INDICATOR_OVAL"] = indicatorOval.isChecked.boolToStr()
            true
        }

        //Create Indicator Distance Slider
        val indicatorDistance = VisTable()
        Tooltip.Builder("The radius of the circle/oval of indicators").target(indicatorDistance).build()
        indicatorDistanceSlider.value = curSettings["INDICATOR_DISTANCE"]!!.toDouble().toFloat()
        indicatorDistanceSlider.changed { _, _ ->
            curSettings["INDICATOR_DISTANCE"] = (round(indicatorDistanceSlider.value.toDouble() * 10.0)/10.0).toString() //Round to 1 decimal place
            indicatorDistanceLabel.setText("Indicator Distance: " + curSettings["INDICATOR_DISTANCE"]!!.toDouble())
        }
        indicatorDistance.add(indicatorDistanceLabel).width(200F)
        indicatorDistance.add(indicatorDistanceSlider).width(250F)

        //Create Show Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (curSettings["INDICATOR_SHOW_TEAM"]!!.strToBool()) showTeam.toggle()
        showTeam.changed { _, _ ->
            curSettings["INDICATOR_SHOW_TEAM"] = showTeam.isChecked.boolToStr()
            true
        }

        //Create Show Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (curSettings["INDICATOR_SHOW_ENEMIES"]!!.strToBool()) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            curSettings["INDICATOR_SHOW_ENEMIES"] = showEnemies.isChecked.boolToStr()
            true
        }

        //Create Show Bomb Toggle
        Tooltip.Builder("Whether or not to show bomb with esp").target(showBomb).build()
        if (curSettings["INDICATOR_SHOW_BOMB"]!!.strToBool()) showBomb.toggle()
        showBomb.changed { _, _ ->
            curSettings["INDICATOR_SHOW_BOMB"] = showBomb.isChecked.boolToStr()
            true
        }

        //Create Show Weapons Toggle
        Tooltip.Builder("Whether or not to show weapons with esp").target(showWeapons).build()
        if (curSettings["INDICATOR_SHOW_WEAPONS"]!!.strToBool()) showWeapons.toggle()
        showWeapons.changed { _, _ ->
            curSettings["INDICATOR_SHOW_WEAPONS"] = showWeapons.isChecked.boolToStr()
            true
        }

        //Create Show Grenades Toggle
        Tooltip.Builder("Whether or not to show grenades with esp").target(showGrenades).build()
        if (curSettings["INDICATOR_SHOW_GRENADES"]!!.strToBool()) showGrenades.toggle()
        showGrenades.changed { _, _ ->
            curSettings["INDICATOR_SHOW_GRENADES"] = showGrenades.isChecked.boolToStr()
            true
        }

        table.padLeft(25F)
        table.padRight(25F)

        //Add all items to label for tabbed pane content
        table.add(indicatorEsp).left().row()
        table.add(indicatorOnScreen).left().row()
        table.add(indicatorOval).left().row()
        table.add(indicatorDistance).left().colspan(2).row()
        table.add(showTeam).left()
        table.add(showEnemies).padRight(50F).left().row()
        table.add(showBomb).left()
        table.add(showWeapons).left().padRight(50F).row()
        table.add(showGrenades).left()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Indicator"
    }
}