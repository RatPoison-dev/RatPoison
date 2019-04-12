package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.settings.*
import rat.poison.ui.bTrigTab
import rat.poison.ui.changed
import rat.poison.ui.mainTabbedPane

class BTrigTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableBoneTrigger = VisCheckBox("Enable Bone Trigger")
    val boneTriggerFovLabel = VisLabel("Bone Trigger Fov: " + BONE_TRIGGER_FOV.toString() + when(BONE_TRIGGER_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
    val boneTriggerFovSlider = VisSlider(0F, 360F, 1F, false)
    val boneTriggerBoneBox = VisSelectBox<String>()
    val boneTriggerCheckHead = VisCheckBox("Head Bone")
    val boneTriggerCheckBody = VisCheckBox("Body Bone")
    val aimOnBoneTrigger = VisCheckBox("Enable Aim On Bone Trigger")
    val boneTriggerEnableKey = VisCheckBox("Enable Bone Trigger On Key")
    val boneTriggerKeyField = VisValidatableTextField(Validators.FLOATS)

    init {
        //Create Enable_Bone_Trigger Toggle
        Tooltip.Builder("Whether or not to enable bone trigger").target(enableBoneTrigger).build()
        enableBoneTrigger.isChecked = ENABLE_BONE_TRIGGER
        enableBoneTrigger.changed { _, _ ->
            ENABLE_BONE_TRIGGER = enableBoneTrigger.isChecked
            true
        }

        //Create Bone_Trigger_Fov Slider
        val boneTriggerFov = VisTable()
        Tooltip.Builder("The bone trigger field of view").target(boneTriggerFov).build()
        boneTriggerFovSlider.value = BONE_TRIGGER_FOV.toFloat()
        boneTriggerFovSlider.changed { _, _ ->
            BONE_TRIGGER_FOV = boneTriggerFovSlider.value.toInt()
            boneTriggerFovLabel.setText("Bone Trigger Fov: " + BONE_TRIGGER_FOV.toString() + when(BONE_TRIGGER_FOV.toString().length) {3->"  " 2->"    " else ->"      "})
        }

        boneTriggerFov.add(boneTriggerFovLabel).spaceRight(6F)
        boneTriggerFov.add(boneTriggerFovSlider)

        //Create Bone Trigger Head Bone
        Tooltip.Builder("Whether to trigger on head bone").target(boneTriggerCheckHead).build()
        if (BONE_TRIGGER_HB) boneTriggerCheckHead.toggle()
        boneTriggerCheckHead.changed { _, _ ->
            BONE_TRIGGER_HB = boneTriggerCheckHead.isChecked
            true
        }

        //Create Bone Trigger Body Bone
        Tooltip.Builder("Whether to trigger on body bone").target(boneTriggerCheckBody).build()
        if (BONE_TRIGGER_BB) boneTriggerCheckBody.toggle()
        boneTriggerCheckBody.changed { _, _ ->
            BONE_TRIGGER_BB = boneTriggerCheckBody.isChecked
            true
        }

        //Create Aim_On_Bone_Trigger Toggle
        Tooltip.Builder("Whether or not to use current aim configuration with bone trigger").target(aimOnBoneTrigger).build()
        if (AIM_ON_BONE_TRIGGER) aimOnBoneTrigger.toggle()
        aimOnBoneTrigger.changed { _, _ ->
            AIM_ON_BONE_TRIGGER = aimOnBoneTrigger.isChecked
            true
        }

        //Create Bone_Trigger_Enable_Key Toggle
        Tooltip.Builder("Whether or not bone trigger activates when an aim key is held down").target(boneTriggerEnableKey).build()
        if (BONE_TRIGGER_ENABLE_KEY) boneTriggerEnableKey.toggle()
        boneTriggerEnableKey.changed { _, _ ->
            BONE_TRIGGER_ENABLE_KEY = boneTriggerEnableKey.isChecked
            true
        }

        //Create Bone_Trigger_Key
        val boneTriggerKey = VisTable()
        Tooltip.Builder("The key bone trigger will check is being held down if BONE_TRIGGER_ENABLE_KEY is enabled").target(boneTriggerKey).build()
        val boneTriggerKeyLabel = VisLabel("Bone Trigger Key: ")
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
        table.add(enableBoneTrigger).row()
        table.add(boneTriggerFov).row()
        table.add(boneTriggerCheckHead).row()
        table.add(boneTriggerCheckBody).row()
        table.add(aimOnBoneTrigger).row()
        table.add(boneTriggerEnableKey).row()
        table.add(boneTriggerKey).row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Bone Trigger"
    }
}