package rat.poison.ui.uiPanelTables

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.CollapsibleWidget
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.settings.*
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.tabs.*
import rat.poison.ui.uiHelpers.overrideWeaponsUI.OverrideVisCheckBoxCustom
import rat.poison.ui.uiHelpers.overrideWeaponsUI.OverrideVisSliderCustom
import rat.poison.ui.uiPanels.overridenWeapons
import rat.poison.ui.uiUpdate
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.toWeaponClass

var weaponOverrideSelected = "DESERT_EAGLE"

class OverridenWeapons : VisTable(false) {
    //private val table = VisTable(true)
    var categorySelected = curSettings["DEFAULT_CATEGORY_SELECTED"]
    var weaponOverride = false
    var enableOverride = false

    private val categorySelectionBox = VisSelectBox<String>()

    //Override Weapon Checkbox & Selection Box
    private val categorySelectLabel = VisLabel("${"Weapon-Category".toLocale()}:")

    private val weaponOverrideSelectionBox = VisSelectBox<String>()
    val weaponOverrideEnableCheckBox = OverrideVisCheckBoxCustom("Enable Override", "tOverride")

    val enableFactorRecoil = OverrideVisCheckBoxCustom("Factor Recoil", "tFRecoil")
    val enableOnShot = OverrideVisCheckBoxCustom("On Shot", "tOnShot")
    val enableFlatAim = OverrideVisCheckBoxCustom("Flat Aim", "tFlatAim")
    val enablePathAim = OverrideVisCheckBoxCustom("Path Aim", "tPathAim")
    val enableScopedOnly = OverrideVisCheckBoxCustom("Scoped Only", "tScopedOnly")

    //TODO Labels might need locale updates?
    private val aimBoneLabel = VisLabel("Bone".toLocale())
    val aimBoneBox = VisSelectBox<String>()
    private val forceBoneLabel = VisLabel("Force-Bone".toLocale())
    val forceBoneBox = VisSelectBox<String>()

    val aimFov = OverrideVisSliderCustom("FOV", "tAimFov", 0.5F, 90F, 0.5F, false, width1 = 225F, width2 = 125F)
    val aimSpeed = OverrideVisSliderCustom("Speed", "tAimSpeed", 0F, 10F, 1F, true, width1 = 225F, width2 = 125F)
    val aimSmoothness = OverrideVisSliderCustom("Smooth", "tAimSmooth", 1F, 5F, .1F, false, width1 = 225F, width2 = 125F)
    val aimAfterShots = OverrideVisSliderCustom("Aim After #", "tAimAfterShots", 0F, 10F, 1F, true, width1 = 225F, width2 = 125F)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = OverrideVisCheckBoxCustom("Perfect Aim", "tPerfectAim")
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFov = OverrideVisSliderCustom("FOV", "tPAimFov", 1F, 90F, .5F, false, width1 = 225F, width2 = 125F)
    val perfectAimChance = OverrideVisSliderCustom("Chance", "tPAimChance", 1F, 100F, 1F, true, width1 = 225F, width2 = 125F)

    val trigEnable = OverrideVisCheckBoxCustom("Enable Trigger", "tBoneTrig")
    val trigAimbot = OverrideVisCheckBoxCustom("Aimbot", "tBTrigAim")
    val trigInCross = OverrideVisCheckBoxCustom("InCross", "tBTrigInCross")
    val trigInFov = OverrideVisCheckBoxCustom("InFov", "tBTrigInFov")
    val trigBacktrack = OverrideVisCheckBoxCustom("Shoot Backtrack", "tBTrigBacktrack")
    val trigFov = OverrideVisSliderCustom("FOV", "tBTrigFov", 0.5F, 90F, 0.5F, false, width1 = 225F, width2 = 125F)
    val trigInitDelay = OverrideVisSliderCustom("Init Shot Delay", "tBTrigInitDelay", 0F, 500F, 10F, true, width1 = 225F, width2 = 125F)
    val trigPerShotDelay = OverrideVisSliderCustom("Per Shot Delay", "tBTrigPerShotDelay", 0F, 500F, 10F, true, width1 = 225F, width2 = 125F)

    val enableBacktrack = OverrideVisCheckBoxCustom("Enable Backtrack", "tBacktrack")
    val backtrackMS = OverrideVisSliderCustom("Backtrack MS", "tBTMS", 20F, 200F, 5F, true, width1 = 225F, width2 = 125F)

    init {
        align(Align.left)

        categorySelectionBox.changed { _, _ ->
            categorySelected = gunCategories[categorySelectionBox.selectedIndex]

            val tmpCategory = when (categorySelected)
            {
                "PISTOL" -> { weaponOverrideSelectionBox.clearItems(); pistolCategory }
                "SMG" -> { weaponOverrideSelectionBox.clearItems(); smgCategory }
                "RIFLE" -> { weaponOverrideSelectionBox.clearItems(); rifleCategory }
                "SNIPER" -> { weaponOverrideSelectionBox.clearItems(); sniperCategory }
                else -> { weaponOverrideSelectionBox.clearItems(); shotgunCategory }
            }

            val itemsArray = Array<String>()
            for (i in tmpCategory) {
                if (dbg && curLocale[i].isBlank()) {
                    println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
                }

                itemsArray.add(curLocale[i])
            }
            weaponOverrideSelectionBox.items = itemsArray

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
        weaponOverrideSelection.add(weaponOverrideSelectionBox).width(125F).padLeft(225F)

        weaponOverrideSelectionBox.setItems("DESERT_EAGLE", "DUAL_BERETTA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250")
        weaponOverrideSelectionBox.changed { _, _ ->
            if (!weaponOverrideSelectionBox.selected.isNullOrEmpty()) {
                weaponOverrideSelected = weaponOverrideSelectionBox.selected
            }
            uiUpdate()
            true
        }
        //End Override Weapon Selection Box

        //Create Category Selector Box
        val categorySelection = VisTable()
        //Create Category Selector Box
        val itemsArray = Array<String>()
        for (i in gunCategories) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
            }

            itemsArray.add(curLocale[i])
        }

