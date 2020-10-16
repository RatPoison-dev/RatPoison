package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.miscVisualsTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class MiscVisualsTab : Tab(false, false) {
    private val table = VisTable()

    val radarEsp = VisCheckBoxCustom("Radar Esp", "RADAR_ESP")
    val legitRadar = VisCheckBoxCustom("Legit Radar", "LEGIT_RADAR")
    val legitRadarDistance = VisSliderCustom("Distance", "LEGIT_RADAR_FOOTSTEPS_DISTANCE", 100F, 5000F, 100F, true)

    val nightMode = VisCheckBoxCustom("Nightmode/Fullbright", "ENABLE_NIGHTMODE")
    val nightModeSlider = VisSliderCustom("%", "NIGHTMODE_VALUE", 0.05F, 5F, .05F, false)

    val visAdrenaline = VisCheckBoxCustom("Adrenaline", "ENABLE_ADRENALINE")
    val adrenalineStrength = VisSliderCustom("Adrenaline Strength", "ADRENALINE_STRENGTH", 0.01F, 1F, 0.01F, false)
    val adrenalineCooldown = VisSliderCustom("Adrenaline Cooldown", "ADRENALINE_COOLDOWN", 0.001F, 0.05F, 0.001F, false, dec = 3)
    val adrenalineBombTime = VisCheckBoxCustom("Adrenaline Bomb Time", "ADRENALINE_BOMB_TIME")

    val showAimFov = VisCheckBoxCustom(" ", "DRAW_AIM_FOV", false)
    val showAimFovColor = VisColorPickerCustom("Draw Aim FOV", "DRAW_AIM_FOV_COLOR")

    val showTriggerFov = VisCheckBoxCustom(" ", "DRAW_TRIGGER_FOV", false)
    val showTriggerFovColor = VisColorPickerCustom("Draw Trigger FOV", "DRAW_TRIGGER_FOV_COLOR")

    val enableSpreadCircle = VisCheckBoxCustom(" ", "SPREAD_CIRCLE", false)
    val spreadCircleColor = VisColorPickerCustom("Enable", "SPREAD_CIRCLE_COLOR")

    val enableHeadLevel = VisCheckBoxCustom(" ", "HEAD_LVL_ENABLE", false)
    val headLevelColor = VisColorPickerCustom("Enable", "HEAD_LVL_COLOR")
    val headLevelDeadzone = VisSliderCustom("Deadzone", "HEAD_LVL_DEADZONE", 1F, 10F, .1F, true, width1 = 225F, width2 = 225F)


    init {
        table.padLeft(25F)
        table.padRight(25F)

        val aimFov = VisTable()
        aimFov.add(showAimFov).left()
        aimFov.add(showAimFovColor).width(175F-showAimFov.width).left()

        val triggerFov = VisTable()
        triggerFov.add(showTriggerFov).left()
        triggerFov.add(showTriggerFovColor).width(175F-showTriggerFov.width).left()

        //Add all items to label for tabbed pane content
        table.add(radarEsp).left().row()
        table.add(legitRadar).left().row()
        table.add(legitRadarDistance).left().row()

        table.addSeparator()

        table.add(nightMode).left().row()
        table.add(nightModeSlider).left().row()

        table.addSeparator()

        table.add(visAdrenaline).left().row()
        table.add(adrenalineBombTime).left().row()
        table.add(adrenalineStrength).left().row()
        table.add(adrenalineCooldown).left().row()

        table.addSeparator()

        table.add(aimFov).left().row()
        table.add(triggerFov).left().row()

        table.addSeparator()

        var tmpTable = VisTable()
        tmpTable.add(enableSpreadCircle)
        tmpTable.add(spreadCircleColor).width(175F - enableSpreadCircle.width).padRight(50F)

        table.add(tmpTable).left().row()

        table.addSeparator()

        tmpTable = VisTable()
        tmpTable.add(enableHeadLevel)
        tmpTable.add(headLevelColor).width(175F - enableHeadLevel.width).padRight(50F)

        table.add(tmpTable).left().row()
        table.add(headLevelDeadzone).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Misc".toLocale()
    }
}

fun miscVisualTabUpdate() {
    miscVisualsTab.apply {
        radarEsp.update()
        legitRadar.update()
        legitRadarDistance.update()
        nightMode.update()
        nightModeSlider.update()
        visAdrenaline.update()
        adrenalineBombTime.update()
        adrenalineStrength.update()
        adrenalineCooldown.update()
        showAimFov.update()
        showAimFovColor.update()
        showTriggerFov.update()
        showTriggerFovColor.update()
        enableSpreadCircle.update()
        spreadCircleColor.update()
        enableHeadLevel.update()
        headLevelColor.update()
        headLevelDeadzone.update()
    }
}