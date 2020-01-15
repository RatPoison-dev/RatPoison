package rat.poison.scripts

import com.kotcrab.vis.ui.widget.VisLabel
import rat.poison.App.haveTarget
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.forEntities
import rat.poison.game.netvars.NetVarOffsets.m_Collision
import rat.poison.game.netvars.NetVarOffsets.m_CollisionGroup
import rat.poison.game.netvars.netVar
import rat.poison.game.rankName
import rat.poison.opened
import rat.poison.ui.tabs.ranksListTable
import rat.poison.utils.every
import rat.poison.utils.extensions.roundNDecimals
import rat.poison.utils.notInGame

//Incomplete, can't test
internal fun ranks() = every(1000) { //Rebuild every second
    if (notInGame) return@every

    //var ctPlayers = "CT:\n"
    //var tPlayers = "\nT:\n"

    //Array of arrays
    //Each contains [team][name][rank][kills][deaths][k/d]
    //                                         [steam profile]

    //private var headLookPos = listOf(0.0, 0.0, 0.0)
    //private var LoL: List<List<Any>> = listOf(emptyList(), emptyList(), emptyList())

    //var ctPlayers = listOf("", "", "", "", "", "")
    //var tPlayers = listOf("", "", "", "", "", "")
    //var listOfPlayers: List<List<String>> = listOf(emptyList())
    //var listOfPlayers = arrayListOf<List<String>>()

    //Bruh -- fix later
    val ctPlayers = arrayListOf<List<String>>()
    val tPlayers = arrayListOf<List<String>>()
    //var ctPlayers: arrayListOf<List<String>> = listOf(emptyList())
    //var tPlayers: arrayListOf<List<String>> = listOf(emptyList())

    forEntities(ccsPlayer) body@{
        val entity = it.entity

        val entTeam = when(entity.team()) { 3L -> "CT" 2L -> "T" else -> "N/A" } //Bruh
        val entName = entity.name()
        val entRank = entity.rank().rankName()
        val entKills = entity.kills().toString()
        val entDeaths = entity.deaths().toString()
        val entKD = when (entDeaths) {
            "0" -> "N/A"
            else -> (entKills.toFloat()/entDeaths.toFloat()).roundNDecimals(2).toString()
        }
        //val entKD = (entKills.toFloat()/entDeaths.toFloat()).roundNDecimals(2).toString()

        //players = listOf(entTeam, entName, entRank, entKills, entDeaths, entKD)
        //listOfPlayers.add(players)

        val player = listOf(entTeam, entName, entRank, entKills, entDeaths, entKD)

        when (entTeam) { //Bruh
            "CT" -> {
                ctPlayers.add(player)
            }

            "T" -> {
                tPlayers.add(player)
            }
        }

/*        when (entity.team()) {
            3L -> { //CT Side
                if (!ctPlayers.contains(entName)) {
                    ctPlayers += "$entName: $entRank - K/D: $entKills/$entDeaths::$entKD\n"
                }
            }
            2L -> { //T Side
                tPlayers += "$entName: $entRank - K/D: $entKills/$entDeaths::$entKD\n"
            }
        }*/

        return@body false
    }

    //[team][name][rank][kills][deaths][k/d]

    if (opened && haveTarget) {
        ranksListTable.clear()

        ranksListTable.add(VisLabel("Team"))
        ranksListTable.add(VisLabel("Name"))
        ranksListTable.add(VisLabel("Rank"))
        ranksListTable.add(VisLabel("Kills"))
        ranksListTable.add(VisLabel("Deaths"))
        ranksListTable.add(VisLabel("K/D")).row()


        //b r u h
        ctPlayers.forEachIndexed { _, ent->
            ent.forEachIndexed { j, str->
                ranksListTable.add(VisLabel(str))
            }
            ranksListTable.row()
        }
        ranksListTable.add(VisLabel()).row()

        tPlayers.forEachIndexed { _, ent->
            ent.forEachIndexed { j, str->
                ranksListTable.add(VisLabel(str))
            }
            ranksListTable.row()
        }

        //ranksListTable.add
        //ranksListText.setText("WIP - Comp Only\n\n$ctPlayers$tPlayers")
    }
}