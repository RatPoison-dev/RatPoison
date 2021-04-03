package rat.poison.ui.tabs.aimtabs

import com.kotcrab.vis.ui.widget.CollapsibleWidget
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.poison.ui.uiHelpers.aimTab.ATabVisSlider
import rat.poison.ui.uiHelpers.binds.VisBindTableCustom

class BacktrackTable: VisTable(false) {
    private val collapsibleTable = VisTable(false)
    val collapsibleWidget = CollapsibleWidget(collapsibleTable)

    //Init labels/sliders/boxes that show values here
    val backtrackEnableKey = VisCheckBoxCustom("Enable On Key", "ENABLE_BACKTRACK_ON_KEY")
    val backtrackKey = VisBindTableCustom("Backtrack Key", "BACKTRACK_KEY")
    val backtrackSpotted = VisCheckBoxCustom("Check Spotted", "BACKTRACK_SPOTTED")
    val backtrackWeaponEnabled = ATabVisCheckBox("Weapon Backtrack", "_BACKTRACK")
    val backtrackMS = ATabVisSlider("Backtrack MS", "_BACKTRACK_MS", 20f, 200f, 5f, true, width1 = 200F, width2 = 200F)

    init {
        collapsibleTable.apply {
            add(backtrackEnableKey).left().row()
            add(backtrackKey).left().row()

            add(backtrackSpotted).left().row()

            add(backtrackWeaponEnabled).left().row()
            add(backtrackMS).left().row()
        }

        add(collapsibleWidget).prefWidth(475F).growX()
    }
}