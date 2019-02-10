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

class MiscTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableBunnyHop = VisCheckBox("Enable Bunny Hop") //Bunny_Hop
    val enableBombTimer = VisCheckBox("Enable Bomb Timer") //Bomb_Timer
    val fireKeyField = VisValidatableTextField(Validators.FLOATS) //Activate_From_Fire_Key
    val visualsToggleKeyField = VisValidatableTextField(Validators.FLOATS) //Visuals_Toggle_Key
    val menuKeyField = VisValidatableTextField(Validators.FLOATS) //Menu_Key_Field
    val enableReducedFlash = VisCheckBox("Enable Reduced Flash") //Reduced_Flash
    val flashMaxAlphaLabel = VisLabel("Flash Max Alpha: $FLASH_MAX_ALPHA" + when(FLASH_MAX_ALPHA.toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Flash_Max_Alpha
    val flashMaxAlphaSlider = VisSlider(0F, 255F, 1F, false) //Flash_Max_Alpha
    val hitSound = VisCheckBox("Hitsound") //Hit_Sound
    val hitSoundVolumeLabel = VisLabel("Hitsound Volume: $HITSOUND_VOLUME") //Hit_Sound_Volume
    val hitSoundVolumeSlider = VisSlider(0.1F, 1F, 0.1F, false) //Hit_Sound_Volume
    val enableRadar = VisCheckBox("RadarHack") //RadarHack

    init {
        //Create Enable_Bunny_Hop Toggle
        //val enableBunnyHop = VisTextButton("ENABLE_BUNNY_HOP", "toggle")
        Tooltip.Builder("Whether or not to enable bunny hop").target(enableBunnyHop).build()
        enableBunnyHop.isChecked = ENABLE_BUNNY_HOP
        enableBunnyHop.changed { _, _ ->
            ENABLE_BUNNY_HOP = enableBunnyHop.isChecked
            true
        }

        //Create Enable_Bomb_Timer Toggle
        //val enableBombTimer = VisTextButton("ENABLE_BOMB_TIMER", "toggle")
        Tooltip.Builder("Whether or not to enable bomb timer").target(enableBombTimer).build()
        enableBombTimer.isChecked = ENABLE_BOMB_TIMER
        enableBombTimer.changed { _, _ ->
            ENABLE_BOMB_TIMER = enableBombTimer.isChecked
            true
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

        //Create Enable_Reduced_Flash Toggle
        //val enableReducedFlash = VisTextButton("ENABLE_REDUCED_FLASH", "toggle")
        Tooltip.Builder("Whether or not to enable reduced flash").target(enableReducedFlash).build()
        enableReducedFlash.isChecked = ENABLE_REDUCED_FLASH
        enableReducedFlash.changed { _, _ ->
            ENABLE_REDUCED_FLASH = enableReducedFlash.isChecked
            true
        }

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

        //Create Hit_Sound Toggle
        //val hitSound = VisTextButton("Hit Sound", "toggle")
        Tooltip.Builder("Whether or not to enable a hitsound on hit").target(hitSound).build()
        if (ENABLE_HITSOUND) hitSound.toggle()
        hitSound.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_HITSOUND = hitSound.isChecked//!INDICATOR_ESP
            }
        }

        //Create Hit_Sound_Volume Slider
        val hitSoundVolume = VisTable()
        Tooltip.Builder("The volume of the hitsound if the hitsound is enabled").target(hitSoundVolume).build()
        //val hitSoundVolumeLabel = VisLabel("Hitsound Volume: " + HITSOUND_VOLUME.toString())
        //val hitSoundVolumeSlider = VisSlider(0.1F, 1F, 0.1F, false)
        hitSoundVolumeSlider.value = HITSOUND_VOLUME.toFloat()
        hitSoundVolumeSlider.changed { _, _ ->
            HITSOUND_VOLUME = Math.round(hitSoundVolumeSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
            hitSoundVolumeLabel.setText("Hitsound Volume: $HITSOUND_VOLUME")
        }
        hitSoundVolume.add(hitSoundVolumeLabel).spaceRight(6F)
        hitSoundVolume.add(hitSoundVolumeSlider)
        
        //Create RadarHack Toggle
        //val enableRadar = VisTextButton("RadarHack", "toggle")
        Tooltip.Builder("Whether or not to enable a RadarHack").target(enableRadar).build()
        if (ENABLE_RADAR) enableRadar.toggle()
        enableRadar.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_RADAR = enableRadar.isChecked//!ENABLE_RADAR
            }
        }


        //Add all items to label for tabbed pane content
        table.add(enableBunnyHop).row() //Add Enable_Bunny_Hop Toggle
        table.add(enableBombTimer).row() //Add Enable_Bomb_Timer Toggle

        table.addSeparator()

        table.add(fireKey).row() //Add Fire_Key Input
        table.add(visualsToggleKey).row() //Add Visuals_Toggle_Key Input
        table.add(menuKey).row() //Add Menu_Key Input

        table.addSeparator()

        table.add(enableReducedFlash).row() //Add Enable_Reduced_Flash Toggle
        table.add(flashMaxAlpha).row() //Add Flash_Max_Alpha Slider

        table.addSeparator()
        table.add(hitSound).row()
        table.add(hitSoundVolume).row()
        
        table.addSeparator()
        table.add(enableRadar).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Misc"
    }
}
