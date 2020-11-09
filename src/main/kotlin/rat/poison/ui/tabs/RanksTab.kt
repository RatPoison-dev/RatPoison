package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter
import rat.poison.toLocale
import rat.poison.ui.tabs.rankstabs.MainRanksTab
import rat.poison.ui.tabs.rankstabs.OptionsRanksTab

var ranksTabbedPane = TabbedPane()
var mainRanksTab = MainRanksTab()
var optionsRanksTab = OptionsRanksTab()

class RanksTab : Tab(false, false) {
    val table = VisTable()

    init {
        ranksTabbedPane.add(mainRanksTab)
        ranksTabbedPane.add(optionsRanksTab)

        ranksTabbedPane.switchTab(mainRanksTab)

        val ranksTabbedPaneContent = VisTable()
        ranksTabbedPaneContent.padTop(10F)
        ranksTabbedPaneContent.padBottom(10F)
        ranksTabbedPaneContent.align(Align.top)
        ranksTabbedPaneContent.columnDefaults(1)

        val ranksScrollPane = ScrollPane(ranksTabbedPaneContent)
        ranksScrollPane.setFlickScroll(false)
        ranksScrollPane.setSize(1000F, 1000F)

        ranksTabbedPaneContent.add(mainRanksTab.contentTable).left().colspan(2).row()

        ranksTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)

        ranksTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                ranksTabbedPaneContent.clear()

                ranksTabbedPaneContent.add(tab.contentTable).left().colspan(2).row()

                ranksTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)
            }
        })
        table.add(ranksTabbedPane.table).minWidth(500F).left().growX().row()
        table.add(ranksScrollPane).minSize(500F, 500F).prefSize(500F, 500F).align(Align.left).growX().growY().row()
    }

    override fun getTabTitle(): String {
        return "Ranks".toLocale()
    }

    override fun getContentTable(): Table {
        return table
    }

}