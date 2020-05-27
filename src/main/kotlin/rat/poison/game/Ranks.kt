package rat.poison.game

enum class Ranks(val id: Int, val str: String = "") {
    NONE(0, "N/A"),
    S1(1, "S1"),
    S2(2, "S2"),
    S3(3, "S3"),
    S4(4, "S4"),
    SE(5, "SE"),
    SEM(6, "SEM"),
    G1(7, "GN1"),
    G2(8, "GN2"),
    G3(9, "GN3"),
    GNM(10, "GNM"),
    MG1(11, "MG1"),
    MG2(12, "MG2"),
    MGE(13, "MGE"),
    DMG(14, "DMG"),
    LE(15, "LE"),
    LEM(16, "LEM"),
    SMFC(17, "SMFC"),
    GE(18, "GE");

    companion object {
        private val cachedValues = values()
        operator fun get(id: Int) = cachedValues.firstOrNull { it.id == id } ?: NONE
    }
}

fun Int.rankName(): String {
    return Ranks[this].str
}