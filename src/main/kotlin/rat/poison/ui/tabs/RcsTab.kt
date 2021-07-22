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
import rat.poison.utils.generalUtil.strToBool

class RcsTab : Tab(false, false) {
    private val table = VisTable(false)

    //Init labels/sliders/boxes that show values here
    val enableRCS = VisCheckBoxCustom("Enable RCS", "ENABLE_RCS")

    val rcsType = VisSelectBoxCustom("RCS Type", "RCS_TYPE", false, true, "STABLE", "LEGACY")

    val rcsSmoothingX = VisSliderCustom("RCS X Accuracy", "RCS_SMOOTHING_X", .02F, 1F, .02F, false, width1 = 200F, width2 = 250F)
    val rcsSmoothingY = VisSliderCustom("RCS Y Accuracy", "RCS_SMOOTHING_Y", .02F, 1F, .02F, false, width1 = 200F, width2 = 250F)

    val rcsReturnAim = VisCheckBoxCustom("Return Aim", "RCS_RETURNAIM")

    val enableRCrosshair = VisCheckBoxCustom("Recoil Crosshair", "ENABLE_RECOIL_CROSSHAIR")
    val enableSCrosshair = VisCheckBoxCustom("Scope Compatible", "ENABLE_SNIPER_CROSSHAIR")

    val rCrosshairType = VisSelectBoxCustom("RCrosshair Type", "RCROSSHAIR_TYPE", false, false, "CIRCLE", "CROSSHAIR")

    val rCrosshairRadius = VisSliderCustom("RCrosshair Radius", "RCROSSHAIR_RADIUS", 1F, 50F, 1F, true, width1 = 200F, width2 = 250F)
    val rCrosshairWidth = VisSliderCustom("RCrosshair Width", "RCROSSHAIR_WIDTH", 1F, 5F, 1F, true, width1 = 200F, width2 = 250F)
    val rCrosshairLength = VisSliderCustom("RCrosshair Length", "RCROSSHAIR_LENGTH", 3F, 100F, 1F, true, width1 = 200F, width2 = 250F)

    val rCrosshairXOffset = VisSliderCustom("RCrosshair X Offset", "RCROSSHAIR_XOFFSET", -48F, 48F, 1F, true, width1 = 200F, width2 = 250F)
    val rCrosshairYOffset = VisSliderCustom("RCrosshair Y Offset", "RCROSSHAIR_YOFFSET", -48F, 48F, 1F, true, width1 = 200F, width2 = 250F)
    val rCrosshairAlpha = VisSliderCustom("RCrosshair Alpha", "RCROSSHAIR_ALPHA", .1F, 1F, .1F, false, width1 = 200F, width2 = 250F)

    val rCrosshairColor = VisColorPickerCustom("Set RCrosshair Color", "RCROSSHAIR_COLOR")

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
        table.add(rCrosshairXOffset).left().row()
        table.add(rCrosshairYOffset).left().row()
        table.add(rCrosshairAlpha).left().row()
        table.add(rCrosshairColor).left().row()
        ////////////////////FORMATTING
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "RCS".toLocale()
    }
}

fun updateDisableRCrosshair() {
    rcsTab.apply {
        if (!opened) return

        val bool = !(curSettings["ENABLE_RECOIL_CROSSHAIR"].strToBool() || curSettings["ENABLE_SNIPER_CROSSHAIR"].strToBool())
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
        } else {
            if (curSettings["RCROSSHAIR_TYPE"].uppercase() == "CROSSHAIR") {
                rCrosshairRadius.disable(true, Color(105F, 105F, 105F, .2F))
                rCrosshairWidth.disable(false, Color(255F, 255F, 255F, 1F))
                rCrosshairLength.disable(false, Color(255F, 255F, 255F, 1F))
            } else {
                rCrosshairRadius.disable(false, Color(255F, 255F, 255F, 1F))
                rCrosshairWidth.disable(true, Color(105F, 105F, 105F, .2F))
                rCrosshairLength.disable(true, Color(105F, 105F, 105F, .2F))
            }
        }

        rCrosshairXOffset.disable(bool, color)
        rCrosshairYOffset.disable(bool, color)
        rCrosshairAlpha.disable(bool, color)
        rCrosshairColor.disable(bool)
    }
}

fun updateDisableRcsSmoothing() {
    rcsTab.apply {
        if (!opened) return

        val bool = !curSettings["ENABLE_RCS"].strToBool()
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
        rCrosshairXOffset.update()
        rCrosshairYOffset.update()
        rCrosshairAlpha.update()
        rCrosshairColor.update()
    }

    updateDisableRcsSmoothing()
    updateDisableRCrosshair()
}