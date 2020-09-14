package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.utils.Align
import com.sun.jna.Memory
import rat.poison.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.forEntities
import rat.poison.game.hooks.defuseKitEntities
import rat.poison.game.me
import rat.poison.game.worldToScreen
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.HEAD_BONE
import rat.poison.settings.MENUTOG
import rat.poison.utils.Vector
import rat.poison.utils.notInGame
import kotlin.math.ceil

private val vHead = Vector()
private val vFeet = Vector()

private val vTop = Vector(0.0, 0.0, 0.0)
private val vBot = Vector(0.0, 0.0, 0.0)

private val boxes = Array(128) { Box() }

private data class Box(var x0: Float = 0F, var y0: Float = 0F,
					   var x1: Float = 0F, var y1: Float = 0F,
					   var color: Color = Color.WHITE,
					   var health: Float = 100F,
					   var armor: Float = 100F,
					   var weapon: String = "",
					   var name: String = "",
					   var type: EntityType = EntityType.NULL)

private var currentIdx = 0

fun boxEsp() = App {
	if (!curSettings["ENABLE_BOX_ESP"].strToBool() || !curSettings["ENABLE_ESP"].strToBool() || MENUTOG || notInGame) return@App

	val meTeam = me.team()
	forEntities(ccsPlayer) { //Replace positioning with abs...?
		//Only enemies atm
		val entity = it.entity
		val entTeam = entity.team()

		val dormCheck = (entity.dormant() && !DANGER_ZONE)
		val enemyCheck = ((!curSettings["BOX_SHOW_ENEMIES"].strToBool() && meTeam != entTeam) && !DANGER_ZONE)
		val teamCheck = ((!curSettings["BOX_SHOW_TEAM"].strToBool() && meTeam == entTeam) && !DANGER_ZONE)

		if (entity == me || entity.dead() || dormCheck || enemyCheck || teamCheck) return@forEntities false

		//Reduce r/w
		//Replace later
		val boneMemory: Memory by lazy {
			Memory(3984)
		}

		val entityMemory: Memory by lazy {
			Memory(45948)
		}

		csgoEXE.read(entity.boneMatrix(), boneMemory)

		val xOff = boneMemory.getFloat(((0x30L * HEAD_BONE) + 0xC)).toDouble()
		val yOff = boneMemory.getFloat(((0x30L * HEAD_BONE) + 0x1C)).toDouble()
		val zOff = boneMemory.getFloat(((0x30L * HEAD_BONE) + 0x2C)).toDouble()

		vHead.set(xOff, yOff, zOff + 9)
		vFeet.set(vHead.x, vHead.y, vHead.z - 75)

		if (worldToScreen(vHead, vTop) && worldToScreen(vFeet, vBot)) {
			val tCol = curSettings["BOX_TEAM_COLOR"].strToColor()
			val eCol = curSettings["BOX_ENEMY_COLOR"].strToColor()
			val c = if (meTeam == entTeam) Color(tCol.red/255F, tCol.green/255F, tCol.blue/255F, 1F) else Color(eCol.red/255F, eCol.green/255F, eCol.blue/255F, 1F)

			val boxH = vBot.y - vTop.y
			val sW = ceil((boxH / 5.0) * 2.0) / 2.0
			val sH = 4

			val box0x: Float
			val box1x: Float
			if (vBot.x > vTop.x) {
				box0x = (vBot.x - sW).toFloat()
				box1x = (vTop.x + sW).toFloat()
			} else {
				box0x = (vTop.x - sW).toFloat()
				box1x = (vBot.x + sW).toFloat()
			}
			val box0y = (vTop.y + sH).toFloat()
			val box1y = (vTop.y + boxH - sH).toFloat()

			boxes[currentIdx].apply {
				x0 = box0x
				y0 = box0y
				x1 = box1x
				y1 = box1y
				color = c

				//Reduce r/w
				//Replace later
				csgoEXE.read(entity, entityMemory)

				health = entityMemory.health().toFloat()
				armor = entityMemory.armor().toFloat()
				weapon = entity.weapon().name
				name = entity.name()

				type = EntityType.CCSPlayer
			}

			currentIdx++
		}
		boneMemory.clear()
		entityMemory.clear()

		false
	}

	if (curSettings["BOX_SHOW_DEFUSERS"].strToBool()) {
		val tmp = defuseKitEntities
		tmp.forEachIndexed { _, entity ->
			val entPos = entity.position()

			val vTop = Vector()
			val vBot = Vector()

			if (worldToScreen(Vector(entPos.x, entPos.y, entPos.z + 10.0), vTop) && worldToScreen(Vector(entPos.x, entPos.y, entPos.z - 10.0), vBot)) {
				val boxH = (vBot.y - vTop.y).toFloat()
				val boxW = boxH / 2F
				val sx = (vTop.x - boxW).toFloat()
				val sy = vTop.y.toFloat()
				val dCol = curSettings["BOX_DEFUSER_COLOR"].strToColor()
				val c = Color(dCol.red / 255F, dCol.green / 255F, dCol.blue / 255F, 1F)

				boxes[currentIdx].apply {
					x0 = sx
					y0 = sy
					x1 = (sx + ceil(boxW * 2F))
					y1 = sy + boxH
					color = c

					type = EntityType.CEconEntity
				}

				currentIdx++
			}
		}
	}

	shapeRenderer.apply sr@{
		begin()

		//To funcs?
		////Vars
		val bEspName = curSettings["BOX_ESP_NAME"].strToBool()
		val bEspNamePos = curSettings["BOX_ESP_NAME_POS"].replace("\"", "")
		val bEspWeapon = curSettings["BOX_ESP_WEAPON"].strToBool()
		val bEspWeaponPos = curSettings["BOX_ESP_WEAPON_POS"].replace("\"", "")

		val bEspHealth = curSettings["BOX_ESP_HEALTH"].strToBool()
		val bEspHealthPos = curSettings["BOX_ESP_HEALTH_POS"].replace("\"", "")
		val bEspArmor = curSettings["BOX_ESP_ARMOR"].strToBool()
		val bEspArmorPos = curSettings["BOX_ESP_ARMOR_POS"].replace("\"", "")

		for (i in 0 until currentIdx) boxes[i].apply {
			this@sr.color = color
			val w = x1 - x0
			val h = y1 - y0

			rect(x0, y0, w, h)

			val detailTextColor = curSettings["BOX_DETAILS_TEXT_COLOR"].strToColorGDX()

			if (type == EntityType.CCSPlayer) {
				if (curSettings["BOX_ESP_DETAILS"].strToBool()) {
					textRenderer.apply {
						this@sr.color = Color.WHITE
						val glyph = GlyphLayout()

						if (sb.isDrawing) {
							sb.end()
						}

						sb.begin()
						////Top
						var yAdd = 0F
						val boxDetailsTextTop = StringBuilder()
						boxDetailsTextTop.append(" ")

						if (bEspName && bEspNamePos == "T") {
							boxDetailsTextTop.append("$name\n")
							yAdd += 16F
						}
						if (bEspWeapon && bEspWeaponPos == "T") {
							boxDetailsTextTop.append(weapon)
							yAdd += 16F
						}

						if (boxDetailsTextTop.isNotBlank() && boxDetailsTextTop.isNotEmpty()) {
							glyph.setText(textRenderer, boxDetailsTextTop, 0, (boxDetailsTextTop as CharSequence).length, detailTextColor, 1F, Align.center, false, null)
							draw(sb, glyph, x0 + (x1 - x0) / 2F, y0 + yAdd)
						}
						////Top

						////Bottom
						val boxDetailsTextBottom = StringBuilder()
						boxDetailsTextBottom.append("")

						if (bEspName && bEspNamePos == "B") {
							boxDetailsTextBottom.append("$name\n")
						}
						if (bEspWeapon && bEspWeaponPos == "B") {
							boxDetailsTextBottom.append(weapon)
						}
						if (boxDetailsTextBottom.isNotBlank() && boxDetailsTextBottom.isNotEmpty()) {
							glyph.setText(this, boxDetailsTextBottom, 0, (boxDetailsTextBottom as CharSequence).length, detailTextColor, 1F, Align.center, false, "")
							draw(sb, glyph, x0 + w / 2F, y0 + h - 4F)
						}
						////Bottom
						sb.end()

						set(ShapeRenderer.ShapeType.Filled)

						val barWidth = clamp(w * .025F, -10F, -2F) //Why is this negative?

						////Left
						var leftMulti = 1

						if (bEspHealth && bEspHealthPos == "L") {
							this@sr.color = Color.BLACK
							rect(x0 + w + (barWidth * leftMulti), y0 + h, barWidth, -h) //Health outline

							this@sr.color = Color(1F - (.01F * health), (.01F * health), 0F, 1F)
							rect(x0 + w + (barWidth * leftMulti), y0 + h, barWidth, -(h * (health / 100F))) //Health

							leftMulti += 2
						}

						if (bEspArmor && bEspArmorPos == "L") {
							this@sr.color = Color.BLACK
							rect(x0 + w + (barWidth * leftMulti), y0 + h, barWidth, -h) //Armor outline

							this@sr.color = Color(0F, .3F, 1F, 1F)
							rect(x0 + w + (barWidth * leftMulti), y0 + h, barWidth, -(h * (armor / 100F))) //Armor
						}
						////Left

						////Right
						var rightMulti = 2

						if (bEspHealth && bEspHealthPos == "R") {
							this@sr.color = Color.BLACK
							rect(x0 - (barWidth * rightMulti), y0 + h, barWidth, -h) //Health outline

							this@sr.color = Color(1F - (.01F * health), (.01F * health), 0F, 1F)
							rect(x0 - (barWidth * rightMulti), y0 + h, barWidth, -(h * (health / 100F))) //Health

							rightMulti += 2
						}

						if (bEspArmor && bEspArmorPos == "R") {
							this@sr.color = Color.BLACK
							rect(x0 - (barWidth * rightMulti), y0 + h, barWidth, -h) //Armor outline

							this@sr.color = Color(0F, .3F, 1F, 1F)
							rect(x0 - (barWidth * rightMulti), y0 + h, barWidth, -(h * (armor / 100F))) //Armor
						}
						////Right

						set(ShapeRenderer.ShapeType.Line)
						this@sr.color = Color.WHITE
					}
				}
			} else if (type == EntityType.CEconEntity) {
				if (curSettings["BOX_ESP_DETAILS"].strToBool()) {
					textRenderer.apply {
						val glyph = GlyphLayout()
						this@sr.color = Color.WHITE

						if (sb.isDrawing) {
							sb.end()
						}

						sb.begin()
						////Top
						var yAdd = 0F
						val boxDetailsTextTop = StringBuilder()

						if (bEspName && bEspNamePos == "T") {
							boxDetailsTextTop.append("DEFUSER")
							yAdd += 16F
						}
						glyph.setText(this, boxDetailsTextTop, 0, (boxDetailsTextTop as CharSequence).length, detailTextColor, 1F, Align.center, false, null)
						draw(sb, glyph, x0 + w / 2F, y0 + yAdd)
						////Top

						////Bottom
						val boxDetailsTextBottom = StringBuilder()

						if (bEspName && bEspNamePos == "B") {
							boxDetailsTextBottom.append("DEFUSER")
						}
						glyph.setText(this, boxDetailsTextBottom, 0, (boxDetailsTextBottom as CharSequence).length, detailTextColor, 1F, Align.center, false, null)
						draw(sb, glyph, x0 + w / 2F, y0 + h - 4F)
						////Bottom

						sb.end()
						set(ShapeRenderer.ShapeType.Line)
						this@sr.color = Color.WHITE
					}
				}
			}
		}
		end()
	}

	currentIdx = 0
}
