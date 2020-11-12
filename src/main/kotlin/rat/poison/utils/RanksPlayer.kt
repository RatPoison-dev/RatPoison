package rat.poison.utils

import rat.poison.game.entity.team
import rat.poison.game.me

data class RanksPlayer(var name: String, var steamID: Int, var rank: String, var teamStr: String, var kills: String, var deaths: String, var KD: String, var wins: String, var money: Int, var team: Long, var score: Int): Comparable<RanksPlayer> {
    override fun compareTo(other: RanksPlayer): Int {
        val meTeam = me.team()
        return when (this.team == meTeam) {
            true -> when (other.team == meTeam) {
                true -> other.score - this.score
                false -> -1
            }
            false -> when (other.team == meTeam) {
                true -> 1
                false -> other.score - this.score
            }
        }
    }
}