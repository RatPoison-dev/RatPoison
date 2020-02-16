package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import org.jire.arrowhead.keyPressed
import rat.poison.*
import rat.poison.scripts.deletePosition
import rat.poison.scripts.esp.updateHitsound
import rat.poison.scripts.selfnade
import rat.poison.scripts.weaponSpam
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
    val bunnyHop = VisCheckBoxCustom("Bunny Hop", "ENABLE_BUNNY_HOP")
    val autoStrafe = VisCheckBoxCustom("Auto Strafe", "AUTO_STRAFE")
    val autoStrafeBHopOnly = VisCheckBoxCustom("BHop Only", "STRAFE_BHOP_ONLY")
    val fastStop = VisCheckBoxCustom("Fast Stop", "FAST_STOP")

    val knifeBot = VisCheckBoxCustom("Knife Bot", "ENABLE_AUTO_KNIFE")

    val aimStrafer = VisCheckBoxCustom("Auto Aim Strafe", "AIM_STRAFER")
    val aimStraferSelectBox = VisSelectBox<String>()
    val aimStraferShift = VisCheckBoxCustom("Shift Walk", "AIM_STRAFER_SHIFT")
    val aimStraferStrictness = VisSliderCustom("Strictness", "AIM_STRAFER_STRICTNESS", 0F, .5F, .01F, false, 3, width1 = 200F, width2 = 250F)

    val fovChanger = VisCheckBoxCustom("Fov Changer", "ENABLE_FOV_CHANGER")
    val fovDefault = VisSliderCustom("Default FOV", "FOV_DEFAULT", 10F, 150F, 1F, true, width1 = 200F, width2 = 250F)
    val fovSmoothing = VisCheckBoxCustom("Smooth FOV Changes", "FOV_SMOOTH")
    val fovSniperDefault = VisSliderCustom("Sniper Default FOV", "FOV_SNIPER_DEFAULT", 10F, 150F, 1F, true, width1 = 200F, width2 = 250F)
    val fovSniperZoom1 = VisSliderCustom("Sniper Zoom 1 FOV", "FOV_ZOOM_1", 10F, 150F, 1F, true, width1 = 200F, width2 = 250F)
    val fovSniperZoom2 = VisSliderCustom("Sniper Zoom 2 FOV", "FOV_ZOOM_2", 10F, 150F, 1F, true, width1 = 200F, width2 = 250F)

    val bombTimer = VisCheckBoxCustom("Bomb Timer", "ENABLE_BOMB_TIMER")
    val bombTimerEnableBars = VisCheckBoxCustom("Timer Bars", "BOMB_TIMER_BARS")
    val bombTimerEnableMenu = VisCheckBoxCustom("Timer Menu", "BOMB_TIMER_MENU")
    val spectatorList = VisCheckBoxCustom("Spectator List", "SPECTATOR_LIST")

    val headWalk = VisCheckBox("Head Walk")
    val lsBomb = VisCheckBox("Perfect Bomb Defuse")

    val doorSpam = VisCheckBoxCustom("Door Spam", "D_SPAM")
    var doorSpamKey = VisInputFieldCustom("Door Spam Key", "D_SPAM_KEY")

    val selfnade = VisTextButton("Self Nade")



    val weaponspam = VisCheckBoxCustom("Weapon Spam", "W_SPAM")
    var weaponSpamKey = VisInputFieldCustom("Weapon Spam Key", "W_SPAM_KEY")

    val enableReducedFlash = VisCheckBoxCustom("Reduced Flash", "ENABLE_REDUCED_FLASH")
    val flashMaxAlpha = VisSliderCustom("Flash Max Alpha", "FLASH_MAX_ALPHA", 1F, 255F, 1F, true, width1 = 200F, width2 = 250F)

    val hitSoundCheckBox = VisCheckBoxCustom("Hitsound", "ENABLE_HITSOUND")
    val hitSoundBox = VisSelectBox<String>()
    val hitSoundVolume = VisSliderCustom("Hitsound Volume", "HITSOUND_VOLUME", .1F, 1F, .1F, false, width1 = 200F, width2 = 250F)

    init {
        selfnade.changed { _, _ ->
            selfnade()
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
        aimStraferTable.add(aimStraferSelectBox).padLeft(200F - aimStrafer.width).left()

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
        hitSound.add(hitSoundBox).padLeft(200F-hitSoundCheckBox.width)

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
        table.add(knifeBot).left().row()
        table.addSeparator()
        table.add(fovChanger).left().row()
        table.add(fovDefault).left().row()
        table.add(fovSmoothing).left().row()
        table.add(fovSniperDefault).left().row()
        table.add(fovSniperZoom1).left().row()
        table.add(fovSniperZoom2).left().row()
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
        table.add(lsBombTable).left().row()
        table.addSeparator()
        table.add(doorSpam).left().row()
        table.add(doorSpamKey).left().row()
        table.addSeparator()
        table.add(weaponspam).left().row()
        table.add(weaponSpamKey).left().row()
        table.addSeparator()
        table.add(selfnade).left().row()
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
        weaponspam.update()
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
