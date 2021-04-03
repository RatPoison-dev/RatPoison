package rat.poison.utils

import kotlin.math.sqrt

data class Vector(var x: Float = 0.0F, var y: Float = 0.0F, var z: Float = 0.0F) {

	fun set(x: Float, y: Float, z: Float) = apply {
		this.x = x
		this.y = y
		this.z = z
	}

	fun invalid() = x == 0.0F && y == 0.0F && z == 0.0F

	fun valid() = !invalid()

	fun dst2 (x: Float, y: Float, z: Float): Float {
		val a = x - this.x;
		val b = y - this.y;
		val c = z - this.z;
		return a * a + b * b + c * c;
	}

	fun dot(x: Float, y: Float, z: Float): Float {
		return this.x * x + this.y * y + this.z * z
	}

	fun set(x: Float, y: Float): Vector {
		this.x = x
		this.y = y
		return this
	}

	fun scl(x: Float, y: Float): Vector {
		this.x *= x
		this.y *= y
		return this
	}

	fun dot(v: Vector): Float {
		return this.x * v.x + this.y * v.y + this.z * v.z
	}

	fun len2(x: Float, y: Float, z: Float): Float {
		return x * x + y * y + z * z
	}

	fun nor(): Vector {
		val len2 = this.len2()
		return if (len2 == 0f || len2 == 1f) this else scl(1f / sqrt(len2.toDouble()).toFloat())
	}

	fun sub(x: Float, y: Float, z: Float): Vector {
		return this.set(this.x - x, this.y - y, this.z - z)
	}

	fun len2(): Float {
		return x * x + y * y + z * z
	}

	operator fun plus(v: Vector): Vector {
		x += v.x
		y += v.y
		z += v.z
		return this
	}

	operator fun minus(v: Vector): Vector {
		x -= v.x
		y -= v.y
		z -= v.z
		return this
	}

	fun scl(value: Float): Vector {
		x *= value
		y *= value
		z *= value
		return this
	}
}