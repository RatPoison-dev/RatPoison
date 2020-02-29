package rat.poison.game.netvars

import org.jire.arrowhead.Addressed
import rat.poison.game.CSGO.csgoEXE
import rat.poison.utils.extensions.readable
import rat.poison.utils.extensions.uint
import kotlin.LazyThreadSafetyMode.NONE

internal class Class(override val address: Long) : Addressed {
	
	val id by lazy(NONE) { csgoEXE.uint(address + 20) }
	
	val next by lazy(NONE) { csgoEXE.uint(address + 16) }
	
	val table by lazy(NONE) { csgoEXE.uint(address + 12) }
	
	fun readable() = csgoEXE.read(address, 40).readable()
	
}