package rat.poison.scripts

import rat.poison.App.haveTarget
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.forEntities
import rat.poison.game.rankName
import rat.poison.opened
import rat.poison.ui.ranksTab
import rat.poison.utils.every
import rat.poison.utils.extensions.roundNDecimals
import rat.poison.utils.notInGame

var ctPlayers = arrayListOf<List<String>>()
var tPlayers = arrayListOf<List<String>>()

fun ranks() = every(1000) { //Rebuild every second
    if (notInGame || !opened || !haveTarget) return@every

    //Bruh -- fix later
    ctPlayers.clear()
    tPlayers.clear()

    forEntities(ccsPlayer) {
        val entity = it.entity

        if (entity.onGround()) { //Change later
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

            val player = listOf(entTeam, entName, entRank, entKills, entDeaths, entKD)

            when (entTeam) { //Bruh
                "CT" -> {
                    ctPlayers.add(player)
                }

                "T" -> {
                    tPlayers.add(player)
                }
            }
        }
        false
    }

    ranksTab.updateRanks()
}
