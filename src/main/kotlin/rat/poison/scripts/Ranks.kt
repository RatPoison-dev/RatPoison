package rat.poison.scripts

import org.apache.commons.lang3.StringUtils
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.rankName
import rat.poison.overlay.App.haveTarget
import rat.poison.overlay.opened
import rat.poison.ui.uiPanels.ranksTab
import rat.poison.ui.uiRefreshing
import rat.poison.utils.every
import rat.poison.utils.extensions.roundNDecimals

var teamList = mutableListOf<String>()
var nameList = mutableListOf<String>()
var steamIDList = mutableListOf<String>()
var rankList = mutableListOf<String>()
var killsList = mutableListOf<String>()
var deathsList = mutableListOf<String>()
var KDList = mutableListOf<String>()
var winsList = mutableListOf<String>()
var moneyList = mutableListOf<String>()

fun ranks() = every(5000, true, inGameCheck = true) { //Rebuild every second
    if (!opened || !haveTarget) return@every

    //Bruh -- fix later
    teamList.clear()
    nameList.clear()
    rankList.clear()
    killsList.clear()
    deathsList.clear()
    KDList.clear()
    winsList.clear()
    moneyList.clear()

    forEntities(EntityType.CCSPlayer) {
        val entity = it.entity

        if (entity.hltv()) return@forEntities

        val entTeam = when (entity.team()) {
            3L -> "CT"
            2L -> "T"
            else -> "N/A"
        }

        val entName = entity.name()
        val entRank = entity.rank().rankName()
        val entKills = entity.kills().toString()
        val entDeaths = entity.deaths().toString()
        val entMoney = entity.money()
        val entKD = when (entDeaths) {
            "0" -> "N/A"
            else -> (entKills.toFloat() / entDeaths.toFloat()).roundNDecimals(2).toString()
        }
        val entWins = entity.wins().toString()

        when (entTeam) { //Bruh
            "CT" -> {
                teamList.add("CT")
            }

            "T" -> {
                teamList.add("T")
            }
        }

        var steamID = 0

        try {
            val entSteam = entity.steamID()
            if (entSteam != "BOT" && entSteam.isNotEmpty() && StringUtils.isNumeric(entSteam.split(":")[2])) {
                steamID = (entSteam.split(":")[2].toInt() * 2) + entSteam.split(":")[1].toInt()
            }
        } catch (e: Exception) { }

        nameList.add(entName)
        steamIDList.add(steamID.toString())
        rankList.add(entRank)
        killsList.add(entKills)
        deathsList.add(entDeaths)
        KDList.add(entKD)
        winsList.add(entWins)
        moneyList.add(entMoney.toString())
    }

    if (!uiRefreshing) {
        ranksTab.updateRanks()
    }
}
