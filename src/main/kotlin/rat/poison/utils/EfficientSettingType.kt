package rat.poison.utils

import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import rat.poison.game.Color
import rat.poison.utils.generalUtil.*
import kotlin.reflect.KClass
import com.badlogic.gdx.graphics.Color as ColorGDX

// Jire is 200 IQ

enum class EfficientSettingType(val kClass: KClass<*>, val convert: (Any) -> Any) {
	
	STRING(String::class, { it }),
	INT(Int::class, { it.cToInt() }),
	LONG(Long::class, { it.cToLong() }),
	FLOAT(Float::class, { it.cToFloat() }),
	DOUBLE(Double::class, { it.cToDouble() }),
	BOOLEAN(Boolean::class, { it.strToBool() }),
	COLOR(Color::class, { it.strToColor() }),
	COLOR_GDX(ColorGDX::class, { it.strToColorGDX() });
	
	val map: Object2ObjectMap<String, Any> = Object2ObjectMaps.synchronize(Object2ObjectOpenHashMap())
	
	companion object {
		val values = values()
		
		fun typeIndex(kClass: KClass<*>): Int {
			for (value in values) if (value.kClass == kClass) return value.ordinal
			//throw IllegalArgumentException("Can't determine type index for kClass: $kClass")
			return -1
		}
	}
	
}