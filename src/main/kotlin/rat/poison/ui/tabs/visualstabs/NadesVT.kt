package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.toLocale
import rat.poison.ui.tabs.nadesTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class NadesVT : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    val nadeTracer = VisCheckBoxCustom(" ", "NADE_TRACER", false)
    val nadeTracerColor = VisColorPickerCustom("Tracer", "NADE_TRACER_COLOR")

    val nadeTracerUpdateTime = VisSliderCustom("Tracer Update Time", "NADE_TRACER_UPDATE_TIME", 5F, curSettings["OPENGL_FPS"].toInt().toFloat(), 1F, true, width1 = 200F, width2 = 250F)
    val nadeTracerTimeout = VisSliderCustom("Tracer Timeout", "NADE_TRACER_TIMEOUT", 1F, 30F, 1F, true, width1 = 200F, width2 = 250F)

    val visualizeSmokes = VisCheckBoxCustom(" ", "VISUALIZE_SMOKES", false)
    val visualizeSmokesColor = VisColorPickerCustom("Smoke Box", "VISUALIZE_SMOKES_COLOR")
    val visualizeSmokesPolys = VisSliderCustom("Smoke Poly Count", "VISUALIZE_SMOKES_POLYS", 3F, 25F, 1F, true, width1 = 200F, width2 = 250F)
    val visualizeSmokesWidth = VisSliderCustom("Smoke Width", "VISUALIZE_SMOKES_WIDTH", 100F, 200F, 1F, true, width1 = 200F, width2 = 250F)
    val visualizeSmokesHeight = VisSliderCustom("Smoke Height", "VISUALIZE_SMOKES_HEIGHT", 100F, 150F, 1F, true, width1 = 200F, width2 = 250F)

    init {
        table.padLeft(25F)
        table.padRight(25F)

        var tmpTable = VisTable()
        tmpTable.add(nadeTracer)
        tmpTable.add(nadeTracerColor).width(175F - nadeTracer.width).padRight(50F)

        table.add(tmpTable).left().row()
        table.add(nadeTracerUpdateTime).row()
        table.add(nadeTracerTimeout).row()

        table.addSeparator()

        tmpTable = VisTable()
        tmpTable.add(visualizeSmokes)
        tmpTable.add(visualizeSmokesColor).width(175F - visualizeSmokes.width).padRight(50F)

        table.add(tmpTable).left().row()
        table.add(visualizeSmokesPolys).row()
        table.add(visualizeSmokesWidth).row()
        table.add(visualizeSmokesHeight).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Nades".toLocale()
    }
}

fun nadesVTUpdate() {
    nadesTab.apply {
        nadeTracer.update()
        nadeTracerColor.update()
        nadeTracerUpdateTime.update()
        nadeTracerTimeout.update()
        visualizeSmokes.update()
        visualizeSmokesColor.update()
        visualizeSmokesPolys.update()
        visualizeSmokesWidth.update()
        visualizeSmokesHeight.update()
    }
}