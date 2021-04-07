package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.BRANCH
import rat.poison.curSettings
import rat.poison.overlay.App
import rat.poison.ui.text
import rat.poison.utils.keyEvalMap
import java.util.Calendar
import kotlin.text.StringBuilder

//TODO hey redo this or smthn sexual

//Builds from UI class VisBindTableCustom.kt
//val keybindNames = mutableListOf<Pair<String, String>>()
private val concatSb = StringBuilder()

fun handleUIWatermark() = App {
    val calendar = Calendar.getInstance()
    uiWatermark.keybindText.setText(getKeybinds())
    uiWatermark.watermarkText.setText(concatSb.clear().append("RatPoison [").append(BRANCH).append("] - ").append(calendar.get(Calendar.HOUR_OF_DAY)).append(":").append(calendar.get(Calendar.MINUTE)).append(":").append(calendar.get(Calendar.SECOND)))
    //the uh the fuck the uh % usage moment the
}

private val stringList = StringBuilder()
//the the uh fhthe uht htme uh the mf uh the tuhthut
fun getKeybinds(): StringBuilder {
    stringList.clear()

    keyEvalMap.forEach {
        if (it.value.third) { //Active
            stringList.append(it.key).appendLine(" ${it.value.first.text()}")
        }

    }

    return stringList
}
