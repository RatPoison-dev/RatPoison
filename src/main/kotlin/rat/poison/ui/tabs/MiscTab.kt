package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import org.jire.arrowhead.keyPressed
import rat.poison.*
import rat.poison.scripts.esp.updateHitsound
import rat.poison.scripts.toggleStand
import rat.poison.scripts.toggleStandKey
import rat.poison.ui.changed
import rat.poison.ui.miscTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.utils.ObservableBoolean
import java.io.File

class MiscTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val bunnyHop = VisCheckBoxCustom("Bunny Hop", "ENABLE_BUNNY_HOP")
    val autoStrafe = VisCheckBoxCustom("Auto Strafe", "AUTO_STRAFE")
    val autoStrafeBHopOnly = VisCheckBoxCustom("BHop Only", "STRAFE_BHOP_ONLY")
    val fastStop = VisCheckBoxCustom("Fast Stop", "FAST_STOP")

    val aimStrafer = VisCheckBoxCustom("Auto Aim Strafe", "AIM_STRAFER")
    val aimStraferSelectBox = VisSelectBox<String>()
    val aimStraferShift = VisCheckBoxCustom("Shift Walk", "AIM_STRAFER_SHIFT")
    val aimStraferStrictness = VisSliderCustom("Strictness", "AIM_STRAFER_STRICTNESS", 0F, .5F, .01F, false, 3)

    val bombTimer = VisCheckBoxCustom("Bomb Timer", "ENABLE_BOMB_TIMER")
    val bombTimerEnableBars = VisCheckBoxCustom("Timer Bars", "BOMB_TIMER_BARS")
    val bombTimerEnableMenu = VisCheckBoxCustom("Timer Menu", "BOMB_TIMER_MENU")
    val spectatorList = VisCheckBoxCustom("Spectator List", "SPECTATOR_LIST")

    val headWalk = VisCheckBox("Head Walk")
    val headWalkToggleText = VisLabel("Toggled: false")
    private val headWalkKeyLabel = VisLabel("Head Walk Key: ")
    val headWalkKeyField = VisValidatableTextField(Validators.FLOATS)

    val menuKeyField = VisValidatableTextField(Validators.FLOATS)
    val enableReducedFlash = VisCheckBoxCustom("Reduced Flash", "ENABLE_REDUCED_FLASH")
    val flashMaxAlpha = VisSliderCustom("Flash Max Alpha", "FLASH_MAX_ALPHA", 1F, 255F, 1F, true)

    val hitSoundCheckBox = VisCheckBoxCustom("Hitsound", "ENABLE_HITSOUND")
    val hitSoundBox = VisSelectBox<String>()
    val hitSoundVolume = VisSliderCustom("Hitsound Volume", "HITSOUND_VOLUME", .1F, 1F, .1F, false)

    init {
        //Disable head walk for now
        if (!EXPERIMENTAL) {
            headWalk.isDisabled = true
            headWalkToggleText.color = Color(105F, 105F, 105F, .2F)
            headWalkKeyLabel.color = Color(105F, 105F, 105F, .2F)
            headWalkKeyField.isDisabled = true
        }

        aimStraferSelectBox.setItems("Same", "Opposite")
        aimStraferSelectBox.changed { _, _ ->
            if (aimStraferSelectBox.selected == "Same") {
                curSettings["AIM_STRAFER_TYPE"] = 1
            } else {
                curSettings["AIM_STRAFER_TYPE"] = 0
            }
        }

        val aimStraferTable = VisTable()
        aimStraferTable.add(aimStrafer).left()
        aimStraferTable.add(aimStraferSelectBox).padLeft(225F - aimStrafer.width).left()

        //Create Head Walk Toggle
        val headWalkTable = VisTable()
        Tooltip.Builder("Head walk master switch").target(headWalk).build()
        headWalk.isChecked = curSettings["HEAD_WALK"].strToBool()
        headWalk.changed { _, _ ->
            curSettings["HEAD_WALK"] = headWalk.isChecked.boolToStr()
            true
        }
        headWalkTable.add(headWalk).left()
        headWalkTable.add(headWalkToggleText).padLeft(225F - headWalk.width).left()

        //Create Head Walk Key Input Box
        val headWalkKey = VisTable()
        Tooltip.Builder("The key code that will toggle headwalk on or off").target(headWalkKey).build()
        headWalkKeyField.text = curSettings["HEAD_WALK_KEY"]
        headWalkKey.changed { _, _ ->
            if (headWalkKeyField.text.toIntOrNull() != null) {
                curSettings["HEAD_WALK_KEY"] = headWalkKeyField.text.toInt().toString()
                toggleStandKey = ObservableBoolean({keyPressed(curSettings["HEAD_WALK_KEY"].toInt())})
            }
        }
        headWalkKey.add(headWalkKeyLabel)
        headWalkKey.add(headWalkKeyField).spaceRight(6F).width(40F)
        headWalkKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Menu Key Input Box
        val menuKey = VisTable()
        Tooltip.Builder("The key code that will toggle the menu on or off").target(menuKey).build()
        val menuKeyLabel = VisLabel("Menu Key: ")
        menuKeyField.text = curSettings["MENU_KEY"]
        menuKey.changed { _, _ ->
            if (menuKeyField.text.toIntOrNull() != null) {
                curSettings["MENU_KEY"] = menuKeyField.text.toInt().toString()
                overlayMenuKey = ObservableBoolean({keyPressed(curSettings["MENU_KEY"].toInt())})
            }
        }
        menuKey.add(menuKeyLabel)
        menuKey.add(menuKeyField).spaceRight(6F).width(40F)
        menuKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Hit Sound Toggle
        Tooltip.Builder("Whether or not to enable a hitsound on hit").target(hitSoundCheckBox).build()
        if (curSettings["ENABLE_HITSOUND"].strToBool()) hitSoundCheckBox.toggle()
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
        hitSound.add(hitSoundBox).padLeft(225F-hitSoundCheckBox.width)

        hitSoundBox.selected = curSettings["HITSOUND_FILE_NAME"].replace("\"", "")

        hitSoundBox.changed { _, _ ->
            updateHitsound(hitSoundBox.selected)
            curSettings["HITSOUND_FILE_NAME"] = hitSoundBox.selected
            true
        }

        table.padLeft(25F)
        table.padRight(25F)

        table.add(bunnyHop).left().row()
        table.add(autoStrafe).left().row()
        table.add(autoStrafeBHopOnly).padLeft(20F).left().row()
        table.add(fastStop).left().row()
        table.addSeparator()
        table.add(aimStraferTable).left().row()
        table.add(aimStraferShift).left().row()
        table.add(aimStraferStrictness).left().row()
        table.addSeparator()
        table.add(bombTimer).left().row()
        table.add(bombTimerEnableMenu).padLeft(20F).left().row()
        table.add(bombTimerEnableBars).padLeft(20F).left().row()
        table.add(spectatorList).left().row()
        table.addSeparator()
        table.add(headWalkTable).left().row()
        table.add(headWalkKey).left().row()
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

fun miscTabUpdate() {
    miscTab.apply {
        bunnyHop.update()
        autoStrafe.update()
        autoStrafeBHopOnly.update()
        fastStop.update()
        aimStrafer.update()
        aimStraferShift.update()
        aimStraferSelectBox.selected = when(curSettings["AIM_STRAFER_TYPE"].toInt()) {
            1 -> "Same"
            else -> "Opposite"
        }
        aimStraferStrictness.update()
        bombTimer.update()
        bombTimerEnableMenu.update()
        bombTimerEnableBars.update()
        spectatorList.update()
        headWalk.isChecked = curSettings["HEAD_WALK"].strToBool()
        headWalkToggleText.setText("Toggled: $toggleStand")
        headWalkKeyField.text = curSettings["HEAD_WALK_KEY"]
        toggleStandKey = ObservableBoolean({keyPressed(curSettings["HEAD_WALK_KEY"].toInt())})
        menuKeyField.text = curSettings["MENU_KEY"]
        enableReducedFlash.update()
        flashMaxAlpha.update()
        hitSoundCheckBox.isChecked = curSettings["ENABLE_HITSOUND"].strToBool()
        hitSoundBox.selected = curSettings["HITSOUND_FILE_NAME"].replace("\"", "")
        hitSoundVolume.update()
    }
}