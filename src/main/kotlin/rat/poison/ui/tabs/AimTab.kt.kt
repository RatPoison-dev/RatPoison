package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.changed

class AimTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val aimBoneBox = VisSelectBox<String>() //Aim_Bone
    val activateFromFireKey = VisTextButton("ACTIVATE_FROM_FIRE_KEY", "toggle") //Activate_From_Fire_Key
    val teammatesAreEnemies = VisTextButton("TEAMMATES_ARE_ENEMIES", "toggle") //Teammates_Are_Enemies
    val forceAimKeyField = VisValidatableTextField(Validators.FLOATS) //Force_Aim_Key
    val aimFovLabel = VisLabel("Aim Fov: " + AIM_FOV.toString() + when(AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Fov
    val aimFovSlider = VisSlider(1F, 360F, 2F, false) //Aim_Fov
    val aimSpeedMinLabel = VisLabel("Aim Speed Min: " + AIM_SPEED_MIN.toString() + when(AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Speed_Min
    val aimSpeedMinSlider = VisSlider(1F, 100F, 1F, false) //Aim_Speed_Min
    val aimSpeedMaxLabel = VisLabel("Aim Speed Max: " + AIM_SPEED_MAX.toString() + when(AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Speed_Max
    val aimSpeedMaxSlider = VisSlider(2F, 100F, 1F, false) //Aim_Speed_Max
    val aimStrictnessLabel = VisLabel("Aim Strictness: " + AIM_STRICTNESS.toString()) //Aim_Strictness
    val aimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false) //Aim_Strictness
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim") //Perfect_Aim
    private val perfectAimTable = VisTable() //Perfect_Aim_Collapsible Table
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable) //Perfect_Aim_Collapsible
    val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + PERFECT_AIM_FOV.toString() + when(PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Fov
    val perfectAimFovSlider = VisSlider(0F, 100F, 1F, false) //Perfect_Aim_Fov
    val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + PERFECT_AIM_CHANCE.toString() + when(PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Chance
    val perfectAimChanceSlider = VisSlider(0F, 100F, 1F, false) //Perfect_Aim_Chance
    val aimAssistCheckBox = VisCheckBox("Enable Aim Assist") //Aim_Assist
    private val aimAssistTable = VisTable() //Aim_Assist_Collapsible Table
    val aimAssistCollapsible = CollapsibleWidget(aimAssistTable) //Aim_Assist_Collapsible
    val aimAssistStrictnessLabel = VisLabel("Aim Assist Strictness: " + AIM_ASSIST_STRICTNESS.toString() + when(AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Assist_Strictness
    val aimAssistStrictnessSlider = VisSlider(0F, 100F, 1F, false) //Aim_Assist_Strictness

    init {
        //Create Aim_Bone Selector
        val aimBoneSelector = VisTable()
        //val aimBoneBox = VisSelectBox<String>()
        val aimBoneLabel = VisLabel("Aim Bone: ")
        aimBoneBox.setItems("HEAD_BONE", "BODY_BONE")
        aimBoneBox.selected = if (AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
        aimBoneSelector.add(aimBoneLabel).top().spaceRight(6F)
        aimBoneSelector.add(aimBoneBox)


        aimBoneBox.changed { _, _ ->
            if (aimBoneBox.selected.toString() == "HEAD_BONE") {
                AIM_BONE = HEAD_BONE
            }
            else if (aimBoneBox.selected.toString() == "BODY_BONE") {
                AIM_BONE = BODY_BONE
            }
        }

        //Create Activate_From_Fire_Key Toggle
        //val activateFromFireKey = VisTextButton("ACTIVATE_FROM_FIRE_KEY", "toggle")
        if (ACTIVATE_FROM_FIRE_KEY) activateFromFireKey.toggle()
        activateFromFireKey.changed { _, _ ->
            if (true) {
                ACTIVATE_FROM_FIRE_KEY = activateFromFireKey.isChecked//!ACTIVATE_FROM_FIRE_KEY
            }
        }

        //Create Teammates_Are_Enemies Toggle
        //val teammatesAreEnemies = VisTextButton("TEAMMATES_ARE_ENEMIES", "toggle")
        if (TEAMMATES_ARE_ENEMIES) teammatesAreEnemies.toggle()
        teammatesAreEnemies.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic /fixl ater
                TEAMMATES_ARE_ENEMIES = teammatesAreEnemies.isChecked//!TEAMMATES_ARE_ENEMIES
            }
        }

        //Create Force_Aim_Key Input
        val forceAimKey = VisTable()
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

        //Create Aim_Fov Slider
        val aimFov = VisTable()
        //val aimFovLabel = VisLabel("Aim Fov: " + AIM_FOV.toString() + when(AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Need alternative for all of these, has to be some kind of setup for it
        //val aimFovSlider = VisSlider(1F, 360F, 2F, false)
        aimFovSlider.value = AIM_FOV.toFloat()
        aimFovSlider.changed { _, _ ->
            AIM_FOV = aimFovSlider.value.toInt()
            aimFovLabel.setText("Aim Fov: " + AIM_FOV.toString() + when(AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
        }
        aimFov.add(aimFovLabel)//.spaceRight(6F)
        aimFov.add(aimFovSlider)

        //Create Aim_Speed_Min Slider
        val aimSpeedMin = VisTable()
        //val aimSpeedMinLabel = VisLabel("Aim Speed Min: " + AIM_SPEED_MIN.toString() + when(AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "})
        //val aimSpeedMinSlider = VisSlider(1F, 100F, 1F, false)
        aimSpeedMinSlider.value = AIM_SPEED_MIN.toFloat()
        aimSpeedMinSlider.changed { _, _ ->
            if ((aimSpeedMinSlider.value.toInt() < AIM_SPEED_MAX)) {
                AIM_SPEED_MIN = aimSpeedMinSlider.value.toInt()
                aimSpeedMinLabel.setText("Aim Speed Min: " + AIM_SPEED_MIN.toString() + when(AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "})
            }
            else
            {
                AIM_SPEED_MIN = AIM_SPEED_MAX -1
                aimSpeedMinSlider.value = (AIM_SPEED_MAX -1).toFloat()
            }
        }
        aimSpeedMin.add(aimSpeedMinLabel)//.spaceRight(6F)
        aimSpeedMin.add(aimSpeedMinSlider)

        //Create Aim_Speed_Max
        val aimSpeedMax = VisTable()
        //val aimSpeedMaxLabel = VisLabel("Aim Speed Max: " + AIM_SPEED_MAX.toString() + when(AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "})
        //val aimSpeedMaxSlider = VisSlider(2F, 100F, 1F, false)
        aimSpeedMaxSlider.value = AIM_SPEED_MAX.toFloat()
        aimSpeedMaxSlider.changed { _, _ ->
            if ((aimSpeedMaxSlider.value.toInt() > AIM_SPEED_MIN)) {
                AIM_SPEED_MAX = aimSpeedMaxSlider.value.toInt()
                aimSpeedMaxLabel.setText("Aim Speed Max: " + AIM_SPEED_MAX.toString() + when(AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "})
            }
            else
            {
                AIM_SPEED_MAX = AIM_SPEED_MIN +1
                aimSpeedMaxSlider.value = (AIM_SPEED_MIN +1).toFloat()
            }
        }
        aimSpeedMax.add(aimSpeedMaxLabel)//.spaceRight(6F) //when gets rid of spaceright
        aimSpeedMax.add(aimSpeedMaxSlider)

        //Create Aim_Strictness Slider
        val aimStrictness = VisTable()
        //val aimStrictnessLabel = VisLabel("Aim Strictness: " + AIM_STRICTNESS.toString()) //Doesnt need when as it stays 1.0 through 5.0
        //val aimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false)
        aimStrictnessSlider.value = AIM_STRICTNESS.toFloat()
        aimStrictnessSlider.changed { _, _ ->
            AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
            aimStrictnessLabel.setText("Aim Strictness: " + AIM_STRICTNESS.toString())
        }
        aimStrictness.add(aimStrictnessLabel).spaceRight(6F)
        aimStrictness.add(aimStrictnessSlider)

        //Create Perfect_Aim Collapsible Check Box
        //val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim")

        perfectAimCheckBox.isChecked = PERFECT_AIM

        //val perfectAimTable = VisTable()
        //val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
        perfectAimCollapsible.setCollapsed(!PERFECT_AIM, true)

        //Create Perfect_Aim_Fov Slider
        val perfectAimFov = VisTable()
        //val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + PERFECT_AIM_FOV.toString() + when(PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
        //val perfectAimFovSlider = VisSlider(0F, 100F, 1F, false)
        perfectAimFovSlider.value = PERFECT_AIM_FOV.toFloat()
        perfectAimFovSlider.changed { _, _ ->
            PERFECT_AIM_FOV = perfectAimFovSlider.value.toInt()
            perfectAimFovLabel.setText("Perfect Aim Fov: " + PERFECT_AIM_FOV.toString() + when(PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
        }
        perfectAimFov.add(perfectAimFovLabel)//.spaceRight(6F)
        perfectAimFov.add(perfectAimFovSlider)
        //End Perfect_Aim_Fov Slider

        //Create Perfect_Aim_Chance Slider
        val perfectAimChance = VisTable()
        //val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + PERFECT_AIM_CHANCE.toString() + when(PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "})
        //val perfectAimChanceSlider = VisSlider(0F, 100F, 1F, false)
        perfectAimChanceSlider.value = PERFECT_AIM_CHANCE.toFloat()
        perfectAimChanceSlider.changed { _, _ ->
            PERFECT_AIM_CHANCE = perfectAimChanceSlider.value.toInt()
            perfectAimChanceLabel.setText("Perfect Aim Chance: " + PERFECT_AIM_CHANCE.toString() + when(PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "})
        }

        perfectAimChance.add(perfectAimChanceLabel)//.spaceRight(6F)
        perfectAimChance.add(perfectAimChanceSlider)
        //End Perfect_Aim_Chance Slider

        perfectAimTable.add(perfectAimFov).row()
        perfectAimTable.add(perfectAimChance).row()

        perfectAimCheckBox.changed { _, _ ->
            PERFECT_AIM = perfectAimCheckBox.isChecked
            perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
        }
        //End Perfect_Aim Collapsible Check Box

        //Create Aim_Assist_Mode Collapsible Check Box
        //val aimAssistCheckBox = VisCheckBox("Enable Aim Assist")

        aimAssistCheckBox.isChecked = AIM_ASSIST_MODE

        if (AIM_ASSIST_MODE) aimAssistCheckBox.isChecked
        //val aimAssistTable = VisTable()
        //val aimAssistCollapsible = CollapsibleWidget(aimAssistTable)
        aimAssistCollapsible.isCollapsed = !AIM_ASSIST_MODE//setCollapsed(!AIM_ASSIST_MODE)

        //Create Aim_Assist_Strictness Slider
        val aimAssistStrictness = VisTable()
        //val aimAssistStrictnessLabel = VisLabel("Aim Assist Strictness: " + AIM_ASSIST_STRICTNESS.toString() + when(AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "})
        //val aimAssistStrictnessSlider = VisSlider(0F, 100F, 1F, false)
        aimAssistStrictnessSlider.value = AIM_ASSIST_STRICTNESS.toFloat()
        aimAssistStrictnessSlider.changed { _, _ ->
            AIM_ASSIST_STRICTNESS = aimAssistStrictnessSlider.value.toInt()
            aimAssistStrictnessLabel.setText("Aim Assist Strictness: " + AIM_ASSIST_STRICTNESS.toString() + when(AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "}) //When is used to not make the sliders jitter when you go from 10 to 9, or 100 to 99, as that character space shifts everything, one character is 2 spaces
        }

        aimAssistStrictness.add(aimAssistStrictnessLabel)//.spaceRight(6F)
        aimAssistStrictness.add(aimAssistStrictnessSlider).width(125F)
        //End Aim_Assist_Strictness Slider

        aimAssistTable.add(aimAssistStrictness)

        aimAssistCheckBox.changed { _, _ ->
            AIM_ASSIST_MODE = aimAssistCheckBox.isChecked
            aimAssistCollapsible.setCollapsed(!aimAssistCollapsible.isCollapsed, true)
        }
        //End Aim_Assist_Mode Collapsible Check Box

        //Add all items to label for tabbed pane content
        table.add(aimBoneSelector).row() //Add Aim_Bone Selector
        table.add(activateFromFireKey).width(250F).row() //Add Activate_From_Fire_Key Toggle
        table.add(teammatesAreEnemies).width(250F).row() //Add Teammates_Are_Enemies Toggle
        table.add(forceAimKey).width(250F).row() //Add Force_Aim Input
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