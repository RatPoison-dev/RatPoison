package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.LinkLabel
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.scripts.*
import rat.poison.toLocale
import rat.poison.ui.uiRefreshing

class RanksTab : Tab(false, false) {
    private val table = VisTable(true)

    private var ranksListTable = VisTable()
    private var teamsLabel = VisLabel()

    private var namesTable = VisTable()
    private var namesLabel = VisLabel()

    private var ranksLabel = VisLabel()
    private var killsLabel = VisLabel()
    private var deathsLabel = VisLabel()
    private var kdLabel = VisLabel()
    private var winsLabel = VisLabel()
    private var moneyLabel = VisLabel()

    init {
        ranksListTable.add(teamsLabel)

        namesTable.add(namesLabel).row()
        ranksListTable.add(namesTable).top().padRight(4f) //Table

        ranksListTable.add(ranksLabel)
        ranksListTable.add(killsLabel)
        ranksListTable.add(deathsLabel)
        ranksListTable.add(kdLabel)
        ranksListTable.add(winsLabel)
        ranksListTable.add(moneyLabel)

        table.add(ranksListTable).left().maxWidth(500F)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Ranks".toLocale()
    }

    fun updateRanks() {
        teamsLabel.setText("Team".toLocale() + "  \n")
        namesLabel.setText("Name".toLocale())
        ranksLabel.setText("Rank".toLocale() + "  \n")
        killsLabel.setText("Kills".toLocale() + "  \n")
        deathsLabel.setText("Deaths".toLocale() + "  \n")
        kdLabel.setText("K/D".toLocale() + "  \n")
        winsLabel.setText("Wins".toLocale() + "  \n")
        moneyLabel.setText("Money".toLocale() + "  \n")

        namesTable.reset()
        namesTable.add(namesLabel).left().row()

        for (i in 0 until teamList.size) {
            if (teamList[i] == "CT") {
                constructRank(i)
            }
        }

        for (i in 0 until teamList.size) {
            if (teamList[i] == "T") {
                constructRank(i)
            }
        }
    }

    private fun constructRank(index: Int) {
        if (uiRefreshing) return

        teamsLabel.setText(teamsLabel.text.toString() + teamList[index].toLocale() + "  \n")

        var tmpName = nameList[index]
        tmpName = tmpName.substring(0, if (tmpName.length > 24) 24 else tmpName.length)

        if (steamIDList[index].toInt() != 0) { //Bot check
            namesTable.add(LinkLabel(tmpName, "https://steamcommunity.com/profiles/%5BU:1:" + steamIDList[index] + "%5D/")).height(21f).left().row()
        } else {
            namesTable.add(tmpName).height(21f).left().row()
        }

        ranksLabel.setText(ranksLabel.text.toString() + rankList[index] + "  \n")
        killsLabel.setText(killsLabel.text.toString() + killsList[index] + "  \n")
        deathsLabel.setText(deathsLabel.text.toString() + deathsList[index] + "  \n")
        kdLabel.setText(kdLabel.text.toString() + KDList[index] + "  \n")
        winsLabel.setText(winsLabel.text.toString() + winsList[index] + "  \n")
        moneyLabel.setText(moneyLabel.text.toString() + moneyList[index] + "  \n")
    }
}