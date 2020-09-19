package rat.poison.ui.tabs

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.tabbedpane.Tab
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPane
import com.kotcrab.vis.ui.widget.tabbedpane.TabbedPaneAdapter
import rat.poison.curSettings
import rat.poison.overlay.opened
import rat.poison.scripts.visuals.disableAllEsp
import rat.poison.toLocale
import rat.poison.ui.tabs.visualstabs.*
import rat.poison.ui.uiHelpers.VisCheckBoxCustom
import rat.poison.ui.uiHelpers.VisInputFieldCustom
import rat.poison.ui.uiPanels.visualsTab
import rat.poison.utils.generalUtil.strToBool

val espTabbedPane = TabbedPane()
var glowEspTab = GlowEspTab()
var chamsEspTab = ChamsEspTab()
var indicatorEspTab = IndicatorEspTab()
var boxEspTab = BoxEspTab()
var snaplinesEspTab = SnaplinesEspTab()
var footStepsEspTab = FootstepsEspTab()
var hitMarkerTab = HitMarkerTab()
var nadesTab = NadesVT()
var miscVisualsTab = MiscVisualsTab()

class VisualsTab : Tab(false, false) {
    private val table = VisTable()

    //Init labels/sliders/boxes that show values here
    //Static Visuals Tab Items
    val enableEsp = VisCheckBoxCustom("Enable ESP", "ENABLE_ESP")
    val visualsToggleKey = VisInputFieldCustom("Visuals Toggle Key", "VISUALS_TOGGLE_KEY")

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
        espTabbedPane.add(miscVisualsTab)

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

                espTabbedPaneContent.add(tab.contentTable).left().colspan(2).row()

                espTabbedPaneContent.addSeparator().colspan(2).padLeft(25F).padRight(25F)
            }
        })

        //Add all items to label for tabbed pane content
        table.add(enableEsp).padLeft(25F).left().row()
        table.add(visualsToggleKey).padLeft(25F).left().padBottom(10F).row()
        table.add(espTabbedPane.table).minWidth(500F).left().growX().row()
        table.add(espScrollPane).minSize(500F, 500F).prefSize(500F, 500F).align(Align.left).growX().growY().row()
    }

    override fun getContentTable(): Table? {
        return table
    }

    override fun getTabTitle(): String? {
        return "Visuals".toLocale()
    }
}

//TODO Move all this to their respective files...
fun updateDisableEsp() {
    visualsTab.apply {
        if (!opened) return

        val bool = !enableEsp.isChecked
        var col = Color(255F, 255F, 255F, 1F)
        if (bool) {
            col = Color(105F, 105F, 105F, .2F)
        }

        visualsToggleKey.disable(bool, col)

        val recTab = espTabbedPane.activeTab

        espTabbedPane.disableTab(glowEspTab, bool)
        espTabbedPane.disableTab(chamsEspTab, bool)
        espTabbedPane.disableTab(indicatorEspTab, bool)
        espTabbedPane.disableTab(boxEspTab, bool)
        espTabbedPane.disableTab(snaplinesEspTab, bool)
        espTabbedPane.disableTab(footStepsEspTab, bool)
        espTabbedPane.disableTab(hitMarkerTab, bool)
        espTabbedPane.disableTab(nadesTab, bool)
        espTabbedPane.disableTab(miscVisualsTab, bool)

        espTabbedPane.switchTab(recTab)

        glowEspTab.glowEsp.disable(bool)
        glowEspTab.glowShowHealth.disable(bool)
        glowEspTab.glowSmokeCheck.disable(bool)
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
        glowEspTab.glowBombAdaptive.disable(bool)
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
        indicatorEspTab.indicatorSmokeCheck.disable(bool)
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
        boxEspTab.advancedBBox.disable(bool)
        boxEspTab.advancedBBox.disable(bool)
        boxEspTab.boxEspDetails.disable(bool)
        boxEspTab.boxEspHealth.disable(bool)
        boxEspTab.boxEspHealthPos.disable(bool, col)
        boxEspTab.boxEspArmor.disable(bool)
        boxEspTab.boxEspArmorPos.disable(bool, col)
        boxEspTab.boxEspName.disable(bool)
        boxEspTab.boxEspNamePos.disable(bool, col)
        boxEspTab.boxEspWeapon.disable(bool)
        boxEspTab.boxEspWeaponPos.disable(bool, col)
        boxEspTab.boxEspMoney.disable(bool)
        boxEspTab.boxEspMoneyPos.disable(bool, col)

        boxEspTab.boxEspHelmet.disable(bool)
        boxEspTab.boxEspHelmetPos.disable(bool, col)
        boxEspTab.boxEspKevlar.disable(bool)
        boxEspTab.boxEspKevlarPos.disable(bool, col)
        boxEspTab.boxEspAmmo.disable(bool)
        boxEspTab.boxEspAmmoPos.disable(bool, col)
        boxEspTab.boxEspScoped.disable(bool)
        boxEspTab.boxEspScopedPos.disable(bool, col)
        boxEspTab.boxEspFlashed.disable(bool)
        boxEspTab.boxEspFlashedPos.disable(bool, col)

        boxEspTab.skeletonEsp.disable(bool)
        boxEspTab.showTeamSkeleton.disable(bool)
        boxEspTab.showEnemiesSkeleton.disable(bool)
        boxEspTab.showTeamBox.disable(bool)
        boxEspTab.showEnemiesBox.disable(bool)
        boxEspTab.showDefusers.disable(bool)
        boxEspTab.showWeapons.disable(bool)
        boxEspTab.boxTeamColor.disable(bool)
        boxEspTab.boxEnemyColor.disable(bool)
        boxEspTab.boxWeaponsColor.disable(bool)
        boxEspTab.boxDefuserColor.disable(bool)

        snaplinesEspTab.enableSnaplines.disable(bool)
        snaplinesEspTab.snaplinesWidth.disable(bool, col)
        snaplinesEspTab.snaplinesSmokeCheck.disable(bool)
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

        miscVisualsTab.apply {
            radarEsp.disable(bool)
            legitRadar.disable(bool)
            legitRadarDistance.disable(bool, col)
            visAdrenaline.disable(bool)
            adrenalineBombTime.disable(bool)
            adrenalineStrength.disable(bool, col)
            adrenalineCooldown.disable(bool, col)
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

            enableSpreadCircle.disable(bool)
            spreadCircleColor.disable(bool)
            enableHeadLevel.disable(bool)
            headLevelColor.disable(bool)
            headLevelDeadzone.disable(bool, col)
        }

        nadesTab.apply {
            nadeTracer.disable(bool)
            nadeTracerColor.disable(bool)
            nadeTracerUpdateTime.disable(bool, col)
            nadeTracerTimeout.disable(bool, col)

            visualizeSmokes.disable(bool)
            visualizeSmokesColor.disable(bool)
            visualizeSmokesPolys.disable(bool, col)
            visualizeSmokesWidth.disable(bool, col)
            visualizeSmokesHeight.disable(bool, col)
        }

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
        visualsToggleKey.update()
        visualsToggleKey.update()
    }
}