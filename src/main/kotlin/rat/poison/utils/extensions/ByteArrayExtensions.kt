package rat.poison.utils.extensions

internal fun ByteArray.toNetVarString(): String {
	for (i in 0 until size) if (0.toByte() == this[i]) this[i] = 32
	return String(this).split(" ")[0].trim()
}