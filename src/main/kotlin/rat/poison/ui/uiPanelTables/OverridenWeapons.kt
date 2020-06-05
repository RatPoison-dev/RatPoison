package rat.poison.ui.uiPanelTables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.*
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.toWeaponClass
import rat.poison.ui.changed
import rat.poison.ui.uiPanels.overridenWeapons
import rat.poison.ui.uiUpdate
import kotlin.math.round

class OverridenWeapons : VisTable(true) {
    //private val table = VisTable(true)

    var categorySelected = "PISTOL"
    var weaponOverride = false
    var enableOverride = false
    var weaponOverrideSelected = ""

    private val categorySelectionBox = VisSelectBox<String>()

    //Override Weapon Checkbox & Selection Box
    private val categorySelectLabel = VisLabel(curLocalization["CATEGORY"])

    private val weaponOverrideSelectionBox = VisSelectBox<String>()
    val weaponOverrideEnableCheckBox = VisCheckBox(curLocalization["ENABLE_OVERRIDE"])

    val enableFactorRecoil = VisCheckBox(curLocalization["FACTOR_RECOIL"])
    val enableFlatAim = VisCheckBox(curLocalization["ENABLE_FLAT_AIM"])
    val enablePathAim = VisCheckBox(curLocalization["ENABLE_PATH_AIM"])
    val enableScopedOnly = VisCheckBox(curLocalization["SNIPER_ENABLE_SCOPED_ONLY"])

    private val aimBoneLabel = VisLabel(curLocalization["AIM_BONE"])
    val aimBoneBox = VisSelectBox<String>()

    val aimFovLabel = VisLabel("${curLocalization["FOV"]}: "  + curSettings[categorySelected + "_AIM_FOV"].toInt().toString())
    val aimFovSlider = VisSlider(1F, 180F, 1F, false)

    val aimSpeedLabel = VisLabel("${curLocalization["OVERRIDE_AIM_SPEED"]} " + curSettings[categorySelected + "_AIM_SPEED"].toInt().toString())
    val aimSpeedSlider = VisSlider(0F, 5F, 1F, false)

    val aimSmoothnessLabel = VisLabel("${curLocalization["OVERRIDE_SMOOTH"]} " + curSettings[categorySelected + "_AIM_SMOOTHNESS"].toFloat())
    val aimSmoothnessSlider = VisSlider(1F, 5F, 0.1F, false)

    val aimAfterShotsLabel = VisLabel("${curLocalization["AIM_AFTER_SHOTS"]} :" + curSettings[categorySelected + "AIM_AFTER_SHOTS"])
    val aimAfterShotsSlider = VisSlider(1F, 10F, 1F, false)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox(curLocalization["ENABLE_PERFECT_AIM"])
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFovLabel = VisLabel("${curLocalization["FOV"]}: " + curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt().toString())
    val perfectAimFovSlider = VisSlider(1F, 180F, 1F, false)
    val perfectAimChanceLabel = VisLabel("${curLocalization["PERFECT_AIM_CHANCE"]}: " + curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toInt().toString())
    val perfectAimChanceSlider = VisSlider(1F, 100F, 1F, false)

