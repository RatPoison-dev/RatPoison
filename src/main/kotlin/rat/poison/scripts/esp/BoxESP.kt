package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.utils.Align
import rat.poison.App
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.settings.BOX_ESP
import rat.poison.settings.BOX_ESP_DETAILS
import rat.poison.settings.ENABLE_ESP
import rat.poison.settings.MENUTOG
import rat.poison.utils.Vector

private val vHead = Vector()
private val vFeet = Vector()

private val vTop = Vector(0.0, 0.0, 0.0)
private val vBot = Vector(0.0, 0.0, 0.0)

private val boxes = Array(128) { Box() }

private var currentIdx = 0

internal fun boxEsp() = App {
	if (!BOX_ESP || !ENABLE_ESP || MENUTOG) return@App

	forEntities(ccsPlayer) { //Only enemies atm
		val entity = it.entity
		if (entity == me || entity.dead() || entity.dormant()) return@forEntities false

		vHead.set(entity.bone(0xC), entity.bone(0x1C), entity.bone(0x2C) + 9)
		vFeet.set(vHead.x, vHead.y, vHead.z - 75)

		if (worldToScreen(vHead, vTop) && worldToScreen(vFeet, vBot)) {
			val boxH = vBot.y - vTop.y
			val boxW = boxH / 5F

			val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1
			val c = if (bomb > 0 && entity == bomb.carrier()) Color.GREEN
			else if (me.team() == entity.team()) Color.BLUE else Color.RED

			val sx = (vTop.x - boxW).toInt()
			val sy = vTop.y.toInt()

			boxes[currentIdx].apply {
				x = sx
				y = sy
				w = Math.ceil(boxW * 2).toInt()
				h = boxH.toInt()
				color = c
				health = entity.health()
				weapon = entity.weapon().name
			}

			currentIdx++
		}

		false
	}

	shapeRenderer.apply sr@ {
		begin()
		for (i in 0 until currentIdx) boxes[i].apply {
			this@sr.color = color
			rect(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())

			if (BOX_ESP_DETAILS) {
				sb.begin()
				textRenderer.apply {
					val text = StringBuilder()
					val glyph = GlyphLayout()
					text.append("Health: " + health.toLong() + '\n' + "Weapon: " + weapon)
					glyph.setText(textRenderer, text, 0, (text as CharSequence).length, Color.WHITE, 10F, Align.left, false, null)
					textRenderer.draw(sb, glyph, x.toFloat() + 2F, y.toFloat())
				}
				sb.end()
			}
		}
		end()
	}

	currentIdx = 0
}

private data class Box(var x: Int = -1, var y: Int = -1,
                       var w: Int = -1, var h: Int = -1,
                       var color: Color = Color.WHITE, var health: Int = 100, var weapon: String = "")