package rat.poison.ui.tabs.aimtabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.settings.*
import rat.poison.toLocale
import rat.poison.ui.changed
import rat.poison.ui.tabs.*
import rat.poison.ui.uiHelpers.overrideWeaponsUI.OverrideVisCheckBoxCustom
import rat.poison.ui.uiHelpers.overrideWeaponsUI.OverrideVisSliderCustom
import rat.poison.ui.uiUpdate
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.toWeaponClass

var weaponOverrideSelected = "DESERT_EAGLE"

class OverrideTab: Tab(true, false) {
    private val table = VisTable()
    val weaponOverrideCheckBox = VisCheckBox("Override-Weapons".toLocale())
    var categorySelected = curSettings["DEFAULT_CATEGORY_SELECTED"]
    var weaponOverride = false
    var enableOverride = false

    private val categorySelectionBox = VisSelectBox<String>()

    //Override Weapon Checkbox & Selection Box
    private val categorySelectLabel = VisLabel("${"Weapon-Category".toLocale()}:")
    private val weaponSelectLabel = VisLabel("${"Weapon".toLocale()}:")

    private val weaponOverrideSelectionBox = VisSelectBox<String>()
    val weaponOverrideEnableCheckBox = OverrideVisCheckBoxCustom("Enable Override", "tOverride")

    val enableFactorRecoil = OverrideVisCheckBoxCustom("Factor Recoil", "tFRecoil")
    val enableOnShot = OverrideVisCheckBoxCustom("On Shot", "tOnShot")
    val enableFlatAim = OverrideVisCheckBoxCustom("Flat Aim", "tFlatAim")
    val enablePathAim = OverrideVisCheckBoxCustom("Path Aim", "tPathAim")
    val enableScopedOnly = OverrideVisCheckBoxCustom("Scoped Only", "tScopedOnly")

    private val aimBoneLabel = VisLabel("Bone".toLocale())
    val aimBoneBox = VisSelectBox<String>()
    private val forceBoneLabel = VisLabel("Force-Bone".toLocale())
    val forceBoneBox = VisSelectBox<String>()

    val aimFov = OverrideVisSliderCustom("FOV", "tAimFov", 0.5F, 90F, 0.5F, false, labelWidth = 225F, barWidth = 225F)
    val aimSpeed = OverrideVisSliderCustom("Speed", "tAimSpeed", 0F, 10F, 1F, true, labelWidth = 225F, barWidth = 225F)
    val aimSmoothness = OverrideVisSliderCustom("Smooth", "tAimSmooth", 1F, 5F, .5F, false, labelWidth = 225F, barWidth = 225F)
    val aimAfterShots = OverrideVisSliderCustom("Aim After #", "tAimAfterShots", 0F, 10F, 1F, true, labelWidth = 225F, barWidth = 225F)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = OverrideVisCheckBoxCustom("Perfect Aim", "tPerfectAim")
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFov = OverrideVisSliderCustom("FOV", "tPAimFov", 1F, 90F, .5F, false, labelWidth = 225F, barWidth = 225F)
    val perfectAimChance = OverrideVisSliderCustom("Chance", "tPAimChance", 1F, 100F, 1F, true, labelWidth = 225F, barWidth = 225F)

    val trigEnable = OverrideVisCheckBoxCustom("Enable Trigger", "tBoneTrig")
    val trigAimbot = OverrideVisCheckBoxCustom("Aimbot", "tBTrigAim")
    val trigInCross = OverrideVisCheckBoxCustom("InCross", "tBTrigInCross")
    val trigInFov = OverrideVisCheckBoxCustom("InFov", "tBTrigInFov")
    val trigBacktrack = OverrideVisCheckBoxCustom("Shoot Backtrack", "tBTrigBacktrack")
    val trigFov = OverrideVisSliderCustom("FOV", "tBTrigFov", 0.5F, 90F, 0.5F, false, labelWidth = 225F, barWidth = 225F)
    val trigInitDelay = OverrideVisSliderCustom("Init Shot Delay", "tBTrigInitDelay", 0F, 500F, 10F, true, labelWidth = 225F, barWidth = 225F)
    val trigPerShotDelay = OverrideVisSliderCustom("Per Shot Delay", "tBTrigPerShotDelay", 0F, 500F, 10F, true, labelWidth = 225F, barWidth = 225F)

    val enableBacktrack = OverrideVisCheckBoxCustom("Enable Backtrack", "tBacktrack")
    val backtrackMS = OverrideVisSliderCustom("Backtrack MS", "tBTMS", 20F, 200F, 5F, true, labelWidth = 225F, barWidth = 225F)

    val autoWepCheckbox = OverrideVisCheckBoxCustom("Automatic Weapons", "tAutowep")
    val autoWepDelay = OverrideVisSliderCustom("Delay", "tAutowepDelay", 0F, 1000F, 10F, true, labelWidth = 225F, barWidth = 225F)

