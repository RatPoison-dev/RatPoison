package rat.poison.scripts

import com.kotcrab.vis.ui.widget.VisLabel
import rat.poison.App.haveTarget
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.forEntities
import rat.poison.game.rankName
import rat.poison.opened
import rat.poison.ui.tabs.ranksListTable
import rat.poison.utils.every
import rat.poison.utils.extensions.roundNDecimals
import rat.poison.utils.notInGame

internal fun ranks() = every(1000) { //Rebuild every second
    if (notInGame) return@every

    //Bruh -- fix later
    val ctPlayers = arrayListOf<List<String>>()
    val tPlayers = arrayListOf<List<String>>()

    forEntities(ccsPlayer) body@{
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
        return@body false
    }

    if (opened && haveTarget) {
        ranksListTable.clear()

        ranksListTable.add(VisLabel("Team"))
        ranksListTable.add(VisLabel("Name"))
        ranksListTable.add(VisLabel("Rank"))
        ranksListTable.add(VisLabel("Kills"))
        ranksListTable.add(VisLabel("Deaths"))
        ranksListTable.add(VisLabel("K/D")).row()

        ctPlayers.forEachIndexed { _, ent->
            ent.forEachIndexed { _, str->
                ranksListTable.add(VisLabel(str))
            }
            ranksListTable.row()
        }
        ranksListTable.add(VisLabel()).row()

        tPlayers.forEachIndexed { _, ent->
            ent.forEachIndexed { _, str->
                ranksListTable.add(VisLabel(str))
            }
            ranksListTable.row()
        }
    }
}
