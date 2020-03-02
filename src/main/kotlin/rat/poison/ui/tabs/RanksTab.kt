package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.scripts.ctPlayers
import rat.poison.scripts.tPlayers

class RanksTab : Tab(false, false) {
    private val table = VisTable(true)

    var ranksListTable = VisTable()
    var ctList = VisLabel()
    var tList = VisLabel()
    //Init labels/sliders/boxes that show values here

    init {
        ranksListTable.add(VisLabel("Team"))
        ranksListTable.add(VisLabel("Name"))
        ranksListTable.add(VisLabel("Rank"))
        ranksListTable.add(VisLabel("Kills"))
        ranksListTable.add(VisLabel("Deaths"))
        ranksListTable.add(VisLabel("K/D")).row()

        ranksListTable.add(ctList).uniform(true).colspan(6)
        ranksListTable.row()
        ranksListTable.add(tList).colspan(6).center()
        //Add all items to label for tabbed pane content
        table.add(ranksListTable)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Ranks"
    }

    fun updateRanks() {
        val sb = StringBuilder()
        ctPlayers.forEachIndexed { _, ent->
            ent.forEachIndexed { idx, str->
                sb.append(str)

                if ((idx != ent.size-1)) {
                    //for (i in 0 until 15 - str.length) {
                        sb.append("          ")
                    //}
                }
            }
            sb.appendln()
        }
        ctList.setText(sb)
        sb.clear()
        tPlayers.forEachIndexed { _, ent->
            ent.forEachIndexed { idx, str->
                sb.append(str)

                if ((idx != ent.size-1)) {
                    //for (i in 0 until 15 - str.length) {
                        sb.append("          ")
                    //}
                }
            }
            sb.appendln()
        }
        tList.setText(sb)
    }
}