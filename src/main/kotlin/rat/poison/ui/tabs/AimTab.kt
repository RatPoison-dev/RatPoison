package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.*
import rat.poison.App.menuStage
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.settings.*
import rat.poison.ui.*
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import kotlin.math.round

//I really couldn't give a shit to update this to the same as the other tabs

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(true)

    var categorySelected = "PISTOL"

    //Init labels/sliders/boxes that show values here
    val enableAim = VisCheckBoxCustom("Enable Aim", "ENABLE_AIM")
    val activateFromFireKey = VisCheckBoxCustom("Activate From Fire Key", "ACTIVATE_FROM_AIM_KEY")
    val teammatesAreEnemies = VisCheckBoxCustom("Teammates Are Enemies", "TEAMMATES_ARE_ENEMIES")

    val aimKey = VisInputFieldCustom("Aim Key", "AIM_KEY")
    val forceAimKey = VisInputFieldCustom("Force Aim Key", "FORCE_AIM_KEY")

    //Automatic Weapons Collapsible
    val automaticWeaponsCheckBox = VisCheckBoxCustom("Automatic Weapons", "AUTOMATIC_WEAPONS")
    val automaticWeaponsInput = VisInputFieldCustom("MS Delay", "AUTO_WEP_DELAY", false)

    val categorySelectionBox = VisSelectBox<String>()

    //Override Weapon Checkbox & Selection Box
    val categorySelectLabel = VisLabel("Weapon Category: ")
    val weaponOverrideCheckBox = VisCheckBox("Override Weapons")

    val enableFactorRecoil = VisCheckBox("Factor Recoil")
    val enableFlatAim = VisCheckBox("Flat Aim")
    val enablePathAim = VisCheckBox("Path Aim")
    val enableScopedOnly = VisCheckBox("Scoped Only")

    val aimBoneLabel = VisLabel("Bone: ")
    val aimBoneBox = VisSelectBox<String>()

    val aimFovLabel = VisLabel("Aim Fov: " + curSettings[categorySelected + "_AIM_FOV"].toInt().toString() + when(curSettings[categorySelected + "_AIM_FOV"].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val aimFovSlider = VisSlider(1F, 180F, 1F, false)

    val aimSpeedLabel = VisLabel("Aim Speed: " + curSettings[categorySelected + "_AIM_SPEED"].toInt().toString() + when(curSettings[categorySelected + "_AIM_SPEED"].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val aimSpeedSlider = VisSlider(0F, 5F, 1F, false)

    val aimSmoothnessLabel = VisLabel("Smoothness: " + curSettings[categorySelected + "_AIM_SMOOTHNESS"].toFloat())
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
        if (curSettings["WARNING"].strToBool()) {
            val dialog = Dialogs.showOKDialog(menuStage, "Warning", "Current Version: 1.5" +
                    "\n\nIf you have any problems submit an issue on Github" +
                    "\nGitHub: https://github.com/TheFuckingRat/RatPoison" +
                    "\n\nThe ranks tab is a WIP, comp only" +
                    "\n\nIf you have ideas/suggestions for btrig suggest them in github issues")
            dialog.setPosition(gameWidth / 4F - dialog.width / 2F, gameHeight.toFloat() / 2F)
            menuStage.addActor(dialog)
        }

        //Create Factor Recoil Toggle
        Tooltip.Builder("Whether or not to factor in recoil when aiming").target(enableFactorRecoil).build()
        enableFactorRecoil.isChecked = curSettings[categorySelected + "_ENABLE_PATH_AIM"].strToBool()
        enableFactorRecoil.changed { _, _ ->
            curSettings[categorySelected + "_FACTOR_RECOIL"] = enableFactorRecoil.isChecked.boolToStr()
            uiUpdate()
            true
        }

        //Create Override Weapon Check Box & Collapsible
        Tooltip.Builder("Global enable override").target(weaponOverrideCheckBox).build()
        weaponOverrideCheckBox.isChecked = overridenWeapons.weaponOverride

        weaponOverrideCheckBox.changed { _, _ ->
            overridenWeapons.weaponOverride = weaponOverrideCheckBox.isChecked
            curSettings["ENABLE_OVERRIDE"] = weaponOverrideCheckBox.isChecked.toString()

            val curWep : Array<Double?> = convStrToArray(curSettings[overridenWeapons.weaponOverrideSelected])
            overridenWeapons.enableOverride = curWep[0]!!.strToBool()

            uiUpdate()
            true
        }

        //Create Category Selector Box
        val categorySelection = VisTable()
        Tooltip.Builder("The weapon category settings to edit").target(categorySelection).build()
        categorySelectionBox.setItems("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
        categorySelectionBox.selected = "PISTOL"
        categorySelected = categorySelectionBox.selected
        categorySelection.add(categorySelectLabel).padRight(200F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox)

        categorySelectionBox.changed { _, _ ->
            categorySelected = categorySelectionBox.selected

            if (categorySelected == "SNIPER") {
                enableScopedOnly.color = Color(255F, 255F, 255F, 1F)
                enableScopedOnly.isDisabled = false
            } else {
                enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
                enableScopedOnly.isDisabled = true
            }

            uiUpdate()
            true
        }

        //Create Flat Aim Toggle
        enableFlatAim.isChecked = curSettings[categorySelected + "_ENABLE_FLAT_AIM"].strToBool()
        enableFlatAim.changed { _, _ ->
            curSettings[categorySelected + "_ENABLE_FLAT_AIM"] = enableFlatAim.isChecked.boolToStr()
            if (curSettings[categorySelected + "_ENABLE_FLAT_AIM"].strToBool()) {curSettings[categorySelected + "_ENABLE_PATH_AIM"] = "false"}
            uiUpdate()
            true
        }

        //Create Path Aim Toggle
        enablePathAim.isChecked = curSettings[categorySelected + "_ENABLE_PATH_AIM"].strToBool()
        enablePathAim.changed { _, _ ->
            curSettings[categorySelected + "_ENABLE_PATH_AIM"] = enablePathAim.isChecked.boolToStr()
            if (curSettings[categorySelected + "_ENABLE_PATH_AIM"].strToBool()) {curSettings[categorySelected + "_ENABLE_FLAT_AIM"] = "false"}
            uiUpdate()
            true
        }

        //Create Scoped Only Toggle
        enableScopedOnly.isChecked = curSettings["SNIPER_ENABLE_SCOPED_ONLY"].strToBool()
        enableScopedOnly.color = Color(255F, 255F, 255F, 0F)
        enableScopedOnly.isDisabled = true
        enableScopedOnly.changed { _, _ ->
            curSettings["SNIPER_ENABLE_SCOPED_ONLY"] = enableScopedOnly.isChecked.boolToStr()
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
        aimBone.add(aimBoneLabel).width(200F)
        aimBone.add(aimBoneBox)

        aimBoneBox.changed { _, _ ->
            val setBone = curSettings[aimBoneBox.selected + "_BONE"].toInt()
            curSettings[categorySelected + "_AIM_BONE"] = setBone.toString()
            true
        }

        //Create Aim FOV Slider
        val aimFov = VisTable()
        Tooltip.Builder("The aim field of view").target(aimFov).build()
        aimFovSlider.value = curSettings[categorySelected + "_AIM_FOV"].toInt().toFloat()
        aimFovSlider.changed { _, _ ->
            curSettings[categorySelected + "_AIM_FOV"] = aimFovSlider.value.toInt().toString()

            aimFovLabel.setText("FOV: " + aimFovSlider.value.toInt())
        }
        aimFov.add(aimFovLabel).width(200F)
        aimFov.add(aimFovSlider).width(250F)

        //Create Aim Speed Slider
        val aimSpeed = VisTable()
        Tooltip.Builder("The aim speed delay in milliseconds").target(aimSpeed).build()
        aimSpeedSlider.value = curSettings[categorySelected + "_AIM_SPEED"].toInt().toFloat()
        aimSpeedSlider.changed { _, _ ->
            curSettings[categorySelected + "_AIM_SPEED"] = aimSpeedSlider.value.toInt().toString()
            aimSpeedLabel.setText("Speed: " + aimSpeedSlider.value.toInt())
        }
        aimSpeed.add(aimSpeedLabel).width(200F)
        aimSpeed.add(aimSpeedSlider).width(250F)

        //Create Aim Smoothness Slider
        val aimSmoothness = VisTable()
        Tooltip.Builder("The smoothness of the aimbot (path aim only)").target(aimSmoothness).build()
        aimSmoothnessSlider.value = curSettings[categorySelected + "_AIM_SMOOTHNESS"].toFloat()
        aimSmoothnessSlider.changed { _, _ ->
            curSettings[categorySelected + "_AIM_SMOOTHNESS"] = (round(aimSmoothnessSlider.value.toDouble() * 10.0) / 10.0).toString()
            aimSmoothnessLabel.setText("Smoothness: " + (round(aimSmoothnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimSmoothness.add(aimSmoothnessLabel).width(200F)
        aimSmoothness.add(aimSmoothnessSlider).width(250F)

        //Create Aim Strictness Slider
        val aimStrictness = VisTable()
        Tooltip.Builder("How strict moving to the aimbone is, lower = less deviation").target(aimStrictness).build()
        aimStrictnessSlider.value = curSettings[categorySelected + "_AIM_STRICTNESS"].toFloat()
        aimStrictnessSlider.changed { _, _ ->
            curSettings[categorySelected + "_AIM_STRICTNESS"] = (round(aimStrictnessSlider.value.toDouble() * 10.0) / 10.0).toString()
            aimStrictnessLabel.setText("Strictness: " + (round(aimStrictnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimStrictness.add(aimStrictnessLabel).width(200F)
        aimStrictness.add(aimStrictnessSlider).width(250F)

        //Create Perfect Aim Collapsible Check Box
        Tooltip.Builder("Whether or not to enable perfect aim").target(perfectAimCheckBox).build()
        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"].strToBool()
        perfectAimCollapsible.setCollapsed(!curSettings[categorySelected + "_PERFECT_AIM"].strToBool(), true)

        //Create Perfect Aim Fov Slider
        val perfectAimFov = VisTable()
        Tooltip.Builder("The perfect aim field of view").target(perfectAimFov).build()
        perfectAimFovSlider.value = curSettings[categorySelected + "_PERFECT_AIM_FOV"].toInt().toFloat()
        perfectAimFovSlider.changed { _, _ ->
            curSettings[categorySelected + "_PERFECT_AIM_FOV"] = perfectAimFovSlider.value.toInt().toString()
            perfectAimFovLabel.setText("FOV: " + perfectAimFovSlider.value.toInt())
        }
        perfectAimFov.add(perfectAimFovLabel).width(180F)
        perfectAimFov.add(perfectAimFovSlider).width(250F)
        //End Perfect Aim Fov Slider

        //Create Perfect Aim Chance Slider
        val perfectAimChance = VisTable()
        Tooltip.Builder("The perfect aim chance (per calculation)").target(perfectAimChance).build()
        perfectAimChanceSlider.value = curSettings[categorySelected + "_PERFECT_AIM_CHANCE"].toInt().toFloat()
        perfectAimChanceSlider.changed { _, _ ->
            curSettings[categorySelected + "_PERFECT_AIM_CHANCE"] = perfectAimChanceSlider.value.toInt().toString()
            perfectAimChanceLabel.setText("Chance: " + perfectAimChanceSlider.value.toInt())
        }

        perfectAimChance.add(perfectAimChanceLabel).width(180F)
        perfectAimChance.add(perfectAimChanceSlider).width(250F)
        //End Perfect Aim Chance Slider

        perfectAimTable.add(perfectAimFov).padLeft(20F).left().row()
        perfectAimTable.add(perfectAimChance).padLeft(20F).left().row()

        perfectAimCheckBox.changed { _, _ ->
            curSettings[categorySelected + "_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
            perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
        }
        //End Perfect Aim Collapsible Check Box

        //Default menu size is 500
        //Texts are 200
        //Sliders are 250
        //Leaves 25 for left and right side to center
        table.padLeft(25F)
        table.padRight(25F)

        //Add all items to label for tabbed pane content

        table.add(enableAim).left().row()

        table.add(activateFromFireKey).left().row()
        table.add(teammatesAreEnemies).left().row()

        table.add(aimKey).left().row()
        table.add(forceAimKey).left().row()

        table.add(automaticWeaponsCheckBox).left().row()
        table.add(automaticWeaponsInput).left().row()

        table.addSeparator()

        table.add(weaponOverrideCheckBox).left().row()
        table.add(categorySelection).left().row()
        table.add(enableFactorRecoil).left().row()
        table.add(enableFlatAim).left().row()
        table.add(enablePathAim).left().row()
        table.add(enableScopedOnly).left().row() //SNIPER selection only
        table.add(aimBone).left().row()
        table.add(aimSpeed).left().row()
        table.add(aimFov).left().row()
        table.add(aimSmoothness).left().row()
        table.add(aimStrictness).left().row()
        table.add(perfectAimCheckBox).left().row()
        table.add(perfectAimCollapsible).left().row()

        table.addSeparator()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Aim"
    }
}

fun updateDisableAim() {
    aimTab.apply {
        val bool = !enableAim.isChecked
        var col = Color(255F, 255F, 255F, 1F)
        if (bool) {
            col = Color(105F, 105F, 105F, .2F)
        }
        activateFromFireKey.disable(bool)
        teammatesAreEnemies.disable(bool)
        automaticWeaponsCheckBox.disable(bool)
        automaticWeaponsInput.disable(bool, col)
        aimKey.disable(bool, col)
        forceAimKey.disable(bool, col)
        categorySelectLabel.color = col
        categorySelectionBox.isDisabled = bool
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