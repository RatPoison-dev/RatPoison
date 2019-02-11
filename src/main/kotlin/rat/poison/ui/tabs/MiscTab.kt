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
    val bunnyHop = VisCheckBox("Enable Bunny Hop")
    val bombTimer = VisCheckBox("Enable Bomb Timer")
    val fireKeyField = VisValidatableTextField(Validators.FLOATS)
    val visualsToggleKeyField = VisValidatableTextField(Validators.FLOATS)
    val menuKeyField = VisValidatableTextField(Validators.FLOATS)
    val enableReducedFlash = VisCheckBox("Enable Reduced Flash")
    val flashMaxAlphaLabel = VisLabel("Flash Max Alpha: $FLASH_MAX_ALPHA" + when(FLASH_MAX_ALPHA.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val flashMaxAlphaSlider = VisSlider(0F, 255F, 1F, false)
    val hitSound = VisCheckBox("Hitsound")
    val hitSoundVolumeLabel = VisLabel("Hitsound Volume: $HITSOUND_VOLUME")
    val hitSoundVolumeSlider = VisSlider(0.1F, 1F, 0.1F, false)
    val radarEsp = VisCheckBox("Radar Esp")

    init {
        //Create Enable_Bunny_Hop Toggle
        Tooltip.Builder("Whether or not to enable bunny hop").target(bunnyHop).build()
        bunnyHop.isChecked = ENABLE_BUNNY_HOP
        bunnyHop.changed { _, _ ->
            ENABLE_BUNNY_HOP = bunnyHop.isChecked
            true
        }

        //Create Enable_Bomb_Timer Toggle
        Tooltip.Builder("Whether or not to enable bomb timer").target(bombTimer).build()
        bombTimer.isChecked = ENABLE_BOMB_TIMER
        bombTimer.changed { _, _ ->
            ENABLE_BOMB_TIMER = bombTimer.isChecked
            true
        }

        //Create Fire_Key Input
        val fireKey = VisTable()
        Tooltip.Builder("The key code of your in-game fire key (default m1)").target(fireKey).build()
        val fireKeyLabel = VisLabel("Fire Key: ")
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
        Tooltip.Builder("Whether or not to enable reduced flash").target(enableReducedFlash).build()
        enableReducedFlash.isChecked = ENABLE_REDUCED_FLASH
        enableReducedFlash.changed { _, _ ->
            ENABLE_REDUCED_FLASH = enableReducedFlash.isChecked
            true
        }

        //Create Flash_Max_Alpha
        val flashMaxAlpha = VisTable()
        Tooltip.Builder("The maximum alpha of flashes (0 is no effect, 255 is normal)").target(flashMaxAlpha).build()
        flashMaxAlphaSlider.value = FLASH_MAX_ALPHA
        flashMaxAlphaSlider.changed { _, _ ->
            FLASH_MAX_ALPHA = flashMaxAlphaSlider.value
            flashMaxAlphaLabel.setText("Flash Max Alpha: " + FLASH_MAX_ALPHA.toInt().toString() + when(FLASH_MAX_ALPHA.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
        }
        flashMaxAlpha.add(flashMaxAlphaLabel)
        flashMaxAlpha.add(flashMaxAlphaSlider)

        //Create Hit_Sound Toggle
        Tooltip.Builder("Whether or not to enable a hitsound on hit").target(hitSound).build()
        if (ENABLE_HITSOUND) hitSound.toggle()
        hitSound.changed { _, _ ->
                ENABLE_HITSOUND = hitSound.isChecked
            true
        }

        //Create Hit_Sound_Volume Slider
        val hitSoundVolume = VisTable()
        Tooltip.Builder("The volume of the hitsound if the hitsound is enabled").target(hitSoundVolume).build()
        hitSoundVolumeSlider.value = HITSOUND_VOLUME.toFloat()
        hitSoundVolumeSlider.changed { _, _ ->
            HITSOUND_VOLUME = Math.round(hitSoundVolumeSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
            hitSoundVolumeLabel.setText("Hitsound Volume: $HITSOUND_VOLUME")
        }
        hitSoundVolume.add(hitSoundVolumeLabel).spaceRight(6F)
        hitSoundVolume.add(hitSoundVolumeSlider)

        //Create Radar_Esp Toggle
        Tooltip.Builder("Whether or not to view the enemy team on the radar").target(radarEsp).build()
        if (RADAR_ESP) radarEsp.toggle()
        radarEsp.changed { _, _ ->
            RADAR_ESP = radarEsp.isChecked
            true
        }


        //Add all items to label for tabbed pane content
        table.add(bunnyHop).row()
        table.add(bombTimer).row()
        table.add(radarEsp).row()

        table.addSeparator()

        table.add(fireKey).row()
        table.add(visualsToggleKey).row()
        table.add(menuKey).row()

        table.addSeparator()

        table.add(enableReducedFlash).row()
        table.add(flashMaxAlpha).row()

        table.addSeparator()
        table.add(hitSound).row()
        table.add(hitSoundVolume)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Misc"
    }
}