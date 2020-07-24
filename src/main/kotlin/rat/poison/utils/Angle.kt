package rat.poison.utils

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Quaternion
import java.lang.Math.abs
import kotlin.math.cos
import kotlin.math.sin

typealias Angle = Vector

fun Vector.normalize() = apply {
    if (x != x) x = 0F
    if (y != y) y = 0F

    if (x > 89) x = 89F
    if (x < -89) x = -89F

    while (y > 180) y -= 360
    while (y <= -180) y += 360

    if (y > 180) y = 180F
    if (y < -180F) y = -180F

    z = 0F
}

internal fun Angle.distanceTo(target: Angle) = abs(x - target.x) + abs(y - target.y) + abs(z - target.z)

internal fun Angle.isValid() = !(z != 0F
        || x < -89 || x > 180
        || y < -180 || y > 180
        || x.isNaN() || y.isNaN() || z.isNaN())

internal fun Angle.finalize(orig: Angle, smoothness: Float) {
    x -= orig.x
    y -= orig.y
    z = 0F
    normalize()

    x = orig.x + x * smoothness
    y = orig.y + y * smoothness
    normalize()
}

internal fun Angle.to(matrix: Matrix4? = null,
                      translation: Vector? = null,
                      forward: Vector? = null,
                      right: Vector? = null,
                      up: Vector? = null,
                      quaternion: Quaternion? = null) {
    val dp = this.x * MathUtils.degreesToRadians
    val dy = this.y * MathUtils.degreesToRadians
    val dr = this.z * MathUtils.degreesToRadians

    val sp = sin(dp)
    val cp = cos(dp)
    val sy = sin(dy)
    val cy = cos(dy)
    val sr = sin(dr)
    val cr = cos(dr)

    //forward
    if (matrix != null || forward != null) {
        val forwardX = cp * cy
        val forwardY = cp * sy
        val forwardZ = -sp
        matrix?.apply {
            `val`[Matrix4.M00] = forwardX.toFloat()
            `val`[Matrix4.M10] = forwardY.toFloat()
            `val`[Matrix4.M20] = forwardZ.toFloat()
            `val`[Matrix4.M30] = 0f
        }
        forward?.apply {
            forward.set(forwardX, forwardY, forwardZ)
        }
    }

    if (matrix != null || right != null || up != null) {
        val crXcy = cr * cy
        val crXsy = cr * sy
        val srXcy = sr * cy
        val srXsy = sr * sy

        //right
        if (matrix != null || right != null) {
            val rightX = -sp * srXcy + crXsy
            val rightY = -sp * srXsy - crXcy
            val rightZ = -sr * cp
            matrix?.apply {
                `val`[Matrix4.M01] = -rightX.toFloat()
                `val`[Matrix4.M11] = -rightY.toFloat()
                `val`[Matrix4.M21] = -rightZ.toFloat()
                `val`[Matrix4.M31] = 0f
            }
            right?.apply {
                right.set(rightX, rightY, rightZ)
            }
        }

        //up
        if (matrix != null || up != null) {
            val upX = sp * crXcy + srXsy
            val upY = sp * crXsy - srXcy
            val upZ = cr * cp
            matrix?.apply {
                `val`[Matrix4.M02] = upX.toFloat()
                `val`[Matrix4.M12] = upY.toFloat()
                `val`[Matrix4.M22] = upZ.toFloat()
                `val`[Matrix4.M32] = 0f
            }
            up?.apply {
                up.set(upX, upY, upZ)
            }
        }

        if (quaternion != null) {
            val srXcp = sr * cp
            val crXsp = cr * sp
            val crXcp = cr * cp
            val srXsp = sr * sp

            val x = srXcp * cy - crXsp * sy // X
            val y = crXsp * cy + srXcp * sy // Y
            val z = crXcp * sy - srXsp * cy // Z
            val w = crXcp * cy + srXsp * sy // W (real component)

            quaternion.set(x.toFloat(), y.toFloat(), z.toFloat(), w.toFloat())
        }
    }

    //should i throw if there is translation but no matrix ?

    matrix?.apply {
        `val`[Matrix4.M03] = translation?.x?.toFloat() ?: 0f
        `val`[Matrix4.M13] = translation?.y?.toFloat() ?: 0f
        `val`[Matrix4.M23] = translation?.z?.toFloat() ?: 0f
        `val`[Matrix4.M33] = 1f
    }
}
