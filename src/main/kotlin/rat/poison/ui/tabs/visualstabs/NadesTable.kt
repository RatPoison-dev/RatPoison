package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curSettings
import rat.poison.ui.tabs.nadesTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class NadesTable: VisTable(false) {
    val nadeTracer = VisCheckBoxCustom("Nade Tracer", "NADE_TRACER", false)
    val nadeTracerColor = VisColorPickerCustom("Nade Tracer Color", "NADE_TRACER_COLOR")

    val nadeTracerUpdateTime = VisSliderCustom("Update Time", "NADE_TRACER_UPDATE_TIME", 5F, curSettings.int["OPENGL_FPS"].toFloat(), 1F, true, labelWidth = 175F, barWidth = 117F)
    val nadeTracerTimeout = VisSliderCustom("Timeout", "NADE_TRACER_TIMEOUT", 1F, 30F, 1F, true, labelWidth = 175F, barWidth = 117F)

    val visualizeSmokes = VisCheckBoxCustom("Visualize Smokes", "VISUALIZE_SMOKES", false)
    val visualizeSmokesColor = VisColorPickerCustom("Smoke Box Color", "VISUALIZE_SMOKES_COLOR")

    val visualizeSmokesPolys = VisSliderCustom("Smoke Poly Count", "VISUALIZE_SMOKES_POLYS", 3F, 25F, 1F, true, labelWidth = 175F, barWidth = 117F)
    val visualizeSmokesWidth = VisSliderCustom("Smoke Width", "VISUALIZE_SMOKES_WIDTH", 100F, 200F, 1F, true, labelWidth = 175F, barWidth = 117F)
    val visualizeSmokesHeight = VisSliderCustom("Smoke Height", "VISUALIZE_SMOKES_HEIGHT", 100F, 150F, 1F, true, labelWidth = 175F, barWidth = 117F)

    val drawSmokesTime = VisCheckBoxCustom("Smoke Time", "SMOKE_WEAR_OFF_TIME", false)
    val drawSmokesTimeColor = VisColorPickerCustom("Smoke Time Color", "SMOKE_WEAR_OFF_TIME_COLOR")

    init {
        val label = VisLabel("Nades")
        label.setColor(1F, 1F, 1F, 1F)

        add(label).colspan(2).expandX().padTop(4F).row()
        addSeparator().colspan(2).width(200F).top().height(2F).padBottom(8F)

        add(nadeTracer).left().padRight(175F - nadeTracer.width)
        add(nadeTracerColor).left().expandX().row()

        add(nadeTracerUpdateTime).colspan(2).left().row()
        add(nadeTracerTimeout).colspan(2).left().padBottom(8F).row()

        add(visualizeSmokes).left().padRight(175F - visualizeSmokes.width)
        add(visualizeSmokesColor).left().expandX().row()

        add(visualizeSmokesPolys).colspan(2).left().row()
        add(visualizeSmokesWidth).colspan(2).left().row()
        add(visualizeSmokesHeight).colspan(2).left().padBottom(8F).row()

        add(drawSmokesTime).left().padRight(175F - drawSmokesTime.width)
        add(drawSmokesTimeColor).left().expandX().row()
    }
}

fun nadesVTUpdate() {
    nadesTable.apply {
        nadeTracer.update()
        nadeTracerColor.update()
        nadeTracerUpdateTime.update()
        nadeTracerTimeout.update()
        visualizeSmokes.update()
        visualizeSmokesColor.update()
        visualizeSmokesPolys.update()
        visualizeSmokesWidth.update()
        visualizeSmokesHeight.update()
        drawSmokesTime.update()
        drawSmokesTimeColor.update()
    }
}

fun nadesTableDisable(bool: Boolean, col: Color) {
    nadesTable.apply {
        nadeTracer.disable(bool)
        nadeTracerColor.disable(bool)
        nadeTracerUpdateTime.disable(bool, col)
        nadeTracerTimeout.disable(bool, col)

        visualizeSmokes.disable(bool)
        visualizeSmokesColor.disable(bool)
        visualizeSmokesPolys.disable(bool, col)
        visualizeSmokesWidth.disable(bool, col)
        visualizeSmokesHeight.disable(bool, col)
        drawSmokesTime.disable(bool)
        drawSmokesTimeColor.disable(bool)
    }
}