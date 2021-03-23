package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.LinkLabel
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.scripts.ranksPlayerList
import rat.poison.ui.uiRefreshing
import rat.poison.utils.RanksPlayer
import rat.poison.utils.saving

var updatingRanks = false

private class VisLabelExtension(mainText: String): VisLabel(mainText) {
    override fun setText(newText: CharSequence?) {
        if (this.text != newText) {
            super.setText(newText)
        }
    }
}

class RanksTab : Tab(false, false) {
    private val table = VisTable(true)

    private var ranksListTable = VisTable(false)
    private var teamsLabel = VisLabelExtension("Team" + "  \n")

    private var namesTable = VisTable(false)
    private var namesLabel = VisLabelExtension("Name" + "  \n")

    private var ranksLabel = VisLabelExtension("Rank" + "  \n")
    private var killsLabel = VisLabelExtension("Kills" + "  \n")
    private var deathsLabel = VisLabelExtension("Deaths" + "  \n")
    private var kdLabel = VisLabelExtension("K/D" + "  \n")
    private var winsLabel = VisLabelExtension("Wins" + "  \n")
    private var moneyLabel = VisLabelExtension("Money" + "  \n")

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

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "Ranks"
    }

    fun updateRanks() {
        if (uiRefreshing || saving || updatingRanks || !isActiveTab) return
        updatingRanks = true
        teamsLabel.setText("Team" + "  \n")
        namesLabel.setText("Name")
        ranksLabel.setText("Rank" + "  \n")
        killsLabel.setText("Kills" + "  \n")
        deathsLabel.setText("Deaths" + "  \n")
        kdLabel.setText("K/D" + "  \n")
        winsLabel.setText("Wins" + "  \n")
        moneyLabel.setText("Money" + "  \n")

        namesTable.clear()
        namesTable.add(namesLabel).left().row()

        ranksPlayerList.forEach {
            constructRank(it)
        }
        updatingRanks = false
    }

    private fun constructRank(player: RanksPlayer) {
        if (uiRefreshing) return

        teamsLabel.setText(teamsLabel.text.toString() + player.teamStr + "  \n")

        var tmpName = player.name
        tmpName = tmpName.substring(0, if (tmpName.length > 23) 23 else tmpName.length)

        if (player.steamID != 0) { //Bot check
            namesTable.add(LinkLabel(tmpName, "https://steamcommunity.com/profiles/%5BU:1:" + player.steamID + "%5B/")).height(20f).left().row()
        } else {
            namesTable.add(tmpName).height(20f).left().row()
        }
        ranksLabel.setText(ranksLabel.text.toString() + player.rank + "  \n")
        killsLabel.setText(killsLabel.text.toString() + player.kills + "  \n")
        deathsLabel.setText(deathsLabel.text.toString() + player.deaths + "  \n")
        kdLabel.setText(kdLabel.text.toString() + player.KD + "  \n")
        winsLabel.setText(winsLabel.text.toString() + player.wins + "  \n")
        moneyLabel.setText(moneyLabel.text.toString() + player.money + "  \n")
    }
}