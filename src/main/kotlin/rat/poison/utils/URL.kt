package rat.poison.utils

import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

fun encodeUrl(URL: String): String = URLEncoder.encode(URL, "utf-8").replace("+", "%20")

fun safeUrlRead(vararg fromParts: String): String {
    var tmpUrl = ""
    var ret = ""
    fromParts.forEachIndexed { index, s ->
        tmpUrl += if (index > 0) {
            encodeUrl(s)
        } else {
            s
        }
    }
    try {
        val connection = URL(tmpUrl)
        val con = connection.openConnection() as HttpsURLConnection
        ret = connection.readText()
        con.disconnect()
    }
    finally {
        return ret
    }
}