package rat.poison.utils.common

import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2ObjectMaps
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import rat.poison.game.Color
import rat.poison.utils.EfficientSettings

private val sbBuilder = StringBuilder()
open class Settings {

    val savedValues: Object2ObjectMap<String, String> =
            Object2ObjectMaps.synchronize(Object2ObjectOpenHashMap())

    operator fun get(key: String) = savedValues[key] ?: ""

    operator fun set(key: String, value: Any): Any? {
        sbBuilder.clear().append(value)
        val r = savedValues.put(key, sbBuilder.toString())
        efficient.update(key)
        return r
    }

    val efficient = EfficientSettings(this)

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

    val int = XInt()

    inner class XInt {
        operator fun get(key: String): Int = efficient[key]
    }

}