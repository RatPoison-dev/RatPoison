@file:Suppress("DEPRECATION")

package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils.clamp
import com.badlogic.gdx.utils.Align
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import rat.poison.SETTINGS_DIRECTORY
import rat.poison.curSettings
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.entity.*
import rat.poison.game.forEntities
import rat.poison.game.me
import rat.poison.game.meTeam
import rat.poison.game.netvars.NetVarOffsets.iClip1
import rat.poison.game.netvars.NetVarOffsets.iPrimaryReserveAmmoCount
import rat.poison.game.netvars.NetVarOffsets.m_Collision
import rat.poison.game.netvars.NetVarOffsets.rgflCoordinateFrame
import rat.poison.game.worldToScreen
import rat.poison.overlay.App
import rat.poison.overlay.App.sb
import rat.poison.settings.DANGER_ZONE
import rat.poison.settings.HEAD_BONE
import rat.poison.utils.AssetManager
import rat.poison.utils.common.Vector
import rat.poison.utils.common.every
import rat.poison.utils.common.inGame
import rat.poison.utils.common.threadLocalPointer
import rat.poison.utils.extensions.getFloatArray
import rat.poison.utils.extensions.lower
import kotlin.math.abs
import kotlin.math.sign

data class BoundingBox(var left: Float = -1F, var right: Float = -1F, var top: Float = -1F, var bottom: Float = -1F) {
	fun set(left: Float, right: Float, top: Float, bottom: Float): BoundingBox {
		this.left = left
		this.right = right
		this.top = top
		this.bottom = bottom
		return this
	}
	fun reset(): BoundingBox {
		this.left = -1F
		this.right = -1F
		this.top = -1F
		this.bottom = -1F
		return this
	}
}
private val bbox = ThreadLocal.withInitial { BoundingBox() }

//Just a bruh moment
private var advancedBBox = false
private var drawBox = curSettings.bool["ENABLE_BOX_ESP"]
private var drawBoxDetails = curSettings.bool["BOX_ESP_DETAILS"]
private var bEspName = false; private var bEspNamePos = "BOTTOM"
private var bEspWeapon = false; private var bEspWeaponPos = "BOTTOM"
private var bEspHealth = false; private var bEspHealthPos = "BOTTOM"
private var bEspArmor = false; private var bEspArmorPos = "BOTTOM"
private var bEspAmmo = false; private var bEspAmmoPos = "BOTTOM"
private var bEspHelmet = false; private var bEspHelmetPos = "BOTTOM"
private var bEspKevlar = false; private var bEspKevlarPos = "BOTTOM"
private var bEspScoped = false; private var bEspScopedPos = "BOTTOM"
private var bEspFlashed = false; private var bEspFlashedPos = "BOTTOM"
private var bEspMoney = false; private var bEspMoneyPos = "BOTTOM"
private var showTeam = false; private var showEnemy = false
private var showWeapons = false
private var showDefuseKits = false
private var bEspUseIcons = false
private var weaponsScale = 0F
private var boxDetailsLeftText = StringBuilder()
private var boxDetailsRightText = StringBuilder()
private var boxDetailsTopText = StringBuilder()
private var boxDetailsBottomText = StringBuilder()
private var ammoStringBuilder = StringBuilder()
private var moneyStringBuilder = StringBuilder()
private var topShift = 0F
private var entPos = Vector()
private var leftShift = 2F
private var bottomShift = 0F
private var rightShift = 0F
private var leftShiftY = 0F
private var rightShiftY = 0F
private var barWidth = 0F

