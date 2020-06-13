package rat.poison.ui

import rat.poison.App.uiAimOverridenWeapons
import rat.poison.App.uiMenu
import rat.poison.App.uiSpecList
import rat.poison.opened
import rat.poison.ui.tabs.*
import rat.poison.ui.tabs.visualstabs.*
import rat.poison.ui.uiPanelTables.overridenWeaponsUpdate

fun uiUpdate() {
    if (!opened) return

    overridenWeaponsUpdate()
    visualsTabUpdate()
    glowEspTabUpdate()
    chamsEspTabUpdate()
    indicatorEspTabUpdate()
    boxEspTabUpdate()
    hitMarkerTabUpdate()
    skinChangerTabUpdate()
    snaplinesEspTabUpdate()
    footStepsEspTabUpdate()
    miscTabUpdate()
    rcsTabUpdate()
    nadeHelperTabUpdate()
    updateTrig()
    updateAim()
    updateBacktrack()
    updateDisableEsp()
    updateOptionsTab()
    nadesVTUpdate()

    //Update windows
    uiAimOverridenWeapons.setPosition(uiMenu.x+uiMenu.width+4F, uiMenu.y)
}