package rat.poison.ui.uiTabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.LinkLabel
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.overlay.App.uiMenu
import rat.poison.scripts.ranksPlayerList
import rat.poison.ui.uiRefreshing
import rat.poison.ui.uiWindows.ranksTab
import rat.poison.utils.RanksPlayer
import rat.poison.utils.locale
import rat.poison.utils.saving

var updatingRanks = false

//TODO move
class VisLabelCustom(mainText: String): VisLabel(mainText.locale()) {
    override fun setText(newText: CharSequence?) {
        super.setText(newText.locale())
    }
}

class VisTextButtonCustom(mainText: String): VisTextButton(mainText.locale()) {
    override fun setText(newText: String?) {
        super.setText(newText.locale())
    }
}

class RanksTab : Tab(false, false) {
    private val table = VisTable(false)

    private var ranksListTable = VisTable(false)
    private var teamsLabel = VisLabelCustom("Team" + "  \n")

    private var namesTable = VisTable(false)
    private var namesLabel = VisLabelCustom("Name" + "  \n")

    private var ranksLabel = VisLabelCustom("Rank" + "  \n")
    private var killsLabel = VisLabelCustom("Kills" + "  \n")
    private var deathsLabel = VisLabelCustom("Deaths" + "  \n")
    private var kdLabel = VisLabelCustom("K/D" + "  \n")
    private var winsLabel = VisLabelCustom("Wins" + "  \n")
    private var moneyLabel = VisLabelCustom("Money" + "  \n")

    init {
        namesTable.add(namesLabel).top()

        ranksListTable.add(teamsLabel).top()
        ranksListTable.add(namesTable).top().padRight(4f) //Table
        ranksListTable.add(ranksLabel).top()
        ranksListTable.add(killsLabel).top()
        ranksListTable.add(deathsLabel).top()
        ranksListTable.add(kdLabel).top()
        ranksListTable.add(winsLabel).top()
        ranksListTable.add(moneyLabel).top()

        table.add(ranksListTable).left().maxWidth(500F)
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "Ranks"
    }

    fun updateRanks() {
        if (uiRefreshing || saving || updatingRanks || uiMenu.activeTab != ranksTab) return
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
        if (player.teamStr == "NIL") return

        teamsLabel.setText(teamsLabel.text.toString() + player.teamStr + "  \n")

        var tmpName = player.name
        tmpName = tmpName.substring(0, if (tmpName.length > 23) 23 else tmpName.length)

        if (player.steamID != 0) { //Bot check
            namesTable.add(LinkLabel(tmpName, "https://steamcommunity.com/profiles/%5BU:1:" + player.steamID + "%5B/")).height(19F).left().top().row()
        } else {
            namesTable.add(tmpName).height(20F).left().top().row()
        }

        ranksLabel.setText(ranksLabel.text.toString() + player.rank + "  \n")
        killsLabel.setText(killsLabel.text.toString() + player.kills + "  \n")
        deathsLabel.setText(deathsLabel.text.toString() + player.deaths + "  \n")
        kdLabel.setText(kdLabel.text.toString() + player.KD + "  \n")
        winsLabel.setText(winsLabel.text.toString() + player.wins + "  \n")
        moneyLabel.setText(moneyLabel.text.toString() + player.money + "  \n")
    }
}