package rat.poison.ui

import rat.poison.overlay.App.uiKeybinds
import rat.poison.overlay.opened
import rat.poison.ui.uiTabs.*
import rat.poison.ui.uiTabs.aimTables.BacktrackTable
import rat.poison.ui.uiTabs.aimTables.MainAimTable
import rat.poison.ui.uiTabs.aimTables.OverrideTable
import rat.poison.ui.uiTabs.aimTables.overridenWeaponsUpdate
import rat.poison.ui.uiTabs.miscTables.*
import rat.poison.ui.uiTabs.visualsTables.*
import rat.poison.ui.uiWindows.*

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
    rcsTabUpdate()
    miscVisualTabUpdate()
    optionsTabUpdate()
    updateBacktrack()
    optionsTabUpdate()
    updateTrig()
    updateAim()
    updateDisableEsp()
    keybindsUpdate(null)

    //Update windows

    //Update lists
    configsTabUpdate()
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
    skinChangerTab = SkinChangerTab()
    optionsTab = OptionsTab()
    configsTab = ConfigsTab()

    mainTabbedPane.add(aimTab)
    mainTabbedPane.add(visualsTab)
    mainTabbedPane.add(rcsTab)
    mainTabbedPane.add(miscTab)
    mainTabbedPane.add(ranksTab)
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

    mainAimTab = MainAimTable()
    backtrackTable = BacktrackTable()
    overrideTable = OverrideTable()


    aimTab.contentTable.add(mainAimTab)
    aimTab.contentTable.add(triggerBotTable)
    aimTab.contentTable.add(backtrackTable)
    aimTab.contentTable.add(overrideTable)

    uiKeybinds.remove()
    uiKeybinds = UIKeybinds()

    mainTabbedPane.switchTab(configsTab)

    uiRefreshing = false
}