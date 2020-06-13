package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.ui.aimTab
import rat.poison.ui.uiHelpers.tables.AimBTrigTable
import rat.poison.ui.uiHelpers.tables.AimTable

//I really couldn't give a shit to update this to the same as the other tabs

var categorySelected = "PISTOL"

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(true)
    val tAim = AimTable()
    val tTrig = AimBTrigTable()

    init {
        table.add(tAim).row()
        table.add(tTrig)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Aim"
    }
}

fun updateDisableAim() {
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
        ingameSens.disable(bool, col)
        weaponOverrideCheckBox.isDisabled = bool
        activateFromFireKey.disable(bool)
        teammatesAreEnemies.disable(bool)
        automaticWeaponsCheckBox.disable(bool)
        automaticWeaponsInput.disable(bool, col)
        targetSwapDelay.disable(bool, col)
        forceAimKey.disable(bool, col)
        forceAimAlways.disable(bool)
        forceAimThroughWalls.disable(bool)
        categorySelectLabel.color = col
        categorySelectionBox.isDisabled = bool
        enableFactorRecoil.isDisabled = bool
        enableFlatAim.isDisabled = bool
        enablePathAim.isDisabled = bool
        enableScopedOnly.isDisabled = bool
        aimBoneLabel.color = col
        aimBoneBox.isDisabled = bool
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
        enableAim.update()
        aimToggleKey.update()
        ingameSens.update()
        activateFromFireKey.update()
        teammatesAreEnemies.update()
        forceAimKey.update()
        forceAimAlways.update()
        forceAimThroughWalls.update()
        automaticWeaponsCheckBox.update()
        automaticWeaponsInput.update()
        targetSwapDelay.update()

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

        aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"].toInt()) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            NEAREST_BONE -> "NEAREST"
            else -> "RANDOM"
        }
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
    aimTab.tTrig.apply {
        val bool = if (!aimTab.tAim.enableAim.isChecked) {
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
        trigDelay.disable(bool, col)
    }
}

fun updateTrig() {
    aimTab.tTrig.apply {
        enableTrig.update()
        enableTrig.update()
        boneTriggerEnableKey.update()
        boneTriggerKey.update()
        trigInCross.update()
        trigInFov.update()
        trigFov.update()
        trigAimbot.update()
        trigDelay.update()
    }

    updateDisableTrig()
}