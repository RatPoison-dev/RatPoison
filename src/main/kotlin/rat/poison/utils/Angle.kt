package rat.poison.utils

internal fun Angle.finalize(orig: Angle, smoothness: Float) {
	x -= orig.x
	y -= orig.y
	z = 0F
	normalize()
	
	x = orig.x + x * smoothness
	y = orig.y + y * smoothness
	normalize()
}