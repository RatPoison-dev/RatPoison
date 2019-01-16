////Courtesy of Mr Noad

package rat.poison.utils.extensions

import com.badlogic.gdx.math.MathUtils

fun Float.roundNDecimals(n: Int): Float {
    var precision = 10f
    for (i in 1 until n) {
        precision *= 10f
    }
    return MathUtils.roundPositive(this * precision) / precision
}