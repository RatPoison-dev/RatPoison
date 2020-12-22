package rat.poison.utils

import rat.poison.game.Color
import rat.poison.utils.generalUtil.strToColor
import rat.poison.utils.generalUtil.strToColorGDX
import kotlin.reflect.KClass
import com.badlogic.gdx.graphics.Color as ColorGDX

// Jire is 200 IQ

enum class EfficientSettingType(val kClass: KClass<*>, val convert: (String) -> Any) {
	
	STRING(String::class, { it }),
	INT(Int::class, { it.toInt() }),
	LONG(Long::class, { it.toLong() }),
	FLOAT(Float::class, { it.toFloat() }),
	DOUBLE(Double::class, { it.toDouble() }),
	BOOLEAN(Boolean::class, { it.toBoolean() }),
	COLOR(Color::class, { it.strToColor() }),
	COLOR_GDX(ColorGDX::class, { it.strToColorGDX() });
	
	companion object {
		val values = values()
		
		fun typeIndex(kClass: KClass<*>): Int {
			for (value in values) if (value.kClass == kClass) return value.ordinal
			throw IllegalArgumentException("Can't determine type index for kClass: $kClass")
		}
		
		/* I don't use cleaner code because we need maximum speed. */
		/*fun typeIndex(kclass: KClass<*>) = when (kclass) {
			String::class -> STRING.ordinal
			Int::class -> INT.ordinal
			Long::class -> LONG.ordinal
			Float::class -> FLOAT.ordinal
			Double::class -> DOUBLE.ordinal
			Boolean::class -> BOOLEAN.ordinal
			Color::class -> COLOR.ordinal
			ColorGDX::class -> COLOR_GDX.ordinal
			else -> -1
		}*/
	}
	
}