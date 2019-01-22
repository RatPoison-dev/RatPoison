package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.changed

class Misc : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val boneTriggerFovLabel = VisLabel("Bone Trigger Fov: " + BONE_TRIGGER_FOV.toString() + when(BONE_TRIGGER_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Bone_Trigger_Fov
    val boneTriggerFovSlider = VisSlider(0F, 360F, 1F, false) //Bone_Trigger_Fov
    val boneTriggerBoneBox = VisSelectBox<String>() //Bone_Trigger_Bone
    val aimOnBoneTrigger = VisTextButton("AIM_ON_BONE_TRIGGER", "toggle") //Aim_On_Bone_Trigger
    val boneTriggerEnableKey = VisTextButton("BONE_TRIGGER_ENABLE_KEY", "toggle") //Bone_Trigger_Enable_Key
    val boneTriggerKeyField = VisValidatableTextField(Validators.FLOATS) //Bone_Trigger_Key
    val rcsMinLabel = VisLabel("RCS Min: " + RCS_MIN.toString() + when(RCS_MIN.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "}) //RCS_Min
    val rcsMinSlider = VisSlider(0.01F, 1.99F, .01F, false) //RCS_Min
    val rcsMaxLabel = VisLabel("RCS Max: " + RCS_MAX.toString() + when(RCS_MAX.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "}) //RCS_Max
    val rcsMaxSlider = VisSlider(0.02F, 2F, .01F, false) //RCS_Max
    val rcsMinDurationLabel = VisLabel("RCS Min Duration: " + RCS_MIN_DURATION.toString() + when(RCS_MIN_DURATION.toString().length) {2->"  " else->"    "}) //RCS_Min_Duration
    val rcsMinDurationSlider = VisSlider(1F, 100F, 1F, false) //RCS_Min_Duration
    val rcsMaxDurationLabel = VisLabel("RCS Max Duration: " + RCS_MAX_DURATION.toString() + when(RCS_MAX_DURATION.toString().length) {2->"  " else->"    "}) //RCS_Max_Duration
    val rcsMaxDurationSlider = VisSlider(1F, 100F, 1F, false) //RCS_Max_Duration
    val flashMaxAlphaLabel = VisLabel("Flash Max Alpha: " + FLASH_MAX_ALPHA.toString() + when(FLASH_MAX_ALPHA.toString().length) {3->"  " 2->"    " else ->"      "}) //Flash_Max_Alpha
    val flashMaxAlphaSlider = VisSlider(0.02F, 2F, .01F, false) //Flash_Max_Alpha
    //val drawFov = VisTextButton("DRAW_FOV", "toggle") //Draw_Fov
    val enemyIndicator = VisTextButton("ENEMY_INDICATOR", "toggle")


    init {
        //Create Bone_Trigger_Fov Slider
        val boneTriggerFov = VisTable()
        //val boneTriggerFovLabel = VisLabel("Bone Trigger Fov: " + BONE_TRIGGER_FOV.toString() + when(BONE_TRIGGER_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
        //val boneTriggerFovSlider = VisSlider(0F, 1000F, 1F, false)
        boneTriggerFovSlider.value = BONE_TRIGGER_FOV.toFloat()
        boneTriggerFovSlider.changed { _, _ ->
            BONE_TRIGGER_FOV = boneTriggerFovSlider.value.toInt()
            boneTriggerFovLabel.setText("Bone Trigger Fov: " + BONE_TRIGGER_FOV.toString() + when(BONE_TRIGGER_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //When is used to not make the sliders jitter when you go from 10 to 9, or 100 to 99, as that character space shifts everything, one character is 2 spaces
        }

        boneTriggerFov.add(boneTriggerFovLabel).spaceRight(6F)
        boneTriggerFov.add(boneTriggerFovSlider)

        //Create Bone_Trigger_Bone Selector
        val boneTriggerBone = VisTable()
        //val boneTriggerBoneBox = VisSelectBox<String>()
        val boneTriggerBoneLabel = VisLabel("Bone Trigger Bone: ")
        boneTriggerBoneBox.setItems("HEAD_BONE", "BODY_BONE")
        boneTriggerBoneBox.selected = if (BONE_TRIGGER_BONE == HEAD_BONE) "HEAD_BONE" else "BODY_BONE"
        boneTriggerBone.add(boneTriggerBoneLabel).top().spaceRight(6F)
        boneTriggerBone.add(boneTriggerBoneBox)

        boneTriggerBoneBox.changed { _, _ ->
            if (boneTriggerBoneBox.selected.toString() == "HEAD_BONE") {
                BONE_TRIGGER_BONE = HEAD_BONE
            }
            else if (boneTriggerBoneBox.selected.toString() == "BODY_BONE") {
                BONE_TRIGGER_BONE = BODY_BONE
            }
        }

        //Create Aim_On_Bone_Trigger Toggle
        //val aimOnBoneTrigger = VisTextButton("AIM_ON_BONE_TRIGGER", "toggle")
        if (AIM_ON_BONE_TRIGGER) aimOnBoneTrigger.toggle()
        aimOnBoneTrigger.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                AIM_ON_BONE_TRIGGER = aimOnBoneTrigger.isChecked//AIM_ON_BONE_TRIGGER
            }
        }

        //Create Bone_Trigger_Enable_Key Toggle
        //val boneTriggerEnableKey = VisTextButton("BONE_TRIGGER_ENABLE_KEY", "toggle")
        if (BONE_TRIGGER_ENABLE_KEY) boneTriggerEnableKey.toggle()
        boneTriggerEnableKey.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                BONE_TRIGGER_ENABLE_KEY = boneTriggerEnableKey.isChecked//!BONE_TRIGGER_ENABLE_KEY
            }
        }

        //Create Bone_Trigger_Key
        val boneTriggerKey = VisTable()
        val boneTriggerKeyLabel = VisLabel("BONE_TRIGGER_KEY: ")
        //val boneTriggerKeyField = VisValidatableTextField(Validators.FLOATS)
        boneTriggerKeyField.text = BONE_TRIGGER_KEY.toString()
        boneTriggerKey.changed { _, _ ->
            if (boneTriggerKeyField.text.toIntOrNull() != null) {
                BONE_TRIGGER_KEY = boneTriggerKeyField.text.toInt()
            }
        }
        boneTriggerKey.add(boneTriggerKeyLabel)
        boneTriggerKey.add(boneTriggerKeyField).spaceRight(6F).width(40F)
        boneTriggerKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        //Create RCS_Min Slider
        val rcsMin = VisTable()
        //val rcsMinLabel = VisLabel("RCS Min: " + RCS_MIN.toString() + when(RCS_MIN.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "})
        //val rcsMinSlider = VisSlider(0.01F, 1.99F, .01F, false)
        rcsMinSlider.value = RCS_MIN.toFloat()
        rcsMinSlider.changed { _, _ ->
            if ((rcsMinSlider.value < RCS_MAX)) {
                RCS_MIN = "%.2f".format(rcsMinSlider.value).toDouble()
                rcsMinLabel.setText("RCS Min: " + RCS_MIN.toString() + when(RCS_MIN.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "})
            }
            else
            {
                RCS_MIN = RCS_MAX -.01F
                rcsMinSlider.value = (RCS_MAX -.01).toFloat()
            }
        }
        rcsMin.add(rcsMinLabel)//.spaceRight(6F)
        rcsMin.add(rcsMinSlider)

        //Create RCS_Max Slider
        val rcsMax = VisTable()
        //val rcsMaxLabel = VisLabel("RCS Max: " + RCS_MAX.toString() + when(RCS_MAX.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "})
        //val rcsMaxSlider = VisSlider(0.02F, 2F, .01F, false)
        rcsMaxSlider.value = RCS_MAX.toFloat()
        rcsMaxSlider.changed { _, _ ->
            if ((rcsMaxSlider.value > RCS_MIN)) {
                RCS_MAX = "%.2f".format(rcsMaxSlider.value).toDouble()
                rcsMaxLabel.setText("RCS Max: " + RCS_MAX.toString() + when(RCS_MAX.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "})
            }
            else
            {
                RCS_MAX = RCS_MIN +.01F
                rcsMaxSlider.value = (RCS_MIN +.01).toFloat()
            }
        }
        rcsMax.add(rcsMaxLabel)//.spaceRight(6F) //when gets rid of spaceright
        rcsMax.add(rcsMaxSlider)

        //Create RCS_Min_Duration
        val rcsMinDuration = VisTable()
        //val rcsMinDurationLabel = VisLabel("RCS Min Duration: " + RCS_MIN_DURATION.toString() + when(RCS_MIN_DURATION.toString().length) {2->"  " else->"    "})
        //val rcsMinDurationSlider = VisSlider(1F, 100F, 1F, false)
        rcsMinDurationSlider.value = RCS_MIN_DURATION.toFloat()
        rcsMinDurationSlider.changed { _, _ ->
            if ((rcsMinDurationSlider.value.toInt() < RCS_MAX_DURATION)) {
                RCS_MIN_DURATION = rcsMinDurationSlider.value.toInt()
                rcsMinDurationLabel.setText("RCS Min Duration: " + RCS_MIN_DURATION.toString() + when(RCS_MIN_DURATION.toString().length) {2->"  " else->"    "})
            }
            else
            {
                RCS_MIN_DURATION = AIM_SPEED_MAX -1
                rcsMinDurationSlider.value = (AIM_SPEED_MAX -1).toFloat()
            }
        }
        rcsMinDuration.add(rcsMinDurationLabel)//.spaceRight(6F)
        rcsMinDuration.add(rcsMinDurationSlider)

        //Create RCS_Max_Duration
        val rcsMaxDuration = VisTable()
        //val rcsMaxDurationLabel = VisLabel("RCS Max Duration: " + RCS_MAX_DURATION.toString() + when(RCS_MAX_DURATION.toString().length) {2->"  " else->"    "})
        //val rcsMaxDurationSlider = VisSlider(1F, 100F, 1F, false)
        rcsMaxDurationSlider.value = RCS_MAX_DURATION.toFloat()
        rcsMaxDurationSlider.changed { _, _ ->
            if ((rcsMaxDurationSlider.value.toInt() > RCS_MIN_DURATION)) {
                RCS_MAX_DURATION = rcsMaxDurationSlider.value.toInt()
                rcsMaxDurationLabel.setText("RCS Max Duration: " + RCS_MAX_DURATION.toString() + when(RCS_MAX_DURATION.toString().length) {2->"  " else->"    "})
            }
            else
            {
                RCS_MAX_DURATION = RCS_MIN_DURATION +1
                rcsMaxDurationSlider.value = (RCS_MAX_DURATION +1).toFloat()
            }
        }
        rcsMaxDuration.add(rcsMaxDurationLabel)//.spaceRight(6F) //when gets rid of spaceright
        rcsMaxDuration.add(rcsMaxDurationSlider)

        //Create Flash_Max_Alpha
        val flashMaxAlpha = VisTable()
        //val flashMaxAlphaLabel = VisLabel("Flash Max Alpha: " + FLASH_MAX_ALPHA.toString() + when(FLASH_MAX_ALPHA.toString().length) {3->"  " 2->"    " else ->"      "})
        //val flashMaxAlphaSlider = VisSlider(0.02F, 2F, .01F, false)
        flashMaxAlphaSlider.value = FLASH_MAX_ALPHA
        flashMaxAlphaSlider.changed { _, _ ->
            FLASH_MAX_ALPHA = "%.2f".format(flashMaxAlphaSlider.value).toFloat()
            rcsMaxLabel.setText("Flash Max Alpha: " + FLASH_MAX_ALPHA.toString() + when(FLASH_MAX_ALPHA.toString().length) {3->"  " 2->"    " else ->"      "})
        }
        flashMaxAlpha.add(flashMaxAlphaLabel)//.spaceRight(6F) //when gets rid of spaceright
        flashMaxAlpha.add(flashMaxAlphaSlider)

        //Create DRAW_FOV Toggle
        //val drawFov = VisTextButton("DRAW_FOV", "toggle")
//        if (DRAW_FOV) drawFov.toggle()
//        drawFov.changed { _, _ ->
//            if (true) { //type Any? changes didnt work im autistic //fix later
//                DRAW_FOV = !DRAW_FOV
//            }
//        }

        //Create ENEMY_INDICATOR Toggle
        //val enemyIndicator = VisTextButton("ENEMY_INDICATOR", "toggle")
        if (ENEMY_INDICATOR) enemyIndicator.toggle()
        enemyIndicator.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENEMY_INDICATOR = enemyIndicator.isChecked//!ENEMY_INDICATOR
            }
        }

        table.add(boneTriggerFov).row() //Add Bone_Trigger_Fov Slider
        table.add(boneTriggerBone).row() //Add Bone_Trigger_Bone Selector
        table.add(aimOnBoneTrigger).row() //Add Aim_On_Bone_Trigger Toggle
        table.add(boneTriggerEnableKey).row() //Add Bone_Trigger_Enable_Key
        table.add(boneTriggerKey).row() //Add Bone_Trigger_Key Field
        table.add(rcsMin).row() //Add RCS_Min Slider
        table.add(rcsMax).row() //Add RCS_Max Slider
        table.add(rcsMinDuration).row() //Add RCS_Min_Duration Slider
        table.add(rcsMaxDuration).row() //Add RCS_Max_Duration Slider
        table.add(flashMaxAlpha).row() //Add Flash_Max_Alpha Slider
        //table.add(drawFov).row() //Add Draw_Fov Toggle
        table.add(enemyIndicator).row() //Add Enemy_Indicator Toggle
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Misc"
    }
}