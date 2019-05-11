package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import org.jire.arrowhead.keyPressed
import rat.poison.*
import rat.poison.ui.changed
import rat.poison.utils.ObservableBoolean

class MiscTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val bunnyHop = VisCheckBox("Enable Bunny Hop")
    val bombTimer = VisCheckBox("Enable Bomb Timer")
    val fireKeyField = VisValidatableTextField(Validators.FLOATS)
    val menuKeyField = VisValidatableTextField(Validators.FLOATS)
    val enableReducedFlash = VisCheckBox("Enable Reduced Flash")
    val flashMaxAlphaLabel = VisLabel("Flash Max Alpha: " + curSettings["FLASH_MAX_ALPHA"].toString().toFloat() + when(curSettings["FLASH_MAX_ALPHA"].toString().toFloat().toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val flashMaxAlphaSlider = VisSlider(0F, 255F, 1F, false)
    val hitSound = VisCheckBox("Hitsound")
    val hitSoundVolumeLabel = VisLabel("Hitsound Volume: " + curSettings["HITSOUND_VOLUME"].toString().toDouble())
    val hitSoundVolumeSlider = VisSlider(0.1F, 1F, 0.1F, false)

    init {
        //Create curSettings["ENABLE_BUNNY_HOP"]!!.strToBool() Toggle
        Tooltip.Builder("Whether or not to enable bunny hop").target(bunnyHop).build()
        bunnyHop.isChecked = curSettings["ENABLE_BUNNY_HOP"]!!.strToBool()
        bunnyHop.changed { _, _ ->
            curSettings["ENABLE_BUNNY_HOP"] = bunnyHop.isChecked.boolToStr()
            true
        }

        //Create curSettings["ENABLE_BOMB_TIMER"]!!.strToBool() Toggle
        Tooltip.Builder("Whether or not to enable bomb timer").target(bombTimer).build()
        bombTimer.isChecked = curSettings["ENABLE_BOMB_TIMER"]!!.strToBool()
        bombTimer.changed { _, _ ->
            curSettings["ENABLE_BOMB_TIMER"] = bombTimer.isChecked.boolToStr()
            true
        }

        //Create Fire_Key Input
        val fireKey = VisTable()
        Tooltip.Builder("The key code of your in-game fire key (default m1)").target(fireKey).build()
        val fireKeyLabel = VisLabel("Fire Key: ")
        fireKeyField.text = curSettings["FIRE_KEY"].toString()
        fireKey.changed { _, _ ->
            if (fireKeyField.text.toIntOrNull() != null) {
                curSettings["FIRE_KEY"] = fireKeyField.text.toInt().toString()
            }
        }
        fireKey.add(fireKeyLabel)
        fireKey.add(fireKeyField).spaceRight(6F).width(40F)
        fireKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create curSettings["MENU_KEY"] Input
        val menuKey = VisTable()
        Tooltip.Builder("The key code that will toggle the menu on or off").target(menuKey).build()
        val menuKeyLabel = VisLabel("Menu Key: ")
        menuKeyField.text = curSettings["MENU_KEY"].toString()
        menuKey.changed { _, _ ->
            if (menuKeyField.text.toIntOrNull() != null) {
                curSettings["MENU_KEY"] = menuKeyField.text.toInt().toString()
                overlayMenuKey = ObservableBoolean({keyPressed(curSettings["MENU_KEY"].toString().toInt())})
            }
        }
        menuKey.add(menuKeyLabel)
        menuKey.add(menuKeyField).spaceRight(6F).width(40F)
        menuKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create curSettings["ENABLE_REDUCED_FLASH"]!!.strToBool() Toggle
        Tooltip.Builder("Whether or not to enable reduced flash").target(enableReducedFlash).build()
        enableReducedFlash.isChecked = curSettings["ENABLE_REDUCED_FLASH"]!!.strToBool()
        enableReducedFlash.changed { _, _ ->
            curSettings["ENABLE_REDUCED_FLASH"] = enableReducedFlash.isChecked.boolToStr()
            true
        }

        //Create curSettings["FLASH_MAX_ALPHA"].toString().toFloat()
        val flashMaxAlpha = VisTable()
        Tooltip.Builder("The maximum alpha of flashes (0 is no effect, 255 is normal)").target(flashMaxAlpha).build()
        flashMaxAlphaSlider.value = curSettings["FLASH_MAX_ALPHA"].toString().toFloat()
        flashMaxAlphaSlider.changed { _, _ ->
            curSettings["FLASH_MAX_ALPHA"] = flashMaxAlphaSlider.value.toString()
            flashMaxAlphaLabel.setText("Flash Max Alpha: " + curSettings["FLASH_MAX_ALPHA"].toString().toFloat().toInt().toString() + when(curSettings["FLASH_MAX_ALPHA"].toString().length) {3->"  " 2->"    " else ->"      "})
        }
        flashMaxAlpha.add(flashMaxAlphaLabel)
        flashMaxAlpha.add(flashMaxAlphaSlider)

        //Create Hit_Sound Toggle
        Tooltip.Builder("Whether or not to enable a hitsound on hit").target(hitSound).build()
        if (curSettings["ENABLE_HITSOUND"]!!.strToBool()) hitSound.toggle()
        hitSound.changed { _, _ ->
            curSettings["ENABLE_HITSOUND"] = hitSound.isChecked.boolToStr()
            true
        }

        //Create Hit_Sound_Volume Slider
        val hitSoundVolume = VisTable()
        Tooltip.Builder("The volume of the hitsound if the hitsound is enabled").target(hitSoundVolume).build()
        hitSoundVolumeSlider.value = curSettings["HITSOUND_VOLUME"].toString().toDouble().toFloat()
        hitSoundVolumeSlider.changed { _, _ ->
            curSettings["HITSOUND_VOLUME"] = (Math.round(hitSoundVolumeSlider.value.toDouble() * 10.0)/10.0).toString() //Round to 1 decimal place
            hitSoundVolumeLabel.setText("Hitsound Volume: " + curSettings["HITSOUND_VOLUME"].toString().toDouble())
        }
        hitSoundVolume.add(hitSoundVolumeLabel).spaceRight(6F)
        hitSoundVolume.add(hitSoundVolumeSlider)


        //Add all items to label for tabbed pane content
        table.add(bunnyHop).row()
        table.add(bombTimer).row()

        table.addSeparator()

        table.add(fireKey).row()
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