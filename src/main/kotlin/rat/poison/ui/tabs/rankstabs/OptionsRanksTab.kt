package rat.poison.ui.tabs.rankstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.ui.changed
import rat.poison.ui.tabs.mainRanksTab
import rat.poison.ui.tabs.optionsRanksTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class OptionsRanksTab : Tab(false, false) {
    private val table = VisTable()
    var displayTeam = VisCheckBoxCustom("Team", "RANKS_TAB_DISPLAY_TEAM")
    var displayName = VisCheckBoxCustom("Name", "RANKS_TAB_DISPLAY_NAME")
    var displayRank = VisCheckBoxCustom("Rank", "RANKS_TAB_DISPLAY_RANK")
    var displayKills = VisCheckBoxCustom("Kills", "RANKS_TAB_DISPLAY_KILLS")
    var displayDeaths = VisCheckBoxCustom("Deaths", "RANKS_TAB_DISPLAY_DEATHS")
    var displayKD = VisCheckBoxCustom("K/D", "RANKS_TAB_DISPLAY_KD")
    var displayWins = VisCheckBoxCustom("Wins", "RANKS_TAB_DISPLAY_WINS")
    var displayMoney = VisCheckBoxCustom("Money", "RANKS_TAB_DISPLAY_MONEY")
    var enableCharLimit = VisCheckBoxCustom("Char Limit", "RANKS_TAB_ENABLE_LIMIT")
    var charLimit = VisSliderCustom("Limit", "RANKS_TAB_CHAR_LIMIT", 1F, 32F, 1F, true, 2, sliderWidth = 200F, labelWidth = 100F)
    init {

        table.padLeft(25F)
        table.padRight(25F)

        table.add(enableCharLimit).left().row()
        table.add(charLimit).left().row()
        table.add(displayTeam).left().row()
        table.add(displayName).left().row()
        table.add(displayRank).left().row()
        table.add(displayKills).left().row()
        table.add(displayDeaths).left().row()
        table.add(displayKD).left().row()
        table.add(displayWins).left().row()
        table.add(displayMoney).left().row()

        displayTeam.changed {_, _ ->
            if (displayTeam.isChecked) { mainRanksTab.rebuildTable() }
            else { mainRanksTab.ranksListTable.removeActor(mainRanksTab.teamsLabel) }
        }

        displayName.changed {_, _ ->
            if (displayName.isChecked) { mainRanksTab.rebuildTable() }
            else { mainRanksTab.ranksListTable.removeActor(mainRanksTab.namesTable); mainRanksTab.ranksListTable.removeActor(mainRanksTab.namesLabel) }
        }

        displayRank.changed {_, _ ->
            if (displayRank.isChecked) { mainRanksTab.rebuildTable() }
            else { mainRanksTab.ranksListTable.removeActor(mainRanksTab.ranksLabel) }
        }

        displayKills.changed {_, _ ->
            if (displayKills.isChecked) { mainRanksTab.rebuildTable() }
            else { mainRanksTab.ranksListTable.removeActor(mainRanksTab.killsLabel) }
        }

        displayDeaths.changed {_, _ ->
            if (displayDeaths.isChecked) { mainRanksTab.rebuildTable() }
            else { mainRanksTab.ranksListTable.removeActor(mainRanksTab.deathsLabel) }
        }

        displayKD.changed {_, _ ->
            if (displayKD.isChecked) { mainRanksTab.rebuildTable() }
            else { mainRanksTab.ranksListTable.removeActor(mainRanksTab.kdLabel) }
        }

        displayWins.changed {_, _ ->
            if (displayWins.isChecked) { mainRanksTab.rebuildTable() }
            else { mainRanksTab.ranksListTable.removeActor(mainRanksTab.winsLabel) }
        }

        displayMoney.changed {_, _ ->
            if (displayMoney.isChecked) { mainRanksTab.rebuildTable() }
            else { mainRanksTab.ranksListTable.removeActor(mainRanksTab.moneyLabel) }
        }
    }
    override fun getTabTitle(): String {
        return "Options"
    }

    override fun getContentTable(): Table {
        return table
    }
}

fun optionsRanksTabUpdate() {
    optionsRanksTab.apply {
        enableCharLimit.update()
        charLimit.update()
        displayTeam.update()
        displayMoney.update()
        displayDeaths.update()
        displayKD.update()
        displayKills.update()
        displayName.update()
        displayRank.update()
        displayWins.update()
    }
}