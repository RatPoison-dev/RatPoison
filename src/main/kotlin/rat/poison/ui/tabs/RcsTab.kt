package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiHelpers.binds.BindsRelatedCheckBox
import rat.poison.ui.uiPanels.rcsTab
import rat.poison.utils.varUtil.strToBool

class RcsTab : Tab(false, false) {
    private val table = VisTable(false)

    //Init labels/sliders/boxes that show values here
    val enableRCS = BindsRelatedCheckBox(curLocalization["ENABLE_RCS"], "ENABLE_RCS", nameInLocalization = "ENABLE_RCS")
    val rcsSmoothingX = VisSliderCustom(curLocalization["RCS_SMOOTHING_X"], "RCS_SMOOTHING_X", .1F, 1F, .02F, false, width1 = 200F, width2 = 250F, nameInLocalization = "RCS_SMOOTHING_X")
    val rcsSmoothingY = VisSliderCustom(curLocalization["RCS_SMOOTHING_Y"], "RCS_SMOOTHING_Y", .1F, 1F, .02F, false, width1 = 200F, width2 = 250F, nameInLocalization = "RCS_SMOOTHING_Y")

    val rcsReturnAim = VisCheckBoxCustom(curLocalization["RCS_RETURNAIM"], "RCS_RETURNAIM", nameInLocalization = "RCS_RETURNAIM")

    val enableRCrosshair = VisCheckBoxCustom(curLocalization["ENABLE_RECOIL_CROSSHAIR"], "ENABLE_RECOIL_CROSSHAIR", nameInLocalization = "ENABLE_RECOIL_CROSSHAIR")
    val enableSCrosshair = VisCheckBoxCustom(curLocalization["ENABLE_SNIPER_CROSSHAIR"], "ENABLE_SNIPER_CROSSHAIR", nameInLocalization = "ENABLE_SNIPER_CROSSHAIR")

    val rCrosshairType = VisSelectBox<String>()
    val rCrosshairWidth = VisSliderCustom(curLocalization["RCROSSHAIR_WIDTH"], "RCROSSHAIR_WIDTH", 1F, 5F, 1F, true, width1 = 200F, width2 = 250F, nameInLocalization = "RCROSSHAIR_WIDTH")
    val rCrosshairLength = VisSliderCustom(curLocalization["RCROSSHAIR_LENGTH"], "RCROSSHAIR_LENGTH", 3F, 100F, 1F, true, width1 = 200F, width2 = 250F, nameInLocalization = "RCROSSHAIR_LENGTH")
    val rCrosshairXOffset = VisSliderCustom(curLocalization["RCROSSHAIR_XOFFSET"], "RCROSSHAIR_XOFFSET", -48F, 48F, 1F, true, width1 = 200F, width2 = 250F, nameInLocalization = "RCROSSHAIR_XOFFSET")
    val rCrosshairYOffset = VisSliderCustom(curLocalization["RCROSSHAIR_YOFFSET"], "RCROSSHAIR_YOFFSET", -48F, 48F, 1F, true, width1 = 200F, width2 = 250F, nameInLocalization = "RCROSSHAIR_YOFFSET")
    val rCrosshairAlpha = VisSliderCustom(curLocalization["RCROSSHAIR_ALPHA"], "RCROSSHAIR_ALPHA", .1F, 1F, .1F, false, width1 = 200F, width2 = 250F, nameInLocalization = "RCROSSHAIR_ALPHA")

    val rCrosshairColor = VisColorPickerCustom(curLocalization["RCROSSHAIR_COLOR"], "RCROSSHAIR_COLOR", nameInLocalization = "RCROSSHAIR_COLOR")

    init {
        ////////////////////FORMATTING
        table.padLeft(25F)
        table.padRight(25F)

        rCrosshairType.setItems(curLocalization["RCROSSHAIR_TYPE_CROSSHAIR"], curLocalization["RCROSSHAIR_TYPE_CIRCLE"])
        rCrosshairType.selected = curLocalization[curSettings["RCROSSHAIR_TYPE"]]
        rCrosshairType.changed { _, _ ->
            if (rCrosshairType.selected == curLocalization["RCROSSHAIR_TYPE_CROSSHAIR"]) {
                curSettings["RCROSSHAIR_TYPE"] = "RCROSSHAIR_TYPE_CROSSHAIR"
            } else {
                curSettings["RCROSSHAIR_TYPE"] = "RCROSSHAIR_TYPE_CIRCLE"
            }
        }
        table.add(enableRCS).left().row()
        table.add(rcsSmoothingX).left().row()
        table.add(rcsSmoothingY).left().row()
        table.add(rcsReturnAim).left().row()
        table.addSeparator()
        val tmpTable = VisTable()
        tmpTable.add(enableRCrosshair).left()
        tmpTable.add(rCrosshairType).padLeft(200F - enableRCrosshair.width)
        table.add(tmpTable).left().row()
        table.add(enableSCrosshair).left().row()
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
        return curLocalization["RCS_TAB_NAME"]
    }
}

fun updateDisableRCrosshair() {
    rcsTab.apply {
        val bool = !(curSettings["ENABLE_RECOIL_CROSSHAIR"].strToBool() || curSettings["ENABLE_SNIPER_CROSSHAIR"].strToBool())
        var color = Color(255F, 255F, 255F, 1F)
        if (bool) {
            color = Color(105F, 105F, 105F, .2F)
        }

        rCrosshairWidth.disable(bool, color)
        rCrosshairLength.disable(bool, color)
        rCrosshairXOffset.disable(bool, color)
        rCrosshairYOffset.disable(bool, color)
        rCrosshairAlpha.disable(bool, color)
        rCrosshairColor.disable(bool)
    }
}

fun updateDisableRcsSmoothing() {
    rcsTab.apply {
        val bool = !curSettings["ENABLE_RCS"].strToBool()
        var color = Color(255F, 255F, 255F, 1F)
        if (bool) {
            color = Color(105F, 105F, 105F, .2F)
        }

        rcsSmoothingX.disable(bool, color)
        rcsSmoothingY.disable(bool, color)
        rcsReturnAim.isDisabled = bool
    }
}

fun rcsTabUpdate() {
    rcsTab.apply {
        enableRCS.update()
        rcsSmoothingX.update()
        rcsSmoothingY.update()
        rcsReturnAim.update()
        enableRCrosshair.update()
        enableSCrosshair.update()
        rCrosshairWidth.update()
        rCrosshairLength.update()
        rCrosshairXOffset.update()
        rCrosshairYOffset.update()
        rCrosshairAlpha.update()
        rCrosshairColor.update()
        rCrosshairColor.updateTitle()
    }

    updateDisableRcsSmoothing()
    updateDisableRCrosshair()
}