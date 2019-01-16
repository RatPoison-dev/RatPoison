package rat.poison.utils.extensions

import com.sun.jna.platform.win32.WinDef
import rat.poison.utils.natives.CUser32
import java.lang.Math.sqrt

fun WinDef.POINT.set(x: Int, y: Int) = apply {
	this.x = x
	this.y = y
}

fun WinDef.POINT.refresh() = apply { CUser32.GetCursorPos(this) }

fun WinDef.POINT.distance(b: WinDef.POINT): Double {
	val px = (b.x - this.x).toDouble()
	val py = (b.y - this.y).toDouble()
	return sqrt(px * px + py * py)
}