    init {
        align(Align.left)

        //Create Category Selector Box
        val categorySelection = VisTable()
        categorySelectionBox.setItems("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
        categorySelectionBox.selected = "PISTOL"
        categorySelected = categorySelectionBox.selected
        categorySelection.add(categorySelectLabel).padRight(125F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox).width(125F)

        categorySelectionBox.changed { _, _ ->
            categorySelected = categorySelectionBox.selected
            when (categorySelected)
            {
                "PISTOL" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("DESERT_EAGLE", "DUAL_BERRETA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250") }
                "SMG" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("MAC10", "P90", "MP5", "UMP45", "MP7", "MP9", "PP_BIZON") }
                "RIFLE" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("AK47", "AUG", "FAMAS", "SG553", "GALIL", "M4A4", "M4A1_SILENCER", "NEGEV", "M249") }
                "SNIPER" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("AWP", "G3SG1", "SCAR20", "SSG08") }
                "SHOTGUN" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("XM1014", "MAG7", "SAWED_OFF", "NOVA") }
            }

            if (categorySelected == "SNIPER") {
                enableScopedOnly.color = Color(255F, 255F, 255F, 1F)
                enableScopedOnly.isDisabled = false
            } else {
                enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
                enableScopedOnly.isDisabled = true
            }

            if (categorySelected == "RIFLE" || categorySelected == "SMG") {
                aimAfterShotsLabel.color = Color(255F, 255F, 255F, 1F)
                aimAfterShotsSlider.color = Color(255F, 255F, 255F, 1F)
                aimAfterShotsSlider.isDisabled = false
            } else {
                aimAfterShotsLabel.color = Color(255F, 255F, 255F, 0F)
                aimAfterShotsSlider.color = Color(255F, 255F, 255F, 0F)
                aimAfterShotsSlider.isDisabled = true
            }

            weaponOverrideSelectionBox.selected = weaponOverrideSelectionBox.items[0]
            uiUpdate()
            true
        }

        //Create Override Weapon Selector
        val weaponOverrideSelection = VisTable()
        weaponOverrideSelectionBox.setItems("DESERT_EAGLE", "DUAL_BERRETA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250")
        weaponOverrideSelectionBox.selected = "DESERT_EAGLE"
        weaponOverrideSelected = weaponOverrideSelectionBox.selected
        weaponOverrideSelection.add(weaponOverrideSelectionBox).width(125F).padLeft(125F)

        weaponOverrideSelectionBox.changed { _, _ ->
            if (!weaponOverrideSelectionBox.selected.isNullOrEmpty()) {
                weaponOverrideSelected = weaponOverrideSelectionBox.selected
            }
            uiUpdate()
            true
        }
        //End Override Weapon Selection Box

        //Create Enable Override Toggle
        weaponOverrideEnableCheckBox.isChecked = curSettings[weaponOverrideSelected].toWeaponClass().tOverride
        weaponOverrideEnableCheckBox.changed { _, _ ->
            val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
            curWep.tOverride = weaponOverrideEnableCheckBox.isChecked
            curSettings[weaponOverrideSelected] = curWep.toString()
            enableOverride = weaponOverrideEnableCheckBox.isChecked
            uiUpdate()
            true
        }

        //Create Flat Aim Toggle
        enableFlatAim.isChecked = curSettings[categorySelected + "_ENABLE_FLAT_AIM"].strToBool()
        enableFlatAim.changed { _, _ ->
            if (weaponOverride)
            {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tFlatAim = enableFlatAim.isChecked
                if (enableFlatAim.isChecked)
                {
                    curWep.tPathAim = false
                }
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            uiUpdate()
            true
        }

        //Create Path Aim Toggle
        enablePathAim.isChecked = curSettings[categorySelected + "_ENABLE_PATH_AIM"].strToBool()
        enablePathAim.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tPathAim = enablePathAim.isChecked
                if (enablePathAim.isChecked)
                {
                    curWep.tFlatAim = false
                }
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            uiUpdate()
            true
        }

        //Create Factor Recoil Toggle
        enableFactorRecoil.isChecked = curSettings[categorySelected + "_ENABLE_PATH_AIM"].strToBool()
        enableFactorRecoil.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tFRecoil = enableFactorRecoil.isChecked
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            //uiUpdate()
            true
        }

        //Create Scoped Only Toggle
        enableScopedOnly.isChecked = curSettings["SNIPER_ENABLE_SCOPED_ONLY"].strToBool()
        enableScopedOnly.isDisabled = true
        enableScopedOnly.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tScopedOnly = enableScopedOnly.isChecked
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            //uiUpdate()
            true
        }

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
        aimBone.add(aimBoneLabel).width(125F)
        aimBone.add(aimBoneBox).width(125F)

        aimBoneBox.changed { _, _ ->
            val setBone = curSettings[aimBoneBox.selected + "_BONE"].toInt()

            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tAimBone = setBone
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
        }

        //Create Aim FOV Slider
        val aimFov = VisTable()
        aimFovSlider.value = curSettings[categorySelected + "_AIM_FOV"].toInt().toFloat()
        aimFovSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tAimFov = aimFovSlider.value.toInt()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }

            aimFovLabel.setText("${curLocalization["FOV"]}: " + aimFovSlider.value.toInt())
        }
        aimFov.add(aimFovLabel).width(125F)
        aimFov.add(aimFovSlider).width(125F)

        //Create Aim Speed Slider
        val aimSpeed = VisTable()
        aimSpeedSlider.value = curSettings[categorySelected + "_AIM_SPEED"].toInt().toFloat()
        aimSpeedSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tAimSpeed = aimSpeedSlider.value.toInt()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            aimSpeedLabel.setText("${curLocalization["OVERRIDE_AIM_SPEED"]} " + aimSpeedSlider.value.toInt())
        }
        aimSpeed.add(aimSpeedLabel).width(125F)
        aimSpeed.add(aimSpeedSlider).width(125F)

        //Create Aim Smoothness Slider
        val aimSmoothness = VisTable()
        aimSmoothnessSlider.value = curSettings[categorySelected + "_AIM_SMOOTHNESS"].toFloat()
        aimSmoothnessSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tAimSmooth = aimSmoothnessSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            aimSmoothnessLabel.setText("${curLocalization["OVERRIDE_SMOOTH"]} " + (round(aimSmoothnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimSmoothness.add(aimSmoothnessLabel).width(125F)
        aimSmoothness.add(aimSmoothnessSlider).width(125F)

        //Create Perfect Aim Collapsible Check Box
        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimCollapsible.setCollapsed(!curSettings[categorySelected + "_PERFECT_AIM"].strToBool(), true)

        //Create Aim After Shot Slider
        val aimAfterShots = VisTable()
        aimAfterShotsSlider.value = curSettings[categorySelected + "_AIM_FOV"].toInt().toFloat()
        aimAfterShotsSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tAimAfterShots = aimAfterShotsSlider.value.toInt()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }

            aimAfterShotsLabel.setText("${curLocalization["AIM_AFTER_SHOTS"]} :" + aimAfterShotsSlider.value.toInt())
        }
        aimAfterShots.add(aimAfterShotsLabel).width(125F)
        aimAfterShots.add(aimAfterShotsSlider).width(125F)

        //Create Perfect Aim Fov Slider
        val perfectAimFov = VisTable()
        perfectAimFovSlider.value = curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt().toFloat()
        perfectAimFovSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tPAimFov = perfectAimFovSlider.value.toInt()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            perfectAimFovLabel.setText("${curLocalization["FOV"]}: " + perfectAimFovSlider.value.toInt())
        }
        perfectAimFov.add(perfectAimFovLabel).width(105F)
        perfectAimFov.add(perfectAimFovSlider).width(125F)
        //End Perfect Aim Fov Slider

        //Create Perfect Aim Chance Slider
        val perfectAimChance = VisTable()
        perfectAimChanceSlider.value = curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toInt().toFloat()
        perfectAimChanceSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tPAimChance = perfectAimChanceSlider.value.toInt()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            perfectAimChanceLabel.setText("${curLocalization["PERFECT_AIM_CHANCE"]}: " + perfectAimChanceSlider.value.toInt())
        }

        perfectAimChance.add(perfectAimChanceLabel).width(105F)
        perfectAimChance.add(perfectAimChanceSlider).width(125F)
        //End Perfect Aim Chance Slider

        perfectAimTable.add(perfectAimFov).padLeft(20F).left().row()
        perfectAimTable.add(perfectAimChance).padLeft(20F).left().row()

        perfectAimCheckBox.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tPerfectAim = perfectAimCheckBox.isChecked
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
        }
        //End Perfect Aim Collapsible Check Box


        //Add all items to label for tabbed pane content
        add(categorySelection).left().row()
        add(weaponOverrideSelection).left().row()
        add(weaponOverrideEnableCheckBox).left().row()
        add(enableFactorRecoil).left().row()
        add(enableFlatAim).left().row()
        add(enablePathAim).left().row()
        add(enableScopedOnly).left().row()
        add(aimBone).left().row()
        add(aimSpeed).left().row()
        add(aimFov).left().row()
        add(aimSmoothness).left().row()
        add(aimAfterShots).left().row()
        add(perfectAimCheckBox).left().row()
        add(perfectAimCollapsible).left().row()
    }
}

