package rat.poison.utils

import com.sun.jna.platform.win32.WinDef
import rat.poison.utils.extensions.distance
import java.lang.Math.toRadians
import kotlin.math.floor
import kotlin.math.sin
import kotlin.math.sqrt
import java.util.concurrent.ThreadLocalRandom.current as tlr

object HumanMouse {
	inline fun fastSteps(a: WinDef.POINT, b: WinDef.POINT, action: (Int, Int) -> Unit) {
		val a2b = a.distance(b)
		val sqa2b = sqrt(a2b)
		
		val steps = sqa2b * 3
		
		val totalSteps = steps.toInt() + 2
		val lastIndex = totalSteps - 1
		for (i in 1..totalSteps) action(lastIndex, i)
		action(lastIndex, lastIndex) // final step
	}
	
	inline fun steps(a: WinDef.POINT, b: WinDef.POINT, action: (Int, Int, Int, Int) -> Unit) {
		val a2b = a.distance(b)
		val sqa2b = sqrt(a2b)
		
		val steps = sqa2b * 3
		val radSteps = toRadians(180 / steps)
		
		val xOffset = (b.x - a.x) / steps
		val yOffset = (b.y - a.y) / steps
		
		var x = radSteps
		var y = radSteps
		
		var waviness = 2.8
		if (a2b < 120) // less than 120px
			waviness = 0.5
		
		var multiplier = 1
		
		if (tlr().nextBoolean()) x *= floor(tlr().nextDouble() * waviness + 1)
		if (tlr().nextBoolean()) y *= floor(tlr().nextDouble() * waviness + 1)
		if (tlr().nextBoolean()) multiplier *= -1
		
		val offset = tlr().nextDouble() * (1.6 + sqrt(steps)) + 6 + 2
		
		val totalSteps = steps.toInt() + 2
		val lastIndex = totalSteps - 1
		for (i in 1..totalSteps) {
			val stepX = a.x + ((xOffset * i).toInt() + multiplier * (offset * sin(x * i)).toInt())
			val stepY = a.y + ((yOffset * i).toInt() + multiplier * (offset * sin(y * i)).toInt())
			action(lastIndex, stepX, stepY, i)
		}
		action(lastIndex, b.x, b.y, lastIndex) // final step
	}
	
}