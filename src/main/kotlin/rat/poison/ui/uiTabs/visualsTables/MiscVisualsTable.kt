package rat.poison.ui.uiTabs.visualsTables

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curSettings
import rat.poison.ui.uiTabs.miscVisualsTable
import rat.poison.ui.uiElements.VisCheckBoxCustom
import rat.poison.ui.uiElements.VisColorPickerCustom
import rat.poison.ui.uiElements.VisSliderCustom
import rat.poison.ui.uiWindows.visualsTab

class MiscVisualsTable: VisTable(false) {
    val radarEsp = VisCheckBoxCustom("Radar Esp", "RADAR_ESP")
    val legitRadar = VisCheckBoxCustom("Legit Radar", "LEGIT_RADAR")
    val legitRadarDistance = VisSliderCustom("Distance", "LEGIT_RADAR_FOOTSTEPS_DISTANCE", 100F, 5000F, 100F, true, 0, 175F, 117F)

    val nightMode = VisCheckBoxCustom("Nightmode/Fullbright", "ENABLE_NIGHTMODE")
    val nightModeSlider = VisSliderCustom("%", "NIGHTMODE_VALUE", 0.05F, 5F, .05F, false, 2, 175F, 117F)

    val visAdrenaline = VisCheckBoxCustom("Adrenaline", "ENABLE_ADRENALINE")
    val adrenalineStrength = VisSliderCustom("Adren. Strength", "ADRENALINE_STRENGTH", 0.01F, 1F, 0.01F, false, 2, 175F, 117F)
    val adrenalineCooldown = VisSliderCustom("Adren. Cooldown", "ADRENALINE_COOLDOWN", 0.001F, 0.05F, 0.001F, false, dec = 3, 175F, 117F)
    val adrenalineBombTime = VisCheckBoxCustom("Add Bomb Time", "ADRENALINE_BOMB_TIME")

    val showAimFov = VisCheckBoxCustom("Draw Aim FOV", "DRAW_AIM_FOV", false)
    val showAimFovColor = VisColorPickerCustom("Aim FOV Color", "DRAW_AIM_FOV_COLOR")

    val backtrackVisualize = VisCheckBoxCustom("Visualize Backtrack", "BACKTRACK_VISUALIZE")
    val backtrackVisualizeSmokeCheck = VisCheckBoxCustom("Visualize Backtrack Smoke Check", "BACKTRACK_VISUALIZE_SMOKE_CHECK")

    val showTriggerFov = VisCheckBoxCustom("Draw Trigger FOV", "DRAW_TRIGGER_FOV", false)
    val showTriggerFovColor = VisColorPickerCustom("Trigger FOV Color", "DRAW_TRIGGER_FOV_COLOR")

    val enableSpreadCircle = VisCheckBoxCustom("Spread Circle", "SPREAD_CIRCLE", false)
    val spreadCircleColor = VisColorPickerCustom("Spread Circle Color", "SPREAD_CIRCLE_COLOR")

    val enableHeadLevel = VisCheckBoxCustom("Head Line", "HEAD_LVL_ENABLE", false)
    val headLevelColor = VisColorPickerCustom("Head Line Color", "HEAD_LVL_COLOR")
    val headLevelDeadzone = VisSliderCustom("Deadzone", "HEAD_LVL_DEADZONE", .01F, 10F, .1F, false, labelWidth = 175F, barWidth = 117F)

    val enableUIWatermark = VisCheckBoxCustom("UI Watermark", "UI_WATERMARK")

    init {
        val label = VisLabel("Miscellaneous")
        label.setColor(1F, 1F, 1F, 1F)

        add(label).colspan(2).expandX().padTop(4F).row()
        addSeparator().colspan(2).width(200F).top().height(2F).padBottom(8F)

        add(showAimFov).left().padRight(175F - showAimFov.width)
        add(showAimFovColor).left().expandX().row()

        add(showTriggerFov).left().padRight(175F - showTriggerFov.width)
        add(showTriggerFovColor).left().expandX().padBottom(8F).row()

        add(radarEsp).colspan(2).left().row()
        add(legitRadar).colspan(2).left().row()
        add(legitRadarDistance).colspan(2).left().padBottom(8F).row()

        add(nightMode).colspan(2).left().row()
        add(nightModeSlider).colspan(2).left().padBottom(8F).row()

        add(visAdrenaline).colspan(2).left().row()
        add(adrenalineBombTime).colspan(2).left().row()
        add(adrenalineStrength).colspan(2).left().row()
        add(adrenalineCooldown).colspan(2).left().padBottom(8F).row()

        add(backtrackVisualize).colspan(2).left().row()
        add(backtrackVisualizeSmokeCheck).colspan(2).left().padBottom(8F).row()

        add(enableSpreadCircle).left().padRight(175F - enableSpreadCircle.width).padBottom(8F)
        add(spreadCircleColor).left().expandX().row()

        add(enableHeadLevel).left().padRight(175F - enableHeadLevel.width)
        add(headLevelColor).left().expandX().row()

        add(headLevelDeadzone).colspan(2).left().row()

        add(enableUIWatermark).colspan(2).left().row()
    }
}

fun miscVisualTabUpdate() {
    miscVisualsTable.apply {
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
        backtrackVisualize.update()
        backtrackVisualizeSmokeCheck.update()
        showTriggerFovColor.update()
        enableSpreadCircle.update()
        spreadCircleColor.update()
        enableHeadLevel.update()
        headLevelColor.update()
        headLevelDeadzone.update()
        enableUIWatermark.update()
    }
}

fun updateDisableDrawFOV() {
    val bool = !visualsTab.enableEsp.isChecked

    miscVisualsTable.apply {
        if (curSettings["FOV_TYPE"].replace("\"", "") == "DISTANCE") {
            showAimFov.disable(true)
            showAimFovColor.disable(true)

            showTriggerFov.disable(true)
            showTriggerFovColor.disable(true)
        } else {
            showAimFov.disable(bool)
            showAimFovColor.disable(bool)

            showTriggerFov.disable(bool)
            showTriggerFovColor.disable(bool)
        }
    }
}

fun miscVisualTableDisable(bool: Boolean, col: Color) {
    updateDisableDrawFOV()
    miscVisualsTable.apply {
        radarEsp.disable(bool)
        legitRadar.disable(bool)
        legitRadarDistance.disable(bool, col)
        visAdrenaline.disable(bool)
        adrenalineBombTime.disable(bool)
        adrenalineStrength.disable(bool, col)
        adrenalineCooldown.disable(bool, col)
        nightMode.disable(bool)
        nightModeSlider.disable(bool, col)
        backtrackVisualizeSmokeCheck.disable(bool)
        backtrackVisualize.disable(bool)
        enableSpreadCircle.disable(bool)
        spreadCircleColor.disable(bool)
        enableHeadLevel.disable(bool)
        headLevelColor.disable(bool)
        headLevelDeadzone.disable(bool, col)
        enableUIWatermark.disable(bool)
    }
}