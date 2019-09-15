package rat.poison.scripts.esp

import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.game.Color
import rat.poison.game.entity.Entity
import rat.poison.scripts.esp.GlowESP.glowEspApp
import rat.poison.scripts.esp.GlowESP.glowEspEvery
import rat.poison.strToBool
import java.util.concurrent.atomic.AtomicLong

val glowTarget = AtomicLong(-1)

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

	if (dbg) { println("[DEBUG] Initializing Chams ESP") }; chamsEsp()
	if (dbg) { println("[DEBUG] Initializing Hitsound ESP") }; hitSoundEsp()
	if (dbg) { println("[DEBUG] Initializing Radar ESP") }; radarEsp()
}

fun Entity.glow(color: Color, model: Boolean) {
	rat.poison.game.CSGO.csgoEXE[this + 0x4] = color.red / 255F
	rat.poison.game.CSGO.csgoEXE[this + 0x8] = color.green / 255F
	rat.poison.game.CSGO.csgoEXE[this + 0xC] = color.blue / 255F
	rat.poison.game.CSGO.csgoEXE[this + 0x10] = color.alpha.toFloat()
	rat.poison.game.CSGO.csgoEXE[this + 0x24] = true //Render When Occluded
	rat.poison.game.CSGO.csgoEXE[this + 0x25] = false //Render When Unoccluded

	rat.poison.game.CSGO.csgoEXE[this + 0x26] = curSettings["INV_GLOW_ESP"].strToBool() //Full Bloom Render

	if (curSettings["MODEL_AND_GLOW"].strToBool())
		rat.poison.game.CSGO.csgoEXE[this + 0x2C] = model
	else
		rat.poison.game.CSGO.csgoEXE[this + 0x2C] = curSettings["MODEL_ESP"].strToBool()
}