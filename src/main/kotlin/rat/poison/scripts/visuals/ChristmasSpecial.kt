package rat.poison.scripts.visuals

import rat.poison.game.CSGO.gameWidth
import rat.poison.overlay.App

fun christmasSpecial() = App {
    val texture = getWeaponTexture(assetManager, "bigrat-christmas-hat")
    sb.begin()
    sb.draw(texture, gameWidth/7F, -5F, texture.width.toFloat(), texture.height.toFloat())
    sb.end()
}