@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.util.dialog.InputDialogAdapter
import com.kotcrab.vis.ui.widget.LinkLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.overlay.App.menuStage
import rat.poison.overlay.App.uiBombWindow
import rat.poison.overlay.App.uiSpecList
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.refreshMenu
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiPanels.optionsTab
import rat.poison.ui.uiRefreshing
import rat.poison.ui.uiUpdate
import rat.poison.utils.*
import rat.poison.utils.generalUtil.loadLocale
import java.io.File

var saving = false

class OptionsTab : Tab(false, false) {
    private val table = VisTable(true)

    val menuKey = VisInputFieldCustom("Menu Key", "MENU_KEY")
    val menuAlpha = VisSliderCustom("Menu Alpha", "MENU_ALPHA", .5F, 1F, .05F, false, width1 = 200F, width2 = 250F)
    val oglFPS = VisSliderCustom("OpenGL FPS", "OPENGL_FPS", 30F, 245F, 5F, true, width1 = 200F, width2 = 250F)
    val stayFocused = VisCheckBoxCustom("Stay Focused", "MENU_STAY_FOCUSED")
    val debug = VisCheckBoxCustom("Debug", "DEBUG")
    val keybinds = VisCheckBoxCustom("Keybinds", "KEYBINDS")
    val blur = VisCheckBoxCustom("Gaussian Blur", "GAUSSIAN_BLUR")
    private val discordLink = LinkLabel("Join-Discord".toLocale(), "https://discord.gg/TGsp8SNcFZ")

    var cfgFileSelectBox = VisSelectBox<String>()
    var localeFileSelectBox = VisSelectBox<String>()
    var loadLocaleButton = VisTextButton("Load-Locale".toLocale())

    init {
        loadLocaleButton.changed { _, _ ->
            curSettings["CURRENT_LOCALE"] = localeFileSelectBox.selected
            loadLocale("$SETTINGS_DIRECTORY\\Localizations\\${curSettings["CURRENT_LOCALE"]}.locale")

            refreshMenu()

            uiUpdate()

            false
        }

        //Create Save Button
        val saveButton = VisTextButton("Save-CFG".toLocale())
        saveButton.changed { _, _ ->
            Dialogs.showInputDialog(menuStage, "Enter-config-name".toLocale(), "", object : InputDialogAdapter() {
                override fun finished(input: String) {
                    saveCFG(input)
                }
            }).setSize(200F, 200F)
            true
        }

        //Create Load Button
        val loadButton = VisTextButton("Load-CFG".toLocale())
        loadButton.changed { _, _ ->
            if (!cfgFileSelectBox.selected.isNullOrEmpty()) {
                if (cfgFileSelectBox.selected.count() > 0) {
                    loadCFG(cfgFileSelectBox.selected)
                }
            }
            true
        }

        //Create Delete Button
        val deleteButton = VisTextButton("Delete-CFG".toLocale())
        deleteButton.changed { _, _ ->
            if (!cfgFileSelectBox.selected.isNullOrEmpty()) {
                if (cfgFileSelectBox.selected.count() > 0) {
                    deleteCFG(cfgFileSelectBox.selected)
                }
            }
            true
        }

        //File Select Box
        updateCFGList()
        updateLocaleList()

        //Create Save Current Config To Default
        val saveCurConfig = VisTextButton("Save-Current-Config-To-Default-Settings".toLocale())
        saveCurConfig.changed { _, _ ->
            saveWindows()
            saveDefault()
            true
        }

        //Add everything to table
        val sldTable = VisTable()
        sldTable.add(saveButton).width(100F)
        sldTable.add(loadButton).padLeft(20F).padRight(20F).width(100F)
        sldTable.add(deleteButton).width(100F)

        debug.changed { _, _ ->
            dbg = debug.isChecked
            true
        }

        table.add(menuKey).padLeft(25F).left().row()
        table.add(menuAlpha).row()
        table.add(oglFPS).row()
        table.add(stayFocused).padLeft(25F).left().row()
        table.add(debug).padLeft(25F).left().row()
        table.add(keybinds).padLeft(25F).left().row()
        table.add(blur).padLeft(25F).left().row()

        table.addSeparator()

        table.add(cfgFileSelectBox).row()

        table.add(sldTable).row()

        table.add(saveCurConfig).width(340F).row()

        table.addSeparator()

        table.add(localeFileSelectBox).row()
        table.add(loadLocaleButton).row()

        table.addSeparator()

        table.add(discordLink)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Options".toLocale()
    }

    fun updateCFGList() {
        if (VisUI.isLoaded()) {
            val cfgFilesArray = Array<String>()
            var items = 0
            File("$SETTINGS_DIRECTORY\\CFGS").listFiles()?.forEach {
                cfgFilesArray.add(it.name.replace(".cfg", ""))
                items++
            }

            if (items > 0) {
                cfgFileSelectBox.items = cfgFilesArray
            } else {
                cfgFileSelectBox.clearItems()
            }
        }
    }

    fun updateLocaleList() {
        if (VisUI.isLoaded()) {
            val localeFilesArray = Array<String>()
            var items = 0
            File("$SETTINGS_DIRECTORY\\Localizations").listFiles()?.forEach {
                localeFilesArray.add(it.name.replace(".locale", ""))
                items++
            }

            if (items > 0) {
                localeFileSelectBox.items = localeFilesArray
                localeFileSelectBox.selected = curSettings["CURRENT_LOCALE"]
            } else {
                localeFileSelectBox.clearItems()
            }
        }
    }
}

fun updateWindows() {
    if (VisUI.isLoaded()) {
        uiBombWindow.updatePosition(curSettings["BOMB_TIMER_X"].toFloat(), curSettings["BOMB_TIMER_Y"].toFloat())
        uiBombWindow.changeAlpha(curSettings["BOMB_TIMER_ALPHA"].toFloat())
        uiSpecList.updatePosition(curSettings["SPECTATOR_LIST_X"].toFloat(), curSettings["SPECTATOR_LIST_Y"].toFloat())
        uiSpecList.changeAlpha(curSettings["SPECTATOR_LIST_ALPHA"].toFloat())
    }
}

fun optionsTabUpdate() {
    if (uiRefreshing) return

    optionsTab.apply {
        menuKey.update()
        menuAlpha.update()
        oglFPS.update()
        stayFocused.update()
        debug.update()
        keybinds.update()
        blur.update()
    }

    updateWindows()
}