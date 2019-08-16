package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import org.jire.arrowhead.keyPressed
import rat.poison.*
import rat.poison.scripts.esp.updateHitsound
import rat.poison.ui.changed
import rat.poison.utils.ObservableBoolean
import java.io.File
import kotlin.math.round

class MiscTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val bunnyHop = VisCheckBox("Bunny Hop")
    val autoStrafe = VisCheckBox("Auto Strafe")
    val autoStrafeBHopOnly = VisCheckBox("BHop Only")
    val fastStop = VisCheckBox("Fast Stop")
    val bombTimer = VisCheckBox("Bomb Timer")
    val bombTimerEnableBars = VisCheckBox("Timer Bars")
    val bombTimerEnableMenu = VisCheckBox("Timer Menu")
    val spectatorList = VisCheckBox("Spectator List")
    val menuKeyField = VisValidatableTextField(Validators.FLOATS)
    val enableReducedFlash = VisCheckBox("Reduced Flash")
    val flashMaxAlphaLabel = VisLabel("Flash Max Alpha: " + curSettings["FLASH_MAX_ALPHA"]!!.toFloat() + when(curSettings["FLASH_MAX_ALPHA"]!!.toFloat().toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val flashMaxAlphaSlider = VisSlider(0F, 255F, 1F, false)
    val hitSoundCheckBox = VisCheckBox("Hitsound")
    val hitSoundBox = VisSelectBox<String>()
    val hitSoundVolumeLabel = VisLabel("Hitsound Volume: " + curSettings["HITSOUND_VOLUME"]!!.toDouble())
    val hitSoundVolumeSlider = VisSlider(0.1F, 1F, 0.1F, false)

    init {
        //Create Bunny Hop Toggle
        Tooltip.Builder("Whether or not to enable bunny hop").target(bunnyHop).build()
        bunnyHop.isChecked = curSettings["ENABLE_BUNNY_HOP"]!!.strToBool()
        bunnyHop.changed { _, _ ->
            curSettings["ENABLE_BUNNY_HOP"] = bunnyHop.isChecked.boolToStr()
            true
        }

        //Create Auto Strafe Toggle
        Tooltip.Builder("Whether or not to enable auto strafe").target(autoStrafe).build()
        autoStrafe.isChecked = curSettings["AUTO_STRAFE"]!!.strToBool()
        autoStrafe.changed { _, _ ->
            curSettings["AUTO_STRAFE"] = autoStrafe.isChecked.boolToStr()
            autoStrafeBHopOnly.isDisabled = !autoStrafe.isChecked
            true
        }

        //Create Auto Strafe BHop Only Toggle
        Tooltip.Builder("Whether or not to enable auto strafe while bhopping only").target(autoStrafeBHopOnly).build()
        autoStrafeBHopOnly.isChecked = curSettings["STRAFE_BHOP_ONLY"]!!.strToBool()
        autoStrafeBHopOnly.changed { _, _ ->
            curSettings["STRAFE_BHOP_ONLY"] = autoStrafeBHopOnly.isChecked.boolToStr()
            true
        }

        //Create Fast Stop Toggle
        Tooltip.Builder("Whether or not to enable fast stop").target(fastStop).build()
        fastStop.isChecked = curSettings["FAST_STOP"]!!.strToBool()
        fastStop.changed { _, _ ->
            curSettings["FAST_STOP"] = fastStop.isChecked.boolToStr()
            true
        }

        //Create Bomb Timer Toggle
        Tooltip.Builder("Whether or not to enable bomb timer").target(bombTimer).build()
        bombTimer.isChecked = curSettings["ENABLE_BOMB_TIMER"]!!.strToBool()
        bombTimer.changed { _, _ ->
            curSettings["ENABLE_BOMB_TIMER"] = bombTimer.isChecked.boolToStr()
            true
        }

        //Create Bomb Timer Menu Toggle
        Tooltip.Builder("Whether or not to enable bomb timer menu").target(bombTimerEnableMenu).build()
        bombTimerEnableMenu.isChecked = curSettings["BOMB_TIMER_MENU"]!!.strToBool()
        bombTimerEnableMenu.changed { _, _ ->
            curSettings["BOMB_TIMER_MENU"] = bombTimerEnableMenu.isChecked.boolToStr()
            true
        }

        //Create Bomb Timer Bars Toggle
        Tooltip.Builder("Whether or not to enable bomb timer bars").target(bombTimerEnableBars).build()
        bombTimerEnableBars.isChecked = curSettings["BOMB_TIMER_BARS"]!!.strToBool()
        bombTimerEnableBars.changed { _, _ ->
            curSettings["BOMB_TIMER_BARS"] = bombTimerEnableBars.isChecked.boolToStr()
            true
        }

        //Create Spectator List Toggle
        Tooltip.Builder("Whether or not to enable spectator list").target(spectatorList).build()
        spectatorList.isChecked = curSettings["SPECTATOR_LIST"]!!.strToBool()
        spectatorList.changed { _, _ ->
            curSettings["SPECTATOR_LIST"] = spectatorList.isChecked.boolToStr()
            true
        }

        //Create Menu Key Input Box
        val menuKey = VisTable()
        Tooltip.Builder("The key code that will toggle the menu on or off").target(menuKey).build()
        val menuKeyLabel = VisLabel("Menu Key: ")
        menuKeyField.text = curSettings["MENU_KEY"]
        menuKey.changed { _, _ ->
            if (menuKeyField.text.toIntOrNull() != null) {
                curSettings["MENU_KEY"] = menuKeyField.text.toInt().toString()
                overlayMenuKey = ObservableBoolean({keyPressed(curSettings["MENU_KEY"]!!.toInt())})
            }
        }
        menuKey.add(menuKeyLabel)
        menuKey.add(menuKeyField).spaceRight(6F).width(40F)
        menuKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Reduced Flash Toggle
        Tooltip.Builder("Whether or not to enable reduced flash").target(enableReducedFlash).build()
        enableReducedFlash.isChecked = curSettings["ENABLE_REDUCED_FLASH"]!!.strToBool()
        enableReducedFlash.changed { _, _ ->
            curSettings["ENABLE_REDUCED_FLASH"] = enableReducedFlash.isChecked.boolToStr()
            true
        }

        //Create Flash Max Alpha Slider
        val flashMaxAlpha = VisTable()
        Tooltip.Builder("The maximum alpha of flashes (0 is no effect, 255 is normal)").target(flashMaxAlpha).build()
        flashMaxAlphaSlider.value = curSettings["FLASH_MAX_ALPHA"]!!.toFloat()
        flashMaxAlphaSlider.changed { _, _ ->
            curSettings["FLASH_MAX_ALPHA"] = flashMaxAlphaSlider.value.toString()
            flashMaxAlphaLabel.setText("Flash Max Alpha: " + curSettings["FLASH_MAX_ALPHA"]!!.toFloat().toInt().toString() + when(curSettings["FLASH_MAX_ALPHA"]!!.length) {3->"  " 2->"    " else ->"      "})
        }
        flashMaxAlpha.add(flashMaxAlphaLabel).width(200F)
        flashMaxAlpha.add(flashMaxAlphaSlider).width(250F)

        //Create Hit Sound Toggle
        Tooltip.Builder("Whether or not to enable a hitsound on hit").target(hitSoundCheckBox).build()
        if (curSettings["ENABLE_HITSOUND"]!!.strToBool()) hitSoundCheckBox.toggle()
        hitSoundCheckBox.changed { _, _ ->
            curSettings["ENABLE_HITSOUND"] = hitSoundCheckBox.isChecked.boolToStr()
            true
        }

        //Create Hit Sound Selector Box
        val hitSound = VisTable()
        val hitSoundFiles = Array<String>()
        File("$SETTINGS_DIRECTORY\\hitsounds").listFiles()?.forEach {
            hitSoundFiles.add(it.name)
        }

        hitSoundBox.items = hitSoundFiles

        hitSound.add(hitSoundCheckBox)
        hitSound.add(hitSoundBox).padLeft(200F-hitSoundCheckBox.width)

        hitSoundBox.selected = curSettings["HITSOUND_FILE_NAME"]?.replace("\"", "")

        hitSoundBox.changed { _, _ ->
            updateHitsound(hitSoundBox.selected)
            curSettings["HITSOUND_FILE_NAME"] = hitSoundBox.selected
            true
        }

        //Create Hit Sound Volume Slider
        val hitSoundVolume = VisTable()
        Tooltip.Builder("The volume of the hitsound if the hitsound is enabled").target(hitSoundVolume).build()
        hitSoundVolumeSlider.value = curSettings["HITSOUND_VOLUME"]!!.toDouble().toFloat()
        hitSoundVolumeSlider.changed { _, _ ->
            curSettings["HITSOUND_VOLUME"] = (round(hitSoundVolumeSlider.value.toDouble() * 10.0)/10.0).toString() //Round to 1 decimal place
            hitSoundVolumeLabel.setText("Hitsound Volume: " + curSettings["HITSOUND_VOLUME"]!!.toDouble())
        }
        hitSoundVolume.add(hitSoundVolumeLabel).width(200F)
        hitSoundVolume.add(hitSoundVolumeSlider).width(250F)

        table.padLeft(25F)
        table.padRight(25F)

        table.add(bunnyHop).left().row()
        table.add(autoStrafe).left().row()
        table.add(autoStrafeBHopOnly).padLeft(20F).left().row()
        table.add(fastStop).left().row()

        table.addSeparator()

        table.add(bombTimer).left().row()
        table.add(bombTimerEnableMenu).padLeft(20F).left().row()
        table.add(bombTimerEnableBars).padLeft(20F).left().row()
        table.add(spectatorList).left().row()

        table.addSeparator()

        table.add(menuKey).left().row()

        table.addSeparator()

        table.add(enableReducedFlash).left().row()
        table.add(flashMaxAlpha).left().row()

        table.addSeparator()

        table.add(hitSound).left().row()
        table.add(hitSoundVolume).left()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Misc"
    }
}