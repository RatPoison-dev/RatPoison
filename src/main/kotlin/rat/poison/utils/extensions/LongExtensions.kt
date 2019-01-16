////Courtesy of Mr Noad

package rat.poison.utils.extensions

import com.badlogic.gdx.utils.StringBuilder as LibgdxStringBuilder

private const val UNITS = " kMGTPE"
private const val DECIMALS = 1

fun Long.humanReadableSize(stringBuilder: StringBuilder): StringBuilder {
    val z = (63 - java.lang.Long.numberOfLeadingZeros(this)) / 10
    val size = this.toFloat() / (1L shl (z * 10))
    val rounded = size.roundNDecimals(DECIMALS)
    return stringBuilder.append(rounded).append(' ').append(UNITS[z]).append('B')
}
fun Long.humanReadableSize(stringBuilder: LibgdxStringBuilder): LibgdxStringBuilder {
    val z = (63 - java.lang.Long.numberOfLeadingZeros(this)) / 10
    val size = this.toFloat() / (1L shl (z * 10))
    val rounded = size.roundNDecimals(DECIMALS)
    return stringBuilder.append(rounded).append(' ').append(UNITS[z]).append('B')
}