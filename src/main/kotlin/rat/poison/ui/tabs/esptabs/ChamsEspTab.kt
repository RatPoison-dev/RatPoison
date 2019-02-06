package rat.poison.ui.tabs.esptabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.scripts.esp.disableEsp
import rat.poison.settings.*
import rat.poison.ui.changed

class ChamsEspTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val chamsEsp = VisCheckBox("Chams Esp")
    val chamsShowHealth = VisCheckBox("Chams Show Health")
    val chamsBrightnessLabel = VisLabel("Chams Brightness: $CHAMS_BRIGHTNESS" + when(CHAMS_BRIGHTNESS.toString().length) {4->"" 3->"  " 2->"    " else ->"      "})
    val chamsBrightnessSlider = VisSlider(0F, 1000F, 1F, false)

    val showTeam = VisCheckBox("Show Team")
    val showEnemies = VisCheckBox("Show Enemies")

    init {
        //Create Chams_Esp Toggle
        //val chamsEsp = VisTextButton("CHAMS_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable chams esp").target(chamsEsp).build()
        if (CHAMS_ESP) chamsEsp.toggle()
        chamsEsp.changed { _, _ ->
            CHAMS_ESP = chamsEsp.isChecked//!CHAMS_ESP

            if (!CHAMS_ESP) {
                disableEsp()
            }
            true
        }

        //Create Chams_Show_Health Toggle
        Tooltip.Builder("Whether or not to enable chams color to be based on health if chams esp is enabled").target(chamsShowHealth).build()
        if (CHAMS_SHOW_HEALTH) chamsShowHealth.toggle()
        chamsShowHealth.changed { _, _ ->
            CHAMS_SHOW_HEALTH = chamsShowHealth.isChecked
            true
        }

        //Create Chams_Brightness Slider
        val chamsBrightness = VisTable()
        Tooltip.Builder("Whether or not to enable chams brightness").target(chamsBrightnessLabel).build()
        Tooltip.Builder("The brightness of chams if chams brightness is enabled").target(chamsBrightnessSlider).build()
        chamsBrightnessSlider.value = CHAMS_BRIGHTNESS.toFloat()
        chamsBrightnessSlider.changed { _, _ ->
            CHAMS_BRIGHTNESS = chamsBrightnessSlider.value.toInt()
            chamsBrightnessLabel.setText("Chams Brightness: $CHAMS_BRIGHTNESS" + when(CHAMS_BRIGHTNESS.toString().length) {4->"" 3->"  " 2->"    " else ->"      "}) //When is used to not make the sliders jitter when you go from 10 to 9, or 100 to 99, as that character space shifts everything, one character is 2 spaces
        }

        chamsBrightness.add(chamsBrightnessLabel).spaceRight(6F)
        chamsBrightness.add(chamsBrightnessSlider)

        //Create Show_Team Toggle
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (CHAMS_SHOW_TEAM) showTeam.toggle()
        showTeam.changed { _, _ ->
            CHAMS_SHOW_TEAM = showTeam.isChecked
            true
        }

        //Create Show_Enemies Toggle
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (CHAMS_SHOW_ENEMIES) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            CHAMS_SHOW_ENEMIES = showEnemies.isChecked
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