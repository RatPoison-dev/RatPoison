package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import org.jire.arrowhead.keyPressed
import rat.poison.App
import rat.poison.settings.*
import rat.poison.ui.changed
import rat.poison.utils.ObservableBoolean

class Misc : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val leagueModeToggle = VisTextButton("LEAGUE_MODE", "toggle") //League_Mode
    val fireKeyField = VisValidatableTextField(Validators.FLOATS) //Activate_From_Fire_Key
    val visualsToggleKeyField = VisValidatableTextField(Validators.FLOATS) //Visuals_Toggle_Key
    val menuKeyField = VisValidatableTextField(Validators.FLOATS) //Menu_Key_Field
    val rcsSmoothingLabel = VisLabel("RCS Smoothing: " + RCS_SMOOTHING.toString()/* + when(RCS_SMOOTHING.toString().length) {2->"  " else->"    "}*/) //RCS_Smoothing
    val rcsSmoothingSlider = VisSlider(0.1F, 1F, .1F, false) //RCS_Smoothing
    val rcsReturnAim = VisTextButton("RCS_RETURNAIM", "toggle") //RCS_ReturnAim
    val flashMaxAlphaLabel = VisLabel("Flash Max Alpha: " + FLASH_MAX_ALPHA.toString() + when(FLASH_MAX_ALPHA.toString().length) {3->"  " 2->"    " else ->"      "}) //Flash_Max_Alpha
    val flashMaxAlphaSlider = VisSlider(0.02F, 2F, .01F, false) //Flash_Max_Alpha

    init {
        //Create League_Mode Toggle
        //val leagueModeToggle = VisTextButton("LEAGUE_MODE", "toggle")
        if (LEAGUE_MODE) leagueModeToggle.toggle()
        leagueModeToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                LEAGUE_MODE = leagueModeToggle.isChecked//!LEAGUE_MODE
            }
        }

        //Create Fire_Key Input
        val fireKey = VisTable()
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

        //Create RCS_Smoothing
        val rcsSmoothing = VisTable()
        //val rcsSmoothingLabel = VisLabel("RCS Smoothing: " + RCS_SMOOTHING.toString() + when(RCS_SMOOTHING.toString().length) {2->"  " else->"    "})
        //val rcsSmoothingSlider = VisSlider(0.1F, 1F, .1F, false)
        rcsSmoothingSlider.value = RCS_SMOOTHING.toFloat()
        rcsSmoothingSlider.changed { _, _ ->
                RCS_SMOOTHING = Math.round(rcsSmoothingSlider.value.toDouble() * 10.0)/10.0
                rcsSmoothingLabel.setText("RCS Smoothing: $RCS_SMOOTHING")/* + when(RCS_SMOOTHING.toString().length) {2->"  " else->"    "})*/
        }
        rcsSmoothing.add(rcsSmoothingLabel)//.spaceRight(6F) //when gets rid of spaceright
        rcsSmoothing.add(rcsSmoothingSlider)

        //Create RCS_ReturnAim
        //val rcsReturnAim = VisTextButton("RCS_RETURNAIM", "toggle")
        if (RCS_RETURNAIM) rcsReturnAim.toggle()
        rcsReturnAim.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                RCS_RETURNAIM = rcsReturnAim.isChecked
            }
        }

        //Create Flash_Max_Alpha
        val flashMaxAlpha = VisTable()
        //val flashMaxAlphaLabel = VisLabel("Flash Max Alpha: " + FLASH_MAX_ALPHA.toString() + when(FLASH_MAX_ALPHA.toString().length) {3->"  " 2->"    " else ->"      "})
        //val flashMaxAlphaSlider = VisSlider(0.02F, 2F, .01F, false)
        flashMaxAlphaSlider.value = FLASH_MAX_ALPHA
        flashMaxAlphaSlider.changed { _, _ ->
            FLASH_MAX_ALPHA = "%.0f".format(flashMaxAlphaSlider.value).toFloat()
            flashMaxAlphaLabel.setText("Flash Max Alpha: " + FLASH_MAX_ALPHA.toString() + when(FLASH_MAX_ALPHA.toString().length) {3->"  " 2->"    " else ->"      "})
        }
        flashMaxAlpha.add(flashMaxAlphaLabel)//.spaceRight(6F) //when gets rid of spaceright
        flashMaxAlpha.add(flashMaxAlphaSlider)


        //Add all items to label for tabbed pane content
        table.add(leagueModeToggle).row() //Add League_Mode Toggle
        table.add(fireKey).row() //Add Fire_Key Input
        table.add(visualsToggleKey).row() //Add Visuals_Toggle_Key Input
        table.add(menuKey).row() //Add Menu_Key Input
        table.add(rcsSmoothing).row() //Add RCS_Smoothing Slider
        table.add(rcsReturnAim).row() //Add RCS_ReturnAim Toggle
        table.add(flashMaxAlpha).row() //Add Flash_Max_Alpha Slider
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Misc"
    }
}