package rat.poison.ui.tabs.misctabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.VisValidatableTextField
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.scripts.changeName
import rat.poison.scripts.selfNade
import rat.poison.scripts.visuals.disablePostProcessing
import rat.poison.scripts.visuals.updateHitsound
import rat.poison.scripts.visuals.updateKillSound
import rat.poison.scripts.writeSpoof
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.tabs.othersTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.utils.generalUtil.boolToStr
import rat.poison.utils.generalUtil.strToBool
import java.io.File

class OthersTab: Tab(false, false) {
    private val table = VisTable()
    val doorSpam = VisCheckBoxCustom("Door Spam", "D_SPAM")
    var doorSpamKey = VisInputFieldCustom("Door Spam Key", "D_SPAM_KEY", true, 225F)
    val weaponSpam = VisCheckBoxCustom("Weapon Spam", "W_SPAM")
    var weaponSpamKey = VisInputFieldCustom("Weapon Spam Key", "W_SPAM_KEY",true, 225F)
    val enableReducedFlash = VisCheckBoxCustom("Reduced Flash", "ENABLE_REDUCED_FLASH")
    val flashMaxAlpha = VisSliderCustom("Max Alpha", "FLASH_MAX_ALPHA", 5F, 255F, 5F, true)
    val hitSoundCheckBox = VisCheckBoxCustom("HitSound", "ENABLE_HITSOUND")
    val hitSoundBox = VisSelectBox<String>()
    val hitSoundVolume = VisSliderCustom("Volume", "HITSOUND_VOLUME", .1F, 1F, .1F, false)
    val killSoundCheckBox = VisCheckBoxCustom("KillSound", "ENABLE_KILLSOUND")
    val killSoundBox = VisSelectBox<String>()
    val killSoundVolume = VisSliderCustom("Volume", "KILLSOUND_VOLUME", .1F, 1F, .1F, false)
    val selfNade = VisTextButton("Self-Nade".toLocale())
    val enableKillBind = VisCheckBoxCustom("Kill Bind", "KILL_BIND")
    val killBindKey = VisInputFieldCustom("Key", "KILL_BIND_KEY", true, 225F)
    private val nameChangeInput = VisValidatableTextField()
    private val nameChange = VisTextButton("Name-Change".toLocale())
    val postProcessingDisable = VisCheckBoxCustom("DISABLE_POST_PROCESSING".toLocale(), "DISABLE_POST_PROCESSING")
    val spectatorList = VisCheckBoxCustom("Spectator List", "SPECTATOR_LIST")
    val enableMusicKitSpoofer = VisCheckBoxCustom("Music Kit Spoofer", "MUSIC_KIT_SPOOFER")
    val musicKitId = VisInputFieldCustom("ID", "MUSIC_KIT_ID", true, 225F, "https://community.hashcsgo.com/threads/music-kit-ids.7/")
    init {
        //Crashing on adding separators with .colspan(2) (?)
        table.padLeft(25F)
        table.padRight(25F)

        val hitSound = VisTable()
        updateHitSoundsList()

        hitSound.add(hitSoundCheckBox).left()
        hitSound.add(hitSoundBox).padLeft(168F-hitSoundCheckBox.width).width(90F)

        val tmpTable = VisTable()
        tmpTable.add(killSoundCheckBox).left()
        tmpTable.add(killSoundBox).padLeft(168F-killSoundCheckBox.width).width(90F)
        table.add(hitSound).left().row()
        table.add(hitSoundVolume).left().row()
        table.addSeparator().row()
        table.add(tmpTable).left().row()
        table.add(killSoundVolume).left().row()
        table.addSeparator().row()
        table.add(enableReducedFlash).left().row()
        table.add(flashMaxAlpha).left().row()
        table.addSeparator().row()
        table.add(doorSpam).left().row()
        table.add(doorSpamKey).left().row()
        table.addSeparator().row()
        table.add(weaponSpam).left().row()
        table.add(weaponSpamKey).left().row()
        table.addSeparator().row()
        table.add(selfNade).left().row()
        table.add(enableKillBind).left().row()
        table.add(killBindKey).left().row()
        table.add(postProcessingDisable).left().row()
        table.addSeparator().row()
        table.add(nameChangeInput).left().row()
        table.add(nameChange).left().padTop(2F).row()
        table.addSeparator().row()
        table.add(spectatorList).left().row()
        table.add(enableMusicKitSpoofer).left().row()
        table.add(musicKitId).left().row()

        selfNade.changed { _, _ ->
            selfNade()
        }

        nameChange.changed { _, _ ->
            changeName(nameChangeInput.text)
        }

        postProcessingDisable.changed { _, _ ->
            disablePostProcessing()
        }

        musicKitId.changed {_, _ ->
            writeSpoof()
        }

        //Create Hit Sound Toggle
        if (curSettings["ENABLE_HITSOUND"].strToBool()) hitSoundCheckBox.toggle()
        hitSoundCheckBox.changed { _, _ ->
            curSettings["ENABLE_HITSOUND"] = hitSoundCheckBox.isChecked.boolToStr()
            true
        }

        //Create Hit Sound Selector Box

        hitSoundBox.selected = curSettings["HITSOUND_FILE_NAME"].replace("\"", "")

        hitSoundBox.changed { _, _ ->
            updateHitsound(hitSoundBox.selected)
            curSettings["HITSOUND_FILE_NAME"] = hitSoundBox.selected
            true
        }

        //Create Kill Sound Toggle
        if (curSettings["ENABLE_KILLSOUND"].strToBool()) killSoundCheckBox.toggle()
        killSoundCheckBox.changed { _, _ ->
            curSettings["ENABLE_KILLSOUND"] = killSoundCheckBox.isChecked.boolToStr()
            true
        }

        //Create Hit Sound Selector Box

        killSoundBox.selected = curSettings["KILLSOUND_FILE_NAME"].replace("\"", "")

        killSoundBox.changed { _, _ ->
            updateKillSound(killSoundBox.selected)
            curSettings["KILLSOUND_FILE_NAME"] = killSoundBox.selected
            true
        }

    }
    override fun getTabTitle(): String {
        return "Others".toLocale()
    }

    override fun getContentTable(): Table {
        return table
    }
    fun updateHitSoundsList() {
        val hitSoundFiles = Array<String>()
        File("$SETTINGS_DIRECTORY\\hitsounds").listFiles()?.forEach {
            hitSoundFiles.add(it.name)
        }
        hitSoundBox.items = hitSoundFiles
        killSoundBox.items = hitSoundFiles
    }
}

fun othersTabUpdate() {
    othersTab.apply {
        doorSpam.update()
        doorSpamKey.update()
        weaponSpam.update()
        weaponSpamKey.update()
        enableReducedFlash.update()
        flashMaxAlpha.update()
        hitSoundCheckBox.update()
        hitSoundBox.selected = curSettings["HITSOUND_FILE_NAME"].replace("\"", "")
        hitSoundVolume.update()
        killSoundCheckBox.update()
        killSoundBox.selected = curSettings["KILLSOUND_FILE_NAME"].replace("\"", "")
        killSoundVolume.update()
        selfNade.setText("Self-Nade".toLocale())
        killBindKey.update()
        enableKillBind.update()
        postProcessingDisable.update()
        spectatorList.update()
        enableMusicKitSpoofer.update()
        musicKitId.update()
    }
}