package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Array
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSelectBox
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.overlay.opened
import rat.poison.ui.changed
import rat.poison.ui.tabs.aimtabs.*
import rat.poison.ui.uiPanels.aimTab
import rat.poison.ui.uiUpdate

var categorySelected = "PISTOL"
val gunCategories = arrayOf("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
var boneCategories = arrayOf("HEAD", "NECK", "CHEST", "STOMACH", "PELVIS", "NEAREST", "RANDOM")
var pistolCategory = arrayOf("DESERT_EAGLE", "DUAL_BERETTA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250")
var smgCategory = arrayOf("MAC10", "P90", "MP5", "UMP45", "MP7", "MP9", "PP_BIZON")
var rifleCategory = arrayOf("AK47", "AUG", "FAMAS", "SG553", "GALIL", "M4A4", "M4A1_SILENCER", "NEGEV", "M249")
var sniperCategory = arrayOf("AWP", "G3SG1", "SCAR20", "SSG08")
var shotgunCategory = arrayOf("XM1014", "MAG7", "SAWED_OFF", "NOVA")

var overridenWeapons = OverrideTable()
var mainAimTab = MainAimTable()
var aimbotTab = AimbotTable()
var triggerTab = TriggerBotTable()
var backtrackTab = BacktrackTable()

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(false)
    val tMain = mainAimTab
    val tAimbot = aimbotTab
    val tTrig = triggerTab
    val tBacktrack = backtrackTab

    //Override Weapon Checkbox & Selection Box
    private val categorySelection = VisTable(false)
    val categorySelectionBox = VisSelectBox<String>()
    val categorySelectLabel = VisLabel("${"Weapon-Category"}:")

    init {
        //Create Category Selector Box
        val itemsArray = Array<String>()
        for (i in gunCategories) {
            itemsArray.add(i)
        }
        categorySelectionBox.items = itemsArray

        categorySelectionBox.selectedIndex = 0
        categorySelected = gunCategories[categorySelectionBox.selectedIndex]
        categorySelection.add(categorySelectLabel).left().padRight(225F - categorySelectLabel.width)
        categorySelection.add(categorySelectionBox).left()

        categorySelectionBox.changed { _, _ ->
            categorySelected = gunCategories[categorySelectionBox.selectedIndex]

            if (categorySelected == "SNIPER") {
                aimbotTab.enableScopedOnly.color = Color(255F, 255F, 255F, 1F)
                aimbotTab.enableScopedOnly.isDisabled = false
            } else {
                aimbotTab.enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
                aimbotTab.enableScopedOnly.isDisabled = true
            }

            if (categorySelected == "RIFLE" || categorySelected == "SMG") {
                aimbotTab.aimAfterShots.disable(false, Color(255F, 255F, 255F, 1F))
            } else {
                aimbotTab.aimAfterShots.disable(true, Color(255F, 255F, 255F, 0F))
            }

            uiUpdate()
            true
        }

        val aimTabTable2 = VisTable(false)

        aimTabTable2.add(categorySelection).padBottom(4F).row()
        aimTabTable2.addSeparator()
        aimTabTable2.add(aimbotTab).top().padTop(2F).padLeft(6F).row()
        aimTabTable2.addSeparator()
        aimTabTable2.add(triggerTab).top().padTop(2F).padLeft(6F).row()
        aimTabTable2.addSeparator()
        aimTabTable2.add(backtrackTab).top().padTop(2F).padLeft(6F)

        table.add(mainAimTab).width(470F).left().top()
        table.addSeparator(true)
        table.add(aimTabTable2).width(470F).left().top()
    }

    override fun getContentTable(): Table {
        return table
    }

    override fun getTabTitle(): String {
        return "Aim"
    }
}