private const val entityMemorySize = 71632
private val entMemory = threadLocalPointer(entityMemorySize)
private val forEntsList = arrayOf(EntityType.CCSPlayer, EntityType.CEconEntity)
//p250 & cz75 share same classid, create enum for WeaponItemIndex using m_iItemDefinitionIndex
fun boxEsp() {
	every(1000, true) { //Update settings
		if ((!curSettings.bool["ENABLE_BOX_ESP"] && !curSettings.bool["BOX_ESP_DETAILS"]) || !curSettings.bool["ENABLE_VISUALS"] || !inGame) return@every

		advancedBBox = curSettings.bool["ADVANCED_BOUNDING_BOX"]

		drawBox = curSettings.bool["ENABLE_BOX_ESP"]
		drawBoxDetails = curSettings.bool["BOX_ESP_DETAILS"]

		bEspName = curSettings.bool["BOX_ESP_NAME"]
		bEspNamePos = curSettings["BOX_ESP_NAME_POS"].replace("\"", "")
		bEspWeapon = curSettings.bool["BOX_ESP_WEAPON"]
		bEspWeaponPos = curSettings["BOX_ESP_WEAPON_POS"].replace("\"", "")
		bEspHealth = curSettings.bool["BOX_ESP_HEALTH"]
		bEspHealthPos = curSettings["BOX_ESP_HEALTH_POS"].replace("\"", "")
		bEspArmor = curSettings.bool["BOX_ESP_ARMOR"]
		bEspArmorPos = curSettings["BOX_ESP_ARMOR_POS"].replace("\"", "")
		bEspAmmo = curSettings.bool["BOX_ESP_AMMO"]
		bEspAmmoPos = curSettings["BOX_ESP_AMMO_POS"].replace("\"", "")
		bEspHelmet = curSettings.bool["BOX_ESP_HELMET"]
		bEspHelmetPos = curSettings["BOX_ESP_HELMET_POS"].replace("\"", "")
		bEspKevlar = curSettings.bool["BOX_ESP_KEVLAR"]
		bEspKevlarPos = curSettings["BOX_ESP_KEVLAR_POS"].replace("\"", "")
		bEspScoped = curSettings.bool["BOX_ESP_SCOPED"]
		bEspScopedPos = curSettings["BOX_ESP_SCOPED_POS"].replace("\"", "")
		bEspFlashed = curSettings.bool["BOX_ESP_FLASHED"]
		bEspFlashedPos = curSettings["BOX_ESP_FLASHED_POS"].replace("\"", "")
		bEspMoney = curSettings.bool["BOX_ESP_MONEY"]
		bEspMoneyPos = curSettings["BOX_ESP_MONEY_POS"].replace("\"", "")

		showTeam = curSettings.bool["BOX_SHOW_TEAM"]
		showEnemy = curSettings.bool["BOX_SHOW_ENEMIES"]
		showWeapons = curSettings.bool["BOX_SHOW_WEAPONS"]
		showDefuseKits = curSettings.bool["BOX_SHOW_DEFUSERS"]
		weaponsScale = curSettings.float["BOX_ESP_WEAPON_SCALE"]

		bEspUseIcons = curSettings.bool["BOX_ESP_USE_ICONS"]
	}

	App {
		if ((!curSettings.bool["ENABLE_BOX_ESP"] && !curSettings.bool["BOX_ESP_DETAILS"]) || !curSettings.bool["ENABLE_VISUALS"] || !inGame) return@App

		val entityMemory = entMemory.get()
		boxDetailsLeftText.clear()
		boxDetailsRightText.clear()
		boxDetailsTopText.clear()
		boxDetailsBottomText.clear()
		forEntities(forEntsList, iterateWeapons = true) { //Player & Weapon boxes
			val ent = it.entity
			val isPlayer = it.type == EntityType.CCSPlayer
			val isWeapon = it.type.weapon
			val isDefuseKit = it.type == EntityType.CEconEntity

			boxDetailsLeftText.clear()
			boxDetailsRightText.clear()
			boxDetailsTopText.clear()
			boxDetailsBottomText.clear()

			leftShift = 2F
			bottomShift = 0F
			rightShift = 0F
			topShift = 0F

			if (ent <= 0) return@forEntities

			if (!isPlayer && !isWeapon && !isDefuseKit) return@forEntities

			//Return if not onscreen
			if (!worldToScreen(ent.position(entPos))) return@forEntities

			if (curSettings.bool["BOX_SMOKE_CHECK"] && lineThroughSmoke(ent)) return@forEntities

			var health = 0
			if (isPlayer) {
				health = ent.health()
			}
			val onTeam = !DANGER_ZONE && ent.team() == meTeam

			//Team + Dormant + Dead + Self check
			if (isPlayer && (ent == me || ent.dormant() || ent.dead() || (!showEnemy && !onTeam) || (!showTeam && onTeam))) return@forEntities
			if (isWeapon && !showWeapons) return@forEntities
			if (isDefuseKit && !showDefuseKits) return@forEntities

			val bbox: BoundingBox = if ((isPlayer && advancedBBox) || (isWeapon && showWeapons) || (isDefuseKit && showDefuseKits)) {
				setupAccurateBox(ent)
			} else {
				setupFakeBox(ent)
			}

			if (bbox.left == -1F && bbox.right == -1F) return@forEntities //Invalid

			val boxWidth = bbox.right - bbox.left
			val boxHeight = bbox.bottom - bbox.top
			barWidth = clamp(boxWidth * .025F, 2F, 20F)

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
							shapeRenderer.color.set((255 - 2.55F * health) / 255F, (2.55F * health) / 255F, 0F, 1F)
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
			csgoEXE.read(ent, entityMemory, entityMemorySize)

			//Set filled for bars
			shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
			val armor = entityMemory.armor()
			val hasHelmet = entityMemory.hasHelmet()

			//Draw possible left elements

			if (bEspHealth && bEspHealthPos == "LEFT" && isPlayer) {
				shapeRenderer.color = Color.BLACK
				shapeRenderer.rect(bbox.left - (barWidth * leftShift), bbox.top, barWidth, boxHeight)

				shapeRenderer.color.set(1F - (.01F * health), (.01F * health), 0F, 1F)
				shapeRenderer.rect(bbox.left - (barWidth * leftShift), bbox.bottom, barWidth, -(boxHeight * (health / 100F)))

				leftShift += 2F
			}

			if (bEspArmor && bEspArmorPos == "LEFT" && isPlayer) {
				shapeRenderer.color = Color.BLACK
				shapeRenderer.rect(bbox.left - (barWidth * leftShift), bbox.top, barWidth, boxHeight)

				shapeRenderer.color.set(0F, .3F, 1F, 1F)
				shapeRenderer.rect(bbox.left - (barWidth * leftShift), bbox.bottom, barWidth, -(boxHeight * (armor / 100F)))

				leftShift += 2F
			}

			//Draw possible right elements

			if (bEspHealth && bEspHealthPos == "RIGHT" && isPlayer) {
				shapeRenderer.color = Color.BLACK
				shapeRenderer.rect(bbox.right + (barWidth * rightShift), bbox.top, barWidth, boxHeight)

				shapeRenderer.color.set(1F - (.01F * health), (.01F * health), 0F, 1F)
				shapeRenderer.rect(bbox.right + (barWidth * rightShift), bbox.bottom, barWidth, -(boxHeight * (health / 100F)))

				rightShift += 2
			}

			if (bEspArmor && bEspArmorPos == "RIGHT" && isPlayer) {
				shapeRenderer.color = Color.BLACK
				shapeRenderer.rect(bbox.right + (barWidth * rightShift), bbox.top, barWidth, boxHeight)

				shapeRenderer.color.set(0F, .3F, 1F, 1F)
				shapeRenderer.rect(bbox.right + (barWidth * rightShift), bbox.bottom, barWidth, -(boxHeight * (armor / 100F)))

				rightShift += 2
			}

			leftShiftY = 0F
			rightShiftY = 0F

			if (bEspName && isWeapon) {
				val name = it.type.name.replace("CWeapon", "")

				drawTextureOrText(assetManager, bEspUseIcons, name, name, bEspNamePos, true)
			}

			if (bEspFlashed && isPlayer && entityMemory.flashed()) {
				drawTextureOrText(assetManager, bEspUseIcons, "flashed", "Flashed", bEspFlashedPos, true)
			}

			if (bEspWeapon && isPlayer) {
				val weaponName = ent.weapon().name
				drawTextureOrText(assetManager, bEspUseIcons, weaponName, weaponName, bEspWeaponPos, true)
			}

			if (isPlayer && bEspKevlar && bEspUseIcons && (!hasHelmet && armor > 0 || !bEspHelmet || bEspHelmetPos != bEspKevlarPos)) {
				drawTextureOrText(assetManager, bEspUseIcons, "kevlar", "K\n", bEspKevlarPos)
			}

			if (isPlayer && bEspHelmet && bEspUseIcons && (hasHelmet && armor > 0 || !bEspKevlar || bEspKevlarPos != bEspHelmetPos)) {
				drawTextureOrText(assetManager, bEspUseIcons, "assaultsuit", "K\n", bEspHelmetPos)
			}

			if (bEspScoped && isPlayer && entityMemory.isScoped()) {
				drawTextureOrText(assetManager, bEspUseIcons, "scoped", "Scoped", bEspScopedPos, true)
			}

			if (bEspName && isPlayer) {
				addText(bEspNamePos, ent.name(), true)
			}

			if (bEspMoney && isPlayer) {
				moneyStringBuilder.clear()
				moneyStringBuilder.append("$")
				moneyStringBuilder.append(entityMemory.money())
				addText(bEspMoneyPos, moneyStringBuilder, true)
			}

			if (bEspHelmet && isPlayer && !bEspUseIcons) {
				addText(bEspHelmetPos, if (bEspKevlar && bEspKevlarPos == bEspHelmetPos) { if (hasHelmet) "H" else "" } else { if (hasHelmet) "H\n" else "" })
			}

			if (bEspKevlar && isPlayer && !bEspUseIcons) {
				addText(bEspKevlarPos, if (armor > 0) "K\n" else "")
			}

			if (bEspAmmo) {
				val curAmmo = csgoEXE.int(ent + iClip1)
				val maxAmmo = csgoEXE.int(ent + iPrimaryReserveAmmoCount)
				if (maxAmmo > 0 && curAmmo > 0) {
					ammoStringBuilder.clear()
					ammoStringBuilder.append("[")
					ammoStringBuilder.append(curAmmo)
					ammoStringBuilder.append("/")
					ammoStringBuilder.append(maxAmmo)
					ammoStringBuilder.appendLine("]")
					addText(bEspAmmoPos, ammoStringBuilder)
				}
			}

			shapeRenderer.end()

			if (!sb.isDrawing) {
				sb.begin()
			}

			//draw details first
			val detailTextColor = curSettings.colorGDX["BOX_DETAILS_TEXT_COLOR"]
			textRenderer.color = detailTextColor
			textRenderer.draw(sb, boxDetailsLeftText, bbox.left - (barWidth * leftShift), bbox.top, 1F, Align.right, false)
			textRenderer.draw(sb, boxDetailsRightText, bbox.right + (barWidth * rightShift), bbox.top, 1F, Align.left, false)
			textRenderer.draw(sb, boxDetailsTopText, (bbox.left + bbox.right) / 2F, bbox.top + topShift, 1F, Align.center, false)
			textRenderer.draw(sb, boxDetailsBottomText, (bbox.left + bbox.right) / 2F, bbox.bottom - bottomShift, 1F, Align.center, false)
			sb.end()
		}
	}
}
private val DEFAULT_STRING_BUILDER = StringBuilder()
fun getStrBuilder(position: String): StringBuilder {
	return when (position)  {
		"LEFT" -> boxDetailsLeftText
		"RIGHT" -> boxDetailsRightText
		"TOP" -> boxDetailsTopText
		"BOTTOM" -> boxDetailsBottomText
		else -> {
			DEFAULT_STRING_BUILDER.clear()
			DEFAULT_STRING_BUILDER
		}
	}
}
fun addText(position: String, str: String, appendLine: Boolean = false) {
	val renderer = getStrBuilder(position)
	if (appendLine) renderer.appendLine(str) else renderer.append(str)
	when (position) {
		"TOP" -> topShift += 18F
	}
}

