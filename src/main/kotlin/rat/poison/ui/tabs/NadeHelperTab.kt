package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.util.dialog.OptionDialogAdapter
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.App
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curLocalization
import rat.poison.scripts.*
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.nadeHelperTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisLabelCustom
import rat.poison.ui.uiHelpers.VisTextButtonCustom
import java.io.File

class NadeHelperTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableNadeHelper = VisCheckBoxCustom(curLocalization["ENABLE_NADE_HELPER"], "ENABLE_NADE_HELPER", nameInLocalization = "ENABLE_NADE_HELPER")
    val nadeHelperLoadedFile = VisLabelCustom(curLocalization["LOADED_NOTHING"], "LOADED_NOTHING")
    val addPosition = VisTextButtonCustom(curLocalization["CREATE_GRENADE_POSITION"], "CREATE_GRENADE_POSITION")
    val saveFileNadeHelper = VisTextButtonCustom(curLocalization["SAVE_AS_FILE"], "SAVE_AS_FILE")
    val loadFileNadeHelper = VisTextButtonCustom(curLocalization["LOAD_FROM_FILE"], "LOAD_FROM_FILE")
    val deleteFileNadeHelper = VisTextButtonCustom(curLocalization["DELETE_SELECTED_FILE"], "DELETE_SELECTED_FILE")
    val clearNadeHelper = VisTextButtonCustom(curLocalization["CLEAR_CURRENTLY_LOADED"], "CLEAR_CURRENTLY_LOADED")
    val deleteCurrentPositionHelper = VisTextButtonCustom(curLocalization["DELETE_AT_CURRENT_POSITION"], "DELETE_AT_CURRENT_POSITION")
    private val nadeHelperFileSelectBox = VisSelectBox<String>()

    init {
        //Nade position create button
        addPosition.changed { _, _ ->
            createPosition()
        }
        saveFileNadeHelper.changed { _, _ ->
            savePositions()
        }

        loadFileNadeHelper.changed { _, _ ->
            if (nadeHelperFileSelectBox.items.count() > 0) {
                loadPositions(nadeHelperFileSelectBox.selected)
            }
        }

        deleteFileNadeHelper.changed { _, _ ->
            if (nadeHelperFileSelectBox.items.count() > 0) {
                deleteNadeHelperFile(nadeHelperFileSelectBox.selected)
            }
        }

        clearNadeHelper.changed { _, _ ->
            Dialogs.showConfirmDialog(App.menuStage, curLocalization["WARNING"], curLocalization["CLEAR_CURRENTLY_LOADED_WARNING"], arrayOf("YES", "NO"), arrayOf(1, 2)) { it ->
                when (it) {
                    1 -> {
                        nadeHelperArrayList.clear()
                        nadeHelperLoadedFile.setText(curLocalization["LOADED_NOTHING"])
                    }
                }
            }
        }

        deleteCurrentPositionHelper.changed { _, _ ->
            deletePosition()
        }

        updateNadeFileHelperList()

        //Add everything to table
        val sldTable = VisTable()
        sldTable.add(saveFileNadeHelper).width(150F)
        sldTable.add(loadFileNadeHelper).padLeft(20F).padRight(20F).width(150F)
        sldTable.add(deleteFileNadeHelper).width(150F)

        table.add(enableNadeHelper).row()

        table.add(nadeHelperFileSelectBox).row()
        table.add(sldTable).row()
        table.add(clearNadeHelper).width(250F).row()

        table.add(addPosition).width(250F).row()
        table.add(deleteCurrentPositionHelper).width(250F).row()

        table.add(nadeHelperLoadedFile).center().row()
    }

    fun updateNadeFileHelperList() {
        val nadeHelperArray = Array<String>()
        File("$SETTINGS_DIRECTORY\\NadeHelper").listFiles()?.forEach {
            nadeHelperArray.add(it.name)
        }

        nadeHelperFileSelectBox.items = nadeHelperArray
    }

    private fun deleteNadeHelperFile(fileName: String) {
        val cfgFile = File("$SETTINGS_DIRECTORY\\NadeHelper\\$fileName")
        if (cfgFile.exists()) {
            cfgFile.delete()
        }
        updateNadeFileHelperList()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return curLocalization["NADE_HELPER_TAB_NAME"]
    }
}

fun nadeHelperTabUpdate() {
    nadeHelperTab.apply {
        enableNadeHelper.update()
        addPosition.update()
        saveFileNadeHelper.update()
        loadFileNadeHelper.update()
        deleteFileNadeHelper.update()
        clearNadeHelper.update()
        nadeHelperLoadedFile.update()
        deleteCurrentPositionHelper.update()
    }
}