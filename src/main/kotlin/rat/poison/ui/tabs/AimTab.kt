package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.App
import rat.poison.App.menuStage
import rat.poison.engine
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.settings.*
import rat.poison.ui.*

//Need to change path/aim to a drop down

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(true)

    var categorySelected = ""
    var overrideCategorySelected = ""
    var weaponCategorySelected = ""

    //Init labels/sliders/boxes that show values here
    val activateFromFireKey = VisCheckBox("Activate From Fire Key") //Activate_From_Fire_Key
    val teammatesAreEnemies = VisCheckBox("Teammates Are Enemies") //Teammates_Are_Enemies
    val forceAimKeyField = VisValidatableTextField(Validators.FLOATS) //Force_Aim_Key

    //Automatic Weapons Collapsible
    val automaticWeaponsCheckBox = VisCheckBox("Enable Automatic Weapons")
    private val automaticWeaponsTable = VisTable()
    val automaticWeaponsCollapsible = CollapsibleWidget(automaticWeaponsTable)
    val maxPunchCheckLabel = VisLabel("Max Punch Check: " + MAX_PUNCH_CHECK.toString() + when(MAX_PUNCH_CHECK.toString().length) {3->"" 2->"  " else ->"    "}) //Max_Punch_Check
    val maxPunchCheckSlider = VisSlider(1F, 32F, 1F, false) //Max_Punch_Check

    private val categorySelectionBox = VisSelectBox<String>() //Category
    val enableFactorRecoil = VisCheckBox("Factor Recoil") //Factor Recoil
    val enableFlatAim = VisCheckBox("Flat Aim") //Enable_Flat_Aim
    val enablePathAim = VisCheckBox("Path Aim") //Enable_Path_Aim
    val aimBoneBox = VisSelectBox<String>() //Aim_Bone
    val aimFovLabel = VisLabel("Aim Fov: " + PISTOL_AIM_FOV.toString() + when(PISTOL_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Fov
    val aimFovSlider = VisSlider(1F, 360F, 2F, false) //Aim_Fov
    val aimSpeedLabel = VisLabel("Aim Speed: " + PISTOL_AIM_SPEED.toString() + when(PISTOL_AIM_SPEED.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Speed_Min
    val aimSpeedSlider = VisSlider(1F, 100F, 1F, false) //Aim_Speed_Min
    val aimSmoothnessLabel = VisLabel("Aim Smoothness: $PISTOL_AIM_SMOOTHNESS") //Aim_Smoothness
    val aimSmoothnessSlider = VisSlider(1F, 10F, 0.1F, false) //Aim_Smoothness
    val aimStrictnessLabel = VisLabel("Aim Strictness: $PISTOL_AIM_STRICTNESS") //Aim_Strictness
    val aimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false) //Aim_Strictness

    //Perfect Aim Collapsible
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim") //Perfect_Aim
    private val perfectAimTable = VisTable() //Perfect_Aim_Collapsible Table
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable) //Perfect_Aim_Collapsible
    val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + PISTOL_PERFECT_AIM_FOV.toString() + when(PISTOL_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Fov
    val perfectAimFovSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Fov
    val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + PISTOL_PERFECT_AIM_CHANCE.toString() + when(PISTOL_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Chance
    val perfectAimChanceSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Chance

    //Aim Assist Collapsible
    val aimAssistCheckBox = VisCheckBox("Enable Aim Assist") //Aim_Assist
    private val aimAssistTable = VisTable() //Aim_Assist_Collapsible Table
    val aimAssistCollapsible = CollapsibleWidget(aimAssistTable) //Aim_Assist_Collapsible
    val aimAssistStrictnessLabel = VisLabel("Aim Assist Strictness: " + PISTOL_AIM_ASSIST_STRICTNESS.toString() + when(PISTOL_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Assist_Strictness
    val aimAssistStrictnessSlider = VisSlider(1F, 100F, 1F, false) //Aim_Assist_Strictness

    //Override Weapons Panel
    val overrideTable = VisTable(true) //Container for all items

    ////Custom Overrides
    val overrideEnableOverride = VisCheckBox("Enable Override") //Enable Override
    val overrideEnableFactorRecoil = VisCheckBox("Factor Recoil") //Factor Recoil
    val overrideEnableFlatAim = VisCheckBox("Flat Aim") //Enable_Flat_Aim
    val overrideEnablePathAim = VisCheckBox("Path Aim") //Enable_Path_Aim
    val overrideAimBoneBox = VisSelectBox<String>() //Aim_Bone
    val overrideAimFovLabel = VisLabel("Aim Fov: " + PISTOL_AIM_FOV.toString() + when(PISTOL_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Fov
    val overrideAimFovSlider = VisSlider(1F, 360F, 2F, false) //Aim_Fov
    val overrideAimSpeedLabel = VisLabel("Aim Speed: " + PISTOL_AIM_SPEED.toString() + when(PISTOL_AIM_SPEED.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Speed_Min
    val overrideAimSpeedSlider = VisSlider(1F, 100F, 1F, false) //Aim_Speed_Min
    val overrideAimSmoothnessLabel = VisLabel("Aim Smoothness: $PISTOL_AIM_SMOOTHNESS") //Aim_Smoothness
    val overrideAimSmoothnessSlider = VisSlider(1F, 10F, 0.1F, false) //Aim_Smoothness
    val overrideAimStrictnessLabel = VisLabel("Aim Strictness: $PISTOL_AIM_STRICTNESS") //Aim_Strictness
    val overrideAimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false) //Aim_Strictness

    //Perfect Aim Collapsible
    val overridePerfectAimCheckBox = VisCheckBox("Enable Perfect Aim") //Perfect_Aim
    private val overridePerfectAimTable = VisTable() //Perfect_Aim_Collapsible Table
    val overridePerfectAimCollapsible = CollapsibleWidget(overridePerfectAimTable) //Perfect_Aim_Collapsible
    val overridePerfectAimFovLabel = VisLabel("Perfect Aim Fov: " + PISTOL_PERFECT_AIM_FOV.toString() + when(PISTOL_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Fov
    val overridePerfectAimFovSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Fov
    val overridePerfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + PISTOL_PERFECT_AIM_CHANCE.toString() + when(PISTOL_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Chance
    val overridePerfectAimChanceSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Chance

    //Aim Assist Collapsible
    val overrideAimAssistCheckBox = VisCheckBox("Enable Aim Assist") //Aim_Assist
    private val overrideAimAssistTable = VisTable() //Aim_Assist_Collapsible Table
    val overrideAimAssistCollapsible = CollapsibleWidget(overrideAimAssistTable) //Aim_Assist_Collapsible
    val overrideAimAssistStrictnessLabel = VisLabel("Aim Assist Strictness: " + PISTOL_AIM_ASSIST_STRICTNESS.toString() + when(PISTOL_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Assist_Strictness
    val overrideAimAssistStrictnessSlider = VisSlider(1F, 100F, 1F, false) //Aim_Assist_Strictness

    //Desert Eagle is the first item in pistols //proof of working, delete and add function to update info for us
    var curOverrideWep : kotlin.DoubleArray = engine.eval("DESERT_EAGLE") as kotlin.DoubleArray

    private val overrideCategorySelectionBox = VisSelectBox<String>() //Category

    private val weaponCategorySelectionBox = VisSelectBox<String>() //Category

    init {
        val dialog = Dialogs.showOKDialog(App.menuStage, "Warning", "If you have any problems submit an issue on github\nIf you are crashing or have an error message in the cmd, include the error message if there is one,\nsettings that were enabled, and when it happened (such as randomly, when joining a game, on round end, etc)\n\nWARNING: This update has not been tested extensively, I assume many issues will pop up, please submit an issue on github\nwith information on the issue, the settings used (create a cfg file and put it in a pastebin), and evidence (such as a picture/video)\nor a way to easily replicate the issue.\nThe custom per weapon settings are available in the GunAimOverride.kts file, in arrays, the gun is the variable name, with the array\ninformation at the top.\nGitHub: https://github.com/astupidrat/ratpoison")
        dialog.setPosition(gameWidth/2F-dialog.width/2F, gameHeight.toFloat())
        menuStage.addActor(dialog)

        //Create Activate_From_Fire_Key Toggle
        Tooltip.Builder("Activate aim if pressing predefined fire key").target(activateFromFireKey).build()
        if (ACTIVATE_FROM_FIRE_KEY) activateFromFireKey.toggle()
        activateFromFireKey.changed { _, _ ->
            ACTIVATE_FROM_FIRE_KEY = activateFromFireKey.isChecked
            true
        }

        //Create Teammates_Are_Enemies Toggle
        Tooltip.Builder("Teammates will be treated as enemies").target(teammatesAreEnemies).build()
        if (TEAMMATES_ARE_ENEMIES) teammatesAreEnemies.toggle()
        teammatesAreEnemies.changed { _, _ ->
            TEAMMATES_ARE_ENEMIES = teammatesAreEnemies.isChecked
            true
        }

        //Create Automatic_Weapons Collapsible Check Box
        Tooltip.Builder("Non-automatic weapons will auto shoot when there is no punch and the fire key is pressed").target(automaticWeaponsCheckBox).build()
        automaticWeaponsCheckBox.isChecked = AUTOMATIC_WEAPONS
        automaticWeaponsCollapsible.isCollapsed = !AUTOMATIC_WEAPONS

        //Create Max_Punch_Check Slider
        val maxPunchCheck = VisTable()
        Tooltip.Builder("The ms delay between checking punch to fire a shot using AUTOMATIC_WEAPONS, the lower the less accurate but faster firing").target(maxPunchCheck).build()
        maxPunchCheckSlider.value = MAX_PUNCH_CHECK.toFloat()
        maxPunchCheckSlider.changed { _, _ ->
            MAX_PUNCH_CHECK = maxPunchCheckSlider.value.toInt()
            maxPunchCheckLabel.setText("Max Punch Check: " + MAX_PUNCH_CHECK.toString() + when(MAX_PUNCH_CHECK.toString().length) {3->"" 2->"  " else ->"    "})
        }
        maxPunchCheck.add(maxPunchCheckLabel)
        maxPunchCheck.add(maxPunchCheckSlider)

        //End Aim_Assist_Strictness Slider

        automaticWeaponsTable.add(maxPunchCheck)

        automaticWeaponsCheckBox.changed { _, _ ->
            AUTOMATIC_WEAPONS = automaticWeaponsCheckBox.isChecked
            automaticWeaponsCollapsible.setCollapsed(!automaticWeaponsCollapsible.isCollapsed, true)
        }
        //End Automatic_Weapons Collapsible Check Box

        //Create Force_Aim_Key Input
        val forceAimKey = VisTable()
        Tooltip.Builder("The key to force lock onto any enemy inside aim fov").target(forceAimKey).build()
        val forceAimKeyLabel = VisLabel("Force Aim Key: ")
        forceAimKeyField.text = FORCE_AIM_KEY.toString()
        forceAimKey.changed { _, _ ->
            if (forceAimKeyField.text.toIntOrNull() != null) {
                FORCE_AIM_KEY = forceAimKeyField.text.toInt()
            }
        }
        forceAimKey.add(forceAimKeyLabel)
        forceAimKey.add(forceAimKeyField).spaceRight(6F).width(40F)
        forceAimKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create Factor Recoil
        Tooltip.Builder("Whether or not to factor in recoil when aiming").target(enableFactorRecoil).build()
        enableFactorRecoil.isChecked = PISTOL_ENABLE_PATH_AIM
        enableFactorRecoil.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_FACTOR_RECOIL = enableFactorRecoil.isChecked
                }

                "RIFLE" -> {
                    RIFLE_FACTOR_RECOIL = enableFactorRecoil.isChecked
                }

                "SMG" -> {
                    SMG_FACTOR_RECOIL = enableFactorRecoil.isChecked
                }

                "SNIPER" -> {
                    SNIPER_FACTOR_RECOIL = enableFactorRecoil.isChecked
                }

                "SHOTGUN" -> {
                    SHOTGUN_FACTOR_RECOIL = enableFactorRecoil.isChecked
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
        enableFlatAim.isChecked = PISTOL_ENABLE_FLAT_AIM
        enableFlatAim.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_ENABLE_FLAT_AIM = enableFlatAim.isChecked
                    if (PISTOL_ENABLE_FLAT_AIM) {
                        PISTOL_ENABLE_PATH_AIM = false
                    }
                }
                
                "RIFLE" -> {
                    RIFLE_ENABLE_FLAT_AIM = enableFlatAim.isChecked
                    if (RIFLE_ENABLE_FLAT_AIM) {
                        RIFLE_ENABLE_PATH_AIM = false
                    }
                }
                
                "SMG" -> {
                    SMG_ENABLE_FLAT_AIM = enableFlatAim.isChecked
                    if (SMG_ENABLE_PATH_AIM) {
                        SMG_ENABLE_PATH_AIM = false
                    }
                }
                
                "SNIPER" -> {
                    SNIPER_ENABLE_FLAT_AIM = enableFlatAim.isChecked
                    if (SNIPER_ENABLE_PATH_AIM) {
                        SNIPER_ENABLE_PATH_AIM = false
                    }
                }
                
                "SHOTGUN" -> {
                    SHOTGUN_ENABLE_FLAT_AIM = enableFlatAim.isChecked
                    if (SHOTGUN_ENABLE_FLAT_AIM) {
                        SHOTGUN_ENABLE_PATH_AIM = false
                    }
                }
            }
            UIUpdate()
            true
        }

        //Create Enable_Path_Aim Toggle
        Tooltip.Builder("Whether or not to enable path aim").target(enablePathAim).build()
        enablePathAim.isChecked = PISTOL_ENABLE_PATH_AIM
        enablePathAim.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_ENABLE_PATH_AIM = enablePathAim.isChecked
                    if (PISTOL_ENABLE_PATH_AIM) {
                        PISTOL_ENABLE_FLAT_AIM = false
                    }
                }

                "RIFLE" -> {
                    RIFLE_ENABLE_PATH_AIM = enablePathAim.isChecked
                    if (RIFLE_ENABLE_PATH_AIM) {
                        RIFLE_ENABLE_FLAT_AIM = false
                    }
                }

                "SMG" -> {
                    SMG_ENABLE_PATH_AIM = enablePathAim.isChecked
                    if (SMG_ENABLE_PATH_AIM) {
                        SMG_ENABLE_FLAT_AIM = false
                    }
                }

                "SNIPER" -> {
                    SNIPER_ENABLE_PATH_AIM = enablePathAim.isChecked
                    if (SNIPER_ENABLE_PATH_AIM) {
                        SNIPER_ENABLE_FLAT_AIM = false
                    }
                }

                "SHOTGUN" -> {
                    SHOTGUN_ENABLE_PATH_AIM = enablePathAim.isChecked
                    if (SHOTGUN_ENABLE_PATH_AIM) {
                        SHOTGUN_ENABLE_FLAT_AIM = false
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
        aimBoneBox.selected = if (PISTOL_AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
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
                    PISTOL_AIM_BONE = setBone
                }

                "RIFLE" -> {
                    RIFLE_AIM_BONE = setBone
                }

                "SMG" -> {
                    SMG_AIM_BONE = setBone
                }

                "SNIPER" -> {
                    SNIPER_AIM_BONE = setBone
                }

                "SHOTGUN" -> {
                    SHOTGUN_AIM_BONE = setBone
                }
            }
        }

        //Create Aim_Fov Slider
        val aimFov = VisTable()
        Tooltip.Builder("The aim field of view").target(aimFov).build()
        aimFovSlider.value = PISTOL_AIM_FOV.toFloat()
        aimFovSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_AIM_FOV = aimFovSlider.value.toInt()
                    aimFovLabel.setText("Aim Fov: $PISTOL_AIM_FOV" + when(PISTOL_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "RIFLE" -> {
                    RIFLE_AIM_FOV = aimFovSlider.value.toInt()
                    aimFovLabel.setText("Aim Fov: $RIFLE_AIM_FOV" + when(RIFLE_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SMG" -> {
                    SMG_AIM_FOV = aimFovSlider.value.toInt()
                    aimFovLabel.setText("Aim Fov: $SMG_AIM_FOV" + when(SMG_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SNIPER" -> {
                    SNIPER_AIM_FOV = aimFovSlider.value.toInt()
                    aimFovLabel.setText("Aim Fov: $SNIPER_AIM_FOV" + when(SNIPER_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SHOTGUN" -> {
                    SHOTGUN_AIM_FOV = aimFovSlider.value.toInt()
                    aimFovLabel.setText("Aim Fov: $SHOTGUN_AIM_FOV" + when(SHOTGUN_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
                }
            }
        }
        aimFov.add(aimFovLabel)
        aimFov.add(aimFovSlider)

        //Create Aim_Speed Slider
        val aimSpeed = VisTable()
        Tooltip.Builder("The aim speed delay in milliseconds").target(aimSpeed).build()
        aimSpeedSlider.value = PISTOL_AIM_SPEED.toFloat()
        aimSpeedSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_AIM_SPEED = aimSpeedSlider.value.toInt()
                    aimSpeedLabel.setText("Aim Speed: " + PISTOL_AIM_SPEED.toString() + when(PISTOL_AIM_SPEED.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "RIFLE" -> {
                    RIFLE_AIM_SPEED = aimSpeedSlider.value.toInt()
                    aimSpeedLabel.setText("Aim Speed: " + RIFLE_AIM_SPEED.toString() + when(RIFLE_AIM_SPEED.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SMG" -> {
                    SMG_AIM_SPEED = aimSpeedSlider.value.toInt()
                    aimSpeedLabel.setText("Aim Speed: " + SMG_AIM_SPEED.toString() + when(SMG_AIM_SPEED.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SNIPER" -> {
                    SNIPER_AIM_SPEED = aimSpeedSlider.value.toInt()
                    aimSpeedLabel.setText("Aim Speed: " + SNIPER_AIM_SPEED.toString() + when(SNIPER_AIM_SPEED.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SHOTGUN" -> {
                    SHOTGUN_AIM_SPEED = aimSpeedSlider.value.toInt()
                    aimSpeedLabel.setText("Aim Speed: " + SHOTGUN_AIM_SPEED.toString() + when(SHOTGUN_AIM_SPEED.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                else -> {} //When needs an else now for some reason?
            }
        }
        aimSpeed.add(aimSpeedLabel)
        aimSpeed.add(aimSpeedSlider)

        //Create Aim_Strictness Slider
        val aimSmoothness = VisTable()
        Tooltip.Builder("The smoothness of the aimbot (path aim only)").target(aimSmoothness).build()
        aimSmoothnessSlider.value = PISTOL_AIM_SMOOTHNESS.toFloat()
        aimSmoothnessSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_AIM_SMOOTHNESS = Math.round(aimSmoothnessSlider.value.toDouble() * 10.0)/10.0
                    aimSmoothnessLabel.setText("Aim Smoothness: $PISTOL_AIM_SMOOTHNESS")
                }

                "RIFLE" -> {
                    RIFLE_AIM_SMOOTHNESS = Math.round(aimSmoothnessSlider.value.toDouble() * 10.0)/10.0
                    aimSmoothnessLabel.setText("Aim Smoothness: $RIFLE_AIM_SMOOTHNESS")
                }

                "SMG" -> {
                    SMG_AIM_SMOOTHNESS = Math.round(aimSmoothnessSlider.value.toDouble() * 10.0)/10.0
                    aimSmoothnessLabel.setText("Aim Smoothness: $SMG_AIM_SMOOTHNESS")
                }

                "SNIPER" -> {
                    SNIPER_AIM_SMOOTHNESS = Math.round(aimSmoothnessSlider.value.toDouble() * 10.0)/10.0
                    aimSmoothnessLabel.setText("Aim Smoothness: $SNIPER_AIM_SMOOTHNESS")
                }

                "SHOTGUN" -> {
                    SHOTGUN_AIM_SMOOTHNESS = Math.round(aimSmoothnessSlider.value.toDouble() * 10.0)/10.0
                    aimSmoothnessLabel.setText("Aim Smoothness: $SHOTGUN_AIM_SMOOTHNESS")
                }
            }
        }
        aimSmoothness.add(aimSmoothnessLabel).spaceRight(6F)
        aimSmoothness.add(aimSmoothnessSlider)

        //Create Aim_Strictness Slider
        val aimStrictness = VisTable()
        Tooltip.Builder("How close to get to the bone before it stops correcting").target(aimStrictness).build()
        aimStrictnessSlider.value = PISTOL_AIM_STRICTNESS.toFloat()
        aimStrictnessSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0
                    aimStrictnessLabel.setText("Aim Strictness: $PISTOL_AIM_STRICTNESS")
                }

                "RIFLE" -> {
                    RIFLE_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0
                    aimStrictnessLabel.setText("Aim Strictness: $RIFLE_AIM_STRICTNESS")
                }

                "SMG" -> {
                    SMG_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0
                    aimStrictnessLabel.setText("Aim Strictness: $SMG_AIM_STRICTNESS")
                }

                "SNIPER" -> {
                    SNIPER_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0
                    aimStrictnessLabel.setText("Aim Strictness: $SNIPER_AIM_STRICTNESS")
                }

                "SHOTGUN" -> {
                    SHOTGUN_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0
                    aimStrictnessLabel.setText("Aim Strictness: $SHOTGUN_AIM_STRICTNESS")
                }
            }
        }
        aimStrictness.add(aimStrictnessLabel).spaceRight(6F)
        aimStrictness.add(aimStrictnessSlider)

        //Create Perfect_Aim Collapsible Check Box
        Tooltip.Builder("Whether or not to enable perfect aim").target(perfectAimCheckBox).build()
        perfectAimCheckBox.isChecked = PISTOL_PERFECT_AIM
        perfectAimCollapsible.setCollapsed(!PISTOL_PERFECT_AIM, true)

        //Create Perfect_Aim_Fov Slider
        val perfectAimFov = VisTable()
        Tooltip.Builder("The perfect aim field of view").target(perfectAimFov).build()
        perfectAimFovSlider.value = PISTOL_PERFECT_AIM_FOV.toFloat()
        perfectAimFovSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_PERFECT_AIM_FOV = perfectAimFovSlider.value.toInt()
                    perfectAimFovLabel.setText("Perfect Aim Fov: $PISTOL_PERFECT_AIM_FOV" + when(PISTOL_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "RIFLE" -> {
                    RIFLE_PERFECT_AIM_FOV = perfectAimFovSlider.value.toInt()
                    perfectAimFovLabel.setText("Perfect Aim Fov: $RIFLE_PERFECT_AIM_FOV" + when(RIFLE_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SMG" -> {
                    SMG_PERFECT_AIM_FOV = perfectAimFovSlider.value.toInt()
                    perfectAimFovLabel.setText("Perfect Aim Fov: $SMG_PERFECT_AIM_FOV" + when(SMG_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SNIPER" -> {
                    SNIPER_PERFECT_AIM_FOV = perfectAimFovSlider.value.toInt()
                    perfectAimFovLabel.setText("Perfect Aim Fov: $SNIPER_PERFECT_AIM_FOV" + when(SNIPER_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SHOTGUN" -> {
                    SHOTGUN_PERFECT_AIM_FOV = perfectAimFovSlider.value.toInt()
                    perfectAimFovLabel.setText("Perfect Aim Fov: $SHOTGUN_PERFECT_AIM_FOV" + when(SHOTGUN_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
                }
            }
        }
        perfectAimFov.add(perfectAimFovLabel)
        perfectAimFov.add(perfectAimFovSlider)
        //End Perfect_Aim_Fov Slider

        //Create Perfect_Aim_Chance Slider
        val perfectAimChance = VisTable()
        Tooltip.Builder("The perfect aim chance (per calculation)").target(perfectAimChance).build()
        perfectAimChanceSlider.value = PISTOL_PERFECT_AIM_CHANCE.toFloat()
        perfectAimChanceSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_PERFECT_AIM_CHANCE = perfectAimChanceSlider.value.toInt()
                    perfectAimChanceLabel.setText("Perfect Aim Chance: " + PISTOL_PERFECT_AIM_CHANCE.toString() + when(PISTOL_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "RIFLE" -> {
                    RIFLE_PERFECT_AIM_CHANCE = perfectAimChanceSlider.value.toInt()
                    perfectAimChanceLabel.setText("Perfect Aim Chance: " + RIFLE_PERFECT_AIM_CHANCE.toString() + when(RIFLE_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SMG" -> {
                    SMG_PERFECT_AIM_CHANCE = perfectAimChanceSlider.value.toInt()
                    perfectAimChanceLabel.setText("Perfect Aim Chance: " + SMG_PERFECT_AIM_CHANCE.toString() + when(SMG_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SNIPER" -> {
                    SNIPER_PERFECT_AIM_CHANCE = perfectAimChanceSlider.value.toInt()
                    perfectAimChanceLabel.setText("Perfect Aim Chance: " + SNIPER_PERFECT_AIM_CHANCE.toString() + when(SNIPER_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SHOTGUN" -> {
                    SHOTGUN_PERFECT_AIM_CHANCE = perfectAimChanceSlider.value.toInt()
                    perfectAimChanceLabel.setText("Perfect Aim Chance: " + SHOTGUN_PERFECT_AIM_CHANCE.toString() + when(SHOTGUN_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "})
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
                    PISTOL_PERFECT_AIM = perfectAimCheckBox.isChecked
                    perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
                }

                "RIFLE" -> {
                    RIFLE_PERFECT_AIM = perfectAimCheckBox.isChecked
                    perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
                }

                "SMG" -> {
                    SMG_PERFECT_AIM = perfectAimCheckBox.isChecked
                    perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
                }

                "SNIPER" -> {
                    SNIPER_PERFECT_AIM = perfectAimCheckBox.isChecked
                    perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
                }

                "SHOTGUN" -> {
                    SHOTGUN_PERFECT_AIM = perfectAimCheckBox.isChecked
                    perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
                }
            }
        }
        //End Perfect_Aim Collapsible Check Box

        //Create Aim_Assist_Mode Collapsible Check Box
        Tooltip.Builder("Whether or not to enable aim assistance").target(aimAssistCheckBox).build()
        aimAssistCheckBox.isChecked = PISTOL_AIM_ASSIST_MODE
        aimAssistCollapsible.isCollapsed = !PISTOL_AIM_ASSIST_MODE

        //Create Aim_Assist_Strictness Slider
        val aimAssistStrictness = VisTable()
        Tooltip.Builder("How close your crosshair is to the aim bone to determine whether to stop aiming").target(aimAssistStrictness).build()
        aimAssistStrictnessSlider.value = PISTOL_AIM_ASSIST_STRICTNESS.toFloat()
        aimAssistStrictnessSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_AIM_ASSIST_STRICTNESS = aimAssistStrictnessSlider.value.toInt()
                    aimAssistStrictnessLabel.setText("Aim Assist Strictness: " + PISTOL_AIM_ASSIST_STRICTNESS.toString() + when(PISTOL_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "RIFLE" -> {
                    RIFLE_AIM_ASSIST_STRICTNESS = aimAssistStrictnessSlider.value.toInt()
                    aimAssistStrictnessLabel.setText("Aim Assist Strictness: " + RIFLE_AIM_ASSIST_STRICTNESS.toString() + when(RIFLE_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SMG" -> {
                    SMG_AIM_ASSIST_STRICTNESS = aimAssistStrictnessSlider.value.toInt()
                    aimAssistStrictnessLabel.setText("Aim Assist Strictness: " + SMG_AIM_ASSIST_STRICTNESS.toString() + when(SMG_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SNIPER" -> {
                    SNIPER_AIM_ASSIST_STRICTNESS = aimAssistStrictnessSlider.value.toInt()
                    aimAssistStrictnessLabel.setText("Aim Assist Strictness: " + SNIPER_AIM_ASSIST_STRICTNESS.toString() + when(SNIPER_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "})
                }

                "SHOTGUN" -> {
                    SHOTGUN_AIM_ASSIST_STRICTNESS = aimAssistStrictnessSlider.value.toInt()
                    aimAssistStrictnessLabel.setText("Aim Assist Strictness: " + SHOTGUN_AIM_ASSIST_STRICTNESS.toString() + when(SHOTGUN_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "})
                }
            }
        }

        aimAssistStrictness.add(aimAssistStrictnessLabel)
        aimAssistStrictness.add(aimAssistStrictnessSlider).width(125F)
        //End Aim_Assist_Strictness Slider

        aimAssistTable.add(aimAssistStrictness)

        aimAssistCheckBox.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_AIM_ASSIST_MODE = aimAssistCheckBox.isChecked
                    aimAssistCollapsible.setCollapsed(!aimAssistCollapsible.isCollapsed, true)
                }

                "RIFLE" -> {
                    RIFLE_AIM_ASSIST_MODE = aimAssistCheckBox.isChecked
                    aimAssistCollapsible.setCollapsed(!aimAssistCollapsible.isCollapsed, true)
                }

                "SMG" -> {
                    SMG_AIM_ASSIST_MODE = aimAssistCheckBox.isChecked
                    aimAssistCollapsible.setCollapsed(!aimAssistCollapsible.isCollapsed, true)
                }

                "SNIPER" -> {
                    SNIPER_AIM_ASSIST_MODE = aimAssistCheckBox.isChecked
                    aimAssistCollapsible.setCollapsed(!aimAssistCollapsible.isCollapsed, true)
                }

                "SHOTGUN" -> {
                    SHOTGUN_AIM_ASSIST_MODE = aimAssistCheckBox.isChecked
                    aimAssistCollapsible.setCollapsed(!aimAssistCollapsible.isCollapsed, true)
                }
            }
        }
        //End Aim_Assist_Mode Collapsible Check Box


        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Start Weapon Override/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        /////Weapon Selection
        
        //Create Category Selector
        val overrideCategorySelection = VisTable()
        Tooltip.Builder("The weapon category settings to edit").target(overrideCategorySelection).build()
        val overrideCategorySelectLabel = VisLabel("Weapon Category: ")
        overrideCategorySelectionBox.setItems("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
        overrideCategorySelectionBox.selected = "PISTOL"
        overrideCategorySelected = overrideCategorySelectionBox.selected
        overrideCategorySelection.add(overrideCategorySelectLabel).top().spaceRight(6F)
        overrideCategorySelection.add(overrideCategorySelectionBox)

        overrideCategorySelectionBox.changed { _, _ ->
            overrideCategorySelected = overrideCategorySelectionBox.selected

            when (overrideCategorySelectionBox.selected) //weaponCategorySelectionBox.items.add results in an error, can't look through enum list and check weapon type
            {
                "PISTOL" -> { weaponCategorySelectionBox.clearItems(); weaponCategorySelectionBox.setItems("DESERT_EAGLE", "DUAL_BERRETA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250") }
                "SMG" -> { weaponCategorySelectionBox.clearItems(); weaponCategorySelectionBox.setItems("MAC10", "P90", "MP5", "UMP45", "MP7", "MP9", "PP_BIZON") }
                "RIFLE" -> { weaponCategorySelectionBox.clearItems(); weaponCategorySelectionBox.setItems("AK47", "AUG", "FAMAS", "GALIL", "M4A4", "M4A1S", "NEGEV", "M249") }
                "SNIPER" -> { weaponCategorySelectionBox.clearItems(); weaponCategorySelectionBox.setItems("AWP", "G3SG1", "SCAR20", "SG553", "SSG08") }
                "SHOTGUN" -> { weaponCategorySelectionBox.clearItems(); weaponCategorySelectionBox.setItems("XM1014", "MAG7", "SAWED OFF", "NOVA") }
            }

            weaponCategorySelectionBox.selected = weaponCategorySelectionBox.items[0]
            UIUpdate()
            true
        }

        //Create Weapon Selector
        val weaponCategorySelection = VisTable()
        Tooltip.Builder("The weapon category settings to edit").target(weaponCategorySelection).build()
        val weaponCategorySelectLabel = VisLabel("Weapon Category: ")

        weaponCategorySelectionBox.setItems("DESERT_EAGLE", "DUAL_BERRETA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250") //First selection is pistol

        weaponCategorySelected = weaponCategorySelectionBox.selected
        weaponCategorySelection.add(weaponCategorySelectLabel).top().spaceRight(6F)
        weaponCategorySelection.add(weaponCategorySelectionBox)

        weaponCategorySelectionBox.changed { _, _ ->
            if (!weaponCategorySelectionBox.selected.isNullOrEmpty())
            {
                weaponCategorySelected = weaponCategorySelectionBox.selected
                curOverrideWep = engine.eval(weaponCategorySelectionBox.selected) as kotlin.DoubleArray
            }
            UIUpdate()
            true
        }

        //               0      1                2              3              4                5                6         7        8          9               10              11           12               13                  14
        //Array Format: [WepID, Enable Override, Factor Recoil, Enable Flat Aim, Enable Path Aim, Aim Bone, Aim Fov, Aim Speed, Aim Smoothness, Aim Strictness, Perfect Aim, Perfect Aim FOV, Perfect Aim Chance, Aim Assist Mode, Aim Assist Strictness]



        //Create Enable Override
        Tooltip.Builder("Whether or not to override aim when this gun is selected").target(overrideEnableOverride).build()
        overrideEnableOverride.isChecked = curOverrideWep[1].toBool()
        overrideEnableOverride.changed { _, _ ->
            curOverrideWep[1] = overrideEnableOverride.isChecked.toDouble()
            UIUpdate()
            true
        }

        //Create Factor Recoil
        Tooltip.Builder("Whether or not to factor in recoil when aiming").target(overrideEnableFactorRecoil).build()
        overrideEnableFactorRecoil.isChecked = curOverrideWep[2].toBool()
        overrideEnableFactorRecoil.changed { _, _ ->
            curOverrideWep[2] = overrideEnableFactorRecoil.isChecked.toDouble()
            UIUpdate()
            true
        }

        //Create Enable_Flat_Aim Toggle
        Tooltip.Builder("Whether or not to enable flat aim").target(overrideEnableFlatAim).build()
        overrideEnableFlatAim.isChecked = curOverrideWep[3].toBool()
        overrideEnableFlatAim.changed { _, _ ->
            curOverrideWep[3] = overrideEnableFlatAim.isChecked.toDouble()
            if (curOverrideWep[3].toBool() && curOverrideWep[4].toBool())
            {
                curOverrideWep[4] = 0.0
            }
            UIUpdate()
            true
        }

        //Create Enable_Path_Aim Toggle
        Tooltip.Builder("Whether or not to enable path aim").target(overrideEnablePathAim).build()
        overrideEnablePathAim.isChecked = curOverrideWep[4].toBool()
        overrideEnablePathAim.changed { _, _ ->
            curOverrideWep[4] = overrideEnablePathAim.isChecked.toDouble()
            if (curOverrideWep[4].toBool() && curOverrideWep[3].toBool())
            {
                curOverrideWep[3] = 0.0
            }
            UIUpdate()
            true
        }

        //Create Aim_Bone Selector
        val overrideAimBone = VisTable()
        Tooltip.Builder("The default aim bone to aim at").target(overrideAimBone).build()
        val overrideAimBoneLabel = VisLabel("Aim Bone: ")
        overrideAimBoneBox.setItems("HEAD_BONE", "BODY_BONE")
        overrideAimBoneBox.selected = if (curOverrideWep[5] == HEAD_BONE.toDouble()) "HEAD_BONE" else "BODY_BONE"
        overrideAimBone.add(overrideAimBoneLabel).top().spaceRight(6F)
        overrideAimBone.add(overrideAimBoneBox)

        overrideAimBoneBox.changed { _, _ ->
            var overrideSetBone = HEAD_BONE

            if (overrideAimBoneBox.selected.toString() == "HEAD_BONE") {
                overrideSetBone = HEAD_BONE
            }
            else if (overrideAimBoneBox.selected.toString() == "BODY_BONE") {
                overrideSetBone = BODY_BONE
            }
            curOverrideWep[5] = overrideSetBone.toDouble()
            true
        }

        //Create Aim_Fov Slider
        val overrideAimFov = VisTable()
        Tooltip.Builder("The aim field of view").target(overrideAimFov).build()
        overrideAimFovSlider.value = curOverrideWep[6].toFloat()
        overrideAimFovSlider.changed { _, _ ->
            curOverrideWep[6] = overrideAimFovSlider.value.toInt().toDouble()
            overrideAimFovLabel.setText("Aim Fov: " + curOverrideWep[6].toInt() + when(curOverrideWep[6].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
            true
        }
        overrideAimFov.add(overrideAimFovLabel)
        overrideAimFov.add(overrideAimFovSlider)

        //Create Aim_Speed Slider
        val overrideAimSpeed = VisTable()
        Tooltip.Builder("The aim speed delay in milliseconds").target(overrideAimSpeed).build()
        overrideAimSpeedSlider.value = curOverrideWep[7].toFloat()
        overrideAimSpeedSlider.changed { _, _ ->
            curOverrideWep[7] = overrideAimSpeedSlider.value.toInt().toDouble()
            overrideAimSpeedLabel.setText("Aim Speed: " + curOverrideWep[7].toInt() + when(curOverrideWep[7].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
        }
        overrideAimSpeed.add(overrideAimSpeedLabel)
        overrideAimSpeed.add(overrideAimSpeedSlider)

        //Create Aim_Smoothness Slider
        val overrideAimSmoothness = VisTable()
        Tooltip.Builder("The smoothness of the aimbot (path aim only)").target(overrideAimSmoothness).build()
        overrideAimSmoothnessSlider.value = curOverrideWep[8].toFloat()
        overrideAimSmoothnessSlider.changed { _, _ ->

                    curOverrideWep[8] = Math.round(overrideAimSmoothnessSlider.value.toDouble() * 10.0)/10.0
                    overrideAimSmoothnessLabel.setText("Aim Smoothness: " + curOverrideWep[8])
        }
        overrideAimSmoothness.add(overrideAimSmoothnessLabel).spaceRight(6F)
        overrideAimSmoothness.add(overrideAimSmoothnessSlider)

        //Create Aim_Strictness Slider
        val overrideAimStrictness = VisTable()
        Tooltip.Builder("How close to get to the bone before it stops correcting").target(overrideAimStrictness).build()
        overrideAimStrictnessSlider.value = curOverrideWep[9].toFloat()
        overrideAimStrictnessSlider.changed { _, _ ->
            curOverrideWep[9] = Math.round(overrideAimStrictnessSlider.value.toDouble() * 10.0)/10.0
            overrideAimStrictnessLabel.setText("Aim Strictness: " + curOverrideWep[9])
        }
        overrideAimStrictness.add(overrideAimStrictnessLabel).spaceRight(6F)
        overrideAimStrictness.add(overrideAimStrictnessSlider)

        //Create Perfect_Aim Collapsible Check Box
        Tooltip.Builder("Whether or not to enable perfect aim").target(overridePerfectAimCheckBox).build()
        overridePerfectAimCheckBox.isChecked = curOverrideWep[10].toBool()
        overridePerfectAimCollapsible.setCollapsed(!overridePerfectAimCollapsible.isCollapsed, true)
        overridePerfectAimCheckBox.changed { _, _ ->
            curOverrideWep[10] = overridePerfectAimCheckBox.isChecked.toDouble()
            overridePerfectAimCollapsible.setCollapsed(!overridePerfectAimCollapsible.isCollapsed, true)
        }

        //Create Perfect_Aim_Fov Slider
        val overridePerfectAimFov = VisTable()
        Tooltip.Builder("The perfect aim field of view").target(overridePerfectAimFov).build()
        overridePerfectAimFovSlider.value = curOverrideWep[11].toFloat()
        overridePerfectAimFovSlider.changed { _, _ ->
            curOverrideWep[11] = overridePerfectAimFovSlider.value.toDouble()
            overridePerfectAimFovLabel.setText("Perfect Aim Fov: " + curOverrideWep[11].toInt() + when(curOverrideWep[11].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
        }
        overridePerfectAimFov.add(overridePerfectAimFovLabel)
        overridePerfectAimFov.add(overridePerfectAimFovSlider)
        //End Perfect_Aim_Fov Slider

        //Create Perfect_Aim_Chance Slider
        val overridePerfectAimChance = VisTable()
        Tooltip.Builder("The perfect aim chance (per calculation)").target(overridePerfectAimChance).build()
        overridePerfectAimChanceSlider.value = curOverrideWep[12].toFloat()
        overridePerfectAimChanceSlider.changed { _, _ ->
            curOverrideWep[12] = overridePerfectAimChanceSlider.value.toDouble()
            overridePerfectAimChanceLabel.setText("Perfect Aim Chance: " + curOverrideWep[12].toInt() + when(curOverrideWep[12].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
        }

        overridePerfectAimChance.add(overridePerfectAimChanceLabel)
        overridePerfectAimChance.add(overridePerfectAimChanceSlider)
        //End Perfect_Aim_Chance Slider

        overridePerfectAimTable.add(overridePerfectAimFov).row()
        overridePerfectAimTable.add(overridePerfectAimChance).row()
        //End Perfect_Aim Collapsible Check Box

        //Create Aim_Assist_Mode Collapsible Check Box
        Tooltip.Builder("Whether or not to enable aim assistance").target(overrideAimAssistCheckBox).build()
        overrideAimAssistCheckBox.isChecked = curOverrideWep[13].toBool()
        overrideAimAssistCollapsible.setCollapsed(!overrideAimAssistCollapsible.isCollapsed, true)

        //Create Aim_Assist_Strictness Slider
        val overrideAimAssistStrictness = VisTable()
        Tooltip.Builder("How close your crosshair is to the aim bone to determine whether to stop aiming").target(overrideAimAssistStrictness).build()
        overrideAimAssistStrictnessSlider.value = curOverrideWep[14].toFloat()
        overrideAimAssistStrictnessSlider.changed { _, _ ->
            curOverrideWep[14] = overrideAimAssistStrictnessSlider.value.toDouble()
            overrideAimAssistStrictnessLabel.setText("Aim Assist Strictness: " + curOverrideWep[14].toInt() + when(curOverrideWep[14].toInt().toString().length) {3->"  " 2->"    " else ->"      "})
        }

        overrideAimAssistStrictness.add(overrideAimAssistStrictnessLabel)
        overrideAimAssistStrictness.add(overrideAimAssistStrictnessSlider).width(125F)
        //End Aim_Assist_Strictness Slider

        overrideAimAssistTable.add(overrideAimAssistStrictness)

        overrideAimAssistCheckBox.changed { _, _ ->
            curOverrideWep[13] = overrideAimAssistCheckBox.isChecked.toDouble()
            overrideAimAssistCollapsible.setCollapsed(!overrideAimAssistCollapsible.isCollapsed, true)
            true
        }
        //End Aim_Assist_Mode Collapsible Check Box



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
        table.add(aimStrictness).row()
        table.add(perfectAimCheckBox).row()
        table.add(perfectAimCollapsible).row()
        table.add(aimAssistCheckBox).row()
        table.add(aimAssistCollapsible).row()//.expandX()

        table.addSeparator()

        table.add(overrideCategorySelection).row()
        table.add(weaponCategorySelection).row()
        table.add(overrideEnableOverride).row()
        table.add(overrideEnableFactorRecoil).row()
        table.add(overrideEnableFlatAim).row()
        table.add(overrideEnablePathAim).row()
        table.add(overrideAimBone).row()
        table.add(overrideAimFov).row()
        table.add(overrideAimSpeed).row()
        table.add(overrideAimSmoothness).row()
        table.add(overrideAimStrictness).row()
        table.add(overridePerfectAimCheckBox).row()
        table.add(overridePerfectAimCollapsible).row()
        table.add(overrideAimAssistCheckBox).row()
        table.add(overrideAimAssistCollapsible).row()
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