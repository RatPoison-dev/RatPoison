/*
 * Charlatano is a premium CS:GO cheat ran on the JVM.
 * Copyright (C) 2016 Thomas Nappo, Jonathan Beaudoin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.charlatano.scripts

import co.paralleluniverse.strands.Strand
import com.charlatano.*
import com.charlatano.game.CSGO.scaleFormDLL
import com.charlatano.game.angle
import com.charlatano.game.clientState
import com.charlatano.game.entities
import com.charlatano.game.entity.*
import com.charlatano.game.me
import com.charlatano.game.offsets.ScaleFormOffsets
import com.charlatano.utils.*
import com.google.common.util.concurrent.AtomicDouble
import org.jire.arrowhead.keyPressed
import java.lang.Math.*
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

val target = AtomicLong(-1)
val perfect = AtomicBoolean(false)
val bone = AtomicInteger(AIM_BONE)
val targetFOV = AtomicDouble(Double.MAX_VALUE)

private fun reset() {
	target.set(-1L)
	targetFOV.set(Double.MAX_VALUE)
	bone.set(AIM_BONE)
	perfect.set(false)
}

fun fovAim() = every(AIM_DURATION) {
	val aim = keyPressed(FIRE_KEY)
	val forceAim = keyPressed(FORCE_AIM_KEY)
	val pressed = aim or forceAim
	var currentTarget = target.get()
	
	if (!pressed || scaleFormDLL.boolean(ScaleFormOffsets.bCursorEnabled)) {
		reset()
		return@every
	}
	
	val weapon = me.weapon()
	if (!weapon.pistol && !weapon.automatic && !weapon.shotgun) {
		reset()
		return@every
	}
	
	val currentAngle = clientState.angle()
	
	val position = me.position()
	if (currentTarget < 0) {
		currentTarget = findTarget(position, currentAngle, aim)
		if (currentTarget < 0) {
			Strand.sleep(200 + randLong(350))
			return@every
		}
		target.set(currentTarget)
	}
	
	if (!canShoot(currentTarget)) {
		reset()
		Strand.sleep(200 + randLong(350))
		return@every
	}
	
	if (!currentTarget.onGround() || !me.onGround()) return@every
	
	val boneID = bone.get()
	val bonePosition = Vector(
			currentTarget.bone(0xC, boneID),
			currentTarget.bone(0x1C, boneID),
			currentTarget.bone(0x2C, boneID))
	
	val dest = calculateAngle(me, bonePosition)
	if (AIM_ASSIST_MODE) dest.finalize(currentAngle, AIM_ASSIST_STRICTNESS / 100.0)
	
	val distance = position.distanceTo(bonePosition)
	var sensMultiplier = AIM_STRICTNESS
	
	if (distance > AIM_STRICTNESS_BASELINE_DISTANCE) {
		val amountOver = AIM_STRICTNESS_BASELINE_DISTANCE / distance
		sensMultiplier *= (amountOver * AIM_STRICTNESS_BASELINE_MODIFIER)
	}
	
	val aimSpeed = AIM_SPEED_MIN + randInt(AIM_SPEED_MAX - AIM_SPEED_MIN)
	aim(currentAngle, dest, aimSpeed, sensMultiplier = sensMultiplier, perfect = perfect.getAndSet(false))
}

internal fun findTarget(position: Angle, angle: Angle, allowPerfect: Boolean, lockFOV: Int = AIM_FOV): Player {
	var closestDelta = Double.MAX_VALUE
	var closetPlayer: Player = -1L
	
	var closestFOV = Double.MAX_VALUE
	
	entities(EntityType.CCSPlayer) {
		val entity = it.entity
		if (entity <= 0) return@entities
		if (!canShoot(entity)) return@entities
		
		val ePos: Angle = Vector(entity.bone(0xC), entity.bone(0x1C), entity.bone(0x2C))
		val distance = position.distanceTo(ePos)
		
		val dest = calculateAngle(me, ePos)
		
		val pitchDiff = abs(angle.x - dest.x)
		val yawDiff = abs(angle.y - dest.y)
		val delta = abs(sin(toRadians(yawDiff)) * distance)
		val fovDelta = abs((sin(toRadians(pitchDiff)) + sin(toRadians(yawDiff))) * distance)
		
		if (fovDelta <= lockFOV && delta < closestDelta) {
			closestDelta = delta
			closetPlayer = entity
			closestFOV = fovDelta
		}
	}
	
	if (closestDelta == Double.MAX_VALUE || closestDelta < 0 || closetPlayer < 0) return -1
	
	targetFOV.set(closestFOV)
	
	if (PERFECT_AIM && allowPerfect && closestFOV <= PERFECT_AIM_FOV && randInt(100 + 1) <= PERFECT_AIM_CHANCE)
		perfect.set(true)
	
	return closetPlayer
}

private fun canShoot(entity: Entity) = !(me.dead() || entity.dead() || entity.dormant()
		|| !entity.spotted() || entity.team() == me.team())