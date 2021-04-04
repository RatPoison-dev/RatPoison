@file:Suppress("BlockingMethodInNonBlockingContext")

package rat.poison.ui.uiTabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.LinkLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.overlay.App.uiBombWindow
import rat.poison.overlay.App.uiSpecList
import rat.poison.ui.changed
import rat.poison.ui.uiElements.VisCheckBoxCustom
import rat.poison.ui.uiElements.VisSliderCustom
import rat.poison.ui.uiElements.binds.VisBindTableCustom
import rat.poison.ui.uiWindows.optionsTab
import rat.poison.ui.uiRefreshing

class OptionsTab : Tab(false, false) {
    private val table = VisTable(true)

    val menuKey = VisBindTableCustom("Menu Key", "MENU_KEY", 225F)
    val menuAlpha = VisSliderCustom("Menu Alpha", "MENU_ALPHA", .5F, 1F, .05F, false)
    val oglFPS = VisSliderCustom("OpenGL FPS", "OPENGL_FPS", 30F, 245F, 5F, true)
    val stayFocused = VisCheckBoxCustom("Stay Focused", "MENU_STAY_FOCUSED")
    val debug = VisCheckBoxCustom("Debug", "DEBUG")
    val keybinds = VisCheckBoxCustom("Keybinds", "KEYBINDS")
    val blur = VisCheckBoxCustom("Menu Blur", "GAUSSIAN_BLUR")
    val overloadKeybinds = VisCheckBoxCustom("Overload Keybinds", "OVERLOAD_KEYBINDS")
    private val discordLink = LinkLabel("Join Discord", "https://dimden.dev/ratpoisonowns")
    private val configsLink = LinkLabel("Join The Giveaway", "https://ratpoison.dimden.dev/")

    init {
        debug.changed { _, _ ->
            dbg = debug.isChecked
            true
        }

        table.add(menuKey).padLeft(25F).left().row()
        table.add(menuAlpha).row()
        table.add(oglFPS).row()
        table.add(stayFocused).padLeft(25F).left().row()
        table.add(debug).padLeft(25F).left().row()
        table.add(keybinds).padLeft(25F).left().row()
        table.add(overloadKeybinds).padLeft(25F).left().row()
        table.add(blur).padLeft(25F).left().row()

        table.addSeparator()

        table.add(discordLink).row()
        table.add(configsLink)
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "Options"
    }
}

fun updateWindows() {
    if (VisUI.isLoaded()) {
        uiBombWindow.updatePosition(curSettings.float["BOMB_TIMER_X"], curSettings.float["BOMB_TIMER_Y"])
        uiBombWindow.changeAlpha(curSettings.float["BOMB_TIMER_ALPHA"])
        uiSpecList.updatePosition(curSettings.float["SPECTATOR_LIST_X"], curSettings.float["SPECTATOR_LIST_Y"])
        uiSpecList.changeAlpha(curSettings.float["SPECTATOR_LIST_ALPHA"])
    }
}

fun optionsTabUpdate() {
    if (uiRefreshing) return

    optionsTab.apply {
        menuKey.update()
        menuAlpha.update()
        oglFPS.update()
        overloadKeybinds.update()
        stayFocused.update()
        debug.update()
        keybinds.update()
        blur.update()
    }

    updateWindows()
}