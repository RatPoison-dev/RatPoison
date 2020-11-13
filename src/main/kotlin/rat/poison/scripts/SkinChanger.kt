package rat.poison.scripts

import org.apache.commons.lang3.StringUtils
import rat.poison.curSettings
import rat.poison.game.CSGO.ENTITY_SIZE
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.clientState
import rat.poison.game.entity.steamID
import rat.poison.game.entity.type
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.hMyWeapons
import rat.poison.game.netvars.NetVarOffsets.m_OriginalOwnerXuidLow
import rat.poison.game.netvars.NetVarOffsets.m_flFallbackWear
import rat.poison.game.netvars.NetVarOffsets.m_iAccountID
import rat.poison.game.netvars.NetVarOffsets.m_iItemIDHigh
import rat.poison.game.netvars.NetVarOffsets.m_nFallbackPaintKit
import rat.poison.game.netvars.NetVarOffsets.m_nFallbackStatTrak
import rat.poison.game.offsets.ClientOffsets.dwEntityList
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.toSkinWeaponClass
import rat.poison.utils.shouldPostProcess

//https://github.com/0xf1a/xSkins

private var shouldUpdate = false

fun skinChanger() = every(1, continuous = true, inGameCheck = true) {
    if ((!curSettings["SKINCHANGER"].strToBool() && !curSettings["KNIFECHANGER"].strToBool())) return@every

    try {
        val sID = me.steamID()
        val split = sID.split(":")
        if (split.size < 3 || !StringUtils.isNumeric(split[2]) || !StringUtils.isNumeric(split[1])) { //This SHOULD make try catch redundant, as toInt() is the only catch case...
            return@every
        }

        val pID = (split[2].toInt() * 2) + split[1].toInt()

        for (i in 0..8) {
            val myWeapon = csgoEXE.uint(me + hMyWeapons + i * 0x4) and 0xFFF

            if (myWeapon.toInt() == 4095) continue

            val weaponEntity = clientDLL.uint(dwEntityList + (myWeapon - 1) * ENTITY_SIZE)

            if (weaponEntity.type().gun && myWeapon > 0 && weaponEntity > 0) {
                if (curSettings["SKINCHANGER"].strToBool()) {
                    val sWep = curSettings["SKIN_" + weaponEntity.type().name].toSkinWeaponClass()

                    //Change these to read weaponEntity kit to a mem and read from it
                    val accountID = csgoEXE.int(weaponEntity + m_OriginalOwnerXuidLow)

                    if (pID == accountID) {
                        val curWepPaint = csgoEXE.int(weaponEntity + m_nFallbackPaintKit)
                        val curStatTrak = csgoEXE.int(weaponEntity + m_nFallbackStatTrak)
                        val curWear = csgoEXE.float(weaponEntity + m_flFallbackWear)

                        val wantedWepPaint = sWep.tSkinID
                        val wantedStatTrak = sWep.tStatTrak
                        val wantedWear = sWep.tWear

                        csgoEXE[weaponEntity + m_iItemIDHigh] = -1
                        csgoEXE[weaponEntity + m_nFallbackPaintKit] = sWep.tSkinID
                        csgoEXE[weaponEntity + m_flFallbackWear] = sWep.tWear
                        csgoEXE[weaponEntity + m_nFallbackStatTrak] = sWep.tStatTrak
                        csgoEXE[weaponEntity + m_iAccountID] = pID

                        if (((curWepPaint != wantedWepPaint) || (curStatTrak != wantedStatTrak) || (curWear != wantedWear)) && curSettings["FORCE_UPDATE_AUTO"].strToBool()) {
                            shouldUpdate = true
                        }
                    }
                }
            }
        }

        if (shouldUpdate) {
            forcedUpdate()
            shouldUpdate = false
        } else {
            if (csgoEXE.int(clientState + 0x174) > 0) {
                for (i in 0..8) {
                    val myWeapon = csgoEXE.uint(me + hMyWeapons + i * 0x4) and 0xFFF

                    if (myWeapon.toInt() == 4095) continue

                    val weaponEntity = clientDLL.uint(dwEntityList + (myWeapon - 1) * ENTITY_SIZE)

                    if (weaponEntity.type().gun && myWeapon > 0 && weaponEntity > 0) {
                        if (curSettings["SKINCHANGER"].strToBool()) {
                            //Change these to read weaponEntity kit to a mem and read from it
                            val accountID = csgoEXE.int(weaponEntity + m_OriginalOwnerXuidLow)

                            if (pID == accountID) {
                                csgoEXE[weaponEntity + m_iItemIDHigh] = 0
                            }
                        }
                    }
                }

                Thread.sleep(500)
            }
        }
    } catch (e: Exception) {
        println("SkinChanger.kt Error...")
        e.printStackTrace()
        //nah
    }
}

fun forcedUpdate() {
    if (csgoEXE.int(clientState + 0x174) > 0 && shouldPostProcess) { //scr8 up
        csgoEXE[clientState + 0x174] = -1
    }
}