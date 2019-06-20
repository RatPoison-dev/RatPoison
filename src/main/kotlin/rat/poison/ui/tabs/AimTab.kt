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

//Need to change path/aim to a drop down

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(true)

    var categorySelected = ""
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
    val automaticWeaponsCheckBox = VisCheckBox("Enable Automatic Weapons")
    private val automaticWeaponsTable = VisTable()
    val automaticWeaponsCollapsible = CollapsibleWidget(automaticWeaponsTable)
    val automaticWeaponsLabel = VisLabel("MS Delay: " + curSettings["AUTO_WEP_DELAY"] + when(curSettings["AUTO_WEP_DELAY"]!!.length) {3->"" 2->"  " else ->"    "})
    val automaticWeaponsSlider = VisSlider(10F, 500F, 10F, false)

    val categorySelectionBox = VisSelectBox<String>()

    //Override Weapon Checkbox & Selection Box
    val categorySelectLabel = VisLabel("Weapon Category: ")
    val weaponOverrideCheckBox = VisCheckBox("Weapon Override")

    val weaponOverrideSelectionBox = VisSelectBox<String>()
    val weaponOverrideEnableCheckBox = VisCheckBox("Enable Override")

    val enableFactorRecoil = VisCheckBox("Factor Recoil")
    val enableFlatAim = VisCheckBox("Flat Aim")
    val enablePathAim = VisCheckBox("Path Aim")

    val aimBoneLabel = VisLabel("Aim Bone: ")
    val aimBoneBox = VisSelectBox<String>()

    val aimFovLabel = VisLabel("Aim Fov: " + curSettings["PISTOL_AIM_FOV"]!!.toInt().toString() + when(curSettings["PISTOL_AIM_FOV"]!!.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val aimFovSlider = VisSlider(1F, 360F, 2F, false)

    val aimSpeedLabel = VisLabel("Aim Speed: " + curSettings["PISTOL_AIM_SPEED"]!!.toInt().toString() + when(curSettings["PISTOL_AIM_SPEED"]!!.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val aimSpeedSlider = VisSlider(1F, 100F, 1F, false)

    val aimSmoothnessLabel = VisLabel("Aim Smoothness: " + curSettings["PISTOL_AIM_SMOOTHNESS"]!!.toFloat())
    val aimSmoothnessSlider = VisSlider(1F, 10F, 0.1F, false)

    val aimStrictnessLabel = VisLabel("Aim Strictness: " + curSettings["PISTOL_AIM_STRICTNESS"])
    val aimStrictnessSlider = VisSlider(1F, 10F, 0.1F, false)

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim")
    private val perfectAimTable = VisTable()
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
    val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + curSettings["PISTOL_PERFECT_AIM_FOV"]!!.toInt().toString() + when(curSettings["PISTOL_PERFECT_AIM_FOV"]!!.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val perfectAimFovSlider = VisSlider(1F, 100F, 1F, false)
    val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + curSettings["PISTOL_PERFECT_AIM_CHANCE"]!!.toInt().toString() + when(curSettings["PISTOL_PERFECT_AIM_CHANCE"]!!.toInt().toString().length) {3->"  " 2->"    " else ->"      "})
    val perfectAimChanceSlider = VisSlider(1F, 100F, 1F, false)

    init {
        if (curSettings["WARNING"]!!.strToBool()) {
            val dialog = Dialogs.showOKDialog(App.menuStage, "Warning", "Current Version: 1.3.2.3\n\nTo override weapon aim settings, check the weapon override checkbox,\nonce you do so you are editing the settings for the weapon selected in\nthe box beside the checkbox whether you are enabling an override or not.\nTo edit the whole group (such as pistols/shotguns) uncheck weapon override\n\nIf you have any problems submit an issue on Github\nGitHub: https://github.com/astupidrat/ratpoison")
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
            automaticWeaponsSlider.isDisabled = bool
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
        if (curSettings["ACTIVATE_FROM_FIRE_KEY"]!!.strToBool()) activateFromFireKey.toggle()
        activateFromFireKey.changed { _, _ ->
            curSettings["ACTIVATE_FROM_FIRE_KEY"] = activateFromFireKey.isChecked.boolToStr()
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
            automaticWeaponsCollapsible.isCollapsed = !curSettings["AUTOMATIC_WEAPONS"]!!.strToBool()

            //Create Auto Wep Delay Slider
            val automaticWeapons = VisTable()
            Tooltip.Builder("The ms delay between checking punch to fire a shot using AUTOMATIC WEAPONS, the lower the less accurate but faster firing").target(automaticWeapons).build()
            automaticWeaponsSlider.value = curSettings["AUTO_WEP_DELAY"]!!.toFloat()
            automaticWeaponsSlider.changed { _, _ ->
                curSettings["AUTO_WEP_DELAY"] = automaticWeaponsSlider.value.toInt()
                automaticWeaponsLabel.setText("MS Delay: " + curSettings["AUTO_WEP_DELAY"] + when(curSettings["AUTO_WEP_DELAY"]!!.length) {3->"" 2->"  " else ->"    "})
            }
            automaticWeapons.add(automaticWeaponsLabel)
            automaticWeapons.add(automaticWeaponsSlider)

            automaticWeaponsTable.add(automaticWeapons)

            automaticWeaponsCheckBox.changed { _, _ ->
                curSettings["AUTOMATIC_WEAPONS"] = automaticWeaponsCheckBox.isChecked.boolToStr()
                automaticWeaponsCollapsible.setCollapsed(!automaticWeaponsCollapsible.isCollapsed, true)
            }
        //End Automatic Weapons Collapsible Check Box

        //Create Fire Key Input Box
        val aimKey = VisTable()
        Tooltip.Builder("The key code of your in-game aim key (default m1)").target(aimKey).build()
        aimKeyField.text = curSettings["FIRE_KEY"]
        aimKey.changed { _, _ ->
            if (aimKeyField.text.toIntOrNull() != null) {
                curSettings["FIRE_KEY"] = aimKeyField.text.toInt().toString()
            }
        }
        aimKey.add(aimKeyLabel)
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
        forceAimKey.add(forceAimKeyLabel)
        forceAimKey.add(forceAimKeyField).spaceRight(6F).width(40F)
        forceAimKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Factor Recoil Toggle
        Tooltip.Builder("Whether or not to factor in recoil when aiming").target(enableFactorRecoil).build()
        enableFactorRecoil.isChecked = curSettings["PISTOL_ENABLE_PATH_AIM"]!!.strToBool()
        enableFactorRecoil.changed { _, _ ->
            if (!(weaponOverride && enableOverride)) {
                curSettings[categorySelected + "_FACTOR_RECOIL"] = enableFactorRecoil.isChecked.boolToStr()
            }
            else {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                curWep[1] = enableFactorRecoil.isChecked.toDouble()
                curSettings[weaponOverrideSelected] = convArrayToStr(curWep.contentToString())
            }
            UIUpdate()
            true
        }

        //Create Category Selector Box
        val categorySelection = VisTable()
        Tooltip.Builder("The weapon category settings to edit").target(categorySelection).build()
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
                "RIFLE" -> { weaponOverrideSelectionBox.clearItems(); weaponOverrideSelectionBox.setItems("AK47", "AUG", "FAMAS", "SG553", "GALIL", "M4A4", "M4A1_SILENCER", "NEGEV", "M249") }
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

                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
                enableOverride = curWep[0]!!.strToBool()

                println(enableOverride)
                println(weaponOverride)
                println(weaponOverrideSelected)

                UIUpdate()
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
            UIUpdate()
            true
        }

        //Create Flat Aim Toggle
        Tooltip.Builder("Whether or not to enable flat aim").target(enableFlatAim).build()
        enableFlatAim.isChecked = curSettings["PISTOL_ENABLE_FLAT_AIM"]!!.strToBool()
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
            UIUpdate()
            true
        }

        //Create Path Aim Toggle
        Tooltip.Builder("Whether or not to enable path aim").target(enablePathAim).build()
        enablePathAim.isChecked = curSettings["PISTOL_ENABLE_PATH_AIM"]!!.strToBool()
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
            UIUpdate()
            true
        }

        //Create Aim Bone Selector Box
        val aimBone = VisTable()
        Tooltip.Builder("The default aim bone to aim at").target(aimBone).build()
        aimBoneBox.setItems("HEAD", "NECK", "CHEST", "STOMACH")
        aimBoneBox.selected = when (curSettings["PISTOL_AIM_BONE"]!!.toInt()) {
            HEAD_BONE -> "HEAD"
            NECK_BONE -> "NECK"
            CHEST_BONE -> "CHEST"
            else -> "STOMACH"
        }
        aimBone.add(aimBoneLabel).top().spaceRight(6F)
        aimBone.add(aimBoneBox)

        aimBoneBox.changed { _, _ ->
            val setBone = curSettings[aimBoneBox.selected + "_BONE"]!!.toInt()

            if (weaponOverride && enableOverride) {
                //println("WE ARE OVERRIDING")
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
        aimFovSlider.value = curSettings["PISTOL_AIM_FOV"]!!.toInt().toFloat()
        aimFovSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
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

        //Create Aim Speed Slider
        val aimSpeed = VisTable()
        Tooltip.Builder("The aim speed delay in milliseconds").target(aimSpeed).build()
        aimSpeedSlider.value = curSettings["PISTOL_AIM_SPEED"]!!.toInt().toFloat()
        aimSpeedSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
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

        //Create Aim Smoothness Slider
        val aimSmoothness = VisTable()
        Tooltip.Builder("The smoothness of the aimbot (path aim only)").target(aimSmoothness).build()
        aimSmoothnessSlider.value = curSettings["PISTOL_AIM_SMOOTHNESS"]!!.toFloat()
        aimSmoothnessSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
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

        //Create Aim Strictness Slider
        val aimStrictness = VisTable()
        Tooltip.Builder("How strict moving to the aimbone is, lower = less deviation").target(aimStrictness).build()
        aimStrictnessSlider.value = curSettings["PISTOL_AIM_STRICTNESS"]!!.toFloat()
        aimStrictnessSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
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

        //Create Perfect Aim Collapsible Check Box
        Tooltip.Builder("Whether or not to enable perfect aim").target(perfectAimCheckBox).build()
        perfectAimCheckBox.isChecked = curSettings["PISTOL_PERFECT_AIM"]!!.strToBool()
        perfectAimCollapsible.setCollapsed(!curSettings["PISTOL_PERFECT_AIM"]!!.strToBool(), true)

        //Create Perfect Aim Fov Slider
        val perfectAimFov = VisTable()
        Tooltip.Builder("The perfect aim field of view").target(perfectAimFov).build()
        perfectAimFovSlider.value = curSettings["PISTOL_PERFECT_AIM_FOV"]!!.toInt().toFloat()
        perfectAimFovSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
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
        //End Perfect Aim Fov Slider

        //Create Perfect Aim Chance Slider
        val perfectAimChance = VisTable()
        Tooltip.Builder("The perfect aim chance (per calculation)").target(perfectAimChance).build()
        perfectAimChanceSlider.value = curSettings["PISTOL_PERFECT_AIM_CHANCE"]!!.toInt().toFloat()
        perfectAimChanceSlider.changed { _, _ ->
            if (weaponOverride && enableOverride) {
                val curWep : Array<Double?> = convStrToArray(curSettings[weaponOverrideSelected])
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
        //End Perfect Aim Chance Slider

        perfectAimTable.add(perfectAimFov).row()
        perfectAimTable.add(perfectAimChance).row()

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


        //Add all items to label for tabbed pane content
        table.add(enableAim).row()

        table.addSeparator()

        table.add(activateFromFireKey).row()
        table.add(teammatesAreEnemies).row()
        table.add(automaticWeaponsCheckBox).row()
        table.add(automaticWeaponsCollapsible).row()
        table.add(aimKey).row()
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
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Aim"
    }
}