package rat.poison.utils

import rat.poison.utils.common.Settings

// Jire is 200 IQ

class EfficientSettings(val settings: Settings) {

    inline operator fun <reified T> get(key: String): T {
        val typeIndex = EfficientSettingType.typeIndex(T::class)
        if (typeIndex < 0) throw IllegalArgumentException("Can't determine type index for kClass: ${T::class}")
        val type = EfficientSettingType.values[typeIndex]
        return get(key, type)
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T> get(key: String, type: EfficientSettingType): T {
        val map = type.map
        val cached = map[key]
        if (cached != null) return cached as T

        val settingsStr = settings[key]
        val value = type.convert.invoke(settingsStr)
        map[key] = value
        return value as? T ?: throw UnsupportedOperationException("Couldn't get key \"$key\" type=$type")
    }

    fun update(key: String) {
        for (type in EfficientSettingType.values) {
            type.map.remove(key)
        }
    }

}