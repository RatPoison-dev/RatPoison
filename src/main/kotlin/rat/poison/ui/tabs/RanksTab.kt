package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.scripts.playerList
import rat.poison.toLocale
import rat.poison.ui.uiHelpers.ranksTab.RanksLinkLabelCustom
import rat.poison.ui.uiRefreshing
import rat.poison.utils.RanksPlayer

var enableEspPlayerList = mutableListOf(0)
private var linksLabelMap = mutableMapOf<Int, RanksLinkLabelCustom>()

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
        playerList.forEach { player ->
            when (player.team) {
                3L -> constructRank(player)
                2L -> constructRank(player)
            }
        }
    }
    private fun constructRank(player: RanksPlayer) {
        if (uiRefreshing) return
        teamsLabel.setText(teamsLabel.text.toString() + player.teamStr.toLocale() + "  \n")
        var tmpName = player.name
        val steamID = player.steamID.toInt()
        if (steamID != 0) { //Bot check
            val linkLabel = linksLabelMap.getOrElse(steamID, {
                val label = RanksLinkLabelCustom(tmpName, "https://steamcommunity.com/profiles/%5BU:1:" + player.steamID + "%5B/", steamID)
                linksLabelMap[steamID] = label
                return@getOrElse label
            })
            namesTable.add(linkLabel).height(21f).left().row()
        } else {
            namesTable.add(tmpName).height(21f).left().row()
        }
        ranksLabel.setText(ranksLabel.text.toString() + player.rank + "  \n")
        killsLabel.setText(killsLabel.text.toString() + player.kills + "  \n")
        deathsLabel.setText(deathsLabel.text.toString() + player.deaths + "  \n")
        kdLabel.setText(kdLabel.text.toString() + player.KD + "  \n")
        winsLabel.setText(winsLabel.text.toString() + player.wins + "  \n")
        moneyLabel.setText(moneyLabel.text.toString() + player.money + "  \n")
    }
}