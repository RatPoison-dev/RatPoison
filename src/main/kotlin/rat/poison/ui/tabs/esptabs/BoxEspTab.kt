package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.changed

class BoxEspTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val boxEsp = VisCheckBox("Box Esp")
    val boxEspDetails = VisCheckBox("Box Esp Details")

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")

    init {
        //Create Box_Esp Toggle
        Tooltip.Builder("Whether or not to enable box esp").target(boxEsp).build()
        if (BOX_ESP) boxEsp.toggle()
        boxEsp.changed { _, _ ->
            BOX_ESP = boxEsp.isChecked
            true
        }

        //Create Box_Esp_Details Toggle
        Tooltip.Builder("Whether or not to enable details with box esp if box esp is enabled").target(boxEspDetails).build()
        if (BOX_ESP_DETAILS) boxEspDetails.toggle()
        boxEspDetails.changed { _, _ ->
            BOX_ESP_DETAILS = boxEspDetails.isChecked

            if (BOX_ESP_DETAILS) {
                BOX_ESP = true
            }
            true
        }

        //Create Show_Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (BOX_SHOW_TEAM) showTeam.toggle()
        showTeam.changed { _, _ ->
            BOX_SHOW_TEAM = showTeam.isChecked
            true
        }

        //Create Show_Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (BOX_SHOW_ENEMIES) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            BOX_SHOW_ENEMIES = showEnemies.isChecked
            true
        }


        table.add(boxEsp).colspan(2).row()
        table.add(boxEspDetails).colspan(2).row()
        table.add(showTeam)
        table.add(showEnemies)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Box"
    }
}