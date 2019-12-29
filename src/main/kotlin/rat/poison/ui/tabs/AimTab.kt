package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.*
import rat.poison.settings.CHEST_BONE
import rat.poison.settings.HEAD_BONE
import rat.poison.settings.NECK_BONE
import rat.poison.settings.STOMACH_BONE
import rat.poison.ui.*
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
        weaponOverrideCheckBox.isDisabled = bool
        activateFromFireKey.disable(bool)
        teammatesAreEnemies.disable(bool)
        automaticWeaponsCheckBox.disable(bool)
        automaticWeaponsInput.disable(bool, col)
        targetSwapDelay.disable(bool, col)
        forceAimKey.disable(bool, col)
        forceAimAlways.disable(bool)
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
        aimStrict.disable(bool, col)
        perfectAimCollapsible.isCollapsed = !perfectAimCheckBox.isChecked
        perfectAimCheckBox.isDisabled = bool
        perfectAimChance.disable(bool, col)
        perfectAimFov.disable(bool, col)
    }
}

fun updateAim() {
    aimTab.tAim.apply {
        enableAim.update()
        aimToggleKey.update()
        activateFromFireKey.update()
        teammatesAreEnemies.update()
        forceAimKey.update()
        forceAimAlways.update()
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
        aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"].toInt()) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            else -> "NEAREST"
        }
        aimFov.update()
        aimSpeed.update()
        aimSmooth.update()
        aimStrict.update()
        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimCollapsible.isCollapsed = !curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimFov.update()
        perfectAimChance.update()

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