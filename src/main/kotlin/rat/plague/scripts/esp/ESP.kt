

package rat.plague.scripts.esp

import rat.plague.settings.ENABLE_ESP

fun esp() {
	if (!ENABLE_ESP) return

	glowEsp()
	boxEsp()
	skeletonEsp()
	chamsEsp()
}