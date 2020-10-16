package rat.poison.utils

import java.lang.Exception
import java.net.URL

fun parseOffset(): Int {
    try {
        val connection = URL("https://raw.githubusercontent.com/Akandesh/blazedumper/master/csgo.json")
        connection.openConnection()
        val text = connection.readText()
        //this obviously is not a perfect solution
        val lines = text.split("\n")
        lines.forEach { line ->
            if ("dwbSendPackets" in line) {
                val regex = "\\d+".toRegex()
                return regex.find(line)!!.value.toInt()
            }
        }
    }
    catch (e: Exception) {

    }
    return -1
}