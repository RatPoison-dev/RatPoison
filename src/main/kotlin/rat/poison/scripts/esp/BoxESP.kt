package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
import com.sun.jna.Memory
import rat.poison.App
import rat.poison.curSettings
import rat.poison.game.*
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.hooks.defuseKitEntities
import rat.poison.settings.*
import rat.poison.strToBool
import rat.poison.strToColor
import rat.poison.utils.Vector
import rat.poison.utils.notInGame
import kotlin.math.ceil

private val vHead = Vector()
private val vFeet = Vector()

private val vTop = Vector(0.0, 0.0, 0.0)
private val vBot = Vector(0.0, 0.0, 0.0)

private val boxes = Array(128) { Box() }

private var currentIdx = 0

internal fun boxEsp() = App {
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
			Memory(45932)
		}

		csgoEXE.read(entity.boneMatrix(), boneMemory)

		val xOff = boneMemory.getFloat(((0x30L * HEAD_BONE) + 0xC)).toDouble()
		val yOff = boneMemory.getFloat(((0x30L * HEAD_BONE) + 0x1C)).toDouble()
		val zOff = boneMemory.getFloat(((0x30L * HEAD_BONE) + 0x2C)).toDouble()

		vHead.set(xOff, yOff, zOff + 9)
		vFeet.set(vHead.x, vHead.y, vHead.z - 75)

		if (worldToScreen(vHead, vTop) && worldToScreen(vFeet, vBot)) {
			val boxH = vBot.y - vTop.y
			val boxW = boxH / 5F

			val tCol = curSettings["BOX_TEAM_COLOR"].strToColor()
			val eCol = curSettings["BOX_ENEMY_COLOR"].strToColor()
			val c = if (meTeam == entTeam) Color(tCol.red/255F, tCol.green/255F, tCol.blue/255F, 1F) else Color(eCol.red/255F, eCol.green/255F, eCol.blue/255F, 1F)

			val sx = (vTop.x - boxW).toInt()
			val sy = vTop.y.toInt()

			boxes[currentIdx].apply {
				x = sx
				y = sy
				w = ceil(boxW * 2).toInt()
				h = boxH.toInt()
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
				val boxH = (vBot.y - vTop.y).toInt()
				val boxW = boxH / 2F
				val sx = (vTop.x - boxW).toInt()
				val sy = vTop.y.toInt()
				val dCol = curSettings["BOX_DEFUSER_COLOR"].strToColor()
				val c = Color(dCol.red / 255F, dCol.green / 255F, dCol.blue / 255F, 1F)

				boxes[currentIdx].apply {
					x = sx
					y = sy
					w = ceil(boxW * 2.0).toInt()
					h = boxH
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
			rect(x.toFloat(), y.toFloat(), w.toFloat(), h.toFloat())

			if (type == EntityType.CCSPlayer) {
				if (curSettings["BOX_ESP_DETAILS"].strToBool()) {
					textRenderer.apply {
						val glyph = GlyphLayout()
						this@sr.color = Color.WHITE


						sb.begin()
						////Top
						var yAdd = 0F
						val boxDetailsTextTop = StringBuilder()

						if (bEspName && bEspNamePos == "T") {
							boxDetailsTextTop.append("$name\n")
							yAdd += 16F
						}
						if (bEspWeapon && bEspWeaponPos == "T") {
							boxDetailsTextTop.append(weapon)
							yAdd += 16F
						}
						glyph.setText(textRenderer, boxDetailsTextTop, 0, (boxDetailsTextTop as CharSequence).length, Color.WHITE, 1F, Align.center, false, null)
						textRenderer.draw(sb, glyph, x + w / 2F, y + yAdd)
						////Top

						////Bottom
						val boxDetailsTextBottom = StringBuilder()

						if (bEspName && bEspNamePos == "B") {
							boxDetailsTextBottom.append("$name\n")
						}
						if (bEspWeapon && bEspWeaponPos == "B") {
							boxDetailsTextBottom.append(weapon)
						}
						glyph.setText(textRenderer, boxDetailsTextBottom, 0, (boxDetailsTextBottom as CharSequence).length, Color.WHITE, 1F, Align.center, false, null)
						textRenderer.draw(sb, glyph, x + w / 2F, y + h - 4F)
						////Bottom

						sb.end()

						set(ShapeRenderer.ShapeType.Filled)

						val barWidth = w * .05F
						////Left
						var leftMulti = 1

						if (bEspHealth && bEspHealthPos == "L") {
							this@sr.color = Color.BLACK
							rect(x + w + (barWidth * leftMulti), y + h.toFloat(), barWidth, -h.toFloat()) //Health outline

							this@sr.color = Color(1F - (.01F * health), (.01F * health), 0F, 1F)
							rect(x + w + (barWidth * leftMulti), y + h.toFloat(), barWidth, -(h * (health / 100F))) //Health

							leftMulti += 2
						}

						if (bEspArmor && bEspArmorPos == "L") {
							this@sr.color = Color.BLACK
							rect(x + w + (barWidth * leftMulti), y + h.toFloat(), barWidth, -h.toFloat()) //Armor outline

							this@sr.color = Color(0F, .3F, 1F, 1F)
							rect(x + w + (barWidth * leftMulti), y + h.toFloat(), barWidth, -(h * (armor / 100F))) //Armor
						}
						////Left

						////Right
						var rightMulti = 2

						if (bEspHealth && bEspHealthPos == "R") {
							this@sr.color = Color.BLACK
							rect(x - (barWidth * rightMulti), y + h.toFloat(), barWidth, -h.toFloat()) //Health outline

							this@sr.color = Color(1F - (.01F * health), (.01F * health), 0F, 1F)
							rect(x - (barWidth * rightMulti), y + h.toFloat(), barWidth, -(h * (health / 100F))) //Health

							rightMulti += 2
						}

						if (bEspArmor && bEspArmorPos == "R") {
							this@sr.color = Color.BLACK
							rect(x - (barWidth * rightMulti), y + h.toFloat(), barWidth, -h.toFloat()) //Armor outline

							this@sr.color = Color(0F, .3F, 1F, 1F)
							rect(x - (barWidth * rightMulti), y + h.toFloat(), barWidth, -(h * (armor / 100F))) //Armor
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

						sb.begin()
						////Top
						var yAdd = 0F
						val boxDetailsTextTop = StringBuilder()

						if (bEspName && bEspNamePos == "T") {
							boxDetailsTextTop.append("DEFUSER")
							yAdd += 16F
						}
						glyph.setText(textRenderer, boxDetailsTextTop, 0, (boxDetailsTextTop as CharSequence).length, Color.WHITE, 1F, Align.center, false, null)
						textRenderer.draw(sb, glyph, x + w / 2F, y + yAdd)
						////Top

						////Bottom
						val boxDetailsTextBottom = StringBuilder()

						if (bEspName && bEspNamePos == "B") {
							boxDetailsTextBottom.append("DEFUSER")
						}
						glyph.setText(textRenderer, boxDetailsTextBottom, 0, (boxDetailsTextBottom as CharSequence).length, Color.WHITE, 1F, Align.center, false, null)
						textRenderer.draw(sb, glyph, x + w / 2F, y + h - 4F)
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

private data class Box(var x: Int = -1, var y: Int = -1,
					   var w: Int = -1, var h: Int = -1,
					   var color: Color = Color.WHITE,
					   var health: Float = 100F,
					   var armor: Float = 100F,
					   var weapon: String = "",
					   var name: String = "",
					   var type: EntityType = EntityType.NULL)