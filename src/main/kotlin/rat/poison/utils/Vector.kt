package rat.poison.utils

data class Vector(var x: Float = 0.0F, var y: Float = 0.0F, var z: Float = 0.0F) {
	
	fun set(x: Float, y: Float, z: Float) = apply {
		this.x = x
		this.y = y
		this.z = z
	}
	
	private fun invalid() = x == 0.0F && y == 0.0F && z == 0.0F
	
	private fun valid() = !invalid()
}

operator fun Vector.plus(v: Vector): Vector {
	x += v.x
	y += v.y
	z += v.z
	return this
}

fun Vector.scl(value: Float): Vector {
	x *= value
	y *= value
	z *= value
	return this
}