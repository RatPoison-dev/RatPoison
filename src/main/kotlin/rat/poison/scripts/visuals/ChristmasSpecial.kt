package rat.poison.scripts.visuals

import rat.poison.curSettings
import rat.poison.game.CSGO.gameWidth
import rat.poison.overlay.App

val texture by lazy(LazyThreadSafetyMode.NONE) {
	getWeaponTexture(App.assetManager, "bigrat-christmas-hat")
}

fun christmasSpecial() = App {
	sb.begin()
	sb.draw(
		texture,
		gameWidth / 7F + curSettings.int["CHRISTMAS_RAT_OFFSET"],
		-5F,
		texture.width.toFloat(),
		texture.height.toFloat()
	)
	sb.end()
}