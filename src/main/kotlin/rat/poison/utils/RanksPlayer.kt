package rat.poison.utils

import rat.poison.game.meTeam

data class RanksPlayer(var name: String = "", var steamID: Int = 0, var rank: String = "", var teamStr: String = "NIL", var kills: String = "", var deaths: String = "", var KD: String = "", var wins: String = "", var money: Int = 0, var team: Long = 0L, var score: Int = 0): Comparable<RanksPlayer> {
    override fun compareTo(other: RanksPlayer): Int {
        if (this.teamStr == "NIL") return -1
        if (other.teamStr == "NIL") return 1
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