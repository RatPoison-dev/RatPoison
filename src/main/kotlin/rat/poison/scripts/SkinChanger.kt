package rat.poison.scripts

import com.sun.jna.Memory
import rat.poison.curSettings
import rat.poison.game.CSGO.ENTITY_SIZE
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.Weapons
import rat.poison.game.clientState
import rat.poison.game.entity.type
import rat.poison.game.entity.weapon
import rat.poison.game.entity.weaponEntity
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.hMyWeapons
import rat.poison.game.netvars.NetVarOffsets.iItemDefinitionIndex
import rat.poison.game.netvars.NetVarOffsets.m_flFallbackWear
import rat.poison.game.netvars.NetVarOffsets.m_iItemIDHigh
import rat.poison.game.netvars.NetVarOffsets.m_nFallbackPaintKit
import rat.poison.game.netvars.NetVarOffsets.m_nFallbackSeed
import rat.poison.game.netvars.NetVarOffsets.m_nFallbackStatTrak
import rat.poison.game.netvars.NetVarOffsets.m_szCustomName
import rat.poison.game.offsets.ClientOffsets.dwEntityList
import rat.poison.game.offsets.EngineOffsets.dwModelPrecache
import rat.poison.strToBool
import rat.poison.toSkinWeaponClass
import rat.poison.utils.every
import rat.poison.utils.extensions.uint

//https://github.com/0xf1a/xSkins

fun skinChanger() = every(1, continuous = true) {
    if (!curSettings["SKINCHANGER"].strToBool()) return@every
//    var modelIndex = 0 //For knife
//    var tmpPlayer = csgoEXE.uint(dwLocalPlayer)
//
//    if (me <= 0) {
//        modelIndex = 0
//    } else if (me != tmpPlayer) {
//        me = tmpPlayer
//        modelIndex = 0
//    }

    //while (!modelIndex.toString().strToBool()) {
        //modelIndex = getModelIndex()
    //}

    for (i in 0..8) {
        val myWeapon = csgoEXE.uint(me + hMyWeapons + i * 0x4) and 0xFFF
        val weaponEntity = clientDLL.uint(dwEntityList + (myWeapon - 1) * ENTITY_SIZE)

        val weaponIndex = csgoEXE.short(weaponEntity + iItemDefinitionIndex)

        //val weaponSkin = getSkinNum(weaponIndex.toInt())

        if (weaponEntity.type().gun) {
            val sWep = curSettings["SKIN_" + weaponEntity.type().name].toSkinWeaponClass()

            val curWepPaint = csgoEXE.uint(weaponEntity + m_nFallbackPaintKit)
            val wantedWepPaint = sWep.tSkinID

            csgoEXE[weaponEntity + m_iItemIDHigh] = -1
            csgoEXE[weaponEntity + m_nFallbackPaintKit] = sWep.tSkinID
            csgoEXE[weaponEntity + m_flFallbackWear] = sWep.tWear
            csgoEXE[weaponEntity + m_nFallbackStatTrak] = sWep.tStatTrak
            //csgoEXE[weaponEntity + m_nFallbackSeed] = sWep.tSeed

            if ((curWepPaint.toInt() != wantedWepPaint) && curSettings["FORCE_UPDATE_AUTO"].strToBool()) {
                forcedUpdate()
            }
        }
    }

//    var weaponIndex = me.weapon().id
//    var activeWepEntity = me.weaponEntity()
//    csgoEXE[activeWepEntity + m_iItemIDHigh] = -1
//    csgoEXE[activeWepEntity + m_nFallbackPaintKit] = 344
//    csgoEXE[activeWepEntity + m_flFallbackWear] = 0.00001F
//    csgoEXE[activeWepEntity + m_nFallbackStatTrak] = 1337





    //Set active weapon
//    var activeWeapon = me.weaponIndex()
//    var activeVModel = csgoEXE.uint(me + m_hViewModel)
//    activeVModel = csgoEXE.uint(dwEntityList + (activeVModel - 1) * 0x10)
//
//    csgoEXE[activeVModel + m_nModelIndex] = modelIndex

}

