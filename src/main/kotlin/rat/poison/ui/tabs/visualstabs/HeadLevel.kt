package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.*
import rat.poison.ui.changed
import rat.poison.ui.tabs.footStepsEspTab
import rat.poison.ui.tabs.headLevelTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class HeadLevelTab : Tab(false, false) {
    private val table = VisTable()

    val enableHeadLevel = VisCheckBoxCustom(" ", "HEAD_LVL_ENABLE", false)
    val headLevelColor = VisColorPickerCustom("Enable", "HEAD_LVL_COLOR")
    val headLevelDeadzone = VisSliderCustom("Deadzone", "HEAD_LVL_DEADZONE", 1F, 45F, 1F, true, width1 = 225F, width2 = 225F)

    init {
        table.padLeft(25F)
        table.padRight(25F)

        val tmpTable = VisTable()
        tmpTable.add(enableHeadLevel)
        tmpTable.add(headLevelColor).width(175F - enableHeadLevel.width).padRight(50F)

        table.add(tmpTable).left().row()
        table.add(headLevelDeadzone).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "HeadLvl".toLocale()
    }
}

fun headLevelTabUpdate() {
    headLevelTab.apply {
        enableHeadLevel.update()
        headLevelColor.update()
        headLevelDeadzone.update()
    }
}