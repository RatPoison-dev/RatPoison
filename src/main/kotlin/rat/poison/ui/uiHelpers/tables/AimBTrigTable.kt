package rat.poison.ui.uiHelpers.tables

import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.CURRENT_LOCALE
import rat.poison.curLocale
import rat.poison.dbg
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.tabs.categorySelected
import rat.poison.ui.tabs.gunCategories
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.poison.ui.uiHelpers.aimTab.ATabVisSlider
import rat.poison.ui.uiPanels.aimTab
import rat.poison.ui.uiUpdate

class AimBTrigTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val enableTrig = VisCheckBoxCustom("Enable Trigger", "ENABLE_TRIGGER")

    val boneTriggerEnableKey = VisCheckBoxCustom("Trigger On Key", "TRIGGER_ENABLE_KEY")
    val boneTriggerKey = VisInputFieldCustom("Trigger Key", "TRIGGER_KEY")

    val trigAimbot = ATabVisCheckBox("Aimbot", "_TRIGGER_AIMBOT")
    val trigInCross = ATabVisCheckBox("InCross", "_TRIGGER_INCROSS")
    val trigInFov = ATabVisCheckBox("InFov", "_TRIGGER_INFOV")
    val trigFov = ATabVisSlider("FOV", "_TRIGGER_FOV", 1F, 90F, 1F, true)
    val trigDelay = ATabVisSlider("Delay", "_TRIGGER_SHOT_DELAY", 0F, 500F, 10F, true)

    //Override Weapon Checkbox & Selection Box
    private val categorySelection = VisTable()
    val categorySelectionBox = VisSelectBox<String>()
    val categorySelectLabel = VisLabel("${"Weapon-Category".toLocale()}:")

    init {
        //Create Category Selector Box
        val itemsArray = Array<String>()
        for (i in gunCategories) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] $CURRENT_LOCALE $i is missing!")
            }

            itemsArray.add(curLocale[i])
        }

        categorySelectionBox.items = itemsArray
        categorySelectionBox.selectedIndex = 0

        categorySelected = gunCategories[categorySelectionBox.selectedIndex]
        categorySelection.add(categorySelectLabel).padRight(200F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox)

        categorySelectionBox.changed { _, _ ->
            categorySelected = gunCategories[categorySelectionBox.selectedIndex]
            aimTab.tAim.categorySelectionBox.selectedIndex = gunCategories.indexOf(categorySelected)
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
            add(boneTriggerEnableKey).left().row()
            add(boneTriggerKey).left().row()
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