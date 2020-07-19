package rat.poison.scripts

import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.forEntities
import rat.poison.game.rankName
import rat.poison.overlay.App.haveTarget
import rat.poison.overlay.opened
import rat.poison.toLocale
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

    forEntities(EntityType.CCSPlayer) {
        val entity = it.entity
        val entTeam = when (entity.team()) {
            3L -> "CT"
            2L -> "T"
            else -> "N/A"
        }

        val entName = entity.name()
        val entRank = entity.rank().rankName()
        val entKills = entity.kills().toString()
        val entDeaths = entity.deaths().toString()
        val entKD = when (entDeaths) {
            "0" -> "N/A"
            else -> (entKills.toFloat() / entDeaths.toFloat()).roundNDecimals(2).toString()
        }
        val entWins = entity.wins().toString()

        when (entTeam) { //Bruh
            "CT" -> {
                teamList.add("CT".toLocale())
            }

            "T" -> {
                teamList.add("T".toLocale())
            }
        }

        nameList.add(entName)
        rankList.add(entRank)
        killsList.add(entKills)
        deathsList.add(entDeaths)
        KDList.add(entKD)
        winsList.add(entWins)
    }

    ranksTab.updateRanks()
}
