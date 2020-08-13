package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.tabs.spreadCircleTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisColorPickerCustom

class SpreadCircleTab : Tab(false, false) {
    private val table = VisTable()

    val enableSpreadCircle = VisCheckBoxCustom(" ", "SPREAD_CIRCLE", false)
    val spreadCircleColor = VisColorPickerCustom("Enable", "SPREAD_CIRCLE_COLOR")

    init {
        table.padLeft(25F)
        table.padRight(25F)

        val tmpTable = VisTable()
        tmpTable.add(enableSpreadCircle)
        tmpTable.add(spreadCircleColor).width(175F - enableSpreadCircle.width).padRight(50F)

        table.add(tmpTable).left().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "SpreadCircle".toLocale()
    }
}

fun spreadCircleTabUpdate() {
    spreadCircleTab.apply {
        enableSpreadCircle.update()
        spreadCircleColor.update()
    }
}