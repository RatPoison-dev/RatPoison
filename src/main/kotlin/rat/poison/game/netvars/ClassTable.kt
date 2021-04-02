package rat.poison.game.netvars

import org.jire.arrowhead.Addressed
import rat.poison.game.CSGO.csgoEXE
import rat.poison.utils.extensions.readable
import rat.poison.utils.extensions.toNetVarString
import rat.poison.utils.extensions.uint
import kotlin.LazyThreadSafetyMode.NONE

private val byteArray = ThreadLocal.withInitial { ByteArray(64) }

fun nameByNetvarTable(address: Long): String {
	val bytes = byteArray.get()

	val memoryAddress = csgoEXE.uint(address + 12)
	val memory = csgoEXE.read(memoryAddress, bytes.size)!!
	memory.read(0, bytes, 0, bytes.size)

	return bytes.toNetVarString()
}

internal class ClassTable(override var address: Long = -1L, var offset: Long = 16) : Addressed {

	val name by lazy(NONE) {
		val bytes = ByteArray(64)

		val memoryAddress = csgoEXE.uint(address + 12)
		val memory = csgoEXE.read(memoryAddress, bytes.size)!!
		memory.read(0, bytes, 0, bytes.size)

		bytes.toNetVarString()
	}

	fun set(address: Long): ClassTable {
		this.address = address
		return this
	}

	val propCount by lazy(NONE) { csgoEXE.int(address + 4) }

	fun propForID(id: Int) = csgoEXE.uint(address) + id * 60

	fun readable() = csgoEXE.read(address, offset.toInt()).readable()
}