//fun getSkinNum(id: Int): Int { //Convert like oWeapons to parse easy
//    var retID = -1
//    when (id) {
//        Weapons.AWP.id -> retID = curSettings["AWP_SKIN_ID"].toInt()
//        Weapons.SSG08.id -> retID = curSettings["SCOUT_SKIN_ID"].toInt()
//        Weapons.AK47.id -> retID = curSettings["AK_SKIN_ID"].toInt()
//        Weapons.M4A4.id -> retID = curSettings["M4_SKIN_ID"].toInt()
//        Weapons.DESERT_EAGLE.id -> retID = curSettings["DEAGLE_SKIN_ID"].toInt()
//        Weapons.USP_SILENCER.id -> retID = curSettings["USPS_SKIN_ID"].toInt()
//    }
//
//    return retID
//}

fun getModelIndexByName(modelName: String): Int {
    val tmpMem: Memory by lazy {
        Memory(5000)
    }

    val nst = csgoEXE.uint(clientState + dwModelPrecache)
    val nsd = csgoEXE.uint(nst + 0x40)
    val nsdi = csgoEXE.uint(nsd + 0xC) //Convert to reading mem

    for (i in 0..1024) {
        val nsdi_i = csgoEXE.read(nsdi + 0xC + i * 0x34, tmpMem)

        val str = tmpMem.getString(0) //0 Offset?

        if (str == modelName && !nsdi_i) {
            return i
        }
    }

    return 0
}

fun getModelIndex(itemIndex: Int): Int {
    var ret = 0

    when (itemIndex) {
        Weapons.KNIFE.id -> ret = getModelIndexByName("models/weapons/v_knife_default_ct.mdl")
        Weapons.KNIFE_T.id -> ret = getModelIndexByName("models/weapons/v_knife_default_t.mdl")
        Weapons.KNIFE_BAYONET.id -> ret = getModelIndexByName("models/weapons/v_knife_bayonet.mdl")
        Weapons.KNIFE_FLIP.id -> getModelIndexByName("models/weapons/v_knife_flip.mdl")
        Weapons.KNIFE_GUT.id -> getModelIndexByName("models/weapons/v_knife_gut.mdl")
        Weapons.KNIFE_KARAMBIT.id -> getModelIndexByName("models/weapons/v_knife_karam.mdl")
        Weapons.KNIFE_M9_BAYONET.id -> getModelIndexByName("models/weapons/v_knife_m9_bay.mdl")
        Weapons.KNIFE_TACTICAL.id -> getModelIndexByName("models/weapons/v_knife_tactical.mdl")
        Weapons.KNIFE_FALCHION.id -> getModelIndexByName("models/weapons/v_knife_falchion_advanced.mdl")
        Weapons.KNIFE_BOWIE.id -> getModelIndexByName("models/weapons/v_knife_survival_bowie.mdl")
        Weapons.KNIFE_BUTTERFLY.id -> getModelIndexByName("models/weapons/v_knife_butterfly.mdl")
        Weapons.KNIFE_PUSH.id -> getModelIndexByName("models/weapons/v_knife_push.mdl")
        Weapons.KNIFE_URSUS.id -> getModelIndexByName("models/weapons/v_knife_ursus.mdl")
        Weapons.KNIFE_GYPSY.id -> getModelIndexByName("models/weapons/v_knife_gypsy_jackknife.mdl")
        Weapons.KNIFE_STILETTO.id -> getModelIndexByName("models/weapons/v_knife_stiletto.mdl")
        Weapons.KNIFE_WIDOWMAKER.id -> getModelIndexByName("models/weapons/v_knife_widowmaker.mdl")
        Weapons.KNIFE_CSS.id -> getModelIndexByName("models/weapons/v_knife_css.mdl")
        Weapons.KNIFE_CORD.id -> getModelIndexByName("models/weapons/v_knife_cord.mdl")
        Weapons.KNIFE_CANIS.id -> getModelIndexByName("models/weapons/v_knife_canis.mdl")
        Weapons.KNIFE_OUTDOOR.id -> getModelIndexByName("models/weapons/v_knife_outdoor.mdl")
        Weapons.KNIFE_SKELETON.id -> getModelIndexByName("models/weapons/v_knife_skeleton.mdl")
    }

    return ret
}

fun forcedUpdate() {
    if (csgoEXE.uint(clientState + 0x174).toInt() != -1) {
        csgoEXE[clientState + 0x174] = -1
    }
}