package rat.poison.scripts.esp

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.utils.Align
import com.sun.jna.Memory
import org.jire.arrowhead.keyPressed
import rat.poison.App
import rat.poison.checkFlags
import rat.poison.curLocalization
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.entity.EntityType.Companion.ccsPlayer
import rat.poison.game.forEntities
import rat.poison.game.hooks.defuseKitEntities
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.iClip1
import rat.poison.game.netvars.NetVarOffsets.iPrimaryReserveAmmoCount
import rat.poison.game.worldToScreen
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.HEAD_BONE
import rat.poison.settings.MENUTOG
import rat.poison.utils.Vector
import rat.poison.utils.notInGame
import rat.poison.utils.varUtil.strToBool
import rat.poison.utils.varUtil.strToColor
import rat.poison.utils.varUtil.strToColorGDX
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.sign

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
					   var curAmmo: Int = 0,
					   var maxAmmo: Int = 0,
					   var helmet: Boolean = false,
					   var kevlar: Boolean = false,
					   var scoped: Boolean = false,
					   var flashed: Boolean = false,
					   var name: String = "",
					   var type: EntityType = EntityType.NULL)

private var currentIdx = 0

fun boxEsp() = App {
	if ((!curSettings["ENABLE_BOX_ESP"].strToBool() && !checkFlags("ENABLE_BOX_ESP") && !curSettings["BOX_ESP_DETAILS"].strToBool()) || !curSettings["ENABLE_ESP"].strToBool() || !checkFlags("ENABLE_ESP") || MENUTOG || notInGame) return@App

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
			val vMid = Vector((vTop.x + vBot.x)/2f, (vTop.y + vBot.y)/2f, (vTop.z + vBot.z)/2f)
			val entHealth = entity.health()
			val tCol = when (curSettings["BOX_SHOW_HEALTH"].strToBool()) {
				true -> rat.poison.game.Color((255 - 2.55 * entHealth).toInt(), (2.55 * entHealth).toInt(), 0, 1.0)
				false -> curSettings["BOX_TEAM_COLOR"].strToColor()
			}
			val eCol = when (curSettings["BOX_SHOW_HEALTH"].strToBool()) {
				true -> rat.poison.game.Color((255 - 2.55 * entHealth).toInt(), (2.55 * entHealth).toInt(), 0, 1.0)
				false -> curSettings["BOX_ENEMY_COLOR"].strToColor()
			}
			val c = if (meTeam == entTeam) Color(tCol.red/255F, tCol.green/255F, tCol.blue/255F, 1F) else Color(eCol.red/255F, eCol.green/255F, eCol.blue/255F, 1F)

			var boxH = vBot.y - vTop.y
			val sW = abs(ceil((boxH / 5.0) * 2.0) / 2.0)
			val sH = 2

			val midX = abs(abs(vTop.x) - abs(vBot.x))
			if (abs(boxH) < sW + midX) {
				boxH = (sW + midX) * sign(boxH)
			}

			val box0x: Float
			val box1x: Float
			if (vBot.x > vTop.x) {
				box0x = (vBot.x + (sW * sign(vBot.x))).toFloat()
				box1x = (vTop.x - (sW * sign(vTop.x))).toFloat()
			} else {
				box0x = (vTop.x + (sW * sign(vTop.x))).toFloat()
				box1x = (vBot.x - (sW * sign(vBot.x))).toFloat()
			}

			val box0y = (vMid.y - boxH/2 + sH).toFloat()
			val box1y = (vMid.y + boxH/2 - sH).toFloat()

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

				weapon = curLocalization[entity.weapon().name]
				name = entity.name()

				val wepEnt = entity.weaponEntity()
				curAmmo = csgoEXE.int(wepEnt + iClip1)
				maxAmmo = csgoEXE.int(wepEnt + iPrimaryReserveAmmoCount)
				helmet = entityMemory.hasHelmet()
				kevlar = armor > 0
				scoped = entityMemory.isScoped()
				flashed = entityMemory.flashed()

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

	val bEspName = curSettings["BOX_ESP_NAME"].strToBool()
	val bEspNamePos = curSettings["BOX_ESP_NAME_POS"].replace("\"", "")
	val bEspWeapon = curSettings["BOX_ESP_WEAPON"].strToBool()
	val bEspWeaponPos = curSettings["BOX_ESP_WEAPON_POS"].replace("\"", "")

	val bEspHealth = curSettings["BOX_ESP_HEALTH"].strToBool()
	val bEspHealthPos = curSettings["BOX_ESP_HEALTH_POS"].replace("\"", "")
	val bEspArmor = curSettings["BOX_ESP_ARMOR"].strToBool()
	val bEspArmorPos = curSettings["BOX_ESP_ARMOR_POS"].replace("\"", "")

	val bEspAmmo = curSettings["BOX_ESP_AMMO"].strToBool()
	val bEspAmmoPos = curSettings["BOX_ESP_AMMO_POS"].replace("\"", "")

	val bEspHelmet = curSettings["BOX_ESP_HELMET"].strToBool()
	val bEspHelmetPos = curSettings["BOX_ESP_HELMET_POS"].replace("\"", "")

	val bEspKevlar = curSettings["BOX_ESP_KEVLAR"].strToBool()
	val bEspKevlarPos = curSettings["BOX_ESP_KEVLAR_POS"].replace("\"", "")

	val bEspScoped = curSettings["BOX_ESP_SCOPED"].strToBool()
	val bEspScopedPos = curSettings["BOX_ESP_SCOPED_POS"].replace("\"", "")

	val bEspFlashed = curSettings["BOX_ESP_FLASHED"].strToBool()
	val bEspFlashedPos = curSettings["BOX_ESP_FLASHED_POS"].replace("\"", "")

	shapeRenderer.apply sr@{
		begin()

		for (i in 0 until currentIdx) boxes[i].apply {
			this@sr.color = color
			val w = x1 - x0
			val h = y1 - y0

			if (curSettings["ENABLE_BOX_ESP"].strToBool() && checkFlags("ENABLE_BOX_ESP")) {
				rect(x0, y0, w, h)
			}

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
						boxDetailsTextTop.append("")

						if (bEspName && bEspNamePos == "T") {
							boxDetailsTextTop.append("$name\n") //\n
							yAdd += 18F
						}
						if (bEspWeapon && bEspWeaponPos == "T") {
							boxDetailsTextTop.append(weapon)
							yAdd += 18F
						}
						if (bEspAmmo && bEspAmmoPos == "T") {
							if (curAmmo != -1 && maxAmmo > 0) {
								if (bEspWeapon) {
									if (bEspWeaponPos == "T") {
										boxDetailsTextTop.append(" ")
									} else {
										yAdd += 18F
									}
								} else {
									boxDetailsTextTop.append("\n")
									yAdd += 18F
								}
								boxDetailsTextTop.append("[$curAmmo/$maxAmmo]")
							}
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
						if (bEspAmmo && bEspAmmoPos == "B") {
							if (curAmmo != -1 && maxAmmo > 0) {
								if (bEspWeapon) {
									if (bEspWeaponPos == "B") {
										boxDetailsTextBottom.append(" ")
									}
								} else {
									boxDetailsTextBottom.append("\n")
								}
								boxDetailsTextBottom.append("[$curAmmo/$maxAmmo]")
							}
						}

						if (boxDetailsTextBottom.isNotBlank() && boxDetailsTextBottom.isNotEmpty()) {
							glyph.setText(textRenderer, boxDetailsTextBottom, 0, (boxDetailsTextBottom as CharSequence).length, detailTextColor, 1F, Align.center, false, null)
							draw(sb, glyph, x0 + w / 2F, y0 + h - 4F)
						}
						////Bottom
						sb.end()

						set(ShapeRenderer.ShapeType.Filled)

						val barWidth = clamp(w * .025F, -10F, -2F) //Why is this negative?

						////Left
						val boxDetailsTextLeft = StringBuilder()
						boxDetailsTextLeft.append("")
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

							leftMulti += 2
						}

						if (bEspHelmet && bEspHelmetPos == "L") {
							boxDetailsTextLeft.append(if (helmet) "H" else "")
							if (!bEspKevlar || bEspKevlarPos == "R") {
								boxDetailsTextLeft.append("\n")
							}
						}

						if (bEspKevlar && bEspKevlarPos == "L") {
							boxDetailsTextLeft.append(if (kevlar) "K\n" else "")
						}

						if (bEspScoped && bEspScopedPos == "L") {
							boxDetailsTextLeft.append(if (scoped) "Scoped\n" else "") //Leave blank later, testing purposes rn
						}

						if (bEspFlashed && bEspFlashedPos == "L") {
							boxDetailsTextLeft.append(if (flashed) "Flashed" else "")
						}

						sb.begin()
						if (boxDetailsTextLeft.isNotBlank() && boxDetailsTextLeft.isNotEmpty()) {
							glyph.setText(textRenderer, boxDetailsTextLeft, 0, (boxDetailsTextLeft as CharSequence).length, detailTextColor, 1F, Align.right, false, null)
							draw(sb, glyph, x0 + w + (barWidth * leftMulti), y0)
						}
						sb.end()
						////Left

						////Right
						val boxDetailsTextRight = StringBuilder()
						boxDetailsTextRight.append("")
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

							rightMulti += 2
						}

						if (bEspHelmet && bEspHelmetPos == "R") {
							boxDetailsTextRight.append(if (helmet) "H" else "")
							if (!bEspKevlar || bEspKevlarPos == "L") {
								boxDetailsTextRight.append("\n")
							}
						}

						if (bEspKevlar && bEspKevlarPos == "R") {
							boxDetailsTextRight.append(if (kevlar) "K\n" else "")
						}

						if (bEspScoped && bEspScopedPos == "R") {
							boxDetailsTextRight.append(if (scoped) "Scoped\n" else "") //Leave blank later, testing purposes rn
						}

						if (bEspFlashed && bEspFlashedPos == "R") {
							boxDetailsTextRight.append(if (flashed) "Flashed" else "")
						}

						sb.begin()
						if (boxDetailsTextRight.isNotBlank() && boxDetailsTextRight.isNotEmpty()) {
							glyph.setText(textRenderer, boxDetailsTextRight, 0, (boxDetailsTextRight as CharSequence).length, detailTextColor, 1F, Align.left, false, null)
							draw(sb, glyph, x0 - (barWidth * rightMulti), y0)
						}
						sb.end()
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
							boxDetailsTextTop.append(curLocalization["DEFUSER"])
							yAdd += 16F
						}
						glyph.setText(this, boxDetailsTextTop, 0, (boxDetailsTextTop as CharSequence).length, detailTextColor, 1F, Align.center, false, null)
						draw(sb, glyph, x0 + w / 2F, y0 + yAdd)
						////Top

						////Bottom
						val boxDetailsTextBottom = StringBuilder()

						if (bEspName && bEspNamePos == "B") {
							boxDetailsTextBottom.append(curLocalization["DEFUSER"])
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
