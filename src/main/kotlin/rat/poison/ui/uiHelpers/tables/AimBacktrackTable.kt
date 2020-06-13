package rat.poison.ui.uiHelpers.tables

import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.ui.uiPanels.aimTab
import rat.poison.ui.changed
import rat.poison.ui.tabs.categorySelected
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.poison.ui.uiHelpers.aimTab.ATabVisSlider
import rat.poison.ui.uiUpdate

class AimBacktrackTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here

    val enableBacktrack = VisCheckBoxCustom("Master Backtrack Switch", "ENABLE_BACKTRACK")
    val backtrackVisualize = VisCheckBoxCustom("Visualize", "BACKTRACK_VISUALIZE")
    val backtrackEnableKey = VisCheckBoxCustom("Backtrack On Key", "ENABLE_BACKTRACK_ON_KEY")
    val backtrackKey = VisInputFieldCustom("Backtrack Key", "BACKTRACK_KEY")
    val backtrackFOV = VisSliderCustom("Activation FOV", "BACKTRACK_FOV", 0.1f, 2f, .1f, false)
    val backtrackMS = VisSliderCustom("Max MS", "BACKTRACK_MS", 20f, 200f, 5f, true)
    val backtrackPreferAccurate = VisCheckBoxCustom("Prefer Accurate Records", "BACKTRACK_PREFER_ACCURATE")
    val backtrackSpotted = VisCheckBoxCustom("Check Visible", "BACKTRACK_SPOTTED")
    val backtrackWeaponEnabled = ATabVisCheckBox("Enable Weapon Backtrack", "_BACKTRACK")
    val backtrackWeaponNeck = ATabVisCheckBox("Neck", "_BACKTRACK_NECK")
    val backtrackWeaponChest = ATabVisCheckBox("Chest", "_BACKTRACK_CHEST")
    val backtrackWeaponStomach = ATabVisCheckBox("Stomach", "_BACKTRACK_STOMACH")
    val backtrackWeaponPelvis = ATabVisCheckBox("Pelvis", "_BACKTRACK_PELVIS")

    //Override Weapon Checkbox & Selection Box
    private val categorySelection = VisTable()
    val categorySelectionBox = VisSelectBox<String>()
    val categorySelectLabel = VisLabel("Weapon Category: ")

    init {
        //Create Category Selector Box
        categorySelectionBox.setItems("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
        categorySelectionBox.selected = "PISTOL"
        categorySelected = categorySelectionBox.selected
        categorySelection.add(categorySelectLabel).padRight(200F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox)

        categorySelectionBox.changed { _, _ ->
            categorySelected = categorySelectionBox.selected
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
            add(backtrackEnableKey).left().row()
            add(backtrackKey).left().row()

            add(backtrackFOV).left().row()
            add(backtrackMS).left().row()
            add(backtrackPreferAccurate).left().row()
            add(backtrackSpotted).left().row()

            add(categorySelection).left().row()
            add(backtrackWeaponEnabled).left().row()

            add(VisLabel("Bones")).left().row()
            add(backtrackWeaponNeck).left().row()
            add(backtrackWeaponChest).left().row()
            add(backtrackWeaponStomach).left().row()
            add(backtrackWeaponPelvis).left().row()

            addSeparator()
        }
    }
}