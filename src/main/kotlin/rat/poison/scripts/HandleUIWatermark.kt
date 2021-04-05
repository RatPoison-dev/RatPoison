package rat.poison.scripts

import org.jire.arrowhead.keyPressed
import rat.poison.BRANCH
import rat.poison.curSettings
import rat.poison.overlay.App
import java.util.Calendar
import kotlin.text.StringBuilder

//TODO hey redo this or smthn sexual

//Builds from UI class VisBindTableCustom.kt
val keybindNames = mutableListOf<Pair<String, String>>()
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

    for (key in keybindNames) {
        if (keyPressed(curSettings.int[key.second])) {
            stringList.append(key.first).appendLine(" [Holding]")
        }
    }

    return stringList
}
