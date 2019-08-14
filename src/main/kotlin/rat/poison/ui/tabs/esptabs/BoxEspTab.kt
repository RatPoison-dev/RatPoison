package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisCheckBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.changed

class BoxEspTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val boxEsp = VisCheckBox("Box Esp")
    val boxEspDetails = VisCheckBox("Box Esp Details")

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")

    init {
        //Create Box ESP Toggle
        Tooltip.Builder("Whether or not to enable box esp").target(boxEsp).build()
        if (curSettings["BOX_ESP"]!!.strToBool()) boxEsp.toggle()
        boxEsp.changed { _, _ ->
            curSettings["BOX_ESP"] = boxEsp.isChecked.boolToStr()
            true
        }

        //Create Box ESP Details Toggle
        Tooltip.Builder("Whether or not to enable details with box esp if box esp is enabled").target(boxEspDetails).build()
        if (curSettings["BOX_ESP_DETAILS"]!!.strToBool()) boxEspDetails.toggle()
        boxEspDetails.changed { _, _ ->
            curSettings["BOX_ESP_DETAILS"] = boxEspDetails.isChecked.boolToStr()

            if (curSettings["BOX_ESP_DETAILS"]!!.strToBool()) {
                curSettings["BOX_ESP"] = "true"
            }
            true
        }

        //Create Show Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (curSettings["BOX_SHOW_TEAM"]!!.strToBool()) showTeam.toggle()
        showTeam.changed { _, _ ->
            curSettings["BOX_SHOW_TEAM"] = showTeam.isChecked.boolToStr()
            true
        }

        //Create Show Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (curSettings["BOX_SHOW_ENEMIES"]!!.strToBool()) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            curSettings["BOX_SHOW_ENEMIES"] = showEnemies.isChecked.boolToStr()
            true
        }

        table.padLeft(25F)
        table.padRight(25F)

        table.add(boxEsp).left().row()
        table.add(boxEspDetails).left().row()
        table.add(showTeam).left()
        table.add(showEnemies).padLeft(200 - boxEspDetails.width).left().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Box"
    }
}