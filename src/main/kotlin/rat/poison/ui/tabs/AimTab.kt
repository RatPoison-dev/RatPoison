package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curLocale
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.overlay.opened
import rat.poison.toLocale
import rat.poison.ui.uiHelpers.tables.AimBacktrackTable
import rat.poison.ui.uiHelpers.tables.AimTable
import rat.poison.ui.uiHelpers.tables.AimTriggerTable
import rat.poison.ui.uiPanels.aimTab
import rat.poison.utils.generalUtil.strToBool


//val itemsArray = Array<String>()
//for (i in boxItems) {
//    itemsArray.add(i)
//}

var categorySelected = "PISTOL"
val gunCategories = arrayOf("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
var boneCategories = arrayOf("HEAD", "NECK", "CHEST", "STOMACH", "NEAREST", "RANDOM")
var pistolCategory = arrayOf("DESERT_EAGLE", "DUAL_BERETTA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250")
var smgCategory = arrayOf("MAC10", "P90", "MP5", "UMP45", "MP7", "MP9", "PP_BIZON")
var rifleCategory = arrayOf("AK47", "AUG", "FAMAS", "SG553", "GALIL", "M4A4", "M4A1_SILENCER", "NEGEV", "M249")
var sniperCategory = arrayOf("AWP", "G3SG1", "SCAR20", "SSG08")
var shotgunCategory = arrayOf("XM1014", "MAG7", "SAWED_OFF", "NOVA")

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(false)
    val tAim = AimTable()
    val tTrig = AimTriggerTable()
    val tBacktrack = AimBacktrackTable()

    init {
        table.add(tAim).growX().row()
        table.add(tTrig).growX().row()
        table.add(tBacktrack).growX().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Aim".toLocale()
    }
}

fun updateDisableAim() {
    if (!opened) return

    aimTab.tAim.apply {
        val bool = !enableAim.isChecked
        var col = Color(255F, 255F, 255F, 1F)
        if (bool) {
            col = Color(105F, 105F, 105F, .2F)
        }
        if (bool) {
            weaponOverrideCheckBox.isChecked = !bool
        } else {
            weaponOverrideCheckBox.isChecked = curSettings["ENABLE_OVERRIDE"].strToBool()
        }
        weaponOverrideCheckBox.isDisabled = bool
        activateFromFireKey.disable(bool)
        teammatesAreEnemies.disable(bool)
        holdAim.disable(bool)
        automaticWeaponsCheckBox.disable(bool)
        automaticWeaponsInput.disable(bool, col)
        targetSwapDelay.disable(bool, col)

        fovType.update()

        forceAimBoneKey.disable(bool, col)
        forceAimKey.disable(bool, col)
        forceAimAlways.disable(bool)
        forceAimThroughWalls.disable(bool)
        categorySelectLabel.color = col
        categorySelectionBox.isDisabled = bool
        enableAimOnShot.isDisabled = bool
        enableFactorRecoil.isDisabled = bool
        enableFlatAim.isDisabled = bool
        enablePathAim.isDisabled = bool
        enableScopedOnly.isDisabled = bool

        aimBone.disable(bool, col)
        forceAimBone.disable(bool, col)

        aimFov.disable(bool, col)
        aimSpeed.disable(bool, col)
        aimSmooth.disable(bool, col)
        if (!aimAfterShots.isDisabled()) {
            aimAfterShots.disable(bool, col)
        }
        perfectAimCollapsible.isCollapsed = !perfectAimCheckBox.isChecked
        perfectAimCheckBox.isDisabled = bool
        perfectAimChance.disable(bool, col)
        perfectAimFov.disable(bool, col)

        randomizeX.disable(bool, col)
        randomizeY.disable(bool, col)
        randomizeDZ.disable(bool, col)
        advancedSettingsCollapsible.isCollapsed = !advancedSettingsCheckBox.isChecked
        advancedSettingsCheckBox.isDisabled = bool
        advancedRcsX.disable(bool, col)
        advancedRcsY.disable(bool, col)
        advancedRcsVariation.disable(bool, col)
        advancedSpeedDivisor.disable(bool, col)
    }
}

