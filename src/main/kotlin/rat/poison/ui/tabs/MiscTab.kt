package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import org.jire.arrowhead.keyPressed
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.scripts.esp.updateHitsound
import rat.poison.scripts.nameChanger
import rat.poison.scripts.selfNade
import rat.poison.scripts.weaponSpamToggleKey
import rat.poison.strToBool
import rat.poison.ui.changed
import rat.poison.ui.miscTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.utils.ObservableBoolean
import java.io.File

class MiscTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    //Movement
    val bunnyHop = VisCheckBoxCustom("Bunny Hop", "ENABLE_BUNNY_HOP")
    val autoStrafe = VisCheckBoxCustom("Auto Strafe", "AUTO_STRAFE")
    val autoStrafeBHopOnly = VisCheckBoxCustom("BHop Only", "STRAFE_BHOP_ONLY")
    val fastStop = VisCheckBoxCustom("Fast Stop", "FAST_STOP")
    val aimStrafer = VisCheckBoxCustom("Auto Aim Strafe", "AIM_STRAFER")
    val aimStraferSelectBox = VisSelectBox<String>()
    val aimStraferShift = VisCheckBoxCustom("Shift Walk", "AIM_STRAFER_SHIFT")
    val aimStraferStrictness = VisSliderCustom("Strictness", "AIM_STRAFER_STRICTNESS", 0F, .5F, .01F, false, 3, width1 = 150F, width2 = 90F)
    val headWalk = VisCheckBox("Head Walk")

    //Fov + bomb timer + spectator list
    val fovChanger = VisCheckBoxCustom("Fov Changer", "ENABLE_FOV_CHANGER")
    val fovDefault = VisSliderCustom("Default FOV", "FOV_DEFAULT", 10F, 150F, 1F, true, width1 = 150F, width2 = 90F)
    val fovSmoothing = VisCheckBoxCustom("Smooth FOV Changes", "FOV_SMOOTH")
    val fovSniperDefault = VisSliderCustom("Default FOV", "FOV_SNIPER_DEFAULT", 10F, 150F, 1F, true, width1 = 150F, width2 = 90F)
    val fovSniperZoom1 = VisSliderCustom("Zoom 1 FOV", "FOV_ZOOM_1", 10F, 150F, 1F, true, width1 = 150F, width2 = 90F)
    val fovSniperZoom2 = VisSliderCustom("Zoom 2 FOV", "FOV_ZOOM_2", 10F, 150F, 1F, true, width1 = 150F, width2 = 90F)
    val bombTimer = VisCheckBoxCustom("Bomb Timer", "ENABLE_BOMB_TIMER")
    val bombTimerEnableBars = VisCheckBoxCustom("Timer Bars", "BOMB_TIMER_BARS")
    val bombTimerEnableMenu = VisCheckBoxCustom("Timer Menu", "BOMB_TIMER_MENU")
    val spectatorList = VisCheckBoxCustom("Spectator List", "SPECTATOR_LIST")

    val knifeBot = VisCheckBoxCustom("Knife Bot", "ENABLE_AUTO_KNIFE")
    val lsBomb = VisCheckBox("Perfect Bomb Defuse")
    val doorSpam = VisCheckBoxCustom("Door Spam", "D_SPAM")
    var doorSpamKey = VisInputFieldCustom("Door Spam Key", "D_SPAM_KEY")
    val weaponSpam = VisCheckBoxCustom("Weapon Spam", "W_SPAM")
    var weaponSpamKey = VisInputFieldCustom("Weapon Spam Key", "W_SPAM_KEY")
    val enableReducedFlash = VisCheckBoxCustom("Reduced Flash", "ENABLE_REDUCED_FLASH")
    val flashMaxAlpha = VisSliderCustom("Flash Max Alpha", "FLASH_MAX_ALPHA", 1F, 255F, 1F, true, width1 = 150F, width2 = 90F)
    val hitSoundCheckBox = VisCheckBoxCustom("Hitsound", "ENABLE_HITSOUND")
    val hitSoundBox = VisSelectBox<String>()
    val hitSoundVolume = VisSliderCustom("Hitsound Volume", "HITSOUND_VOLUME", .1F, 1F, .1F, false, width1 = 150F, width2 = 90F)
    private val selfNade = VisTextButton("Self Nade")
    private val nameChangeInput = VisValidatableTextField()
    private val nameChange = VisTextButton("Name Change")

    init {
        selfNade.changed { _, _ ->
            selfNade()
        }

        nameChange.changed { _, _ ->
            nameChanger(nameChangeInput.text)
        }

        weaponSpamKey.changed { _, _ ->
            weaponSpamToggleKey = ObservableBoolean({keyPressed(weaponSpamKey.value.toString().toInt())})
            true
        }

        //Aim Strafer Table
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
        aimStraferTable.add(aimStraferSelectBox).padLeft(150F - aimStrafer.width).left()

        //Create Head Walk Toggle
        val headWalkTable = VisTable()
        headWalk.isChecked = curSettings["HEAD_WALK"].strToBool()
        headWalk.changed { _, _ ->
            curSettings["HEAD_WALK"] = headWalk.isChecked.boolToStr()
            true
        }
        headWalkTable.add(headWalk).left()

        //enable last sec bomb defuse
        val lsBombTable = VisTable()
        lsBomb.isChecked = curSettings["LS_BOMB"].strToBool()
        lsBomb.changed { _, _ ->
            curSettings["LS_BOMB"] = lsBomb.isChecked.boolToStr()
            true
        }
        lsBombTable.add(lsBomb).left()

        //Create Hit Sound Toggle
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
        hitSound.add(hitSoundBox).padLeft(150F-hitSoundCheckBox.width).width(90F)

        hitSoundBox.selected = curSettings["HITSOUND_FILE_NAME"].replace("\"", "")

        hitSoundBox.changed { _, _ ->
            updateHitsound(hitSoundBox.selected)
            curSettings["HITSOUND_FILE_NAME"] = hitSoundBox.selected
            true
        }

        //testing this bs
        val subPaneTable1 = VisTable()
        val subPaneTable2 = VisTable()
        val superPaneTable1 = VisTable()
        val subPane = VisSplitPane(subPaneTable1, subPaneTable2, true)
        val superPane = VisSplitPane(subPane, superPaneTable1, false)
        
        subPane.setColor(1F, 1F, 1F, 1F)
        superPane.setColor(1F, 1F, 1F, 1F)

        //Set alignments
        subPaneTable1.align(Align.topLeft)
        subPaneTable2.align(Align.topLeft)
        superPaneTable1.align(Align.topLeft)

        subPane.touchable = Touchable.childrenOnly
        superPane.touchable = Touchable.childrenOnly

        //Top left pane (movement)
        subPaneTable1.add(bunnyHop).left().padLeft(5F).row()
        subPaneTable1.add(autoStrafe).left().padLeft(15F).row()
        subPaneTable1.add(autoStrafeBHopOnly).left().padLeft(15F).row()
        subPaneTable1.addSeparator().width(250F).left()
        subPaneTable1.add(fastStop).left().padLeft(5F).row()
        subPaneTable1.addSeparator().width(250F).left()
        subPaneTable1.add(aimStraferTable).left().padLeft(5F).row()
        subPaneTable1.add(aimStraferShift).left().padLeft(5F).row()
        subPaneTable1.add(aimStraferStrictness).left().padLeft(5F).row()
        subPaneTable1.addSeparator().width(250F).left()
        subPaneTable1.add(headWalkTable).left().padLeft(5F).row()
        //Bottom left pane (fov + bomb timer + spectator list)
        subPaneTable2.add(fovChanger).left().padLeft(5F).row()
        subPaneTable2.add(fovDefault).left().padLeft(5F).row()
        subPaneTable2.add(fovSmoothing).left().padLeft(5F).row()
        subPaneTable2.add(fovSniperDefault).left().padLeft(5F).row()
        subPaneTable2.add(fovSniperZoom1).left().padLeft(5F).row()
        subPaneTable2.add(fovSniperZoom2).left().padLeft(5F).row()
        subPaneTable2.addSeparator().width(250F).left()
        subPaneTable2.add(spectatorList).left().padLeft(5F).row()
        subPaneTable2.add(bombTimer).left().padLeft(5F).row()
        subPaneTable2.add(bombTimerEnableMenu).left().padLeft(15F).row()
        subPaneTable2.add(bombTimerEnableBars).left().padLeft(15F).row()
        //Right pane (misc?)
        superPaneTable1.add(hitSound).left().padLeft(5F).row()
        superPaneTable1.add(hitSoundVolume).left().padLeft(5F).row()
        superPaneTable1.addSeparator().width(250F).left()
        superPaneTable1.add(enableReducedFlash).left().padLeft(5F).row()
        superPaneTable1.add(flashMaxAlpha).left().padLeft(5F).row()
        superPaneTable1.addSeparator().width(250F).left()
        superPaneTable1.add(knifeBot).left().padLeft(5F).row()
        superPaneTable1.addSeparator().width(250F).left()
        superPaneTable1.add(lsBombTable).left().padLeft(5F).row()
        superPaneTable1.addSeparator().width(250F).left()
        superPaneTable1.add(doorSpam).left().padLeft(5F).row()
        superPaneTable1.add(doorSpamKey).left().padLeft(5F).row()
        superPaneTable1.addSeparator().width(250F).left()
        superPaneTable1.add(weaponSpam).left().padLeft(5F).row()
        superPaneTable1.add(weaponSpamKey).left().padLeft(5F).row()
        superPaneTable1.addSeparator().width(250F).left()
        superPaneTable1.add(selfNade).pad(5F).top().left().width(240F).row()
        superPaneTable1.addSeparator().width(250F).left()
        //superPaneTable1.add(nameChangeInput).pad(5F).top().left().width(240F).row()
        //superPaneTable1.add(nameChange).pad(5F).top().left().width(240F)

        table.add(superPane).size(500F, 480F).top()
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
        knifeBot.update()
        fovChanger.update()
        fovDefault.update()
        fovSmoothing.update()
        fovSniperDefault.update()
        fovSniperZoom1.update()
        fovSniperZoom2.update()
        aimStrafer.update()
        aimStraferShift.update()
        aimStraferSelectBox.selected = when(curSettings["AIM_STRAFER_TYPE"].toInt()) {
            1 -> "Same"
            else -> "Opposite"
        }
        aimStraferStrictness.update()
        doorSpam.update()
        doorSpamKey.update()
        weaponSpam.update()
        weaponSpamKey.update()
        bombTimer.update()
        bombTimerEnableMenu.update()
        bombTimerEnableBars.update()
        spectatorList.update()
        headWalk.isChecked = curSettings["HEAD_WALK"].strToBool()
        enableReducedFlash.update()
        flashMaxAlpha.update()
        hitSoundCheckBox.isChecked = curSettings["ENABLE_HITSOUND"].strToBool()
        hitSoundBox.selected = curSettings["HITSOUND_FILE_NAME"].replace("\"", "")
        hitSoundVolume.update()
        lsBomb.isChecked = curSettings["LS_BOMB"].strToBool()
    }
}
