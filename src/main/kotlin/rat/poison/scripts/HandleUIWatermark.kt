package rat.poison.scripts

import com.badlogic.gdx.utils.StringBuilder
import org.jire.arrowhead.keyPressed
import rat.poison.BRANCH
import rat.poison.curSettings
import rat.poison.overlay.App
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//TODO hey redo this or smthn sexual

//Builds from UI class VisBindTableCustom.kt
val keybindNames = mutableListOf<Pair<String, String>>()

fun handleUIWatermark() = App {
    uiWatermark.keybindText.setText(getKeybinds())
    uiWatermark.watermarkText.setText("RatPoison [$BRANCH] - ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))}") //the uhm the uh the optimization moment the uh
    //the uh the fuck the uh % usage moment the
}

//the the uh fhthe uht htme uh the mf uh the tuhthut
fun getKeybinds(): StringBuilder {
    val stringList = StringBuilder()

    for (key in keybindNames) {
        if (keyPressed(curSettings.int[key.second])) {
            stringList.append(key.first + " [Holding]\n")
        }
    }

    return stringList
}
