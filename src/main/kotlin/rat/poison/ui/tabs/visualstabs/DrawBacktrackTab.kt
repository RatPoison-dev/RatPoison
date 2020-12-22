package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.drawBacktrackTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom

class DrawBacktrackTab : Tab(true, false) {

    val table = VisTable()
    val backtrackVisualize = VisCheckBoxCustom("Backtrack Visualize", "BACKTRACK_VISUALIZE")
    val backtrackVisualizeSmokeCheck = VisCheckBoxCustom("Backtrack Visualize Smoke Check", "BACKTRACK_VISUALIZE_SMOKE_CHECK")
    val backtrackVisualizeAudible = VisCheckBoxCustom("Backtrack Visualize Audible", "BACKTRACK_VISUALIZE_AUDIBLE")
    val backtrackVisualizeColor = VisColorPickerCustom("Backtrack Visualize Color", "BACKTRACK_VISUALIZE_COLOR")

    init {
        table.padLeft(25F)
        table.padRight(25F)

        table.add(backtrackVisualize).left().row()
        table.add(backtrackVisualizeSmokeCheck).left().row()
        table.add(backtrackVisualizeAudible).left().row()
        table.add(backtrackVisualizeColor).left().row()
    }

    override fun getTabTitle(): String {
        return "Backtrack".toLocale()
    }

    override fun getContentTable(): Table {
        return table
    }
}

fun drawBacktrackTabUpdate() {
    drawBacktrackTab.apply {
        backtrackVisualize.update()
        backtrackVisualizeSmokeCheck.update()
        backtrackVisualizeAudible.update()
        backtrackVisualizeColor.update()
    }
}

fun drawBacktrackTabDisable(bool: Boolean) {
    drawBacktrackTab.backtrackVisualize.disable(bool)
    drawBacktrackTab.backtrackVisualizeSmokeCheck.disable(bool)
    drawBacktrackTab.backtrackVisualizeAudible.disable(bool)
    drawBacktrackTab.backtrackVisualizeColor.disable(bool)
}