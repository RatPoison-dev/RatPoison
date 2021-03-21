

package rat.poison.ui.tabs.visualstabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.tabs.farEspTab
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.utils.WebSocket

class FarEspTab : Tab(false, false) {
    val table = VisTable()
    val farRadarBox = VisCheckBoxCustom("Far Radar", "BOX_FAR_RADAR")
    val sharedEsp = VisCheckBoxCustom("Shared ESP", "SHARED_ESP")
    init {
        table.padLeft(25F)
        table.padRight(25F)
        sharedEsp.changed { _, _ ->
            if (!sharedEsp.isChecked) {
                WebSocket.createSendTask("deleteInfo")
            }
            true
        }

        table.add(farRadarBox).left().row()
        table.add(sharedEsp).left().row()
    }

    override fun getTabTitle(): String {
        return "FarEsp".toLocale()
    }

    override fun getContentTable(): Table {
        return table
    }
}

fun farEspTabUpdate() {
    farEspTab.apply {
        farRadarBox.update()
        sharedEsp.update()
    }
}

fun farEspTabDisable(bool: Boolean, col: Color) {
    farEspTab.farRadarBox.disable(bool)
    farEspTab.sharedEsp.disable(bool)
}