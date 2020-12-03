package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.scripts.changeName
import rat.poison.scripts.selfNade
import rat.poison.scripts.visuals.updateHitsound
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiPanels.miscTab
import rat.poison.utils.generalUtil.boolToStr
import rat.poison.utils.generalUtil.strToBool
import java.io.File

class MiscTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    //Movement
    val bunnyHop = VisCheckBoxCustom("Bunny Hop", "ENABLE_BUNNY_HOP")
    val bunnyHopHitChance = VisSliderCustom("HitChance", "BHOP_HITCHANCE", 0F, 100F,  1F, true, width1 = 150F, width2 = 73F)
    val autoStrafe = VisCheckBoxCustom("Auto Strafe", "AUTO_STRAFE")
    val autoStrafeBHopOnly = VisCheckBoxCustom("BHop Only", "STRAFE_BHOP_ONLY")
    val fastStop = VisCheckBoxCustom("Fast Stop", "FAST_STOP")
    val headWalk = VisCheckBoxCustom("Head Walk", "HEAD_WALK")

    //Fov + bomb timer + spectator list
    val fovChanger = VisCheckBoxCustom("Fov Changer", "ENABLE_FOV_CHANGER")
    val fovDefault = VisSliderCustom("Default FOV", "FOV_DEFAULT", 10F, 150F, 1F, true, width1 = 142F, width2 = 90F)
    val fovSmoothing = VisCheckBoxCustom("Smooth FOV Changes", "FOV_SMOOTH")
    val fovSniperDefault = VisSliderCustom("Default FOV", "FOV_SNIPER_DEFAULT", 10F, 150F, 1F, true, width1 = 142F, width2 = 90F)
    val fovSniperZoom1 = VisSliderCustom("Zoom 1 FOV", "FOV_ZOOM_1", 10F, 150F, 1F, true, width1 = 142F, width2 = 90F)
    val fovSniperZoom2 = VisSliderCustom("Zoom 2 FOV", "FOV_ZOOM_2", 10F, 150F, 1F, true, width1 = 142F, width2 = 90F)
    val bombTimer = VisCheckBoxCustom("Bomb Timer", "ENABLE_BOMB_TIMER")
    val bombTimerEnableBars = VisCheckBoxCustom("Timer Bars", "BOMB_TIMER_BARS")
    val bombTimerEnableMenu = VisCheckBoxCustom("Timer Menu", "BOMB_TIMER_MENU")
    val spectatorList = VisCheckBoxCustom("Spectator List", "SPECTATOR_LIST")

    val knifeBot = VisCheckBoxCustom("Knife Bot", "ENABLE_AUTO_KNIFE")
    val lsBomb = VisCheckBoxCustom("Perfect Bomb Defuse", "LS_BOMB")
    val doorSpam = VisCheckBoxCustom("Door Spam", "D_SPAM")
    var doorSpamKey = VisInputFieldCustom("Door Spam Key", "D_SPAM_KEY")
    val weaponSpam = VisCheckBoxCustom("Weapon Spam", "W_SPAM")
    var weaponSpamKey = VisInputFieldCustom("Weapon Spam Key", "W_SPAM_KEY")
    val enableReducedFlash = VisCheckBoxCustom("Reduced Flash", "ENABLE_REDUCED_FLASH")
    val flashMaxAlpha = VisSliderCustom("Max Alpha", "FLASH_MAX_ALPHA", 5F, 255F, 5F, true, width1 = 150F, width2 = 90F)
    val hitSoundCheckBox = VisCheckBoxCustom("Hitsound", "ENABLE_HITSOUND")
    val hitSoundBox = VisSelectBox<String>()
    val hitSoundVolume = VisSliderCustom("Volume", "HITSOUND_VOLUME", .1F, 1F, .1F, false, width1 = 150F, width2 = 90F)
    val selfNade = VisTextButton("Self-Nade".toLocale())
    val enableKillBind = VisCheckBoxCustom("Kill Bind", "KILL_BIND")
    val killBindKey = VisInputFieldCustom("Key", "KILL_BIND_KEY")
    private val nameChangeInput = VisValidatableTextField()
    private val nameChange = VisTextButton("Name-Change".toLocale())
    val postProcessingDisable = VisCheckBoxCustom("DISABLE_POST_PROCESSING".toLocale(), "DISABLE_POST_PROCESSING")

    init {
        selfNade.changed { _, _ ->
            selfNade()
        }

        nameChange.changed { _, _ ->
            changeName(nameChangeInput.text)
        }

        //Create Hit Sound Toggle
        if (curSettings["ENABLE_HITSOUND"].strToBool()) hitSoundCheckBox.toggle()
        hitSoundCheckBox.changed { _, _ ->
            curSettings["ENABLE_HITSOUND"] = hitSoundCheckBox.isChecked.boolToStr()
            true
        }

        //Create Hit Sound Selector Box
        val hitSound = VisTable()
        updateHitSoundsList()

        hitSound.add(hitSoundCheckBox)
        hitSound.add(hitSoundBox).padLeft(100F-hitSoundCheckBox.width).width(90F)

        hitSoundBox.selected = curSettings["HITSOUND_FILE_NAME"].replace("\"", "")

        hitSoundBox.changed { _, _ ->
            updateHitsound(hitSoundBox.selected)
            curSettings["HITSOUND_FILE_NAME"] = hitSoundBox.selected
            true
        }

        //testing this bs
        val leftTable = VisTable()
        val rightTable = VisTable()
        val superTable = VisSplitPane(leftTable, rightTable, false)
        
        superTable.setColor(1F, 1F, 1F, 1F)

        //Set alignments
        leftTable.align(Align.topLeft)
        rightTable.align(Align.topLeft)

        superTable.touchable = Touchable.childrenOnly

        //Top left pane (movement)
        leftTable.add(bunnyHop).left().padLeft(14F).row()
        leftTable.add(bunnyHopHitChance).left().padLeft(24F).row()
        leftTable.add(autoStrafe).left().padLeft(24F).row()
        leftTable.add(autoStrafeBHopOnly).left().padLeft(24F).row()
        leftTable.addSeparator().width(250F).left()
        leftTable.add(fastStop).left().padLeft(14F).row()
        leftTable.addSeparator().width(250F).left()
        leftTable.add(headWalk).left().padLeft(14F).row()
        leftTable.addSeparator().width(250F).left()
        //Bottom left pane (fov + bomb timer + spectator list)
        leftTable.add(fovChanger).left().padLeft(14F).row()
        leftTable.add(fovDefault).left().padLeft(14F).row()
        leftTable.add(fovSmoothing).left().padLeft(14F).row()
        leftTable.add(fovSniperDefault).left().padLeft(14F).row()
        leftTable.add(fovSniperZoom1).left().padLeft(14F).row()
        leftTable.add(fovSniperZoom2).left().padLeft(14F).row()
        leftTable.addSeparator().width(250F).left()
        leftTable.add(spectatorList).left().padLeft(14F).row()
        leftTable.add(bombTimer).left().padLeft(14F).row()
        leftTable.add(bombTimerEnableMenu).left().padLeft(24F).row()
        leftTable.add(bombTimerEnableBars).left().padLeft(24F).row()
        //Right pane (misc?)
        rightTable.add(hitSound).left().padLeft(5F).row()
        rightTable.add(hitSoundVolume).left().padLeft(5F).row()
        rightTable.addSeparator().width(250F).left()
        rightTable.add(enableReducedFlash).left().padLeft(5F).row()
        rightTable.add(flashMaxAlpha).left().padLeft(5F).row()
        rightTable.addSeparator().width(250F).left()
        rightTable.add(knifeBot).left().padLeft(5F).row()
        rightTable.addSeparator().width(250F).left()
        rightTable.add(lsBomb).left().padLeft(5F).row()
        rightTable.addSeparator().width(250F).left()
        rightTable.add(doorSpam).left().padLeft(5F).row()
        rightTable.add(doorSpamKey).left().padLeft(5F).row()
        rightTable.addSeparator().width(250F).left()
        rightTable.add(weaponSpam).left().padLeft(5F).row()
        rightTable.add(weaponSpamKey).left().padLeft(5F).row()
        rightTable.addSeparator().width(250F).left()
        rightTable.add(selfNade).pad(5F).top().left().width(240F).row()
        rightTable.addSeparator().width(250F).left()
        rightTable.add(enableKillBind).left().padLeft(5F).row()
        rightTable.add(killBindKey).left().padLeft(5F).row()
        rightTable.add(postProcessingDisable).left().padLeft(5F).row()
        rightTable.addSeparator().width(250F).left()
        rightTable.add(nameChangeInput).pad(5F).top().left().width(240F).row()
        rightTable.add(nameChange).pad(5F).top().left().width(240F)


        table.add(superTable).size(500F, 520F).top().growX()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Misc".toLocale()
    }

    fun updateHitSoundsList() {
        if (VisUI.isLoaded()) {
            val hitSoundFiles = Array<String>()
            File("$SETTINGS_DIRECTORY\\hitsounds").listFiles()?.forEach {
                hitSoundFiles.add(it.name)
            }
            hitSoundBox.items = hitSoundFiles
        }
    }
}

fun miscTabUpdate() {
    miscTab.apply {
        bunnyHop.update()
        bunnyHopHitChance.update()
        autoStrafe.update()
        postProcessingDisable.update()
        autoStrafeBHopOnly.update()
        fastStop.update()
        knifeBot.update()
        fovChanger.update()
        fovDefault.update()
        fovSmoothing.update()
        fovSniperDefault.update()
        fovSniperZoom1.update()
        fovSniperZoom2.update()
        doorSpam.update()
        doorSpamKey.update()
        weaponSpam.update()
        weaponSpamKey.update()
        bombTimer.update()
        bombTimerEnableMenu.update()
        bombTimerEnableBars.update()
        spectatorList.update()
        headWalk.update()
        enableReducedFlash.update()
        flashMaxAlpha.update()
        hitSoundCheckBox.update()
        hitSoundBox.selected = curSettings["HITSOUND_FILE_NAME"].replace("\"", "")
        hitSoundVolume.update()
        lsBomb.update()
        selfNade.setText("Self-Nade".toLocale())
        killBindKey.update()
        enableKillBind.update()
    }
}
