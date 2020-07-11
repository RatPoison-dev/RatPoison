package rat.poison.ui.uiHelpers.overrideWeaponsUI

import rat.poison.curSettings
import rat.poison.utils.generalUtil.pull

fun splitOverrideString(curWep: String): MutableList<String> {
    var tStr = curWep
    tStr = tStr.replace("oWeapon(", "").replace(")", "")
    val tSA = tStr.split(", ")

    return tSA as MutableList<String>
}

fun getOverrideVar(curWep: String, index: Int): Any {
    val tSA = splitOverrideString(curSettings[curWep])

    return tSA.pull(index)
}

fun getOverrideVarIndex(curWep: String, varName: String): Int {
    val tSA = splitOverrideString(curWep)

    var idx = -1
    for (i in tSA.indices) {
        if (tSA[i].contains(varName)) {
            idx = i
        }
    }
    return idx
}

fun setOverrideVar(curWep: String, index: Int, value: Any) {
    val tSA = splitOverrideString(curSettings[curWep])

    tSA[index] = tSA[index].split("=".toRegex(), 2)[0] + "=" + value.toString()
    var newWep = tSA.toString()
    newWep = newWep.replace("[", "oWeapon(").replace("]", ")")
    curSettings[curWep] = newWep
}