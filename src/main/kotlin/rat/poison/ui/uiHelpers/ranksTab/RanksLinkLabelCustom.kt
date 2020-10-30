package rat.poison.ui.uiHelpers.ranksTab

import com.kotcrab.vis.ui.widget.LinkLabel
import rat.poison.curSettings
import rat.poison.overlay.App.uiRanksWindow
import rat.poison.ui.rightClicked
import rat.poison.utils.generalUtil.strToBool

//get rid of this bitch one day
class RanksLinkLabelCustom(mainText: String, url: String, steamID: Int): LinkLabel(mainText, url) {
    init {
        rightClicked {_, _, _, _, _ ->
            curSettings["RANKS"] = !curSettings["RANKS"].strToBool()
            uiRanksWindow.bindToUser(mainText, steamID)
            return@rightClicked true
        }
    }
}