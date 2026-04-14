package rat.poison.utils

fun log2(i: Short): Short {
    return (31 - Integer.numberOfLeadingZeros(i.toInt())).toShort()
}