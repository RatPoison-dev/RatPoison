package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.ui.bTrigTab
import rat.poison.ui.changed
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom

class BTrigTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableAutoKnife = VisCheckBoxCustom("Enable Auto Knife", "ENABLE_AUTO_KNIFE")
    val enableBoneTrigger = VisCheckBoxCustom("Enable Bone Trigger", "ENABLE_BONE_TRIGGER")

    val boneTriggerDelay = VisSliderCustom("Shot Delay", "BONE_TRIGGER_SHOT_DELAY", 0F, 1000F, 5F, true)
    val boneTriggerCheckHead = VisCheckBoxCustom("Head", "BONE_TRIGGER_HB")
    val boneTriggerCheckBody = VisCheckBoxCustom("Torso", "BONE_TRIGGER_BB")

    val boneTriggerPistols = VisCheckBoxCustom("Pistols", "BONE_TRIGGER_PISTOLS")
    val boneTriggerRifles = VisCheckBoxCustom("Rifles", "BONE_TRIGGER_RIFLES")
    val boneTriggerSmgs = VisCheckBoxCustom("Smgs", "BONE_TRIGGER_SMGS")
    val boneTriggerSnipers = VisCheckBoxCustom("Snipers", "BONE_TRIGGER_SNIPERS")
    val boneTriggerShotguns = VisCheckBoxCustom("Shotguns", "BONE_TRIGGER_SHOTGUNS")

    val boneTriggerPistolsAimbot = VisCheckBoxCustom("Aimbot", "BONE_TRIGGER_PISTOLS_AIMBOT")
    val boneTriggerRiflesAimbot = VisCheckBoxCustom("Aimbot", "BONE_TRIGGER_RIFLES_AIMBOT")
    val boneTriggerSmgsAimbot = VisCheckBoxCustom("Aimbot", "BONE_TRIGGER_SMGS_AIMBOT")
    val boneTriggerSnipersAimbot = VisCheckBoxCustom("Aimbot", "BONE_TRIGGER_SNIPERS_AIMBOT")
    val boneTriggerShotgunsAimbot = VisCheckBoxCustom("Aimbot", "BONE_TRIGGER_SHOTGUNS_AIMBOT")

    val boneTriggerPistolsFOV = VisSliderCustom("Pistol FOV", "BONE_TRIGGER_PISTOLS_FOV", 1F, 90F, 1F, true)
    val boneTriggerRiflesFOV = VisSliderCustom("Rifle FOV", "BONE_TRIGGER_RIFLES_FOV", 1F, 90F, 1F, true)
    val boneTriggerSmgsFOV = VisSliderCustom("Smg FOV", "BONE_TRIGGER_SMGS_FOV", 1F, 90F, 1F, true)
    val boneTriggerSnipersFOV = VisSliderCustom("Sniper FOV", "BONE_TRIGGER_SNIPERS_FOV", 1F, 90F, 1F, true)
    val boneTriggerShotgunsFOV = VisSliderCustom("Shotgun FOV", "BONE_TRIGGER_SHOTGUNS_FOV", 1F, 90F, 1F, true)

    val boneTriggerEnableKey = VisCheckBoxCustom("Bone Trigger On Key", "BONE_TRIGGER_ENABLE_KEY")
    val boneTriggerKey = VisInputFieldCustom("Bone Trigger Key", "BONE_TRIGGER_KEY")
    //val boneTriggerKeyField = VisValidatableTextField(Validators.FLOATS)


    init {
        val boneTriggerWeapons = VisTable()
        boneTriggerWeapons.add(boneTriggerPistols).left()
        boneTriggerWeapons.add(boneTriggerPistolsAimbot).left().row()
        boneTriggerWeapons.add(boneTriggerPistolsFOV).left().colspan(2).spaceBottom(20F).row()

        boneTriggerWeapons.add(boneTriggerRifles).left()
        boneTriggerWeapons.add(boneTriggerRiflesAimbot).left().row()
        boneTriggerWeapons.add(boneTriggerRiflesFOV).left().colspan(2).spaceBottom(20F).row()

        boneTriggerWeapons.add(boneTriggerSmgs).left()
        boneTriggerWeapons.add(boneTriggerSmgsAimbot).left().row()
        boneTriggerWeapons.add(boneTriggerSmgsFOV).left().colspan(2).spaceBottom(20F).row()

        boneTriggerWeapons.add(boneTriggerSnipers).left()
        boneTriggerWeapons.add(boneTriggerSnipersAimbot).left().row()
        boneTriggerWeapons.add(boneTriggerSnipersFOV).left().colspan(2).spaceBottom(20F).row()

        boneTriggerWeapons.add(boneTriggerShotguns).left().padRight(10F)
        boneTriggerWeapons.add(boneTriggerShotgunsAimbot).left().row()
        boneTriggerWeapons.add(boneTriggerShotgunsFOV).left().colspan(2).spaceBottom(20F).row()

        table.padLeft(25F)
        table.padRight(25F)

        table.add(enableAutoKnife).left().row()
        table.addSeparator()
        table.add(enableBoneTrigger).left().row()
        table.add(boneTriggerDelay).left().row()
        table.add(boneTriggerCheckHead).left().row()
        table.add(boneTriggerCheckBody).left().row()
        table.add(boneTriggerEnableKey).left().row()
        table.add(boneTriggerKey).padLeft(20F).left().row()
        table.add(boneTriggerWeapons).left().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "BTrig"
    }
}

fun bTrigTabUpdate() {
    bTrigTab.apply {
        enableAutoKnife.update()
        enableBoneTrigger.update()
        boneTriggerDelay.update()
        boneTriggerCheckHead.update()
        boneTriggerCheckBody.update()
        boneTriggerPistols.update()
        boneTriggerRifles.update()
        boneTriggerSmgs.update()
        boneTriggerSnipers.update()
        boneTriggerShotguns.update()
        boneTriggerPistolsAimbot.update()
        boneTriggerRiflesAimbot.update()
        boneTriggerSmgsAimbot.update()
        boneTriggerSnipersAimbot.update()
        boneTriggerShotgunsAimbot.update()
        boneTriggerPistolsFOV.update()
        boneTriggerRiflesFOV.update()
        boneTriggerSmgsFOV.update()
        boneTriggerSnipersFOV.update()
        boneTriggerShotgunsFOV.update()
        boneTriggerEnableKey.update()
        boneTriggerKey.update()
    }
}