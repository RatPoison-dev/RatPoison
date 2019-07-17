package rat.poison.scripts.esp

import rat.poison.curSettings
import rat.poison.strToBool

fun esp() {
	val d = curSettings["DEBUG"]!!.strToBool()

	if (d) { //Placeholders for cleanup
		println("[Debug] Initializing ESPs")
	}

	glowEsp()
	indicatorEsp()
	boxEsp()

	if (d) { //Placeholders for cleanup
		println("[Debug] GlowEsp, IndicatorEsp, BoxEsp initialized")
	}

	skeletonEsp()
	chamsEsp()
	hitSoundEsp()

	if (d) { //Placeholders for cleanup
		println("[Debug] SkeletonEsp, ChamsEsp, HitsoundEsp initialized")
	}

	radarEsp()

	if (d) { //Placeholders for cleanup
		println("[Debug] RadarEsp initialized")
	}
}