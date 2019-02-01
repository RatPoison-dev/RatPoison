package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.bTrigTab
import rat.poison.ui.changed
import rat.poison.ui.mainTabbedPane

class BTrig : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableBoneTrigger = VisCheckBox("Enable Bone Trigger") //Bone_Trigger
    val boneTriggerFovLabel = VisLabel("Bone Trigger Fov: " + BONE_TRIGGER_FOV.toString() + when(BONE_TRIGGER_FOV.toString().length) {3->"  " 2->"    " else ->"      "}) //Bone_Trigger_Fov
    val boneTriggerFovSlider = VisSlider(0F, 360F, 1F, false) //Bone_Trigger_Fov
    val boneTriggerBoneBox = VisSelectBox<String>() //Bone_Trigger_Bone
    val aimOnBoneTrigger = VisCheckBox("Enable Aim On Bone Trigger") //Aim_On_Bone_Trigger
    val boneTriggerEnableKey = VisCheckBox("Enable Bone Trigger On Key") //Bone_Trigger_Enable_Key
    val boneTriggerKeyField = VisValidatableTextField(Validators.FLOATS) //Bone_Trigger_Key

    init {
        //Create Enable_Bone_Trigger Toggle
        //val enableBoneTrigger = VisTextButton("ENABLE_BONE_TRIGGER", "toggle")
        Tooltip.Builder("Whether or not to enable bone trigger").target(enableBoneTrigger).build()
        enableBoneTrigger.isChecked = ENABLE_BONE_TRIGGER
        enableBoneTrigger.changed { _, _ ->
            ENABLE_BONE_TRIGGER = enableBoneTrigger.isChecked
            true
        }

        //Create Bone_Trigger_Fov Slider
        val boneTriggerFov = VisTable()
        Tooltip.Builder("The bone trigger field of view").target(boneTriggerFov).build()
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
        Tooltip.Builder("The aim bone that bone trigger will fire at").target(boneTriggerBone).build()
        //val boneTriggerBoneBox = VisSelectBox<String>()
        val boneTriggerBoneLabel = VisLabel("Bone Trigger Bone: ")
        boneTriggerBoneBox.setItems("Head Bone", "Body Bone")
        boneTriggerBoneBox.selected = if (BONE_TRIGGER_BONE == HEAD_BONE) "Head Bone" else "Body Bone"
        boneTriggerBone.add(boneTriggerBoneLabel).top().spaceRight(6F)
        boneTriggerBone.add(boneTriggerBoneBox)

        boneTriggerBoneBox.changed { _, _ ->
            if (boneTriggerBoneBox.selected.toString() == "Head Bone") {
                BONE_TRIGGER_BONE = HEAD_BONE
            }
            else if (boneTriggerBoneBox.selected.toString() == "Body Bone") {
                BONE_TRIGGER_BONE = BODY_BONE
            }
        }

        //Create Aim_On_Bone_Trigger Toggle
        //val aimOnBoneTrigger = VisTextButton("AIM_ON_BONE_TRIGGER", "toggle")
        Tooltip.Builder("Whether or not to use current aim configuration with bone trigger").target(aimOnBoneTrigger).build()
        if (AIM_ON_BONE_TRIGGER) aimOnBoneTrigger.toggle()
        aimOnBoneTrigger.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                AIM_ON_BONE_TRIGGER = aimOnBoneTrigger.isChecked//AIM_ON_BONE_TRIGGER
            }
        }

        //Create Bone_Trigger_Enable_Key Toggle
        //val boneTriggerEnableKey = VisTextButton("BONE_TRIGGER_ENABLE_KEY", "toggle")
        Tooltip.Builder("Whether or not bone trigger activates when an aim key is held down").target(boneTriggerEnableKey).build()
        if (BONE_TRIGGER_ENABLE_KEY) boneTriggerEnableKey.toggle()
        boneTriggerEnableKey.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                BONE_TRIGGER_ENABLE_KEY = boneTriggerEnableKey.isChecked//!BONE_TRIGGER_ENABLE_KEY
            }
        }

        //Create Bone_Trigger_Key
        val boneTriggerKey = VisTable()
        Tooltip.Builder("The key bone trigger will check is being held down if BONE_TRIGGER_ENABLE_KEY is enabled").target(boneTriggerKey).build()
        val boneTriggerKeyLabel = VisLabel("Bone Trigger Key: ")
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

        //Add all items to label for tabbed pane content
        table.add(enableBoneTrigger).row() //Add Enable_Bone_Trigger Toggle
        table.add(boneTriggerFov).row() //Add Bone_Trigger_Fov Slider
        table.add(boneTriggerBone).row() //Add Bone_Trigger_Bone Selector
        table.add(aimOnBoneTrigger).row() //Add Aim_On_Bone_Trigger Toggle
        table.add(boneTriggerEnableKey).row() //Add Bone_Trigger_Enable_Key
        table.add(boneTriggerKey).row() //Add Bone_Trigger_Key Field
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Bone Trigger"
    }
}