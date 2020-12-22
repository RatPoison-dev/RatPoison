package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter
import rat.poison.curSettings
import rat.poison.overlay.opened
import rat.poison.scripts.visuals.disableAllEsp
import rat.poison.toLocale
import rat.poison.ui.tabs.visualstabs.*
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiHelpers.binds.VisBindTableCustom
import rat.poison.ui.uiPanels.visualsTab
import rat.poison.utils.generalUtil.strToBool

val espTabbedPane = TabbedPane()
var glowEspTab = GlowEspTab()
var chamsEspTab = ChamsEspTab()
var indicatorEspTab = IndicatorEspTab()
var boxEspTab = BoxEspTab()
var drawBacktrackTab = DrawBacktrackTab()
var snaplinesEspTab = SnaplinesEspTab()
var footStepsEspTab = FootstepsEspTab()
var hitMarkerTab = HitMarkerTab()
var nadesTab = NadesVT()
var miscVisualsTab = MiscVisualsTab()

class VisualsTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    //Static Visuals Tab Items
    val enableEsp = VisCheckBoxCustom("Enable ESP", "ENABLE_ESP")
    val visualsToggleKey = VisBindTableCustom("Visuals Toggle Key", "VISUALS_TOGGLE_KEY")
    val espAudibleDistance = VisSliderCustom("Audible ESP Range", "AUDIBLE_ESP_RANGE", 100F, 5000F, 100F, true)

    init {
        //ESP Tab
        espTabbedPane.add(glowEspTab)
        espTabbedPane.add(chamsEspTab)
        espTabbedPane.add(indicatorEspTab)
        espTabbedPane.add(boxEspTab)
        espTabbedPane.add(snaplinesEspTab)
        espTabbedPane.add(footStepsEspTab)
        espTabbedPane.add(hitMarkerTab)
        espTabbedPane.add(nadesTab)
        espTabbedPane.add(miscVisualsTab)
        espTabbedPane.add(drawBacktrackTab)

        espTabbedPane.switchTab(glowEspTab)

        val espTabbedPaneContent = VisTable()
        espTabbedPaneContent.padTop(10F)
        espTabbedPaneContent.padBottom(10F)
        espTabbedPaneContent.align(Align.top)
        espTabbedPaneContent.columnDefaults(1)

        val espScrollPane = ScrollPane(espTabbedPaneContent)
        espScrollPane.setFlickScroll(false)
        espScrollPane.setSize(1000F, 1000F)

        espTabbedPaneContent.add(glowEspTab.contentTable).left().colspan(2).row()

        espTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)

        espTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                espTabbedPaneContent.clear()

                espTabbedPaneContent.add(tab.contentTable).left().colspan(2).row()

                espTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)
            }
        })

        //Add all items to label for tabbed pane content
        table.add(enableEsp).padLeft(25F).left().row()
        table.add(visualsToggleKey).padLeft(25F).left().row()
        table.add(espAudibleDistance).padLeft(25F).left().padBottom(10F).row()
        table.add(espTabbedPane.table).minWidth(500F).left().growX().row()
        table.add(espScrollPane).minSize(500F, 500F).prefSize(500F, 500F).align(Align.left).growX().growY().row()
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "Visuals".toLocale()
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
        espAudibleDistance.disable(bool, col)

        val recTab = espTabbedPane.activeTab

        espTabbedPane.disableTab(glowEspTab, bool)
        espTabbedPane.disableTab(chamsEspTab, bool)
        espTabbedPane.disableTab(indicatorEspTab, bool)
        espTabbedPane.disableTab(boxEspTab, bool)
        espTabbedPane.disableTab(snaplinesEspTab, bool)
        espTabbedPane.disableTab(footStepsEspTab, bool)
        espTabbedPane.disableTab(hitMarkerTab, bool)
        espTabbedPane.disableTab(nadesTab, bool)
        espTabbedPane.disableTab(miscVisualsTab, bool)
        espTabbedPane.disableTab(drawBacktrackTab, bool)

        espTabbedPane.switchTab(recTab)

        glowEspTabDisable(bool)
        chamsEspTabDisable(bool, col)
        indicatorEspTabDisable(bool, col)
        boxEspTabDisable(bool, col)
        snaplinesEspTabDisable(bool, col)
        footStepsEspTabDisable(bool, col)
        hitMarkerTabDisable(bool, col)
        miscVisualTabDisable(bool, col)
        nadesVTabDisable(bool, col)
        drawBacktrackTabDisable(bool)

        if (!curSettings["ENABLE_ESP"].strToBool()) {
            disableAllEsp()
        } else if (!curSettings["CHAMS_ESP"].strToBool()) {
            disableAllEsp()
        }
    }
}

fun visualsTabUpdate() {
    visualsTab.apply {
        enableEsp.update()
        visualsToggleKey.update()
        visualsToggleKey.update()
        espAudibleDistance.update()
    }
}