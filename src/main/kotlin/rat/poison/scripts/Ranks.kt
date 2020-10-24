package rat.poison.scripts

import org.apache.commons.lang3.StringUtils
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.rankName
import rat.poison.overlay.App.haveTarget
import rat.poison.overlay.opened
import rat.poison.ui.uiPanels.ranksTab
import rat.poison.ui.uiRefreshing
import rat.poison.utils.RanksPlayer
import rat.poison.utils.every
import rat.poison.utils.extensions.roundNDecimals
import rat.poison.utils.sortRanksPlayerList

var playerList = mutableListOf<RanksPlayer>()

fun ranks() = every(1000, true, inGameCheck = true) { //Rebuild every second
    if (!opened || !haveTarget) return@every

    //Bruh -- fix later
    playerList.clear()

    forEntities(EntityType.CCSPlayer) {
        val entity = it.entity

        if (entity.hltv()) return@forEntities

        val tmpTeam = when (entity.team()) {
            3L -> "CT"
            2L -> "T"
            else -> "N/A"
        }
        val entTeam = entity.team()

        val entName = entity.name()
        val entRank = entity.rank().rankName()
        val entKills = entity.kills().toString()
        val entDeaths = entity.deaths().toString()
        val entMoney = entity.money()
        val entScore = entity.score()
        val entKD = when (entDeaths) {
            "0" -> "N/A"
            else -> (entKills.toFloat() / entDeaths.toFloat()).roundNDecimals(2).toString()
        }
        val entWins = entity.wins().toString()

        var steamID = 0

        try {
            val entSteam = entity.steamID()
            if (entSteam != "BOT" && entSteam.isNotEmpty() && StringUtils.isNumeric(entSteam.split(":")[2])) {
                steamID = (entSteam.split(":")[2].toInt() * 2) + entSteam.split(":")[1].toInt()
            }
        } catch (e: Exception) { }
        playerList.add(RanksPlayer(name=entName, team = entTeam, steamID = steamID.toString(), teamStr = tmpTeam, rank=entRank, kills = entKills, deaths = entDeaths, KD = entKD, wins = entWins, money = entMoney, score = entScore))
        //playerList = sortRanksPlayerList(me.team(), playerList)
    }
    if (!uiRefreshing) {
        ranksTab.updateRanks()
    }
}
