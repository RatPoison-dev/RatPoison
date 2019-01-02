package com.charlatano.scripts.esp

import com.charlatano.settings.ACTION_LOG
import com.charlatano.settings.ENABLE_ESP
import com.charlatano.settings.ESP_TOGGLE_KEY
import com.charlatano.utils.*
import org.jire.arrowhead.keyPressed

fun espToggle() = every(4) {
    if (keyPressed(ESP_TOGGLE_KEY) && !inBackground) {
        ENABLE_ESP = !ENABLE_ESP
        esp()
        if (ACTION_LOG) {
            println("ESP toggled to " + ENABLE_ESP)
        }
        Thread.sleep(500)
    }
}