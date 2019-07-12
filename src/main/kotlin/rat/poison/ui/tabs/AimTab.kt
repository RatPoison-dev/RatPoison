package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.*
import rat.poison.App.menuStage
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.settings.*
import rat.poison.ui.*
import kotlin.math.round

//Need to change path/aim to a drop down

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(true)

    var categorySelected = "PISTOL"
    var weaponOverrideSelected = ""
    var weaponOverride = false
    private var enableOverride = false

    //Init labels/sliders/boxes that show values here
    val enableAim = VisCheckBox("Enable Aim")
    val activateFromFireKey = VisCheckBox("Activate From Fire Key")
    val teammatesAreEnemies = VisCheckBox("Teammates Are Enemies")

    val aimKeyLabel = VisLabel("Aim Key: ")
    val aimKeyField = VisValidatableTextField(Validators.FLOATS)

    val forceAimKeyLabel = VisLabel("Force Aim Key: ")
    val forceAimKeyField = VisValidatableTextField(Validators.FLOATS)

    //Automatic Weapons Collapsible
    val automaticWeaponsCheckBox = VisCheckBox("Automatic Weapons")
    val automaticWeaponsLabel = VisLabel("MS Delay: ")
    val automaticWeaponsField = VisValidatableTextField(Validators.INTEGERS)

    val categorySelectionBox = VisSelectBox<String>()

    //Override Weapon Checkbox & Selection Box
    val categorySelectLabel = VisLabel("Weapon Category: ")
    val weaponOverrideCheckBox = VisCheckBox("Weapon Override")

    val weaponOverrideSelectionBox = VisSelectBox<String>()
    val weaponOverrideEnableCheckBox = VisCheckBox("Enable Override")

    val enableFactorRecoil = VisCheckBox("Factor Recoil")
    val enableFlatAim = VisCheckBox("Flat Aim")
    val enablePathAim = VisCheckBox("Path Aim")

    val aimBoneLabel = VisLabel("Bone: ")
    val aimBoneBox = VisSelectBox<String>()

    val aimFovLabel = VisLabel("Aim Fov: " + curSettings[categorySelected + "_AIM_FOV"]!!.toInt().toString() + when(curSettings[categorySelected + "_AIM_FOV"]!!.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val aimFovSlider = VisSlider(1F, 180F, 1F, false)

    val aimSpeedLabel = VisLabel("Aim Speed: " + curSettings[categorySelected + "_AIM_SPEED"]!!.toInt().toString() + when(curSettings[categorySelected + "_AIM_SPEED"]!!.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val aimSpeedSlider = VisSlider(0F, 5F, 1F, false)

    val aimSmoothnessLabel = VisLabel("Smoothness: " + curSettings[categorySelected + "_AIM_SMOOTHNESS"]!!.toFloat())
    val aimSmoothnessSlider = VisSlider(1F, 5F, 0.1F, false)

    val aimStrictnessLabel = VisLabel("Strictness: " + curSettings[categorySelected + "_AIM_STRICTNESS"])
    val aimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim")
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFovLabel = VisLabel("FOV: " + curSettings[categorySelected + "_PERFECT_AIM_FOV"]!!.toInt().toString() + when(curSettings[categorySelected + "_PERFECT_AIM_FOV"]!!.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val perfectAimFovSlider = VisSlider(1F, 45F, 1F, false)
    val perfectAimChanceLabel = VisLabel("Chance: " + curSettings[categorySelected + "_PERFECT_AIM_CHANCE"]!!.toInt().toString() + when(curSettings[categorySelected + "_PERFECT_AIM_CHANCE"]!!.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val perfectAimChanceSlider = VisSlider(1F, 100F, 1F, false)

    init {
        if (curSettings["WARNING"]!!.strToBool()) {
            val dialog = Dialogs.showOKDialog(menuStage, "Warning", "Current Version: 1.3.4\n\nIf you have any problems submit an issue on Github\nGitHub: https://github.com/TheFuckingRat/RatPoison")
            dialog.setPosition(gameWidth / 4F - dialog.width / 2F, gameHeight.toFloat() / 2F)
            menuStage.addActor(dialog)
        }

        //Create Enable Aim Toggle
        Tooltip.Builder("Whether or not to completely enable or disable aims").target(enableAim).build()
        if (curSettings["ENABLE_AIM"]!!.strToBool()) enableAim.toggle()
        enableAim.changed { _, _ ->
            curSettings["ENABLE_AIM"] = enableAim.isChecked.boolToStr()

            val bool = !enableAim.isChecked
            var col = Color(255F, 255F, 255F, 1F)
            if (bool) {
                col = Color(105F, 105F, 105F, .2F)
            }
            activateFromFireKey.isDisabled = bool
            teammatesAreEnemies.isDisabled = bool
            automaticWeaponsCheckBox.isDisabled = bool
            weaponOverrideCheckBox.isDisabled = bool
            automaticWeaponsCheckBox.isDisabled = bool
            automaticWeaponsLabel.color = col
            automaticWeaponsField.isDisabled = bool
            aimKeyLabel.color = col
            aimKeyField.isDisabled = bool
            forceAimKeyLabel.color = col
            forceAimKeyField.isDisabled = bool
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
            perfectAimCheckBox.isDisabled = bool
            perfectAimChanceLabel.color = col
            perfectAimChanceSlider.isDisabled = bool
            perfectAimFovLabel.color = col
            perfectAimFovSlider.isDisabled = bool
            bTrigTab.aimOnBoneTrigger.isDisabled = bool
            true
        }

        //Create Activate From Fire Key Toggle
        Tooltip.Builder("Activate aim if pressing predefined aim key").target(activateFromFireKey).build()
        if (curSettings["ACTIVATE_FROM_AIM_KEY"]!!.strToBool()) activateFromFireKey.toggle()
        activateFromFireKey.changed { _, _ ->
            curSettings["ACTIVATE_FROM_AIM_KEY"] = activateFromFireKey.isChecked.boolToStr()
            true
        }

        //Create Teammates Are Enemies Toggle
        Tooltip.Builder("Teammates will be treated as enemies").target(teammatesAreEnemies).build()
        if (curSettings["TEAMMATES_ARE_ENEMIES"]!!.strToBool()) teammatesAreEnemies.toggle()
        teammatesAreEnemies.changed { _, _ ->
            curSettings["TEAMMATES_ARE_ENEMIES"] = teammatesAreEnemies.isChecked.boolToStr()
            true
        }

        //Create Automatic Weapons Collapsible Check Box
            Tooltip.Builder("Non-automatic weapons will auto shoot when there is no punch and the aim key is pressed").target(automaticWeaponsCheckBox).build()
            automaticWeaponsCheckBox.isChecked = curSettings["AUTOMATIC_WEAPONS"]!!.strToBool()

            automaticWeaponsCheckBox.changed { _, _ ->
                curSettings["AUTOMATIC_WEAPONS"] = automaticWeaponsCheckBox.isChecked.boolToStr()
                true
            }

            //Create Auto Wep Delay Slider
            val automaticWeapons = VisTable()
            Tooltip.Builder("The ms delay between checking punch to fire a shot using AUTOMATIC WEAPONS, the lower the less accurate but faster firing").target(automaticWeapons).build()

            automaticWeaponsField.text = curSettings["AUTO_WEP_DELAY"]

            automaticWeapons.changed { _, _ ->
                if (automaticWeaponsField.text.toIntOrNull() != null) {
                    curSettings["AUTO_WEP_DELAY"] = automaticWeaponsField.text.toInt().toString()
                }
            }
            automaticWeapons.add(automaticWeaponsLabel).width(180F)
            automaticWeapons.add(automaticWeaponsField).spaceRight(6F).width(40F)
        //End Automatic Weapons Collapsible Check Box

        //Create Aim Key Input Box
        val aimKey = VisTable()
        Tooltip.Builder("The key code of your in-game aim key (default m1)").target(aimKey).build()
        aimKeyField.text = curSettings["AIM_KEY"]
        aimKey.changed { _, _ ->
            if (aimKeyField.text.toIntOrNull() != null) {
                curSettings["AIM_KEY"] = aimKeyField.text.toInt().toString()
            }
        }
        aimKey.add(aimKeyLabel).width(200F)
        aimKey.add(aimKeyField).spaceRight(6F).width(40F)
        aimKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Force Aim Key Input Box
        val forceAimKey = VisTable()
        Tooltip.Builder("The key to force lock onto any enemy inside aim fov").target(forceAimKey).build()
        forceAimKeyField.text = curSettings["FORCE_AIM_KEY"]
        forceAimKey.changed { _, _ ->
            if (forceAimKeyField.text.toIntOrNull() != null) {
                curSettings["FORCE_AIM_KEY"] = forceAimKeyField.text.toInt().toString()
            }
        }
        forceAimKey.add(forceAimKeyLabel).width(200F)
        forceAimKey.add(forceAimKeyField).spaceRight(6F).width(40F)
        forceAimKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Factor Recoil Toggle
        Tooltip.Builder("Whether or not to factor in recoil when aiming").target(enableFactorRecoil).build()
        enableFactorRecoil.isChecked = curSettings[categorySelected + "_ENABLE_PATH_AIM"]!!.strToBool()
        enableFactorRecoil.changed { _, _ ->
            if (weaponOverride && enableOverride) {
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
        categorySelection.add(categorySelectLabel).padRight(200F-categorySelectLabel.width)
        categorySelection.add(categorySelectionBox)

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
            weaponOverrideSelectionBox.selected = weaponOverrideSelectionBox.items[0]
            uiUpdate()
            true
        }

        //Create Override Weapon Check Box & Collapsible
        Tooltip.Builder("Whether or not to override a custom weapon's settings").target(weaponOverrideCheckBox).build()
        weaponOverrideCheckBox.isChecked = weaponOverride

        //Create Override Weapon Selector
            val weaponOverrideSelection = VisTable()
            weaponOverrideSelectionBox.setItems("DESERT_EAGLE", "DUAL_BERRETA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250")
            weaponOverrideSelectionBox.selected = "DESERT_EAGLE"
            weaponOverrideSelectionBox.isDisabled = !weaponOverride
            weaponOverrideSelected = weaponOverrideSelectionBox.selected
            weaponOverrideSelection.add(weaponOverrideCheckBox).padRight(200F-weaponOverrideCheckBox.width)
            weaponOverrideSelection.add(weaponOverrideSelectionBox)

            weaponOverrideSelectionBox.changed { _, _ ->
                if (!weaponOverrideSelectionBox.selected.isNullOrEmpty()) {
                    weaponOverrideSelected = weaponOverrideSelectionBox.selected
                }
                uiUpdate()
                true
            }
            //End Override Weapon Selection Box

            weaponOverrideCheckBox.changed { _, _ ->
                weaponOverride = weaponOverrideCheckBox.isChecked
                weaponOverrideSelectionBox.isDisabled = !weaponOverride
                weaponOverrideEnableCheckBox.isDisabled = !weaponOverride

                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                enableOverride = curWep[0]!!.strToBool()

                uiUpdate()
                true
            }
        //End Override Weapon Check Box

        //Create Enable Override Toggle
        Tooltip.Builder("Whether or not to override aim when this gun is selected").target(weaponOverrideEnableCheckBox).build()
        weaponOverrideEnableCheckBox.isChecked = convStrToArray(curSettings[weaponOverrideSelected])[0]!!.toBool()
        weaponOverrideEnableCheckBox.isDisabled = !weaponOverride
        weaponOverrideEnableCheckBox.changed { _, _ ->
            val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
            curWep[0] = weaponOverrideEnableCheckBox.isChecked.toDouble()
            curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            enableOverride = weaponOverrideEnableCheckBox.isChecked
            uiUpdate()
            true
        }

        //Create Flat Aim Toggle
        Tooltip.Builder("Whether or not to enable flat aim").target(enableFlatAim).build()
        enableFlatAim.isChecked = curSettings[categorySelected + "_ENABLE_FLAT_AIM"]!!.strToBool()
        enableFlatAim.changed { _, _ ->
            if (weaponOverride && enableOverride)
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
                if (curSettings[categorySelected + "_ENABLE_FLAT_AIM"]!!.strToBool()) {curSettings[categorySelected + "_ENABLE_PATH_AIM"] = "false"}
            }
            uiUpdate()
            true
        }

        //Create Path Aim Toggle
        Tooltip.Builder("Whether or not to enable path aim").target(enablePathAim).build()
        enablePathAim.isChecked = curSettings[categorySelected + "_ENABLE_PATH_AIM"]!!.strToBool()
        enablePathAim.changed { _, _ ->
            if (weaponOverride && enableOverride) {
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
                if (curSettings[categorySelected + "_ENABLE_PATH_AIM"]!!.strToBool()) {curSettings[categorySelected + "_ENABLE_FLAT_AIM"] = "false"}
            }
            uiUpdate()
            true
        }

        //Create Aim Bone Selector Box
        val aimBone = VisTable()
        Tooltip.Builder("The default aim bone to aim at").target(aimBone).build()
        aimBoneBox.setItems("HEAD", "NECK", "CHEST", "STOMACH", "NEAREST")
        aimBoneBox.selected = when (curSettings[categorySelected + "_AIM_BONE"]!!.toInt()) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            STOMACH_BONE -> "STOMACH"
            else -> "NEAREST"
        }
        aimBone.add(aimBoneLabel).width(200F)
        aimBone.add(aimBoneBox)

        aimBoneBox.changed { _, _ ->
            val setBone = curSettings[aimBoneBox.selected + "_BONE"]!!.toInt()

            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[4] = setBone.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_AIM_BONE"] = setBone.toString()
            }
        }

        //Create Aim FOV Slider
        val aimFov = VisTable()
        Tooltip.Builder("The aim field of view").target(aimFov).build()
        aimFovSlider.value = curSettings[categorySelected + "_AIM_FOV"]!!.toInt().toFloat()
        aimFovSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[5] = aimFovSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            } else {
                curSettings[categorySelected + "_AIM_FOV"] = aimFovSlider.value.toInt().toString()
            }

            aimFovLabel.setText("FOV: " + aimFovSlider.value.toInt())
        }
        aimFov.add(aimFovLabel).width(200F)
        aimFov.add(aimFovSlider).width(250F)

        //Create Aim Speed Slider
        val aimSpeed = VisTable()
        Tooltip.Builder("The aim speed delay in milliseconds").target(aimSpeed).build()
        aimSpeedSlider.value = curSettings[categorySelected + "_AIM_SPEED"]!!.toInt().toFloat()
        aimSpeedSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[6] = aimSpeedSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            } else {
                curSettings[categorySelected + "_AIM_SPEED"] = aimSpeedSlider.value.toInt().toString()
            }
            aimSpeedLabel.setText("Speed: " + aimSpeedSlider.value.toInt())
        }
        aimSpeed.add(aimSpeedLabel).width(200F)
        aimSpeed.add(aimSpeedSlider).width(250F)

        //Create Aim Smoothness Slider
        val aimSmoothness = VisTable()
        Tooltip.Builder("The smoothness of the aimbot (path aim only)").target(aimSmoothness).build()
        aimSmoothnessSlider.value = curSettings[categorySelected + "_AIM_SMOOTHNESS"]!!.toFloat()
        aimSmoothnessSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[7] = aimSmoothnessSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_AIM_SMOOTHNESS"] = (round(aimSmoothnessSlider.value.toDouble() * 10.0) / 10.0).toString()
            }
            aimSmoothnessLabel.setText("Smoothness: " + (round(aimSmoothnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimSmoothness.add(aimSmoothnessLabel).width(200F)
        aimSmoothness.add(aimSmoothnessSlider).width(250F)

        //Create Aim Strictness Slider
        val aimStrictness = VisTable()
        Tooltip.Builder("How strict moving to the aimbone is, lower = less deviation").target(aimStrictness).build()
        aimStrictnessSlider.value = curSettings[categorySelected + "_AIM_STRICTNESS"]!!.toFloat()
        aimStrictnessSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[8] = aimStrictnessSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_AIM_STRICTNESS"] = (round(aimStrictnessSlider.value.toDouble() * 10.0) / 10.0).toString()
            }
            aimStrictnessLabel.setText("Strictness: " + (round(aimStrictnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimStrictness.add(aimStrictnessLabel).width(200F)
        aimStrictness.add(aimStrictnessSlider).width(250F)

        //Create Perfect Aim Collapsible Check Box
        Tooltip.Builder("Whether or not to enable perfect aim").target(perfectAimCheckBox).build()
        perfectAimCheckBox.isChecked = curSettings[categorySelected + "_PERFECT_AIM"]!!.strToBool()
        perfectAimCollapsible.setCollapsed(!curSettings[categorySelected + "_PERFECT_AIM"]!!.strToBool(), true)

        //Create Perfect Aim Fov Slider
        val perfectAimFov = VisTable()
        Tooltip.Builder("The perfect aim field of view").target(perfectAimFov).build()
        perfectAimFovSlider.value = curSettings[categorySelected + "_PERFECT_AIM_FOV"]!!.toInt().toFloat()
        perfectAimFovSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[10] = perfectAimFovSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_PERFECT_AIM_FOV"] = perfectAimFovSlider.value.toInt().toString()
            }
            perfectAimFovLabel.setText("FOV: " + perfectAimFovSlider.value.toInt()) //+ when (perfectAimFovSlider.value.toInt().toString().length) {3 -> "  "2 -> "    "else -> "      " })
        }
        perfectAimFov.add(perfectAimFovLabel).width(180F)
        perfectAimFov.add(perfectAimFovSlider).width(250F)
        //End Perfect Aim Fov Slider

        //Create Perfect Aim Chance Slider
        val perfectAimChance = VisTable()
        Tooltip.Builder("The perfect aim chance (per calculation)").target(perfectAimChance).build()
        perfectAimChanceSlider.value = curSettings[categorySelected + "_PERFECT_AIM_CHANCE"]!!.toInt().toFloat()
        perfectAimChanceSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[11] = perfectAimChanceSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_PERFECT_AIM_CHANCE"] = perfectAimChanceSlider.value.toInt().toString()
            }
            perfectAimChanceLabel.setText("Chance: " + perfectAimChanceSlider.value.toInt())// + when (perfectAimChanceSlider.value.toInt().toString().length) { 3 -> "  " 2 -> "    " else-> "      " })
        }

        perfectAimChance.add(perfectAimChanceLabel).width(180F)
        perfectAimChance.add(perfectAimChanceSlider).width(250F)
        //End Perfect Aim Chance Slider

        perfectAimTable.add(perfectAimFov).padLeft(20F).left().row()
        perfectAimTable.add(perfectAimChance).padLeft(20F).left().row()

        perfectAimCheckBox.changed { _, _ ->
            if (weaponOverride && enableOverride) {
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
        table.add(automaticWeaponsCheckBox).left().row()
        table.add(automaticWeapons).padLeft(20F).left().row() //Shifted right

        table.add(aimKey).left().row()
        table.add(forceAimKey).left().row()

        table.addSeparator()

        table.add(categorySelection).left().row()
        table.add(weaponOverrideSelection).left().row()
        table.add(weaponOverrideEnableCheckBox).left().row()
        table.add(enableFactorRecoil).left().row()
        table.add(enableFlatAim).left().row()
        table.add(enablePathAim).left().row()
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