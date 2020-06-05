package rat.poison.ui.uiHelpers.tables

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.sun.jna.platform.win32.OaIdl
import rat.poison.*
import rat.poison.game.CSGO
import rat.poison.settings.*
import rat.poison.ui.uiPanels.aimTab
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.overridenWeapons
import rat.poison.ui.tabs.categorySelected
import rat.poison.ui.tabs.updateDisableEsp
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.poison.ui.uiHelpers.aimTab.ATabVisSlider
import rat.poison.ui.uiUpdate

class AimTable: VisTable(false) {
    //Init labels/sliders/boxes that show values here
    val enableAim = VisCheckBoxCustom(curLocalization["ENABLE_AIM"], "ENABLE_AIM")
    val aimToggleKey = VisInputFieldCustom(curLocalization["AIM_TOGGLE_KEY"], "AIM_TOGGLE_KEY")
    val activateFromFireKey = VisCheckBoxCustom(curLocalization["ACTIVATE_FROM_AIM_KEY"], "ACTIVATE_FROM_AIM_KEY")
    val holdAim = VisCheckBoxCustom(curLocalization["HOLD_AIM"], "HOLD_AIM")
    val teammatesAreEnemies = VisCheckBoxCustom(curLocalization["TEAMMATES_ARE_ENEMIES"], "TEAMMATES_ARE_ENEMIES")

    val fovTypeLabel = VisLabel(curLocalization["FOV_TYPE"])
    val fovTypeBox = VisSelectBox<String>()

    val forceAimBoneKey = VisInputFieldCustom(curLocalization["FORCE_AIM_BONE_KEY"], "FORCE_AIM_BONE_KEY")
    val forceAimKey = VisInputFieldCustom(curLocalization["FORCE_AIM_KEY"], "FORCE_AIM_KEY")
    val forceAimAlways = VisCheckBoxCustom(curLocalization["FORCE_AIM_ALWAYS"], "FORCE_AIM_ALWAYS")
    val forceAimThroughWalls = VisCheckBoxCustom(curLocalization["FORCE_AIM_THROUGH_WALLS"], "FORCE_AIM_THROUGH_WALLS")

    //Automatic Weapons Collapsible
    val automaticWeaponsCheckBox = VisCheckBoxCustom(curLocalization["AUTOMATIC_WEAPONS"], "AUTOMATIC_WEAPONS")
    val automaticWeaponsInput = VisInputFieldCustom(curLocalization["AUTO_WEP_DELAY"], "AUTO_WEP_DELAY", false)

    val targetSwapDelay = VisSliderCustom(curLocalization["AIM_TARGET_SWAP_DELAY"], "AIM_TARGET_SWAP_DELAY", 0F, 500F, 10F, true, width1 = 200F, width2 = 250F)

    //Override Weapon Checkbox & Selection Box
    private val categorySelection = VisTable()
    val categorySelectionBox = VisSelectBox<String>()
    val categorySelectLabel = VisLabel(curLocalization["WEAPON_CATEGORY"])
    val weaponOverrideCheckBox = VisCheckBox(curLocalization["OVERRIDE_WEAPONS_CHECKBOX"])

    val enableAimOnShot = ATabVisCheckBox(curLocalization["AIM_ONLY_ON_SHOT"], "_AIM_ONLY_ON_SHOT")
    val enableFactorRecoil = ATabVisCheckBox(curLocalization["FACTOR_RECOIL"], "_FACTOR_RECOIL")
    val enableFlatAim = ATabVisCheckBox(curLocalization["ENABLE_FLAT_AIM"], "_ENABLE_FLAT_AIM")
    val enablePathAim = ATabVisCheckBox(curLocalization["ENABLE_PATH_AIM"], "_ENABLE_PATH_AIM")
    val enableScopedOnly = VisCheckBoxCustom(curLocalization["SNIPER_ENABLE_SCOPED_ONLY"], "SNIPER_ENABLE_SCOPED_ONLY")

    val aimBoneLabel = VisLabel(curLocalization["AIM_BONE"])
    val aimBoneBox = VisSelectBox<String>()

    val forceAimBoneLabel = VisLabel(curLocalization["AIM_FORCE_BONE"])
    val forceAimBoneBox = VisSelectBox<String>()

    val aimFov = ATabVisSlider(curLocalization["AIM_FOV"], "_AIM_FOV", 1F, 180F, 1F, true)
    val aimSpeed = ATabVisSlider(curLocalization["AIM_SPEED"], "_AIM_SPEED", 0F, 10F, 1F, true)
    val aimSmooth = ATabVisSlider(curLocalization["AIM_SMOOTHNESS"], "_AIM_SMOOTHNESS", 1F, 5F, .1F, false)
    val aimAfterShots = ATabVisSlider(curLocalization["AIM_AFTER_SHOTS"], "_AIM_AFTER_SHOTS", 0F, 10F, 1F, true)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox(curLocalization["ENABLE_PERFECT_AIM"])
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFov = ATabVisSlider(curLocalization["FOV"], "_PERFECT_AIM_FOV", 1F, 180F, 1F, true)
    val perfectAimChance = ATabVisSlider(curLocalization["PERFECT_AIM_CHANCE"], "_PERFECT_AIM_CHANCE", 1F, 100F, 1F, true)

