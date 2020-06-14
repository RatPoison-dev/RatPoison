package rat.poison.scripts

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
import rat.poison.game.netvars.NetVarOffsets.m_iItemIDHigh
import rat.poison.game.netvars.NetVarOffsets.m_nFallbackPaintKit
import rat.poison.game.netvars.NetVarOffsets.m_nFallbackStatTrak
import rat.poison.game.offsets.ClientOffsets.dwEntityList
import rat.poison.toSkinWeaponClass
import rat.poison.utils.every
import rat.poison.utils.extensions.uint
import rat.poison.utils.notInGame
import rat.poison.utils.varUtil.strToBool

//https://github.com/0xf1a/xSkins

private var preBayonetCT = 89
private var preBayonetT = 64

//fuck
fun skinChanger() = every(2) {
    if (!curSettings["SKINCHANGER"].strToBool() || notInGame) return@every

    //val modelIndex = curSettings["KNIFE_IDX"].toInt() //For knife
    //val indexOffset = if (modelIndex < 11) 1 else 2

    //val activeWeaponEntID = csgoEXE.int(me.weaponEntity() + m_iViewModelIndex)
    //val activeWeapon = me.weapon().id

    //val calcModelIndex: Int

    //calcModelIndex = if (me.team() == 3L) { //Cant read from sv_precache correctly cause im dumb //stfu loser bitch gg
    //    activeWeaponEntID + preBayonetCT + 3 * modelIndex + indexOffset
    //} else {
    //    activeWeaponEntID + preBayonetT + 3 * modelIndex + indexOffset
    //}

    var invalid = false

    for (i in 0..8) {
        val myWeapon = csgoEXE.uint(me + hMyWeapons + i * 0x4) and 0xFFF
        val weaponEntity = clientDLL.uint(dwEntityList + (myWeapon - 1) * ENTITY_SIZE)

        if (weaponEntity > 0) {
            val sID = me.steamID()
            val split = sID.split(":")
            if (split.size < 3) {
                return@every
            }
            val pID = (split[2].toInt() * 2) + split[1].toInt()

            if (weaponEntity.type().gun) {
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

                        //Fuck stattrak fuck wear and fuck 12 you another bitch ass causing issues
                        if (((curWepPaint != wantedWepPaint) || (curStatTrak != wantedStatTrak) || (curWear != wantedWear)) && curSettings["FORCE_UPDATE_AUTO"].strToBool()) {
                            invalid = true
                        }
                    }
                }
            }
        }
        //else if (weaponEntity.type().knife) {
        //    if (curSettings["KNIFE_IDX"].toInt() != -1 && curSettings["KNIFECHANGER"].strToBool()) {
            //csgoEXE[weaponEntity + iItemDefinitionIndex] = Weapons.KNIFE_GUT.id //?
            //csgoEXE[weaponEntity + m_nModelIndex] = calcModelIndex
            //csgoEXE[weaponEntity + m_iViewModelIndex] = calcModelIndex
        //    csgoEXE[weaponEntity + m_iEntityQuality] = 3

//                val sWep = curSettings["SKIN_KNIFE"].toSkinWeaponClass()
//
//                val curWepPaint = csgoEXE.int(weaponEntity + m_nFallbackPaintKit)
//                val wantedWepPaintKit = sWep.tSkinID
//
//                csgoEXE[weaponEntity + m_iItemIDHigh] = -1
//                csgoEXE[weaponEntity + m_nFallbackPaintKit] = sWep.tSkinID
//
//                if (curWepPaint != wantedWepPaintKit && curSettings["FORCE_UPDATE_AUTO"].strToBool()) {
//                    //println("cur: $curWepPaint wanted: $wantedWepPaintKit")
//                    //forcedUpdate() //Cant without fucking up el knifoskin, try starting a thread and loop setting paintKit an call forceUpdate here in the middle?
//                }
         //   }
        //}
    }

    if (invalid) {
        forcedUpdate()
    }

    //if (curSettings["KNIFE_IDX"].toInt() != -1 && curSettings["KNIFECHANGER"].strToBool()) {
    //    var knifeVMod = csgoEXE.uint(me + m_hViewModel) and 0xFFF
    //    knifeVMod = clientDLL.uint(dwEntityList + (knifeVMod - 1) * ENTITY_SIZE)

    //    if (((activeWeapon == 42 || activeWeapon == 59) && activeWeaponEntID > 0) || activeWeapon == Weapons.KNIFE_SKELETON.id) {
    //        if (csgoEXE.int(knifeVMod + m_nModelIndex) != calcModelIndex) {
    //            csgoEXE[knifeVMod + m_nModelIndex] = calcModelIndex
    //        }
    //    }
    //}
}

fun forcedUpdate() {
    if (csgoEXE.int(clientState + 0x174) > 0) {
        csgoEXE[clientState + 0x174] = -1
        Thread.sleep(25)
    }
}