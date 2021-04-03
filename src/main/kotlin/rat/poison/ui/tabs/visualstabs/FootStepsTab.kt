package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.footStepsEspTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

val footstepItems = arrayOf("TEXT", "CIRCLE")

class FootstepsEspTable: VisTable(false) {
    val enableFootSteps = VisCheckBoxCustom("Enable", "ENABLE_FOOTSTEPS")
    val footStepType = VisSelectBox<String>()
    val footStepUpdateTimer = VisSliderCustom("Update Timer", "FOOTSTEP_UPDATE", 5F, 120F, 1F, true, 0, 175F, 117F)
    val footStepTTL = VisSliderCustom("TTL", "FOOTSTEP_TTL", 15F, 240F, 1F, true, 0, 175F, 117F)

    val footStepTeamBox = VisCheckBoxCustom("Teammates", "FOOTSTEP_TEAM", false)
    val footStepTeamColor = VisColorPickerCustom("Footstep Teammate Color","FOOTSTEP_TEAM_COLOR")

    val footStepEnemyBox = VisCheckBoxCustom("Enemies", "FOOTSTEP_ENEMY", false)
    val footStepEnemyColor = VisColorPickerCustom("Footstep Enemy Color", "FOOTSTEP_ENEMY_COLOR")

    init {
        val itemsArray = Array<String>()
        for (i in footstepItems) {
            itemsArray.add(i)
        }
        footStepType.items = itemsArray

        footStepType.selectedIndex = footstepItems.indexOf(when (curSettings.int["FOOTSTEP_TYPE"]) {
            1 -> "TEXT"
            else -> "CIRCLE"
        })

        footStepType.changed { _, _ ->
            curSettings["FOOTSTEP_TYPE"] = when (footstepItems[footStepType.selectedIndex]) { "TEXT" -> 1; else -> 2}
            true
        }

        val label = VisLabel("Footsteps")
        label.setColor(1F, 1F, 1F, 1F)

        add(label).colspan(2).expandX().padTop(4F).row()
        addSeparator().colspan(2).width(200F).top().height(2F).padBottom(8F)

        add(enableFootSteps).left()
        add(footStepType).left().row()
        add(footStepUpdateTimer).colspan(2).left().row()
        add(footStepTTL).colspan(2).left().row()

        add(footStepTeamBox).left().padRight(175F - footStepTeamBox.width)
        add(footStepTeamColor).left().expandX().row()

        add(footStepEnemyBox).left().padRight(175F - footStepEnemyBox.width)
        add(footStepEnemyColor).left().expandX().row()
    }
}

fun footStepsEspTabUpdate() {
    footStepsEspTable.apply {
        enableFootSteps.update()
        footStepUpdateTimer.update()
        footStepTTL.update()
        footStepTeamBox.update()
        footStepTeamColor.update()
        footStepEnemyBox.update()
        footStepEnemyColor.update()
    }
}

fun footStepsEspTableDisable(bool: Boolean, col: Color) {
    footStepsEspTable.enableFootSteps.disable(bool)
    footStepsEspTable.footStepType.isDisabled = bool
    footStepsEspTable.footStepUpdateTimer.disable(bool, col)
    footStepsEspTable.footStepTTL.disable(bool, col)
    footStepsEspTable.footStepTeamBox.disable(bool)
    footStepsEspTable.footStepTeamColor.disable(bool)
    footStepsEspTable.footStepEnemyBox.disable(bool)
    footStepsEspTable.footStepEnemyColor.disable(bool)
}