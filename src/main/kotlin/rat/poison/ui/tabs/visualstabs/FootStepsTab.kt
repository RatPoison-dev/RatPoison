package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.footStepsEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class FootstepsEspTab : Tab(false, false) {
    private val table = VisTable()

    val enableFootSteps = VisCheckBoxCustom(curLocalization["ENABLE"], "ENABLE_FOOTSTEPS", nameInLocalization = "ENABLE")
    val footStepType = VisSelectBox<String>()
    val footStepUpdateTimer = VisSliderCustom(curLocalization["FOOTSTEP_UPDATE"], "FOOTSTEP_UPDATE", 5F, 120F, 1F, true, nameInLocalization = "FOOTSTEP_UPDATE")
    val footStepTTL = VisSliderCustom(curLocalization["FOOTSTEP_TTL"], "FOOTSTEP_TTL", 15F, 240F, 1F, true, nameInLocalization = "FOOTSTEP_TTL")

    val footStepTeamBox = VisCheckBoxCustom(" ", "FOOTSTEP_TEAM")
    val footStepTeamColor = VisColorPickerCustom(curLocalization["TEAMMATES"],"FOOTSTEP_TEAM_COLOR", nameInLocalization = "TEAMMATES")

    val footStepEnemyBox = VisCheckBoxCustom(" ", "FOOTSTEP_ENEMY")
    val footStepEnemyColor = VisColorPickerCustom(curLocalization["ENEMIES"], "FOOTSTEP_ENEMY_COLOR", nameInLocalization = "ENEMIES")

    init {
        footStepType.setItems(curLocalization["FOOTSTEPS_TEXT"], curLocalization["FOOTSTEPS_CIRCLE"])
        footStepType.selected = when (curSettings["FOOTSTEP_TYPE"].toInt()) {
            1 -> curLocalization["FOOTSTEPS_TEXT"]
            else -> curLocalization["FOOTSTEPS_CIRCLE"]
        }
        footStepType.changed { _, _ ->
            curSettings["FOOTSTEP_TYPE"] = when (footStepType.selected) {
                curLocalization["FOOTSTEPS_TEXT"] -> 1;
                else -> 2
            }
            true
        }


        table.padLeft(25F)
        table.padRight(25F)

        table.add(enableFootSteps).padRight(225F - enableFootSteps.width)
        table.add(footStepType).padRight(225F - footStepType.width).row()
        table.add(footStepUpdateTimer).colspan(2).left().row()
        table.add(footStepTTL).colspan(2).left().row()

        var tmpTable = VisTable()
        tmpTable.add(footStepTeamBox)
        tmpTable.add(footStepTeamColor).width(175F - footStepTeamBox.width).padRight(50F)

        table.add(tmpTable).left()

        tmpTable = VisTable()
        tmpTable.add(footStepEnemyBox)
        tmpTable.add(footStepEnemyColor).width(175F - footStepEnemyBox.width).padRight(50F)

        table.add(tmpTable).left()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return curLocalization["FOOTSTEPS_TAB_NAME"]
    }
}

fun footStepsEspTabUpdate() {
    footStepsEspTab.apply {
        footStepType.setItems(curLocalization["FOOTSTEPS_TEXT"], curLocalization["FOOTSTEPS_CIRCLE"])
        footStepType.selected = when (curSettings["FOOTSTEP_TYPE"].toInt()) {
            1 -> curLocalization["FOOTSTEPS_TEXT"]
            else -> curLocalization["FOOTSTEPS_CIRCLE"]
        }
        enableFootSteps.update()
        footStepUpdateTimer.update()
        footStepTTL.update()
        footStepTeamBox.update()
        footStepTeamColor.update()
        footStepEnemyBox.update()
        footStepEnemyColor.update()
    }
}