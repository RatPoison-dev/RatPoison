package rat.poison.utils

import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap

// Jire is 200 IQ

class EfficientSettings(val settings: Settings) {
	
	val types = Array<Object2ObjectMap<String, Any>>(EfficientSettingType.values.size) { Object2ObjectOpenHashMap() }
	
	inline operator fun <reified T> get(key: String): T {
		val typeIndex = EfficientSettingType.typeIndex(T::class)
		val type = EfficientSettingType.values[typeIndex]
		return get(key, type)
	}
	
	@Suppress("UNCHECKED_CAST")
	operator fun <T> get(key: String, type: EfficientSettingType): T {
		val map = types[type.ordinal]
		val cached = map[key]
		if (cached != null) return cached as T
		
		val settingsStr = settings[key]
		val value = type.convert.invoke(settingsStr)
		map[key] = value
		return value as? T ?: throw UnsupportedOperationException("Couldn't get key \"$key\" type=$type")
	}
	
	operator fun set(key: String, value: Any) {
		val typeIndex = EfficientSettingType.typeIndex(value::class)
		val map = types[typeIndex]
		map[key] = value
	}
	
}