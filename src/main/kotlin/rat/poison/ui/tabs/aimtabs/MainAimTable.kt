package rat.poison.ui.tabs.aimtabs

import com.kotcrab.vis.ui.widget.*
import rat.poison.ui.changed
import rat.poison.ui.tabs.visualstabs.updateDisableDrawFOV
import rat.poison.ui.uiHelpers.*
import rat.poison.ui.uiHelpers.binds.VisBindTableCustom
import rat.poison.ui.uiPanels.aimTab

class MainAimTable: VisTable(false) {
    val enableAim = VisCheckBoxCustom("Aimbot Master Switch", "ENABLE_AIM")
    val enableTrig = VisCheckBoxCustom("Trigger Master Switch", "ENABLE_TRIGGER") //Master switch
    val enableBacktrack = VisCheckBoxCustom("Backtrack Master Switch", "ENABLE_BACKTRACK")

    val aimToggleKey = VisBindTableCustom("Toggle Aim Key", "AIM_TOGGLE_KEY")
    val activateFromFireKey = VisCheckBoxCustom("Activate From Fire Key", "ACTIVATE_FROM_AIM_KEY")
    val holdAim = VisCheckBoxCustom("Hold Aim", "HOLD_AIM")
    val teammatesAreEnemies = VisCheckBoxCustom("Teammates Are Enemies", "TEAMMATES_ARE_ENEMIES")

    val fovType = VisSelectBoxCustom("Fov Type", "FOV_TYPE", false, true, "DISTANCE", "STATIC")

    val forceAimBoneKey = VisBindTableCustom("Force Aim Bone Key", "FORCE_AIM_BONE_KEY")
    val forceAimKey = VisBindTableCustom("Force Aim Key", "FORCE_AIM_KEY")
    val forceAimAlways = VisCheckBoxCustom("Force Aim Always", "FORCE_AIM_ALWAYS")
    val forceAimThroughWalls = VisCheckBoxCustom("Force Aim Through Walls", "FORCE_AIM_THROUGH_WALLS")

    //Automatic Weapons Collapsible
    val automaticWeaponsCheckBox = VisCheckBoxCustom("Automatic Weapons", "GLOBAL_AUTOMATIC_WEAPONS")
    val automaticWeaponsInput = VisInputFieldCustom("MS Delay", "GLOBAL_AUTO_WEP_DELAY", false)

    val targetSwapDelay = VisSliderCustom("Target Swap Delay", "AIM_TARGET_SWAP_DELAY", 0F, 500F, 10F, true, labelWidth = 200F, barWidth = 250F)

    init {
        fovType.changed {_, _ ->
            updateDisableDrawFOV()
        }

        enableAim.changed { _, _ ->
            aimTab.tAimbot.collapsibleWidget.isCollapsed = !enableAim.isChecked
            true
        }

        enableTrig.changed{ _, _ ->
            aimTab.tTrig.collapsibleWidget.isCollapsed = !enableTrig.isChecked
            true
        }

        enableBacktrack.changed { _, _ ->
            aimTab.tBacktrack.collapsibleWidget.isCollapsed = !enableBacktrack.isChecked
            true
        }

        enableAim.backgroundImage.setColor(1F, .1F, .1F, 1F)

        //End

        //Default menu size is 500
        //Texts are 200
        //Sliders are 250
        //Leaves 25 for left and right side to center
        padLeft(25F)
        padRight(25F)

        //Add all items to label for tabbed pane content

        add(enableAim).left().padBottom(8F).row()
        add(enableTrig).left().padBottom(8F).row()
        add(enableBacktrack).left().padBottom(8F).row()
        add(aimToggleKey).left().row()

        add(activateFromFireKey).left().row()
        add(teammatesAreEnemies).left().row()
        add(holdAim).left().row()
        add(targetSwapDelay).left().row()
        add(fovType).left().row()

        addSeparator()

        add(forceAimBoneKey).left().row()
        add(forceAimKey).left().row()
        add(forceAimAlways).left().row()
        add(forceAimThroughWalls).left().row()

        addSeparator()

        add(automaticWeaponsCheckBox).left().row()
        add(automaticWeaponsInput).left().row()

        addSeparator()
    }
}