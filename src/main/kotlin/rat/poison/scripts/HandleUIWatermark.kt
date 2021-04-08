package rat.poison.scripts

import rat.poison.BRANCH
import rat.poison.overlay.App
import rat.poison.utils.keyEvalMap
import java.util.*

//TODO hey redo this or smthn sexual

//Builds from UI class VisBindTableCustom.kt
//val keybindNames = mutableListOf<Pair<String, String>>()
private val concatSb = StringBuilder()

private val calendar = Calendar.getInstance()

fun handleUIWatermark() = App {
    calendar.timeInMillis = System.currentTimeMillis()
    uiWatermark.keybindText.setText(getKeybinds())
    uiWatermark.watermarkText.setText(concatSb.clear().append("RatPoison [").append(BRANCH).append("] - ").append(calendar.get(Calendar.HOUR_OF_DAY)).append(":").append(calendar.get(Calendar.MINUTE)).append(":").append(calendar.get(Calendar.SECOND)))
    //the uh the fuck the uh % usage moment the
}

private val stringList = StringBuilder()
//the the uh fhthe uht htme uh the mf uh the tuhthut
fun getKeybinds(): StringBuilder {
    stringList.clear()
    for (i in 0 until keyEvalMap.keys.size) {
        val value = keyEvalMap.values[i]
        val key = keyEvalMap.keys[i]
        if (value.third) { //Active
            stringList.append(key).append(" ").appendLine(value.first.prettyPrint)
        }
    }
    return stringList
}
