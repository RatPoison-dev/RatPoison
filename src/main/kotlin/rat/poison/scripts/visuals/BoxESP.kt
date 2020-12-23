@file:Suppress("DEPRECATION")

package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.utils.Align
import it.unimi.dsi.fastutil.longs.LongArrayList
import org.jire.kna.int
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.netvars.NetVarOffsets.iClip1
import rat.poison.game.netvars.NetVarOffsets.iPrimaryReserveAmmoCount
import rat.poison.game.netvars.NetVarOffsets.m_Collision
import rat.poison.game.netvars.NetVarOffsets.rgflCoordinateFrame
import rat.poison.game.w2s
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.HEAD_BONE
import rat.poison.toLocale
import rat.poison.utils.*
import rat.poison.utils.generalUtil.strToBool
import kotlin.math.abs
import kotlin.math.sign

data class BoundingBox(var left: Float = -1F, var right: Float = -1F, var top: Float = -1F, var bottom: Float = -1F)

data class DrawableTexture(var texture: Texture, var position: String = "")

//Just a bruh moment
private var advancedBBox = false
private var drawBox = curSettings["ENABLE_BOX_ESP"].strToBool()
private var drawBoxDetails = curSettings["BOX_ESP_DETAILS"].strToBool()
private var bEspName = false;
private var bEspNamePos = "BOTTOM"
private var bEspWeapon = false;
private var bEspWeaponPos = "BOTTOM"
private var bEspHealth = false;
private var bEspHealthPos = "BOTTOM"
private var bEspArmor = false;
private var bEspArmorPos = "BOTTOM"
private var bEspAmmo = false;
private var bEspAmmoPos = "BOTTOM"
private var bEspHelmet = false;
private var bEspHelmetPos = "BOTTOM"
private var bEspKevlar = false;
private var bEspKevlarPos = "BOTTOM"
private var bEspScoped = false;
private var bEspScopedPos = "BOTTOM"
private var bEspFlashed = false;
private var bEspFlashedPos = "BOTTOM"
private var bEspMoney = false;
private var bEspMoneyPos = "BOTTOM"
private var showTeam = false;
private var showEnemy = false
private var showWeapons = false
private var showDefuseKits = false
private var bEspUseIcons = false
private var weaponsScale = 0F
private val boxDetailsLeftText = StringBuilder("")
private val boxDetailsRightText = StringBuilder("")
private val boxDetailsTopText = StringBuilder("")
private val boxDetailsBottomText = StringBuilder("")
private var topShift = 0F
private var textureBuilder = mutableListOf<DrawableTexture>()

private val entityMemory = threadLocalMemory(45948)

