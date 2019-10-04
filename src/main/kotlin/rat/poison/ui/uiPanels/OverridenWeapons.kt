package rat.poison.ui.uiPanels

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import rat.poison.*
import rat.poison.App.menuStage
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.settings.*
import rat.poison.ui.*
import kotlin.math.round

class OverridenWeapons : VisTable(true) {
    //private val table = VisTable(true)

    var categorySelected = "PISTOL"
    var weaponOverride = false
    var enableOverride = false
    var weaponOverrideSelected = ""

    private val categorySelectionBox = VisSelectBox<String>()

    //Override Weapon Checkbox & Selection Box
    private val categorySelectLabel = VisLabel("Category: ")

    private val weaponOverrideSelectionBox = VisSelectBox<String>()
    val weaponOverrideEnableCheckBox = VisCheckBox("Enable Override")

    val enableFactorRecoil = VisCheckBox("Factor Recoil")
    val enableFlatAim = VisCheckBox("Flat Aim")
    val enablePathAim = VisCheckBox("Path Aim")
    val enableScopedOnly = VisCheckBox("Scoped Only")

    private val aimBoneLabel = VisLabel("Bone: ")
    val aimBoneBox = VisSelectBox<String>()

    val aimFovLabel = VisLabel("FOV: " + curSettings[categorySelected + "_AIM_FOV"].toInt().toString())
    val aimFovSlider = VisSlider(1F, 180F, 1F, false)

    val aimSpeedLabel = VisLabel("Speed: " + curSettings[categorySelected + "_AIM_SPEED"].toInt().toString())
    val aimSpeedSlider = VisSlider(0F, 5F, 1F, false)

    val aimSmoothnessLabel = VisLabel("Smooth: " + curSettings[categorySelected + "_AIM_SMOOTHNESS"].toFloat())
    val aimSmoothnessSlider = VisSlider(1F, 5F, 0.1F, false)

    val aimStrictnessLabel = VisLabel("Strictness: " + curSettings[categorySelected + "_AIM_STRICTNESS"])
    val aimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim")
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFovLabel = VisLabel("FOV: " + curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt().toString())
    val perfectAimFovSlider = VisSlider(1F, 45F, 1F, false)
    val perfectAimChanceLabel = VisLabel("Chance: " + curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toInt().toString())
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
        aimBoneBox.setItems("HEAD", "NECK", "CHEST", "STOMACH", "NEAREST")
        aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"].toInt()) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            else -> "NEAREST"
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

            aimFovLabel.setText("FOV: " + aimFovSlider.value.toInt())
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
            aimSpeedLabel.setText("Speed: " + aimSpeedSlider.value.toInt())
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
            aimSmoothnessLabel.setText("Smooth: " + (round(aimSmoothnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimSmoothness.add(aimSmoothnessLabel).width(125F)
        aimSmoothness.add(aimSmoothnessSlider).width(125F)

        //Create Aim Strictness Slider
        val aimStrictness = VisTable()
        aimStrictnessSlider.value = curSettings[categorySelected + "_AIM_STRICTNESS"].toFloat()
        aimStrictnessSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tAimStrict = aimStrictnessSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            aimStrictnessLabel.setText("Strictness: " + (round(aimStrictnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimStrictness.add(aimStrictnessLabel).width(125F)
        aimStrictness.add(aimStrictnessSlider).width(125F)
        //Create Perfect Aim Collapsible Check Box
        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimCollapsible.setCollapsed(!curSettings[categorySelected + "_PERFECT_AIM"].strToBool(), true)

        //Create Perfect Aim Fov Slider
        val perfectAimFov = VisTable()
        perfectAimFovSlider.value = curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt().toFloat()
        perfectAimFovSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tPAimFov = perfectAimFovSlider.value.toInt()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
            perfectAimFovLabel.setText("FOV: " + perfectAimFovSlider.value.toInt())
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
            perfectAimChanceLabel.setText("Chance: " + perfectAimChanceSlider.value.toInt())
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
        add(aimStrictness).left().row()
        add(perfectAimCheckBox).left().row()
        add(perfectAimCollapsible).left().row()
    }
}

fun updateEnable() {

}

//fun updateOverridenWeapons() { //This isn't needed... because the Override Weapons hides it
//    overridenWeapons.apply {
//        val bool = !aimTab.enableAim.isChecked
//        var col = Color(255F, 255F, 255F, 1F)
//        if (bool) {
//            col = Color(105F, 105F, 105F, .2F)
//        }
//        aimTab.weaponOverrideCheckBox.isDisabled = bool
//        categorySelectLabel.color = col
//        categorySelectionBox.isDisabled = bool
//        weaponOverrideSelectionBox.isDisabled = bool
//        //if (!weaponOverride) {
//        //    weaponOverrideEnableCheckBox.isDisabled = true
//        //} else {
//        //    weaponOverrideEnableCheckBox.isDisabled = bool
//        //}
//        enableFactorRecoil.isDisabled = bool
//        enableFlatAim.isDisabled = bool
//        enablePathAim.isDisabled = bool
//        enableScopedOnly.isDisabled = bool
//        aimBoneLabel.color = col
//        aimBoneBox.isDisabled = bool
//        aimFovLabel.color = col
//        aimFovSlider.isDisabled = bool
//        aimSpeedLabel.color = col
//        aimSpeedSlider.isDisabled = bool
//        aimSmoothnessLabel.color = col
//        aimSmoothnessSlider.isDisabled = bool
//        aimStrictnessLabel.color = col
//        aimStrictnessSlider.isDisabled = bool
//        perfectAimCollapsible.isCollapsed = bool
//        perfectAimCheckBox.isDisabled = bool
//        perfectAimChanceLabel.color = col
//        perfectAimChanceSlider.isDisabled = bool
//        perfectAimFovLabel.color = col
//        perfectAimFovSlider.isDisabled = bool
//    }
//}