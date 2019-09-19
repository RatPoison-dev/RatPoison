package rat.poison.scripts

import rat.poison.App.haveTarget
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.entity.name
import rat.poison.game.entity.rank
import rat.poison.game.entity.team
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.rankName
import rat.poison.opened
import rat.poison.ui.tabs.ranksListText
import rat.poison.utils.every
import rat.poison.utils.notInGame

//Incomplete, can't test
internal fun ranks() = every(1000) { //Rebuild every 30 seconds
    if (notInGame) return@every

    var ctPlayers = "CT:\n"
    var tPlayers = "\nT:\n"

    forEntities(ccsPlayer) body@{
        val entity = it.entity

        val entName = entity.name()
        val entRank = entity.rank().rankName()

        when (entity.team()) {
            3L -> ctPlayers += "$entName: $entRank\n"
            2L -> tPlayers += "$entName: $entRank\n"
        }

        return@body false
    }

    if (opened && haveTarget) {
        ranksListText.setText("WIP - Comp Only\n\n$ctPlayers$tPlayers")
    }
}