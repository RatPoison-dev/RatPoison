package rat.poison.ui.uiHelpers.ranksTab

import com.kotcrab.vis.ui.widget.LinkLabel
import rat.poison.ui.rightClicked
import rat.poison.ui.tabs.enableEspPlayerList

class RanksLinkLabelCustom(mainText: String, url: String, steamID: Int): LinkLabel(mainText, url) {
    init {
        rightClicked {_, _, _, _, _ ->
            if (enableEspPlayerList.contains(steamID)) {
                enableEspPlayerList.remove(steamID)
            }
            else {
                enableEspPlayerList.add(steamID)
            }
            return@rightClicked true
        }
    }
}