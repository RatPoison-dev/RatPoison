package rat.poison.ui.uiTabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.ui.uiTabs.miscTables.BombTable
import rat.poison.ui.uiTabs.miscTables.FOVChangerTable
import rat.poison.ui.uiTabs.miscTables.MovementTable

var movementTable = MovementTable()
var fovChangerTable = FOVChangerTable()
var bombTable = BombTable()
class MiscTabs: Tab(false, false) {
    private val table = VisTable(false)

    init {
        buildTable()
    }

    private fun buildTable() {
        table.clear()

        val leftTable = VisTable()

        leftTable.add(movementTable).width(470F).growX().left().row()
        leftTable.addSeparator()
        leftTable.add(fovChangerTable).width(470F).growX().top().padTop(2F).left().row()
        leftTable.addSeparator()
        leftTable.add(bombTable).width(470F).growX().top().padTop(2F).left()

        table.add(leftTable).width(470F).left().top()
        table.addSeparator(true)
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "Misc"
    }
}