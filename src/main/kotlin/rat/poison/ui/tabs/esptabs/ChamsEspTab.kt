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
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val chamsEsp = VisCheckBox("Chams Esp")
    val chamsShowHealth = VisCheckBox("Chams Show Health")
    val chamsBrightnessLabel = VisLabel("Chams Brightness: " + curSettings["CHAMS_BRIGHTNESS"]!!.toInt() + when(curSettings["CHAMS_BRIGHTNESS"]!!.toInt().toString().length) {4->"" 3->"  " 2->"    " else ->"      "})
    val chamsBrightnessSlider = VisSlider(0F, 1000F, 1F, false)

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")

    init {
        //Create Chams ESP Toggle
        Tooltip.Builder("Whether or not to enable chams esp").target(chamsEsp).build()
        if (curSettings["CHAMS_ESP"]!!.strToBool()) chamsEsp.toggle()
        chamsEsp.changed { _, _ ->
            curSettings["CHAMS_ESP"] = chamsEsp.isChecked.boolToStr()

            if (!curSettings["CHAMS_ESP"]!!.strToBool()) {
                disableEsp()
            }
            true
        }

        //Create Chams Show Health Toggle
        Tooltip.Builder("Whether or not to enable chams color to be based on health if chams esp is enabled").target(chamsShowHealth).build()
        if (curSettings["CHAMS_SHOW_HEALTH"]!!.strToBool()) chamsShowHealth.toggle()
        chamsShowHealth.changed { _, _ ->
            curSettings["CHAMS_SHOW_HEALTH"] = chamsShowHealth.isChecked
            true
        }

        //Create Chams Brightness Slider
        val chamsBrightness = VisTable()
        Tooltip.Builder("Whether or not to enable chams brightness").target(chamsBrightnessLabel).build()
        Tooltip.Builder("The brightness of chams if chams brightness is enabled").target(chamsBrightnessSlider).build()
        chamsBrightnessSlider.value = curSettings["CHAMS_BRIGHTNESS"]!!.toInt().toFloat()
        chamsBrightnessSlider.changed { _, _ ->
            curSettings["CHAMS_BRIGHTNESS"] = chamsBrightnessSlider.value.toInt().toString()
            chamsBrightnessLabel.setText("Chams Brightness: " + curSettings["CHAMS_BRIGHTNESS"]!!.toInt())
        }

        chamsBrightness.add(chamsBrightnessLabel).width(200F)
        chamsBrightness.add(chamsBrightnessSlider).width(250F)

        //Create Show Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (curSettings["CHAMS_SHOW_TEAM"]!!.strToBool()) showTeam.toggle()
        showTeam.changed { _, _ ->
            curSettings["CHAMS_SHOW_TEAM"] = showTeam.isChecked
            true
        }

        //Create Show Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (curSettings["CHAMS_SHOW_ENEMIES"]!!.strToBool()) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            curSettings["CHAMS_SHOW_ENEMIES"] = showEnemies.isChecked
            true
        }

        table.padLeft(25F)
        table.padRight(25F)

        table.add(chamsEsp).left()
        table.add(chamsShowHealth).padLeft(8F).left().row()

        table.add(chamsBrightness).left().colspan(2).row()

        table.add(showTeam).left()
        table.add(showEnemies).padLeft(8F).left()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Chams"
    }
}