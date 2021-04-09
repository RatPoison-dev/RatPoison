package rat.poison.utils.extensions

fun Boolean.toBitString(): String {
    return when(this)  {
        false -> "0"
        true -> "1"
    }
}