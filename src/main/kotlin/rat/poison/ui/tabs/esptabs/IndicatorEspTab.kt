package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.changed

class IndicatorEspTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val indicatorEsp = VisCheckBox("Entity Indicator")
    val indicatorOnScreen = VisCheckBox("Indicator Show Onscreen")
    val indicatorOval = VisCheckBox("Indicator As Oval")
    val indicatorDistanceLabel = VisLabel("Indicator Distance: " + curSettings["INDICATOR_DISTANCE"].toString().toDouble())
    val indicatorDistanceSlider = VisSlider(2F, 25F, 0.1F, false)

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")
    val showDormant = VisCheckBox("Show Dormant")
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
        indicatorDistanceSlider.value = curSettings["INDICATOR_DISTANCE"].toString().toDouble().toFloat()
        indicatorDistanceSlider.changed { _, _ ->
            curSettings["INDICATOR_DISTANCE"] = (Math.round(indicatorDistanceSlider.value.toDouble() * 10.0)/10.0).toString() //Round to 1 decimal place
            indicatorDistanceLabel.setText("Indicator Distance: " + curSettings["INDICATOR_DISTANCE"].toString().toDouble())
        }
        indicatorDistance.add(indicatorDistanceLabel).spaceRight(6F)
        indicatorDistance.add(indicatorDistanceSlider)

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

        //Create Show Dormant Toggle
        Tooltip.Builder("Whether or not to show dormant entities with esp").target(showDormant).build()
        if (curSettings["INDICATOR_SHOW_DORMANT"]!!.strToBool()) showDormant.toggle()
        showDormant.changed { _, _ ->
            curSettings["INDICATOR_SHOW_DORMANT"] = showDormant.isChecked.boolToStr()
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


        //Add all items to label for tabbed pane content
        table.add(indicatorEsp).colspan(2).row()
        table.add(indicatorOnScreen).colspan(2).row()
        table.add(indicatorOval).colspan(2).row()
        table.add(indicatorDistance).colspan(2).row()
        table.add(showTeam)
        table.add(showEnemies).row()
        table.add(showDormant)
        table.add(showBomb).row()
        table.add(showWeapons)
        table.add(showGrenades).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Indicator"
    }
}