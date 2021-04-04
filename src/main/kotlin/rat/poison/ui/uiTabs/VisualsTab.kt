package rat.poison.ui.uiTabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.overlay.opened
import rat.poison.scripts.visuals.disableAllEsp
import rat.poison.ui.uiTabs.visualsTables.*
import rat.poison.ui.uiElements.VisCheckBoxCustom
import rat.poison.ui.uiElements.binds.VisBindTableCustom
import rat.poison.ui.uiWindows.visualsTab

//val espTabbedPane = TabbedPane()
var glowEspTable = GlowEspTable()
var chamsEspTable = ChamsEspTable()
var indicatorEspTable = IndicatorEspTable()
var skeletonEspTable = SkeletonEspTable()
var boxEspTable = BoxEspTable()
var snaplinesEspTable = SnaplinesEspTable()
var footStepsEspTable = FootstepsEspTable()
var hitMarkerTable = HitMarkerTable()
var nadesTable = NadesTable()
var miscVisualsTable = MiscVisualsTable()

class VisualsTab : Tab(false, false) {
    private val table = VisTable(false)

    //Init labels/sliders/boxes that show values here
    //Static Visuals Tab Items
    val enableEsp = VisCheckBoxCustom("Enable ESP", "ENABLE_ESP")
    val visualsToggleKey = VisBindTableCustom("Visuals Toggle Key", "VISUALS_TOGGLE_KEY")

    init {
        val leftTable = VisTable(false)
        val middleTable = VisTable(false)
        val rightTable = VisTable(false)

        leftTable.add(enableEsp).left().width(310F).row()
        leftTable.add(visualsToggleKey).left().width(310F).row()
        leftTable.addSeparator().height(4F)
        leftTable.add(glowEspTable).left().width(310F).row()
        leftTable.addSeparator().height(4F)
        leftTable.add(chamsEspTable).left().width(310F).row()
        leftTable.addSeparator().height(4F)
        leftTable.add(footStepsEspTable).left().width(310F).row()
        leftTable.addSeparator().height(4F)
        leftTable.add(nadesTable).left().width(310F).top().expandY().row()
        leftTable.addSeparator().height(4F)

        middleTable.addSeparator().height(4F)
        middleTable.add(skeletonEspTable).width(310F).left().padLeft(4F).row()
        middleTable.addSeparator().height(4F)
        middleTable.add(indicatorEspTable).width(310F).left().padLeft(4F).row()
        middleTable.addSeparator().height(4F)
        middleTable.add(snaplinesEspTable).width(310F).left().padLeft(4F).row()
        middleTable.addSeparator().height(4F)
        middleTable.add(hitMarkerTable).width(310F).top().left().padLeft(4F).expandY().row()
        middleTable.addSeparator().height(4F)

        rightTable.addSeparator().height(4F)
        rightTable.add(boxEspTable).width(310F).left().padLeft(4F).row()
        rightTable.addSeparator().height(4F)
        rightTable.add(miscVisualsTable).width(310F).top().left().padLeft(4F).expandY().row()
        rightTable.addSeparator().height(4F)

        table.add(leftTable).width(310F).top().right().expandX().growY()
        table.addSeparator(true).width(4F)
        table.add(middleTable).width(310F).top().expandX().growY()
        table.addSeparator(true).width(4F)
        table.add(rightTable).width(310F).top().left().expandX().growY()
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "Visuals"
    }
}


fun updateDisableEsp() {
    visualsTab.apply {
        if (!opened) return

        val bool = !enableEsp.isChecked
        var col = Color(255F, 255F, 255F, 1F)
        if (bool) {
            col = Color(105F, 105F, 105F, .2F)
        }

        visualsToggleKey.disable(bool, col)

        glowEspTableDisable(bool)
        chamsEspTableDisable(bool, col)
        indicatorEspTableDisable(bool, col)
        skeletonEspTableDisable(bool)
        boxEspTableDisable(bool, col)
        snaplinesEspTableDisable(bool, col)
        footStepsEspTableDisable(bool, col)
        hitMarkerTableDisable(bool, col)
        miscVisualTableDisable(bool, col)
        nadesTableDisable(bool, col)

        if (!curSettings.bool["ENABLE_ESP"]) {
            disableAllEsp()
        } else if (!curSettings.bool["CHAMS_ESP"]) {
            disableAllEsp()
        }
    }
}

fun visualsTabUpdate() {
    visualsTab.apply {
        enableEsp.update()
        visualsToggleKey.update()
        visualsToggleKey.update()
    }
}