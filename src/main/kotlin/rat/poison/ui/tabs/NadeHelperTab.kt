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
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.overlay.App
import rat.poison.scripts.*
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiPanels.nadeHelperTab
import java.io.File

var nadeHelperLoadedFileStr = "N/A"

class NadeHelperTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableNadeHelper = VisCheckBoxCustom("Nade-Helper".toLocale(), "ENABLE_NADE_HELPER")
    val accuracyRadius = VisSliderCustom("Accuracy radius", "NADE_ACCURACY_RADIUS", 100F, 5000F, 20F, true, 2, 175F, 100F)
    val nadeHelperLoadedFile = VisLabel("Loaded:-N/A".toLocale())
    val enableNadeThrower = VisCheckBoxCustom("Auto throw", "ENABLE_NADE_THROWER")
    val nadeThrowerKey = VisInputFieldCustom("Key", "NADE_THROWER_KEY", true, 100F)
    val nadeThrowerSmooth = VisSliderCustom("Smoothness", "NADE_THROWER_SMOOTHNESS", 10F, 100F, 1F, true, 2, 150F, 100F)
    val nadeHelperFileSelectBox = VisSelectBox<String>()

    init {
        //Nade position create button
        val addPosition = VisTextButton("Create-Grenade-Position".toLocale())
        addPosition.changed { _, _ ->
            createPosition()
        }

        val saveFileNadeHelper = VisTextButton("Save-As-File".toLocale())
        saveFileNadeHelper.changed { _, _ ->
            savePositions()
        }

        val loadFileNadeHelper = VisTextButton("Load-From-File".toLocale())
        loadFileNadeHelper.changed { _, _ ->
            if (nadeHelperFileSelectBox.items.count() > 0) {
                loadPositions(nadeHelperFileSelectBox.selected)
            }
        }

        val deleteFileNadeHelper = VisTextButton("Delete-Selected-File".toLocale())
        deleteFileNadeHelper.changed { _, _ ->
            if (nadeHelperFileSelectBox.items.count() > 0) {
                deleteNadeHelperFile(nadeHelperFileSelectBox.selected)
            }
        }

        val clearNadeHelper = VisTextButton("Clear-Currently-Loaded".toLocale())
        clearNadeHelper.changed { _, _ ->
            Dialogs.showOptionDialog(App.menuStage, "Warning".toLocale(), "CLEAR_POSITIONS".toLocale(), Dialogs.OptionDialogType.YES_NO, object: OptionDialogAdapter() {
                override fun yes() {
                    nadeHelperArrayList.clear()
                    nadeHelperLoadedFile.setText("Loaded:-N/A".toLocale())
                }
            })
        }

        val deleteCurrentPositionHelper = VisTextButton("Delete-At-Current-Position".toLocale())
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
        table.add(accuracyRadius).row()
        table.add(enableNadeThrower).row()
        table.add(nadeThrowerKey).row()
        table.add(nadeThrowerSmooth).center().row()

        table.add(nadeHelperFileSelectBox).row()
        table.add(sldTable).row()
        table.add(clearNadeHelper).width(250F).row()

        table.add(addPosition).width(250F).row()
        table.add(deleteCurrentPositionHelper).width(250F).row()

        table.add(nadeHelperLoadedFile).row()
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
        return "Nade-Helper".toLocale()
    }
}

fun nadeHelperTabUpdate() {
    nadeHelperTab.apply {
        enableNadeHelper.update()
        accuracyRadius.update()
        enableNadeThrower.update()
        nadeThrowerKey.update()
        nadeThrowerSmooth.update()
        nadeHelperLoadedFile.setText("Loaded: $nadeHelperLoadedFileStr")
    }
}