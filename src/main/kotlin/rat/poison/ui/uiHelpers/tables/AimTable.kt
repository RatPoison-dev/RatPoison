package rat.poison.ui.uiHelpers.tables

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.widget.CollapsibleWidget
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.aimingMap
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.settings.*
import rat.poison.toWeaponClass
import rat.poison.ui.changed
import rat.poison.ui.tabs.categorySelected
import rat.poison.ui.tabs.updateDisableEsp
import rat.poison.ui.uiHelpers.*
import rat.poison.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.poison.ui.uiHelpers.aimTab.ATabVisSlider
import rat.poison.ui.uiHelpers.binds.BindsRelatedCheckBox
import rat.poison.ui.uiPanelTables.weaponOverrideSelected
import rat.poison.ui.uiPanels.aimTab
import rat.poison.ui.uiPanels.overridenWeapons
import rat.poison.ui.uiUpdate
import rat.poison.utils.varUtil.boolToStr
import rat.poison.utils.varUtil.strToBool

class AimTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val enableAim = BindsRelatedCheckBox(curLocalization["ENABLE_AIM"], "ENABLE_AIM", nameInLocalization = "ENABLE_AIM")
    val activateFromFireKey = VisCheckBoxCustom(curLocalization["ACTIVATE_FROM_AIM_KEY"], "ACTIVATE_FROM_AIM_KEY", nameInLocalization = "ACTIVATE_FROM_AIM_KEY")
    val holdAim = VisCheckBoxCustom(curLocalization["HOLD_AIM"], "HOLD_AIM", nameInLocalization = "HOLD_AIM")
    val teammatesAreEnemies = VisCheckBoxCustom(curLocalization["TEAMMATES_ARE_ENEMIES"], "TEAMMATES_ARE_ENEMIES", nameInLocalization = "TEAMMATES_ARE_ENEMIES")

    val fovTypeLabel = VisLabelCustom(curLocalization["FOV_TYPE"], nameInLocalization = "FOV_TYPE")
    val fovTypeBox = VisSelectBox<String>()

    val forceAimBoneKey = VisInputFieldCustom(curLocalization["FORCE_AIM_BONE_KEY"], "FORCE_AIM_BONE_KEY", nameInLocalization = "FORCE_AIM_BONE_KEY")
    val forceAimKey = VisInputFieldCustom(curLocalization["FORCE_AIM_KEY"], "FORCE_AIM_KEY", nameInLocalization = "FORCE_AIM_KEY")
    val forceAimAlways = VisCheckBoxCustom(curLocalization["FORCE_AIM_ALWAYS"], "FORCE_AIM_ALWAYS", nameInLocalization = "FORCE_AIM_ALWAYS")
    val forceAimThroughWalls = VisCheckBoxCustom(curLocalization["FORCE_AIM_THROUGH_WALLS"], "FORCE_AIM_THROUGH_WALLS", nameInLocalization = "FORCE_AIM_THROUGH_WALLS")

    //Automatic Weapons Collapsible
    val automaticWeaponsCheckBox = VisCheckBoxCustom(curLocalization["AUTOMATIC_WEAPONS"], "AUTOMATIC_WEAPONS", nameInLocalization = "AUTOMATIC_WEAPONS")
    val targetSwapDelay = VisSliderCustom(curLocalization["AIM_TARGET_SWAP_DELAY"], "AIM_TARGET_SWAP_DELAY", 0F, 500F, 10F, true, width1 = 200F, width2 = 250F, nameInLocalization = "AIM_TARGET_SWAP_DELAY")

    //Override Weapon Checkbox & Selection Box
    private val categorySelection = VisTable()
    val categorySelectionBox = VisSelectBox<String>()
    val categorySelectLabel = VisLabelCustom(curLocalization["WEAPON_CATEGORY"], nameInLocalization = "WEAPON_CATEGORY")
    val weaponOverrideCheckBox = VisCheckBoxCustomWithoutVar(curLocalization["OVERRIDE_WEAPONS_CHECKBOX"], "OVERRIDE_WEAPONS_CHECKBOX")

    val enableAimOnShot = ATabVisCheckBox(curLocalization["AIM_ONLY_ON_SHOT"], "_AIM_ONLY_ON_SHOT", nameInLocalization = "AIM_ONLY_ON_SHOT")
    val enableFactorRecoil = ATabVisCheckBox(curLocalization["FACTOR_RECOIL"], "_FACTOR_RECOIL", nameInLocalization = "FACTOR_RECOIL")
    val enableFlatAim = ATabVisCheckBox(curLocalization["ENABLE_FLAT_AIM"], "_ENABLE_FLAT_AIM", nameInLocalization = "ENABLE_FLAT_AIM")
    val enablePathAim = ATabVisCheckBox(curLocalization["ENABLE_PATH_AIM"], "_ENABLE_PATH_AIM", nameInLocalization = "ENABLE_PATH_AIM")
    val enableScopedOnly = VisCheckBoxCustom(curLocalization["SNIPER_ENABLE_SCOPED_ONLY"], "SNIPER_ENABLE_SCOPED_ONLY", nameInLocalization = "SNIPER_ENABLE_SCOPED_ONLY")

    val aimBoneLabel = VisLabelCustom(curLocalization["AIM_BONE"], nameInLocalization = "AIM_BONE")
    val aimBoneBox = VisSelectBox<String>()

    val forceAimBoneLabel = VisLabelCustom(curLocalization["AIM_FORCE_BONE"], nameInLocalization = "AIM_FORCE_BONE")
    val forceAimBoneBox = VisSelectBox<String>()

    val aimFov = ATabVisSlider(curLocalization["AIM_FOV"], "_AIM_FOV", 1F, 180F, 1F, true, nameInLocalization = "AIM_FOV")
    val aimSpeed = ATabVisSlider(curLocalization["AIM_SPEED"], "_AIM_SPEED", 0F, 10F, 1F, true, nameInLocalization = "AIM_SPEED")
    val aimSmooth = ATabVisSlider(curLocalization["AIM_SMOOTHNESS"], "_AIM_SMOOTHNESS", 1F, 5F, .1F, false, nameInLocalization = "AIM_SMOOTHNESS")
    val aimAfterShots = ATabVisSlider(curLocalization["AIM_AFTER_SHOTS"], "_AIM_AFTER_SHOTS", 0F, 10F, 1F, true, nameInLocalization = "AIM_AFTER_SHOTS")

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBoxCustomWithoutVar(curLocalization["ENABLE_PERFECT_AIM"], "ENABLE_PERFECT_AIM")
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFov = ATabVisSlider(curLocalization["FOV"], "_PERFECT_AIM_FOV", 1F, 180F, 1F, true, nameInLocalization = "FOV")
    val perfectAimChance = ATabVisSlider(curLocalization["PERFECT_AIM_CHANCE"], "_PERFECT_AIM_CHANCE", 1F, 100F, 1F, true, nameInLocalization = "PERFECT_AIM_CHANCE")

    //Advanced Settings Collapsible
    val advancedSettingsCheckBox = VisCheckBoxCustomWithoutVar(curLocalization["ADVANCED_SETTINGS"], "ADVANCED_SETTINGS")
    private val advancedSettingsTable = VisTable()
    val advancedSettingsCollapsible = CollapsibleWidget(advancedSettingsTable)
    val randomizeX = ATabVisSlider(curLocalization["RANDOM_X_VARIATION"], "_RANDOM_X_VARIATION", 0F, 50F, 1F, true, nameInLocalization = "RANDOM_X_VARIATION")
    val randomizeY = ATabVisSlider(curLocalization["RANDOM_Y_VARIATION"], "_RANDOM_Y_VARIATION", 0F, 50F, 1F, true, nameInLocalization = "RANDOM_Y_VARIATION")
    val randomizeDZ = ATabVisSlider(curLocalization["VARIATION_DEADZONE"], "_VARIATION_DEADZONE", 0F, 100F, 5F, true, nameInLocalization = "VARIATION_DEADZONE")
    val advancedRcsX = ATabVisSlider(curLocalization["AIM_RCS_X"], "_AIM_RCS_X", 0.05F, 1F, 0.05F, false, nameInLocalization = "AIM_RCS_X")
    val advancedRcsY = ATabVisSlider(curLocalization["AIM_RCS_Y"], "_AIM_RCS_Y", 0.05F, 1F, 0.05F, false, nameInLocalization = "AIM_RCS_Y")
    val advancedRcsVariation = ATabVisSlider(curLocalization["AIM_RCS_VARIATION"], "_AIM_RCS_VARIATION", 0F, 1F, 0.05F, false, nameInLocalization = "AIM_RCS_VARIATION")
    val advancedSpeedDivisor = ATabVisSlider(curLocalization["AIM_SPEED_DIVISOR"], "_AIM_SPEED_DIVISOR", 1F, 10F, 1F, true, nameInLocalization = "AIM_SPEED_DIVISOR")
    var map = aimingMap()

    fun updateMap () {
        map = aimingMap()
    }

    init {
        //Fov Type
        val fovType = VisTable()
        fovTypeBox.setItems(curLocalization["STATIC"], curLocalization["DISTANCE"])
        fovTypeBox.selected = curLocalization[curSettings["FOV_TYPE"].replace("\"", "")]

        fovTypeBox.changed { _, _ ->
            curSettings["FOV_TYPE"] = map[fovTypeBox.selected]
            updateDisableEsp()
            true
        }
        fovType.add(fovTypeLabel).width(200F)
        fovType.add(fovTypeBox)

        //Create Override Weapon Check Box & Collapsible
        weaponOverrideCheckBox.isChecked = curSettings["ENABLE_OVERRIDE"].strToBool()
        overridenWeapons.weaponOverride = weaponOverrideCheckBox.isChecked

        weaponOverrideCheckBox.changed { _, _ ->
            overridenWeapons.weaponOverride = weaponOverrideCheckBox.isChecked
            curSettings["ENABLE_OVERRIDE"] = weaponOverrideCheckBox.isChecked.toString()

            val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
            overridenWeapons.enableOverride = curWep.tOverride

            uiUpdate()
            true
        }

        //Create Category Selector Box
        categorySelectionBox.setItems(curLocalization["PISTOL"], curLocalization["RIFLE"], curLocalization["SMG"], curLocalization["SNIPER"], curLocalization["SHOTGUN"])
        categorySelectionBox.selected = curLocalization[curSettings["DEFAULT_CATEGORY_SELECTED"]]
        categorySelected = map[categorySelectionBox.selected]
        categorySelection.add(categorySelectLabel).padRight(200F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox)

        categorySelectionBox.changed { _, _ ->
            categorySelected = map[categorySelectionBox.selected]
            aimTab.tTrig.categorySelectionBox.selected = categorySelected

            if (categorySelected == "SNIPER") {
                enableScopedOnly.color = Color(255F, 255F, 255F, 1F)
                enableScopedOnly.isDisabled = false
            } else {
                enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
                enableScopedOnly.isDisabled = true
            }

            if (categorySelected == "RIFLE" || categorySelected == "SMG") {
                aimAfterShots.disable(false, Color(255F, 255F, 255F, 1F))
            } else {
                aimAfterShots.disable(true, Color(255F, 255F, 255F, 0F))
            }

            uiUpdate()
            true
        }

        //Disable on start, default is pistol
        aimAfterShots.disable(true, Color(255F, 255F, 255F, 0F))

        //Create Scoped Only Toggle
        enableScopedOnly.isChecked = curSettings["SNIPER_ENABLE_SCOPED_ONLY"].strToBool()
        enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
        enableScopedOnly.isDisabled = true

        //Create Aim Bone Selector Box
        val aimBone = VisTable()
        aimBoneBox.setItems(curLocalization["HEAD"], curLocalization["NECK"], curLocalization["CHEST"], curLocalization["STOMACH"], curLocalization["NEAREST"], curLocalization["RANDOM"])
        aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"].toInt()) {
            HEAD_BONE -> curLocalization["HEAD"]
            NECK_BONE -> curLocalization["NECK"]
            CHEST_BONE -> curLocalization["CHEST"]
            STOMACH_BONE -> curLocalization["STOMACH"]
            NEAREST_BONE -> curLocalization["NEAREST"]
            else -> curLocalization["RANDOM"]
        }
        aimBone.add(aimBoneLabel).width(200F)
        aimBone.add(aimBoneBox)

        aimBoneBox.changed { _, _ ->
            val setBone = curSettings[map[aimBoneBox.selected] + "_BONE"].toInt()
            curSettings[categorySelected + "_AIM_BONE"] = setBone.toString()
            true
        }

        //Create Force Aim Bone Selector Box
        val forceAimBone = VisTable()
        forceAimBoneBox.setItems(curLocalization["HEAD"], curLocalization["NECK"], curLocalization["CHEST"], curLocalization["STOMACH"], curLocalization["NEAREST"], curLocalization["RANDOM"])
        forceAimBoneBox.selected = when (curSettings[categorySelected + "_AIM_FORCE_BONE"].toInt()) {
            HEAD_BONE -> curLocalization["HEAD"]
            NECK_BONE -> curLocalization["NECK"]
            CHEST_BONE -> curLocalization["CHEST"]
            STOMACH_BONE -> curLocalization["STOMACH"]
            NEAREST_BONE -> curLocalization["NEAREST"]
            else -> curLocalization["RANDOM"]
        }
        forceAimBone.add(forceAimBoneLabel).width(200F)
        forceAimBone.add(forceAimBoneBox)

        forceAimBoneBox.changed { _, _ ->
            val setBone = curSettings[map[forceAimBoneBox.selected] + "_BONE"].toInt()
            curSettings[categorySelected + "_AIM_FORCE_BONE"] = setBone.toString()
            true
        }

        //Create Perfect Aim Collapsible Check Box
        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimCheckBox.changed { _, _ ->
            curSettings[categorySelected + "_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
            perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
        }

        perfectAimCollapsible.setCollapsed(!curSettings[categorySelected + "_PERFECT_AIM"].strToBool(), true)
        perfectAimTable.add(perfectAimFov).left().row()
        perfectAimTable.add(perfectAimChance).left().row()

        //End Perfect Aim Collapsible Check Box

        //Create Advanced Aim Settings Collapsible
        advancedSettingsCheckBox.isChecked = curSettings[categorySelected + "_ADVANCED_SETTINGS"].strToBool()
        advancedSettingsCheckBox.changed { _, _ ->
            curSettings[categorySelected + "_ADVANCED_SETTINGS"] = advancedSettingsCheckBox.isChecked.boolToStr()
            advancedSettingsCollapsible.setCollapsed(!advancedSettingsCollapsible.isCollapsed, true)
        }

        advancedSettingsCollapsible.setCollapsed(!curSettings[categorySelected + "_ADVANCED_SETTINGS"].strToBool(), true)

        advancedSettingsTable.add(randomizeX).left().row()
        advancedSettingsTable.add(randomizeY).left().row()
        advancedSettingsTable.add(randomizeDZ).left().row()
        advancedSettingsTable.add(advancedRcsX).left().row()
        advancedSettingsTable.add(advancedRcsY).left().row()
        advancedSettingsTable.add(advancedRcsVariation).left().row()
        advancedSettingsTable.add(advancedSpeedDivisor).left().row()
        //End

        //Default menu size is 500
        //Texts are 200
        //Sliders are 250
        //Leaves 25 for left and right side to center
        padLeft(25F)
        padRight(25F)

        //Add all items to label for tabbed pane content

        add(enableAim).left().row()

        add(activateFromFireKey).left().row()
        add(teammatesAreEnemies).left().row()
        add(holdAim).left().row()
        add(targetSwapDelay).left().row()
        add(fovType).left().row()

        //addSeparator()

        addSeparator()

        add(forceAimBoneKey).left().row()
        add(forceAimKey).left().row()
        add(forceAimAlways).left().row()
        add(forceAimThroughWalls).left().row()

        addSeparator()

        add(automaticWeaponsCheckBox).left().row()

        addSeparator()

        add(weaponOverrideCheckBox).left().row()
        add(categorySelection).left().row()
        add(enableFactorRecoil).left().row()
        add(enableAimOnShot).left().row()
        add(enableFlatAim).left().row()
        add(enablePathAim).left().row()
        add(enableScopedOnly).left().row() //SNIPER selection only
        add(aimBone).left().row()
        add(forceAimBone).left().row()
        add(aimSpeed).left().row()
        add(aimFov).left().row()
        add(aimSmooth).left().row()
        add(aimAfterShots).left().row() //RIFLE & SMG selection only
        add(perfectAimCheckBox).left().row()
        add(perfectAimCollapsible).left().row()
        add(advancedSettingsCheckBox).left().row()
        add(advancedSettingsCollapsible).left().row()

        addSeparator()
    }
}