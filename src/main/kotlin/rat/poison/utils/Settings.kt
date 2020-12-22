package rat.poison.utils

import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import rat.poison.game.Color

open class Settings {
	
	private val savedValues: Object2ObjectMap<String, String> = Object2ObjectOpenHashMap()
	
	operator fun get(key: String): String {
		if (savedValues.containsKey(key)) {
			return savedValues[key].toString()
		}
		return ""
	}
	
	val efficient = EfficientSettings(this)
	
	//inline operator fun <reified T> invoke(key: String): T = efficient[key]
	
	// Kotlin compiler bug so can't use this yet
	// https://youtrack.jetbrains.com/issue/KT-43923
	
	//inline operator fun <reified T> get(key: String): T = efficient[key]
	
	inner class XBool {
		operator fun get(key: String): Boolean = efficient[key]
	}
	val bool = XBool()
	
	inner class XDouble {
		operator fun get(key: String): Double = efficient[key]
	}
	val double = XDouble()
	
	inner class XFloat {
		operator fun get(key: String): Float = efficient[key]
	}
	val float = XFloat()
	
	inner class XColor {
		operator fun get(key: String): Color = efficient[key]
	}
	val color = XColor()
	
	inner class XColorGDX {
		operator fun get(key: String): com.badlogic.gdx.graphics.Color = efficient[key]
	}
	val colorGDX = XColorGDX()
	
	operator fun set(key: String, value: Any): Any? {
		val string = value.toString()
		val r = savedValues.put(key, string)
		val typeIndex = EfficientSettingType.typeIndex(value::class)
		if (typeIndex >= 0) {
			efficient[key] = value
		}
		return r
	}
	
}