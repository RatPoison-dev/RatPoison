package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.util.dialog.Dialogs
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.App
import rat.poison.App.stage
import rat.poison.game.CSGO.gameHeight
import rat.poison.game.CSGO.gameWidth
import rat.poison.settings.*
import rat.poison.ui.*

//Need to change path/aim to a drop down

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(true)

    var categorySelected = ""

    //Init labels/sliders/boxes that show values here
    val activateFromFireKey = VisCheckBox("Activate From Fire Key") //Activate_From_Fire_Key
    val teammatesAreEnemies = VisCheckBox("Teammates Are Enemies") //Teammates_Are_Enemies
    val forceAimKeyField = VisValidatableTextField(Validators.FLOATS) //Force_Aim_Key
    val automaticWeapons = VisCheckBox("Automatic Weapons")
    val maxPunchCheckLabel = VisLabel("Max Punch Check: " + MAX_PUNCH_CHECK.toString() + when(MAX_PUNCH_CHECK.toString().length) {3->"" 2->"  " else ->"    "}) //Max_Punch_Check
    val maxPunchCheckSlider = VisSlider(1F, 32F, 1F, false) //Max_Punch_Check

    private val categorySelectionBox = VisSelectBox<String>() //Category
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
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim") //Perfect_Aim
    private val perfectAimTable = VisTable() //Perfect_Aim_Collapsible Table
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable) //Perfect_Aim_Collapsible
    val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + PISTOL_PERFECT_AIM_FOV.toString() + when(PISTOL_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Fov
    val perfectAimFovSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Fov
    val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + PISTOL_PERFECT_AIM_CHANCE.toString() + when(PISTOL_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Chance
    val perfectAimChanceSlider = VisSlider(1F, 100F, 1F, false) //Perfect_Aim_Chance
    val aimAssistCheckBox = VisCheckBox("Enable Aim Assist") //Aim_Assist
    private val aimAssistTable = VisTable() //Aim_Assist_Collapsible Table
    val aimAssistCollapsible = CollapsibleWidget(aimAssistTable) //Aim_Assist_Collapsible
    val aimAssistStrictnessLabel = VisLabel("Aim Assist Strictness: " + PISTOL_AIM_ASSIST_STRICTNESS.toString() + when(PISTOL_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Assist_Strictness
    val aimAssistStrictnessSlider = VisSlider(1F, 100F, 1F, false) //Aim_Assist_Strictness

    init {
        val dialog = Dialogs.showOKDialog(App.stage, "Warning", "If you have any problems submit an issue on github or @Stupid Rat#9999 for a quick response.\nIf you are crashing or have error message in the cmd, include the error message if there is one,\nsettings that were enabled, and when it happened (such as randomly, when joining a game, on round end, etc)")
        dialog.setPosition(gameWidth/2F-dialog.width/2F, gameHeight.toFloat())
        stage.addActor(dialog)

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

        //Create Automatic_Weapons Toggle
        Tooltip.Builder("Non-automatic weapons will auto shoot when there is no punch and the fire key is pressed").target(automaticWeapons).build()
        automaticWeapons.isChecked = AUTOMATIC_WEAPONS
        automaticWeapons.changed { _, _ ->
            AUTOMATIC_WEAPONS = automaticWeapons.isChecked
            true
        }

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

        //Create Aim_Speed_Min Slider
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

        //Add all items to label for tabbed pane content
        table.add(activateFromFireKey).row()
        table.add(teammatesAreEnemies).row()
        table.add(automaticWeapons).row()
        table.add(maxPunchCheck).row()
        table.add(forceAimKey).row()
        table.addSeparator()
        table.add(categorySelection).row()
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
        table.add(aimAssistCollapsible).expandX()

    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Aim"
    }
}