fun addText(position: String, str: StringBuilder, appendLine: Boolean = false) {
	val renderer = getStrBuilder(position)
	if (appendLine) renderer.appendLine(str) else renderer.append(str)
	when (position) {
		"TOP" -> topShift += 18F
	}
}

fun drawTextureOrText(assetManager: AssetManager, useIcons: Boolean, textureName: String, text: String, position: String, appendLine: Boolean = false) {
	val bbox = bbox.get()
	if (!useIcons) { addText(position, text, appendLine); return }
	sb.begin()
	val texture = getWeaponTexture(assetManager, textureName)
	val realHeight = texture.height / weaponsScale
	val realWidth = texture.width / weaponsScale
	when (position) { //fix later
		"LEFT" -> {
			sb.draw(
					texture,
					(bbox.left - (barWidth * leftShift) - realWidth),
					(bbox.top - realHeight - leftShiftY),
					realWidth,
					realHeight
			)
			leftShiftY += realHeight
		}
		"RIGHT" -> {
			sb.draw(
					texture,
					(bbox.right + (barWidth * rightShift)) + (weaponsScale / 2),
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
					bbox.bottom - realHeight - bottomShift,
					realWidth,
					realHeight
			)
			bottomShift += realHeight
		}
	}
	sb.end()
}

