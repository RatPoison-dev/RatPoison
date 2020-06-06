package rat.poison.scripts

import com.kotcrab.vis.ui.widget.VisLabel
import rat.poison.App.haveTarget
import rat.poison.curLocalization
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.forEntities
import rat.poison.game.rankName
import rat.poison.opened
import rat.poison.ui.uiPanels.ranksTab
import rat.poison.utils.every
import rat.poison.utils.extensions.roundNDecimals
import rat.poison.utils.notInGame

//var ctPlayers = arrayListOf<List<String>>()
//var tPlayers = arrayListOf<List<String>>()
var teamList = mutableListOf<String>()
var nameList = mutableListOf<String>()
var rankList = mutableListOf<String>()
var killsList = mutableListOf<String>()
var deathsList = mutableListOf<String>()
var KDList = mutableListOf<String>()
var winsList = mutableListOf<String>()

fun ranks() = every(1000, true) { //Rebuild every second
    if (notInGame || !opened || !haveTarget) return@every

    //Bruh -- fix later
    teamList.clear()
    nameList.clear()
    rankList.clear()
    killsList.clear()
    deathsList.clear()
    KDList.clear()
    winsList.clear()
    //ctPlayers.clear()
    //tPlayers.clear()

    forEntities(ccsPlayer) {
        val entity = it.entity
        if (entity.onGround()) { //Change later
            val entTeam = when (entity.team()) {
                3L -> curLocalization["CT"]
                2L -> curLocalization["T"]
                else -> curLocalization["UNDEFINED"]
            }

            val entName = entity.name()
            val entRank = entity.rank().rankName()
            val entKills = entity.kills().toString()
            val entDeaths = entity.deaths().toString()
            val entKD = when (entDeaths) {
                "0" -> curLocalization["UNDEFINED"]
                else -> (entKills.toFloat() / entDeaths.toFloat()).roundNDecimals(2).toString()
            }
            val entWins = entity.wins().toString()

            when (entTeam) { //Bruh
                curLocalization["CT"] -> {
                    teamList.add(curLocalization["CT"])
                }

                curLocalization["T"] -> {
                    teamList.add(curLocalization["T"])
                }
            }

            nameList.add(entName)
            rankList.add(entRank)
            killsList.add(entKills)
            deathsList.add(entDeaths)
            KDList.add(entKD)
            winsList.add(entWins)
        }
        false
    }

    ranksTab.updateRanks()
}
