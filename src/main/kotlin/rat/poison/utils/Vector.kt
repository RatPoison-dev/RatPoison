package rat.poison.utils

import kotlin.math.abs

interface Vector {
	val value: Long
	
	val x: Float
	val y: Float
	val z: Float
	
	fun set(x: Float, y: Float, z: Float = this.z): Vector
	
	fun x(x: Float): Vector
	fun y(y: Float): Vector
	fun z(z: Float): Vector
	
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
		
		set(x, y)
	}
	
	fun distanceTo(target: Vector) = abs(x - target.x) + abs(y - target.y) + abs(z - target.z)
}

inline class Angle(override val value: Long) : Vector {
	constructor(x: Float = 0F, y: Float = 0F) : this(angleLong(x, y))
	
	override val x: Float
		get() = (value ushr 32).toFloat()
	override val y: Float
		get() = (value and 0xFFFF).toFloat()
	override val z: Float
		get() = 0F
	
	override fun set(x: Float, y: Float, z: Float) = Angle(x, y)
	override fun x(x: Float) = Angle(x, y)
	override fun y(y: Float) = Angle(x, y)
	override fun z(z: Float) = Angle(x, y)
	
	override fun isValid() = !(z != 0F
			|| x < -89 || x > 180
			|| y < -180 || y > 180
			|| x.isNaN() || y.isNaN() || z.isNaN())
}

fun angle(x: Float = 0F, y: Float = 0F): Angle = Angle(x, y)

fun angleLong(x: Float = 0F, y: Float = 0F): Long {
	var vector = x.toLong() shl 32
	vector = vector or y.toLong()
	return vector
}

inline class InlineVector(override val value: Long) : Vector {
	constructor(x: Float = 0F, y: Float = 0F, z: Float = 0F) : this(vectorLong(x, y, z))
	
	override val x: Float
		get() = toFloat(value ushr 32)
	override val y: Float
		get() = toFloat((value ushr 16) and 0xFFFF)
	override val z: Float
		get() = toFloat(value and 0xFFFF)
	
	override fun set(x: Float, y: Float, z: Float) = InlineVector(x, y, z)
	override fun x(x: Float) = InlineVector(x, y, z)
	override fun y(y: Float) = InlineVector(x, y, z)
	override fun z(z: Float) = InlineVector(x, y, z)
}

fun Vector(value: Long): Vector = InlineVector(value)

fun Vector(x: Float = 0F, y: Float = 0F, z: Float = 0F) = vector(x, y, z)

fun vector(x: Float = 0F, y: Float = 0F, z: Float = 0F): Vector = InlineVector(vectorLong(x, y, z))

fun vectorLong(x: Float = 0F, y: Float = 0F, z: Float = 0F): Long {
	val x2 = toHalf(x)
	val y2 = toHalf(y)
	val z2 = toHalf(z)
	
	var vector = x2 shl 32
	vector = vector or (y2 shl 16)
	vector = vector or z2
	return vector
}

fun toHalf(f: Float): Long {
	val bits = java.lang.Float.floatToRawIntBits(f)
	val s = bits ushr FP32_SIGN_SHIFT
	var e = bits ushr FP32_EXPONENT_SHIFT and FP32_SHIFTED_EXPONENT_MASK
	var m = bits and FP32_SIGNIFICAND_MASK
	var outE = 0
	var outM = 0
	if (e == 0xff) { // Infinite or NaN
		outE = 0x1f
		outM = if (m != 0) 0x200 else 0
	} else {
		e = e - FP32_EXPONENT_BIAS + EXPONENT_BIAS
		if (e >= 0x1f) { // Overflow
			outE = 0x1f
		} else if (e <= 0) { // Underflow
			if (e < -10) {
				// The absolute fp32 value is less than MIN_VALUE, flush to +/-0
			} else {
				// The fp32 value is a normalized float less than MIN_NORMAL,
				// we convert to a denorm fp16
				m = m or 0x800000
				val shift = 14 - e
				outM = m shr shift
				val lowm = m and (1 shl shift) - 1
				val hway = 1 shl shift - 1
				// if above halfway or exactly halfway and outM is odd
				if (lowm + (outM and 1) > hway) {
					// Round to nearest even
					// Can overflow into exponent bit, which surprisingly is OK.
					// This increment relies on the +outM in the return statement below
					outM++
				}
			}
		} else {
			outE = e
			outM = m shr 13
			// if above halfway or exactly halfway and outM is odd
			if ((m and 0x1fff) + (outM and 0x1) > 0x1000) {
				// Round to nearest even
				// Can overflow into exponent bit, which surprisingly is OK.
				// This increment relies on the +outM in the return statement below
				outM++
			}
		}
	}
	// The outM is added here as the +1 increments for outM above can
	// cause an overflow in the exponent bit which is OK.
	return (s shl SIGN_SHIFT or (outE shl EXPONENT_SHIFT) + outM).toShort().toLong()
}

fun toFloat(h: Long): Float {
	val bits: Int = h.toInt() and 0xffff
	val s = bits and SIGN_MASK
	val e = bits ushr EXPONENT_SHIFT and SHIFTED_EXPONENT_MASK
	val m = bits and SIGNIFICAND_MASK
	var outE = 0
	var outM = 0
	if (e == 0) { // Denormal or 0
		if (m != 0) {
			// Convert denorm fp16 into normalized fp32
			var o = java.lang.Float.intBitsToFloat(FP32_DENORMAL_MAGIC + m)
			o -= FP32_DENORMAL_FLOAT
			return if (s == 0) o else -o
		}
	} else {
		outM = m shl 13
		if (e == 0x1f) { // Infinite or NaN
			outE = 0xff
			if (outM != 0) { // SNaNs are quieted
				outM = outM or FP32_QNAN_MASK
			}
		} else {
			outE = e - EXPONENT_BIAS + FP32_EXPONENT_BIAS
		}
	}
	val out = s shl 16 or (outE shl FP32_EXPONENT_SHIFT) or outM
	return java.lang.Float.intBitsToFloat(out)
}

const val SIGN_SHIFT = 15
const val SIGN_MASK = 0x8000
const val SHIFTED_EXPONENT_MASK = 0x1f
const val SIGNIFICAND_MASK = 0x3ff
const val EXPONENT_SHIFT = 10
const val EXPONENT_BIAS = 15
private const val FP32_SIGN_SHIFT = 31
private const val FP32_EXPONENT_SHIFT = 23
private const val FP32_SHIFTED_EXPONENT_MASK = 0xff
private const val FP32_SIGNIFICAND_MASK = 0x7fffff
private const val FP32_EXPONENT_BIAS = 127
private const val FP32_QNAN_MASK = 0x400000
private const val FP32_DENORMAL_MAGIC = 126 shl 23
private val FP32_DENORMAL_FLOAT = java.lang.Float.intBitsToFloat(FP32_DENORMAL_MAGIC)

fun main() {
	val v = vector(1.11F, 2.22F, 817.777F)
	println("${v.x},${v.y},${v.z}")
}