package rat.poison.utils

internal fun Angle.finalize(orig: Vector, smoothness: Float): Angle {
	val a = angle(x - orig.x, y - orig.y).normalize()
	try {
		return angle(orig.x + a.x * smoothness, orig.y + a.y * smoothness).normalize()
	} finally {
		a.release()
	}
}