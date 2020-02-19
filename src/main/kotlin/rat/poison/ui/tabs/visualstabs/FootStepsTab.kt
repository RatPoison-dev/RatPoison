package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.footStepsEspTab
import rat.poison.ui.tabs.snaplinesEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class FootstepsEspTab : Tab(false, false) {
    private val table = VisTable()

    val enableFootSteps = VisCheckBoxCustom("Enable", "ENABLE_FOOTSTEPS")
    val footStepType = VisSelectBox<String>()
    val footStepUpdateTimer = VisSliderCustom("Update Timer", "FOOTSTEP_UPDATE", 5F, 120F, 1F, true)
    val footStepTTL = VisSliderCustom("TTL", "FOOTSTEP_TTL", 15F, 240F, 1F, true)

    init {
        footStepType.setItems("Text", "Circle")
        footStepType.selected = when (curSettings["FOOTSTEP_TYPE"].toInt()) {
            1 -> "Text"
            else -> "Circle"
        }
        footStepType.changed { _, _ ->
            curSettings["FOOTSTEP_TYPE"] = when (footStepType.selected) { "Text" -> 1; else -> 2}
            true
        }


        table.padLeft(25F)
        table.padRight(25F)

        table.add(enableFootSteps).padRight(225F - enableFootSteps.width)
        table.add(footStepType).padRight(225F - footStepType.width).row()
        table.add(footStepUpdateTimer).colspan(2).left().row()
        table.add(footStepTTL).colspan(2).left().row()

    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "FootSteps"
    }
}

fun footStepsEspTabUpdate() {
    footStepsEspTab.apply {
        enableFootSteps.update()
        footStepUpdateTimer.update()
        footStepTTL.update()
    }
}