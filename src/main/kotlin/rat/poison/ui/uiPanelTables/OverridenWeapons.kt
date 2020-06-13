package rat.poison.ui.uiPanelTables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.*
import rat.poison.*
import rat.poison.settings.*
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.overrideWeaponsUI.OverrideVisCheckBoxCustom
import rat.poison.ui.uiHelpers.overrideWeaponsUI.OverrideVisSliderCustom
import rat.poison.ui.uiHelpers.VisCheckBoxCustomWithoutVar
import rat.poison.ui.uiHelpers.VisLabelCustom
import rat.poison.ui.uiPanels.overridenWeapons
import rat.poison.ui.uiUpdate
import rat.poison.utils.GetWeaponsMap
import kotlin.math.round

var weaponOverrideSelected = "DESERT_EAGLE"

class OverridenWeapons : VisTable(true) {
    //private val table = VisTable(true)
    var map = aimingMap()
    var weaponsMap = GetWeaponsMap()
    var categorySelected = curSettings["DEFAULT_CATEGORY_SELECTED"]
    var weaponOverride = false
    var enableOverride = false

    private val categorySelectionBox = VisSelectBox<String>()

    //Override Weapon Checkbox & Selection Box
    val categorySelectLabel = VisLabelCustom(curLocalization["CATEGORY"], nameInLocalization = "CATEGORY")

    private val weaponOverrideSelectionBox = VisSelectBox<String>()
    val weaponOverrideEnableCheckBox = VisCheckBoxCustomWithoutVar(curLocalization["ENABLE_OVERRIDE"], "ENABLE_OVERRIDE")

    val enableFactorRecoil = OverrideVisCheckBoxCustom("Factor Recoil", "tFRecoil")
    val enableFlatAim = OverrideVisCheckBoxCustom("Write Angles", "tFlatAim")
    val enablePathAim = OverrideVisCheckBoxCustom("Mouse Movement", "tPathAim")
    val enableScopedOnly = OverrideVisCheckBoxCustom("Scoped Only", "tScopedOnly")

    val aimBoneLabel = VisLabelCustom(curLocalization["AIM_BONE"], nameInLocalization = "AIM_BONE")
    val aimBoneBox = VisSelectBox<String>()

    val aimFov = OverrideVisSliderCustom("FOV", "tAimFov", 1F, 180F, 1F, true, width1 = 125F, width2 = 125F)
    val aimSpeed = OverrideVisSliderCustom("Speed", "tAimSpeed", 0F, 10F, 1F, true, width1 = 125F, width2 = 125F)
    val aimSmoothness = OverrideVisSliderCustom("Smooth", "tAimSmooth", 1F, 5F, .1F, false, width1 = 125F, width2 = 125F)
    val aimAfterShots = OverrideVisSliderCustom("Aim After #", "tAimAfterShots", 0F, 10F, 1F, true, width1 = 125F, width2 = 125F)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = OverrideVisCheckBoxCustom("Perfect Aim", "tPerfectAim")
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFov = OverrideVisSliderCustom("FOV", "tPAimFov", 1F, 180F, 1F, true, width1 = 125F, width2 = 125F)
    val perfectAimChance = OverrideVisSliderCustom("Chance", "tPAimChance", 1F, 100F, 1F, true, width1 = 125F, width2 = 125F)

    fun updateMap () {
        map = aimingMap()
        weaponsMap = GetWeaponsMap()
    }
    init {
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
                enableScopedOnly.disable(false)
            } else {
                enableScopedOnly.disable(true)
            }

            if (categorySelected == "RIFLE" || categorySelected == "SMG") {
                aimAfterShots.disable(false, Color(255F, 255F, 255F, 1F))
            } else {
                aimAfterShots.disable(true, Color(105F, 105F, 105F, .2F))
            }

            weaponOverrideSelectionBox.selected = weaponOverrideSelectionBox.items[0]
            uiUpdate()
            true
        }

        //Create Override Weapon Selector
        val weaponOverrideSelection = VisTable()
        weaponOverrideSelectionBox.setItems(curLocalization["DESERT_EAGLE"], curLocalization["DUAL_BERRETA"], curLocalization["FIVE_SEVEN"], curLocalization["GLOCK"], curLocalization["USP_SILENCER"], curLocalization["CZ75A"], curLocalization["R8_REVOLVER"], curLocalization["P2000"], curLocalization["TEC9"], curLocalization["P250"])
        weaponOverrideSelectionBox.selected = curLocalization["DESERT_EAGLE"]
        weaponOverrideSelected = weaponsMap[weaponOverrideSelectionBox.selected]
        weaponOverrideSelection.add(weaponOverrideSelectionBox).width(125F).padLeft(125F)

        weaponOverrideSelectionBox.changed { _, _ ->
            if (!weaponOverrideSelectionBox.selected.isNullOrEmpty()) {
                weaponOverrideSelected = weaponsMap[weaponOverrideSelectionBox.selected]
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

        //Create Perfect Aim Collapsible Check Box
        perfectAimCollapsible.setCollapsed(!curSettings[categorySelected + "_PERFECT_AIM"].strToBool(), true)

        perfectAimTable.add(perfectAimFov).padLeft(20F).left().row()
        perfectAimTable.add(perfectAimChance).padLeft(20F).left().row()

        perfectAimCheckBox.changed { _, _ ->
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
        val curWep = curSettings[weaponOverrideSelected].toWeaponClass()

        overridenWeapons.weaponOverrideEnableCheckBox.isChecked = curWep.tOverride

        if (categorySelected == "SNIPER") {
            enableScopedOnly.disable(false)
        } else {
            enableScopedOnly.disable(true)
        }

        enableFactorRecoil.update()
        enableFlatAim.update()
        enablePathAim.update()
        enableScopedOnly.update()

        if (categorySelected == "RIFLE" || categorySelected == "SMG") {
            aimAfterShots.disable(false, Color(255F, 255F, 255F, 1F))
        } else {
            aimAfterShots.disable(true, Color(105F, 105F, 105F, .2F))
        }

        aimBoneBox.selected = when (curWep.tAimBone) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            NEAREST_BONE -> "NEAREST"
            else -> "RANDOM"
        }
        aimFov.update()
        aimSpeed.update()
        aimSmoothness.update()
        aimAfterShots.update()

        perfectAimCheckBox.isChecked = curWep.tPerfectAim
        perfectAimCollapsible.isCollapsed = !curWep.tPerfectAim
        perfectAimFov.update()
        perfectAimChance.update()
    }
}