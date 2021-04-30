@file:Suppress("DEPRECATION")

package rat.poison.scripts

import org.jire.arrowhead.keyReleased
import rat.poison.curSettings
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.entity.*
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.game.offsets.ClientOffsets.dwForceAttack2
import rat.poison.overlay.App
import rat.poison.overlay.opened
import rat.poison.robot
import rat.poison.scripts.aim.findTarget
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.meCurWepEnt
import rat.poison.scripts.userCmd.meDead
import rat.poison.settings.AIM_KEY
import rat.poison.settings.DANGER_ZONE
import rat.poison.utils.common.Vector
import rat.poison.utils.common.distanceTo
import rat.poison.utils.common.every
import java.awt.event.MouseEvent

private const val SwingDistance = 96f
private const val StabDistance = 64f

private val mePositionVector = Vector()
private val meAbsPositionVector = Vector()
private val targetAbsPositionVector = Vector()
private val velocityVec = Vector()
private val meAng = Vector()
private val targetDirectionVector = Vector()
private val boneList = listOf(-2)
internal fun autoKnife() = every(10, inGameCheck = true) {
    if (curSettings.bool["MENU"] && opened && App.haveTarget && !DANGER_ZONE && !meDead) {
        if (curSettings.bool["ENABLE_AUTO_KNIFE"]) {
            if (meCurWep.knife) {
                val currentAngle = clientState.angle(meAng)
                val position = me.position(mePositionVector)
                val target = findTarget(position, currentAngle, false, 32F, boneList)
                if (target > 0) {
                    if (keyReleased(AIM_KEY)) {
                        val targetPos = target.absPosition(targetAbsPositionVector)
                        val mePos = me.absPosition(meAbsPositionVector)
                        val dst = mePos.distanceTo(targetPos)
                        if (dst <= SwingDistance) {
                            val canStab = dst <= StabDistance
                            if (!isBehindMe(currentAngle, mePos, targetPos)) {
                                val imBehind = canBackStab(targetPos, mePos, target.direction(targetDirectionVector))
                                val attackType: KnifeAttackType = if (canStab) {
                                    val health = target.health()
                                    val hasArmor = target.armor() > 0
                                    val swingDmg =
                                            (if (meCurWepEnt.nextPrimaryAttack() + .4f < me.time()) KnifeAttackType.SWING else KnifeAttackType.SLASH).getDmg(imBehind, hasArmor)
                                    val slashDmg = KnifeAttackType.SLASH.getDmg(imBehind, hasArmor)
                                    val stabDmg = KnifeAttackType.STAB.getDmg(imBehind, hasArmor)

                                    when {
                                        // IF health lower than swing_dmg, do a swing
                                        health <= swingDmg -> KnifeAttackType.SLASH
                                        // IF health lower than stab_dmg, do a stab
                                        health <= stabDmg -> KnifeAttackType.STAB
                                        // IF health more than swing+swing+stab, do a stab
                                        health > (swingDmg + slashDmg + stabDmg) -> KnifeAttackType.STAB
                                        // ELSE swing (initiate swing+swing+stab)
                                        else -> KnifeAttackType.SLASH
                                    }
                                } else {
                                    val velocityVec = me.velocity(velocityVec)
                                    if (imBehind
                                            && velocityVec.len2() > 0
                                            && meAbsPositionVector.distanceTo(targetPos) > StabDistance) {
                                        //wait to get close enough to be able to back stab
                                        KnifeAttackType.NONE
                                    } else {
                                        KnifeAttackType.SLASH
                                    }
                                }
                                attackType.attack()
                            }
                        }
                    }
                }
            }
        }
    }
}

private val directionVec = Vector()

private fun isBehindMe(eyeAngle: Vector, mePosition: Vector, position: Vector): Boolean {
    return (mePosition - position).nor().dot(me.direction(directionVec, eyeAngle)) > 0.475f
}

private fun canBackStab(position: Vector, mePosition: Vector, direction: Vector): Boolean {
    return (position - mePosition).nor().dot(direction) > 0.475f
}

var TRIGGER_FORCE_VALUES = false

private enum class KnifeAttackType(private val frontNoArmorDmg: Float = 0f,
                                   private val frontArmorDmg: Float = 0f,
                                   private val behindNoArmorDmg: Float = 0f,
                                   private val behindArmorDmg: Float = 0f) {
    NONE,
    SWING(40f, 34f, 90f, 76f),
    SLASH(25f, 21f, 90f, 76f),
    STAB(65f, 55f, 180f, 153f);

    fun getDmg(behind: Boolean, hasArmor: Boolean): Float = if (behind) {
        if (hasArmor) behindArmorDmg else behindNoArmorDmg
    } else {
        if (hasArmor) frontArmorDmg else frontNoArmorDmg
    }

    fun attack() {
        when (this) {
            NONE -> return
            STAB -> rightClick()
            //swing or slash
            else -> leftClick()
        }
    }
}

fun leftClick() {
    if (TRIGGER_FORCE_VALUES) {
        clientDLL[dwForceAttack] = 6
    } else {
        robot.mousePress(MouseEvent.BUTTON1_MASK)
        robot.mouseRelease(MouseEvent.BUTTON1_MASK)
    }
}

fun rightClick() {
    if (TRIGGER_FORCE_VALUES) {
        clientDLL[dwForceAttack2] = 6
    } else {
        robot.mousePress(MouseEvent.BUTTON3_MASK)
        robot.mouseRelease(MouseEvent.BUTTON3_MASK)
    }
}