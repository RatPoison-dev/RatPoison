package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.sun.jna.platform.win32.WinNT
import org.jire.arrowhead.keyPressed
import rat.poison.App
import rat.poison.settings.*
import rat.poison.ui.UIUpdate
import rat.poison.ui.changed
import rat.poison.utils.ObservableBoolean

class Misc : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val leagueMode = VisTextButton("LEAGUE_MODE", "toggle") //League_Mode
    val fireKeyField = VisValidatableTextField(Validators.FLOATS) //Activate_From_Fire_Key
    val visualsToggleKeyField = VisValidatableTextField(Validators.FLOATS) //Visuals_Toggle_Key
    val menuKeyField = VisValidatableTextField(Validators.FLOATS) //Menu_Key_Field
    val flashMaxAlphaLabel = VisLabel("Flash Max Alpha: " + FLASH_MAX_ALPHA.toInt().toString() + when(FLASH_MAX_ALPHA.toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Flash_Max_Alpha
    val flashMaxAlphaSlider = VisSlider(0F, 255F, 1F, false) //Flash_Max_Alpha

    init {
        //Create League_Mode Toggle
        //val leagueMode = VisTextButton("LEAGUE_MODE", "toggle")
        Tooltip.Builder("Whether or not to enable league mode").target(leagueMode).build()
        if (LEAGUE_MODE) leagueMode.toggle()
        leagueMode.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                LEAGUE_MODE = leagueMode.isChecked//!LEAGUE_MODE
                if (LEAGUE_MODE) {
                    GLOW_ESP = false
                    BOX_ESP = false
                    SKELETON_ESP = false
                    CHAMS_ESP = false
                    CHAMS_BRIGHTNESS = 0
                    MODEL_ESP = false
                    MODEL_AND_GLOW = false
                    ENEMY_INDICATOR = false
                    ENABLE_ESP = false

                    ENABLE_BOMB_TIMER = false
                    ENABLE_REDUCED_FLASH = false
                    ENABLE_FLAT_AIM = false

                    SERVER_TICK_RATE = 128 // most leagues are 128-tick
                    PROCESS_ACCESS_FLAGS = WinNT.PROCESS_QUERY_INFORMATION or WinNT.PROCESS_VM_READ // all we need
                    GARBAGE_COLLECT_ON_MAP_START = true // get rid of traces

                    UIUpdate()
                }
            }
        }

        //Create Fire_Key Input
        val fireKey = VisTable()
        Tooltip.Builder("The key code of your in-game fire key (default m1)").target(fireKey).build()
        val fireKeyLabel = VisLabel("Fire Key: ")
        //val fireKeyField = VisValidatableTextField(Validators.FLOATS)
        fireKeyField.text = FIRE_KEY.toString()
        fireKey.changed { _, _ ->
            if (fireKeyField.text.toIntOrNull() != null) {
                FIRE_KEY = fireKeyField.text.toInt()
            }
        }
        fireKey.add(fireKeyLabel)
        fireKey.add(fireKeyField).spaceRight(6F).width(40F)
        fireKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Visuals_Toggle_Key Input
        val visualsToggleKey = VisTable()
        Tooltip.Builder("The key code that will toggle all enabled visuals on or off").target(visualsToggleKey).build()
        val visualsToggleKeyLabel = VisLabel("Visuals Toggle Key: ")
        //val visualsToggleKeyField = VisValidatableTextField(Validators.FLOATS)
        visualsToggleKeyField.text = VISUALS_TOGGLE_KEY.toString()
        visualsToggleKey.changed { _, _ ->
            if (fireKeyField.text.toIntOrNull() != null) {
                VISUALS_TOGGLE_KEY = visualsToggleKeyField.text.toInt()
            }
        }
        visualsToggleKey.add(visualsToggleKeyLabel)
        visualsToggleKey.add(visualsToggleKeyField).spaceRight(6F).width(40F)
        visualsToggleKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Menu_Key Input
        val menuKey = VisTable()
        Tooltip.Builder("The key code that will toggle the menu on or off").target(menuKey).build()
        val menuKeyLabel = VisLabel("Menu Key: ")
        //val menuKeyField = VisValidatableTextField(Validators.FLOATS)
        menuKeyField.text = MENU_KEY.toString()
        menuKey.changed { _, _ ->
            if (menuKeyField.text.toIntOrNull() != null) {
                MENU_KEY = menuKeyField.text.toInt()
                App.Menu_Key = ObservableBoolean({ keyPressed(MENU_KEY) })
            }
        }
        menuKey.add(menuKeyLabel)
        menuKey.add(menuKeyField).spaceRight(6F).width(40F)
        menuKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))


        //Create Flash_Max_Alpha
        val flashMaxAlpha = VisTable()
        Tooltip.Builder("The maximum alpha of flashes (0 is no effect, 255 is normal)").target(flashMaxAlpha).build()
        //val flashMaxAlphaLabel = VisLabel("Flash Max Alpha: " + FLASH_MAX_ALPHA.toString() + when(FLASH_MAX_ALPHA.toString().length) {3->"  " 2->"    " else ->"      "})
        //val flashMaxAlphaSlider = VisSlider(0.02F, 2F, .01F, false)
        flashMaxAlphaSlider.value = FLASH_MAX_ALPHA
        flashMaxAlphaSlider.changed { _, _ ->
            FLASH_MAX_ALPHA = flashMaxAlphaSlider.value//"%.0f".format(flashMaxAlphaSlider.value).toFloat()
            flashMaxAlphaLabel.setText("Flash Max Alpha: " + FLASH_MAX_ALPHA.toInt().toString() + when(FLASH_MAX_ALPHA.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
        }
        flashMaxAlpha.add(flashMaxAlphaLabel)//.spaceRight(6F) //when gets rid of spaceright
        flashMaxAlpha.add(flashMaxAlphaSlider)


        //Add all items to label for tabbed pane content
        table.add(leagueMode).row() //Add League_Mode Toggle
        table.add(fireKey).row() //Add Fire_Key Input
        table.add(visualsToggleKey).row() //Add Visuals_Toggle_Key Input
        table.add(menuKey).row() //Add Menu_Key Input
        table.add(flashMaxAlpha).row() //Add Flash_Max_Alpha Slider
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Misc"
    }
}