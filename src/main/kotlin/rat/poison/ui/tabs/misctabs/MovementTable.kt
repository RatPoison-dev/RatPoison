package rat.poison.ui.tabs.misctabs

import com.badlogic.gdx.scenes.scene2d.ui.Value.prefWidth
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.tabs.movementTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiHelpers.binds.VisBindTableCustom

class MovementTable: VisTable(false) {
    val bunnyHop = VisCheckBoxCustom("Bunny Hop", "ENABLE_BUNNY_HOP")
    val bunnyHopHitChance = VisSliderCustom("HitChance", "BHOP_HITCHANCE", 0F, 100F,  1F, true)
    val bunnyHopKey = VisBindTableCustom("Bunny Hop Key", "ENABLE_BUNNY_HOP_KEY", keyWidth = 225F)
    val autoStrafe = VisCheckBoxCustom("Auto Strafe", "AUTO_STRAFE")
    val autoStrafeBHopOnly = VisCheckBoxCustom("BHop Only", "STRAFE_BHOP_ONLY")
    val fastStop = VisCheckBoxCustom("Fast Stop", "FAST_STOP")
    val headWalk = VisCheckBoxCustom("Head Walk", "HEAD_WALK")
    val knifeBot = VisCheckBoxCustom("Knife Bot", "ENABLE_AUTO_KNIFE")
    val blockBot = VisCheckBoxCustom("Block Bot", "BLOCK_BOT")
    val blockBotKey = VisBindTableCustom("Block Bot Key", "BLOCK_BOT_KEY")
    val blockBotDistance = VisSliderCustom("Block Bot Distance", "BLOCK_BOT_DISTANCE", 100F, 5000F, 100F, true)

    init {
        add(bunnyHop).expandX().left().row()
        add(bunnyHopHitChance).expandX().left().row()
        add(bunnyHopKey).expandX().left().row()
        add(autoStrafe).expandX().left().row()
        add(autoStrafeBHopOnly).expandX().left().row()
        //addSeparator()
        add(fastStop).expandX().left().row()
        add(headWalk).expandX().left().row()
        add(knifeBot).expandX().left().row()
        //addSeparator()
        add(blockBot).expandX().left().row()
        add(blockBotKey).expandX().left().row()
        add(blockBotDistance).expandX().left().row()
    }
}

fun movementTabUpdate() {
    movementTable.apply {
        bunnyHop.update()
        bunnyHopHitChance.update()
        bunnyHopKey.update()
        autoStrafe.update()
        autoStrafeBHopOnly.update()
        fastStop.update()
        headWalk.update()
        knifeBot.update()
        blockBot.update()
        blockBotKey.update()
        blockBotDistance.update()
    }
}