fun overridenWeaponsUpdate() { //This isn't needed... because the Override Weapons hides it
    overridenWeapons.apply {
        val curWep = curSettings[overridenWeapons.weaponOverrideSelected].toWeaponClass()

        overridenWeapons.weaponOverrideEnableCheckBox.isChecked = curWep.tOverride
        enableFactorRecoil.isChecked = curWep.tFRecoil
        enableFlatAim.isChecked = curWep.tFlatAim
        enablePathAim.isChecked = curWep.tPathAim
        enableScopedOnly.isChecked = curWep.tScopedOnly
        if (categorySelected == "SNIPER") {
            enableScopedOnly.color = Color(255F, 255F, 255F, 1F)
            enableScopedOnly.isDisabled = false
        } else {
            enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
            enableScopedOnly.isDisabled = true
        }

        aimAfterShotsSlider.value = curWep.tAimAfterShots.toFloat()
        if (categorySelected == "RIFLE" || categorySelected == "SMG") {
            aimAfterShotsLabel.color = Color(255F, 255F, 255F, 1F)
            aimAfterShotsSlider.color = Color(255F, 255F, 255F, 1F)
            aimAfterShotsSlider.isDisabled = false
        } else {
            aimAfterShotsLabel.color = Color(255F, 255F, 255F, 0F)
            aimAfterShotsSlider.color = Color(255F, 255F, 255F, 0F)
            aimAfterShotsSlider.isDisabled = true
        }

        aimBoneBox.selected = when (curWep.tAimBone) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            NEAREST_BONE -> "NEAREST"
            else -> "RANDOM"
        }
        aimFovLabel.setText("${curLocalization["FOV"]}: " + curWep.tAimFov)
        aimFovSlider.value = curWep.tAimFov.toFloat()
        aimSpeedLabel.setText("${curLocalization["OVERRIDE_AIM_SPEED"]} " + curWep.tAimSpeed)
        aimSpeedSlider.value = curWep.tAimSpeed.toFloat()
        aimSmoothnessLabel.setText("${curLocalization["OVERRIDE_SMOOTH"]} " + curWep.tAimSmooth)
        aimSmoothnessSlider.value = curWep.tAimSmooth.toFloat()
        aimAfterShotsLabel.setText("${curLocalization["AIM_AFTER_SHOTS"]} :" + curWep.tAimAfterShots)
        aimAfterShotsSlider.value = curWep.tAimAfterShots.toFloat()
        perfectAimCheckBox.isChecked = curWep.tPerfectAim
        perfectAimCollapsible.isCollapsed = !curWep.tPerfectAim
        perfectAimFovLabel.setText("${curLocalization["FOV"]}: " + curWep.tPAimFov)
        perfectAimFovSlider.value = curWep.tPAimFov.toFloat()
        perfectAimChanceLabel.setText("${curLocalization["PERFECT_AIM_CHANCE"]}: " + curWep.tPAimChance)
        perfectAimChanceSlider.value = curWep.tPAimChance.toFloat()
    }
}