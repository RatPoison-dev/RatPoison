package rat.poison.ui.uiHelpers.tables

import com.badlogic.gdx.graphics.Color
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import rat.poison.*
import rat.poison.game.CSGO
import rat.poison.settings.CHEST_BONE
import rat.poison.settings.HEAD_BONE
import rat.poison.settings.NECK_BONE
import rat.poison.settings.STOMACH_BONE
import rat.poison.ui.aimTab
import rat.poison.ui.changed
import rat.poison.ui.overridenWeapons
import rat.poison.ui.tabs.categorySelected
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiHelpers.aimTab.ATabVisCheckBox
import rat.poison.ui.uiHelpers.aimTab.ATabVisSlider
import rat.poison.ui.uiUpdate

class AimTable: VisTable(true) {
    //Init labels/sliders/boxes that show values here
    val enableAim = VisCheckBoxCustom("Enable Aim", "ENABLE_AIM")
    val activateFromFireKey = VisCheckBoxCustom("Activate From Fire Key", "ACTIVATE_FROM_AIM_KEY")
    val teammatesAreEnemies = VisCheckBoxCustom("Teammates Are Enemies", "TEAMMATES_ARE_ENEMIES")

    val forceAimKey = VisInputFieldCustom("Force Aim Key", "FORCE_AIM_KEY")
    val forceAimAlways = VisCheckBoxCustom("Force Aim Always", "FORCE_AIM_ALWAYS")

    //Automatic Weapons Collapsible
    val automaticWeaponsCheckBox = VisCheckBoxCustom("Automatic Weapons", "AUTOMATIC_WEAPONS")
    val automaticWeaponsInput = VisInputFieldCustom("MS Delay", "AUTO_WEP_DELAY", false)

    val targetSwapDelay = VisSliderCustom("Target Swap Delay", "AIM_TARGET_SWAP_DELAY", 0F, 500F, 10F, true, width1 = 200F, width2 = 250F)

    //Override Weapon Checkbox & Selection Box
    private val categorySelection = VisTable()
    val categorySelectionBox = VisSelectBox<String>()
    val categorySelectLabel = VisLabel("Weapon Category: ")
    val weaponOverrideCheckBox = VisCheckBox("Override Weapons")

    val enableFactorRecoil = ATabVisCheckBox("Factor Recoil", "_FACTOR_RECOIL")
    val enableFlatAim = ATabVisCheckBox("Flat Aim", "_ENABLE_FLAT_AIM")
    val enablePathAim = ATabVisCheckBox("Path Aim", "_ENABLE_PATH_AIM")
    val enableScopedOnly = VisCheckBoxCustom("Scoped Only", "SNIPER_ENABLE_SCOPED_ONLY")

    val aimBoneLabel = VisLabel("Bone: ")
    val aimBoneBox = VisSelectBox<String>()

    val aimFov = ATabVisSlider("Aim FOV", "_AIM_FOV", 1F, 180F, 1F, true)
    val aimSpeed = ATabVisSlider("Aim Speed", "_AIM_SPEED", 0F, 5F, 1F, true)
    val aimSmooth = ATabVisSlider("Smoothness", "_AIM_SMOOTHNESS", 1F, 5F, .1F, false)
    val aimStrict = ATabVisSlider("Strictness", "_AIM_STRICTNESS", 1F, 5F, .1F, false)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim")
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFov = ATabVisSlider("FOV", "_PERFECT_AIM_FOV", 1F, 45F, 1F, true)
    val perfectAimChance = ATabVisSlider("Chance", "_PERFECT_AIM_CHANCE", 1F, 100F, 1F, true)

    init {
        if (curSettings["WARNING"].strToBool()) {
            val dialog = Dialogs.showOKDialog(App.menuStage, "Warning", "Current Version: 1.5.2" +
                    "\n\nIf you have any problems submit an issue on Github" +
                    "\nGitHub: https://github.com/TheFuckingRat/RatPoison" +
                    "\n\nMany names of settings have changed, you should reconfig from default")
            dialog.setPosition(CSGO.gameWidth / 4F - dialog.width / 2F, CSGO.gameHeight.toFloat() / 2F)
            App.menuStage.addActor(dialog)
        }

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

            uiUpdate()
            true
        }

        //Create Scoped Only Toggle
        enableScopedOnly.isChecked = curSettings["SNIPER_ENABLE_SCOPED_ONLY"].strToBool()
        enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
        enableScopedOnly.isDisabled = true

        //Create Aim Bone Selector Box
        val aimBone = VisTable()
        aimBoneBox.setItems("HEAD", "NECK", "CHEST", "STOMACH", "NEAREST")
        aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"].toInt()) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            else -> "NEAREST"
        }
        aimBone.add(aimBoneLabel).width(200F)
        aimBone.add(aimBoneBox)

        aimBoneBox.changed { _, _ ->
            val setBone = curSettings[aimBoneBox.selected + "_BONE"].toInt()
            curSettings[categorySelected + "_AIM_BONE"] = setBone.toString()
            true
        }

        //Create Perfect Aim Collapsible Check Box
        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimCollapsible.setCollapsed(!curSettings[categorySelected + "_PERFECT_AIM"].strToBool(), true)

        perfectAimTable.add(perfectAimFov).left().row()
        perfectAimTable.add(perfectAimChance).left().row()

        perfectAimCheckBox.changed { _, _ ->
            curSettings[categorySelected + "_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
            perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
        }
        //End Perfect Aim Collapsible Check Box

        //Default menu size is 500
        //Texts are 200
        //Sliders are 250
        //Leaves 25 for left and right side to center
        apply {
            padLeft(25F)
            padRight(25F)

            //Add all items to label for tabbed pane content

            add(enableAim).left().row()

            add(activateFromFireKey).left().row()
            add(teammatesAreEnemies).left().row()

            add(forceAimKey).left().row()
            add(forceAimAlways).left().row()

            add(automaticWeaponsCheckBox).left().row()
            add(automaticWeaponsInput).left().row()

            add(targetSwapDelay).left().row()

            addSeparator()

            add(weaponOverrideCheckBox).left().row()
            add(categorySelection).left().row()
            add(enableFactorRecoil).left().row()
            add(enableFlatAim).left().row()
            add(enablePathAim).left().row()
            add(enableScopedOnly).left().row() //SNIPER selection only
            add(aimBone).left().row()
            add(aimSpeed).left().row()
            add(aimFov).left().row()
            add(aimSmooth).left().row()
            add(aimStrict).left().row()
            add(perfectAimCheckBox).left().row()
            add(perfectAimCollapsible).left().row()

            addSeparator()
        }
    }
}