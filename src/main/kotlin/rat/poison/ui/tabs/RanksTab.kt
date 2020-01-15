package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab

lateinit var ranksListTable : VisTable

class RanksTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here

    init {
        ranksListTable = VisTable(true)
        //Add all items to label for tabbed pane content
        table.add(ranksListTable)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Ranks"
    }
}