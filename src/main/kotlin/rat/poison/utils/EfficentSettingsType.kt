
package rat.poison.utils

import it.unimi.dsi.fastutil.objects.Object2IntMap
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
import rat.poison.game.Color
import rat.poison.utils.generalUtil.*
import rat.poison.utils.maps.StringToAnyMap
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

    val map = StringToAnyMap<Any>()

    companion object {
        val values = values()
        private val classToIndex: Object2IntMap<KClass<*>> = Object2IntOpenHashMap()

        init {
            for (value in values) classToIndex[value.kClass] = value.ordinal
        }

        fun typeIndex(kClass: KClass<*>) = classToIndex.getInt(kClass)//classToIndex.getOrDefault(kClass, -1)
    }

}