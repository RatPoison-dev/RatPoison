package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import org.jire.arrowhead.keyPressed
import rat.poison.*
import rat.poison.scripts.esp.updateHitsound
import rat.poison.scripts.nameChanger
import rat.poison.scripts.selfNade
import rat.poison.scripts.weaponSpamToggleKey
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.miscTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiHelpers.VisTextButtonCustom
import rat.poison.utils.ObservableBoolean
import java.io.File

class MiscTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    //Movement
    val bunnyHop = VisCheckBoxCustom(curLocalization["ENABLE_BUNNY_HOP"], "ENABLE_BUNNY_HOP", nameInLocalization = "ENABLE_BUNNY_HOP")
    val autoStrafe = VisCheckBoxCustom(curLocalization["AUTO_STRAFE"], "AUTO_STRAFE", nameInLocalization = "AUTO_STRAFE")
    val autoStrafeBHopOnly = VisCheckBoxCustom(curLocalization["STRAFE_BHOP_ONLY"], "STRAFE_BHOP_ONLY", nameInLocalization = "STRAFE_BHOP_ONLY")
    val fastStop = VisCheckBoxCustom(curLocalization["FAST_STOP"], "FAST_STOP", nameInLocalization = "FAST_STOP")
    val aimStrafer = VisCheckBoxCustom(curLocalization["AIM_STRAFER"], "AIM_STRAFER", nameInLocalization = "AIM_STRAFER")
    val aimStraferSelectBox = VisSelectBox<String>()
    val aimStraferShift = VisCheckBoxCustom(curLocalization["AIM_STRAFER_SHIFT"], "AIM_STRAFER_SHIFT", nameInLocalization = "AIM_STRAFER_SHIFT")
    val aimStraferStrictness = VisSliderCustom(curLocalization["AIM_STRAFER_STRICTNESS"], "AIM_STRAFER_STRICTNESS", 0F, .5F, .01F, false, 3, width1 = 150F, width2 = 90F, nameInLocalization = "AIM_STRAFER_STRICTNESS")
    val headWalk = VisCheckBox(curLocalization["HEAD_WALK"])

    //Fov + bomb timer + spectator list
    val fovChanger = VisCheckBoxCustom(curLocalization["ENABLE_FOV_CHANGER"], "ENABLE_FOV_CHANGER", nameInLocalization = "ENABLE_FOV_CHANGER")
    val fovDefault = VisSliderCustom(curLocalization["FOV_DEFAULT"], "FOV_DEFAULT", 10F, 150F, 1F, true, width1 = 142F, width2 = 90F, nameInLocalization = "FOV_DEFAULT")
    val fovSmoothing = VisCheckBoxCustom(curLocalization["FOV_SMOOTH"], "FOV_SMOOTH", nameInLocalization = "FOV_SMOOTH")
    val fovSniperDefault = VisSliderCustom(curLocalization["FOV_SNIPER_DEFAULT"], "FOV_SNIPER_DEFAULT", 10F, 150F, 1F, true, width1 = 142F, width2 = 90F, nameInLocalization = "FOV_SNIPER_DEFAULT")
    val fovSniperZoom1 = VisSliderCustom(curLocalization["FOV_ZOOM_1"], "FOV_ZOOM_1", 10F, 150F, 1F, true, width1 = 142F, width2 = 90F, nameInLocalization = "FOV_ZOOM_1")
    val fovSniperZoom2 = VisSliderCustom(curLocalization["FOV_ZOOM_2"], "FOV_ZOOM_2", 10F, 150F, 1F, true, width1 = 142F, width2 = 90F, nameInLocalization = "FOV_ZOOM_2")
    val bombTimer = VisCheckBoxCustom(curLocalization["ENABLE_BOMB_TIMER"], "ENABLE_BOMB_TIMER", nameInLocalization = "ENABLE_BOMB_TIMER")
    val bombTimerEnableBars = VisCheckBoxCustom(curLocalization["BOMB_TIMER_BARS"], "BOMB_TIMER_BARS", nameInLocalization = "BOMB_TIMER_BARS")
    val bombTimerEnableMenu = VisCheckBoxCustom(curLocalization["BOMB_TIMER_MENU"], "BOMB_TIMER_MENU", nameInLocalization = "BOMB_TIMER_MENU")
    val spectatorList = VisCheckBoxCustom(curLocalization["ENABLE_SPECTATOR_LIST"], "SPECTATOR_LIST", nameInLocalization = "ENABLE_SPECTATOR_LIST")

    val knifeBot = VisCheckBoxCustom(curLocalization["ENABLE_AUTO_KNIFE"], "ENABLE_AUTO_KNIFE", nameInLocalization = "ENABLE_AUTO_KNIFE")
    val lsBomb = VisCheckBox(curLocalization["ENABLE_PERFECT_BOMB_DEFUSE"])
    val doorSpam = VisCheckBoxCustom(curLocalization["ENABLE_DOOR_SPAM"], "D_SPAM", nameInLocalization = "ENABLE_DOOR_SPAM")
    var doorSpamKey = VisInputFieldCustom(curLocalization["DOOR_SPAM_KEY"], "D_SPAM_KEY", nameInLocalization = "DOOR_SPAM_KEY")
    val weaponSpam = VisCheckBoxCustom(curLocalization["ENABLE_WEAPON_SPAM"], "W_SPAM", nameInLocalization = "ENABLE_WEAPON_SPAM")
    var weaponSpamKey = VisInputFieldCustom(curLocalization["WEAPON_SPAM_KEY"], "W_SPAM_KEY", nameInLocalization = "WEAPON_SPAM_KEY")
    val enableReducedFlash = VisCheckBoxCustom(curLocalization["ENABLE_REDUCED_FLASH"], "ENABLE_REDUCED_FLASH", nameInLocalization = "ENABLE_REDUCED_FLASH")
    val flashMaxAlpha = VisSliderCustom(curLocalization["FLASH_MAX_ALPHA"], "FLASH_MAX_ALPHA", 1F, 255F, 1F, true, width1 = 150F, width2 = 90F, nameInLocalization = "FLASH_MAX_ALPHA")
    val hitSoundCheckBox = VisCheckBoxCustom(curLocalization["ENABLE_HITSOUND"], "ENABLE_HITSOUND", nameInLocalization = "ENABLE_HITSOUND")
    val hitSoundBox = VisSelectBox<String>()
    val hitSoundVolume = VisSliderCustom(curLocalization["HITSOUND_VOLUME"], "HITSOUND_VOLUME", .1F, 1F, .1F, false, width1 = 150F, width2 = 90F, nameInLocalization = "HITSOUND_VOLUME")
    private val selfNade = VisTextButtonCustom(curLocalization["THROW_SELF_NADE"], nameInLocalization = "THROW_SELF_NADE")
    private val nameChangeInput = VisValidatableTextField()
    private val nameChange = VisTextButtonCustom(curLocalization["ENABLE_NAME_CHANGER"], nameInLocalization = "ENABLE_NAME_CHANGER")

    init {
        selfNade.changed { _, _ ->
            selfNade()
        }

        nameChange.changed { _, _ ->
            nameChanger(nameChangeInput.text)
        }

        weaponSpamKey.changed { _, _ ->
            weaponSpamToggleKey = ObservableBoolean({keyPressed(weaponSpamKey.value)})
            true
        }

        //Aim Strafer Table
        aimStraferSelectBox.setItems(curLocalization["AIM_STRAFER_SAME"], curLocalization["AIM_STRAFER_OPPOSITE"])
        aimStraferSelectBox.changed { _, _ ->
            if (aimStraferSelectBox.selected == curLocalization["AIM_STRAFER_SAME"]) {
                curSettings["AIM_STRAFER_TYPE"] = 1
            } else {
                curSettings["AIM_STRAFER_TYPE"] = 0
            }
        }
        val aimStraferTable = VisTable()
        aimStraferTable.add(aimStrafer).left()
        aimStraferTable.add(aimStraferSelectBox).padLeft(142F - aimStrafer.width).left()

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
        subPaneTable1.add(bunnyHop).left().padLeft(14F).row()
        subPaneTable1.add(autoStrafe).left().padLeft(24F).row()
        subPaneTable1.add(autoStrafeBHopOnly).left().padLeft(24F).row()
        subPaneTable1.addSeparator().width(250F).left()
        subPaneTable1.add(fastStop).left().padLeft(14F).row()
        subPaneTable1.addSeparator().width(250F).left()
        subPaneTable1.add(aimStraferTable).left().padLeft(14F).row()
        subPaneTable1.add(aimStraferShift).left().padLeft(14F).row()
        subPaneTable1.add(aimStraferStrictness).left().padLeft(14F).row()
        subPaneTable1.addSeparator().width(250F).left()
        subPaneTable1.add(headWalkTable).left().padLeft(14F).row()
        //Bottom left pane (fov + bomb timer + spectator list)
        subPaneTable2.add(fovChanger).left().padLeft(14F).row()
        subPaneTable2.add(fovDefault).left().padLeft(14F).row()
        subPaneTable2.add(fovSmoothing).left().padLeft(14F).row()
        subPaneTable2.add(fovSniperDefault).left().padLeft(14F).row()
        subPaneTable2.add(fovSniperZoom1).left().padLeft(14F).row()
        subPaneTable2.add(fovSniperZoom2).left().padLeft(14F).row()
        subPaneTable2.addSeparator().width(250F).left()
        subPaneTable2.add(spectatorList).left().padLeft(14F).row()
        subPaneTable2.add(bombTimer).left().padLeft(14F).row()
        subPaneTable2.add(bombTimerEnableMenu).left().padLeft(24F).row()
        subPaneTable2.add(bombTimerEnableBars).left().padLeft(24F).row()
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

        table.add(superPane).size(500F, 480F).top().growX()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return curLocalization["MISC_TAB_NAME"]
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
            1 -> curLocalization["AIM_STRAFER_SAME"]
            else -> curLocalization["AIM_STRAFER_OPPOSITE"]
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
