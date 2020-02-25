package rat.poison.ui.tabs

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.util.Validators
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.curSettings
import rat.poison.scripts.forcedUpdate
import rat.poison.scripts.skinChanger
import rat.poison.toSkinWeaponClass
import rat.poison.ui.*
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom

class SkinChangerTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    private var enableSkinChanger = VisCheckBoxCustom("Enable Skinchanger", "SKINCHANGER")
    private var warningLabel = VisLabel("This is experimental and is not what the completed\n" +
            " version will look like just a temporary version to show\n" +
            "it works, updates are in progress, and to report any issues.\n" +
            "To find the correct ID use csgostash.com and use the\n" +
            "'Finish Catalog' number.\n\n" +
            "May be cpu intensive. When enabled, press force update\n" +
            "to apply skins.")

    private var categorySelectionBox = VisSelectBox<String>()
    private var weaponSelectionBox = VisSelectBox<String>()

    private var skinIDInput = VisValidatableTextField(Validators.INTEGERS)
    private var skinStatTrak = VisValidatableTextField(Validators.INTEGERS)
    private var skinWear = VisValidatableTextField(Validators.FLOATS)

    private var forceUpdate = VisTextButton("Manual Force Update")
    private var autoForceUpdate = VisCheckBoxCustom("Auto Force Update", "FORCE_UPDATE_AUTO")

    private var weaponSelected = "DESERT_EAGLE"

    init {
        categorySelectionBox.setItems("PISTOL", "RIFLE", "SMG", "SNIPER", "SHOTGUN")
        categorySelectionBox.selected = "PISTOL"
        weaponSelectionBox.selected = "DESERT_EAGLE"

        categorySelectionBox.changed { _, _ ->
            when (categorySelectionBox.selected) {
                "PISTOL" -> { weaponSelectionBox.clearItems(); weaponSelectionBox.setItems("DESERT_EAGLE", "DUAL_BERRETA", "FIVE_SEVEN", "GLOCK", "USP_SILENCER", "CZ75A", "R8_REVOLVER", "P2000", "TEC9", "P250") }
                "SMG" -> { weaponSelectionBox.clearItems(); weaponSelectionBox.setItems("MAC10", "P90", "MP5", "UMP45", "MP7", "MP9", "PP_BIZON") }
                "RIFLE" -> { weaponSelectionBox.clearItems(); weaponSelectionBox.setItems("AK47", "AUG", "FAMAS", "SG553", "GALIL", "M4A4", "M4A1_SILENCER", "NEGEV", "M249") }
                "SNIPER" -> { weaponSelectionBox.clearItems(); weaponSelectionBox.setItems("AWP", "G3SG1", "SCAR20", "SSG08") }
                "SHOTGUN" -> { weaponSelectionBox.clearItems(); weaponSelectionBox.setItems("XM1014", "MAG7", "SAWED_OFF", "NOVA") }
            }

            weaponSelectionBox.selected = weaponSelectionBox.items[0]
            true
        }

        weaponSelectionBox.changed { _, _ ->
            if (!weaponSelectionBox.selected.isNullOrEmpty()) {
                weaponSelected = weaponSelectionBox.selected

                val skinWep = curSettings["SKIN_$weaponSelected"].toSkinWeaponClass()

                skinIDInput.text = skinWep.tSkinID.toString()
                skinStatTrak.text = skinWep.tStatTrak.toString()
                skinWear.text = skinWep.tWear.toString()
            }
        }

        skinIDInput.changed { _, _ ->
            val skinWep = curSettings["SKIN_$weaponSelected"].toSkinWeaponClass()
            if (skinIDInput.isInputValid) {
                skinWep.tSkinID = skinIDInput.text.toInt()
                curSettings["SKIN_$weaponSelected"] = skinWep.toString()
            }

            true
        }

        skinStatTrak.changed { _, _ ->
            val skinWep = curSettings["SKIN_$weaponSelected"].toSkinWeaponClass()
            if (skinStatTrak.isInputValid) {
                skinWep.tStatTrak = skinStatTrak.text.toInt()
                curSettings["SKIN_$weaponSelected"] = skinWep.toString()
            }
        }

        skinWear.changed { _, _ ->
            val skinWep = curSettings["SKIN_$weaponSelected"].toSkinWeaponClass()
            if (skinWear.isInputValid) {
                skinWep.tWear = skinWear.text.toFloat()
                curSettings["SKIN_$weaponSelected"] = skinWep.toString()
            }
        }

        enableSkinChanger.changed { _, _ ->
            skinChanger()
        }

        forceUpdate.changed { _, _ ->
            forcedUpdate()

            true
        }
        ////////////////////FORMATTING
        table.padLeft(25F)
        table.padRight(25F)

        table.add(warningLabel).row()
        table.add(enableSkinChanger).row()
        table.add(categorySelectionBox).row()
        table.add(weaponSelectionBox).row()

        table.add(skinIDInput).row()
        table.add(skinStatTrak).row()
        table.add(skinWear).row()

        table.add(forceUpdate).row()
        table.add(autoForceUpdate)

        ////////////////////FORMATTING
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Skin Changer"
    }
}

fun skinChangerTabUpdate() {

}