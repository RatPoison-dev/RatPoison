package rat.poison.game

enum class Ranks(val id: Int, val str: String = "") {

    NONE(0, "N/A"),
    S1(1, "Silver I"),
    S2(2, "Silver II"),
    S3(3, "Silver III"),
    S4(4, "Silver IV"),
    SE(5, "Silver Elite"),
    SEM(6, "Silver Elite Master"),
    G1(7, "Gold Nova I"),
    G2(8, "Gold Nova II"),
    G3(9, "Gold Nova III"),
    GNM(10, "Gold Nova Master"),
    MG1(11, "Master Guardian I"),
    MG2(12, "Master Guardian II"),
    MGE(13, "Master Guardian Elite"),
    DMG(14, "Distinguished Master Guardian"),
    LE(15, "Legendary Eagle"),
    LEM(16, "Legendary Eagle Master"),
    SMFC(17, "Supreme Master First Class"),
    GE(18, "Global Elite");

    companion object {
        private val cachedValues = values()
        operator fun get(id: Int) = cachedValues.firstOrNull { it.id == id } ?: NONE
    }
}

fun Int.rankName(): String {
    return Ranks[this].str
}