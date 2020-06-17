package rat.poison.ui.uiHelpers.tables

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
import rat.poison.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.poison.ui.uiHelpers.aimTab.ATabVisSlider
import rat.poison.ui.uiHelpers.binds.BindsRelatedCheckBox
import rat.poison.ui.uiPanels.aimTab
import rat.poison.ui.uiUpdate

class AimBTrigTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val enableTrig = BindsRelatedCheckBox(curLocalization["ENABLE_TRIGGER"], "ENABLE_TRIGGER", nameInLocalization = "ENABLE_TRIGGER", padLeft = 200F)

    val trigAimbot = ATabVisCheckBox(curLocalization["TRIGGER_AIMBOT"], "_TRIGGER_AIMBOT", nameInLocalization = "TRIGGER_AIMBOT")
    val trigInCross = ATabVisCheckBox(curLocalization["TRIGGER_IS_IN_CROSS"], "_TRIGGER_INCROSS", nameInLocalization = "TRIGGER_IS_IN_CROSS")
    val trigInFov = ATabVisCheckBox(curLocalization["TRIGGER_IS_IN_FOV"], "_TRIGGER_INFOV", nameInLocalization = "TRIGGER_IS_IN_FOV")
    val trigFov = ATabVisSlider(curLocalization["FOV"], "_TRIGGER_FOV", 1F, 90F, 1F, true, nameInLocalization = "FOV")
    val trigDelay = ATabVisSlider(curLocalization["TRIGGER_DELAY"], "_TRIGGER_SHOT_DELAY", 0F, 500F, 10F, true, nameInLocalization = "TRIGGER_DELAY")

    //Override Weapon Checkbox & Selection Box
    private val categorySelection = VisTable()
    val categorySelectionBox = VisSelectBox<String>()
    val categorySelectLabel = VisLabelCustom(curLocalization["WEAPON_CATEGORY"], nameInLocalization = "WEAPON_CATEGORY")

    init {
        val map = aimingMap()
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

            add(enableTrig).left().row()
            add(categorySelection).left().row()
            add(trigAimbot).left().row()
            add(trigInCross).left().row()
            add(trigInFov).left().row()
            add(trigFov).left().row()
            add(trigDelay).left().row()

            addSeparator()
        }
    }
}