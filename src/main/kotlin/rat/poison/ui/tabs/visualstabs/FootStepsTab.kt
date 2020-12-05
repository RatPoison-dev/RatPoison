package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.tabs.footStepsEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

val footstepItems = arrayOf("TEXT", "CIRCLE")

class FootstepsEspTab : Tab(false, false) {
    private val table = VisTable()

    val enableFootSteps = VisCheckBoxCustom("Enable", "ENABLE_FOOTSTEPS")
    val footStepType = VisSelectBox<String>()
    val footStepUpdateTimer = VisSliderCustom("Update Timer", "FOOTSTEP_UPDATE", 5F, 120F, 1F, true)
    val footStepTTL = VisSliderCustom("TTL", "FOOTSTEP_TTL", 15F, 240F, 1F, true)

    val footStepTeamBox = VisCheckBoxCustom(" ", "FOOTSTEP_TEAM", false)
    val footStepTeamColor = VisColorPickerCustom("Teammates","FOOTSTEP_TEAM_COLOR")

    val footStepEnemyBox = VisCheckBoxCustom(" ", "FOOTSTEP_ENEMY", false)
    val footStepEnemyColor = VisColorPickerCustom("Enemies", "FOOTSTEP_ENEMY_COLOR")

    init {
        val itemsArray = Array<String>()
        for (i in footstepItems) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
            }

            itemsArray.add(curLocale[i])
        }
        footStepType.items = itemsArray

        footStepType.selectedIndex = footstepItems.indexOf(when (curSettings["FOOTSTEP_TYPE"].toInt()) {
            1 -> "TEXT"
            else -> "CIRCLE"
        })

        footStepType.changed { _, _ ->
            curSettings["FOOTSTEP_TYPE"] = when (footstepItems[footStepType.selectedIndex]) { "TEXT" -> 1; else -> 2}
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
        return "FootSteps".toLocale()
    }
}

fun footStepsEspTabUpdate() {
    footStepsEspTab.apply {
        enableFootSteps.update()
        footStepUpdateTimer.update()
        footStepTTL.update()
        footStepTeamBox.update()
        footStepTeamColor.update()
        footStepEnemyBox.update()
        footStepEnemyColor.update()
    }
}