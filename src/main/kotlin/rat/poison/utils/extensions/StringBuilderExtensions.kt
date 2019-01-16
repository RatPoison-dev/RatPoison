////Courtesy of Mr Noad

package rat.poison.utils.extensions

import com.badlogic.gdx.utils.StringBuilder as LibgdxStringBuilder

fun StringBuilder.appendHumanReadableSize(size: Long): StringBuilder {
    return size.humanReadableSize(this)
}

fun StringBuilder.appendSubstring(
        string: CharSequence,
        startIndex: Int,
        endIndex: Int = string.length - 1
): StringBuilder {
    for (i in startIndex..endIndex) {
        this.append(string[i])
    }
    return this
}

fun LibgdxStringBuilder.set(o: Any): LibgdxStringBuilder {
    this.setLength(0)
    return this.append(o)
}
fun LibgdxStringBuilder.appendHumanReadableSize(size: Long): LibgdxStringBuilder {
    return size.humanReadableSize(this)
}

fun LibgdxStringBuilder.appendSubstring(
        string: CharSequence,
        startIndex: Int,
        endIndex: Int = string.length - 1
): LibgdxStringBuilder {
    for (i in startIndex..endIndex) {
        this.append(string[i])
    }
    return this
}