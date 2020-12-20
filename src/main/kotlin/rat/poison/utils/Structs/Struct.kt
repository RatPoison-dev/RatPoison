package rat.poison.utils.Structs

import com.sun.jna.Structure

abstract class Struct : Structure() {
	
	override fun getFieldOrder(): List<String> = javaClass.declaredFields.map { it.name }
	
}