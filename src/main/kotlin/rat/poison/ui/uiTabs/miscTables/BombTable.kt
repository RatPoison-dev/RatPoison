package rat.poison.ui.uiTabs.miscTables

import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.uiElements.VisCheckBoxCustom
import rat.poison.ui.uiTabs.bombTable

class BombTable: VisTable(false) {
    val bombTimer = VisCheckBoxCustom("Bomb Timer", "ENABLE_BOMB_TIMER")
    val bombTimerEnableBars = VisCheckBoxCustom("Timer Bars", "BOMB_TIMER_BARS")
    val bombTimerEnableMenu = VisCheckBoxCustom("Timer Menu", "BOMB_TIMER_MENU")
    val bombTimerShowTTE = VisCheckBoxCustom("Time To Explode", "BOMB_TIMER_BARS_SHOW_TTE")
    val lsBomb = VisCheckBoxCustom("Perfect Bomb Defuse", "LS_BOMB")
    
    init {
        add(bombTimer).expandX().left().row()
        add(bombTimerEnableMenu).expandX().left().row()
        add(bombTimerEnableBars).expandX().left().row()
        add(bombTimerShowTTE).expandX().left().row()
        add(lsBomb).expandX().left().row()
    }
}

fun bombTabUpdate() {
    bombTable.apply {
        bombTimer.update()
        bombTimerEnableBars.update()
        bombTimerEnableMenu.update()
        bombTimerShowTTE.update()
        lsBomb.update()
    }
}