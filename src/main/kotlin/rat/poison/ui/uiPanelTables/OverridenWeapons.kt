package rat.poison.ui.uiPanelTables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.*
import rat.poison.*
import rat.poison.settings.*
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.VisLabelCustom
import rat.poison.ui.uiPanels.overridenWeapons
import rat.poison.ui.uiUpdate
import rat.poison.utils.GetWeaponsMap
import kotlin.math.round

class OverridenWeapons : VisTable(true) {
    //private val table = VisTable(true)
    var categorySelected = curLocalization[curSettings["DEFAULT_CATEGORY_SELECTED"]]
    var weaponOverride = false
    var enableOverride = false
    var weaponOverrideSelected = ""

    private val categorySelectionBox = VisSelectBox<String>()

    //Override Weapon Checkbox & Selection Box
    private val categorySelectLabel = VisLabelCustom(curLocalization["CATEGORY"], nameInLocalization = "CATEGORY")

    private val weaponOverrideSelectionBox = VisSelectBox<String>()
    val weaponOverrideEnableCheckBox = VisCheckBox(curLocalization["ENABLE_OVERRIDE"])

    val enableFactorRecoil = VisCheckBox(curLocalization["FACTOR_RECOIL"])
    val enableFlatAim = VisCheckBox(curLocalization["ENABLE_FLAT_AIM"])
    val enablePathAim = VisCheckBox(curLocalization["ENABLE_PATH_AIM"])
    val enableScopedOnly = VisCheckBox(curLocalization["SNIPER_ENABLE_SCOPED_ONLY"])

    private val aimBoneLabel = VisLabelCustom(curLocalization["AIM_BONE"], nameInLocalization = "AIM_BONE")
    val aimBoneBox = VisSelectBox<String>()

    val aimFovLabel = VisLabelCustom("${curLocalization["FOV"]}: "  + curSettings[categorySelected + "_AIM_FOV"].toInt().toString(), nameInLocalization = "FOV")
    val aimFovSlider = VisSlider(1F, 180F, 1F, false)

    val aimSpeedLabel = VisLabelCustom("${curLocalization["OVERRIDE_AIM_SPEED"]} " + curSettings[categorySelected + "_AIM_SPEED"].toInt().toString(), nameInLocalization = "OVERRIDE_AIM_SPEED")
    val aimSpeedSlider = VisSlider(0F, 5F, 1F, false)

    val aimSmoothnessLabel = VisLabelCustom("${curLocalization["OVERRIDE_SMOOTH"]} " + curSettings[categorySelected + "_AIM_SMOOTHNESS"].toFloat(), nameInLocalization = "OVERRIDE_SMOOTH")
    val aimSmoothnessSlider = VisSlider(1F, 5F, 0.1F, false)

    val aimAfterShotsLabel = VisLabelCustom("${curLocalization["AIM_AFTER_SHOTS"]} :" + curSettings[categorySelected + "AIM_AFTER_SHOTS"], nameInLocalization = "AIM_AFTER_SHOTS")
    val aimAfterShotsSlider = VisSlider(1F, 10F, 1F, false)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox(curLocalization["ENABLE_PERFECT_AIM"])
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFovLabel = VisLabelCustom("${curLocalization["FOV"]}: " + curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt().toString(), nameInLocalization = "FOV")
    val perfectAimFovSlider = VisSlider(1F, 180F, 1F, false)
    val perfectAimChanceLabel = VisLabelCustom("${curLocalization["PERFECT_AIM_CHANCE"]}: " + curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toInt().toString(), nameInLocalization = "PERFECT_AIM_CHANCE")
    val perfectAimChanceSlider = VisSlider(1F, 100F, 1F, false)

    init {
        val WeaponsMap = GetWeaponsMap()
        align(Align.left)
        val map = aimingMap()
        //Create Category Selector Box
        val categorySelection = VisTable()
        categorySelectionBox.setItems(curLocalization["PISTOL"], curLocalization["RIFLE"], curLocalization["SMG"], curLocalization["SNIPER"], curLocalization["SHOTGUN"])
        categorySelectionBox.selected = curLocalization[curSettings["DEFAULT_CATEGORY_SELECTED"]]
        categorySelected = map[categorySelectionBox.selected]
        categorySelection.add(categorySelectLabel).padRight(125F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox).width(125F)

        categorySelectionBox.changed { _, _ ->
            categorySelected = map[categorySelectionBox.selected]
            when (categorySelected)
            {
                "PISTOL" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems(curLocalization["DESERT_EAGLE"], curLocalization["DUAL_BERRETA"], curLocalization["FIVE_SEVEN"], curLocalization["GLOCK"], curLocalization["USP_SILENCER"], curLocalization["CZ75A"], curLocalization["R8_REVOLVER"], curLocalization["P2000"], curLocalization["TEC9"], curLocalization["P250"]) }
                "SMG" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems(curLocalization["MAC10"], curLocalization["P90"], curLocalization["MP5"], curLocalization["UMP45"], curLocalization["MP7"], curLocalization["MP9"], curLocalization["PP_BIZON"]) }
                "RIFLE" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems(curLocalization["AK47"], curLocalization["AUG"], curLocalization["FAMAS"], curLocalization["SG553"], curLocalization["GALIL"], curLocalization["M4A4"], curLocalization["M4A1_SILENCER"], curLocalization["NEGEV"], curLocalization["M249"]) }
                "SNIPER" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems(curLocalization["AWP"], curLocalization["G3SG1"], curLocalization["SCAR20"], curLocalization["SSG08"]) }
                "SHOTGUN" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems(curLocalization["XM1014"], curLocalization["MAG7"], curLocalization["SAWED_OFF"], curLocalization["NOVA"]) }
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
        weaponOverrideSelectionBox.setItems(curLocalization["DESERT_EAGLE"], curLocalization["DUAL_BERRETA"], curLocalization["FIVE_SEVEN"], curLocalization["GLOCK"], curLocalization["USP_SILENCER"], curLocalization["CZ75A"], curLocalization["R8_REVOLVER"], curLocalization["P2000"], curLocalization["TEC9"], curLocalization["P250"])
        weaponOverrideSelectionBox.selected = curLocalization["DESERT_EAGLE"]
        weaponOverrideSelected = WeaponsMap[weaponOverrideSelectionBox.selected]
        weaponOverrideSelection.add(weaponOverrideSelectionBox).width(125F).padLeft(125F)

        weaponOverrideSelectionBox.changed { _, _ ->
            if (!weaponOverrideSelectionBox.selected.isNullOrEmpty()) {
                weaponOverrideSelected = WeaponsMap[weaponOverrideSelectionBox.selected]
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
        aimBoneBox.setItems(curLocalization["HEAD"], curLocalization["NECK"], curLocalization["CHEST"], curLocalization["STOMACH"], curLocalization["NEAREST"], curLocalization["RANDOM"])
        aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"].toInt()) {
            HEAD_BONE -> curLocalization["HEAD"]
            NECK_BONE -> curLocalization["NECK"]
            CHEST_BONE -> curLocalization["CHEST"]
            STOMACH_BONE -> curLocalization["STOMACH"]
            NEAREST_BONE -> curLocalization["NEAREST"]
            else -> curLocalization["RANDOM"]
        }
        aimBone.add(aimBoneLabel).width(125F)
        aimBone.add(aimBoneBox).width(125F)

        aimBoneBox.changed { _, _ ->
            val setBone = curSettings[map[aimBoneBox.selected] + "_BONE"].toInt()

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