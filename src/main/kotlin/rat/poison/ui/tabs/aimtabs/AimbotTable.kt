package rat.poison.ui.tabs.aimtabs

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.CollapsibleWidget
import com.kotcrab.vis.ui.widget.VisCheckBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curSettings
import rat.poison.ui.changed
import rat.poison.ui.tabs.boneCategories
import rat.poison.ui.tabs.categorySelected
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisSelectBoxCustom
import rat.poison.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.poison.ui.uiHelpers.aimTab.ATabVisSlider
import rat.poison.utils.generalUtil.boolToStr

class AimbotTable: VisTable(false) {
    private val collapsibleTable = VisTable(false)
    val collapsibleWidget = CollapsibleWidget(collapsibleTable)

    val enableAimOnShot = ATabVisCheckBox("Aim On Shot", "_AIM_ONLY_ON_SHOT")
    val enableFactorRecoil = ATabVisCheckBox("Factor Recoil", "_FACTOR_RECOIL")
    val enableFlatAim = ATabVisCheckBox("Write Angles", "_ENABLE_FLAT_AIM")
    val enablePathAim = ATabVisCheckBox("Mouse Movement", "_ENABLE_PATH_AIM")
    val enableScopedOnly = VisCheckBoxCustom("Scoped Only", "SNIPER_ENABLE_SCOPED_ONLY")

    val aimBones = VisSelectBoxCustom("Aim Bone", "_AIM_BONE", useCategory = true, showText = true, items = boneCategories)
    val forceAimBone = VisSelectBoxCustom("Force Aim Bone", "_AIM_FORCE_BONE", useCategory = true, showText = true, items = boneCategories)

    val aimFov = ATabVisSlider("Aim FOV", "_AIM_FOV", .5F, 90F, .5F, false, 1, 200F, 200F)
    val aimSpeed = ATabVisSlider("Aim Speed", "_AIM_SPEED", 0F, 10F, 1F, true, 0, 200F, 200F)
    val aimSmooth = ATabVisSlider("Smoothness", "_AIM_SMOOTHNESS", 1F, 5F, .1F, false, 1, 200F, 200F)
    val aimAfterShots = ATabVisSlider("Aim After #", "_AIM_AFTER_SHOTS", 0F, 10F, 1F, true, 0, 200F, 200F)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox("Enable-Perfect-Aim")
    private val perfectAimTable = VisTable(false)
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFov = ATabVisSlider("FOV", "_PERFECT_AIM_FOV", 1F, 90F, .5F, false, 1, 200F, 200F)
    val perfectAimChance = ATabVisSlider("Chance", "_PERFECT_AIM_CHANCE", 1F, 100F, 1F, true, 0, 200F, 200F)

    //Advanced Settings Collapsible
    val advancedSettingsCheckBox = VisCheckBox("Advanced-Settings")
    private val advancedSettingsTable = VisTable(false)
    val advancedSettingsCollapsible = CollapsibleWidget(advancedSettingsTable)
    val randomizeX = ATabVisSlider("X Variation", "_RANDOM_X_VARIATION", 0F, 50F, 1F, true, 0, 200F, 200F)
    val randomizeY = ATabVisSlider("Y Variation", "_RANDOM_Y_VARIATION", 0F, 50F, 1F, true, 0, 200F, 200F)
    val randomizeDZ = ATabVisSlider("Variation Deadzone", "_VARIATION_DEADZONE", 0F, 100F, 5F, true, 0, 200F, 200F)
    val advancedRcsX = ATabVisSlider("RCS X", "_AIM_RCS_X", 0.05F, 1F, 0.05F, false, 2, 200F, 200F)
    val advancedRcsY = ATabVisSlider("RCS Y", "_AIM_RCS_Y", 0.05F, 1F, 0.05F, false, 2, 200F, 200F)
    val advancedRcsVariation = ATabVisSlider("RCS Variation", "_AIM_RCS_VARIATION", 0F, 1F, 0.05F, false, 2, 200F, 200F)
    val advancedSpeedDivisor = ATabVisSlider("Mouse Move Divisor", "_AIM_SPEED_DIVISOR", 1F, 10F, 1F, true, 0, 200F, 200F)

    init {
        //Disable on start, default is pistol
        aimAfterShots.disable(true, Color(255F, 255F, 255F, 0F))

        //Create Scoped Only Toggle
        enableScopedOnly.isChecked = curSettings.bool["SNIPER_ENABLE_SCOPED_ONLY"]
        enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
        enableScopedOnly.isDisabled = true

        //Create Perfect Aim Collapsible Check Box
        perfectAimCheckBox.isChecked = curSettings.bool[categorySelected + "_PERFECT_AIM"]
        perfectAimCheckBox.changed { _, _ ->
            curSettings[categorySelected + "_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
            perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
        }

        perfectAimCollapsible.setCollapsed(!curSettings.bool[categorySelected + "_PERFECT_AIM"], true)
        perfectAimTable.add(perfectAimFov).left().row()
        perfectAimTable.add(perfectAimChance).left().row()
        //End Perfect Aim Collapsible Check Box

        //Create Advanced Aim Settings Collapsible
        advancedSettingsCheckBox.isChecked = curSettings.bool[categorySelected + "_ADVANCED_SETTINGS"]
        advancedSettingsCheckBox.changed { _, _ ->
            curSettings[categorySelected + "_ADVANCED_SETTINGS"] = advancedSettingsCheckBox.isChecked.boolToStr()
            advancedSettingsCollapsible.setCollapsed(!advancedSettingsCollapsible.isCollapsed, true)
        }

        advancedSettingsCollapsible.setCollapsed(!curSettings.bool[categorySelected + "_ADVANCED_SETTINGS"], true)

        advancedSettingsTable.add(randomizeX).left().row()
        advancedSettingsTable.add(randomizeY).left().row()
        advancedSettingsTable.add(randomizeDZ).left().row()
        advancedSettingsTable.add(advancedRcsX).left().row()
        advancedSettingsTable.add(advancedRcsY).left().row()
        advancedSettingsTable.add(advancedRcsVariation).left().row()
        advancedSettingsTable.add(advancedSpeedDivisor).left().row()
        //End

        collapsibleTable.apply {
            var addTable = VisTable(false)
            addTable.add(enableFactorRecoil).left().padRight(224F - enableFactorRecoil.width)
            addTable.add(enableAimOnShot).left()
            add(addTable).left().row()

            addTable = VisTable(false)
            addTable.add(enableFlatAim).left().padRight(224F - enableFlatAim.width)
            addTable.add(enablePathAim).left()
            add(addTable).left().row()

            add(enableScopedOnly).left().row() //Sniper only

            add(aimBones).left().row()
            add(forceAimBone).left().row()
            add(aimSpeed).left().row()
            add(aimFov).left().row()
            add(aimSmooth).left().row()

            add(aimAfterShots).left().row() //Rifle + Smg only

            add(perfectAimCheckBox).left().row()
            add(perfectAimCollapsible).left().row()
            add(advancedSettingsCheckBox).left().row()
            add(advancedSettingsCollapsible).left().row()
        }

        add(collapsibleWidget).prefWidth(475F).growX()
    }
}