package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.scripts.esp.disableAllEsp
import rat.poison.ui.tabs.visualstabs.*
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.binds.BindsRelatedCheckBox
import rat.poison.ui.uiHelpers.VisColorPickerCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiHelpers.VisSliderCustom
import rat.poison.ui.uiPanels.visualsTab
import rat.poison.utils.varUtil.strToBool

var espTabbedPane = TabbedPane()
var glowEspTab = GlowEspTab()
var chamsEspTab = ChamsEspTab()
var indicatorEspTab = IndicatorEspTab()
var boxEspTab = BoxEspTab()
var snaplinesEspTab = SnaplinesEspTab()
var footStepsEspTab = FootstepsEspTab()
var hitMarkerTab = HitMarkerTab()
var nadesTab = NadesVT()

class VisualsTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    //Static Visuals Tab Items
    val enableEsp = BindsRelatedCheckBox(curLocalization["ENABLE_ESP"], "ENABLE_ESP", nameInLocalization = "ENABLE_ESP")
    val radarEsp = BindsRelatedCheckBox(curLocalization["ENABLE_RADAR_ESP"], "RADAR_ESP", nameInLocalization = "ENABLE_RADAR_ESP")
    val nightMode = BindsRelatedCheckBox(curLocalization["ENABLE_NIGHTMODE"], "ENABLE_NIGHTMODE", nameInLocalization = "ENABLE_NIGHTMODE")
    val nightModeSlider = VisSliderCustom(curLocalization["NIGHTMODE_VALUE"], "NIGHTMODE_VALUE", 0.05F, 5F, .05F, false, nameInLocalization = "NIGHTMODE_VALUE")

    val visAdrenaline = BindsRelatedCheckBox(curLocalization["ENABLE_ADRENALINE"], "ENABLE_ADRENALINE", nameInLocalization = "ENABLE_ADRENALINE")

    val showAimFov = VisCheckBoxCustom(" ", "DRAW_AIM_FOV")
    val showAimFovColor = VisColorPickerCustom( curLocalization["DRAW_AIM_FOV_COLOR"], "DRAW_AIM_FOV_COLOR", nameInLocalization = "DRAW_AIM_FOV_COLOR")

    val showTriggerFov = VisCheckBoxCustom(" ", "DRAW_TRIGGER_FOV")
    val showTriggerFovColor = VisColorPickerCustom(curLocalization["DRAW_TRIGGER_FOV_COLOR"], "DRAW_TRIGGER_FOV_COLOR", nameInLocalization = "DRAW_TRIGGER_FOV_COLOR")

    init {
        //ESP Tab
        espTabbedPane.add(glowEspTab)
        espTabbedPane.add(chamsEspTab)
        espTabbedPane.add(indicatorEspTab)
        espTabbedPane.add(boxEspTab)
        espTabbedPane.add(snaplinesEspTab)
        espTabbedPane.add(footStepsEspTab)
        espTabbedPane.add(hitMarkerTab)
        espTabbedPane.add(nadesTab)

        espTabbedPane.switchTab(glowEspTab)

        val espTabbedPaneContent = VisTable()
        espTabbedPaneContent.padTop(10F)
        espTabbedPaneContent.padBottom(10F)
        espTabbedPaneContent.align(Align.top)
        espTabbedPaneContent.columnDefaults(1)

        val espScrollPane = ScrollPane(espTabbedPaneContent)
        espScrollPane.setFlickScroll(false)
        espScrollPane.setSize(1000F, 1000F)

        espTabbedPaneContent.add(glowEspTab.contentTable).left().colspan(2).row()

        espTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)

        espTabbedPane.addListener(object : TabbedPaneAdapter() {
            override fun switchedTab(tab: Tab?) {
                if (tab == null) return

                espTabbedPaneContent.clear()

                when (tab) {
                    glowEspTab -> {
                        espTabbedPaneContent.add(glowEspTab.contentTable).left().colspan(2).row()
                    }
                    chamsEspTab -> {
                        espTabbedPaneContent.add(chamsEspTab.contentTable).left().colspan(2).row()
                    }
                    indicatorEspTab -> {
                        espTabbedPaneContent.add(indicatorEspTab.contentTable).left().colspan(2).row()
                    }
                    boxEspTab -> {
                        espTabbedPaneContent.add(boxEspTab.contentTable).left().colspan(2).row()
                    }
                    snaplinesEspTab -> {
                        espTabbedPaneContent.add(snaplinesEspTab.contentTable).left().colspan(2).row()
                    }
                    footStepsEspTab -> {
                        espTabbedPaneContent.add(footStepsEspTab.contentTable).left().colspan(2).row()
                    }
                    hitMarkerTab -> {
                        espTabbedPaneContent.add(hitMarkerTab.contentTable).left().colspan(2).row()
                    }
                    nadesTab -> {
                        espTabbedPaneContent.add(nadesTab.contentTable).left().colspan(2).row()
                    }
                }

                espTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)
            }
        })

        val aimFov = VisTable()
        aimFov.add(showAimFov).left()
        aimFov.add(showAimFovColor).width(175F-showAimFov.width).left()

        val triggerFov = VisTable()
        triggerFov.add(showTriggerFov).left()
        triggerFov.add(showTriggerFovColor).width(175F-showTriggerFov.width).left()

        //Add all items to label for tabbed pane content
        table.add(enableEsp).padLeft(25F).left().row()
        table.add(radarEsp).padLeft(25F).left().row()
        table.add(nightMode).padLeft(25F).left().row()
        table.add(nightModeSlider).padLeft(25F).left().row()
        table.add(visAdrenaline).padLeft(25F).left().row()
        table.add(aimFov).padLeft(25F).left().row()
        table.add(triggerFov).padLeft(25F).left().row()
        table.add(espTabbedPane.table).minWidth(500F).left().growX().row()
        table.add(espScrollPane).minSize(500F, 500F).prefSize(500F, 500F).align(Align.left).growX().growY().row()
    }

    fun updateESPTabs () {
        espTabbedPane.updateTabTitle(glowEspTab)
        espTabbedPane.updateTabTitle(chamsEspTab)
        espTabbedPane.remove(boxEspTab)
        boxEspTab = BoxEspTab()
        espTabbedPane.insert(3, boxEspTab)
        espTabbedPane.updateTabTitle(indicatorEspTab)
        espTabbedPane.updateTabTitle(snaplinesEspTab)
        espTabbedPane.updateTabTitle(footStepsEspTab)
        espTabbedPane.updateTabTitle(hitMarkerTab)
        espTabbedPane.updateTabTitle(nadesTab)
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return curLocalization["VISUALS_TAB_NAME"]
    }
}

