package rat.poison.ui.tabs.misctabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.ui.tabs.bombTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom

class BombTab: Tab(false, false) {
    private val table = VisTable(false)
    val bombTimer = VisCheckBoxCustom("Bomb Timer", "ENABLE_BOMB_TIMER")
    val bombTimerEnableBars = VisCheckBoxCustom("Timer Bars", "BOMB_TIMER_BARS")
    val bombTimerEnableMenu = VisCheckBoxCustom("Timer Menu", "BOMB_TIMER_MENU")
    val bombTimerShowTTE = VisCheckBoxCustom("Time To Explode", "BOMB_TIMER_BARS_SHOW_TTE")
    val lsBomb = VisCheckBoxCustom("Perfect Bomb Defuse", "LS_BOMB")
    init {

        table.padLeft(25F)
        table.padRight(25F)

        table.add(bombTimer).left().row()
        table.add(bombTimerEnableMenu).left().row()
        table.add(bombTimerEnableBars).left().row()
        table.add(bombTimerShowTTE).left().row()
        table.add(lsBomb).left().row()
    }

    override fun getTabTitle(): String {
        return "Bomb"
    }

    override fun getContentTable(): Table {
        return table
    }
}

fun bombTabUpdate() {
    bombTab.apply {
        bombTimer.update()
        bombTimerEnableBars.update()
        bombTimerEnableMenu.update()
        bombTimerShowTTE.update()
        lsBomb.update()
    }
}