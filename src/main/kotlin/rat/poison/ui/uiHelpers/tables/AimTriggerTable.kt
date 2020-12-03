package rat.poison.ui.uiHelpers.tables

import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curLocale
import rat.poison.curSettings
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

class AimTriggerTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val enableTrig = VisCheckBoxCustom("Enable Trigger", "ENABLE_TRIGGER") //Master switch

    val boneTriggerEnableKey = VisCheckBoxCustom("Trigger On Key", "TRIGGER_ENABLE_KEY")
    val boneTriggerKey = VisInputFieldCustom("Trigger Key", "TRIGGER_KEY")

    val trigEnable = ATabVisCheckBox("Enable", "_TRIGGER") //Per weapon category

    val trigAimbot = ATabVisCheckBox("Aimbot", "_TRIGGER_AIMBOT")
    val trigInCross = ATabVisCheckBox("InCross", "_TRIGGER_INCROSS")
    val trigInFov = ATabVisCheckBox("InFov", "_TRIGGER_INFOV")
    val trigFov = ATabVisSlider("FOV", "_TRIGGER_FOV", .5F, 90F, .5F, false)
    val trigShootBacktrack = ATabVisCheckBox("Shoot Backtrack", "_TRIGGER_BACKTRACK")
    val initTrigDelay = ATabVisSlider("First Shot Delay", "_TRIGGER_INIT_SHOT_DELAY", 0F, 500F, 10F, true)
    val perShotTrigDelay = ATabVisSlider("Per Shot Delay", "_TRIGGER_PER_SHOT_DELAY", 0F, 500F, 10F, true)

    //Override Weapon Checkbox & Selection Box
    private val categorySelection = VisTable()
    val categorySelectionBox = VisSelectBox<String>()
    val categorySelectLabel = VisLabel("${"Weapon-Category".toLocale()}:")

    init {
        //Create Category Selector Box
        val itemsArray = Array<String>()
        for (i in gunCategories) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
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
            aimTab.tBacktrack.categorySelectionBox.selectedIndex = gunCategories.indexOf(categorySelected)
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
            add(trigEnable).left().row()

            add(trigAimbot).left().row()
            add(trigInCross).left().row()
            add(trigInFov).left().row()
            add(trigShootBacktrack).left().row()
            add(trigFov).left().row()
            add(initTrigDelay).left().row()
            add(perShotTrigDelay).left().row()

            addSeparator()
        }
    }
}