fun updateDisableAim() {
    if (!opened) return

    val bool = !aimTab.tMain.enableAim.isChecked
    var col = Color(255F, 255F, 255F, 1F)
    if (bool) {
        col = Color(105F, 105F, 105F, .2F)
    }

    if (!aimTab.tMain.enableAim.isChecked && !aimTab.tMain.enableBacktrack.isChecked && !aimTab.tMain.enableTrig.isChecked) {
        aimTab.categorySelectLabel.color = Color(105F, 105F, 105F, .2F)
        aimTab.categorySelectionBox.isDisabled = true
    } else {
        aimTab.categorySelectLabel.color = Color(255F, 255F, 255F, 1F)
        aimTab.categorySelectionBox.isDisabled = false
    }

    aimTab.tMain.apply {
        activateFromFireKey.disable(bool)
        teammatesAreEnemies.disable(bool)
        holdAim.disable(bool)
        automaticWeaponsCheckBox.disable(bool)
        automaticWeaponsInput.disable(bool, col)
        targetSwapDelay.disable(bool, col)

        fovType.disable(bool, col)

        forceAimBoneKey.disable(bool, col)
        forceAimKey.disable(bool, col)
        forceAimAlways.disable(bool)
        forceAimThroughWalls.disable(bool)
    }

    aimTab.tAimbot.apply {
        enableAimOnShot.isDisabled = bool
        enableFactorRecoil.isDisabled = bool
        enableFlatAim.isDisabled = bool
        enablePathAim.isDisabled = bool
        enableScopedOnly.isDisabled = bool

        aimBones.disable(bool, col)
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
    overridenWeapons.weaponOverrideCheckBox.update()
    aimTab.tMain.apply {
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

    }

    aimTab.categorySelectLabel.setText("Weapon Category:")
    //Create Category Selector Box
    val itemsArray = Array<String>()
    for (i in gunCategories) {
        itemsArray.add(i)
    }

    aimTab.categorySelectionBox.items = itemsArray

    aimTab.tAimbot.apply {
        collapsibleWidget.isCollapsed = !aimTab.tMain.enableAim.isChecked

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

        aimBones.update()
        forceAimBone.update()

        aimFov.update()
        aimSpeed.update()
        aimSmooth.update()
        aimAfterShots.update()
        perfectAimCheckBox.isChecked = curSettings.bool[categorySelected + "_PERFECT_AIM"]
        perfectAimCollapsible.isCollapsed = !curSettings.bool[categorySelected + "_PERFECT_AIM"]
        perfectAimFov.update()
        perfectAimChance.update()

        randomizeX.update()
        randomizeY.update()
        randomizeDZ.update()
        advancedSettingsCheckBox.isChecked = curSettings.bool[categorySelected + "_ADVANCED_SETTINGS"]
        advancedSettingsCollapsible.isCollapsed = !curSettings.bool[categorySelected + "_ADVANCED_SETTINGS"]
        advancedRcsX.update()
        advancedRcsY.update()
        advancedRcsVariation.update()
        advancedSpeedDivisor.update()

        updateDisableAim()
    }
}

fun updateDisableTrig() {
    if (!opened) return

    var bool = !aimTab.tMain.enableTrig.isChecked

    aimTab.tTrig.apply {
        var col = Color(255F, 255F, 255F, 1F)
        if (bool) {
            col = Color(105F, 105F, 105F, .2F)
        }

        boneTriggerEnableKey.disable(bool)
        boneTriggerKey.disable(bool, col)
        trigEnable.disable(bool)

        if (!trigEnable.isChecked) {
            bool = true
            col = Color(105F, 105F, 105F, .2F)
        }

        trigShootBacktrack.disable(if (!bool) { !aimTab.tMain.enableBacktrack.isChecked } else { true })

        if (!aimTab.tMain.enableAim.isChecked) {
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
    aimTab.tMain.enableTrig.update()

    aimTab.tTrig.apply {
        collapsibleWidget.isCollapsed = !aimTab.tMain.enableTrig.isChecked

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

    val bool = !aimTab.tMain.enableBacktrack.isChecked

    aimTab.tBacktrack.apply {
        var col = Color(255F, 255F, 255F, 1F)
        if (bool) {
            col = Color(105F, 105F, 105F, .2F)
        }

        backtrackEnableKey.disable(bool)
        backtrackKey.disable(bool, col)
        backtrackMS.disable(bool, col)
        backtrackSpotted.disable(bool)
        backtrackWeaponEnabled.disable(bool)
    }
}

fun updateBacktrack() {
    if (!opened) return

    aimTab.tMain.enableBacktrack.update()

    aimTab.tBacktrack.apply {
        collapsibleWidget.isCollapsed = !aimTab.tMain.enableBacktrack.isChecked

        backtrackEnableKey.update()
        backtrackKey.update()
        backtrackMS.update()
        backtrackSpotted.update()
        backtrackWeaponEnabled.update()
    }

    updateDisableBacktrack()
}