package rat.poison.ui

import rat.poison.overlay.App.uiBombWindow
import rat.poison.overlay.App.uiKeybinds
import rat.poison.overlay.App.uiSpecList
import rat.poison.overlay.opened
import rat.poison.ui.tabs.*
import rat.poison.ui.tabs.aimtabs.BacktrackTable
import rat.poison.ui.tabs.aimtabs.MainAimTable
import rat.poison.ui.tabs.aimtabs.OverrideTable
import rat.poison.ui.tabs.aimtabs.overridenWeaponsUpdate
import rat.poison.ui.tabs.misctabs.*
import rat.poison.ui.tabs.visualstabs.*
import rat.poison.ui.uiPanels.*

var uiRefreshing = false

fun uiUpdate() {
    if (!opened || uiRefreshing) return

    overridenWeaponsUpdate()
    visualsTabUpdate()
    glowEspTabUpdate()
    chamsEspTabUpdate()
    indicatorEspTabUpdate()
    skeletonEspTableUpdate()
    boxEspTabUpdate()
    hitMarkerTabUpdate()
    skinChangerTabUpdate()
    nadesVTUpdate()
    snaplinesEspTabUpdate()
    footStepsEspTabUpdate()
    movementTabUpdate()
    fovChangerTabUpdate()
    bombTabUpdate()
    othersTabUpdate()
    rcsTabUpdate()
    miscVisualTabUpdate()
    optionsTabUpdate()
    nadeHelperTabUpdate()
    updateBacktrack()
    optionsTabUpdate()
    updateTrig()
    updateAim()
    updateDisableEsp()
    keybindsUpdate(null)

    //Update windows

    //Update lists
    configsTabUpdate()
    nadeHelperTab.updateNadeFileHelperList()
}

fun refreshMenu() {
    if (uiRefreshing) return
    uiRefreshing = true

    mainTabbedPane.removeAll()

    aimTab = AimTab()
    visualsTab = VisualsTab()
    rcsTab = RcsTab()
    miscTab = MiscTabs()
    ranksTab = RanksTab()
    nadeHelperTab = NadeHelperTab()
    skinChangerTab = SkinChangerTab()
    optionsTab = OptionsTab()
    configsTab = ConfigsTab()

    mainTabbedPane.add(aimTab)
    mainTabbedPane.add(visualsTab)
    mainTabbedPane.add(rcsTab)
    mainTabbedPane.add(miscTab)
    mainTabbedPane.add(ranksTab)
    mainTabbedPane.add(nadeHelperTab)
    mainTabbedPane.add(skinChangerTab)
    mainTabbedPane.add(optionsTab)
    mainTabbedPane.add(configsTab)

    glowEspTable = GlowEspTable()
    chamsEspTable = ChamsEspTable()
    indicatorEspTable = IndicatorEspTable()
    boxEspTable = BoxEspTable()
    snaplinesEspTable = SnaplinesEspTable()
    footStepsEspTable = FootstepsEspTable()
    hitMarkerTable = HitMarkerTable()
    nadesTable = NadesTable()
    miscVisualsTable = MiscVisualsTable()

    movementTable = MovementTable()
    fovChangerTable = FOVChangerTable()
    bombTable = BombTable()
    othersTable = OthersTable()

    mainAimTab = MainAimTable()
    backtrackTab = BacktrackTable()
    overridenWeapons = OverrideTable()


    aimTab.contentTable.add(mainAimTab)
    aimTab.contentTable.add(triggerTab)
    aimTab.contentTable.add(backtrackTab)
    aimTab.contentTable.add(overridenWeapons)

    //espTabbedPane.add(glowEspTable)
    //espTabbedPane.add(chamsEspTab)
    //espTabbedPane.add(indicatorEspTab)
    //espTabbedPane.add(boxEspTab)
    //espTabbedPane.add(snaplinesEspTab)
    //espTabbedPane.add(footStepsEspTab)
    //espTabbedPane.add(hitMarkerTab)
    //espTabbedPane.add(nadesTab)
    //espTabbedPane.add(miscVisualsTab)

    uiSpecList.remove()
    uiSpecList = UISpectatorList()

    uiBombWindow.remove()
    uiBombWindow = UIBombTimer()

    uiKeybinds.remove()
    uiKeybinds = UIKeybinds()

    mainTabbedPane.switchTab(configsTab)

    uiRefreshing = false
}