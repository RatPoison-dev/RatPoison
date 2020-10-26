package rat.poison.utils

import rat.poison.game.entity.team
import rat.poison.game.me

//me and the boys looking for beans at 2am
data class RanksPlayer(var name: String, var steamID: String, var rank: String, var teamStr: String, var kills: String, var deaths: String, var KD: String, var wins: String, var money: Int, var team: Long, var score: Int): Comparable<RanksPlayer> {
    override fun compareTo(y: RanksPlayer): Int {
        val meTeam = me.team()
        return when (this.team == meTeam) {
            true -> when (y.team == meTeam) {
                true -> y.score - this.score
                false -> -1
            }
            false -> when (y.team == meTeam) {
                true -> 1
                false -> y.score - this.score
            }
        }
    }
}