//p250 & cz75 share same classid, create enum for WeaponItemIndex using m_iItemDefinitionIndex
fun boxEsp() {
	every(1000, true) { //Update settings
		if ((!curSettings["ENABLE_BOX_ESP"].strToBool() && !curSettings["BOX_ESP_DETAILS"].strToBool()) || !curSettings["ENABLE_ESP"].strToBool() || !inGame) return@every
		
		advancedBBox = curSettings["ADVANCED_BOUNDING_BOX"].strToBool()
		
		drawBox = curSettings["ENABLE_BOX_ESP"].strToBool()
		drawBoxDetails = curSettings["BOX_ESP_DETAILS"].strToBool()
		
		bEspName = curSettings["BOX_ESP_NAME"].strToBool()
		bEspNamePos = curSettings["BOX_ESP_NAME_POS"].replace("\"", "")
		bEspWeapon = curSettings["BOX_ESP_WEAPON"].strToBool()
		bEspWeaponPos = curSettings["BOX_ESP_WEAPON_POS"].replace("\"", "")
		bEspHealth = curSettings["BOX_ESP_HEALTH"].strToBool()
		bEspHealthPos = curSettings["BOX_ESP_HEALTH_POS"].replace("\"", "")
		bEspArmor = curSettings["BOX_ESP_ARMOR"].strToBool()
		bEspArmorPos = curSettings["BOX_ESP_ARMOR_POS"].replace("\"", "")
		bEspAmmo = curSettings["BOX_ESP_AMMO"].strToBool()
		bEspAmmoPos = curSettings["BOX_ESP_AMMO_POS"].replace("\"", "")
		bEspHelmet = curSettings["BOX_ESP_HELMET"].strToBool()
		bEspHelmetPos = curSettings["BOX_ESP_HELMET_POS"].replace("\"", "")
		bEspKevlar = curSettings["BOX_ESP_KEVLAR"].strToBool()
		bEspKevlarPos = curSettings["BOX_ESP_KEVLAR_POS"].replace("\"", "")
		bEspScoped = curSettings["BOX_ESP_SCOPED"].strToBool()
		bEspScopedPos = curSettings["BOX_ESP_SCOPED_POS"].replace("\"", "")
		bEspFlashed = curSettings["BOX_ESP_FLASHED"].strToBool()
		bEspFlashedPos = curSettings["BOX_ESP_FLASHED_POS"].replace("\"", "")
		bEspMoney = curSettings["BOX_ESP_MONEY"].strToBool()
		bEspMoneyPos = curSettings["BOX_ESP_MONEY_POS"].replace("\"", "")
		
		showTeam = curSettings["BOX_SHOW_TEAM"].strToBool()
		showEnemy = curSettings["BOX_SHOW_ENEMIES"].strToBool()
		showWeapons = curSettings["BOX_SHOW_WEAPONS"].strToBool()
		showDefuseKits = curSettings["BOX_SHOW_DEFUSERS"].strToBool()
		weaponsScale = curSettings["BOX_ESP_WEAPON_SCALE"].toFloat()
		
		bEspUseIcons = curSettings["BOX_ESP_USE_ICONS"].strToBool()
	}
	
	App {
		if ((!curSettings.bool["ENABLE_BOX_ESP"] && !curSettings.bool["BOX_ESP_DETAILS"]) || !curSettings.bool["ENABLE_ESP"] || !inGame) return@App
		
		forEntities { //Player & Weapon boxes
			val ent = it.entity
			val isPlayer = it.type == EntityType.CCSPlayer
			val isWeapon = it.type.weapon
			val isDefuseKit = it.type == EntityType.CEconEntity
			
			boxDetailsLeftText.clear()
			boxDetailsRightText.clear()
			boxDetailsTopText.clear()
			boxDetailsBottomText.clear()
			
			var leftShift = 2F
			var bottomShift = 0F
			var rightShift = 0F
			topShift = 0F
			textureBuilder.clear()
			
			if (ent <= 0) return@forEntities
			
			if (!isPlayer && !isWeapon && !isDefuseKit) return@forEntities
			
			//Return if not onscreen
			if (!worldToScreen(ent.position()).w2s()) return@forEntities
			
			if (curSettings.bool["BOX_SMOKE_CHECK"] && lineThroughSmoke(ent)) return@forEntities
			
			var health = 0
			if (isPlayer) {
				health = ent.health()
			}
			val onTeam = !DANGER_ZONE && ent.team() == me.team()
			
			//Team + Dormant + Dead + Self check
			if (isPlayer && (ent == me || ent.dormant() || ent.dead() || (!showEnemy && !onTeam) || (!showTeam && onTeam))) return@forEntities
			if (isWeapon && !showWeapons) return@forEntities
			if (isDefuseKit && !showDefuseKits) return@forEntities
			
			val bbox: BoundingBox =
				if ((isPlayer && advancedBBox) || (isWeapon && showWeapons) || (isDefuseKit && showDefuseKits)) {
					setupAccurateBox(ent)
				} else {
					setupFakeBox(ent)
				}
			
			if (bbox.left == -1F && bbox.right == -1F) return@forEntities //Invalid
			
			val boxWidth = bbox.right - bbox.left
			val boxHeight = bbox.bottom - bbox.top
			val barWidth = clamp(boxWidth * .025F, 2F, 20F)
			
			if (shapeRenderer.isDrawing) {
				shapeRenderer.end()
			}
			
			shapeRenderer.begin()
			shapeRenderer.set(ShapeRenderer.ShapeType.Line)
			
			//Draw the entity bounding box
			if (drawBox) {
				if (isPlayer) {
					when {
						curSettings.bool["BOX_SHOW_HEALTH"] -> {
							shapeRenderer.setColor((255 - 2.55F * health) / 255F, (2.55F * health) / 255F, 0F, 1F)
						}
						
						onTeam -> {
							shapeRenderer.color = curSettings.colorGDX["BOX_TEAM_COLOR"]
						}
						
						else -> {
							shapeRenderer.color = curSettings.colorGDX["BOX_ENEMY_COLOR"]
						}
					}
				} else {
					shapeRenderer.color = curSettings.colorGDX["BOX_WEAPON_COLOR"]
				}
				
				shapeRenderer.rect(bbox.left, bbox.top, boxWidth, boxHeight)
			}
			
			if (!drawBoxDetails) return@forEntities
			
			//Setup entity values
			val entityMemory = entityMemory.get()
			if (!csgoEXE.read(ent, entityMemory)) return@forEntities
			
			//Set filled for bars
			shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
			
			//Draw possible left elements
			
			if (bEspHealth && bEspHealthPos == "LEFT" && isPlayer) {
				shapeRenderer.color = Color.BLACK
				shapeRenderer.rect(bbox.left - (barWidth * leftShift), bbox.top, barWidth, boxHeight)
				
				shapeRenderer.color = Color(1F - (.01F * health), (.01F * health), 0F, 1F)
				shapeRenderer.rect(
					bbox.left - (barWidth * leftShift),
					bbox.bottom,
					barWidth,
					-(boxHeight * (health / 100F))
				)
				
				leftShift += 2F
			}
			
			if (bEspArmor && bEspArmorPos == "LEFT" && isPlayer) {
				shapeRenderer.color = Color.BLACK
				shapeRenderer.rect(bbox.left - (barWidth * leftShift), bbox.top, barWidth, boxHeight)
				
				shapeRenderer.color = Color(0F, .3F, 1F, 1F)
				shapeRenderer.rect(
					bbox.left - (barWidth * leftShift),
					bbox.bottom,
					barWidth,
					-(boxHeight * (entityMemory.armor() / 100F))
				)
				
				leftShift += 2F
			}
			
			if (bEspName && isPlayer) {
				addText(bEspNamePos, "${ent.name()}\n")
			}
			
			if (bEspMoney && isPlayer) {
				addText(bEspMoneyPos, "$${entityMemory.money()}\n")
			}
			
			if (bEspHelmet && isPlayer && !bEspUseIcons) {
				addText(
					bEspHelmetPos, if (bEspKevlar && bEspKevlarPos == bEspHelmetPos) {
						if (entityMemory.hasHelmet()) "H" else ""
					} else {
						if (entityMemory.hasHelmet()) "H\n" else ""
					}
				)
			}
			
			if (bEspKevlar && isPlayer && !bEspUseIcons) {
				addText(bEspKevlarPos, if (entityMemory.armor() > 0) "K\n" else "")
			}
			
			if (bEspAmmo) {
				val curAmmo = csgoEXE.int(ent + iClip1)
				val maxAmmo = csgoEXE.int(ent + iPrimaryReserveAmmoCount)
				if (curAmmo != -1 && maxAmmo > 0) {
					addText(bEspAmmoPos, "[$curAmmo/$maxAmmo]\n")
				}
			}
			
			if (bEspName && isWeapon) {
				addTextureOrText(
					assetManager,
					bEspUseIcons,
					it.type.name.replace("CWeapon", ""),
					"${it.type.name.replace("CWeapon", "").toLocale()}\n",
					bEspNamePos
				)
			}
			
			if (bEspFlashed && isPlayer && entityMemory.flashed()) {
				addTextureOrText(assetManager, bEspUseIcons, "flashed", "Flashed\n", bEspFlashedPos)
			}
			
			if (bEspWeapon && isPlayer) {
				addTextureOrText(
					assetManager,
					bEspUseIcons,
					ent.weapon().name,
					"${ent.weapon().name.toLocale()}\n",
					bEspWeaponPos
				)
			}
			
			if (bEspKevlar && isPlayer && bEspUseIcons && !entityMemory.hasHelmet() && entityMemory.armor() > 0) {
				addTextureOrText(assetManager, bEspUseIcons, "kevlar", "K\n", bEspKevlarPos)
			}
			
			if (bEspKevlar && isPlayer && bEspUseIcons && bEspHelmet && entityMemory.hasHelmet() && entityMemory.armor() > 0) {
				addTextureOrText(assetManager, bEspUseIcons, "assaultsuit", "K\n", bEspKevlarPos)
			}
			
			if (bEspScoped && isPlayer && entityMemory.isScoped()) {
				addTextureOrText(assetManager, bEspUseIcons, "scoped", "Scoped\n", bEspScopedPos)
			}
			
			
			//Draw possible right elements
			
			if (bEspHealth && bEspHealthPos == "RIGHT" && isPlayer) {
				shapeRenderer.color = Color.BLACK
				shapeRenderer.rect(bbox.right + (barWidth * rightShift), bbox.top, barWidth, boxHeight)
				
				shapeRenderer.color = Color(1F - (.01F * health), (.01F * health), 0F, 1F)
				shapeRenderer.rect(
					bbox.right + (barWidth * rightShift),
					bbox.bottom,
					barWidth,
					-(boxHeight * (health / 100F))
				)
				
				rightShift += 2
			}
			
			if (bEspArmor && bEspArmorPos == "RIGHT" && isPlayer) {
				shapeRenderer.color = Color.BLACK
				shapeRenderer.rect(bbox.right + (barWidth * rightShift), bbox.top, barWidth, boxHeight)
				
				shapeRenderer.color = Color(0F, .3F, 1F, 1F)
				shapeRenderer.rect(
					bbox.right + (barWidth * rightShift),
					bbox.bottom,
					barWidth,
					-(boxHeight * (entityMemory.armor() / 100F))
				)
				
				rightShift += 2
			}
			
			shapeRenderer.end()
			
			if (!sb.isDrawing) {
				sb.begin()
			}
			
			//draw details first
			val detailTextColor: Color = curSettings.colorGDX["BOX_DETAILS_TEXT_COLOR"]
			textRenderer.color = detailTextColor
			textRenderer.draw(
				sb,
				boxDetailsLeftText,
				bbox.left - (barWidth * leftShift),
				bbox.top,
				1F,
				Align.right,
				false
			)
			textRenderer.draw(
				sb,
				boxDetailsRightText,
				bbox.right + (barWidth * rightShift),
				bbox.top,
				1F,
				Align.left,
				false
			)
			textRenderer.draw(
				sb,
				boxDetailsTopText,
				(bbox.left + bbox.right) / 2F,
				bbox.top + topShift,
				1F,
				Align.center,
				false
			)
			textRenderer.draw(
				sb,
				boxDetailsBottomText,
				(bbox.left + bbox.right) / 2F,
				bbox.bottom - bottomShift,
				1F,
				Align.center,
				false
			)
			
			var leftShiftY = 0F
			var rightShiftY = 0F
			textureBuilder.forEach { dr_texture ->
				val texture = dr_texture.texture
				val position = dr_texture.position
				val realHeight = texture.height / weaponsScale
				val realWidth = texture.width / weaponsScale
				val (height, width) = getRealTextParams(textRenderer, getStrBuilder(position), layout)
				when (position) {
					"LEFT" -> {
						sb.draw(
							texture,
							(bbox.left - (barWidth * leftShift) - realWidth - width),
							(bbox.top - realHeight - leftShiftY),
							realWidth,
							realHeight
						)
						leftShiftY += realHeight
					}
					"RIGHT" -> {
						sb.draw(
							texture,
							(bbox.right + (barWidth * rightShift)) + (weaponsScale / 2) + width,
							bbox.top - realHeight - rightShiftY,
							realWidth,
							realHeight
						)
						rightShiftY += realHeight
					}
					"TOP" -> {
						sb.draw(
							texture,
							((bbox.left + bbox.right) / 2) - (texture.width / (weaponsScale * 2)),
							bbox.top + topShift,
							realWidth,
							realHeight
						)
						topShift += realHeight
					}
					"BOTTOM" -> {
						sb.draw(
							texture,
							((bbox.left + bbox.right) / 2) - (texture.width / (weaponsScale * 2)),
							bbox.bottom - height - bottomShift,
							realWidth,
							realHeight
						)
						bottomShift += realHeight
					}
				}
			}
			
			sb.end()
		}
	}
}

