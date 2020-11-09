package rat.poison.ui.uiHelpers.ranksTab

import com.kotcrab.vis.ui.widget.VisCheckBox
import rat.poison.ui.changed
import rat.poison.ui.tabs.rankstabs.enableEspPlayerList

class RanksVisCheckBoxCustom(steamID: Int) : VisCheckBox("") {

    init {
        this.isChecked = steamID in enableEspPlayerList
        changed {_, _ ->
            when (this.isChecked) {
                true -> enableEspPlayerList.add(steamID)
                false -> enableEspPlayerList.remove(steamID)
            }
            true
        }
    }
}