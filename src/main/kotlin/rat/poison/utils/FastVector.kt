package rat.poison.utils

import java.util.concurrent.ThreadLocalRandom
import kotlin.math.abs
import kotlin.math.roundToLong

inline class FastVector(val value: Long) {
	constructor(x: Float = 0F, y: Float = 0F, z: Float = 0F) : this(vectorLong(x, y, z))
	constructor(vector: Vector) : this(vector.x, vector.y, vector.z)
	
	val x: Float
		get() = ((value ushr 42).toFloat() / FAST_VECTOR_PRECISION_DIV) - FAST_VECTOR_OFFSET
	val y: Float
		get() = (((value ushr 21) and FAST_VECTOR_BITMASK).toFloat() / FAST_VECTOR_PRECISION_DIV) - FAST_VECTOR_OFFSET
	val z: Float
		get() = ((value and FAST_VECTOR_BITMASK).toFloat() / FAST_VECTOR_PRECISION_DIV) - FAST_VECTOR_OFFSET
	
	fun set(x: Float, y: Float, z: Float = 0F) = Vector(x, y, z)
	fun x(x: Float) = Vector(x, y, z)
	fun y(y: Float) = Vector(x, y, z)
	fun z(z: Float) = Vector(x, y, z)
	
	fun invalid() = x == 0F && y == 0F && z == 0F
	fun valid() = !invalid()
	
	fun isValid() = true
	
	fun normalize() = run {
		var x = x
		var y = y
		
		if (x != x) x = 0F
		if (y != y) y = 0F
		
		if (x > 89) x = 89F
		if (x < -89) x = -89F
		
		while (y > 180) y -= 360
		while (y <= -180) y += 360
		
		if (y > 180) y = 180F
		if (y < -180F) y = -180F
		
		set(x, y, z)
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

const val FAST_VECTOR_OFFSET = 9999F
const val FAST_VECTOR_PRECISION_MULT = 100.0
const val FAST_VECTOR_PRECISION_DIV = FAST_VECTOR_PRECISION_MULT.toFloat()
const val FAST_VECTOR_BITMASK = 0x1FFFFFL

fun vectorLong(x: Float = 0F, y: Float = 0F, z: Float = 0F): Long {
	val x2 = ((x + FAST_VECTOR_OFFSET) * FAST_VECTOR_PRECISION_MULT).roundToLong() and FAST_VECTOR_BITMASK
	val y2 = ((y + FAST_VECTOR_OFFSET) * FAST_VECTOR_PRECISION_MULT).roundToLong() and FAST_VECTOR_BITMASK
	val z2 = ((z + FAST_VECTOR_OFFSET) * FAST_VECTOR_PRECISION_MULT).roundToLong() and FAST_VECTOR_BITMASK
	
	var vector = x2 shl 42
	vector = vector or (y2 shl 21)
	vector = vector or z2
	return vector
}

fun main() {
	val bound = 5000
	val tlr = ThreadLocalRandom.current()
	for (i in 0..1000000) {
		val x = tlr.nextInt(bound) + tlr.nextFloat()
		val y = tlr.nextInt(bound) + tlr.nextFloat()
		val z = tlr.nextInt(bound) + tlr.nextFloat()
		
		val v = FastVector(x, y, z)
		val vx = v.x
		val vy = v.y
		val vz = v.z
		if (abs(vx - x) > 0.1 || abs(vy - y) > 0.1 || abs(vz - z) > 0.1) {
			println("FAILED x $vx should be $x, y $vy should be $y, z $vz should be $z")
		}
	}
	
	val v = FastVector(1000.1337F, -69F, 123.123F)
	println("${v.x}, ${v.y}, ${v.z}")
}