package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.strToColor
import rat.poison.utils.Vector

private val vHead = Vector()
private val vFeet = Vector()

private val vTop = Vector(0.0, 0.0, 0.0)
private val vBot = Vector(0.0, 0.0, 0.0)

private val boxes = Array(128) { Box() }

private var currentIdx = 0

internal fun boxEsp() = App {
	if (!curSettings["BOX_ESP"]!!.strToBool() || !curSettings["ENABLE_ESP"]!!.strToBool() || MENUTOG) return@App

	forEntities(ccsPlayer) { //Only enemies atm
		val entity = it.entity
		if (entity == me || entity.dead() || entity.dormant() || (!curSettings["BOX_SHOW_ENEMIES"]!!.strToBool() && me.team() != entity.team()) || (!curSettings["BOX_SHOW_ENEMIES"]!!.strToBool() && me.team() == entity.team()) ) return@forEntities false

		vHead.set(entity.bone(0xC), entity.bone(0x1C), entity.bone(0x2C) + 9)
		vFeet.set(vHead.x, vHead.y, vHead.z - 75)

		if (worldToScreen(vHead, vTop) && worldToScreen(vFeet, vBot)) {
			val boxH = vBot.y - vTop.y
			val boxW = boxH / 5F

			//val bomb: Entity = entityByType(EntityType.CC4)?.entity ?: -1
			val tCol = curSettings["TEAM_COLOR"]!!.strToColor()
			val eCol = curSettings["ENEMY_COLOR"]!!.strToColor()
			val c = if (me.team() == entity.team()) Color(tCol.red.toFloat(), tCol.green.toFloat(), tCol.blue.toFloat(), 1F) else Color(eCol.red.toFloat(), eCol.green.toFloat(), eCol.blue.toFloat(), 1F)

			val sx = (vTop.x - boxW).toInt()
			val sy = vTop.y.toInt()

			boxes[currentIdx].apply {
				x = sx
				y = sy
				w = Math.ceil(boxW * 2).toInt()
				h = boxH.toInt()
				color = c
				health = entity.health().toFloat()
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

			if (curSettings["BOX_ESP_DETAILS"]!!.strToBool()) {
				textRenderer.apply {
					val glyph = GlyphLayout()

					sb.begin()
					color = Color.WHITE
					val text = StringBuilder()
					text.append(weapon)
					glyph.setText(textRenderer,text, 0, (text as CharSequence).length, Color.WHITE, 10F, Align.center, false, null)
					textRenderer.draw(sb, glyph, (x+(w/2)).toFloat(), (y+h-4).toFloat())
					sb.end()

					set(ShapeRenderer.ShapeType.Filled)
					this@sr.color = Color(1F - (.01F*health), (.01F*health), 0F, 1F)
					rect(x+w-4F, (y+h).toFloat(), w*.1F, -(h*(health/100F))) //Something better than ((4F*((x+w)/(x+w)*1.2F)
					set(ShapeRenderer.ShapeType.Line)
					this@sr.color = Color.WHITE
				}
			}
		}
		end()
	}

	currentIdx = 0
}

private data class Box(var x: Int = -1, var y: Int = -1,
					   var w: Int = -1, var h: Int = -1,
					   var color: Color = Color.WHITE, var health: Float = 100F, var weapon: String = "")