package rat.poison.utils.common

import rat.poison.game.Color
import rat.poison.utils.EfficientSettings
import rat.poison.utils.maps.StringToAnyMap

private val sbBuilder = StringBuilder()
open class Settings {

    val savedValues = StringToAnyMap<String>()

    operator fun get(key: String) = savedValues[key] ?: ""
    operator fun get(key: StringBuilder) = savedValues[key] ?: ""

    operator fun set(key: String, value: Any): Any? {
        sbBuilder.clear().append(value)
        val r = savedValues.put(key, sbBuilder.toString())
        efficient.update(key)
        return r
    }
    operator fun set(key: StringBuilder, value: Any): Any? {
        sbBuilder.clear().append(value)
        val r = savedValues.put(key, sbBuilder.toString())
        efficient.update(key)
        return r
    }

    operator fun set(key: String, value: String): Any? {
        val r = savedValues.put(key, value)
        efficient.update(key)
        return r
    }
    operator fun set(key: StringBuilder, value: String): Any? {
        val r = savedValues.put(key, value)
        efficient.update(key)
        return r
    }

    val efficient = EfficientSettings(this)

    // Kotlin compiler bug so can't use this yet
    // https://youtrack.jetbrains.com/issue/KT-43923

    //inline operator fun <reified T> get(key: String): T = efficient[key]

    inner class XBool {
        operator fun get(key: String): Boolean = efficient[key]
        operator fun get(key: StringBuilder): Boolean = efficient[key]
    }

    val bool = XBool()

    inner class XDouble {
        operator fun get(key: String): Double = efficient[key]
        operator fun get(key: StringBuilder): Double = efficient[key]
    }

    val double = XDouble()

    inner class XFloat {
        operator fun get(key: String): Float = efficient[key]
        operator fun get(key: StringBuilder): Float = efficient[key]
    }

    val float = XFloat()

    inner class XColor {
        operator fun get(key: String): Color = efficient[key]
        operator fun get(key: StringBuilder): Color = efficient[key]
    }

    val color = XColor()

    inner class XColorGDX {
        operator fun get(key: String): com.badlogic.gdx.graphics.Color = efficient[key]
        operator fun get(key: StringBuilder): com.badlogic.gdx.graphics.Color = efficient[key]
    }

    val colorGDX = XColorGDX()

    val int = XInt()

    inner class XInt {
        operator fun get(key: String): Int = efficient[key]
        operator fun get(key: StringBuilder): Int = efficient[key]
    }

}