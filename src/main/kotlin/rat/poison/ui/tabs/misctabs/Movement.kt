package rat.poison.ui.tabs.misctabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.movementTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class MovementTab: Tab(false, false) {
    private val table = VisTable()
    val bunnyHop = VisCheckBoxCustom("Bunny Hop", "ENABLE_BUNNY_HOP")
    val bunnyHopHitChance = VisSliderCustom("HitChance", "BHOP_HITCHANCE", 0F, 100F,  1F, true)
    val bunnyHopKey = VisInputFieldCustom("Bunny Hop Key", "ENABLE_BUNNY_HOP_KEY", keyWidth = 225F)
    val autoStrafe = VisCheckBoxCustom("Auto Strafe", "AUTO_STRAFE")
    val autoStrafeBHopOnly = VisCheckBoxCustom("BHop Only", "STRAFE_BHOP_ONLY")
    val fastStop = VisCheckBoxCustom("Fast Stop", "FAST_STOP")
    val headWalk = VisCheckBoxCustom("Head Walk", "HEAD_WALK")
    val knifeBot = VisCheckBoxCustom("Knife Bot", "ENABLE_AUTO_KNIFE")
    init {

        table.padLeft(25F)
        table.padRight(25F)

        table.add(bunnyHop).left().row()
        table.add(bunnyHopHitChance).left().padLeft(14F).row()
        table.add(bunnyHopKey).left().padLeft(14F).row()
        table.add(autoStrafe).left().padLeft(14F).row()
        table.add(autoStrafeBHopOnly).left().padLeft(14F).row()
        table.add(fastStop).left().row()
        table.add(headWalk).left().row()
        table.add(knifeBot).left().row()
    }

    override fun getTabTitle(): String {
        return "Movement".toLocale()
    }

    override fun getContentTable(): Table {
        return table
    }

}

fun movementTabUpdate() {
    movementTab.apply {
        bunnyHop.update()
        bunnyHopHitChance.update()
        bunnyHopKey.update()
        autoStrafe.update()
        autoStrafeBHopOnly.update()
        fastStop.update()
        headWalk.update()
        knifeBot.update()
    }
}