fun getStrBuilder(position: String): StringBuilder {
	return when (position) {
		"LEFT" -> boxDetailsLeftText
		"RIGHT" -> boxDetailsRightText
		"TOP" -> boxDetailsTopText
		"BOTTOM" -> boxDetailsBottomText
		else -> StringBuilder()
	}
}

fun addText(position: String, str: String) {
	val renderer = getStrBuilder(position)
	renderer.append(str)
	when (position) {
		"TOP" -> topShift += 18F
	}
}

fun addTextureOrText(
	assetManager: AssetManager,
	useIcons: Boolean,
	textureName: String,
	text: String,
	position: String
) {
	if (!useIcons) {
		addText(position, text); return
	}
	val texture = getWeaponTexture(assetManager, textureName)
	textureBuilder.add(DrawableTexture(texture, position))
}

fun getRealTextParams(font: BitmapFont, builder: StringBuilder, layout: GlyphLayout): Pair<Float, Float> {
	layout.setText(font, builder)
	if (builder.isEmpty()) {
		return Pair(0F, 0F)
	}
	return Pair(layout.height, layout.width)
}

private val boneMemory = threadLocalMemory(3984)

//Create a fake accurate box using headpos
fun setupFakeBox(ent: Entity): BoundingBox {
	val bbox = BoundingBox()
	
	val boneMemory = boneMemory.get()
	if (!csgoEXE.read(ent.boneMatrix(), boneMemory)) throw IllegalStateException("Couldn't read bone matrix")
	
	val headPos = Vector(
		boneMemory.getFloat(((0x30L * HEAD_BONE) + 0xC)),
		boneMemory.getFloat(((0x30L * HEAD_BONE) + 0x1C)),
		boneMemory.getFloat(((0x30L * HEAD_BONE) + 0x2C))
	)
	
	val vHead = Vector(headPos.x, headPos.y, headPos.z + 9)
	val vFeet = Vector(vHead.x, vHead.y, vHead.z - 75)
	
	val vTop = worldToScreen(vHead)
	val vBottom = worldToScreen(vFeet)
	
	if (vTop.w2s() && vBottom.w2s()) {
		val vMiddle = Vector((vTop.x + vBottom.x) / 2F, (vTop.y + vBottom.y) / 2F/*, (vTop.z + vBottom.z)/2F*/)
		var boxH = vBottom.y - vTop.y
		val sW = abs(((boxH / 5.0) * 2.0) / 2.0)
		val sH = 2.0
		
		val midX = abs(abs(vTop.x) - abs(vBottom.x))
		if (abs(boxH) < sW + midX) {
			boxH = ((sW + midX) * sign(boxH)).toFloat()
		}
		
		if (vBottom.x > vTop.x) {
			bbox.left = (vBottom.x + sW).toFloat()
			bbox.right = (vTop.x - sW).toFloat()
		} else {
			bbox.left = (vTop.x + sW).toFloat()
			bbox.right = (vBottom.x - sW).toFloat()
		}
		
		bbox.top = (vMiddle.y - boxH / 2.0 + sH).toFloat()
		bbox.bottom = (vMiddle.y + boxH / 2.0 + sH).toFloat()
	}
	
	return bbox
}

