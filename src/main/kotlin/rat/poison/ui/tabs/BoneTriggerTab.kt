package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.boolToStr
import rat.poison.curSettings
import rat.poison.strToBool
import rat.poison.ui.changed

class BTrigTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableAutoKnife = VisCheckBox("Enable Auto Knife")
    val enableBoneTrigger = VisCheckBox("Enable Bone Trigger")
    val boneTriggerFovLabel = VisLabel("FOV: " + curSettings["BONE_TRIGGER_FOV"] + when(curSettings["BONE_TRIGGER_FOV"]!!.length) {3->"  " 2->"    " else ->"      "})
    val boneTriggerFovSlider = VisSlider(0F, 200F, 1F, false)
    val boneTriggerDelayLabel = VisLabel("Shot Delay: " + curSettings["BONE_TRIGGER_SHOT_DELAY"] + when(curSettings["BONE_TRIGGER_SHOT_DELAY"]!!.length) {3->"  " 2->"    " else ->"      "})
    val boneTriggerDelaySlider = VisSlider(0F, 200F, 1F, false)
    val boneTriggerBoneBox = VisSelectBox<String>()
    val boneTriggerCheckHead = VisCheckBox("Head")
    val boneTriggerCheckBody = VisCheckBox("Torso")
    val aimOnBoneTrigger = VisCheckBox("Enable Aimbot")
    val boneTriggerEnableKey = VisCheckBox("Bone Trigger On Key")
    val boneTriggerKeyField = VisValidatableTextField(Validators.FLOATS)

    init {
        //Create Auto Knife Toggle
        Tooltip.Builder("Whether or not to auto knife when available").target(enableAutoKnife).build()
        enableAutoKnife.isChecked = curSettings["ENABLE_AUTO_KNIFE"]!!.strToBool()
        enableAutoKnife.changed { _, _ ->
            curSettings["ENABLE_AUTO_KNIFE"] = enableAutoKnife.isChecked.boolToStr()
            true
        }

        //Create Bone Trigger Toggle
        Tooltip.Builder("Whether or not to enable bone trigger").target(enableBoneTrigger).build()
        enableBoneTrigger.isChecked = curSettings["ENABLE_BONE_TRIGGER"]!!.strToBool()
        enableBoneTrigger.changed { _, _ ->
            curSettings["ENABLE_BONE_TRIGGER"] = enableBoneTrigger.isChecked.boolToStr()
            true
        }

        //Create Bone Trigger FOV Slider
        val boneTriggerFov = VisTable()
        Tooltip.Builder("The bone trigger field of view").target(boneTriggerFov).build()
        boneTriggerFovSlider.value = curSettings["BONE_TRIGGER_FOV"]!!.toFloat()
        boneTriggerFovSlider.changed { _, _ ->
            curSettings["BONE_TRIGGER_FOV"] = boneTriggerFovSlider.value.toInt().toString()
            boneTriggerFovLabel.setText("FOV: " + curSettings["BONE_TRIGGER_FOV"] + when(curSettings["BONE_TRIGGER_FOV"]!!.length) {3->"  " 2->"    " else ->"      "})
        }

        boneTriggerFov.add(boneTriggerFovLabel).width(200F)
        boneTriggerFov.add(boneTriggerFovSlider).width(250F)

        //Create Bone Trigger Shot Delay Slider
        val boneTriggerDelay = VisTable()
        Tooltip.Builder("The shot delay of bone trigger").target(boneTriggerDelay).build()
        boneTriggerDelaySlider.value = curSettings["BONE_TRIGGER_SHOT_DELAY"]!!.toFloat()
        boneTriggerDelaySlider.changed { _, _ ->
            curSettings["BONE_TRIGGER_SHOT_DELAY"] = boneTriggerDelaySlider.value.toInt().toString()
            boneTriggerDelayLabel.setText("Shot Delay: " + curSettings["BONE_TRIGGER_SHOT_DELAY"] + when(curSettings["BONE_TRIGGER_SHOT_DELAY"]!!.length) {3->"  " 2->"    " else ->"      "})
        }

        boneTriggerDelay.add(boneTriggerDelayLabel).width(200F)
        boneTriggerDelay.add(boneTriggerDelaySlider).width(250F)

        //Create Bone Trigger Head Bone Check Box
        Tooltip.Builder("Whether to trigger on head bone").target(boneTriggerCheckHead).build()
        if (curSettings["BONE_TRIGGER_HB"]!!.strToBool()) boneTriggerCheckHead.toggle()
        boneTriggerCheckHead.changed { _, _ ->
            curSettings["BONE_TRIGGER_HB"] = boneTriggerCheckHead.isChecked.boolToStr()
            true
        }

        //Create Bone Trigger Body Bone Check Box
        Tooltip.Builder("Whether to trigger on body bone").target(boneTriggerCheckBody).build()
        if (curSettings["BONE_TRIGGER_BB"]!!.strToBool()) boneTriggerCheckBody.toggle()
        boneTriggerCheckBody.changed { _, _ ->
            curSettings["BONE_TRIGGER_BB"] = boneTriggerCheckBody.isChecked.boolToStr()
            true
        }

        //Create Aim On Bone Trigger Toggle
        Tooltip.Builder("Whether or not to use current aim configuration with bone trigger").target(aimOnBoneTrigger).build()
        if (curSettings["AIM_ON_BONE_TRIGGER"]!!.strToBool()) aimOnBoneTrigger.toggle()
        aimOnBoneTrigger.changed { _, _ ->
            curSettings["AIM_ON_BONE_TRIGGER"] = aimOnBoneTrigger.isChecked.boolToStr()
            true
        }

        //Create Bone Trigger Enable Key Toggle
        Tooltip.Builder("Whether or not bone trigger activates when an aim key is held down").target(boneTriggerEnableKey).build()
        if (curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool()) boneTriggerEnableKey.toggle()
        boneTriggerEnableKey.changed { _, _ ->
            curSettings["BONE_TRIGGER_ENABLE_KEY"] = boneTriggerEnableKey.isChecked.boolToStr()
            true
        }

        //Create Bone Trigger Key Input Box
        val boneTriggerKey = VisTable()
        Tooltip.Builder("The key bone trigger will check is being held down if BONE_TRIGGER_ENABLE_KEY is enabled").target(boneTriggerKey).build()
        val boneTriggerKeyLabel = VisLabel("Bone Trigger Key: ")
        boneTriggerKeyField.text = curSettings["BONE_TRIGGER_KEY"]
        boneTriggerKey.changed { _, _ ->
            if (boneTriggerKeyField.text.toIntOrNull() != null) {
                curSettings["BONE_TRIGGER_KEY"] = boneTriggerKeyField.text.toInt()
            }
        }
        boneTriggerKey.add(boneTriggerKeyLabel)
        boneTriggerKey.add(boneTriggerKeyField).spaceRight(6F).width(40F)
        boneTriggerKey.add(LinkLabel("?", "http://cherrytree.at/misc/vk.htm"))

        table.padLeft(25F)
        table.padRight(25F)

        table.add(enableAutoKnife).left().row()
        table.addSeparator()
        table.add(enableBoneTrigger).left().row()
        table.add(boneTriggerFov).left().row()
        table.add(boneTriggerDelay).left().row()
        table.add(boneTriggerCheckHead).left().row()
        table.add(boneTriggerCheckBody).left().row()
        table.add(aimOnBoneTrigger).left().row()
        table.add(boneTriggerEnableKey).left().row()
        table.add(boneTriggerKey).padLeft(20F).left().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Bone Trigger"
    }
}