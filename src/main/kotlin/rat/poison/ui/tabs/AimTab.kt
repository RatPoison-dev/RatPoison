package rat.poison.ui.tabs

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

//Need to change path/aim to a drop down

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(true)

    var categorySelected = ""
    //var overrideCategorySelected = ""
    var weaponOverrideSelected = ""
    var weaponOverride = false
    var enableOverride = false

    //Init labels/sliders/boxes that show values here
    val activateFromFireKey = VisCheckBox("Activate From Fire Key") //curSettings["ACTIVATE_FROM_FIRE_KEY"]!!.strToBool()
    val teammatesAreEnemies = VisCheckBox("Teammates Are Enemies") //curSettings["TEAMMATES_ARE_ENEMIES"]!!.strToBool()
    val forceAimKeyField = VisValidatableTextField(Validators.FLOATS) //curSettings["FORCE_AIM_KEY"].toString()

    //Automatic Weapons Collapsible
    val automaticWeaponsCheckBox = VisCheckBox("Enable Automatic Weapons")
    private val automaticWeaponsTable = VisTable()
    val automaticWeaponsCollapsible = CollapsibleWidget(automaticWeaponsTable)
    val maxPunchCheckLabel = VisLabel("MS Delay: " + curSettings["AUTO_WEP_DELAY"].toString() + when(curSettings["AUTO_WEP_DELAY"].toString().length) {3->"" 2->"  " else ->"    "}) //curSettings["AUTO_WEP_DELAY"]
    val maxPunchCheckSlider = VisSlider(10F, 500F, 10F, false) //curSettings["AUTO_WEP_DELAY"]

    private val categorySelectionBox = VisSelectBox<String>() //Category

    //Override Weapon Checkbox & Selection Box
    private val weaponOverrideCheckBox = VisCheckBox("Weapon Override") //Override Weapon Check Box
    private val weaponOverrideSelectionBox = VisSelectBox<String>() //Override Weapon Select Box
    val weaponOverrideEnableCheckBox = VisCheckBox("Enable Override") //Enable Override

    val enableFactorRecoil = VisCheckBox("Factor Recoil") //Factor Recoil
    val enableFlatAim = VisCheckBox("Flat Aim") //Enable_Flat_Aim
    val enablePathAim = VisCheckBox("Path Aim") //Enable_Path_Aim
    val aimBoneBox = VisSelectBox<String>() //Aim_Bone
    val aimFovLabel = VisLabel("Aim Fov: " + curSettings["PISTOL_AIM_FOV"].toString().toInt().toString() + when(curSettings["PISTOL_AIM_FOV"].toString().toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Fov
    val aimFovSlider = VisSlider(1F, 360F, 2F, false) //Aim_Fov
    val aimSpeedLabel = VisLabel("Aim Speed: " + curSettings["PISTOL_AIM_SPEED"].toString().toInt().toString() + when(curSettings["PISTOL_AIM_SPEED"].toString().toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Speed_Min
    val aimSpeedSlider = VisSlider(1F, 100F, 1F, false) //Aim_Speed_Min
    val aimSmoothnessLabel = VisLabel("Aim Smoothness: " + curSettings["PISTOL_AIM_SMOOTHNESS"].toString().toFloat()) //Aim_Smoothness
    val aimSmoothnessSlider = VisSlider(1F, 10F, 0.1F, false) //Aim_Smoothness
    val aimStrictnessLabel = VisLabel("Aim Strictness: " + curSettings["PISTOL_AIM_STRICTNESS"]) //Aim_Strictness
    val aimStrictnessSlider = VisSlider(1F, 10F, 0.1F, false) //Aim_Strictness

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim") //Perfect_Aim
    private val perfectAimTable = VisTable() //Perfect_Aim_Collapsible Table
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable) //Perfect_Aim_Collapsible
    val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + curSettings["PISTOL_PERFECT_AIM_FOV"].toString().toInt().toString() + when(curSettings["PISTOL_PERFECT_AIM_FOV"].toString().toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Fov
    val perfectAimFovSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Fov
    val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().toInt().toString() + when(curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Chance
    val perfectAimChanceSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Chance

    init {
        val dialog = Dialogs.showOKDialog(App.menuStage, "Warning", "Current Version: 1.3.1\n\nTo override weapon aim settings, check the weapon override checkbox,\nonce you do so you are editing the settings for the weapon selected in\nthe box beside the checkbox whether you are enabling an override or not.\nTo edit the whole group (such as pistols/shotguns) uncheck weapon override\n\nIf you have any problems submit an issue on Github\nGitHub: https://github.com/astupidrat/ratpoison")
        dialog.setPosition(gameWidth/4F-dialog.width/2F, gameHeight.toFloat()/2F)
        menuStage.addActor(dialog)

        //Create curSettings["ACTIVATE_FROM_FIRE_KEY"]!!.strToBool() Toggle
        Tooltip.Builder("Activate aim if pressing predefined fire key").target(activateFromFireKey).build()
        if (curSettings["ACTIVATE_FROM_FIRE_KEY"]!!.strToBool()) activateFromFireKey.toggle()
        activateFromFireKey.changed { _, _ ->
            curSettings["ACTIVATE_FROM_FIRE_KEY"] = activateFromFireKey.isChecked.boolToStr()
            true
        }

        //Create curSettings["TEAMMATES_ARE_ENEMIES"]!!.strToBool() Toggle
        Tooltip.Builder("Teammates will be treated as enemies").target(teammatesAreEnemies).build()
        if (curSettings["TEAMMATES_ARE_ENEMIES"]!!.strToBool()) teammatesAreEnemies.toggle()
        teammatesAreEnemies.changed { _, _ ->
            curSettings["TEAMMATES_ARE_ENEMIES"] = teammatesAreEnemies.isChecked.boolToStr()
            true
        }

        //Create curSettings["AUTOMATIC_WEAPONS"]!!.strToBool() Collapsible Check Box
        Tooltip.Builder("Non-automatic weapons will auto shoot when there is no punch and the fire key is pressed").target(automaticWeaponsCheckBox).build()
        automaticWeaponsCheckBox.isChecked = curSettings["AUTOMATIC_WEAPONS"]!!.strToBool()
        automaticWeaponsCollapsible.isCollapsed = !curSettings["AUTOMATIC_WEAPONS"]!!.strToBool()

        //Create curSettings["AUTO_WEP_DELAY"] Slider
        val maxPunchCheck = VisTable()
        Tooltip.Builder("The ms delay between checking punch to fire a shot using AUTOMATIC WEAPONS, the lower the less accurate but faster firing").target(maxPunchCheck).build()
        maxPunchCheckSlider.value = curSettings["AUTO_WEP_DELAY"].toString().toFloat()
        maxPunchCheckSlider.changed { _, _ ->
            curSettings["AUTO_WEP_DELAY"] = maxPunchCheckSlider.value.toInt()
            maxPunchCheckLabel.setText("MS Delay: " + curSettings["AUTO_WEP_DELAY"].toString() + when(curSettings["AUTO_WEP_DELAY"].toString().length) {3->"" 2->"  " else ->"    "})
        }
        maxPunchCheck.add(maxPunchCheckLabel)
        maxPunchCheck.add(maxPunchCheckSlider)

        //End Aim_Assist_Strictness Slider

        automaticWeaponsTable.add(maxPunchCheck)

        automaticWeaponsCheckBox.changed { _, _ ->
            curSettings["AUTOMATIC_WEAPONS"] = automaticWeaponsCheckBox.isChecked.boolToStr()
            automaticWeaponsCollapsible.setCollapsed(!automaticWeaponsCollapsible.isCollapsed, true)
        }
        //End curSettings["AUTOMATIC_WEAPONS"]!!.strToBool() Collapsible Check Box

        //Create curSettings["FORCE_AIM_KEY"].toString() Input
        val forceAimKey = VisTable()
        Tooltip.Builder("The key to force lock onto any enemy inside aim fov").target(forceAimKey).build()
        val forceAimKeyLabel = VisLabel("Force Aim Key: ")
        forceAimKeyField.text = curSettings["FORCE_AIM_KEY"].toString()
        forceAimKey.changed { _, _ ->
            if (forceAimKeyField.text.toIntOrNull() != null) {
                curSettings["FORCE_AIM_KEY"] = forceAimKeyField.text.toInt().toString()
            }
        }
        forceAimKey.add(forceAimKeyLabel)
        forceAimKey.add(forceAimKeyField).spaceRight(6F).width(40F)
        forceAimKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Factor Recoil
        Tooltip.Builder("Whether or not to factor in recoil when aiming").target(enableFactorRecoil).build()
        enableFactorRecoil.isChecked = curSettings["PISTOL_ENABLE_PATH_AIM"]!!.strToBool()
        enableFactorRecoil.changed { _, _ ->
            if (!(weaponOverride && enableOverride)) {
                curSettings[categorySelected + "_FACTOR_RECOIL"] = enableFactorRecoil.isChecked.boolToStr()
            }
            else {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
                curWep[1] = enableFactorRecoil.isChecked.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            UIUpdate()
            true
        }

        //Create Category Selector
        val categorySelection = VisTable()
        Tooltip.Builder("The weapon category settings to edit").target(categorySelection).build()
        val categorySelectLabel = VisLabel("Weapon Category: ")
        categorySelectionBox.setItems("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
        categorySelectionBox.selected = "PISTOL"
        categorySelected = categorySelectionBox.selected
        categorySelection.add(categorySelectLabel).top().spaceRight(6F)
        categorySelection.add(categorySelectionBox)

        categorySelectionBox.changed { _, _ ->
            categorySelected = categorySelectionBox.selected
            when (categorySelected)
            {
                "PISTOL" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("DESERT_EAGLE", "DUAL_BERRETA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250") }
                "SMG" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("MAC10", "P90", "MP5", "UMP45", "MP7", "MP9", "PP_BIZON") }
                "RIFLE" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("AK47", "AUG", "FAMAS", "SG553", "GALIL", "M4A4", "M4A1S", "NEGEV", "M249") }
                "SNIPER" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("AWP", "G3SG1", "SCAR20", "SSG08") }
                "SHOTGUN" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("XM1014", "MAG7", "SAWED_OFF", "NOVA") }
            }
            weaponOverrideSelectionBox.selected = weaponOverrideSelectionBox.items[0]
            UIUpdate()
            true
        }

        //Create Override Weapon Check Box & Collapsible
        Tooltip.Builder("Whether or not to override a custom weapon's settings").target(weaponOverrideCheckBox).build()
        weaponOverrideCheckBox.isChecked = weaponOverride

        //Create Override Weapon Selector
        val weaponOverrideSelection = VisTable()
        //Tooltip.Builder("The weapon category settings to edit").target(weaponOverrideSelection).build()
        weaponOverrideSelectionBox.setItems("DESERT_EAGLE", "DUAL_BERRETA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250")
        weaponOverrideSelectionBox.selected = "DESERT_EAGLE"
        weaponOverrideSelectionBox.isDisabled = !weaponOverride
        weaponOverrideSelected = weaponOverrideSelectionBox.selected
        weaponOverrideSelection.add(weaponOverrideCheckBox).top().spaceRight(6F)
        weaponOverrideSelection.add(weaponOverrideSelectionBox)

        weaponOverrideSelectionBox.changed { _, _ ->
            if (!weaponOverrideSelectionBox.selected.isNullOrEmpty()) {
                weaponOverrideSelected = weaponOverrideSelectionBox.selected
            }
            UIUpdate()
            true
        }
        //End Override Weapon Selection Box

        weaponOverrideCheckBox.changed { _, _ ->
            weaponOverride = weaponOverrideCheckBox.isChecked
            weaponOverrideSelectionBox.isDisabled = !weaponOverride
            weaponOverrideEnableCheckBox.isDisabled = !weaponOverride


            val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
            enableOverride = curWep[0]!!.strToBool()
            enableOverride = weaponOverrideCheckBox.isChecked


            UIUpdate()
            true
        }
        //End Override Weapon Check Box

        //Create Enable Override
        Tooltip.Builder("Whether or not to override aim when this gun is selected").target(weaponOverrideEnableCheckBox).build()
        weaponOverrideEnableCheckBox.isChecked = convStrToArray(curSettings[weaponOverrideSelected].toString())[0]!!.toBool()
        weaponOverrideEnableCheckBox.isDisabled = !weaponOverride
        weaponOverrideEnableCheckBox.changed { _, _ ->
            val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
            curWep[0] = weaponOverrideEnableCheckBox.isChecked.toDouble()
            curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            enableOverride = weaponOverrideEnableCheckBox.isChecked
            UIUpdate()
            true
        }

        //Create Enable_Flat_Aim Toggle
        Tooltip.Builder("Whether or not to enable flat aim").target(enableFlatAim).build()
        enableFlatAim.isChecked = curSettings["PISTOL_ENABLE_FLAT_AIM"]!!.strToBool()
        enableFlatAim.changed { _, _ ->
            if (weaponOverride && enableOverride)
            {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
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
            UIUpdate()
            true
        }

        //Create Enable_Path_Aim Toggle
        Tooltip.Builder("Whether or not to enable path aim").target(enablePathAim).build()
        enablePathAim.isChecked = curSettings["PISTOL_ENABLE_PATH_AIM"]!!.strToBool()
        enablePathAim.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
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
            UIUpdate()
            true
        }

        //Create Aim_Bone Selector
        val aimBone = VisTable()
        Tooltip.Builder("The default aim bone to aim at").target(aimBone).build()
        val aimBoneLabel = VisLabel("Aim Bone: ")
        aimBoneBox.setItems("HEAD_BONE", "BODY_BONE")
        aimBoneBox.selected = if (curSettings["PISTOL_AIM_BONE"].toString().toInt() == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
        aimBone.add(aimBoneLabel).top().spaceRight(6F)
        aimBone.add(aimBoneBox)

        aimBoneBox.changed { _, _ ->
            var setBone = HEAD_BONE

            if (aimBoneBox.selected.toString() == "HEAD_BONE") {
                setBone = HEAD_BONE
            }
            else if (aimBoneBox.selected.toString() == "BODY_BONE") {
                setBone = BODY_BONE
            }

            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
                curWep[4] = setBone.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_AIM_BONE"] = setBone.toString()
            }
        }

        //Create Aim_Fov Slider
        val aimFov = VisTable()
        Tooltip.Builder("The aim field of view").target(aimFov).build()
        aimFovSlider.value = curSettings["PISTOL_AIM_FOV"].toString().toInt().toFloat()
        aimFovSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
                curWep[5] = aimFovSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_AIM_FOV"] = aimFovSlider.value.toInt().toString()
            }

            aimFovLabel.setText("Aim Fov: " + aimFovSlider.value.toInt() + when (aimFovSlider.value.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
        }
        aimFov.add(aimFovLabel)
        aimFov.add(aimFovSlider)

        //Create Aim_Speed Slider
        val aimSpeed = VisTable()
        Tooltip.Builder("The aim speed delay in milliseconds").target(aimSpeed).build()
        aimSpeedSlider.value = curSettings["PISTOL_AIM_SPEED"].toString().toInt().toFloat()
        aimSpeedSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
                curWep[6] = aimSpeedSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_AIM_SPEED"] = aimSpeedSlider.value.toInt().toString()
            }
            aimSpeedLabel.setText("Aim Speed: " + aimSpeedSlider.value.toInt() + when (aimSpeedSlider.value.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
        }
        aimSpeed.add(aimSpeedLabel)
        aimSpeed.add(aimSpeedSlider)

        //Create Aim_Smoothness Slider
        val aimSmoothness = VisTable()
        Tooltip.Builder("The smoothness of the aimbot (path aim only)").target(aimSmoothness).build()
        aimSmoothnessSlider.value = curSettings["PISTOL_AIM_SMOOTHNESS"].toString().toFloat()
        aimSmoothnessSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
                curWep[7] = aimSmoothnessSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_AIM_SMOOTHNESS"] = (Math.round(aimSmoothnessSlider.value.toDouble() * 10.0) / 10.0).toString()
            }
            aimSmoothnessLabel.setText("Aim Smoothness: " + (Math.round(aimSmoothnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimSmoothness.add(aimSmoothnessLabel).spaceRight(6F)
        aimSmoothness.add(aimSmoothnessSlider)

        //Create Aim_Strictness Slider
        val aimStrictness = VisTable()
        Tooltip.Builder("How strict moving to the aimbone is, lower = less deviation").target(aimStrictness).build()
        aimStrictnessSlider.value = curSettings["PISTOL_AIM_STRICTNESS"].toString().toFloat()
        aimStrictnessSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
                curWep[8] = aimStrictnessSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_AIM_STRICTNESS"] = (Math.round(aimStrictnessSlider.value.toDouble() * 10.0) / 10.0).toString()
            }
            aimStrictnessLabel.setText("Aim Strictness: " + (Math.round(aimStrictnessSlider.value.toDouble() * 10.0) / 10.0))
        }
        aimStrictness.add(aimStrictnessLabel).spaceRight(6F)
        aimStrictness.add(aimStrictnessSlider)

        //Create Perfect_Aim Collapsible Check Box
        Tooltip.Builder("Whether or not to enable perfect aim").target(perfectAimCheckBox).build()
        perfectAimCheckBox.isChecked = curSettings["PISTOL_PERFECT_AIM"]!!.strToBool()
        perfectAimCollapsible.setCollapsed(!curSettings["PISTOL_PERFECT_AIM"]!!.strToBool(), true)

        //Create Perfect_Aim_Fov Slider
        val perfectAimFov = VisTable()
        Tooltip.Builder("The perfect aim field of view").target(perfectAimFov).build()
        perfectAimFovSlider.value = curSettings["PISTOL_PERFECT_AIM_FOV"].toString().toInt().toFloat()
        perfectAimFovSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
                curWep[10] = perfectAimFovSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_PERFECT_AIM_FOV"] = perfectAimFovSlider.value.toInt().toString()
            }
            perfectAimFovLabel.setText("Perfect Aim Fov: " + perfectAimFovSlider.value.toInt() + when (perfectAimFovSlider.value.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
        }
        perfectAimFov.add(perfectAimFovLabel)
        perfectAimFov.add(perfectAimFovSlider)
        //End Perfect_Aim_Fov Slider

        //Create Perfect_Aim_Chance Slider
        val perfectAimChance = VisTable()
        Tooltip.Builder("The perfect aim chance (per calculation)").target(perfectAimChance).build()
        perfectAimChanceSlider.value = curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().toInt().toFloat()
        perfectAimChanceSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
                curWep[11] = perfectAimChanceSlider.value.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_PERFECT_AIM_CHANCE"] = perfectAimChanceSlider.value.toInt().toString()
            }
            perfectAimChanceLabel.setText("Perfect Aim Chance: " + perfectAimChanceSlider.value.toInt() + when (perfectAimChanceSlider.value.toInt().toString().length) {
                3 -> "  "
                2 -> "    "
                else -> "      "
            })
        }

        perfectAimChance.add(perfectAimChanceLabel)
        perfectAimChance.add(perfectAimChanceSlider)
        //End Perfect_Aim_Chance Slider

        perfectAimTable.add(perfectAimFov).row()
        perfectAimTable.add(perfectAimChance).row()

        perfectAimCheckBox.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected].toString())
                curWep[9] = perfectAimCheckBox.isChecked.boolToDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            else {
                curSettings[categorySelected + "_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
            }
            perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
        }
        //End Perfect_Aim Collapsible Check Box


        //Add all items to label for tabbed pane content
        table.add(activateFromFireKey).row()
        table.add(teammatesAreEnemies).row()
        table.add(automaticWeaponsCheckBox).row()
        table.add(automaticWeaponsCollapsible).row()
        table.add(forceAimKey).row()

        table.addSeparator()

        table.add(categorySelection).row()
        table.add(weaponOverrideSelection).row()
        table.add(weaponOverrideEnableCheckBox).row()
        table.add(enableFactorRecoil).row()
        table.add(enableFlatAim).row()
        table.add(enablePathAim).row()
        table.add(aimBone).row()
        table.add(aimFov).row()
        table.add(aimSpeed).row()
        table.add(aimSmoothness).row()
        table.add(aimStrictness).row()
        table.add(perfectAimCheckBox).row()
        table.add(perfectAimCollapsible).row()

        table.addSeparator()

//        table.add(overrideCategorySelection).row()
//        table.add(weaponCategorySelection).row()
//        table.add(overrideEnableOverride).row()
//        table.add(overrideEnableFactorRecoil).row()
//        table.add(overrideEnableFlatAim).row()
//        table.add(overrideEnablePathAim).row()
//        table.add(overrideAimBone).row()
//        table.add(overrideAimFov).row()
//        table.add(overrideAimSpeed).row()
//        table.add(overrideAimSmoothness).row()
//        table.add(overridePerfectAimCheckBox).row()
//        table.add(overridePerfectAimCollapsible).row()
//        table.add(overrideAimAssistCheckBox).row()
//        table.add(overrideAimAssistCollapsible).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Aim"
    }

    fun Boolean.toFloat() = if (this) 1F else 0F

    fun Boolean.toDouble() = if (this) 1.0 else 0.0
}