fun updateAim() {
    aimTab.tAim.apply {
        categorySelectLabel.setText("${"Weapon-Category".toLocale()}:") //Do not like this
        //Create Category Selector Box
        val itemsArray = Array<String>()
        for (i in gunCategories) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
            }

            itemsArray.add(curLocale[i])
        }

        categorySelectionBox.items = itemsArray
        //categorySelectionBox.selectedIndex = 1

        enableAim.update()
        aimToggleKey.update()
        activateFromFireKey.update()
        teammatesAreEnemies.update()
        holdAim.update()
        forceAimBoneKey.update()
        forceAimKey.update()
        forceAimAlways.update()
        forceAimThroughWalls.update()
        automaticWeaponsCheckBox.update()
        automaticWeaponsInput.update()
        targetSwapDelay.update()

        fovType.update()
        //fovTypeBox.selected = curSettings["FOV_TYPE"].replace("\"", "")

        enableAimOnShot.update()
        enableFactorRecoil.update()
        enableFlatAim.update()
        enablePathAim.update()

        enableScopedOnly.update()
        if (categorySelected == "SNIPER") {
            enableScopedOnly.color = Color(255F, 255F, 255F, 1F)
            enableScopedOnly.isDisabled = false
        } else {
            enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
            enableScopedOnly.isDisabled = true
        }

        aimAfterShots.update()
        if (categorySelected == "RIFLE" || categorySelected == "SMG") {
            aimAfterShots.disable(false, Color(255F, 255F, 255F, 1F))
        } else {
            aimAfterShots.disable(true, Color(0F, 0F, 0F, 0F))
        }

        aimBone.update()
        forceAimBone.update()

        aimFov.update()
        aimSpeed.update()
        aimSmooth.update()
        aimAfterShots.update()
        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimCollapsible.isCollapsed = !curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimFov.update()
        perfectAimChance.update()

        randomizeX.update()
        randomizeY.update()
        randomizeDZ.update()
        advancedSettingsCheckBox.isChecked = curSettings[categorySelected + "_ADVANCED_SETTINGS"].strToBool()
        advancedSettingsCollapsible.isCollapsed = !curSettings[categorySelected + "_ADVANCED_SETTINGS"].strToBool()
        advancedRcsX.update()
        advancedRcsY.update()
        advancedRcsVariation.update()
        advancedSpeedDivisor.update()

        updateDisableAim()
    }
}

fun updateDisableTrig() {
    if (!opened) return

    aimTab.tTrig.apply {
        var bool = if (!aimTab.tAim.enableAim.isChecked) {
            //enableTrig.disable(true)
            //true
            enableTrig.disable(false)
            !enableTrig.isChecked
        } else {
            enableTrig.disable(false)
            !enableTrig.isChecked
        }
        var col = Color(255F, 255F, 255F, 1F)
        if (bool) {
            col = Color(105F, 105F, 105F, .2F)
        }
        boneTriggerEnableKey.disable(bool)
        boneTriggerKey.disable(bool, col)
        categorySelectLabel.color = col
        categorySelectionBox.isDisabled = bool
        trigEnable.disable(bool)

        if (!trigEnable.isChecked) {
            bool = true
            col = Color(105F, 105F, 105F, .2F)
        }

        trigShootBacktrack.disable(if (!bool) { !aimTab.tBacktrack.enableBacktrack.isChecked } else { true })

        if (!aimTab.tAim.enableAim.isChecked) {
            trigAimbot.disable(true)
        } else {
            trigAimbot.disable(bool)
        }
        trigInCross.disable(bool)
        trigInFov.disable(bool)
        if (!trigInFov.isChecked) {
            trigFov.disable(true, Color(105F, 105F, 105F, .2F))
        } else {
            trigFov.disable(bool, col)
        }
        trigShootBacktrack.disable(bool)
        initTrigDelay.disable(bool, col)
        perShotTrigDelay.disable(bool, col)
    }
}

fun updateTrig() {
    aimTab.tTrig.apply {
        categorySelectLabel.setText("${"Weapon-Category".toLocale()}:")
        //Create Category Selector Box
        val itemsArray = Array<String>()
        for (i in gunCategories) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
            }

            itemsArray.add(curLocale[i])
        }

        categorySelectionBox.items = itemsArray
        //categorySelectionBox.selectedIndex = 1

        enableTrig.update()
        enableTrig.update()
        boneTriggerEnableKey.update()
        boneTriggerKey.update()
        trigEnable.update()
        trigInCross.update()
        trigInFov.update()
        trigFov.update()
        trigShootBacktrack.update()
        trigAimbot.update()
        initTrigDelay.update()
        perShotTrigDelay.update()
    }

    updateDisableTrig()
}

//Backtrack
fun updateDisableBacktrack() {
    if (!opened) return

    aimTab.tBacktrack.apply {
        val bool = true

        var col = Color(255F, 255F, 255F, 1F)
        if (bool) {
            col = Color(105F, 105F, 105F, .2F)
        }

        enableBacktrack.disable(bool)
        backtrackVisualize.disable(bool)
        backtrackEnableKey.disable(bool)
        backtrackKey.disable(bool, col)
        backtrackMS.disable(bool, col)
        backtrackSpotted.disable(bool)
        categorySelectLabel.color = col
        categorySelectionBox.isDisabled = bool
        backtrackWeaponEnabled.disable(bool)
    }
}

fun updateBacktrack() {
    if (!opened) return

    aimTab.tBacktrack.apply {
        categorySelectLabel.setText("${"Weapon-Category".toLocale()}:")
        //Create Category Selector Box
        val itemsArray = Array<String>()
        for (i in gunCategories) {
            if (dbg && curLocale[i].isBlank()) {
                println("[DEBUG] ${curSettings["CURRENT_LOCALE"]} $i is missing!")
            }

            itemsArray.add(curLocale[i])
        }

        categorySelectionBox.items = itemsArray

        enableBacktrack.update()
        backtrackVisualize.update()
        backtrackEnableKey.update()
        backtrackKey.update()
        backtrackMS.update()
        backtrackSpotted.update()
        backtrackWeaponEnabled.update()
    }

    updateDisableBacktrack()
}