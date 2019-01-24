package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisSlider
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextButton
import com.kotcrab.vis.ui.widget.color.ColorPicker
import com.kotcrab.vis.ui.widget.color.ColorPickerAdapter
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import rat.poison.App
import rat.poison.settings.*
import rat.poison.ui.changed

class EspKts : Tab(false, false) {
    private val table = VisTable(true)

    //Init labels/sliders/boxes that show values here
    val enableSkeletonEspToggle = VisTextButton("SKELETON_ESP", "toggle") //Skeleton_Esp
    val enableBoxEspToggle = VisTextButton("BOX_ESP", "toggle") //Box_Esp
    val enableBoxEspDetailsToggle = VisTextButton("BOX_ESP_DETAILS", "toggle") //Box_Esp_Details
    val enableGlowEspToggle = VisTextButton("GLOW_ESP", "toggle") //Glow_Esp
    val enableInvGlowEspToggle = VisTextButton("INV_GLOW_ESP", "toggle") //Inv_Glow_Esp
    val enableModelEspToggle = VisTextButton("MODEL_ESP", "toggle") //Model_Esp
    val enableChamsEspToggle = VisTextButton("CHAMS_ESP", "toggle") //Chams_Esp
    val enableChamsShowHealthToggle = VisTextButton("CHAMS_SHOW_HEALTH", "toggle") //Chams_Show_Health
    val chamsBrightnessLabel = VisLabel("Chams Brightness: " + CHAMS_BRIGHTNESS.toString() + when(CHAMS_BRIGHTNESS.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "}) //Chams_Brightness
    val chamsBrightnessSlider = VisSlider(0F, 1000F, 1F, false) //Chams_Brightness
    val enableShowTeamToggle = VisTextButton("SHOW_TEAM", "toggle") //Show_Team
    val enableShowEnemiesToggle = VisTextButton("SHOW_ENEMIES", "toggle") //Show_Enemies
    val enableShowDormantToggle = VisTextButton("SHOW_DORMANT", "toggle") //Show_Dormant
    val enableShowBombToggle = VisTextButton("SHOW_BOMB", "toggle") //Show_Bomb
    val enableShowWeaponsToggle = VisTextButton("SHOW_WEAPONS", "toggle") //Show_Weapons
    val enableShowGrenadesToggle = VisTextButton("SHOW_GRENADES", "toggle") //Show_Grenades
    val teamColorShow = VisTextButton("Set Team Color") //Team_Color
    val enemyColorShow = VisTextButton("Set Enemy Color") //Enemy_Color
    val bombColorShow = VisTextButton("Set Bomb Color") //Bomb_Color
    val weaponColorShow = VisTextButton("Set Weapon Color") //Weapon_Color
    val grenadeColorShow = VisTextButton("Set Weapon Color") //Grenade_Color
    val chamsColorShow = VisTextButton("Set Chams Color") //Chams_Color


    init {
        //Create Skeleton_Esp Toggle
        //val enableSkeletonEspToggle = VisTextButton("SKELETON_ESP", "toggle")
        if (SKELETON_ESP) enableSkeletonEspToggle.toggle()
        enableSkeletonEspToggle.changed { _, _ ->
            if (true) {
                SKELETON_ESP = enableSkeletonEspToggle.isChecked//!SKELETON_ESP
            }
        }

        //Create Box_Esp Toggle
        //val enableBoxEspToggle = VisTextButton("BOX_ESP", "toggle")
        if (BOX_ESP) enableBoxEspToggle.toggle()
        enableBoxEspToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                BOX_ESP = enableBoxEspToggle.isChecked//!BOX_ESP
            }
        }

        //Create Box_Esp_Details Toggle
        //val enableBoxEspDetailsToggle = VisTextButton("BOX_ESP", "toggle")
        if (BOX_ESP_DETAILS) enableBoxEspDetailsToggle.toggle()
        enableBoxEspDetailsToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                BOX_ESP_DETAILS = enableBoxEspDetailsToggle.isChecked//!BOX_ESP
            }
        }

        //Create Glow_Esp Toggle
        //val enableGlowEspToggle = VisTextButton("GLOW_ESP", "toggle")
        if (GLOW_ESP) enableGlowEspToggle.toggle()
        enableGlowEspToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                GLOW_ESP = enableGlowEspToggle.isChecked//!GLOW_ESP
            }
        }

        //Create Inv_Glow_Esp Toggle
        //val enableInvGlowEspToggle = VisTextButton("INV_GLOW_ESP", "toggle")
        if (INV_GLOW_ESP) enableInvGlowEspToggle.toggle()
        enableInvGlowEspToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                INV_GLOW_ESP = enableInvGlowEspToggle.isChecked//!GLOW_ESP
            }
        }

        //Create Model_Esp Toggle
        //val enableModelEspToggle = VisTextButton("MODEL_ESP", "toggle")
        if (MODEL_ESP) enableModelEspToggle.toggle()
        enableModelEspToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                MODEL_ESP = enableModelEspToggle.isChecked//!MODEL_ESP
            }
        }

        //Create Chams_Esp Toggle
        //val enableChamsEspToggle = VisTextButton("CHAMS_ESP", "toggle")
        if (CHAMS_ESP) enableChamsEspToggle.toggle()
        enableChamsEspToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                CHAMS_ESP = enableChamsEspToggle.isChecked//!CHAMS_ESP
            }
        }

        //Create Chams_Show_Health Toggle
       // val enableChamsShowHealthToggle = VisTextButton("CHAMS_SHOW_HEALTH", "toggle")
        if (CHAMS_SHOW_HEALTH) enableChamsShowHealthToggle.toggle()
        enableChamsShowHealthToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                CHAMS_SHOW_HEALTH = enableChamsShowHealthToggle.isChecked//!CHAMS_SHOW_HEALTH
            }
        }

        //Create Chams_Brightness Slider
        val chamsBrightness = VisTable()
        //val chamsBrightnessLabel = VisLabel("Chams Brightness: " + CHAMS_BRIGHTNESS.toString() + when(CHAMS_BRIGHTNESS.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "})
        //val chamsBrightnessSlider = VisSlider(0F, 1000F, 1F, false)
        chamsBrightnessSlider.value = CHAMS_BRIGHTNESS.toFloat()
        chamsBrightnessSlider.changed { _, _ ->
            CHAMS_BRIGHTNESS = chamsBrightnessSlider.value.toInt()
            chamsBrightnessLabel.setText("Chams Brightness: " + CHAMS_BRIGHTNESS.toString() + when(CHAMS_BRIGHTNESS.toString().length) {4->"  " 3->"    " 2->"      " else ->"        "}) //When is used to not make the sliders jitter when you go from 10 to 9, or 100 to 99, as that character space shifts everything, one character is 2 spaces
        }

        chamsBrightness.add(chamsBrightnessLabel).spaceRight(6F)
        chamsBrightness.add(chamsBrightnessSlider)

        //Create Show_Team Toggle
        //val enableShowTeamToggle = VisTextButton("SHOW_TEAM", "toggle")
        if (SHOW_TEAM) enableShowTeamToggle.toggle()
        enableShowTeamToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_TEAM = enableShowTeamToggle.isChecked//!SHOW_TEAM
            }
        }

        //Create Show_Enemies Toggle
        //val enableShowEnemiesToggle = VisTextButton("SHOW_ENEMIES", "toggle")
        if (SHOW_ENEMIES) enableShowEnemiesToggle.toggle()
        enableShowEnemiesToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_ENEMIES = enableShowEnemiesToggle.isChecked//!SHOW_ENEMIES
            }
        }

        //Create Show_Dormant Toggle
        //val enableShowDormantToggle = VisTextButton("SHOW_DORMANT", "toggle")
        if (SHOW_DORMANT) enableShowDormantToggle.toggle()
        enableShowDormantToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_DORMANT = enableShowDormantToggle.isChecked//!SHOW_DORMANT
            }
        }

        //Create Show_Bomb Toggle
        //val enableShowBombToggle = VisTextButton("SHOW_BOMB", "toggle")
        if (SHOW_BOMB) enableShowBombToggle.toggle()
        enableShowBombToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_BOMB = enableShowBombToggle.isChecked//!SHOW_BOMB
            }
        }

        //Create Show_Bomb Toggle
        //val enableShowWeaponsToggle = VisTextButton("SHOW_WEAPONS", "toggle")
        if (SHOW_WEAPONS) enableShowWeaponsToggle.toggle()
        enableShowWeaponsToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_WEAPONS = enableShowWeaponsToggle.isChecked//!SHOW_WEAPONS
            }
        }

        //Create Show_Bomb Toggle
        //val enableShowGrenadesToggle = VisTextButton("SHOW_GRENADES", "toggle")
        if (SHOW_GRENADES) enableShowGrenadesToggle.toggle()
        enableShowGrenadesToggle.changed { _, _ ->
            if (true) { //type Any? changes didnt work im autistic //fix later
                SHOW_GRENADES = enableShowGrenadesToggle.isChecked//!SHOW_GRENADES
            }
        }

        //VisImage(Color) doesnt work??
        //Create Team_Color Picker
        val teamColor = VisTable()
        //val teamColorShow = VisTextButton("Set Team Color")
        teamColorShow.setColor(TEAM_COLOR.red.toFloat(), TEAM_COLOR.green.toFloat(), TEAM_COLOR.blue.toFloat(), TEAM_COLOR.alpha.toFloat())
        val teamColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                teamColorShow.color = newCol
                TEAM_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
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
        //val enemyColorShow = VisTextButton("Set Enemy Color")
        enemyColorShow.setColor(ENEMY_COLOR.red.toFloat(), ENEMY_COLOR.green.toFloat(), ENEMY_COLOR.blue.toFloat(), ENEMY_COLOR.alpha.toFloat())
        val enemyColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                enemyColorShow.color = newCol
                ENEMY_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        enemyColorShow.changed { _, _ ->
            App.stage.addActor(enemyColorPicker.fadeIn())
        }

        enemyColor.add(enemyColorShow)

        //Create Bomb_Color Picker
        val bombColor = VisTable()
        //val bombColorShow = VisTextButton("Set Bomb Color")
        bombColorShow.setColor(BOMB_COLOR.red.toFloat(), BOMB_COLOR.green.toFloat(), BOMB_COLOR.blue.toFloat(), BOMB_COLOR.alpha.toFloat())
        val bombColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                bombColorShow.color = newCol
                BOMB_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
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
        //val weaponColorShow = VisTextButton("Set Weapon Color")
        weaponColorShow.setColor(WEAPON_COLOR.red.toFloat(), WEAPON_COLOR.green.toFloat(), WEAPON_COLOR.blue.toFloat(), WEAPON_COLOR.alpha.toFloat())
        val weaponColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                weaponColorShow.color = newCol
                WEAPON_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
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
        val grenadeColor = VisTable()
        //val grenadeColorShow = VisTextButton("Set Weapon Color")
        grenadeColorShow.setColor(GRENADE_COLOR.red.toFloat(), GRENADE_COLOR.green.toFloat(), GRENADE_COLOR.blue.toFloat(), GRENADE_COLOR.alpha.toFloat())
        val grenadeColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                grenadeColorShow.color = newCol
                GRENADE_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        grenadeColorShow.changed { _, _ ->
            App.stage.addActor(grenadeColorPicker.fadeIn())
        }

        grenadeColor.add(grenadeColorShow)

        //Create Chams_Esp_Color Picker
        val chamsColor = VisTable()
        //val chamsColorShow = VisTextButton("Set Chams Color")
        chamsColorShow.setColor(CHAMS_ESP_COLOR.red.toFloat(), CHAMS_ESP_COLOR.green.toFloat(), CHAMS_ESP_COLOR.blue.toFloat(), CHAMS_ESP_COLOR.alpha.toFloat())
        val chamsColorPicker = ColorPicker("Color Picker", object : ColorPickerAdapter() {
            override fun finished(newCol: Color) {
                chamsColorShow.color = newCol
                CHAMS_ESP_COLOR = rat.poison.game.Color((newCol.r*255F).toInt(), (newCol.g*255F).toInt(), (newCol.b*255F).toInt(), newCol.a.toDouble())
                dispose()
            }

            override fun canceled(oldCol: Color) {
                dispose()
            }
        })

        chamsColorShow.changed { _, _ ->
            App.stage.addActor(chamsColorPicker.fadeIn())
        }

        chamsColor.add(chamsColorShow)

        //Add all items to label for tabbed pane content
        table.add(enableSkeletonEspToggle).row() //Add Enable_Skeleton_Esp Toggle

        table.add(enableBoxEspToggle).row() //Add Enable_Box_Esp Toggle
        table.add(enableBoxEspDetailsToggle).row() //Add Enable_Box_Esp_Details Toggle

        table.add(enableGlowEspToggle).row() //Add Enable_Glow_Esp Toggle
        table.add(enableInvGlowEspToggle).row() //Add Enable_Inv_Glow_Esp Toggle
        table.add(enableModelEspToggle).row() //Add Enable_Model_Esp Toggle
        table.add(enableChamsEspToggle).row() //Add Enable_Chams_Esp Toggle
        table.add(enableChamsShowHealthToggle).row() //Add Enable_Chams_Show_Health Toggle
        table.add(chamsBrightness).width(250F).row() //Add Chams_Brightness Slider
        table.add(enableShowTeamToggle).row() //Add Enable_Bunny_Hop Toggle
        table.add(enableShowEnemiesToggle).row() //Add Enable_Show_Enemies Toggle
        table.add(enableShowDormantToggle).row() //Add Enable_Show_Dormant Toggle
        table.add(enableShowBombToggle).row() //Add Enable_Show_Bomb Toggle
        table.add(enableShowWeaponsToggle).row() //Add Enable_Show_Weapons Toggle
        table.add(enableShowGrenadesToggle).row() //Add Enable_Show_Grenades Toggle
        table.add(teamColor).row() //Add Team_Color Picker + Button
        table.add(enemyColor).row() //Add Team_Color Picker + Button
        table.add(bombColor).row() //Add Bomb_Color Picker + Button
        table.add(weaponColor).row() //Add Weapon_Color Picker + Button
        table.add(grenadeColor).row() //Add Grenade_Color Picker + Button
        table.add(chamsColor).row() //Add Chams_Color Picker + Button
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "ESP.kts"
    }
}