    //Advanced Settings Collapsible
    val advancedSettingsCheckBox = VisCheckBox(curLocalization["ADVANCED_SETTINGS"])
    private val advancedSettingsTable = VisTable()
    val advancedSettingsCollapsible = CollapsibleWidget(advancedSettingsTable)
    val randomizeX = ATabVisSlider(curLocalization["RANDOM_X_VARIATION"], "_RANDOM_X_VARIATION", 0F, 50F, 1F, true)
    val randomizeY = ATabVisSlider(curLocalization["RANDOM_Y_VARIATION"], "_RANDOM_Y_VARIATION", 0F, 50F, 1F, true)
    val randomizeDZ = ATabVisSlider(curLocalization["VARIATION_DEADZONE"], "_VARIATION_DEADZONE", 0F, 100F, 5F, true)
    val advancedRcsX = ATabVisSlider(curLocalization["AIM_RCS_X"], "_AIM_RCS_X", 0.05F, 1F, 0.05F, false)
    val advancedRcsY = ATabVisSlider(curLocalization["AIM_RCS_Y"], "_AIM_RCS_Y", 0.05F, 1F, 0.05F, false)
    val advancedRcsVariation = ATabVisSlider(curLocalization["AIM_RCS_VARIATION"], "_AIM_RCS_VARIATION", 0F, 1F, 0.05F, false)
    val advancedSpeedDivisor = ATabVisSlider(curLocalization["AIM_SPEED_DIVISOR"], "_AIM_SPEED_DIVISOR", 1F, 10F, 1F, true)

    init {
        val map = aimingMap()
        if (curSettings["WARNING"].strToBool()) {
            val dialog = Dialogs.showOKDialog(App.menuStage, "Warning", "Current Version: 1.7" +
                    "\n\nIf you have any problems submit an issue on Github" +
                    "\nGitHub: https://github.com/TheFuckingRat/RatPoison" +
                    "\n\nUpdate 1.6 removes aim strictness from aim settings" +
                    "\nOlder configs shouldn't break, but your aim settings" +
                    "\nmight need to be updated." +
                    "\n\n Official discord server: https://discord.gg/J2uHTJ2")
            dialog.setPosition(CSGO.gameWidth / 4F - dialog.width / 2F, CSGO.gameHeight.toFloat() / 2F)
            App.menuStage.addActor(dialog)
        }

        //Fov Type
        val fovType = VisTable()
        fovTypeBox.setItems(curLocalization["STATIC"], curLocalization["DISTANCE"])
        fovTypeBox.selected = curLocalization[(curSettings["FOV_TYPE"].replace("\"", ""))]

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

            val curWep = curSettings[overridenWeapons.weaponOverrideSelected].toWeaponClass()
            overridenWeapons.enableOverride = curWep.tOverride

            uiUpdate()
            true
        }

        //Create Category Selector Box
        categorySelectionBox.setItems("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
        categorySelectionBox.selected = "PISTOL"
        categorySelected = categorySelectionBox.selected
        categorySelection.add(categorySelectLabel).padRight(200F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox)

        categorySelectionBox.changed { _, _ ->
            categorySelected = categorySelectionBox.selected
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
        aimBoneBox.setItems("HEAD", "NECK", "CHEST", "STOMACH", "NEAREST", "RANDOM")
        aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"].toInt()) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            NEAREST_BONE -> "NEAREST"
            else -> "RANDOM"
        }
        aimBone.add(aimBoneLabel).width(200F)
        aimBone.add(aimBoneBox)

        aimBoneBox.changed { _, _ ->
            val setBone = curSettings[aimBoneBox.selected + "_BONE"].toInt()
            curSettings[categorySelected + "_AIM_BONE"] = setBone.toString()
            true
        }

        //Create Force Aim Bone Selector Box
        val forceAimBone = VisTable()
        forceAimBoneBox.setItems("HEAD", "NECK", "CHEST", "STOMACH", "NEAREST", "RANDOM")
        forceAimBoneBox.selected = when (curSettings[categorySelected + "_AIM_FORCE_BONE"].toInt()) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            NEAREST_BONE -> "NEAREST"
            else -> "RANDOM"
        }
        forceAimBone.add(forceAimBoneLabel).width(200F)
        forceAimBone.add(forceAimBoneBox)

        forceAimBoneBox.changed { _, _ ->
            val setBone = curSettings[forceAimBoneBox.selected + "_BONE"].toInt()
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