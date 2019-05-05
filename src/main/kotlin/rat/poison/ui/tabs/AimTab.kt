package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.App
import rat.poison.App.menuStage
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.ui.*

//Need to change path/aim to a drop down

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(true)

    var categorySelected = ""
    var overrideCategorySelected = ""
    var weaponCategorySelected = ""

    //Init labels/sliders/boxes that show values here
    val activateFromFireKey = VisCheckBox("Activate From Fire Key") //curSettings["ACTIVATE_FROM_FIRE_KEY"]!!.strToBool()
    val teammatesAreEnemies = VisCheckBox("Teammates Are Enemies") //curSettings["TEAMMATES_ARE_ENEMIES"]!!.strToBool()
    val forceAimKeyField = VisValidatableTextField(Validators.FLOATS) //curSettings["FORCE_AIM_KEY"].toString()

    //Automatic Weapons Collapsible
    val automaticWeaponsCheckBox = VisCheckBox("Enable Automatic Weapons")
    private val automaticWeaponsTable = VisTable()
    val automaticWeaponsCollapsible = CollapsibleWidget(automaticWeaponsTable)
    val maxPunchCheckLabel = VisLabel("Max Punch Check: " + curSettings["MAX_PUNCH_CHECK"].toString() + when(curSettings["MAX_PUNCH_CHECK"].toString().length) {3->"" 2->"  " else ->"    "}) //curSettings["MAX_PUNCH_CHECK"]
    val maxPunchCheckSlider = VisSlider(1F, 32F, 1F, false) //curSettings["MAX_PUNCH_CHECK"]

    private val categorySelectionBox = VisSelectBox<String>() //Category
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

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim") //Perfect_Aim
    private val perfectAimTable = VisTable() //Perfect_Aim_Collapsible Table
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable) //Perfect_Aim_Collapsible
    val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + curSettings["PISTOL_PERFECT_AIM_FOV"].toString().toInt().toString() + when(curSettings["PISTOL_PERFECT_AIM_FOV"].toString().toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Fov
    val perfectAimFovSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Fov
    val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().toInt().toString() + when(curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Chance
    val perfectAimChanceSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Chance

//    //Override Weapons Panel
//    val overrideTable = VisTable(true) //Container for all items
//
//    ////Custom Overrides
//    val overrideEnableOverride = VisCheckBox("Enable Override") //Enable Override
//    val overrideEnableFactorRecoil = VisCheckBox("Factor Recoil") //Factor Recoil
//    val overrideEnableFlatAim = VisCheckBox("Flat Aim") //Enable_Flat_Aim
//    val overrideEnablePathAim = VisCheckBox("Path Aim") //Enable_Path_Aim
//    val overrideAimBoneBox = VisSelectBox<String>() //Aim_Bone
//    val overrideAimFovLabel = VisLabel("Aim Fov: " + curSettings["PISTOL_AIM_FOV"].toString().toInt().toString() + when(curSettings["PISTOL_AIM_FOV"].toString().toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Fov
//    val overrideAimFovSlider = VisSlider(1F, 360F, 2F, false) //Aim_Fov
//    val overrideAimSpeedLabel = VisLabel("Aim Speed: " + curSettings["PISTOL_AIM_SPEED"].toString().toInt().toString() + when(curSettings["PISTOL_AIM_SPEED"].toString().toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Speed_Min
//    val overrideAimSpeedSlider = VisSlider(1F, 100F, 1F, false) //Aim_Speed_Min
//    val overrideAimSmoothnessLabel = VisLabel("Aim Smoothness: $curSettings["PISTOL_AIM_SMOOTHNESS"].toString().toFloat()") //Aim_Smoothness
//    val overrideAimSmoothnessSlider = VisSlider(1F, 10F, 0.1F, false) //Aim_Smoothness
//
//    //Perfect Aim Collapsible
//    val overridePerfectAimCheckBox = VisCheckBox("Enable Perfect Aim") //Perfect_Aim
//    private val overridePerfectAimTable = VisTable() //Perfect_Aim_Collapsible Table
//    val overridePerfectAimCollapsible = CollapsibleWidget(overridePerfectAimTable) //Perfect_Aim_Collapsible
//    val overridePerfectAimFovLabel = VisLabel("Perfect Aim Fov: " + curSettings["PISTOL_PERFECT_AIM_FOV"].toString().toInt().toString() + when(curSettings["PISTOL_PERFECT_AIM_FOV"].toString().toInt().toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Fov
//    val overridePerfectAimFovSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Fov
//    val overridePerfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().toInt() + when(curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().toInt().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Chance
//    val overridePerfectAimChanceSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Chance
//
//    //Aim Assist Collapsible
//    val overrideAimAssistCheckBox = VisCheckBox("Enable Aim Assist") //Aim_Assist
//    private val overrideAimAssistTable = VisTable() //Aim_Assist_Collapsible Table
//    val overrideAimAssistCollapsible = CollapsibleWidget(overrideAimAssistTable) //Aim_Assist_Collapsible
//
//    //Desert Eagle is the first item in pistols //proof of working, delete and add function to update info for us
//    var curOverrideWep : kotlin.DoubleArray = engine.eval("DESERT_EAGLE") as kotlin.DoubleArray
//
//    private val overrideCategorySelectionBox = VisSelectBox<String>() //Category
//
//    private val weaponCategorySelectionBox = VisSelectBox<String>() //Category

    init {
        val dialog = Dialogs.showOKDialog(App.menuStage, "Warning", "If you have any problems submit an issue on github\nIf you are crashing or have an error message in the cmd, include the error message if there is one,\nsettings that were enabled, and when it happened (such as randomly, when joining a game, on round end, etc)\n\nWARNING: This update has not been tested extensively, I assume many issues will pop up, please submit an issue on github\nwith information on the issue, the settings used (create a cfg file and put it in a pastebin), and evidence (such as a picture/video)\nor a way to easily replicate the issue.\nThe custom per weapon settings are available in the GunAimOverride.kts file, in arrays, the gun is the variable name, with the array\ninformation at the top.\nGitHub: https://github.com/astupidrat/ratpoison")
        dialog.setPosition(gameWidth/2F-dialog.width/2F, gameHeight.toFloat())
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

        //Create curSettings["MAX_PUNCH_CHECK"] Slider
        val maxPunchCheck = VisTable()
        Tooltip.Builder("The ms delay between checking punch to fire a shot using AUTOMATIC WEAPONS, the lower the less accurate but faster firing").target(maxPunchCheck).build()
        maxPunchCheckSlider.value = curSettings["MAX_PUNCH_CHECK"].toString().toFloat()
        maxPunchCheckSlider.changed { _, _ ->
            curSettings["MAX_PUNCH_CHECK"] = maxPunchCheckSlider.value.toInt()
            maxPunchCheckLabel.setText("Max Punch Check: " + curSettings["MAX_PUNCH_CHECK"].toString() + when(curSettings["MAX_PUNCH_CHECK"].toString().length) {3->"" 2->"  " else ->"    "})
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
            when (categorySelected) {
                "PISTOL" -> {
                    curSettings["PISTOL_FACTOR_RECOIL"] = enableFactorRecoil.isChecked.boolToStr()
                }

                "RIFLE" -> {
                    curSettings["RIFLE_FACTOR_RECOIL"] = enableFactorRecoil.isChecked.boolToStr()
                }

                "SMG" -> {
                    curSettings["SMG_FACTOR_RECOIL"] = enableFactorRecoil.isChecked.boolToStr()
                }

                "SNIPER" -> {
                    curSettings["SNIPER_FACTOR_RECOIL"] = enableFactorRecoil.isChecked.boolToStr()
                }

                "SHOTGUN" -> {
                    curSettings["SHOTGUN_FACTOR_RECOIL"] = enableFactorRecoil.isChecked.boolToStr()
                }
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
            UIUpdate()
            true
        }

        //Create Enable_Flat_Aim Toggle
        Tooltip.Builder("Whether or not to enable flat aim").target(enableFlatAim).build()
        enableFlatAim.isChecked = curSettings["PISTOL_ENABLE_FLAT_AIM"]!!.strToBool()
        enableFlatAim.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    curSettings["PISTOL_ENABLE_FLAT_AIM"] = enableFlatAim.isChecked.boolToStr()
                    if (curSettings["PISTOL_ENABLE_FLAT_AIM"]!!.strToBool()) {
                        curSettings["PISTOL_ENABLE_PATH_AIM"] = "false"
                    }
                }

                "RIFLE" -> {
                    curSettings["RIFLE_ENABLE_FLAT_AIM"] = enableFlatAim.isChecked.boolToStr()
                    if (curSettings["RIFLE_ENABLE_FLAT_AIM"]!!.strToBool()) {
                        curSettings["RIFLE_ENABLE_PATH_AIM"] = "false"
                    }
                }

                "SMG" -> {
                    curSettings["SMG_ENABLE_FLAT_AIM"] = enableFlatAim.isChecked.boolToStr()
                    if (curSettings["SMG_ENABLE_PATH_AIM"]!!.strToBool()) {
                        curSettings["SMG_ENABLE_PATH_AIM"] = "false"
                    }
                }

                "SNIPER" -> {
                    curSettings["SNIPER_ENABLE_FLAT_AIM"] = enableFlatAim.isChecked.boolToStr()
                    if (curSettings["SNIPER_ENABLE_PATH_AIM"]!!.strToBool()) {
                        curSettings["SNIPER_ENABLE_PATH_AIM"] = "false"
                    }
                }

                "SHOTGUN" -> {
                    curSettings["SHOTGUN_ENABLE_FLAT_AIM"] = enableFlatAim.isChecked.boolToStr()
                    if (curSettings["SHOTGUN_ENABLE_FLAT_AIM"]!!.strToBool()) {
                        curSettings["SHOTGUN_ENABLE_PATH_AIM"] = "false"
                    }
                }
            }
            UIUpdate()
            true
        }

        //Create Enable_Path_Aim Toggle
        Tooltip.Builder("Whether or not to enable path aim").target(enablePathAim).build()
        enablePathAim.isChecked = curSettings["PISTOL_ENABLE_PATH_AIM"]!!.strToBool()
        enablePathAim.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    curSettings["PISTOL_ENABLE_PATH_AIM"] = enablePathAim.isChecked.boolToStr()
                    if (curSettings["PISTOL_ENABLE_PATH_AIM"]!!.strToBool()) {
                        curSettings["PISTOL_ENABLE_FLAT_AIM"] = "false"
                    }
                }

                "RIFLE" -> {
                    curSettings["RIFLE_ENABLE_PATH_AIM"] = enablePathAim.isChecked.boolToStr()
                    if (curSettings["RIFLE_ENABLE_PATH_AIM"]!!.strToBool()) {
                        curSettings["RIFLE_ENABLE_FLAT_AIM"] = "false"
                    }
                }

                "SMG" -> {
                    curSettings["SMG_ENABLE_PATH_AIM"] = enablePathAim.isChecked.boolToStr()
                    if (curSettings["SMG_ENABLE_PATH_AIM"]!!.strToBool()) {
                        curSettings["SMG_ENABLE_FLAT_AIM"] = "false"
                    }
                }

                "SNIPER" -> {
                    curSettings["SNIPER_ENABLE_PATH_AIM"] = enablePathAim.isChecked.boolToStr()
                    if (curSettings["SNIPER_ENABLE_PATH_AIM"]!!.strToBool()) {
                        curSettings["SNIPER_ENABLE_FLAT_AIM"] = "false"
                    }
                }

                "SHOTGUN" -> {
                    curSettings["SHOTGUN_ENABLE_PATH_AIM"] = enablePathAim.isChecked.boolToStr()
                    if (curSettings["SHOTGUN_ENABLE_PATH_AIM"]!!.strToBool()) {
                        curSettings["SHOTGUN_ENABLE_FLAT_AIM"] = "false"
                    }
                }
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

            when (categorySelected) {
                "PISTOL" -> {
                    curSettings["PISTOL_AIM_BONE"] = setBone.toString()
                }

                "RIFLE" -> {
                    curSettings["RIFLE_AIM_BONE"] = setBone.toString()
                }

                "SMG" -> {
                    curSettings["SMG_AIM_BONE"] = setBone.toString()
                }

                "SNIPER" -> {
                    curSettings["SNIPER_AIM_BONE"] = setBone.toString()
                }

                "SHOTGUN" -> {
                    curSettings["SHOTGUN_AIM_BONE"] = setBone.toString()
                }
            }
        }

        //Create Aim_Fov Slider
        val aimFov = VisTable()
        Tooltip.Builder("The aim field of view").target(aimFov).build()
        aimFovSlider.value = curSettings["PISTOL_AIM_FOV"].toString().toInt().toFloat()
        aimFovSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    curSettings["PISTOL_AIM_FOV"] = aimFovSlider.value.toInt().toString()
                    aimFovLabel.setText("Aim Fov: " + curSettings["PISTOL_AIM_FOV"] + when(curSettings["PISTOL_AIM_FOV"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "RIFLE" -> {
                    curSettings["RIFLE_AIM_FOV"] = aimFovSlider.value.toInt().toString()
                    aimFovLabel.setText("Aim Fov: " + curSettings["RIFLE_AIM_FOV"] + when(curSettings["RIFLE_AIM_FOV"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SMG" -> {
                    curSettings["SMG_AIM_FOV"] = aimFovSlider.value.toInt().toString()
                    aimFovLabel.setText("Aim Fov: " + curSettings["SMG_AIM_FOV"] + when(curSettings["SMG_AIM_FOV"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SNIPER" -> {
                    curSettings["SNIPER_AIM_FOV"] = aimFovSlider.value.toInt().toString()
                    aimFovLabel.setText("Aim Fov: " + curSettings["SNIPER_AIM_FOV"] + when(curSettings["SNIPER_AIM_FOV"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SHOTGUN" -> {
                    curSettings["SHOTGUN_AIM_FOV"] = aimFovSlider.value.toInt().toString()
                    aimFovLabel.setText("Aim Fov: " + curSettings["SHOTGUN_AIM_FOV"] + when(curSettings["SHOTGUN_AIM_FOV"].toString().length) {3->"  " 2->"    " else ->"      "})
                }
            }
        }
        aimFov.add(aimFovLabel)
        aimFov.add(aimFovSlider)

        //Create Aim_Speed Slider
        val aimSpeed = VisTable()
        Tooltip.Builder("The aim speed delay in milliseconds").target(aimSpeed).build()
        aimSpeedSlider.value = curSettings["PISTOL_AIM_SPEED"].toString().toInt().toFloat()
        aimSpeedSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    curSettings["PISTOL_AIM_SPEED"] = aimSpeedSlider.value.toInt().toString()
                    aimSpeedLabel.setText("Aim Speed: " + curSettings["PISTOL_AIM_SPEED"] + when(curSettings["PISTOL_AIM_SPEED"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "RIFLE" -> {
                    curSettings["RIFLE_AIM_SPEED"] = aimSpeedSlider.value.toInt().toString()
                    aimSpeedLabel.setText("Aim Speed: " + curSettings["RIFLE_AIM_SPEED"] + when(curSettings["RIFLE_AIM_SPEED"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SMG" -> {
                    curSettings["SMG_AIM_SPEED"] = aimSpeedSlider.value.toInt().toString()
                    aimSpeedLabel.setText("Aim Speed: " + curSettings["SMG_AIM_SPEED"] + when(curSettings["SMG_AIM_SPEED"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SNIPER" -> {
                    curSettings["SNIPER_AIM_SPEED"] = aimSpeedSlider.value.toInt().toString()
                    aimSpeedLabel.setText("Aim Speed: " + curSettings["SNIPER_AIM_SPEED"] + when(curSettings["SNIPER_AIM_SPEED"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SHOTGUN" -> {
                    curSettings["SHOTGUN_AIM_SPEED"] = aimSpeedSlider.value.toInt().toString()
                    aimSpeedLabel.setText("Aim Speed: " + curSettings["SHOTGUN_AIM_SPEED"] + when(curSettings["SHOTGUN_AIM_SPEED"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                else -> {} //When needs an else now for some reason?
            }
        }
        aimSpeed.add(aimSpeedLabel)
        aimSpeed.add(aimSpeedSlider)

        //Create Aim_Smoothness Slider
        val aimSmoothness = VisTable()
        Tooltip.Builder("The smoothness of the aimbot (path aim only)").target(aimSmoothness).build()
        aimSmoothnessSlider.value = curSettings["PISTOL_AIM_SMOOTHNESS"].toString().toFloat()
        aimSmoothnessSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    curSettings["PISTOL_AIM_SMOOTHNESS"] = (Math.round(aimSmoothnessSlider.value.toDouble() * 10.0)/10.0).toString()
                    aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings["PISTOL_AIM_SMOOTHNESS"].toString().toFloat())
                }

                "RIFLE" -> {
                    curSettings["RIFLE_AIM_SMOOTHNESS"] = (Math.round(aimSmoothnessSlider.value.toDouble() * 10.0)/10.0).toString()
                    aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings["RIFLE_AIM_SMOOTHNESS"])
                }

                "SMG" -> {
                    curSettings["SMG_AIM_SMOOTHNESS"] = (Math.round(aimSmoothnessSlider.value.toDouble() * 10.0)/10.0).toString()
                    aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings["SMG_AIM_SMOOTHNESS"])
                }

                "SNIPER" -> {
                    curSettings["SNIPER_AIM_SMOOTHNESS"] = (Math.round(aimSmoothnessSlider.value.toDouble() * 10.0)/10.0).toString()
                    aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings["SNIPER_AIM_SMOOTHNESS"])
                }

                "SHOTGUN" -> {
                    curSettings["SHOTGUN_AIM_SMOOTHNESS"] = (Math.round(aimSmoothnessSlider.value.toDouble() * 10.0)/10.0).toString()
                    aimSmoothnessLabel.setText("Aim Smoothness: " + curSettings["SHOTGUN_AIM_SMOOTHNESS"])
                }
            }
        }
        aimSmoothness.add(aimSmoothnessLabel).spaceRight(6F)
        aimSmoothness.add(aimSmoothnessSlider)

        //Create Perfect_Aim Collapsible Check Box
        Tooltip.Builder("Whether or not to enable perfect aim").target(perfectAimCheckBox).build()
        perfectAimCheckBox.isChecked = curSettings["PISTOL_PERFECT_AIM"]!!.strToBool()
        perfectAimCollapsible.setCollapsed(!curSettings["PISTOL_PERFECT_AIM"]!!.strToBool(), true)

        //Create Perfect_Aim_Fov Slider
        val perfectAimFov = VisTable()
        Tooltip.Builder("The perfect aim field of view").target(perfectAimFov).build()
        perfectAimFovSlider.value = curSettings["PISTOL_PERFECT_AIM_FOV"].toString().toInt().toFloat()
        perfectAimFovSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    curSettings["PISTOL_PERFECT_AIM_FOV"] = perfectAimFovSlider.value.toInt().toString()
                    perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings["PISTOL_PERFECT_AIM_FOV"] + when(curSettings["PISTOL_PERFECT_AIM_FOV"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "RIFLE" -> {
                    curSettings["RIFLE_PERFECT_AIM_FOV"] = perfectAimFovSlider.value.toInt().toString()
                    perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings["RIFLE_PERFECT_AIM_FOV"] + when(curSettings["RIFLE_PERFECT_AIM_FOV"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SMG" -> {
                    curSettings["SMG_PERFECT_AIM_FOV"] = perfectAimFovSlider.value.toInt().toString()
                    perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings["SMG_PERFECT_AIM_FOV"] + when(curSettings["SMG_PERFECT_AIM_FOV"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SNIPER" -> {
                    curSettings["SNIPER_PERFECT_AIM_FOV"] = perfectAimFovSlider.value.toInt().toString()
                    perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings["SNIPER_PERFECT_AIM_FOV"] + when(curSettings["SNIPER_PERFECT_AIM_FOV"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SHOTGUN" -> {
                    curSettings["SHOTGUN_PERFECT_AIM_FOV"] = perfectAimFovSlider.value.toInt().toString()
                    perfectAimFovLabel.setText("Perfect Aim Fov: " + curSettings["SHOTGUN_PERFECT_AIM_FOV"] + when(curSettings["SHOTGUN_PERFECT_AIM_FOV"].toString().length) {3->"  " 2->"    " else ->"      "})
                }
            }
        }
        perfectAimFov.add(perfectAimFovLabel)
        perfectAimFov.add(perfectAimFovSlider)
        //End Perfect_Aim_Fov Slider

        //Create Perfect_Aim_Chance Slider
        val perfectAimChance = VisTable()
        Tooltip.Builder("The perfect aim chance (per calculation)").target(perfectAimChance).build()
        perfectAimChanceSlider.value = curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().toInt().toFloat()
        perfectAimChanceSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    curSettings["PISTOL_PERFECT_AIM_CHANCE"] = perfectAimChanceSlider.value.toInt().toString()
                    perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings["PISTOL_PERFECT_AIM_CHANCE"] + when(curSettings["PISTOL_PERFECT_AIM_CHANCE"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "RIFLE" -> {
                    curSettings["RIFLE_PERFECT_AIM_CHANCE"] = perfectAimChanceSlider.value.toInt().toString()
                    perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings["RIFLE_PERFECT_AIM_CHANCE"] + when(curSettings["RIFLE_PERFECT_AIM_CHANCE"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SMG" -> {
                    curSettings["SMG_PERFECT_AIM_CHANCE"] = perfectAimChanceSlider.value.toInt().toString()
                    perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings["SMG_PERFECT_AIM_CHANCE"] + when(curSettings["SMG_PERFECT_AIM_CHANCE"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SNIPER" -> {
                    curSettings["SNIPER_PERFECT_AIM_CHANCE"] = perfectAimChanceSlider.value.toInt().toString()
                    perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings["SNIPER_PERFECT_AIM_CHANCE"] + when(curSettings["SNIPER_PERFECT_AIM_CHANCE"].toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SHOTGUN" -> {
                    curSettings["SHOTGUN_PERFECT_AIM_CHANCE"] = perfectAimChanceSlider.value.toInt().toString()
                    perfectAimChanceLabel.setText("Perfect Aim Chance: " + curSettings["SHOTGUN_PERFECT_AIM_CHANCE"] + when(curSettings["SHOTGUN_PERFECT_AIM_CHANCE"].toString().length) {3->"  " 2->"    " else ->"      "})
                }
            }
        }

        perfectAimChance.add(perfectAimChanceLabel)
        perfectAimChance.add(perfectAimChanceSlider)
        //End Perfect_Aim_Chance Slider

        perfectAimTable.add(perfectAimFov).row()
        perfectAimTable.add(perfectAimChance).row()

        perfectAimCheckBox.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    curSettings["PISTOL_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
                }

                "RIFLE" -> {
                    curSettings["RIFLE_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
                }

                "SMG" -> {
                    curSettings["SMG_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
                }

                "SNIPER" -> {
                    curSettings["SNIPER_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
                }

                "SHOTGUN" -> {
                    curSettings["SHOTGUN_PERFECT_AIM"] = perfectAimCheckBox.isChecked.boolToStr()
                }
            }
            perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
        }
        //End Perfect_Aim Collapsible Check Box

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Start Weapon Override/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////Weapon Selection

//        //Create Category Selector
//        val overrideCategorySelection = VisTable()
//        Tooltip.Builder("The weapon category settings to edit").target(overrideCategorySelection).build()
//        val overrideCategorySelectLabel = VisLabel("Weapon Category: ")
//        overrideCategorySelectionBox.setItems("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
//        overrideCategorySelectionBox.selected = "PISTOL"
//        overrideCategorySelected = overrideCategorySelectionBox.selected
//        overrideCategorySelection.add(overrideCategorySelectLabel).top().spaceRight(6F)
//        overrideCategorySelection.add(overrideCategorySelectionBox)
//
//        overrideCategorySelectionBox.changed { _, _ ->
//            overrideCategorySelected = overrideCategorySelectionBox.selected
//
//            when (overrideCategorySelectionBox.selected) //weaponCategorySelectionBox.items.add results in an error, can't look through enum list and check weapon type
//            {
//                "PISTOL" -> { weaponCategorySelectionBox.clearItems(); weaponCategorySelectionBox.setItems("DESERT_EAGLE", "DUAL_BERRETA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250") }
//                "SMG" -> { weaponCategorySelectionBox.clearItems(); weaponCategorySelectionBox.setItems("MAC10", "P90", "MP5", "UMP45", "MP7", "MP9", "PP_BIZON") }
//                "RIFLE" -> { weaponCategorySelectionBox.clearItems(); weaponCategorySelectionBox.setItems("AK47", "AUG", "FAMAS", "SG553", "GALIL", "M4A4", "M4A1S", "NEGEV", "M249") }
//                "SNIPER" -> { weaponCategorySelectionBox.clearItems(); weaponCategorySelectionBox.setItems("AWP", "G3SG1", "SCAR20", "SSG08") }
//                "SHOTGUN" -> { weaponCategorySelectionBox.clearItems(); weaponCategorySelectionBox.setItems("XM1014", "MAG7", "SAWED_OFF", "NOVA") }
//            }
//
//            weaponCategorySelectionBox.selected = weaponCategorySelectionBox.items[0]
//            UIUpdate()
//            true
//        }
//
//        //Create Weapon Selector
//        val weaponCategorySelection = VisTable()
//        Tooltip.Builder("The weapon category settings to edit").target(weaponCategorySelection).build()
//        val weaponCategorySelectLabel = VisLabel("Weapon Category: ")
//
//        weaponCategorySelectionBox.setItems("DESERT_EAGLE", "DUAL_BERRETA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250") //First selection is pistol
//
//        weaponCategorySelected = weaponCategorySelectionBox.selected
//        weaponCategorySelection.add(weaponCategorySelectLabel).top().spaceRight(6F)
//        weaponCategorySelection.add(weaponCategorySelectionBox)
//
//        weaponCategorySelectionBox.changed { _, _ ->
//            if (!weaponCategorySelectionBox.selected.isNullOrEmpty())
//            {
//                weaponCategorySelected = weaponCategorySelectionBox.selected
//                curOverrideWep = engine.eval(weaponCategorySelectionBox.selected) as kotlin.DoubleArray
//            }
//            UIUpdate()
//            true
//        }
//
//        //               0      1                2              3              4                5                6         7        8          9               10              11           12               13                  14
//        //Array Format: [WepID, Enable Override, Factor Recoil, Enable Flat Aim, Enable Path Aim, Aim Bone, Aim Fov, Aim Speed, Aim Smoothness, Aim Strictness, Perfect Aim, Perfect Aim FOV, Perfect Aim Chance, Aim Assist Mode, Aim Assist Strictness]
//
//
//
//        //Create Enable Override
//        Tooltip.Builder("Whether or not to override aim when this gun is selected").target(overrideEnableOverride).build()
//        overrideEnableOverride.isChecked = curOverrideWep[1].toBool()
//        overrideEnableOverride.changed { _, _ ->
//            curOverrideWep[1] = overrideEnableOverride.isChecked.toDouble()
//            UIUpdate()
//            true
//        }
//
//        //Create Factor Recoil
//        Tooltip.Builder("Whether or not to factor in recoil when aiming").target(overrideEnableFactorRecoil).build()
//        overrideEnableFactorRecoil.isChecked = curOverrideWep[2].toBool()
//        overrideEnableFactorRecoil.changed { _, _ ->
//            curOverrideWep[2] = overrideEnableFactorRecoil.isChecked.toDouble()
//            UIUpdate()
//            true
//        }
//
//        //Create Enable_Flat_Aim Toggle
//        Tooltip.Builder("Whether or not to enable flat aim").target(overrideEnableFlatAim).build()
//        overrideEnableFlatAim.isChecked = curOverrideWep[3].toBool()
//        overrideEnableFlatAim.changed { _, _ ->
//            curOverrideWep[3] = overrideEnableFlatAim.isChecked.toDouble()
//            if (curOverrideWep[3].toBool() && curOverrideWep[4].toBool())
//            {
//                curOverrideWep[4] = 0.0
//            }
//            UIUpdate()
//            true
//        }
//
//        //Create Enable_Path_Aim Toggle
//        Tooltip.Builder("Whether or not to enable path aim").target(overrideEnablePathAim).build()
//        overrideEnablePathAim.isChecked = curOverrideWep[4].toBool()
//        overrideEnablePathAim.changed { _, _ ->
//            curOverrideWep[4] = overrideEnablePathAim.isChecked.toDouble()
//            if (curOverrideWep[4].toBool() && curOverrideWep[3].toBool())
//            {
//                curOverrideWep[3] = 0.0
//            }
//            UIUpdate()
//            true
//        }
//
//        //Create Aim_Bone Selector
//        val overrideAimBone = VisTable()
//        Tooltip.Builder("The default aim bone to aim at").target(overrideAimBone).build()
//        val overrideAimBoneLabel = VisLabel("Aim Bone: ")
//        overrideAimBoneBox.setItems("HEAD_BONE", "BODY_BONE")
//        overrideAimBoneBox.selected = if (curOverrideWep[5] == HEAD_BONE.toDouble()) "HEAD_BONE" else "BODY_BONE"
//        overrideAimBone.add(overrideAimBoneLabel).top().spaceRight(6F)
//        overrideAimBone.add(overrideAimBoneBox)
//
//        overrideAimBoneBox.changed { _, _ ->
//            var overrideSetBone = HEAD_BONE
//
//            if (overrideAimBoneBox.selected.toString() == "HEAD_BONE") {
//                overrideSetBone = HEAD_BONE
//            }
//            else if (overrideAimBoneBox.selected.toString() == "BODY_BONE") {
//                overrideSetBone = BODY_BONE
//            }
//            curOverrideWep[5] = overrideSetBone.toDouble()
//            true
//        }
//
//        //Create Aim_Fov Slider
//        val overrideAimFov = VisTable()
//        Tooltip.Builder("The aim field of view").target(overrideAimFov).build()
//        overrideAimFovSlider.value = curOverrideWep[6].toFloat()
//        overrideAimFovSlider.changed { _, _ ->
//            curOverrideWep[6] = overrideAimFovSlider.value.toInt().toDouble()
//            overrideAimFovLabel.setText("Aim Fov: " + curOverrideWep[6].toInt() + when(curOverrideWep[6].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
//            true
//        }
//        overrideAimFov.add(overrideAimFovLabel)
//        overrideAimFov.add(overrideAimFovSlider)
//
//        //Create Aim_Speed Slider
//        val overrideAimSpeed = VisTable()
//        Tooltip.Builder("The aim speed delay in milliseconds").target(overrideAimSpeed).build()
//        overrideAimSpeedSlider.value = curOverrideWep[7].toFloat()
//        overrideAimSpeedSlider.changed { _, _ ->
//            curOverrideWep[7] = overrideAimSpeedSlider.value.toInt().toDouble()
//            overrideAimSpeedLabel.setText("Aim Speed: " + curOverrideWep[7].toInt() + when(curOverrideWep[7].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
//        }
//        overrideAimSpeed.add(overrideAimSpeedLabel)
//        overrideAimSpeed.add(overrideAimSpeedSlider)
//
//        //Create Aim_Smoothness Slider
//        val overrideAimSmoothness = VisTable()
//        Tooltip.Builder("The smoothness of the aimbot (path aim only)").target(overrideAimSmoothness).build()
//        overrideAimSmoothnessSlider.value = curOverrideWep[8].toFloat()
//        overrideAimSmoothnessSlider.changed { _, _ ->
//
//                    curOverrideWep[8] = Math.round(overrideAimSmoothnessSlider.value.toDouble() * 10.0)/10.0
//                    overrideAimSmoothnessLabel.setText("Aim Smoothness: " + curOverrideWep[8])
//        }
//        overrideAimSmoothness.add(overrideAimSmoothnessLabel).spaceRight(6F)
//        overrideAimSmoothness.add(overrideAimSmoothnessSlider)
//
//        //Create Perfect_Aim_Fov Slider
//        val overridePerfectAimFov = VisTable()
//        Tooltip.Builder("The perfect aim field of view").target(overridePerfectAimFov).build()
//        overridePerfectAimFovSlider.value = curOverrideWep[11].toFloat()
//        overridePerfectAimFovSlider.changed { _, _ ->
//            curOverrideWep[11] = overridePerfectAimFovSlider.value.toDouble()
//            overridePerfectAimFovLabel.setText("Perfect Aim Fov: " + curOverrideWep[11].toInt() + when(curOverrideWep[11].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
//        }
//        overridePerfectAimFov.add(overridePerfectAimFovLabel)
//        overridePerfectAimFov.add(overridePerfectAimFovSlider)
//        //End Perfect_Aim_Fov Slider
//
//        //Create Perfect_Aim_Chance Slider
//        val overridePerfectAimChance = VisTable()
//        Tooltip.Builder("The perfect aim chance (per calculation)").target(overridePerfectAimChance).build()
//        overridePerfectAimChanceSlider.value = curOverrideWep[12].toFloat()
//        overridePerfectAimChanceSlider.changed { _, _ ->
//            curOverrideWep[12] = overridePerfectAimChanceSlider.value.toDouble()
//            overridePerfectAimChanceLabel.setText("Perfect Aim Chance: " + curOverrideWep[12].toInt() + when(curOverrideWep[12].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
//        }
//
//        overridePerfectAimChance.add(overridePerfectAimChanceLabel)
//        overridePerfectAimChance.add(overridePerfectAimChanceSlider)
//        //End Perfect_Aim_Chance Slider
//
//        overridePerfectAimTable.add(overridePerfectAimFov).row()
//        overridePerfectAimTable.add(overridePerfectAimChance).row()
//        //End Perfect_Aim Collapsible Check Box
//
//        //Create Aim_Assist_Mode Collapsible Check Box
//        Tooltip.Builder("Whether or not to enable aim assistance").target(overrideAimAssistCheckBox).build()
//        overrideAimAssistCheckBox.isChecked = curOverrideWep[13].toBool()
//        overrideAimAssistCollapsible.setCollapsed(!overrideAimAssistCollapsible.isCollapsed, true)
//
//        //Create Aim_Assist_Strictness Slider
//        val overrideAimAssistStrictness = VisTable()
//        Tooltip.Builder("How close your crosshair is to the aim bone to determine whether to stop aiming").target(overrideAimAssistStrictness).build()
//        overrideAimAssistStrictnessSlider.value = curOverrideWep[14].toFloat()
//        overrideAimAssistStrictnessSlider.changed { _, _ ->
//            curOverrideWep[14] = overrideAimAssistStrictnessSlider.value.toDouble()
//            overrideAimAssistStrictnessLabel.setText("Aim Assist Strictness: " + curOverrideWep[14].toInt() + when(curOverrideWep[14].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
//        }
//
//        overrideAimAssistStrictness.add(overrideAimAssistStrictnessLabel)
//        overrideAimAssistStrictness.add(overrideAimAssistStrictnessSlider).width(125F)
//        //End Aim_Assist_Strictness Slider
//
//        overrideAimAssistTable.add(overrideAimAssistStrictness)
//
//        overrideAimAssistCheckBox.changed { _, _ ->
//            curOverrideWep[13] = overrideAimAssistCheckBox.isChecked.toDouble()
//            overrideAimAssistCollapsible.setCollapsed(!overrideAimAssistCollapsible.isCollapsed, true)
//            true
//        }
//        //End Aim_Assist_Mode Collapsible Check Box



        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //Add all items to label for tabbed pane content
        table.add(activateFromFireKey).row()
        table.add(teammatesAreEnemies).row()
        table.add(automaticWeaponsCheckBox).row()
        table.add(automaticWeaponsCollapsible).row()
        table.add(forceAimKey).row()

        table.addSeparator()

        table.add(categorySelection).row()
        table.add(enableFactorRecoil).row()
        table.add(enableFlatAim).row()
        table.add(enablePathAim).row()
        table.add(aimBone).row()
        table.add(aimFov).row()
        table.add(aimSpeed).row()
        table.add(aimSmoothness).row()
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

    fun Double.toBool() = this == 1.0
}