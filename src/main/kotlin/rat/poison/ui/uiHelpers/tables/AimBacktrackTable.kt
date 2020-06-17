package rat.poison.ui.uiHelpers.tables

import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.aimingMap
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.categorySelected
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisLabelCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.poison.ui.uiHelpers.binds.BindsRelatedCheckBox
import rat.poison.ui.uiPanels.aimTab
import rat.poison.ui.uiUpdate

class AimBacktrackTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here

    val enableBacktrack = BindsRelatedCheckBox(curLocalization["ENABLE_BACKTRACK"], "ENABLE_BACKTRACK", padLeft = 200F)
    val backtrackVisualize = VisCheckBoxCustom(curLocalization["BACKTRACK_VISUALIZE"], "BACKTRACK_VISUALIZE")
    val backtrackFOV = VisSliderCustom(curLocalization["FOV"], "BACKTRACK_FOV", 0.1f, 2f, .1f, false, nameInLocalization = "FOV")
    val backtrackMS = VisSliderCustom(curLocalization["BACKTRACK_MS"], "BACKTRACK_MS", 20f, 200f, 5f, true)
    val backtrackPreferAccurate = VisCheckBoxCustom(curLocalization["BACKTRACK_PREFER_ACCURATE"], "BACKTRACK_PREFER_ACCURATE")
    val backtrackSpotted = VisCheckBoxCustom(curLocalization["BACKTRACK_SPOTTED"], "BACKTRACK_SPOTTED")
    val backtrackWeaponEnabled = ATabVisCheckBox("Enable Weapon Backtrack", "_BACKTRACK", nameInLocalization = "ENABLE_WEAPON_BACKTRACK")
    val backtrackWeaponNeck = ATabVisCheckBox(curLocalization["NECK"], "_BACKTRACK_NECK", "NECK")
    val backtrackWeaponChest = ATabVisCheckBox(curLocalization["CHEST"], "_BACKTRACK_CHEST", "CHEST")
    val backtrackWeaponStomach = ATabVisCheckBox(curLocalization["STOMACH"], "_BACKTRACK_STOMACH" ,"STOMACH")
    val backtrackWeaponPelvis = ATabVisCheckBox(curLocalization["PELVIS"], "_BACKTRACK_PELVIS", "PELVIS")
    val bonesVisLabel = VisLabelCustom(curLocalization["BONES"], "BONES")
    //Override Weapon Checkbox & Selection Box
    private val categorySelection = VisTable()
    val categorySelectionBox = VisSelectBox<String>()
    val categorySelectLabel = VisLabel(curLocalization["WEAPON_CATEGORY"])
    var map = aimingMap()
    fun updateMap() {
        map = aimingMap()
    }
    init {
        //Create Category Selector Box
        categorySelectionBox.setItems(curLocalization["PISTOL"], curLocalization["RIFLE"], curLocalization["SMG"], curLocalization["SNIPER"], curLocalization["SHOTGUN"])
        categorySelectionBox.selected = curLocalization[curSettings["DEFAULT_CATEGORY_SELECTED"]]
        categorySelected = map[categorySelectionBox.selected]
        categorySelection.add(categorySelectLabel).padRight(200F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox)

        categorySelectionBox.changed { _, _ ->
            categorySelected = map[categorySelectionBox.selected]
            aimTab.tAim.categorySelectionBox.selected = categorySelected
            uiUpdate()
            true
        }

        //Default menu size width is 565
        //Default menu size heght is 535
        //Texts are 200
        //Sliders are 250
        //Leaves 25 for left and right side to center
        apply {
            padLeft(25F)
            padRight(25F)
            //Add all items to label for tabbed pane content
            add(enableBacktrack).left().row()
            add(backtrackVisualize).left().row()

            add(backtrackFOV).left().row()
            add(backtrackMS).left().row()
            add(backtrackPreferAccurate).left().row()
            add(backtrackSpotted).left().row()

            add(categorySelection).left().row()
            add(backtrackWeaponEnabled).left().row()

            add(bonesVisLabel).left().row()
            add(backtrackWeaponNeck).left().row()
            add(backtrackWeaponChest).left().row()
            add(backtrackWeaponStomach).left().row()
            add(backtrackWeaponPelvis).left().row()

            addSeparator()
        }
    }
}