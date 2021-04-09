package rat.poison.game.netvars

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.offsets.ClientOffsets.dwFirstClass
import rat.poison.utils.extensions.readable
import rat.poison.utils.extensions.uint

object NetVars {

	internal val map = Object2ObjectOpenHashMap<StringBuilder, ClassOffset>(20_000) // Cover us for a while with 20K

	private fun scanTable( table: Long, offset: Long, name: String) {
		for (i in 0 until csgoEXE.int(table + 4)) { //propCount
			val propAddress = csgoEXE.uint(table) + i * 60
			val propName = propName(propAddress)
			val propOffset = offset + csgoEXE.uint(propAddress + 0x2C)
			if (Character.isDigit(propName[0])) continue

			if (!propName.contains("baseclass")) {
				val netVar = ClassOffset(name, propName, propOffset)
				map[hashClassAndVar(name, propName)] = netVar

				//println(netVar) //Quick dumper
			}

			val child = csgoEXE.uint(propAddress + 0x28)
			if (0L != child) scanTable(child, propOffset, name)
		}
	}

	fun load() {
		map.clear() // for reloads

		var clientClass = dwFirstClass
		while (csgoEXE.read(clientClass, 40).readable()) {
			val table = csgoEXE.uint(clientClass + 12) // read table
			val name = nameByNetvarTable(table)
			if (csgoEXE.read(table, 40).readable()) scanTable(table, 0, name)
			clientClass = csgoEXE.uint(clientClass + 16) //next
		}
	}
	private val classNameToStringBuilderArrayMap = Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, StringBuilder>>()
	internal fun hashClassAndVar(className: String, varName: String): StringBuilder {
		var getClass = classNameToStringBuilderArrayMap[className]
		if (getClass == null) {
			val builtMap = Object2ObjectOpenHashMap<String, StringBuilder>()
			classNameToStringBuilderArrayMap[className] = builtMap
			getClass = builtMap
		}
		var getSb = getClass[varName]
		if (getSb == null) {
			val tmpSb = StringBuilder()
			tmpSb.append(className)
			tmpSb.append(";")
			tmpSb.append(varName)
			getClass[varName] = tmpSb
			getSb = tmpSb
		}
		return getSb
	}
}