//Create a fake accurate box using headpos

fun setupFakeBox(vTop: Vector, vBottom: Vector, bbox: BoundingBox = BoundingBox()): BoundingBox {
	val vMiddleY = (vTop.y + vBottom.y)/2F
	//val vMiddle = Vector((vTop.x + vBottom.x)/2F, (vTop.y + vBottom.y)/2F, (vTop.z + vBottom.z)/2F)
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

	bbox.top = (vMiddleY - boxH / 2.0 + sH).toFloat()
	bbox.bottom = (vMiddleY + boxH / 2.0 + sH).toFloat()
	return bbox
}

private val vBottom = Vector()
private val vTop = Vector()
private val vHead = Vector()
private val vFeet = Vector()
private const val boneMemorySize = 3984
private val boneMemory = threadLocalPointer(boneMemorySize)
fun setupFakeBox(ent: Entity): BoundingBox {
	val bbox = bbox.get()
	bbox.reset()
	val mem = boneMemory.get()
	csgoEXE.read(ent.boneMatrix(), mem, boneMemorySize)
	val boneMemory = mem ?: return bbox

	vHead.set(boneMemory.getFloat(((0x30L * HEAD_BONE) + 0xC)),
			boneMemory.getFloat(((0x30L * HEAD_BONE) + 0x1C)),
			boneMemory.getFloat(((0x30L * HEAD_BONE) + 0x2C)) + 9)

	vFeet.set(vHead.x, vHead.y, vHead.z - 84)

	if (worldToScreen(vHead, vTop) && worldToScreen(vFeet, vBottom)) {
		setupFakeBox(vTop, vBottom, bbox)
	}

	return bbox
}