private val collisionMem = threadLocalMemory(56) //Incorrect
private val frameMatrix = ThreadLocal.withInitial { Array(4) { FloatArray(4) } }
private val bufferFloatArray = ThreadLocal.withInitial { FloatArray(16) }

//Create a real accurate box using vecMins & vecMaxs
fun setupAccurateBox(ent: Entity): BoundingBox {
	//Get frameMatrix
	val frameMatrix = frameMatrix.get()
	
	val buffer = csgoEXE.read(ent + rgflCoordinateFrame - 0x30, 4 * 4 * 4)
	if (buffer != null) {
		val bufferFloatArray = bufferFloatArray.get()
		buffer.read(0, bufferFloatArray, 0, 16)
		if (bufferFloatArray.all(Float::isFinite)) {
			var offset = 0
			for (row in 0..3) for (col in 0..3) {
				val value = buffer.getFloat(offset.toLong())
				frameMatrix[row][col] = value
				offset += 4
			}
		}
	}
	
	val collisionMem = collisionMem.get()
	
	csgoEXE.read(ent + m_Collision, collisionMem)
	
	//Set min/max
	val vecMins = Vector(collisionMem.getFloat(8), collisionMem.getFloat(12), collisionMem.getFloat(16))
	val vecMaxs = Vector(collisionMem.getFloat(20), collisionMem.getFloat(24), collisionMem.getFloat(28))
	
	//Set OBB to loop
	val pointsArray = longArrayOf(
		Vector(vecMins.x, vecMins.y, vecMins.z).value,
		Vector(vecMins.x, vecMaxs.y, vecMins.z).value,
		Vector(vecMaxs.x, vecMaxs.y, vecMins.z).value,
		Vector(vecMaxs.x, vecMins.y, vecMins.z).value,
		Vector(vecMaxs.x, vecMaxs.y, vecMaxs.z).value,
		Vector(vecMins.x, vecMaxs.y, vecMaxs.z).value,
		Vector(vecMins.x, vecMins.y, vecMaxs.z).value,
		Vector(vecMaxs.x, vecMins.y, vecMaxs.z).value
	)
	
	val screenPointsTransformedArray = LongArrayList()
	
	for (i in pointsArray) {
		val vecOut = worldToScreen(transformVector(Vector(i), frameMatrix))
		screenPointsTransformedArray.add(vecOut.value)
	}
	
	val first = Vector(screenPointsTransformedArray.getLong(0))
	var left = first.x
	var top = first.y
	var right = first.x
	var bottom = first.y
	
	val it = screenPointsTransformedArray.listIterator()
	while (it.hasNext()) {
		val i = Vector(it.nextLong())
		if (left > i.x) {
			left = i.x
		}
		
		if (top < i.y) {
			top = i.y
		}
		
		if (right < i.x) {
			right = i.x
		}
		
		if (bottom > i.y) {
			bottom = i.y
		}
	}
	
	return BoundingBox(left, right, top, bottom)
}

fun transformVector(vec: Vector, array: Array<FloatArray>): Vector = Vector(
	(vec.dot(array[0][0], array[0][1], array[0][2]) + array[0][3]),
	(vec.dot(array[1][0], array[1][1], array[1][2]) + array[1][3]),
	(vec.dot(array[2][0], array[2][1], array[2][2]) + array[2][3])
)

fun getWeaponTexture(assetManager: AssetManager, name: String): Texture {
	val assetName = "$SETTINGS_DIRECTORY/Assets/Images/${name.toLowerCase()}.png"
	return when (assetManager.contains(assetName)) {
		true -> assetManager.get(assetName)
		false -> assetManager.get("$SETTINGS_DIRECTORY/Assets/Images/knife.png")
	}
}