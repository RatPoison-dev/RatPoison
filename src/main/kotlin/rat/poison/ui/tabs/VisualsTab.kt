package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.overlay.opened
import rat.poison.scripts.visuals.disableAllEsp
import rat.poison.ui.tabs.visualstabs.*
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.binds.VisBindTableCustom
import rat.poison.ui.uiPanels.visualsTab

//val espTabbedPane = TabbedPane()
var glowEspTable = GlowEspTable()
var chamsEspTable = ChamsEspTable()
var indicatorEspTable = IndicatorEspTable()
var boxEspTab = BoxEspTab()
var snaplinesEspTab = SnaplinesEspTab()
var footStepsEspTab = FootstepsEspTab()
var hitMarkerTab = HitMarkerTab()
var nadesTab = NadesVT()
var miscVisualsTab = MiscVisualsTab()

class VisualsTab : Tab(false, false) {
    private val table = VisTable(false)

    //Init labels/sliders/boxes that show values here
    //Static Visuals Tab Items
    val enableEsp = VisCheckBoxCustom("Enable ESP", "ENABLE_ESP")
    val visualsToggleKey = VisBindTableCustom("Visuals Toggle Key", "VISUALS_TOGGLE_KEY")

    init {
        //ESP Tab
//        espTabbedPane.add(glowEspTab)
//        espTabbedPane.add(chamsEspTab)
//        espTabbedPane.add(indicatorEspTab)
//        espTabbedPane.add(boxEspTab)
//        espTabbedPane.add(snaplinesEspTab)
//        espTabbedPane.add(footStepsEspTab)
//        espTabbedPane.add(hitMarkerTab)
//        espTabbedPane.add(nadesTab)
//        espTabbedPane.add(miscVisualsTab)
//
//        espTabbedPane.switchTab(glowEspTab)



//        val espTabbedPaneContent = VisTable(false)
//        espTabbedPaneContent.padTop(10F)
//        espTabbedPaneContent.padBottom(10F)
//        espTabbedPaneContent.align(Align.top)
//        espTabbedPaneContent.columnDefaults(1)
//
//        val espScrollPane = ScrollPane(espTabbedPaneContent)
//        espScrollPane.setFlickScroll(false)
//        espScrollPane.setSize(1000F, 1000F)
//
//        espTabbedPaneContent.add(glowEspTab.contentTable).left().colspan(2).row()
//
//        espTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)
//
//        espTabbedPane.addListener(object : TabbedPaneAdapter() {
//            override fun switchedTab(tab: Tab?) {
//                if (tab == null) return
//
//                espTabbedPaneContent.clear()
//
//                espTabbedPaneContent.add(tab.contentTable).left().colspan(2).row()
//
//                espTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)
//            }
//        })

        val leftTable = VisTable(false)
        val middleTable = VisTable(false)
        val rightTable = VisTable(false)

        leftTable.add(enableEsp).left().row()
        leftTable.add(visualsToggleKey).left().row()
        leftTable.addSeparator()
        leftTable.add(glowEspTable).left().maxWidth(300F).row()
        leftTable.addSeparator()
        leftTable.add(chamsEspTable).left().maxWidth(300F).row()
        leftTable.addSeparator()
        //leftTable.add(indicatorEspTable).left().row()

        table.add(leftTable).width(300F)
        table.addSeparator(true)
        //table.add(middleTable).width(300F)
        //table.addSeparator(true)
        //table.add(rightTable).width(300F)



        //Add all items to label for tabbed pane content

        //table.add(glowEspTable).left()

//        espTabbedPane.add(indicatorEspTab)
//        espTabbedPane.add(boxEspTab)
//        espTabbedPane.add(snaplinesEspTab)
//        espTabbedPane.add(footStepsEspTab)
//        espTabbedPane.add(hitMarkerTab)
//        espTabbedPane.add(nadesTab)
//        espTabbedPane.add(miscVisualsTab)
//
//        espTabbedPane.switchTab(glowEspTab)














        //table.add(espTabbedPane.table).minWidth(500F).left().growX().row()
        //table.add(espScrollPane).minSize(500F, 500F).prefSize(500F, 500F).align(Align.left).growX().growY().row()
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

//        val recTab = espTabbedPane.activeTab
//
//        espTabbedPane.disableTab(glowEspTab, bool)
//        espTabbedPane.disableTab(chamsEspTab, bool)
//        espTabbedPane.disableTab(indicatorEspTab, bool)
//        espTabbedPane.disableTab(boxEspTab, bool)
//        espTabbedPane.disableTab(snaplinesEspTab, bool)
//        espTabbedPane.disableTab(footStepsEspTab, bool)
//        espTabbedPane.disableTab(hitMarkerTab, bool)
//        espTabbedPane.disableTab(nadesTab, bool)
//        espTabbedPane.disableTab(miscVisualsTab, bool)
//
//        espTabbedPane.switchTab(recTab)

        glowEspTabDisable(bool)
        chamsEspTabDisable(bool, col)
        indicatorEspTabDisable(bool, col)
        boxEspTabDisable(bool, col)
        snaplinesEspTabDisable(bool, col)
        footStepsEspTabDisable(bool, col)
        hitMarkerTabDisable(bool, col)
        miscVisualTabDisable(bool, col)
        nadesVTabDisable(bool, col)

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