private const val collisionMemSize = 56
private val collisionMem = threadLocalPointer(collisionMemSize)
private val screenPointsTransformedArray = Array(8) { Vector() }
private val frameMatrix = ThreadLocal.withInitial { Array(4) { FloatArray(4) } }
private val vecOut = Vector()
private val vecMins = Vector()
private val vecMaxs = Vector()
private val pointsArray = Array(8) { Vector() }
private val floatArray = ThreadLocal.withInitial { FloatArray(16) }
private const val frameMatrixMemorySize = 4*4*4
private val frameMatrixMemory = threadLocalPointer(frameMatrixMemorySize)
//Create a real accurate box using vecMins & vecMaxs
fun setupAccurateBox(ent: Entity): BoundingBox {
	val bbox = bbox.get()
	bbox.reset()
	//Get frameMatrix
	val frameMatrix = frameMatrix.get()
	val frameMatrixMemory = frameMatrixMemory.get()
	val floatArray = floatArray.get()
	csgoEXE.read(ent + rgflCoordinateFrame - 0x30, frameMatrixMemory, frameMatrixMemorySize)
	if (frameMatrixMemory != null) {
		if (frameMatrixMemory.getFloatArray(0, 16, floatArray).all(Float::isFinite)) {
			var offset = 0
			for (row in 0..3) for (col in 0..3) {
				val value = frameMatrixMemory.getFloat(offset.toLong())
				frameMatrix[row][col] = value
				offset += 4
			}
		}
	}

	val collisionMem = collisionMem.get()
	csgoEXE.read(ent + m_Collision, collisionMem)

	//Set min/max
	vecMins.set(collisionMem.getFloat(8), collisionMem.getFloat(12), collisionMem.getFloat(16))
	vecMaxs.set(collisionMem.getFloat(20), collisionMem.getFloat(24), collisionMem.getFloat(28))

	//Set OBB to loop
	pointsArray[0].set(vecMins.x, vecMins.y, vecMins.z)
	pointsArray[1].set(vecMins.x, vecMaxs.y, vecMins.z)
	pointsArray[2].set(vecMaxs.x, vecMaxs.y, vecMins.z)
	pointsArray[3].set(vecMaxs.x, vecMins.y, vecMins.z)
	pointsArray[4].set(vecMaxs.x, vecMaxs.y, vecMaxs.z)
	pointsArray[5].set(vecMins.x, vecMaxs.y, vecMaxs.z)
	pointsArray[6].set(vecMins.x, vecMins.y, vecMaxs.z)
	pointsArray[7].set(vecMaxs.x, vecMins.y, vecMaxs.z)


	pointsArray.forEachIndexed { index, i ->
		worldToScreen(transformVector(i, frameMatrix), vecOut)
		screenPointsTransformedArray[index].set(vecOut.x, vecOut.y, vecOut.z)
	}

	var left = screenPointsTransformedArray[0].x
	var top = screenPointsTransformedArray[0].y
	var right = screenPointsTransformedArray[0].x
	var bottom = screenPointsTransformedArray[0].y

	for (i in screenPointsTransformedArray) {
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

	return bbox.set(left, right, top, bottom)
}

fun transformVector(vec: Vector, array: Array<FloatArray>): Vector {
	vec.set(
			(vec.dot(array[0][0], array[0][1], array[0][2]) + array[0][3]),
			(vec.dot(array[1][0], array[1][1], array[1][2]) + array[1][3]),
			(vec.dot(array[2][0], array[2][1], array[2][2]) + array[2][3])
	)
	return vec
}

private val nameHashToAssetName: Object2ObjectMap<String, String> = Object2ObjectOpenHashMap()
fun getWeaponTexture(assetManager: AssetManager, name: String): Texture {
	val assetName =
			if (nameHashToAssetName.containsKey(name)) {
				nameHashToAssetName[name]
			} else "$SETTINGS_DIRECTORY/Assets/Images/${name.lower()}.png".apply {
				nameHashToAssetName[name] = this
			}
	return when (assetManager.contains(assetName)) {
		true -> assetManager.get(assetName)
		false -> assetManager.get("$SETTINGS_DIRECTORY/Assets/Images/knife.png")
	}
}