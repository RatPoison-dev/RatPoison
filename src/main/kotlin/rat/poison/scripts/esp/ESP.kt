package rat.poison.scripts.esp

import com.sun.jna.Memory
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.game.CSGO
import rat.poison.game.Color
import rat.poison.game.entity.Entity
import rat.poison.game.entity.EntityType
import rat.poison.scripts.esp.GlowESP.glowEspApp
import rat.poison.scripts.esp.GlowESP.glowEspEvery
import rat.poison.utils.extensions.uint
import rat.poison.utils.generalUtil.strToBool
import rat.poison.utils.generalUtil.toInt

var glowTarget = -1L

fun esp() {
	if (curSettings["MENU"].strToBool()) { //Temp until app fix
		if (dbg) { println("[DEBUG] Initializing Glow ESP") }; glowEspApp()
		if (dbg) { println("[DEBUG] Initializing Indicator ESP") }; indicatorEsp()
		if (dbg) { println("[DEBUG] Initializing Box ESP") }; boxEsp()
		if (dbg) { println("[DEBUG] Initializing Skeleton ESP") }; skeletonEsp()
		if (dbg) { println("[DEBUG] Initializing Snap Lines") }; snapLines()
	} else {
		if (dbg) { println("[DEBUG] Menu disabled, using alternate glow esp") }; glowEspEvery()
	}

	if (dbg) { println("[DEBUG] Initializing Footstep ESP") }; footStepEsp() //Needed with & without menu
	if (dbg) { println("[DEBUG] Initializing Chams ESP") }; chamsEsp()
	if (dbg) { println("[DEBUG] Initializing Hitsound ESP") }; hitSoundEsp()
	if (dbg) { println("[DEBUG] Initializing Radar ESP") }; radarEsp()
}

fun Entity.glow(color: Color, glowType: Int) {
	val glowMemory: Memory by lazy {
		Memory(60)
	}

	//Revalidate
	val ent = CSGO.csgoEXE.uint(this)
	val entType = EntityType.byEntityAddress(ent)

	if ((entType == EntityType.CCSPlayer || entType.weapon || entType.grenade || entType.bomb) && ent > 0) {
		CSGO.csgoEXE.read(this, glowMemory)

		if (glowMemory.getPointer(0) != null) {
			glowMemory.setFloat(0x4, color.red / 255F)
			glowMemory.setFloat(0x8, color.green / 255F)
			glowMemory.setFloat(0xC, color.blue / 255F)
			glowMemory.setFloat(0x10, color.alpha.toFloat())
			glowMemory.setByte(0x24, 1)
			glowMemory.setByte(0x25, 0)

			glowMemory.setByte(0x26, curSettings["INV_GLOW_ESP"].toBoolean().toInt().toByte())

			glowMemory.setByte(0x2C, glowType.toByte())

			CSGO.csgoEXE.write(this, glowMemory)
		}
	}
}

fun String.toGlowNum(): Int {
	return when(this) {
		"NORMAL" -> 0
		"MODEL" -> 1
		"VISIBLE" -> 2
		else -> 3
	}
}