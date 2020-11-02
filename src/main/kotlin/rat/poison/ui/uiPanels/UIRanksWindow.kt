package rat.poison.ui.uiPanels

import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisWindow
import rat.poison.curSettings
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.tabs.enableEspPlayerList
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import kotlin.math.round

class UIRanksWindow : VisWindow("Ranks".toLocale()) {
    private var playerName = VisLabel("")
    private var mainTable = VisTable()
    private var enableCharLimit = VisCheckBoxCustom("Char Limit", "RANKS_TAB_ENABLE_LIMIT")
    private var charLimit = VisSliderCustom("Limit", "RANKS_TAB_CHAR_LIMIT", 1F, 32F, 1F, true, 2, sliderWidth = 200F, labelWidth = 100F)
    private var enablePlayerEsp = VisCheckBoxCustom("Player ESP", "PLAYER_ESP")

    //customize displayable things
    private var displayTeam = VisCheckBoxCustom("Team", "RANKS_TAB_DISPLAY_TEAM")
    private var displayName = VisCheckBoxCustom("Name", "RANKS_TAB_DISPLAY_NAME")
    private var displayRank = VisCheckBoxCustom("Rank", "RANKS_TAB_DISPLAY_RANK")
    private var displayKills = VisCheckBoxCustom("Kills", "RANKS_TAB_DISPLAY_KILLS")
    private var displayDeaths = VisCheckBoxCustom("Deaths", "RANKS_TAB_DISPLAY_DEATHS")
    private var displayKD = VisCheckBoxCustom("K/D", "RANKS_TAB_DISPLAY_KD")
    private var displayWins = VisCheckBoxCustom("Wins", "RANKS_TAB_DISPLAY_WINS")
    private var displayMoney = VisCheckBoxCustom("Money", "RANKS_TAB_DISPLAY_MONEY")

    private val menuAlphaSlider = VisSlider(0.5F, 1F, 0.05F, false)
    init {
        padLeft(25F)
        padRight(25F)
        buildTable()
        menuAlphaSlider.value = 1F
        menuAlphaSlider.changed { _, _ ->
            val alp = (round(menuAlphaSlider.value * 100F) / 100F)
            changeAlpha(alp)

            true
        }

        displayTeam.changed {_, _ ->
            if (displayTeam.isChecked) { ranksTab.rebuildTable() }
            else { ranksTab.ranksListTable.removeActor(ranksTab.teamsLabel) }
        }

        displayName.changed {_, _ ->
            if (displayName.isChecked) { ranksTab.rebuildTable() }
            else { ranksTab.ranksListTable.removeActor(ranksTab.namesTable); ranksTab.ranksListTable.removeActor(ranksTab.namesLabel) }
        }

        displayRank.changed {_, _ ->
            if (displayRank.isChecked) { ranksTab.rebuildTable() }
            else { ranksTab.ranksListTable.removeActor(ranksTab.ranksLabel) }
        }

        displayKills.changed {_, _ ->
            if (displayKills.isChecked) { ranksTab.rebuildTable() }
            else { ranksTab.ranksListTable.removeActor(ranksTab.killsLabel) }
        }

        displayDeaths.changed {_, _ ->
            if (displayDeaths.isChecked) { ranksTab.rebuildTable() }
            else { ranksTab.ranksListTable.removeActor(ranksTab.deathsLabel) }
        }

        displayKD.changed {_, _ ->
            if (displayKD.isChecked) { ranksTab.rebuildTable() }
            else { ranksTab.ranksListTable.removeActor(ranksTab.kdLabel) }
        }

        displayWins.changed {_, _ ->
            if (displayWins.isChecked) { ranksTab.rebuildTable() }
            else { ranksTab.ranksListTable.removeActor(ranksTab.winsLabel) }
        }

        displayMoney.changed {_, _ ->
            if (displayMoney.isChecked) { ranksTab.rebuildTable() }
            else { ranksTab.ranksListTable.removeActor(ranksTab.moneyLabel) }
        }



        mainTable.add(menuAlphaSlider).width(300F)
        add(mainTable).top().left()

        pack()

        setSize(325F, 400F)
        setPosition(curSettings["RANKS_X"].toFloat(), curSettings["RANKS_Y"].toFloat())
        color.a = curSettings["RANKS_ALPHA"].toFloat()
        isResizable = false
    }
    private fun buildTable() {
        defaults().left()
        align(Align.topLeft)
        mainTable.add(enableCharLimit).left().row()
        mainTable.add(charLimit).left().row()
        mainTable.add(displayTeam).left().row()
        mainTable.add(displayName).left().row()
        mainTable.add(displayRank).left().row()
        mainTable.add(displayKills).left().row()
        mainTable.add(displayDeaths).left().row()
        mainTable.add(displayKD).left().row()
        mainTable.add(displayWins).left().row()
        mainTable.add(displayMoney).left().row()


    }
    fun changeAlpha(alpha: Float) {
        color.a = alpha
    }
    fun bindToUser(name: String, steamID: Int) {
        reset()
        mainTable.reset()
        buildTable()
        mainTable.addSeparator().width(325F).row()
        playerName.setText(name)
        mainTable.add(playerName).left().row()
        mainTable.add(enablePlayerEsp).left().row()
        enablePlayerEsp.isChecked = steamID in enableEspPlayerList
        enablePlayerEsp.changed {_, _ ->
            if (enableEspPlayerList.contains(steamID)) {
                enableEspPlayerList.remove(steamID)
            }
            else {
                enableEspPlayerList.add(steamID)
            }
            return@changed true
        }
        mainTable.add(menuAlphaSlider).width(300F)

        add(mainTable).left()
    }
    fun update() {
        enableCharLimit.update()
        charLimit.update()
        enablePlayerEsp.update()
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