package rat.poison.utils

import java.net.URL
import javax.net.ssl.HttpsURLConnection

fun parseOffset(): Int {
    try {
        val connection = URL("https://raw.githubusercontent.com/frk1/hazedumper/master/csgo.json")
        val con = connection.openConnection() as HttpsURLConnection
        val text = connection.readText()
        con.disconnect()

        //this obviously is not a perfect solution
        val lines = text.split("\n")
        lines.forEach { line ->
            if ("dwbSendPackets" in line) {
                val regex = "\\d+".toRegex()
                return regex.find(line)!!.value.toInt()
            }
        }
    }
    catch (e: Exception) { }
    return -1
}