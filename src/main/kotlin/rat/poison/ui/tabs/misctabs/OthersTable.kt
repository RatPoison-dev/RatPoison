package rat.poison.ui.tabs.misctabs

import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.*
import rat.poison.MUSIC_KITS_FILE
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.overlay.App.assetManager
import rat.poison.overlay.opened
import rat.poison.scripts.writeSpoof
import rat.poison.ui.changed
import rat.poison.ui.tabs.ListAdapter
import rat.poison.ui.tabs.miscVisualsTable
import rat.poison.ui.tabs.othersTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiHelpers.binds.VisBindTableCustom
import rat.poison.ui.uiRefreshing
import rat.poison.utils.generalUtil.boolToStr
import rat.poison.utils.saving
import java.io.File

data class MusicKit(var id: Int = 0, var name: String = "")

class OthersTable: VisTable(false) {
    val doorSpam = VisCheckBoxCustom("Door Spam", "D_SPAM")
    var doorSpamKey = VisBindTableCustom("Door Spam Key", "D_SPAM_KEY", keyWidth = 0F)
    val weaponSpam = VisCheckBoxCustom("Weapon Spam", "W_SPAM")
    var weaponSpamKey = VisBindTableCustom("Weapon Spam Key", "W_SPAM_KEY", keyWidth = 0F)
    val enableReducedFlash = VisCheckBoxCustom("No Flash", "ENABLE_REDUCED_FLASH")
    val flashMaxAlpha = VisSliderCustom("Alpha", "FLASH_MAX_ALPHA", 5F, 255F, 5F, true, 0, 100F, 100F)
    val hitSoundCheckBox = VisCheckBoxCustom("HitSound", "ENABLE_HITSOUND")
    val hitSoundBox = VisSelectBox<String>()
    val hitSoundVolume = VisSliderCustom("Volume", "HITSOUND_VOLUME", .1F, 1F, .1F, false, 1, 100F, 100F)
    val killSoundCheckBox = VisCheckBoxCustom("KillSound", "ENABLE_KILLSOUND")
    val killSoundBox = VisSelectBox<String>()
    val killSoundVolume = VisSliderCustom("Volume", "KILLSOUND_VOLUME", .1F, 1F, .1F, false, 1, 100F, 100F)
    val enableKillBind = VisCheckBoxCustom("Kill Bind", "KILL_BIND")
    val killBindKey = VisBindTableCustom("Key", "KILL_BIND_KEY", keyWidth = 0F)

    val postProcessingDisable = VisCheckBoxCustom("Disable Post Processing", "DISABLE_POST_PROCESSING")
    val spectatorList = VisCheckBoxCustom("Spectator List", "SPECTATOR_LIST")
    val enableMusicKitSpoofer = VisCheckBoxCustom("Music Kit Spoofer", "MUSIC_KIT_SPOOFER")
    val fakeLag = VisCheckBoxCustom("Fake Lag", "FAKE_LAG")
    val fakeLagTicks = VisSliderCustom("Fake Lag Ticks", "FAKE_LAG_TICKS", 1F, 14F, 1F, true, 0, 250F, 200F)

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
            miscVisualsTable.nightMode.disable(postProcessingDisable.isChecked)
            if (postProcessingDisable.isChecked) {
                curSettings["ENABLE_NIGHTMODE"] = false
                miscVisualsTable.nightMode.update()
            }
            true
        }

        val selected = musicKitArray.first { it.id == curSettings.int["MUSIC_KIT_ID"] }.name
        val currentlySelected = VisLabel("${"CURRENTLY"}: $selected")
        //Crashing on adding separators with .colspan(2) (?)
        updateHitSoundsList()

        musicKitsAdapter.setItemClickListener { str ->
            if (!str.isNullOrEmpty()) {
                val id = getMusicKitId(str)
                curSettings["MUSIC_KIT_ID"] = id
                if (curSettings.bool["MUSIC_KIT_SPOOFER"]) {
                    writeSpoof()
                }
                currentlySelected.setText("${"CURRENTLY"}: $str")
            }
        }

        //Create Hit Sound Toggle
        if (curSettings.bool["ENABLE_HITSOUND"]) hitSoundCheckBox.toggle()
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
            assetManager.loadMusic()
            curSettings["HITSOUND_FILE_NAME"] = hitSoundBox.selected
            true
        }

        //Create Kill Sound Toggle
        if (curSettings.bool["ENABLE_KILLSOUND"]) killSoundCheckBox.toggle()
        killSoundCheckBox.changed { _, _ ->
            assetManager.loadMusic()
            curSettings["ENABLE_KILLSOUND"] = killSoundCheckBox.isChecked.boolToStr()
            true
        }

        //Create Hit Sound Selector Box

        killSoundBox.selected = curSettings["KILLSOUND_FILE_NAME"].replace("\"", "")

        killSoundBox.changed { _, _ ->
            assetManager.loadMusic()
            curSettings["KILLSOUND_FILE_NAME"] = killSoundBox.selected
            true
        }

        add(hitSoundCheckBox).expandX().left()
        add(hitSoundBox).expandX().left()
        add(hitSoundVolume).expandX().left().row()

        add(killSoundCheckBox).expandX().left()
        add(killSoundBox).expandX().left()
        add(killSoundVolume).expandX().left().row()

        add(enableReducedFlash).colspan(2).expandX().left()
        add(flashMaxAlpha).expandX().left().row()

        add(doorSpam).colspan(2).expandX().left()
        add(doorSpamKey).expandX().left().row()

        add(weaponSpam).colspan(2).expandX().left()
        add(weaponSpamKey).expandX().left().row()

        add(enableKillBind).colspan(2).expandX().left()
        add(killBindKey).expandX().left().row()

        add(postProcessingDisable).colspan(3).left().row()

        add(spectatorList).colspan(3).left().row()

        //add(fakeLag).colspan(3).left().row()
        //add(fakeLagTicks).colspan(3).left().row()

        add(enableMusicKitSpoofer).colspan(3).left().row()
        add(currentlySelected).colspan(3).left().row()
        add(musicKitsSelection.mainTable).colspan(3).left().height(200F).row()
    }

    fun updateHitSoundsList() {
        if (opened && !saving && !uiRefreshing) {
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
    othersTable.apply {
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
        fakeLagTicks.update()
        fakeLag.update()
        killSoundBox.selected = curSettings["KILLSOUND_FILE_NAME"].replace("\"", "")
        killSoundVolume.update()
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
    othersTable.musicKitArray.forEach {
        if (it.name == name) return it.id
    }
    return 1
}