fun updateDisableEsp() {
    visualsTab.apply {
        val bool = !enableEsp.isChecked
        var col = Color(255F, 255F, 255F, 1F)
        if (bool) {
            col = Color(105F, 105F, 105F, .2F)
        }

        radarEsp.disable(bool)
        visAdrenaline.disable(bool)
        nightMode.disable(bool)
        nightModeSlider.disable(bool, col)

        if (curSettings["FOV_TYPE"].replace("\"", "") == "DISTANCE") {
            showAimFov.disable(true)
            showAimFovColor.disable(true)

            showTriggerFov.disable(true)
            showTriggerFovColor.disable(true)
        } else {
            showAimFov.disable(bool)
            showAimFovColor.disable(bool)

            showTriggerFov.disable(bool)
            showTriggerFovColor.disable(bool)
        }

        val recTab = espTabbedPane.activeTab

        espTabbedPane.disableTab(glowEspTab, bool)
        espTabbedPane.disableTab(chamsEspTab, bool)
        espTabbedPane.disableTab(indicatorEspTab, bool)
        espTabbedPane.disableTab(boxEspTab, bool)
        espTabbedPane.disableTab(snaplinesEspTab, bool)
        espTabbedPane.disableTab(footStepsEspTab, bool)
        espTabbedPane.disableTab(hitMarkerTab, bool)
        espTabbedPane.disableTab(nadesTab, bool)

        espTabbedPane.switchTab(recTab)

        glowEspTab.glowEsp.disable(bool)
        glowEspTab.glowShowHealth.disable(bool)
        glowEspTab.showTeam.disable(bool)
        glowEspTab.showEnemies.disable(bool)
        glowEspTab.showBomb.disable(bool)
        glowEspTab.showBombCarrier.disable(bool)
        glowEspTab.showWeapons.disable(bool)
        glowEspTab.showGrenades.disable(bool)
        glowEspTab.showTarget.disable(bool)
        glowEspTab.glowTeamColor.disable(bool)
        glowEspTab.glowEnemyColor.disable(bool)
        glowEspTab.glowBombColor.disable(bool)
        glowEspTab.glowBombCarrierColor.disable(bool)
        glowEspTab.glowWeaponColor.disable(bool)
        glowEspTab.glowGrenadeColor.disable(bool)
        glowEspTab.glowHighlightColor.disable(bool)

        chamsEspTab.chamsEsp.disable(bool)
        chamsEspTab.chamsShowHealth.disable(bool)
        chamsEspTab.chamsBrightness.disable(bool, col)
        chamsEspTab.showTeam.disable(bool)
        chamsEspTab.showEnemies.disable(bool)
        chamsEspTab.chamsTeamColor.disable(bool)
        chamsEspTab.chamsEnemyColor.disable(bool)

        indicatorEspTab.indicatorEsp.disable(bool)
        indicatorEspTab.indicatorDistance.disable(bool, col)
        indicatorEspTab.indicatorSize.disable(bool, col)
        indicatorEspTab.showTeam.disable(bool)
        indicatorEspTab.showEnemies.disable(bool)
        indicatorEspTab.showBomb.disable(bool)
        indicatorEspTab.showBombCarrier.disable(bool)
        indicatorEspTab.showWeapons.disable(bool)
        indicatorEspTab.showGrenades.disable(bool)
        indicatorEspTab.showDefusers.disable(bool)
        indicatorEspTab.indicatorTeamColor.disable(bool)
        indicatorEspTab.indicatorEnemyColor.disable(bool)
        indicatorEspTab.indicatorBombColor.disable(bool)
        indicatorEspTab.indicatorDefuserColor.disable(bool)
        indicatorEspTab.indicatorWeaponColor.disable(bool)
        indicatorEspTab.indicatorGrenadeColor.disable(bool)

        boxEspTab.boxEsp.disable(bool)
        boxEspTab.boxEspDetails.disable(bool)
        boxEspTab.boxShowHealth.disable(bool)
        boxEspTab.boxEspHealth.disable(bool)
        boxEspTab.boxEspHealthPos.isDisabled = bool
        boxEspTab.boxEspArmor.disable(bool)
        boxEspTab.boxEspArmorPos.isDisabled = bool
        boxEspTab.boxEspName.disable(bool)
        boxEspTab.boxEspNamePos.isDisabled = bool
        boxEspTab.boxEspWeapon.disable(bool)
        boxEspTab.boxEspWeaponPos.isDisabled = bool

        boxEspTab.boxEspHelmet.disable(bool)
        boxEspTab.boxEspHelmetPos.isDisabled = bool
        boxEspTab.boxEspKevlar.disable(bool)
        boxEspTab.boxEspKevlarPos.isDisabled = bool
        boxEspTab.boxEspAmmo.disable(bool)
        boxEspTab.boxEspAmmoPos.isDisabled = bool
        boxEspTab.boxEspScoped.disable(bool)
        boxEspTab.boxEspScopedPos.isDisabled = bool
        boxEspTab.boxEspFlashed.disable(bool)
        boxEspTab.boxEspFlashedPos.isDisabled = bool

        boxEspTab.skeletonEsp.disable(bool)
        boxEspTab.showTeamSkeleton.disable(bool)
        boxEspTab.showEnemiesSkeleton.disable(bool)
        boxEspTab.showTeamBox.disable(bool)
        boxEspTab.showEnemiesBox.disable(bool)
        boxEspTab.showDefusers.disable(bool)
        boxEspTab.boxTeamColor.disable(bool)
        boxEspTab.boxEnemyColor.disable(bool)
        boxEspTab.boxDefuserColor.disable(bool)

        snaplinesEspTab.enableSnaplines.disable(bool)
        snaplinesEspTab.enemySnaplines.disable(bool)
        snaplinesEspTab.enemySnaplinesColor.disable(bool)
        snaplinesEspTab.teamSnaplines.disable(bool)
        snaplinesEspTab.teamSnaplinesColor.disable(bool)
        snaplinesEspTab.weaponSnaplines.disable(bool)
        snaplinesEspTab.weaponSnaplinesColor.disable(bool)
        snaplinesEspTab.bombSnaplines.disable(bool)
        snaplinesEspTab.bombSnaplinesColor.disable(bool)
        snaplinesEspTab.bombCarrierSnaplines.disable(bool)
        snaplinesEspTab.bombCarrierSnaplinesColor.disable(bool)

        footStepsEspTab.enableFootSteps.disable(bool)
        footStepsEspTab.footStepType.isDisabled = bool
        footStepsEspTab.footStepUpdateTimer.disable(bool, col)
        footStepsEspTab.footStepTTL.disable(bool, col)
        footStepsEspTab.footStepTeamBox.disable(bool)
        footStepsEspTab.footStepTeamColor.disable(bool)
        footStepsEspTab.footStepEnemyBox.disable(bool)
        footStepsEspTab.footStepEnemyColor.disable(bool)

        hitMarkerTab.hitMarker.disable(bool)
        hitMarkerTab.hitMarkerOutline.disable(bool)
        hitMarkerTab.hitMarkerCombo.disable(bool)
        hitMarkerTab.hitMarkerRecoilPos.disable(bool)
        hitMarkerTab.hitMarkerSpacing.disable(bool, col)
        hitMarkerTab.hitMarkerLength.disable(bool, col)
        hitMarkerTab.hitMarkerWidth.disable(bool, col)
        hitMarkerTab.hitMarkerColor.disable(bool)
        hitMarkerTab.hitMarkerOutlineColor.disable(bool)
        hitMarkerTab.hitMarkerComboColor.disable(bool)
        //Add disable nades tab

        if (!curSettings["ENABLE_ESP"].strToBool()) {
            disableAllEsp()
        } else if (!curSettings["CHAMS_ESP"].strToBool()) {
            disableAllEsp()
        }
    }
}

fun visualsTabUpdate() {
    visualsTab.apply {
        enableEsp.update()
        radarEsp.update()
        visAdrenaline.update()
        nightMode.update()
        nightModeSlider.update()
        showAimFov.update()
        showAimFovColor.update()
        showAimFovColor.updateTitle()
        showTriggerFov.update()
        showTriggerFovColor.update()
        showTriggerFovColor.updateTitle()
    }
}