    init {
        table.padLeft(25F)
        table.padRight(25F)

        categorySelectionBox.changed { _, _ ->
            categorySelected = gunCategories[categorySelectionBox.selectedIndex]

            val tmpCategory = when (categorySelected) {
                "PISTOL" -> {
                    weaponOverrideSelectionBox.clearItems(); pistolCategory
                }
                "SMG" -> {
                    weaponOverrideSelectionBox.clearItems(); smgCategory
                }
                "RIFLE" -> {
                    weaponOverrideSelectionBox.clearItems(); rifleCategory
                }
                "SNIPER" -> {
                    weaponOverrideSelectionBox.clearItems(); sniperCategory
                }
                else -> {
                    weaponOverrideSelectionBox.clearItems(); shotgunCategory
                }
            }

            val itemsArray = Array<String>()
            for (i in tmpCategory) {
                if (curLocale[i].isBlank()) {
                    if (dbg) println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
                    itemsArray.add(i)
                } else {
                    itemsArray.add(curLocale[i])
                }
            }
            weaponOverrideSelectionBox.items = itemsArray

            if (categorySelected == "SNIPER") {
                enableScopedOnly.disable(false)
            } else {
                enableScopedOnly.disable(true)
            }

            if (categorySelected == "RIFLE" || categorySelected == "SMG") {
                aimAfterShots.disable(false, Color(225F, 225F, 225F, 1F))
            } else {
                aimAfterShots.disable(true, Color(105F, 105F, 105F, .2F))
            }

            weaponOverrideSelectionBox.selected = weaponOverrideSelectionBox.items[0]
            uiUpdate()
            true
        }

        //Create Override Weapon Selector
        val weaponOverrideSelection = VisTable()
        weaponOverrideSelection.add(weaponSelectLabel).padRight(225F - weaponSelectLabel.width)
        weaponOverrideSelection.add(weaponOverrideSelectionBox).width(225F)

        weaponOverrideSelectionBox.setItems(*pistolCategory)
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
        categorySelection.add(categorySelectLabel).padRight(225F - categorySelectLabel.width)
        categorySelection.add(categorySelectionBox).width(225F)

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
            if (curLocale[i].isBlank()) {
                if (dbg) println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
                boneArray.add(i)
            } else {
                boneArray.add(curLocale[i])
            }
        }
        aimBoneBox.items = boneArray
        aimBoneBox.selectedIndex = boneCategories.indexOf(curSettings[categorySelected + "_AIM_BONE"].toUpperCase())
        aimBone.add(aimBoneLabel).width(225F)
        aimBone.add(aimBoneBox).width(225F)

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

        forceBoneBox.selectedIndex = boneCategories.indexOf(curSettings[categorySelected + "_AIM_BONE"].toUpperCase())
        forceBone.add(forceBoneLabel).width(225F)
        forceBone.add(forceBoneBox).width(225F)

        forceBoneBox.changed { _, _ ->
            val setBone = curSettings[boneCategories[forceBoneBox.selectedIndex] + "_BONE"]
            if (weaponOverride) {
                val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
                curWep.tForceBone = setBone.toInt()
                curSettings[weaponOverrideSelected] = curWep.toString()
            }
        }
        //End Force Bone Selector Box


        perfectAimCollapsible.setCollapsed(!curSettings[categorySelected + "_PERFECT_AIM"].strToBool(), true)

        perfectAimTable.add(perfectAimFov).padLeft(20F).left().row()
        perfectAimTable.add(perfectAimChance).padLeft(20F).left().row()

        perfectAimCheckBox.changed { _, _ ->
            perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
        }

        weaponOverrideCheckBox.isChecked = curSettings["ENABLE_OVERRIDE"].strToBool()

        weaponOverride = weaponOverrideCheckBox.isChecked

        weaponOverrideCheckBox.changed { _, _ ->
            weaponOverride = weaponOverrideCheckBox.isChecked
            curSettings["ENABLE_OVERRIDE"] = weaponOverrideCheckBox.isChecked.toString()

            val curWep = curSettings[weaponOverrideSelected].toWeaponClass()
            enableOverride = curWep.tOverride

            uiUpdate()
            true
        }
        //End Perfect Aim Collapsible Check Box

        //Add all items to label for tabbed pane content
        table.add(weaponOverrideCheckBox).left().row()
        table.add(categorySelection).left().row()
        table.add(weaponOverrideSelection).left().row()
        table.add(weaponOverrideEnableCheckBox).left().row()
        table.add(enableFactorRecoil).left().row()
        table.add(enableOnShot).left().row()
        table.add(enableFlatAim).left().row()
        table.add(enablePathAim).left().row()
        table.add(enableScopedOnly).left().row()
        table.add(aimBone).left().row()
        table.add(forceBone).left().row()
        table.add(aimSpeed).left().row()
        table.add(aimFov).left().row()
        table.add(aimSmoothness).left().row()
        table.add(aimAfterShots).left().row()
        table.add(perfectAimCheckBox).left().row()
        table.add(perfectAimCollapsible).left().row()

        table.addSeparator()
        table.add(autoWepCheckbox).left().row()
        table.add(autoWepDelay).left().row()

        table.addSeparator()

        table.add(trigEnable).left().row()
        table.add(trigAimbot).left().row()
        table.add(trigInCross).left().row()
        table.add(trigInFov).left().row()
        table.add(trigBacktrack).left().row()
        table.add(trigFov).left().row()
        table.add(trigInitDelay).left().row()
        table.add(trigPerShotDelay).left().row()

        table.addSeparator()

        table.add(enableBacktrack).left().row()
        table.add(backtrackMS).left().row()
    }

    override fun getTabTitle(): String {
        return "Override".toLocale()
    }

    override fun getContentTable(): Table {
        return table
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
            aimAfterShots.disable(false, Color(225F, 225F, 225F, 1F))
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
            PELVIS_BONE -> "PELVIS"
            else -> "RANDOM"
        })

        forceBoneBox.selectedIndex = boneCategories.indexOf(when (curWep.tForceBone) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            NEAREST_BONE -> "NEAREST"
            PELVIS_BONE -> "PELVIS"
            else -> "RANDOM"
        })

        aimFov.update()
        aimSpeed.update()

        aimSmoothness.update()
        aimAfterShots.update()
         // issue is not here

        autoWepDelay.update()
        autoWepCheckbox.update()

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