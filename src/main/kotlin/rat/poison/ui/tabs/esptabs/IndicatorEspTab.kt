package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.changed

class IndicatorEspTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val indicatorEsp = VisCheckBox("Entity Indicator")
    val indicatorOnScreen = VisCheckBox("Indicator Show Onscreen")
    val indicatorOval = VisCheckBox("Indicator As Oval")
    val indicatorDistanceLabel = VisLabel("Indicator Distance: $INDICATOR_DISTANCE")
    val indicatorDistanceSlider = VisSlider(2.5F, 25F, 0.1F, false)

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")
    val showDormant = VisCheckBox("Show Dormant")
    val showBomb = VisCheckBox("Show Bomb")
    val showWeapons = VisCheckBox("Show Weapons")
    val showGrenades = VisCheckBox("Show Grenades")

    init {
        //Create Indicator Esp Toggle
        Tooltip.Builder("Whether or not to enable entity indicator esp").target(indicatorEsp).build()
        indicatorEsp.isChecked = INDICATOR_ESP
        indicatorEsp.changed { _, _ ->
            INDICATOR_ESP = indicatorEsp.isChecked
            true
        }

        //Create Onscreen Toggle
        Tooltip.Builder("Whether or not to indicate entities onscreen").target(indicatorOnScreen).build()
        indicatorOnScreen.isChecked = INDICATOR_SHOW_ONSCREEN
        indicatorOnScreen.changed { _, _ ->
            INDICATOR_SHOW_ONSCREEN = indicatorOnScreen.isChecked
            true
        }

        //Create Oval Toggle
        Tooltip.Builder("Whether or not to display entities offscreen in an oval shape").target(indicatorOval).build()
        indicatorOval.isChecked = INDICATOR_OVAL
        indicatorOval.changed { _, _ ->
            INDICATOR_OVAL = indicatorOval.isChecked
            true
        }

        //Create Indicator Distance Slider
        val indicatorDistance = VisTable()
        Tooltip.Builder("The radius of the circle/oval of indicators").target(indicatorDistance).build()
        indicatorDistanceSlider.value = INDICATOR_DISTANCE.toFloat()
        indicatorDistanceSlider.changed { _, _ ->
            INDICATOR_DISTANCE = Math.round(indicatorDistanceSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
            indicatorDistanceLabel.setText("Indicator Distance: $INDICATOR_DISTANCE")
        }
        indicatorDistance.add(indicatorDistanceLabel).spaceRight(6F)
        indicatorDistance.add(indicatorDistanceSlider)

        //Create Show_Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (INDICATOR_SHOW_TEAM) showTeam.toggle()
        showTeam.changed { _, _ ->
            INDICATOR_SHOW_TEAM = showTeam.isChecked
            true
        }

        //Create Show_Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (INDICATOR_SHOW_ENEMIES) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            INDICATOR_SHOW_ENEMIES = showEnemies.isChecked
            true
        }

        //Create Show_Dormant Toggle
        Tooltip.Builder("Whether or not to show dormant entities with esp").target(showDormant).build()
        if (INDICATOR_SHOW_DORMANT) showDormant.toggle()
        showDormant.changed { _, _ ->
            INDICATOR_SHOW_DORMANT = showDormant.isChecked
            true
        }

        //Create Show_Bomb Toggle
        Tooltip.Builder("Whether or not to show bomb with esp").target(showBomb).build()
        if (INDICATOR_SHOW_BOMB) showBomb.toggle()
        showBomb.changed { _, _ ->
            INDICATOR_SHOW_BOMB = showBomb.isChecked
            true
        }

        //Create Show_Bomb Toggle
        Tooltip.Builder("Whether or not to show weapons with esp").target(showWeapons).build()
        if (INDICATOR_SHOW_WEAPONS) showWeapons.toggle()
        showWeapons.changed { _, _ ->
            INDICATOR_SHOW_WEAPONS = showWeapons.isChecked
            true
        }

        //Create Show_Bomb Toggle
        Tooltip.Builder("Whether or not to show grenades with esp").target(showGrenades).build()
        if (INDICATOR_SHOW_GRENADES) showGrenades.toggle()
        showGrenades.changed { _, _ ->
            INDICATOR_SHOW_GRENADES = showGrenades.isChecked
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