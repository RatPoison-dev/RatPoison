package rat.poison.utils

import net.openhft.chronicle.core.OS
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.abs

inline class Vector(val value: Long) {
	constructor(x: Float = 0F, y: Float = 0F, z: Float = 0F) : this(vectorLong(x, y, z))
	
	var x
		get() = memory.readFloat(value)
		set(value) = memory.writeFloat(this.value, value)
	var y
		get() = memory.readFloat(value + 4)
		set(value) = memory.writeFloat(this.value + 4, value)
	var z
		get() = memory.readFloat(value + 8)
		set(value) = memory.writeFloat(this.value + 8, value)
	
	fun release() = memory.freeMemory(value, 12)
	
	inline fun use(crossinline use: (Vector) -> Unit) {
		try {
			use(this)
		} finally {
			release()
		}
	}
	
	fun set(x: Float, y: Float, z: Float = 0F) = apply {
		this.x = x
		this.y = y
		this.z = z
	}
	
	fun invalid() = x == 0F && y == 0F && z == 0F
	fun valid() = !invalid()
	
	fun isValid() = true
	
	fun normalize() = apply {
		if (x != x) x = 0F
		if (y != y) y = 0F
		
		if (x > 89) x = 89F
		if (x < -89) x = -89F
		
		while (y > 180) y -= 360
		while (y <= -180) y += 360
		
		if (y > 180) y = 180F
		if (y < -180F) y = -180F
	}
	
	fun distanceTo(target: Vector) = abs(x - target.x) + abs(y - target.y) + abs(z - target.z)
	
	fun dot(x: Float, y: Float, z: Float) = dot(x, y, z, this.x, this.y, this.z)
	
	fun dst2(x: Float, y: Float, z: Float): Float {
		val a = x - this.x
		val b = y - this.y
		val c = z - this.z
		return a * a + b * b + c * c
	}
}

fun dot(x: Float, y: Float, z: Float, tx: Float, ty: Float, tz: Float): Float = tx * x + ty * y + tz * z

private val memory = OS.memory()

fun vectorLong(x: Float = 0F, y: Float = 0F, z: Float = 0F): Long {
	val address = memory.allocate(12)
	memory.writeFloat(address, x)
	memory.writeFloat(address + 4, y)
	memory.writeFloat(address + 8, z)
	return address
}

typealias Angle = Vector

fun angle(x: Float = 0F, y: Float = 0F): Angle = Vector(x, y)

fun vector(x: Float = 0F, y: Float = 0F, z: Float = 0F): Vector = Vector(vectorLong(x, y, z))

fun main() {
	val tlr = ThreadLocalRandom.current()
	for (i in 0..100000) {
		val x = tlr.nextFloat()
		val y = tlr.nextFloat()
		val z = tlr.nextFloat()
		
		val v = vector(x, y, z)
		if (abs(v.x - x) > 0.001 || abs(v.y - y) > 0.001 || abs(v.z - z) > 0.001) {
			println("FAILED x $x should be ${v.x}, y $y should be ${v.y}, z $z should be ${v.z}")
		}
	}
}