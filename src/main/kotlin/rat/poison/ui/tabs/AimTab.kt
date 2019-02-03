package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.*

//todo change path/flat aim buttons to a selector box
//if not laggy, change when statement to engine_eval

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

    val categorySelectionBox = VisSelectBox<String>() //Category

    val enableFlatAim = VisCheckBox("Flat Aim") //Enable_Flat_Aim
    val enablePathAim = VisCheckBox("Path Aim") //Enable_Path_Aim
    val aimBoneBox = VisSelectBox<String>() //Aim_Bone
    val aimFovLabel = VisLabel("Aim Fov: " + PISTOL_AIM_FOV.toString() + when(PISTOL_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Fov
    val aimFovSlider = VisSlider(1F, 360F, 2F, false) //Aim_Fov
    val aimSpeedMinLabel = VisLabel("Aim Speed Min: " + PISTOL_AIM_SPEED_MIN.toString() + when(PISTOL_AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Speed_Min
    val aimSpeedMinSlider = VisSlider(1F, 100F, 1F, false) //Aim_Speed_Min
    val aimSpeedMaxLabel = VisLabel("Aim Speed Max: " + PISTOL_AIM_SPEED_MAX.toString() + when(PISTOL_AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Speed_Max
    val aimSpeedMaxSlider = VisSlider(2F, 100F, 1F, false) //Aim_Speed_Max
    val aimStrictnessLabel = VisLabel("Aim Strictness: " + PISTOL_AIM_STRICTNESS.toString()) //Aim_Strictness
    val aimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false) //Aim_Strictness
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim") //Perfect_Aim
    private val perfectAimTable = VisTable() //Perfect_Aim_Collapsible Table
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable) //Perfect_Aim_Collapsible
    val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + PISTOL_PERFECT_AIM_FOV.toString() + when(PISTOL_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Fov
    val perfectAimFovSlider = VisSlider(0F, 100F, 1F, false) //Perfect_Aim_Fov
    val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + PISTOL_PERFECT_AIM_CHANCE.toString() + when(PISTOL_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Chance
    val perfectAimChanceSlider = VisSlider(0F, 100F, 1F, false) //Perfect_Aim_Chance
    val aimAssistCheckBox = VisCheckBox("Enable Aim Assist") //Aim_Assist
    private val aimAssistTable = VisTable() //Aim_Assist_Collapsible Table
    val aimAssistCollapsible = CollapsibleWidget(aimAssistTable) //Aim_Assist_Collapsible
    val aimAssistStrictnessLabel = VisLabel("Aim Assist Strictness: " + PISTOL_AIM_ASSIST_STRICTNESS.toString() + when(PISTOL_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Assist_Strictness
    val aimAssistStrictnessSlider = VisSlider(0F, 100F, 1F, false) //Aim_Assist_Strictness

    init {
        //Create Activate_From_Fire_Key Toggle
        //val activateFromFireKey = VisTextButton("ACTIVATE_FROM_FIRE_KEY", "toggle")
        Tooltip.Builder("Activate aim if pressing predefined fire key").target(activateFromFireKey).build()
        if (ACTIVATE_FROM_FIRE_KEY) activateFromFireKey.toggle()
        activateFromFireKey.changed { _, _ ->
            if (true) {
                ACTIVATE_FROM_FIRE_KEY = activateFromFireKey.isChecked//!ACTIVATE_FROM_FIRE_KEY
            }
        }

        //Create Teammates_Are_Enemies Toggle
        //val teammatesAreEnemies = VisTextButton("TEAMMATES_ARE_ENEMIES", "toggle")
        Tooltip.Builder("Teammates will be treated as enemies").target(teammatesAreEnemies).build()
        if (TEAMMATES_ARE_ENEMIES) teammatesAreEnemies.toggle()
        teammatesAreEnemies.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic /fixl ater
                TEAMMATES_ARE_ENEMIES = teammatesAreEnemies.isChecked//!TEAMMATES_ARE_ENEMIES
            }
        }

        //Create Automatic_Weapons Toggle
        //val automaticWeapons = VisTextButton("Automatic Weapons", "toggle")
        Tooltip.Builder("Non-automatic weapons will auto shoot when there is no punch and the fire key is pressed").target(automaticWeapons).build()
        automaticWeapons.isChecked = AUTOMATIC_WEAPONS
        automaticWeapons.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic /fixl ater
                AUTOMATIC_WEAPONS = automaticWeapons.isChecked
            }
        }

        //Create Max_Punch_Check Slider
        val maxPunchCheck = VisTable()
        Tooltip.Builder("The ms delay between checking punch to fire a shot using AUTOMATIC_WEAPONS, the lower the less accurate but faster firing").target(maxPunchCheck).build()
//      val maxPunchCheckLabel = VisLabel("Max Punch Check: " + MAX_PUNCH_CHECK.toString() + when(MAX_PUNCH_CHECK.toString().length) {3->"" 2->"  " else ->"    "}) //Max_Punch_Check
//      val maxPunchCheckSlider = VisSlider(1F, 32F, 1F, false) //Max_Punch_Check
        maxPunchCheckSlider.value = MAX_PUNCH_CHECK.toFloat()
        maxPunchCheckSlider.changed { _, _ ->
            MAX_PUNCH_CHECK = maxPunchCheckSlider.value.toInt()
            maxPunchCheckLabel.setText("Max Punch Check: " + MAX_PUNCH_CHECK.toString() + when(MAX_PUNCH_CHECK.toString().length) {3->"" 2->"  " else ->"    "})
        }
        maxPunchCheck.add(maxPunchCheckLabel)//.spaceRight(6F)
        maxPunchCheck.add(maxPunchCheckSlider)

        //Create Force_Aim_Key Input
        val forceAimKey = VisTable()
        Tooltip.Builder("The key to force lock onto any enemy inside aim fov").target(forceAimKey).build()
        val forceAimKeyLabel = VisLabel("Force Aim Key: ")
        //val forceAimKeyField = VisValidatableTextField(Validators.FLOATS)
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
        //val aimBoneBox = VisSelectBox<String>()
        val categorySelectLabel = VisLabel("Weapon Category: ")
        categorySelectionBox.setItems("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
        categorySelectionBox.selected = "PISTOL" //Default Selection
        categorySelected = categorySelectionBox.selected
        categorySelection.add(categorySelectLabel).top().spaceRight(6F)
        categorySelection.add(categorySelectionBox)

        categorySelectionBox.changed { _, _ ->
            categorySelected = categorySelectionBox.selected
            UIUpdate()
            true
        }


        //Create Enable_Flat_Aim Toggle
        //val enableFlatAim = VisTextButton("ENABLE_FLAT_AIM", "toggle")
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
        //val enablePathAim = VisTextButton("ENABLE_PATH_AIM", "toggle")
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
        //val aimBoneBox = VisSelectBox<String>()
        val aimBoneLabel = VisLabel("Aim Bone: ")
        aimBoneBox.setItems("HEAD_BONE", "BODY_BONE")
        aimBoneBox.selected = if (PISTOL_AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
        aimBone.add(aimBoneLabel).top().spaceRight(6F)
        aimBone.add(aimBoneBox)

        aimBoneBox.changed { _, _ ->
            var setBone = HEAD_BONE //Default

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
        //val aimFovLabel = VisLabel("Aim Fov: " + RIFLE_AIM_FOV.toString() + when(RIFLE_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Need alternative for all of these, has to be some kind of setup for it
        //val aimFovSlider = VisSlider(1F, 360F, 2F, false)
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
        aimFov.add(aimFovLabel)//.spaceRight(6F)
        aimFov.add(aimFovSlider)

        //Create Aim_Speed_Min Slider
        val aimSpeedMin = VisTable()
        Tooltip.Builder("The minimum aim speed in milliseconds").target(aimSpeedMin).build()
        //val aimSpeedMinLabel = VisLabel("Aim Speed Min: " + RIFLE_AIM_SPEED_MIN.toString() + when(RIFLE_AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "})
        //val aimSpeedMinSlider = VisSlider(1F, 100F, 1F, false)
        aimSpeedMinSlider.value = PISTOL_AIM_SPEED_MIN.toFloat()
        aimSpeedMinSlider.changed { _, _ ->
            when (categorySelected) { //Needs cleanup
                "PISTOL" -> {
                    if ((aimSpeedMinSlider.value.toInt() < PISTOL_AIM_SPEED_MAX)) {
                        PISTOL_AIM_SPEED_MIN = aimSpeedMinSlider.value.toInt()
                        aimSpeedMinLabel.setText("Aim Speed Min: $PISTOL_AIM_SPEED_MIN" + when(PISTOL_AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "})
                    }
                    else
                    {
                        PISTOL_AIM_SPEED_MIN = PISTOL_AIM_SPEED_MAX -1
                        aimSpeedMinSlider.value = (PISTOL_AIM_SPEED_MAX -1).toFloat()
                    }
                }

                "RIFLE" -> {
                    if ((aimSpeedMinSlider.value.toInt() < RIFLE_AIM_SPEED_MAX)) {
                        RIFLE_AIM_SPEED_MIN = aimSpeedMinSlider.value.toInt()
                        aimSpeedMinLabel.setText("Aim Speed Min: $RIFLE_AIM_SPEED_MIN" + when(RIFLE_AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "})
                    }
                    else
                    {
                        RIFLE_AIM_SPEED_MIN = RIFLE_AIM_SPEED_MAX -1
                        aimSpeedMinSlider.value = (RIFLE_AIM_SPEED_MAX -1).toFloat()
                    }
                }

                "SMG" -> {
                    if ((aimSpeedMinSlider.value.toInt() < SMG_AIM_SPEED_MAX)) {
                        SMG_AIM_SPEED_MIN = aimSpeedMinSlider.value.toInt()
                        aimSpeedMinLabel.setText("Aim Speed Min: $SMG_AIM_SPEED_MIN" + when(SMG_AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "})
                    }
                    else
                    {
                        SMG_AIM_SPEED_MIN = SMG_AIM_SPEED_MAX -1
                        aimSpeedMinSlider.value = (SMG_AIM_SPEED_MAX -1).toFloat()
                    }
                }

                "SNIPER" -> {
                    if ((aimSpeedMinSlider.value.toInt() < SNIPER_AIM_SPEED_MAX)) {
                        SNIPER_AIM_SPEED_MIN = aimSpeedMinSlider.value.toInt()
                        aimSpeedMinLabel.setText("Aim Speed Min: $SNIPER_AIM_SPEED_MIN" + when(SNIPER_AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "})
                    }
                    else
                    {
                        SNIPER_AIM_SPEED_MIN = SNIPER_AIM_SPEED_MAX -1
                        aimSpeedMinSlider.value = (SNIPER_AIM_SPEED_MAX -1).toFloat()
                    }
                }

                "SHOTGUN" -> {
                    if ((aimSpeedMinSlider.value.toInt() < SHOTGUN_AIM_SPEED_MAX)) {
                        SHOTGUN_AIM_SPEED_MIN = aimSpeedMinSlider.value.toInt()
                        aimSpeedMinLabel.setText("Aim Speed Min: $SHOTGUN_AIM_SPEED_MIN" + when(SHOTGUN_AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "})
                    }
                    else
                    {
                        SHOTGUN_AIM_SPEED_MIN = SHOTGUN_AIM_SPEED_MAX -1
                        aimSpeedMinSlider.value = (SHOTGUN_AIM_SPEED_MAX -1).toFloat()
                    }
                }
            }
        }
        aimSpeedMin.add(aimSpeedMinLabel)//.spaceRight(6F)
        aimSpeedMin.add(aimSpeedMinSlider)

        //Create Aim_Speed_Max
        val aimSpeedMax = VisTable()
        Tooltip.Builder("The maximum aim speed in milliseconds").target(aimSpeedMax).build()
        //val aimSpeedMaxLabel = VisLabel("Aim Speed Max: " + RIFLE_AIM_SPEED_MAX.toString() + when(RIFLE_AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "})
        //val aimSpeedMaxSlider = VisSlider(2F, 100F, 1F, false)
        aimSpeedMaxSlider.value = PISTOL_AIM_SPEED_MAX.toFloat()
        aimSpeedMaxSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    if ((aimSpeedMaxSlider.value.toInt() > PISTOL_AIM_SPEED_MIN)) {
                        PISTOL_AIM_SPEED_MAX = aimSpeedMaxSlider.value.toInt()
                        aimSpeedMaxLabel.setText("Aim Speed Max: $PISTOL_AIM_SPEED_MAX" + when(PISTOL_AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "})
                    }
                    else
                    {
                        PISTOL_AIM_SPEED_MAX = PISTOL_AIM_SPEED_MIN +1
                        aimSpeedMaxSlider.value = (PISTOL_AIM_SPEED_MIN +1).toFloat()
                    }
                }

                "RIFLE" -> {
                    if ((aimSpeedMaxSlider.value.toInt() > RIFLE_AIM_SPEED_MIN)) {
                        RIFLE_AIM_SPEED_MAX = aimSpeedMaxSlider.value.toInt()
                        aimSpeedMaxLabel.setText("Aim Speed Max: $RIFLE_AIM_SPEED_MAX" + when(RIFLE_AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "})
                    }
                    else
                    {
                        RIFLE_AIM_SPEED_MAX = RIFLE_AIM_SPEED_MIN +1
                        aimSpeedMaxSlider.value = (RIFLE_AIM_SPEED_MIN +1).toFloat()
                    }
                }

                "SMG" -> {
                    if ((aimSpeedMaxSlider.value.toInt() > SMG_AIM_SPEED_MIN)) {
                        SMG_AIM_SPEED_MAX = aimSpeedMaxSlider.value.toInt()
                        aimSpeedMaxLabel.setText("Aim Speed Max: $SMG_AIM_SPEED_MAX" + when(SMG_AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "})
                    }
                    else
                    {
                        SMG_AIM_SPEED_MAX = SMG_AIM_SPEED_MIN +1
                        aimSpeedMaxSlider.value = (SMG_AIM_SPEED_MIN +1).toFloat()
                    }
                }

                "SNIPER" -> {
                    if ((aimSpeedMaxSlider.value.toInt() > SNIPER_AIM_SPEED_MIN)) {
                        SNIPER_AIM_SPEED_MAX = aimSpeedMaxSlider.value.toInt()
                        aimSpeedMaxLabel.setText("Aim Speed Max: $SNIPER_AIM_SPEED_MAX" + when(SNIPER_AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "})
                    }
                    else
                    {
                        SNIPER_AIM_SPEED_MAX = SNIPER_AIM_SPEED_MIN +1
                        aimSpeedMaxSlider.value = (SNIPER_AIM_SPEED_MIN +1).toFloat()
                    }
                }

                "SHOTGUN" -> {
                    if ((aimSpeedMaxSlider.value.toInt() > SHOTGUN_AIM_SPEED_MIN)) {
                        SHOTGUN_AIM_SPEED_MAX = aimSpeedMaxSlider.value.toInt()
                        aimSpeedMaxLabel.setText("Aim Speed Max: $SHOTGUN_AIM_SPEED_MAX" + when(SHOTGUN_AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "})
                    }
                    else
                    {
                        SHOTGUN_AIM_SPEED_MAX = SHOTGUN_AIM_SPEED_MIN +1
                        aimSpeedMaxSlider.value = (SHOTGUN_AIM_SPEED_MIN +1).toFloat()
                    }
                }
            }
        }
        aimSpeedMax.add(aimSpeedMaxLabel)//.spaceRight(6F) //when gets rid of spaceright
        aimSpeedMax.add(aimSpeedMaxSlider)

        //Create Aim_Strictness Slider
        val aimStrictness = VisTable()
        Tooltip.Builder("The aim sensitivity (multiplier)").target(aimStrictness).build()
        //val aimStrictnessLabel = VisLabel("Aim Strictness: " + RIFLE_AIM_STRICTNESS.toString()) //Doesnt need when as it stays 1.0 through 5.0
        //val aimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false)
        aimStrictnessSlider.value = PISTOL_AIM_STRICTNESS.toFloat()
        aimStrictnessSlider.changed { _, _ ->
            when (categorySelected) {
                "PISTOL" -> {
                    PISTOL_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
                    aimStrictnessLabel.setText("Aim Strictness: $PISTOL_AIM_STRICTNESS")
                }

                "RIFLE" -> {
                    RIFLE_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
                    aimStrictnessLabel.setText("Aim Strictness: $RIFLE_AIM_STRICTNESS")
                }

                "SMG" -> {
                    SMG_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
                    aimStrictnessLabel.setText("Aim Strictness: $SMG_AIM_STRICTNESS")
                }

                "SNIPER" -> {
                    SNIPER_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
                    aimStrictnessLabel.setText("Aim Strictness: $SNIPER_AIM_STRICTNESS")
                }

                "SHOTGUN" -> {
                    SHOTGUN_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
                    aimStrictnessLabel.setText("Aim Strictness: $SHOTGUN_AIM_STRICTNESS")
                }
            }
        }
        aimStrictness.add(aimStrictnessLabel).spaceRight(6F)
        aimStrictness.add(aimStrictnessSlider)

        //Create Perfect_Aim Collapsible Check Box
        //val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim")
        Tooltip.Builder("Whether or not to enable perfect aim").target(perfectAimCheckBox).build()
        perfectAimCheckBox.isChecked = PISTOL_PERFECT_AIM

        //val perfectAimTable = VisTable()
        //val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
        perfectAimCollapsible.setCollapsed(!PISTOL_PERFECT_AIM, true)

        //Create Perfect_Aim_Fov Slider
        val perfectAimFov = VisTable()
        Tooltip.Builder("The perfect aim field of view").target(perfectAimFov).build()
        //val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + RIFLE_PERFECT_AIM.toString() + when(RIFLE_PERFECT_AIM.toString().length) {3->"  " 2->"    " else ->"      "})
        //val perfectAimFovSlider = VisSlider(0F, 100F, 1F, false)
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
        perfectAimFov.add(perfectAimFovLabel)//.spaceRight(6F)
        perfectAimFov.add(perfectAimFovSlider)
        //End Perfect_Aim_Fov Slider

        //Create Perfect_Aim_Chance Slider
        val perfectAimChance = VisTable()
        Tooltip.Builder("The perfect aim chance (per calculation)").target(perfectAimChance).build()
        //val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + RIFLE_PERFECT_AIM_CHANCE.toString() + when(RIFLE_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "})
        //val perfectAimChanceSlider = VisSlider(0F, 100F, 1F, false)
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

        perfectAimChance.add(perfectAimChanceLabel)//.spaceRight(6F)
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
        //val aimAssistCheckBox = VisCheckBox("Enable Aim Assist")
        Tooltip.Builder("Whether or not to enable aim assistance").target(aimAssistCheckBox).build()
        aimAssistCheckBox.isChecked = PISTOL_AIM_ASSIST_MODE
        //val aimAssistTable = VisTable()
        //val aimAssistCollapsible = CollapsibleWidget(aimAssistTable)
        aimAssistCollapsible.isCollapsed = !PISTOL_AIM_ASSIST_MODE//setCollapsed(!RIFLE_AIM_ASSIST_MODE)

        //Create Aim_Assist_Strictness Slider
        val aimAssistStrictness = VisTable()
        Tooltip.Builder("The aim assistance smoothness").target(aimAssistStrictness).build()
        //val aimAssistStrictnessLabel = VisLabel("Aim Assist Strictness: " + RIFLE_AIM_ASSIST_STRICTNESS.toString() + when(RIFLE_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "})
        //val aimAssistStrictnessSlider = VisSlider(0F, 100F, 1F, false)
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

        aimAssistStrictness.add(aimAssistStrictnessLabel)//.spaceRight(6F)
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
        table.add(categorySelection).row() //Add Category_Selection
        table.add(enableFlatAim).row() //Add Flat_Aim
        table.add(enablePathAim).row() //Add Path_Aim
        table.add(aimBone).row() //Add Aim_Bone Selector
        table.add(aimFov).width(250F).row() //Add Aim_Fov
        table.add(aimSpeedMin).width(250F).row() //Add Aim_Speed_Min Slider
        table.add(aimSpeedMax).width(250F).row() //Add Aim_Speed_Max Slider
        table.add(aimStrictness).width(250F).row() //Add Aim_Strictness Slider
        //Skipped target swap min delay
        //Skipped target swap max delay
        table.add(perfectAimCheckBox).row() //Add Perfect_Aim CollapsibleCheckBox
        table.add(perfectAimCollapsible).row() //Add Perfect_Aim Var Slider Collapsible
        table.add(aimAssistCheckBox).row() //Add Aim_Assist CollapsibleCheckBox
        table.add(aimAssistCollapsible).expandX() //Add Aim_Assist Var Slider Collapsible

    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Aim"
    }
}