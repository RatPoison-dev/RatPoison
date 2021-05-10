package rat.poison.ui.uiTabs.aimTables

import com.kotcrab.vis.ui.widget.CollapsibleWidget
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.uiElements.VisCheckBoxCustom
import rat.poison.ui.uiElements.aim.AimVisCheckBox
import rat.poison.ui.uiElements.aim.AimVisSlider
import rat.poison.ui.uiElements.binds.VisBindTableCustom

class BacktrackTable: VisTable(false) {
    private val collapsibleTable = VisTable(false)
    val collapsibleWidget = CollapsibleWidget(collapsibleTable)

    //Init labels/sliders/boxes that show values here
    val backtrackEnableKey = VisCheckBoxCustom("Backtrack On Key", "ENABLE_BACKTRACK_ON_KEY")
    val backtrackKey = VisBindTableCustom("Backtrack Key", "BACKTRACK_KEY", keyWidth = 225F)
    val backtrackSpotted = VisCheckBoxCustom("Check Spotted", "BACKTRACK_SPOTTED")
    val backtrackWeaponEnabled = AimVisCheckBox("Enable Backtrack", "_BACKTRACK")
    val backtrackMS = AimVisSlider("Backtrack MS", "_BACKTRACK_MS", 20f, 200f, 5f, true, width1 = 225F, width2 = 225F)

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