package rat.poison.ui.uiTabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.crosshairArray
import rat.poison.curSettings
import rat.poison.overlay.opened
import rat.poison.ui.changed
import rat.poison.ui.uiElements.*
import rat.poison.ui.uiWindows.rcsTab
import rat.poison.utils.extensions.upper

class RcsTab : Tab(false, false) {
    private val table = VisTable(false)

    //Init labels/sliders/boxes that show values here
    val enableRCS = VisCheckBoxCustom("Enable RCS", "ENABLE_RCS")

    val rcsType = VisSelectBoxCustom("RCS Type", "RCS_TYPE", false, true, "STABLE", "LEGACY")

    val rcsSmoothingX = VisSliderCustom("X Accuracy", "RCS_SMOOTHING_X", .02F, 1F, .02F, false, labelWidth = 125F, barWidth = 125F)
    val rcsSmoothingY = VisSliderCustom("Y Accuracy", "RCS_SMOOTHING_Y", .02F, 1F, .02F, false, labelWidth = 125F, barWidth = 125F)

    val rcsReturnAim = VisCheckBoxCustom("Return Aim", "RCS_RETURNAIM")

    val enableRCrosshair = VisCheckBoxCustom("Recoil Crosshair", "ENABLE_RECOIL_CROSSHAIR")
    val enableSCrosshair = VisCheckBoxCustom("Scope Compatible", "ENABLE_SNIPER_CROSSHAIR")

    val rCrosshairXOffset = VisSliderCustom("X Offset", "RCROSSHAIR_XOFFSET", -48F, 48F, 1F, true, labelWidth = 100F, barWidth = 150F)
    val rCrosshairYOffset = VisSliderCustom("Y Offset", "RCROSSHAIR_YOFFSET", -48F, 48F, 1F, true, labelWidth = 100F, barWidth = 150F)

    val rCrosshairColor = VisColorPickerCustom("Color", "RCROSSHAIR_COLOR")

    var crosshairBuilderTable = VisTable()

    val rCrosshairBuilderResolution = VisSliderCustom("Resolution", "RCROSSHAIR_BUILDER_RESOLUTION", 1F, 9F, 1.9F, true, 0, labelWidth = 100F, barWidth = 150F)
    val rCrosshairBuilderSize = VisSliderCustom("Box Size", "RCROSSHAIR_BUILDER_SIZE", 5F, 100F, 1F, true, 0, labelWidth = 100F, barWidth = 150F)

    init {
        rCrosshairBuilderResolution.changed { _, _ ->
            crosshairArray.clear()

            buildTable()

            true
        }

        buildTable()
    }

    private fun buildTable() {
        crosshairBuilderTable = createCrosshairBuilder()

        table.clear()

        table.add(enableRCS).colspan(2).left().row()
        table.add(rcsType).colspan(2).left().row()
        table.add(rcsSmoothingX).colspan(2).left().row()
        table.add(rcsSmoothingY).colspan(2).left().row()
        table.add(rcsReturnAim).colspan(2).left().padBottom(32F).row()

        table.addSeparator().colspan(2)

        table.add(crosshairBuilderTable).colspan(2).left().row()

        table.add(enableRCrosshair).left().row()
        table.add(rCrosshairColor).left().expandX().row()

        table.add(rCrosshairBuilderResolution).colspan(2).left().row()
        table.add(rCrosshairBuilderSize).colspan(2).left().row()

        table.add(enableSCrosshair).colspan(2).left().row()
        table.add(rCrosshairXOffset).colspan(2).left().row()
        table.add(rCrosshairYOffset).colspan(2).left().row()
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "RCS"
    }
}

fun createCrosshairBuilder(): VisTable {
    val builderRes = curSettings["RCROSSHAIR_BUILDER_RESOLUTION"].toInt()
    val table = VisTable()

    for (i in 1..builderRes) {
        for (j in 1..builderRes) {
            val box = VisRCSBoxCustom(i, j)

            table.add(box).size(25F)

            if (j == builderRes) {
                table.row()
            }
        }
    }

    return table
}

fun updateDisableRCrosshair() {
    rcsTab.apply {
        if (!opened) return

        val bool = !(curSettings.bool["ENABLE_RECOIL_CROSSHAIR"] || curSettings.bool["ENABLE_SNIPER_CROSSHAIR"])
        var color = Color(255F, 255F, 255F, 1F)
        if (bool) {
            color = Color(105F, 105F, 105F, .2F)
        }

        enableSCrosshair.disable(bool)

        if (bool) {
            rCrosshairColor.disable(true)
        } else {
            if (curSettings["RCROSSHAIR_TYPE"].upper() == "CROSSHAIR") {
                rCrosshairColor.disable(false)
            } else {
                rCrosshairColor.disable(true)
            }
        }

        rCrosshairXOffset.disable(bool, color)
        rCrosshairYOffset.disable(bool, color)
        rCrosshairColor.disable(bool)
        rCrosshairBuilderResolution.disable(bool, color)
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
        enableSCrosshair.update()
        rCrosshairBuilderResolution.update()
        rCrosshairXOffset.update()
        rCrosshairYOffset.update()
        rCrosshairColor.update()

        rCrosshairBuilderSize.update()

        for (actor in rcsTab.crosshairBuilderTable.children) {
            with (actor as VisRCSBoxCustom) {
                update()
            }
        }
    }

    updateDisableRcsSmoothing()
    updateDisableRCrosshair()
}