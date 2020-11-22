package rat.poison.ui.tabs.misctabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.MUSIC_KITS_FILE
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.game.hooks.cursorEnable
import rat.poison.overlay.opened
import rat.poison.scripts.changeName
import rat.poison.scripts.selfNade
import rat.poison.scripts.visuals.updateHitsound
import rat.poison.scripts.visuals.updateKillSound
import rat.poison.scripts.writeSpoof
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.tabs.ListAdapter
import rat.poison.ui.tabs.miscVisualsTab
import rat.poison.ui.tabs.othersTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiHelpers.binds.VisBindTableCustom
import rat.poison.utils.generalUtil.boolToStr
import rat.poison.utils.generalUtil.strToBool
import java.io.File

data class MusicKit(var id: Int = 0, var name: String = "")

class OthersTab: Tab(false, false) {
    private val table = VisTable()
    val doorSpam = VisCheckBoxCustom("Door Spam", "D_SPAM")
    var doorSpamKey = VisBindTableCustom("Door Spam Key", "D_SPAM_KEY")
    val weaponSpam = VisCheckBoxCustom("Weapon Spam", "W_SPAM")
    var weaponSpamKey = VisBindTableCustom("Weapon Spam Key", "W_SPAM_KEY")
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
    val killBindKey = VisBindTableCustom("Key", "KILL_BIND_KEY")
    private val nameChangeInput = VisValidatableTextField()
    private val nameChange = VisTextButton("Name-Change".toLocale())
    val postProcessingDisable = VisCheckBoxCustom("DISABLE_POST_PROCESSING".toLocale(), "DISABLE_POST_PROCESSING")
    val spectatorList = VisCheckBoxCustom("Spectator List", "SPECTATOR_LIST")
    val enableMusicKitSpoofer = VisCheckBoxCustom("Music Kit Spoofer", "MUSIC_KIT_SPOOFER")
    // me when the suggestions
    private var musicKitsAdapter = ListAdapter(ArrayList())
    private var musicKitsSelection = ListView(musicKitsAdapter)
    val musicKitArray = getMusicKitsArray()
    init {
        musicKitsSelection.updatePolicy = ListView.UpdatePolicy.ON_DRAW

        musicKitArray.forEach {
            musicKitsAdapter.add(it.name)
        }
        postProcessingDisable.changed {_, _ ->
            when (postProcessingDisable.isChecked) {
                true -> {
                    curSettings["ENABLE_NIGHTMODE"] = false
                    miscVisualsTab.nightMode.update()
                    miscVisualsTab.nightMode.disable(true)
                }
                false -> miscVisualsTab.nightMode.disable(false)
            }
            true
        }

        val selected = musicKitArray.first { it.id == curSettings["MUSIC_KIT_ID"].toInt() }.name
        val currentlySelected = VisLabel("${"CURRENTLY".toLocale()}: $selected")
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
        table.addSeparator().row()
        table.add(enableMusicKitSpoofer).left().row()
        table.add(currentlySelected).left().row()
        table.add(musicKitsSelection.mainTable).left().height(120F).row()

        selfNade.changed { _, _ ->
            selfNade()
        }

        nameChange.changed { _, _ ->
            changeName(nameChangeInput.text)
        }

        musicKitsAdapter.setItemClickListener { str ->
            if (!str.isNullOrEmpty()) {
                val id = getMusicKitId(str)
                curSettings["MUSIC_KIT_ID"] = id
                if (curSettings["MUSIC_KIT_SPOOFER"].strToBool()) {
                    writeSpoof()
                }
                currentlySelected.setText("${"CURRENTLY".toLocale()}: $str")
            }
        }

        //Create Hit Sound Toggle
        if (curSettings["ENABLE_HITSOUND"].strToBool()) hitSoundCheckBox.toggle()
        hitSoundCheckBox.changed { _, _ ->
            curSettings["ENABLE_HITSOUND"] = hitSoundCheckBox.isChecked.boolToStr()
            true
        }

        enableMusicKitSpoofer.changed {_, _ ->
            if (!enableMusicKitSpoofer.isChecked) {
                curSettings["MUSIC_KIT_ID"] = 1
            }
            writeSpoof()
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
        if (opened) {
            val hitSoundFiles = Array<String>()
            File("$SETTINGS_DIRECTORY\\hitsounds").listFiles()?.forEach {
                hitSoundFiles.add(it.name)
            }
            hitSoundBox.items = hitSoundFiles
            killSoundBox.items = hitSoundFiles
        }
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
        updateHitSoundsList()
    }
}

fun getMusicKitsArray(): Array<MusicKit> {
    val musicKitsArray = Array<MusicKit>()
    var strList: List<String>

    MUSIC_KITS_FILE.forEachLine { line->
        val musicKit = MusicKit()
        strList = line.split(" : ")
        try {
            musicKit.id = strList[0].toInt()
            musicKit.name = strList[1]
        } catch (e: Exception) {
            println("$strList is FUCKING WRONG BROOOOOOOOO FUCK")
        }
        finally {
            musicKitsArray.add(musicKit)
        }
    }

    return musicKitsArray
}

fun getMusicKitId(name: String): Int {
    othersTab.musicKitArray.forEach {
        if (it.name == name) return it.id
    }
    return 1
}