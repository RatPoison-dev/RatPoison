package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter
import rat.poison.toLocale
import rat.poison.ui.tabs.misctabs.BombTab
import rat.poison.ui.tabs.misctabs.FOVChangerTab
import rat.poison.ui.tabs.misctabs.MovementTab
import rat.poison.ui.tabs.misctabs.OthersTab

var miscTabbedPane = TabbedPane()
var movementTab = MovementTab()
var fovChangerTab = FOVChangerTab()
var bombTab = BombTab()
var othersTab = OthersTab()

class MiscTabs : Tab(false, false) {
    private val table = VisTable()
    init {
        miscTabbedPane.add(movementTab)
        miscTabbedPane.add(fovChangerTab)
        miscTabbedPane.add(bombTab)
        miscTabbedPane.add(othersTab)

        miscTabbedPane.switchTab(movementTab)

        val miscTabbedPaneContent = VisTable()
        miscTabbedPaneContent.padTop(10F)
        miscTabbedPaneContent.padBottom(10F)
        miscTabbedPaneContent.align(Align.top)
        miscTabbedPaneContent.columnDefaults(1)

        val miscScrollPane = ScrollPane(miscTabbedPaneContent)
        miscScrollPane.setFlickScroll(false)
        miscScrollPane.setSize(1000F, 1000F)

        miscTabbedPaneContent.add(movementTab.contentTable).left().colspan(2).row()

        miscTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)

        miscTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                miscTabbedPaneContent.clear()

                miscTabbedPaneContent.add(tab.contentTable).left().colspan(2).row()

                miscTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)
            }
        })
        table.add(miscTabbedPane.table).minWidth(500F).left().growX().row()
        table.add(miscScrollPane).minSize(500F, 500F).prefSize(500F, 500F).align(Align.left).growX().growY().row()
    }
    override fun getTabTitle(): String {
        return "Misc".toLocale()
    }

    override fun getContentTable(): Table {
        return table
    }

}