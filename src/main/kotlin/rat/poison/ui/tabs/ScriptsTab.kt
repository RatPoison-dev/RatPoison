package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.Tooltip
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.scripts.esp.disableEsp
import rat.poison.settings.*
import rat.poison.ui.*

class ScriptsTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here

    init {

        //Add all items to label for tabbed pane content

    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Scripts"
    }
}