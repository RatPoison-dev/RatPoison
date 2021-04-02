package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.overlay.opened
import rat.poison.toLocale
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSelectBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiPanels.rcsTab
import rat.poison.utils.extensions.upper

class RcsTab : Tab(false, false) {
    private val table = VisTable(false)

    //Init labels/sliders/boxes that show values here
    val enableRCS = VisCheckBoxCustom("Enable RCS", "ENABLE_RCS")

    val rcsType = VisSelectBoxCustom("RCS Type", "RCS_TYPE", false, true, "STABLE", "LEGACY")

    val rcsSmoothingX = VisSliderCustom("RCS X Accuracy", "RCS_SMOOTHING_X", .02F, 1F, .02F, false, labelWidth = 200F, barWidth = 250F)
    val rcsSmoothingY = VisSliderCustom("RCS Y Accuracy", "RCS_SMOOTHING_Y", .02F, 1F, .02F, false, labelWidth = 200F, barWidth = 250F)

    val rcsReturnAim = VisCheckBoxCustom("Return Aim", "RCS_RETURNAIM")

    val enableRCrosshair = VisCheckBoxCustom("Recoil Crosshair", "ENABLE_RECOIL_CROSSHAIR")
    val enableSCrosshair = VisCheckBoxCustom("Scope Compatible", "ENABLE_SNIPER_CROSSHAIR")

    val rCrosshairType = VisSelectBoxCustom("RCrosshair Type", "RCROSSHAIR_TYPE", false, false, "CIRCLE", "CROSSHAIR")

    val rCrosshairRadius = VisSliderCustom("RCrosshair Radius", "RCROSSHAIR_RADIUS", 1F, 50F, 1F, false, labelWidth = 200F, barWidth = 250F)
    val rCrosshairWidth = VisSliderCustom("RCrosshair Width", "RCROSSHAIR_WIDTH", 1F, 5F, 1F, true, labelWidth = 200F, barWidth = 250F)
    val rCrosshairLength = VisSliderCustom("RCrosshair Length", "RCROSSHAIR_LENGTH", 3F, 100F, 1F, true, labelWidth = 200F, barWidth = 250F)
    val rCrosshairGap = VisSliderCustom("RCrosshair Gap", "RCROSSHAIR_GAP", .1F, 20F, .1F, false, labelWidth = 200F, barWidth = 250F)
    val rCrosshairDot = VisCheckBoxCustom("Center Dot", "RCROSSHAIR_CENTER_DOT")
    val rCrosshairDotRadius = VisSliderCustom("RCrosshair Dot Radius", "RCROSSHAIR_DOT_RADIUS", .8F, 20F, .1F, false, labelWidth = 200F, barWidth = 250F)
    val rCrosshairTop    = VisCheckBoxCustom("Top", "RCROSSHAIR_TOP")
    val rCrosshairBottom = VisCheckBoxCustom("Bottom", "RCROSSHAIR_BOTTOM")
    val rCrosshairLeft   = VisCheckBoxCustom("Left", "RCROSSHAIR_LEFT")
    val rCrosshairRight  = VisCheckBoxCustom("Right", "RCROSSHAIR_RIGHT")
    val rCrosshairOutline = VisCheckBoxCustom("Outline", "RCROSSHAIR_OUTLINE")
    val rCrosshairOutlineWidth = VisSliderCustom("Outline Width", "RCROSSHAIR_OUTLINE_WIDTH", .8F, 20F, .1F, false, labelWidth = 200F, barWidth = 250F)
    val rCrosshairDynamic = VisCheckBoxCustom("Dynamic", "RCROSSHAIR_DYNAMIC")
    val rCrosshairMaxSpread = VisSliderCustom("Max Spread", "RCROSSHAIR_MAX_SPREAD", 0F, 100F, 1F, false, labelWidth = 200F, barWidth = 250F)

    val rCrosshairXOffset = VisSliderCustom("RCrosshair X Offset", "RCROSSHAIR_XOFFSET", -48F, 48F, 1F, true, labelWidth = 200F, barWidth = 250F)
    val rCrosshairYOffset = VisSliderCustom("RCrosshair Y Offset", "RCROSSHAIR_YOFFSET", -48F, 48F, 1F, true, labelWidth = 200F, barWidth = 250F)

    val rCrosshairColor = VisColorPickerCustom("RCrosshair Color", "RCROSSHAIR_COLOR")
    val rCrosshairOutlineColor = VisColorPickerCustom("Outline Color", "RCROSSHAIR_OUTLINE_COLOR")

    init {
        ////////////////////FORMATTING
        table.padLeft(25F)
        table.padRight(25F)
        table.add(enableRCS).left().row()
        table.add(rcsType).left().row()
        table.add(rcsSmoothingX).left().row()
        table.add(rcsSmoothingY).left().row()
        table.add(rcsReturnAim).left().row()
        table.addSeparator()
        table.add(enableRCrosshair).left().row()
        table.add(rCrosshairType).left().row()
        table.add(enableSCrosshair).left().row()
        table.add(rCrosshairRadius).left().row()
        table.add(rCrosshairWidth).left().row()
        table.add(rCrosshairLength).left().row()
        table.add(rCrosshairGap).left().row()
        table.add(rCrosshairDot).left().row()
        table.add(rCrosshairDotRadius).left().row()
        table.add(rCrosshairTop).left().row()
        table.add(rCrosshairBottom).left().row()
        table.add(rCrosshairLeft).left().row()
        table.add(rCrosshairRight).left().row()
        table.add(rCrosshairOutline).left().row()
        table.add(rCrosshairOutlineWidth).left().row()
        table.add(rCrosshairDynamic).left().row()
        table.add(rCrosshairMaxSpread).left().row()
        table.add(rCrosshairXOffset).left().row()
        table.add(rCrosshairYOffset).left().row()
        table.add(rCrosshairColor).left().row()
        table.add(rCrosshairOutlineColor).left().row()
        ////////////////////FORMATTING
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "RCS".toLocale()
    }
}

