package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.boolToStr
import rat.poison.scripts.esp.disableEsp
import rat.poison.settings.*
import rat.poison.ui.changed
import rat.poison.curSettings
import rat.poison.strToBool

class ChamsEspTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val chamsEsp = VisCheckBox("Chams Esp")
    val chamsShowHealth = VisCheckBox("Chams Show Health")
    val chamsBrightnessLabel = VisLabel("Chams Brightness: " + curSettings["CHAMS_BRIGHTNESS"].toString().toInt() + when(curSettings["CHAMS_BRIGHTNESS"].toString().toInt().toString().length) {4->"" 3->"  " 2->"    " else ->"      "})
    val chamsBrightnessSlider = VisSlider(0F, 1000F, 1F, false)

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")

    init {
        //Create curSettings["CHAMS_ESP"]!!.strToBool() Toggle
        //val chamsEsp = VisTextButton("curSettings["CHAMS_ESP"]!!.strToBool()", "toggle")
        Tooltip.Builder("Whether or not to enable chams esp").target(chamsEsp).build()
        if (curSettings["CHAMS_ESP"]!!.strToBool()) chamsEsp.toggle()
        chamsEsp.changed { _, _ ->
            curSettings.set("CHAMS_ESP", chamsEsp.isChecked.boolToStr())

            if (!curSettings["CHAMS_ESP"]!!.strToBool()) {
                disableEsp()
            }
            true
        }

        //Create curSettings["CHAMS_SHOW_HEALTH"]!!.strToBool() Toggle
        Tooltip.Builder("Whether or not to enable chams color to be based on health if chams esp is enabled").target(chamsShowHealth).build()
        if (curSettings["CHAMS_SHOW_HEALTH"]!!.strToBool()) chamsShowHealth.toggle()
        chamsShowHealth.changed { _, _ ->
            curSettings["CHAMS_SHOW_HEALTH"] = chamsShowHealth.isChecked
            true
        }

        //Create curSettings["CHAMS_BRIGHTNESS"].toString().toInt() Slider
        val chamsBrightness = VisTable()
        Tooltip.Builder("Whether or not to enable chams brightness").target(chamsBrightnessLabel).build()
        Tooltip.Builder("The brightness of chams if chams brightness is enabled").target(chamsBrightnessSlider).build()
        chamsBrightnessSlider.value = curSettings["CHAMS_BRIGHTNESS"].toString().toInt().toFloat()
        chamsBrightnessSlider.changed { _, _ ->
            curSettings.set("CHAMS_BRIGHTNESS", chamsBrightnessSlider.value.toInt().toString())
            chamsBrightnessLabel.setText("Chams Brightness: " + curSettings["CHAMS_BRIGHTNESS"].toString().toInt() + when(curSettings["CHAMS_BRIGHTNESS"].toString().toInt().toString().length) {4->"" 3->"  " 2->"    " else ->"      "}) //When is used to not make the sliders jitter when you go from 10 to 9, or 100 to 99, as that character space shifts everything, one character is 2 spaces
        }

        chamsBrightness.add(chamsBrightnessLabel).spaceRight(6F)
        chamsBrightness.add(chamsBrightnessSlider)

        //Create Show_Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (curSettings["CHAMS_SHOW_TEAM"]!!.strToBool()) showTeam.toggle()
        showTeam.changed { _, _ ->
            curSettings["CHAMS_SHOW_TEAM"] = showTeam.isChecked
            true
        }

        //Create Show_Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (curSettings["CHAMS_SHOW_ENEMIES"]!!.strToBool()) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            curSettings["CHAMS_SHOW_ENEMIES"] = showEnemies.isChecked
            true
        }


        //Add all items to label for tabbed pane content
        table.add(chamsEsp).colspan(2).row()
        table.add(chamsShowHealth).colspan(2).row()
        table.add(chamsBrightness).colspan(2).row()
        table.add(showTeam)
        table.add(showEnemies)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Chams"
    }
}