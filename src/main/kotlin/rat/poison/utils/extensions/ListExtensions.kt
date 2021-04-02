package rat.poison.utils.extensions

fun MutableList<Any>.copy(): List<Any> {
    val tmpList = mutableListOf<Any>()
    for (i in this) {
        tmpList.add(i)
    }
    return tmpList
}