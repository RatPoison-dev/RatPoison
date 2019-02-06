package rat.poison.scripts.esp

import rat.poison.settings.ENABLE_ESP

fun esp() {
	if (!ENABLE_ESP) return

	glowEsp()
	boxEsp()
	skeletonEsp()
	chamsEsp()
	indicatorEsp()
	hitSoundEsp()
}