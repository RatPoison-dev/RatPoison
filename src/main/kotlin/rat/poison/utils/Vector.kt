package rat.poison.utils

data class Vector(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {
	
	fun set(x: Double, y: Double, z: Double) = apply {
		this.x = x
		this.y = y
		this.z = z
	}
	
	private fun invalid() = x == 0.0 && y == 0.0 && z == 0.0
	
	private fun valid() = !invalid()
}

val emptyVector = Vector(0.0, 0.0, 0.0)