        categorySelectionBox.items = itemsArray
        categorySelectionBox.selectedIndex = 0

        categorySelected = gunCategories[categorySelectionBox.selectedIndex]
        categorySelection.add(categorySelectLabel).padRight(225F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox).width(125F)

        //Create Enable Override Toggle
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

        val boneArray = Array<String>()
        for (i in boneCategories) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
            }

            boneArray.add(curLocale[i])
        }
        aimBoneBox.items = boneArray
        aimBoneBox.selectedIndex = boneCategories.indexOf(curSettings[categorySelected + "_AIM_BONE"].uppercase())
        aimBone.add(aimBoneLabel).width(225F)
        aimBone.add(aimBoneBox).width(125F)

        aimBoneBox.changed { _, _ ->
            val setBone = curSettings[boneCategories[aimBoneBox.selectedIndex] + "_BONE"]
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tAimBone = setBone.toInt()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
        }
        //End Aim Bone Selector Box

        //Create Force Bone Selector Box
        val forceBone = VisTable()

        forceBoneBox.items = boneArray

        forceBoneBox.selectedIndex = boneCategories.indexOf(curSettings[categorySelected + "_AIM_BONE"].uppercase())
        forceBone.add(forceBoneLabel).width(225F)
        forceBone.add(forceBoneBox).width(125F)

        forceBoneBox.changed { _, _ ->
            val setBone = curSettings[boneCategories[forceBoneBox.selectedIndex] + "_BONE"]
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tForceBone = setBone.toInt()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
        }
        //End Force Bone Selector Box

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
        add(enableOnShot).left().row()
        add(enableFlatAim).left().row()
        add(enablePathAim).left().row()
        add(enableScopedOnly).left().row()
        add(aimBone).left().row()
        add(forceBone).left().row()
        add(aimSpeed).left().row()
        add(aimFov).left().row()
        add(aimSmoothness).left().row()
        add(aimAfterShots).left().row()
        add(perfectAimCheckBox).left().row()
        add(perfectAimCollapsible).left().row()

        addSeparator()

        add(trigEnable).left().row()
        add(trigAimbot).left().row()
        add(trigInCross).left().row()
        add(trigInFov).left().row()
        add(trigBacktrack).left().row()
        add(trigFov).left().row()
        add(trigInitDelay).left().row()
        add(trigPerShotDelay).left().row()

        addSeparator()

        add(enableBacktrack).left().row()
        add(backtrackMS).left().row()
    }
}

fun overridenWeaponsUpdate() {
    overridenWeapons.apply {
        val curWep = curSettings[weaponOverrideSelected].toWeaponClass()

        overridenWeapons.weaponOverrideEnableCheckBox.isChecked = curWep.tOverride

        if (categorySelected == "SNIPER") {
            enableScopedOnly.disable(false)
        } else {
            enableScopedOnly.disable(true)
        }

        enableFactorRecoil.update()
        enableOnShot.update()
        enableFlatAim.update()
        enablePathAim.update()
        enableScopedOnly.update()

        if (categorySelected == "RIFLE" || categorySelected == "SMG") {
            aimAfterShots.disable(false, Color(255F, 255F, 255F, 1F))
        } else {
            aimAfterShots.disable(true, Color(105F, 105F, 105F, .2F))
        }

        val boneArray = Array<String>()
        for (i in boneCategories) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
            }

            boneArray.add(curLocale[i])
        }

        if (boneArray != Array<String>()) {
            aimBoneBox.items = boneArray
            forceBoneBox.items = boneArray
        }

        aimBoneBox.selectedIndex = boneCategories.indexOf(when (curWep.tAimBone) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            NEAREST_BONE -> "NEAREST"
            else -> "RANDOM"
        })

        forceBoneBox.selectedIndex = boneCategories.indexOf(when (curWep.tForceBone) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            NEAREST_BONE -> "NEAREST"
            else -> "RANDOM"
        })

        aimFov.update()
        aimSpeed.update()

        aimSmoothness.update()
        aimAfterShots.update()

        perfectAimCheckBox.isChecked = curWep.tPerfectAim
        perfectAimCollapsible.isCollapsed = !curWep.tPerfectAim
        perfectAimFov.update()
        perfectAimChance.update()

        trigEnable.update()
        trigAimbot.update()
        trigInCross.update()
        trigInFov.update()
        trigBacktrack.update()
        trigFov.update()
        trigInitDelay.update()
        trigPerShotDelay.update()

        enableBacktrack.update()
        backtrackMS.update()
    }
}