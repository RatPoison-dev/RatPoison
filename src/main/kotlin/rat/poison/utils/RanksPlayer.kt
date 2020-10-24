package rat.poison.utils

data class RanksPlayer(var name: String, var steamID: String, var rank: String, var teamStr: String, var kills: String, var deaths: String, var KD: String, var wins: String, var money: Int, var team: Long, var score: Int) {}

//less then
//me and the boys looking for beans at 2am
fun lt(team: Long, self: RanksPlayer, y: RanksPlayer): Boolean {
    if (self.team == team) {
        return if (y.team == team) {
            when (y.score > self.score) {
                true -> false
                false -> true
            }
        }
        else {
            true
        }
    }
    else {
        return if (y.team == team) {
            false
        }
        else {
            when (y.score > self.score) {
                true -> false
                false -> true
            }
        }
    }
}

fun sortRanksPlayerList(team: Long, arr: MutableList<RanksPlayer>): MutableList<RanksPlayer>{
    var swap = true
    while(swap){
        swap = false
        for(i in 0 until arr.size-1){
            if(lt(team, arr[i+1] ,arr[i])){
                val temp = arr[i]
                arr[i] = arr[i+1]
                arr[i + 1] = temp

                swap = true
            }
        }
    }
    return arr
}