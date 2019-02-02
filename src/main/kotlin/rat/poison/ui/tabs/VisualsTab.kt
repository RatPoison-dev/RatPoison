package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.*
import com.kotcrab.vis.ui.widget.color.ColorPicker
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.App
import rat.poison.scripts.esp.disableEsp
import rat.poison.settings.*
import rat.poison.ui.UIUpdate
import rat.poison.ui.changed

class VisualsTab : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableEsp = VisCheckBox("Enable Esp") //ESP
    val skeletonEsp = VisCheckBox("Skeleton Esp") //Skeleton_Esp
    val boxEsp = VisCheckBox("Box Esp") //Box_Esp
    val boxEspDetails = VisCheckBox("Box Esp Details") //Box_Esp_Details
    val glowEsp = VisCheckBox("Glow Esp") //Glow_Esp
    val invGlowEsp = VisCheckBox("Inv Glow Esp") //Inv_Glow_Esp
    val modelEsp = VisCheckBox("Model Esp") //Model_Esp
    val modelAndGlow = VisCheckBox("Model & Glow Esp") //Model_And_Glow
    val enemyIndicator = VisCheckBox("Enemy Indicator") //Enemy_Indicator
    val hitSound = VisCheckBox("Hitsound") //Hit_Sound
    val hitSoundVolumeLabel = VisLabel("Hitsound Volume: $HITSOUND_VOLUME") //Hit_Sound_Volume
    val hitSoundVolumeSlider = VisSlider(0.1F, 1F, 0.1F, false) //Hit_Sound_Volume
    val chamsEsp = VisCheckBox("Chams Esp") //Chams_Esp
    val chamsShowHealth = VisCheckBox("Chams Show Health") //Chams_Show_Health
    val chamsBrightnessLabel = VisLabel("Chams Brightness: $CHAMS_BRIGHTNESS" + when(CHAMS_BRIGHTNESS.toString().length) {4->"" 3->"  " 2->"    " else ->"      "}) //Chams_Brightness
    val chamsBrightnessSlider = VisSlider(0F, 1000F, 1F, false) //Chams_Brightness
    val showTeam = VisCheckBox("Show Team") //Show_Team
    val showEnemies = VisCheckBox("Show Enemies") //Show_Enemies
    val showDormant = VisCheckBox("Show Dormant") //Show_Dormant
    val showBomb = VisCheckBox("Show Bomb") //Show_Bomb
    val showWeapons = VisCheckBox("Show Weapons") //Show_Weapons
    val showGrenades = VisCheckBox("Show Grenades") //Show_Grenades
    val teamColorShow = VisTextButton("Set Team Color") //Team_Color
    val enemyColorShow = VisTextButton("Set Enemy Color") //Enemy_Color
    val bombColorShow = VisTextButton("Set Bomb Color") //Bomb_Color
    val weaponColorShow = VisTextButton("Set Weapon Color") //Weapon_Color
    val grenadeColor = VisTextButton("Set Grenade Color") //Grenade_Color


    init {
        //Create Enable_Esp Toggle
        //val enableEsp = VisTextButton("ENABLE_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable esp").target(enableEsp).build()
        enableEsp.isChecked = ENABLE_ESP
        enableEsp.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_ESP = enableEsp.isChecked

                if (!ENABLE_ESP) {
                    disableEsp()
                }
            }
        }

        //Create Skeleton_Esp Toggle
        //val skeletonEsp = VisTextButton("SKELETON_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable skeleton esp").target(skeletonEsp).build()
        if (SKELETON_ESP) skeletonEsp.toggle()
        skeletonEsp.changed { _, _ ->
            if (true) {
                SKELETON_ESP = skeletonEsp.isChecked//!SKELETON_ESP
            }
        }

        //Create Box_Esp Toggle
        //val boxEsp = VisTextButton("BOX_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable box esp").target(boxEsp).build()
        if (BOX_ESP) boxEsp.toggle()
        boxEsp.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                BOX_ESP = boxEsp.isChecked//!BOX_ESP
            }
        }

        //Create Box_Esp_Details Toggle
        //val boxEspDetails = VisTextButton("BOX_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable details with box esp if box esp is enabled").target(boxEspDetails).build()
        if (BOX_ESP_DETAILS) boxEspDetails.toggle()
        boxEspDetails.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                BOX_ESP_DETAILS = boxEspDetails.isChecked//!BOX_ESP

                if (BOX_ESP_DETAILS) {
                    BOX_ESP = true
                }
                UIUpdate()
            }
        }

        //Create Glow_Esp Toggle
        //val glowEsp = VisTextButton("GLOW_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable glow esp").target(glowEsp).build()
        if (GLOW_ESP) glowEsp.toggle()
        glowEsp.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                GLOW_ESP = glowEsp.isChecked//!GLOW_ESP
                if (!GLOW_ESP) {
                    disableEsp()
                }
            }
        }

        //Create Inv_Glow_Esp Toggle
        //val invGlowEsp = VisTextButton("INV_GLOW_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable inverted glow esp if glow esp is enabled").target(invGlowEsp).build()
        if (INV_GLOW_ESP) invGlowEsp.toggle()
        invGlowEsp.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                INV_GLOW_ESP = invGlowEsp.isChecked//!GLOW_ESP

                if (INV_GLOW_ESP) {
                    GLOW_ESP = true
                }
                UIUpdate()
            }
        }

        //Create Model_Esp Toggle
        //val modelEsp = VisTextButton("MODEL_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable model esp").target(modelEsp).build()
        if (MODEL_ESP) modelEsp.toggle()
        modelEsp.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                MODEL_ESP = modelEsp.isChecked//!MODEL_ESP
            }
        }

        //Create Model_And_Glow Toggle
        //val modelAndGlow = VisTextButton("MODEL_AND_GLOW", "toggle")
        Tooltip.Builder("Whether or not to enable model when visible, glow when not visible esp").target(modelAndGlow).build()
        modelAndGlow.isChecked = MODEL_AND_GLOW
        modelAndGlow.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                MODEL_AND_GLOW = modelAndGlow.isChecked//!MODEL_ESP
            }
        }

        //Create ENEMY_INDICATOR Toggle
        //val enemyIndicator = VisTextButton("ENEMY_INDICATOR", "toggle")
        Tooltip.Builder("Whether or not to enable enemy indicator esp").target(enemyIndicator).build()
        if (ENEMY_INDICATOR) enemyIndicator.toggle()
        enemyIndicator.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENEMY_INDICATOR = enemyIndicator.isChecked//!ENEMY_INDICATOR
            }
        }

        //Create ENEMY_INDICATOR Toggle
        //val hitSound = VisTextButton("ENEMY_INDICATOR", "toggle")
        Tooltip.Builder("Whether or not to enable a hitsound on hit").target(hitSound).build()
        if (ENABLE_HITSOUND) hitSound.toggle()
        hitSound.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                ENABLE_HITSOUND = hitSound.isChecked//!ENEMY_INDICATOR
            }
        }

        //Create Hit_Sound_Volume Slider
        val hitSoundVolume = VisTable()
        Tooltip.Builder("The volume of the hitsound if the hitsound is enabled").target(hitSoundVolume).build()
        //val hitSoundVolumeLabel = VisLabel("Hitsound Volume: " + HITSOUND_VOLUME.toString())
        //val hitSoundVolumeSlider = VisSlider(0.1F, 1F, 0.1F, false)
        hitSoundVolumeSlider.value = HITSOUND_VOLUME.toFloat()
        hitSoundVolumeSlider.changed { _, _ ->
            HITSOUND_VOLUME = Math.round(hitSoundVolumeSlider.value.toDouble() * 10.0)/10.0 //Round to 1 decimal place
            hitSoundVolumeLabel.setText("Hitsound Volume: $HITSOUND_VOLUME")
        }
        hitSoundVolume.add(hitSoundVolumeLabel).spaceRight(6F)
        hitSoundVolume.add(hitSoundVolumeSlider)

        //Create Chams_Esp Toggle
        //val chamsEsp = VisTextButton("CHAMS_ESP", "toggle")
        Tooltip.Builder("Whether or not to enable chams esp").target(chamsEsp).build()
        if (CHAMS_ESP) chamsEsp.toggle()
        chamsEsp.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                CHAMS_ESP = chamsEsp.isChecked//!CHAMS_ESP

                if (!CHAMS_ESP) {
                    disableEsp()
                }
            }
        }

        //Create Chams_Show_Health Toggle
       // val chamsShowHealth = VisTextButton("CHAMS_SHOW_HEALTH", "toggle")
        Tooltip.Builder("Whether or not to enable chams color to be based on health if chams esp is enabled").target(chamsShowHealth).build()
        if (CHAMS_SHOW_HEALTH) chamsShowHealth.toggle()
        chamsShowHealth.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                CHAMS_SHOW_HEALTH = chamsShowHealth.isChecked//!CHAMS_SHOW_HEALTH
            }
        }

        //Create Chams_Brightness Slider
        val chamsBrightness = VisTable()
        //val chamsBrightnessLabel = VisLabel("Chams Brightness: " + CHAMS_BRIGHTNESS.toString() + when(CHAMS_BRIGHTNESS.toString().length) {4->"" 3->"  " 2->"    " else ->"      "})
        Tooltip.Builder("Whether or not to enable chams brightness").target(chamsBrightnessLabel).build()
        //val chamsBrightnessSlider = VisSlider(0F, 1000F, 1F, false)
        Tooltip.Builder("The brightness of chams if chams brightness is enabled").target(chamsBrightnessSlider).build()
        chamsBrightnessSlider.value = CHAMS_BRIGHTNESS.toFloat()
        chamsBrightnessSlider.changed { _, _ ->
            CHAMS_BRIGHTNESS = chamsBrightnessSlider.value.toInt()
            chamsBrightnessLabel.setText("Chams Brightness: $CHAMS_BRIGHTNESS" + when(CHAMS_BRIGHTNESS.toString().length) {4->"" 3->"  " 2->"    " else ->"      "}) //When is used to not make the sliders jitter when you go from 10 to 9, or 100 to 99, as that character space shifts everything, one character is 2 spaces
        }

        chamsBrightness.add(chamsBrightnessLabel).spaceRight(6F)
        chamsBrightness.add(chamsBrightnessSlider)

        //Create Show_Team Toggle
        //val showTeam = VisTextButton("SHOW_TEAM", "toggle")
        Tooltip.Builder("Whether or not to show team with esp").target(showTeam).build()
        if (SHOW_TEAM) showTeam.toggle()
        showTeam.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_TEAM = showTeam.isChecked//!SHOW_TEAM
            }
        }

        //Create Show_Enemies Toggle
        //val showEnemies = VisTextButton("SHOW_ENEMIES", "toggle")
        Tooltip.Builder("Whether or not to show enemies with esp").target(showEnemies).build()
        if (SHOW_ENEMIES) showEnemies.toggle()
        showEnemies.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_ENEMIES = showEnemies.isChecked//!SHOW_ENEMIES
            }
        }

        //Create Show_Dormant Toggle
        //val showDormant = VisTextButton("SHOW_DORMANT", "toggle")
        Tooltip.Builder("Whether or not to show dormant entities with esp").target(showDormant).build()
        if (SHOW_DORMANT) showDormant.toggle()
        showDormant.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_DORMANT = showDormant.isChecked//!SHOW_DORMANT
            }
        }

        //Create Show_Bomb Toggle
        //val showBomb = VisTextButton("SHOW_BOMB", "toggle")
        Tooltip.Builder("Whether or not to show bomb with esp").target(showBomb).build()
        if (SHOW_BOMB) showBomb.toggle()
        showBomb.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_BOMB = showBomb.isChecked//!SHOW_BOMB
            }
        }

        //Create Show_Bomb Toggle
        //val showWeapons = VisTextButton("SHOW_WEAPONS", "toggle")
        Tooltip.Builder("Whether or not to show eapons with esp").target(showWeapons).build()
        if (SHOW_WEAPONS) showWeapons.toggle()
        showWeapons.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_WEAPONS = showWeapons.isChecked//!SHOW_WEAPONS
            }
        }

        //Create Show_Bomb Toggle
        //val showGrenades = VisTextButton("SHOW_GRENADES", "toggle")
        Tooltip.Builder("Whether or not to show grenades with esp").target(showGrenades).build()
        if (SHOW_GRENADES) showGrenades.toggle()
        showGrenades.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_GRENADES = showGrenades.isChecked//!SHOW_GRENADES
            }
        }

        //VisImage(Color) doesnt work??
        //Create Team_Color Picker
        val teamColor = VisTable()
        Tooltip.Builder("The esp color of teammates").target(teamColor).build()
        //val teamColorShow = VisTextButton("Set Team Color")
        teamColorShow.setColor(TEAM_COLOR.red.toFloat(), TEAM_COLOR.green.toFloat(), TEAM_COLOR.blue.toFloat(), 1F/*TEAM_COLOR.alpha.toFloat()*/)
        val teamColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                TEAM_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                teamColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        teamColorShow.changed { _, _ ->
            App.stage.addActor(teamColorPicker.fadeIn())
        }

        teamColor.add(teamColorShow)

        //Create Enemy_Color Picker
        val enemyColor = VisTable()
        Tooltip.Builder("The esp color of enemies").target(enemyColor).build()
        //val enemyColorShow = VisTextButton("Set Enemy Color")
        enemyColorShow.setColor(ENEMY_COLOR.red.toFloat(), ENEMY_COLOR.green.toFloat(), ENEMY_COLOR.blue.toFloat(), 1F/*ENEMY_COLOR.alpha.toFloat()*/)
        val enemyColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                ENEMY_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                enemyColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        //enemyColorPicker

        enemyColorShow.changed { _, _ ->
            App.stage.addActor(enemyColorPicker.fadeIn())
        }

        enemyColor.add(enemyColorShow)

        //Create Bomb_Color Picker
        val bombColor = VisTable()
        Tooltip.Builder("The esp color of the bomb").target(bombColor).build()
        //val bombColorShow = VisTextButton("Set Bomb Color")
        bombColorShow.setColor(BOMB_COLOR.red.toFloat(), BOMB_COLOR.green.toFloat(), BOMB_COLOR.blue.toFloat(), 1F/*BOMB_COLOR.alpha.toFloat()*/)
        val bombColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                BOMB_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                bombColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        bombColorShow.changed { _, _ ->
            App.stage.addActor(bombColorPicker.fadeIn())
        }

        bombColor.add(bombColorShow)

        //Create Weapon_Color Picker
        val weaponColor = VisTable()
        Tooltip.Builder("The esp color of weapons").target(weaponColor).build()
        //val weaponColorShow = VisTextButton("Set Weapon Color")
        weaponColorShow.setColor(WEAPON_COLOR.red.toFloat(), WEAPON_COLOR.green.toFloat(), WEAPON_COLOR.blue.toFloat(), 1F/*WEAPON_COLOR.alpha.toFloat()*/)
        val weaponColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                WEAPON_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                weaponColorShow.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        weaponColorShow.changed { _, _ ->
            App.stage.addActor(weaponColorPicker.fadeIn())
        }

        weaponColor.add(weaponColorShow)

        //Create Grenade_Color Picker
        //val grenadeColorShow = VisTextButton("Set Weapon Color")
        grenadeColor.setColor(GRENADE_COLOR.red.toFloat(), GRENADE_COLOR.green.toFloat(), GRENADE_COLOR.blue.toFloat(), 1F/*GRENADE_COLOR.alpha.toFloat()*/)
        Tooltip.Builder("The esp color of grenades").target(grenadeColor).build()
        val grenadeColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                GRENADE_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                newCol.a = 1F
                grenadeColor.color = newCol
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        grenadeColor.changed { _, _ ->
            App.stage.addActor(grenadeColorPicker.fadeIn())
        }


        //Add all items to label for tabbed pane content
        table.columnDefaults(2)

        table.add(enableEsp).colspan(2).row() //Add Enable_RCrosshair Toggle

        table.addSeparator().colspan(2)

        table.add(skeletonEsp) //Add Enable_Skeleton_Esp Toggle
        table.add(boxEsp).row() //Add Enable_Box_Esp Toggle

        table.add(glowEsp) //Add Enable_Glow_Esp Toggle
        table.add(boxEspDetails).row() //Add Enable_Box_Esp_Details Toggle

        table.add(invGlowEsp) //Add Enable_Inv_Glow_Esp Toggle
        table.add(modelEsp).row() //Add Enable_Model_Esp Toggle

        table.add(modelAndGlow) //Add Model_And_Glow Toggle
        table.add(enemyIndicator).row() //Add Enemy_Indicator Toggle

        table.addSeparator().colspan(2)

        table.add(chamsEsp) //Add Enable_Chams_Esp Toggle
        table.add(chamsShowHealth).row() //Add Enable_Chams_Show_Health Toggle
        table.add(chamsBrightness).colspan(2).row() //Add Chams_Brightness Slider

        table.addSeparator().colspan(2)

        table.add(hitSound).colspan(2).row() //Add Hit_Sound Toggle
        table.add(hitSoundVolume).colspan(2).row() //Add Hit_Sound_Volume Slider

        table.addSeparator().colspan(2)

        table.add(showTeam) //Add Enable_Bunny_Hop Toggle
        table.add(showEnemies).row() //Add Enable_Show_Enemies Toggle

        table.add(showDormant) //Add Enable_Show_Dormant Toggle
        table.add(showBomb).row() //Add Enable_Show_Bomb Toggle

        table.add(showWeapons) //Add Enable_Show_Weapons Toggle
        table.add(showGrenades).row() //Add Enable_Show_Grenades Toggle

        table.add(teamColor).right() //Add Team_Color Picker + Button
        table.add(enemyColor).left().row() //Add Team_Color Picker + Button

        table.add(bombColor).right() //Add Bomb_Color Picker + Button
        table.add(weaponColor).left().row() //Add Weapon_Color Picker + Button

        table.add(grenadeColor).colspan(2).row() //Add Grenade_Color Picker + Button
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Visuals"
    }
}