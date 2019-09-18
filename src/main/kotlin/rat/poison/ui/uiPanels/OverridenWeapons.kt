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

    val categorySelectionBox = VisSelectBox<String>()

    //Override Weapon Checkbox & Selection Box
    val categorySelectLabel = VisLabel("Category: ")

    val weaponOverrideSelectionBox = VisSelectBox<String>()
    val weaponOverrideEnableCheckBox = VisCheckBox("Enable Override")

    val enableFactorRecoil = VisCheckBox("Factor Recoil")
    val enableFlatAim = VisCheckBox("Flat Aim")
    val enablePathAim = VisCheckBox("Path Aim")
    val enableScopedOnly = VisCheckBox("Scoped Only")

    val aimBoneLabel = VisLabel("Bone: ")
    val aimBoneBox = VisSelectBox<String>()

    val aimFovLabel = VisLabel("FOV: " + curSettings[categorySelected + "_AIM_FOV"].toInt().toString() + when(curSettings[categorySelected + "_AIM_FOV"].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val aimFovSlider = VisSlider(1F, 180F, 1F, false)

    val aimSpeedLabel = VisLabel("Speed: " + curSettings[categorySelected + "_AIM_SPEED"].toInt().toString() + when(curSettings[categorySelected + "_AIM_SPEED"].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val aimSpeedSlider = VisSlider(0F, 5F, 1F, false)

    val aimSmoothnessLabel = VisLabel("Smooth: " + curSettings[categorySelected + "_AIM_SMOOTHNESS"].toFloat())
    val aimSmoothnessSlider = VisSlider(1F, 5F, 0.1F, false)

    val aimStrictnessLabel = VisLabel("Strictness: " + curSettings[categorySelected + "_AIM_STRICTNESS"])
    val aimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim")
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFovLabel = VisLabel("FOV: " + curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt().toString() + when(curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val perfectAimFovSlider = VisSlider(1F, 45F, 1F, false)
    val perfectAimChanceLabel = VisLabel("Chance: " + curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toInt().toString() + when(curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val perfectAimChanceSlider = VisSlider(1F, 100F, 1F, false)

    init {
        align(Align.left)

        //Create Factor Recoil Toggle
        Tooltip.Builder("Whether or not to factor in recoil when aiming").target(enableFactorRecoil).build()
        enableFactorRecoil.isChecked = curSettings[categorySelected + "_ENABLE_PATH_AIM"].strToBool()
        enableFactorRecoil.changed { _, _ ->
            if (weaponOverride) {
                val curWep: Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[1] = enableFactorRecoil.isChecked.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            } else {
                curSettings[categorySelected + "_FACTOR_RECOIL"] = enableFactorRecoil.isChecked.boolToStr()
            }
            uiUpdate()
            true
        }

        //Create Category Selector Box
        val categorySelection = VisTable()
        Tooltip.Builder("The weapon category settings to edit").target(categorySelection).build()
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
        Tooltip.Builder("Whether or not to override aim when this gun is selected").target(weaponOverrideEnableCheckBox).build()
        weaponOverrideEnableCheckBox.isChecked = convStrToArray(curSettings[weaponOverrideSelected])[0]!!.toBool()
        weaponOverrideEnableCheckBox.changed { _, _ ->
            val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
            curWep[0] = weaponOverrideEnableCheckBox.isChecked.toDouble()
            curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            enableOverride = weaponOverrideEnableCheckBox.isChecked
            uiUpdate()
            true
        }

        //Create Flat Aim Toggle
        enableFlatAim.isChecked = curSettings[categorySelected + "_ENABLE_FLAT_AIM"].strToBool()
        enableFlatAim.changed { _, _ ->
            if (weaponOverride)
            {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[2] = enableFlatAim.isChecked.toDouble()
                if (enableFlatAim.isChecked)
                {
                    curWep[3] = 0.0
                }
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_ENABLE_FLAT_AIM"] = enableFlatAim.isChecked.boolToStr()
                if (curSettings[categorySelected + "_ENABLE_FLAT_AIM"].strToBool()) {curSettings[categorySelected + "_ENABLE_PATH_AIM"] = "false"}
            }
            uiUpdate()
            true
        }

        //Create Path Aim Toggle
        enablePathAim.isChecked = curSettings[categorySelected + "_ENABLE_PATH_AIM"].strToBool()
        enablePathAim.changed { _, _ ->
            if (weaponOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[3] = enablePathAim.isChecked.toDouble()
                if (enablePathAim.isChecked)
                {
                    curWep[2] = 0.0
                }
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_ENABLE_PATH_AIM"] = enablePathAim.isChecked.boolToStr()
                if (curSettings[categorySelected + "_ENABLE_PATH_AIM"].strToBool()) {curSettings[categorySelected + "_ENABLE_FLAT_AIM"] = "false"}
            }
            uiUpdate()
            true
        }

        //Create Scoped Only Toggle
        enableScopedOnly.isChecked = curSettings["SNIPER_ENABLE_SCOPED_ONLY"].strToBool()
        enableScopedOnly.isDisabled = true
        enableScopedOnly.changed { _, _ ->
            if (weaponOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[12] = enableScopedOnly.isChecked.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings["SNIPER_ENABLE_SCOPED_ONLY"] = enableScopedOnly.isChecked.boolToStr()
            }
            uiUpdate()
            true
        }

        //Create Aim Bone Selector Box
        val aimBone = VisTable()
        Tooltip.Builder("The default aim bone to aim at").target(aimBone).build()
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
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[4] = setBone.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            } else {
                curSettings[categorySelected + "_AIM_BONE"] = setBone.toString()
            }
        }

        //Create Aim FOV Slider
        val aimFov = VisTable()
        Tooltip.Builder("The aim field of view").target(aimFov).build()
        aimFovSlider.value = curSettings[categorySelected + "_AIM_FOV"].toInt().toFloat()
        aimFovSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[5] = aimFovSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            } else {
                curSettings[categorySelected + "_AIM_FOV"] = aimFovSlider.value.toInt().toString()
            }

            aimFovLabel.setText("FOV: " + aimFovSlider.value.toInt())
        }
        aimFov.add(aimFovLabel).width(125F)
        aimFov.add(aimFovSlider).width(125F)

        //Create Aim Speed Slider
        val aimSpeed = VisTable()
        Tooltip.Builder("The aim speed delay in milliseconds").target(aimSpeed).build()
        aimSpeedSlider.value = curSettings[categorySelected + "_AIM_SPEED"].toInt().toFloat()
        aimSpeedSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[6] = aimSpeedSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            } else {
                curSettings[categorySelected + "_AIM_SPEED"] = aimSpeedSlider.value.toInt().toString()
            }
            aimSpeedLabel.setText("Speed: " + aimSpeedSlider.value.toInt())
        }
        aimSpeed.add(aimSpeedLabel).width(125F)
        aimSpeed.add(aimSpeedSlider).width(125F)

        //Create Aim Smoothness Slider
        val aimSmoothness = VisTable()
        Tooltip.Builder("The smoothness of the aimbot (path aim only)").target(aimSmoothness).build()
        aimSmoothnessSlider.value = curSettings[categorySelected + "_AIM_SMOOTHNESS"].toFloat()
        aimSmoothnessSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[7] = aimSmoothnessSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_AIM_SMOOTHNESS"] = (round(aimSmoothnessSlider.value.toDouble() * 10.0) / 10.0).toString()
            }
            aimSmoothnessLabel.setText("Smooth: " + (round(aimSmoothnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimSmoothness.add(aimSmoothnessLabel).width(125F)
        aimSmoothness.add(aimSmoothnessSlider).width(125F)

        //Create Aim Strictness Slider
        val aimStrictness = VisTable()
        Tooltip.Builder("How strict moving to the aimbone is, lower = less deviation").target(aimStrictness).build()
        aimStrictnessSlider.value = curSettings[categorySelected + "_AIM_STRICTNESS"].toFloat()
        aimStrictnessSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[8] = aimStrictnessSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_AIM_STRICTNESS"] = (round(aimStrictnessSlider.value.toDouble() * 10.0) / 10.0).toString()
            }
            aimStrictnessLabel.setText("Strictness: " + (round(aimStrictnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimStrictness.add(aimStrictnessLabel).width(125F)
        aimStrictness.add(aimStrictnessSlider).width(125F)
        //Create Perfect Aim Collapsible Check Box
        Tooltip.Builder("Whether or not to enable perfect aim").target(perfectAimCheckBox).build()
        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimCollapsible.setCollapsed(!curSettings[categorySelected + "_PERFECT_AIM"].strToBool(), true)

        //Create Perfect Aim Fov Slider
        val perfectAimFov = VisTable()
        Tooltip.Builder("The perfect aim field of view").target(perfectAimFov).build()
        perfectAimFovSlider.value = curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt().toFloat()
        perfectAimFovSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[10] = perfectAimFovSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_PERFECT_AIM_FOV"] = perfectAimFovSlider.value.toInt().toString()
            }
            perfectAimFovLabel.setText("FOV: " + perfectAimFovSlider.value.toInt())
        }
        perfectAimFov.add(perfectAimFovLabel).width(105F)
        perfectAimFov.add(perfectAimFovSlider).width(125F)
        //End Perfect Aim Fov Slider

        //Create Perfect Aim Chance Slider
        val perfectAimChance = VisTable()
        Tooltip.Builder("The perfect aim chance (per calculation)").target(perfectAimChance).build()
        perfectAimChanceSlider.value = curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toInt().toFloat()
        perfectAimChanceSlider.changed { _, _ ->
            if (weaponOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[11] = perfectAimChanceSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_PERFECT_AIM_CHANCE"] = perfectAimChanceSlider.value.toInt().toString()
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
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[9] = perfectAimCheckBox.isChecked.boolToDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
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

fun updateOverridenWeapons() {
    overridenWeapons.apply {
        val bool = !aimTab.enableAim.isChecked
        var col = Color(255F, 255F, 255F, 1F)
        if (bool) {
            col = Color(105F, 105F, 105F, .2F)
        }
        aimTab.weaponOverrideCheckBox.isDisabled = bool
        categorySelectLabel.color = col
        categorySelectionBox.isDisabled = bool
        weaponOverrideSelectionBox.isDisabled = bool
        if (!weaponOverride) {
            weaponOverrideEnableCheckBox.isDisabled = true
        } else {
            weaponOverrideEnableCheckBox.isDisabled = bool
        }
        enableFactorRecoil.isDisabled = bool
        enableFlatAim.isDisabled = bool
        enablePathAim.isDisabled = bool
        enableScopedOnly.isDisabled = bool
        aimBoneLabel.color = col
        aimBoneBox.isDisabled = bool
        aimFovLabel.color = col
        aimFovSlider.isDisabled = bool
        aimSpeedLabel.color = col
        aimSpeedSlider.isDisabled = bool
        aimSmoothnessLabel.color = col
        aimSmoothnessSlider.isDisabled = bool
        aimStrictnessLabel.color = col
        aimStrictnessSlider.isDisabled = bool
        perfectAimCollapsible.isCollapsed = bool
        perfectAimCheckBox.isDisabled = bool
        perfectAimChanceLabel.color = col
        perfectAimChanceSlider.isDisabled = bool
        perfectAimFovLabel.color = col
        perfectAimFovSlider.isDisabled = bool
    }
}