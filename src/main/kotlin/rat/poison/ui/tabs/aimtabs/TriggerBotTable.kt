package rat.poison.ui.tabs.aimtabs

import com.kotcrab.vis.ui.widget.CollapsibleWidget
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.poison.ui.uiHelpers.aimTab.ATabVisSlider
import rat.poison.ui.uiHelpers.binds.VisBindTableCustom

class TriggerBotTable: VisTable(false) {
    private val collapsibleTable = VisTable()
    val collapsibleWidget = CollapsibleWidget(collapsibleTable)

    //Init labels/sliders/boxes that show values here
    val boneTriggerEnableKey = VisCheckBoxCustom("Trigger On Key", "TRIGGER_ENABLE_KEY")
    val boneTriggerKey = VisBindTableCustom("Trigger Key", "TRIGGER_KEY")

    val trigEnable = ATabVisCheckBox("Enable", "_TRIGGER") //Per weapon category

    val trigAimbot = ATabVisCheckBox("Aimbot", "_TRIGGER_AIMBOT")
    val trigInCross = ATabVisCheckBox("InCross", "_TRIGGER_INCROSS")
    val trigInFov = ATabVisCheckBox("InFov", "_TRIGGER_INFOV")
    val trigFov = ATabVisSlider("FOV", "_TRIGGER_FOV", .1F, 90F, .5F, false)
    val trigShootBacktrack = ATabVisCheckBox("Shoot Backtrack", "_TRIGGER_BACKTRACK")
    val initTrigDelay = ATabVisSlider("First Shot Delay", "_TRIGGER_INIT_SHOT_DELAY", 0F, 500F, 10F, true)
    val perShotTrigDelay = ATabVisSlider("Per Shot Delay", "_TRIGGER_PER_SHOT_DELAY", 0F, 500F, 10F, true)

    init {
        padLeft(25F)
        padRight(25F)

        //Set specific colors
        trigAimbot.backgroundImage.setColor(1F, .1F, .1F, 1F)

        collapsibleTable.apply {
            add(boneTriggerEnableKey).left().row()
            add(boneTriggerKey).left().row()
            add(trigEnable).left().padTop(8F).padBottom(8F).row()

            add(trigAimbot).left().row()
            add(trigInCross).left().row()
            add(trigInFov).left().row()
            add(trigShootBacktrack).left().row()
            add(trigFov).left().row()
            add(initTrigDelay).left().row()
            add(perShotTrigDelay).left().row()
        }

        add(collapsibleWidget)
    }
}