fun updateDisableRCrosshair() {
    rcsTab.apply {
        if (!opened) return

        val bool = !(curSettings.bool["ENABLE_RECOIL_CROSSHAIR"] || curSettings.bool["ENABLE_SNIPER_CROSSHAIR"])
        var color = Color(255F, 255F, 255F, 1F)
        if (bool) {
            color = Color(105F, 105F, 105F, .2F)
        }

        rCrosshairType.disable(bool, color)
        enableSCrosshair.disable(bool)

        if (bool) {
            rCrosshairRadius.disable(true, color)
            rCrosshairWidth.disable(true, color)
            rCrosshairLength.disable(true, color)
            rCrosshairColor.disable(true)
            rCrosshairGap.disable(true, color)
            rCrosshairDot.disable(true)
            rCrosshairDotRadius.disable(true, color)
            rCrosshairTop.disable(true)
            rCrosshairBottom.disable(true)
            rCrosshairLeft.disable(true)
            rCrosshairRight.disable(true)
            rCrosshairOutline.disable(true)
            rCrosshairOutlineWidth.disable(true, color)
            rCrosshairMaxSpread.disable(true, color)
            rCrosshairDynamic.disable(true)
            rCrosshairOutlineColor.disable(true)
        } else {
            if (curSettings["RCROSSHAIR_TYPE"].upper() == "CROSSHAIR") {
                rCrosshairRadius.disable(true, Color(105F, 105F, 105F, .2F))
                rCrosshairWidth.disable(false, Color(255F, 255F, 255F, 1F))
                rCrosshairLength.disable(false, Color(255F, 255F, 255F, 1F))
                rCrosshairColor.disable(false)
                rCrosshairGap.disable(false, Color(255F, 255F, 255F, 1F))
                rCrosshairDot.disable(false)
                rCrosshairDotRadius.disable(false, Color(255F, 255F, 255F, 1F))
                rCrosshairTop.disable(false)
                rCrosshairBottom.disable(false)
                rCrosshairLeft.disable(false)
                rCrosshairRight.disable(false)
                rCrosshairOutline.disable(false)
                rCrosshairOutlineWidth.disable(false, Color(255F, 255F, 255F, 1F))
                rCrosshairMaxSpread.disable(false, Color(255F, 255F, 255F, 1F))
                rCrosshairDynamic.disable(false)
                rCrosshairOutlineColor.disable(false)
            } else {
                rCrosshairRadius.disable(false, Color(255F, 255F, 255F, 1F))
                rCrosshairWidth.disable(true, Color(105F, 105F, 105F, .2F))
                rCrosshairLength.disable(true, Color(105F, 105F, 105F, .2F))
                rCrosshairColor.disable(true)
                rCrosshairGap.disable(true, Color(105F, 105F, 105F, .2F))
                rCrosshairDot.disable(true)
                rCrosshairDotRadius.disable(true, Color(105F, 105F, 105F, .2F))
                rCrosshairTop.disable(true)
                rCrosshairBottom.disable(true)
                rCrosshairLeft.disable(true)
                rCrosshairRight.disable(true)
                rCrosshairOutline.disable(true)
                rCrosshairOutlineWidth.disable(true, Color(105F, 105F, 105F, .2F))
                rCrosshairMaxSpread.disable(true, Color(105F, 105F, 105F, .2F))
                rCrosshairDynamic.disable(true)
                rCrosshairOutlineColor.disable(true)
            }
        }

        rCrosshairXOffset.disable(bool, color)
        rCrosshairYOffset.disable(bool, color)
        rCrosshairColor.disable(bool)
    }
}

fun updateDisableRcsSmoothing() {
    rcsTab.apply {
        if (!opened) return

        val bool = !curSettings.bool["ENABLE_RCS"]
        var color = Color(255F, 255F, 255F, 1F)
        if (bool) {
            color = Color(105F, 105F, 105F, .2F)
        }

        rcsType.disable(bool, color)
        rcsSmoothingX.disable(bool, color)
        rcsSmoothingY.disable(bool, color)
        rcsReturnAim.isDisabled = bool
    }
}

fun rcsTabUpdate() {
    rcsTab.apply {
        enableRCS.update()
        rcsType.update()
        rcsSmoothingX.update()
        rcsSmoothingY.update()
        rcsReturnAim.update()
        enableRCrosshair.update()
        rCrosshairType.update()
        enableSCrosshair.update()
        rCrosshairRadius.update()
        rCrosshairWidth.update()
        rCrosshairLength.update()
        rCrosshairGap.update()
        rCrosshairTop.update()
        rCrosshairDot.update()
        rCrosshairDotRadius.update()
        rCrosshairBottom.update()
        rCrosshairLeft.update()
        rCrosshairRight.update()
        rCrosshairOutlineColor.update()
        rCrosshairOutlineWidth.update()
        rCrosshairOutline.update()
        rCrosshairDynamic.update()
        rCrosshairMaxSpread.update()
        rCrosshairXOffset.update()
        rCrosshairYOffset.update()
        rCrosshairColor.update()
    }

    updateDisableRcsSmoothing()
    updateDisableRCrosshair()
}