package rat.poison.ui.tabs.aimtabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.UIUpdate
import rat.poison.ui.changed

class AimRifleTab : Tab(true, false) { //Aim.kts tab
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableFlatAim = VisTextButton("ENABLE_FLAT_AIM", "toggle") //Enable_Flat_Aim
    val enablePathAim = VisTextButton("ENABLE_PATH_AIM", "toggle") //Enable_Path_Aim
    val aimBoneBox = VisSelectBox<String>() //Aim_Bone
    val aimFovLabel = VisLabel("Aim Fov: " + RIFLE_AIM_FOV.toString() + when(RIFLE_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Fov
    val aimFovSlider = VisSlider(1F, 360F, 2F, false) //Aim_Fov
    val aimSpeedMinLabel = VisLabel("Aim Speed Min: " + RIFLE_AIM_SPEED_MIN.toString() + when(RIFLE_AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Speed_Min
    val aimSpeedMinSlider = VisSlider(1F, 100F, 1F, false) //Aim_Speed_Min
    val aimSpeedMaxLabel = VisLabel("Aim Speed Max: " + RIFLE_AIM_SPEED_MAX.toString() + when(RIFLE_AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Speed_Max
    val aimSpeedMaxSlider = VisSlider(2F, 100F, 1F, false) //Aim_Speed_Max
    val aimStrictnessLabel = VisLabel("Aim Strictness: " + RIFLE_AIM_STRICTNESS.toString()) //Aim_Strictness
    val aimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false) //Aim_Strictness
    val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim") //Perfect_Aim
    private val perfectAimTable = VisTable() //Perfect_Aim_Collapsible Table
    val perfectAimCollapsible = CollapsibleWidget(perfectAimTable) //Perfect_Aim_Collapsible
    val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + RIFLE_PERFECT_AIM_FOV.toString() + when(RIFLE_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Fov
    val perfectAimFovSlider = VisSlider(0F, 100F, 1F, false) //Perfect_Aim_Fov
    val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + RIFLE_PERFECT_AIM_CHANCE.toString() + when(RIFLE_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "}) //Perfect_Aim_Chance
    val perfectAimChanceSlider = VisSlider(0F, 100F, 1F, false) //Perfect_Aim_Chance
    val aimAssistCheckBox = VisCheckBox("Enable Aim Assist") //Aim_Assist
    private val aimAssistTable = VisTable() //Aim_Assist_Collapsible Table
    val aimAssistCollapsible = CollapsibleWidget(aimAssistTable) //Aim_Assist_Collapsible
    val aimAssistStrictnessLabel = VisLabel("Aim Assist Strictness: " + RIFLE_AIM_ASSIST_STRICTNESS.toString() + when(RIFLE_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "}) //Aim_Assist_Strictness
    val aimAssistStrictnessSlider = VisSlider(0F, 100F, 1F, false) //Aim_Assist_Strictness

    init {
        //Create Enable_Flat_Aim Toggle
        //val enableFlatAim = VisTextButton("ENABLE_FLAT_AIM", "toggle")
        Tooltip.Builder("Whether or not to enable flat aim").target(enableFlatAim).build()
        enableFlatAim.isChecked = RIFLE_ENABLE_FLAT_AIM
        enableFlatAim.changed { _, _ ->
            RIFLE_ENABLE_FLAT_AIM = enableFlatAim.isChecked
            RIFLE_ENABLE_PATH_AIM = !RIFLE_ENABLE_FLAT_AIM
            UIUpdate()
            true
        }

        //Create Enable_Path_Aim Toggle
        //val enablePathAim = VisTextButton("ENABLE_PATH_AIM", "toggle")
        Tooltip.Builder("Whether or not to enable path aim").target(enablePathAim).build()
        enablePathAim.isChecked = RIFLE_ENABLE_PATH_AIM
        enablePathAim.changed { _, _ ->
            RIFLE_ENABLE_PATH_AIM = enablePathAim.isChecked
            RIFLE_ENABLE_FLAT_AIM = !RIFLE_ENABLE_PATH_AIM
            UIUpdate()
            true
        }

        //Create Aim_Bone Selector
        val aimBone = VisTable()
        Tooltip.Builder("The default aim bone to aim at").target(aimBone).build()
        //val aimBoneBox = VisSelectBox<String>()
        val aimBoneLabel = VisLabel("Aim Bone: ")
        aimBoneBox.setItems("HEAD_BONE", "BODY_BONE")
        aimBoneBox.selected = if (RIFLE_AIM_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
        aimBone.add(aimBoneLabel).top().spaceRight(6F)
        aimBone.add(aimBoneBox)

        aimBoneBox.changed { _, _ ->
            if (aimBoneBox.selected.toString() == "HEAD_BONE") {
                RIFLE_AIM_BONE = HEAD_BONE
            }
            else if (aimBoneBox.selected.toString() == "BODY_BONE") {
                RIFLE_AIM_BONE = BODY_BONE
            }
        }

        //Create Aim_Fov Slider
        val aimFov = VisTable()
        Tooltip.Builder("The aim field of view").target(aimFov).build()
        //val aimFovLabel = VisLabel("Aim Fov: " + RIFLE_AIM_FOV.toString() + when(RIFLE_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Need alternative for all of these, has to be some kind of setup for it
        //val aimFovSlider = VisSlider(1F, 360F, 2F, false)
        aimFovSlider.value = RIFLE_AIM_FOV.toFloat()
        aimFovSlider.changed { _, _ ->
            RIFLE_AIM_FOV = aimFovSlider.value.toInt()
            aimFovLabel.setText("Aim Fov: $RIFLE_AIM_FOV" + when(RIFLE_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
        }
        aimFov.add(aimFovLabel)//.spaceRight(6F)
        aimFov.add(aimFovSlider)

        //Create Aim_Speed_Min Slider
        val aimSpeedMin = VisTable()
        Tooltip.Builder("The minimum aim speed in milliseconds").target(aimSpeedMin).build()
        //val aimSpeedMinLabel = VisLabel("Aim Speed Min: " + RIFLE_AIM_SPEED_MIN.toString() + when(RIFLE_AIM_SPEED_MIN.toString().length) {3->"  " 2->"    " else ->"      "})
        //val aimSpeedMinSlider = VisSlider(1F, 100F, 1F, false)
        aimSpeedMinSlider.value = RIFLE_AIM_SPEED_MIN.toFloat()
        aimSpeedMinSlider.changed { _, _ ->
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
        aimSpeedMin.add(aimSpeedMinLabel)//.spaceRight(6F)
        aimSpeedMin.add(aimSpeedMinSlider)

        //Create Aim_Speed_Max
        val aimSpeedMax = VisTable()
        Tooltip.Builder("The maximum aim speed in milliseconds").target(aimSpeedMax).build()
        //val aimSpeedMaxLabel = VisLabel("Aim Speed Max: " + RIFLE_AIM_SPEED_MAX.toString() + when(RIFLE_AIM_SPEED_MAX.toString().length) {3->"  " 2->"    " else ->"      "})
        //val aimSpeedMaxSlider = VisSlider(2F, 100F, 1F, false)
        aimSpeedMaxSlider.value = RIFLE_AIM_SPEED_MAX.toFloat()
        aimSpeedMaxSlider.changed { _, _ ->
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
        aimSpeedMax.add(aimSpeedMaxLabel)//.spaceRight(6F) //when gets rid of spaceright
        aimSpeedMax.add(aimSpeedMaxSlider)

        //Create Aim_Strictness Slider
        val aimStrictness = VisTable()
        Tooltip.Builder("The aim sensitivity (multiplier)").target(aimStrictness).build()
        //val aimStrictnessLabel = VisLabel("Aim Strictness: " + RIFLE_AIM_STRICTNESS.toString()) //Doesnt need when as it stays 1.0 through 5.0
        //val aimStrictnessSlider = VisSlider(1F, 5F, 0.1F, false)
        aimStrictnessSlider.value = RIFLE_AIM_STRICTNESS.toFloat()
        aimStrictnessSlider.changed { _, _ ->
            RIFLE_AIM_STRICTNESS = Math.round(aimStrictnessSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
            aimStrictnessLabel.setText("Aim Strictness: $RIFLE_AIM_STRICTNESS")
        }
        aimStrictness.add(aimStrictnessLabel).spaceRight(6F)
        aimStrictness.add(aimStrictnessSlider)

        //Create Perfect_Aim Collapsible Check Box
        //val perfectAimCheckBox = VisCheckBox("Enable Perfect Aim")
        Tooltip.Builder("Whether or not to enable perfect aim").target(perfectAimCheckBox).build()
        perfectAimCheckBox.isChecked = RIFLE_PERFECT_AIM

        //val perfectAimTable = VisTable()
        //val perfectAimCollapsible = CollapsibleWidget(perfectAimTable)
        perfectAimCollapsible.setCollapsed(!RIFLE_PERFECT_AIM, true)

        //Create Perfect_Aim_Fov Slider
        val perfectAimFov = VisTable()
        Tooltip.Builder("The perfect aim field of view").target(perfectAimFov).build()
        //val perfectAimFovLabel = VisLabel("Perfect Aim Fov: " + RIFLE_PERFECT_AIM.toString() + when(RIFLE_PERFECT_AIM.toString().length) {3->"  " 2->"    " else ->"      "})
        //val perfectAimFovSlider = VisSlider(0F, 100F, 1F, false)
        perfectAimFovSlider.value = RIFLE_PERFECT_AIM_FOV.toFloat()
        perfectAimFovSlider.changed { _, _ ->
            RIFLE_PERFECT_AIM_FOV = perfectAimFovSlider.value.toInt()
            perfectAimFovLabel.setText("Perfect Aim Fov: $RIFLE_PERFECT_AIM_FOV" + when(RIFLE_PERFECT_AIM_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
        }
        perfectAimFov.add(perfectAimFovLabel)//.spaceRight(6F)
        perfectAimFov.add(perfectAimFovSlider)
        //End Perfect_Aim_Fov Slider

        //Create Perfect_Aim_Chance Slider
        val perfectAimChance = VisTable()
        Tooltip.Builder("The perfect aim chance (per calculation)").target(perfectAimChance).build()
        //val perfectAimChanceLabel = VisLabel("Perfect Aim Chance: " + RIFLE_PERFECT_AIM_CHANCE.toString() + when(RIFLE_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "})
        //val perfectAimChanceSlider = VisSlider(0F, 100F, 1F, false)
        perfectAimChanceSlider.value = RIFLE_PERFECT_AIM_CHANCE.toFloat()
        perfectAimChanceSlider.changed { _, _ ->
            RIFLE_PERFECT_AIM_CHANCE = perfectAimChanceSlider.value.toInt()
            perfectAimChanceLabel.setText("Perfect Aim Chance: " + RIFLE_PERFECT_AIM_CHANCE.toString() + when(RIFLE_PERFECT_AIM_CHANCE.toString().length) {3->"  " 2->"    " else ->"      "})
        }

        perfectAimChance.add(perfectAimChanceLabel)//.spaceRight(6F)
        perfectAimChance.add(perfectAimChanceSlider)
        //End Perfect_Aim_Chance Slider

        perfectAimTable.add(perfectAimFov).row()
        perfectAimTable.add(perfectAimChance).row()

        perfectAimCheckBox.changed { _, _ ->
            RIFLE_PERFECT_AIM = perfectAimCheckBox.isChecked
            perfectAimCollapsible.setCollapsed(!perfectAimCollapsible.isCollapsed, true)
        }
        //End Perfect_Aim Collapsible Check Box

        //Create Aim_Assist_Mode Collapsible Check Box
        //val aimAssistCheckBox = VisCheckBox("Enable Aim Assist")
        Tooltip.Builder("Whether or not to enable aim assistance").target(aimAssistCheckBox).build()
        aimAssistCheckBox.isChecked = RIFLE_AIM_ASSIST_MODE

        if (RIFLE_AIM_ASSIST_MODE) aimAssistCheckBox.isChecked
        //val aimAssistTable = VisTable()
        //val aimAssistCollapsible = CollapsibleWidget(aimAssistTable)
        aimAssistCollapsible.isCollapsed = !RIFLE_AIM_ASSIST_MODE//setCollapsed(!RIFLE_AIM_ASSIST_MODE)

        //Create Aim_Assist_Strictness Slider
        val aimAssistStrictness = VisTable()
        Tooltip.Builder("The aim assistance smoothness").target(aimAssistStrictness).build()
        //val aimAssistStrictnessLabel = VisLabel("Aim Assist Strictness: " + RIFLE_AIM_ASSIST_STRICTNESS.toString() + when(RIFLE_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "})
        //val aimAssistStrictnessSlider = VisSlider(0F, 100F, 1F, false)
        aimAssistStrictnessSlider.value = RIFLE_AIM_ASSIST_STRICTNESS.toFloat()
        aimAssistStrictnessSlider.changed { _, _ ->
            RIFLE_AIM_ASSIST_STRICTNESS = aimAssistStrictnessSlider.value.toInt()
            aimAssistStrictnessLabel.setText("Aim Assist Strictness: " + RIFLE_AIM_ASSIST_STRICTNESS.toString() + when(RIFLE_AIM_ASSIST_STRICTNESS.toString().length) {3->"  " 2->"    " else ->"      "}) //When is used to not make the sliders jitter when you go from 10 to 9, or 100 to 99, as that character space shifts everything, one character is 2 spaces
        }

        aimAssistStrictness.add(aimAssistStrictnessLabel)//.spaceRight(6F)
        aimAssistStrictness.add(aimAssistStrictnessSlider).width(125F)
        //End Aim_Assist_Strictness Slider

        aimAssistTable.add(aimAssistStrictness)

        aimAssistCheckBox.changed { _, _ ->
            RIFLE_AIM_ASSIST_MODE = aimAssistCheckBox.isChecked
            aimAssistCollapsible.setCollapsed(!aimAssistCollapsible.isCollapsed, true)
        }
        //End Aim_Assist_Mode Collapsible Check Box

        //Add all items to label for tabbed pane content
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
        return "Rifle Aim"
    }
}