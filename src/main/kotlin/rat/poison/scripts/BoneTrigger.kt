package rat.poison.scripts

import com.badlogic.gdx.math.Vector3
import org.jire.arrowhead.keyPressed
import org.jire.arrowhead.keyReleased
import rat.poison.App.haveTarget
import rat.poison.game.CSGO.clientDLL
import rat.poison.game.angle
import rat.poison.game.clientState
import rat.poison.game.me
import rat.poison.game.offsets.ClientOffsets.dwForceAttack
import rat.poison.scripts.aim.findTarget
import rat.poison.settings.*
import rat.poison.utils.*
import rat.poison.curSettings
import rat.poison.game.entity.*
import rat.poison.game.entity.position
import rat.poison.game.offsets.ClientOffsets.dwForceAttack2
import rat.poison.opened
import rat.poison.scripts.aim.boneTrig
import rat.poison.strToBool
import rat.poison.ui.bTrigTab
import rat.poison.ui.mainTabbedPane
import java.awt.Robot
import java.awt.event.MouseEvent

private const val SwingDistance = 96f
private const val StabDistance = 64f

private val onBoneTriggerTarget = every(4) {
    if (curSettings["MENU"]!!.strToBool() && opened && haveTarget) {
        if (DANGER_ZONE) {
            mainTabbedPane.disableTab(bTrigTab, true)
        } else {
            mainTabbedPane.disableTab(bTrigTab, false)
        }
    }

    if (curSettings["ENABLE_BONE_TRIGGER"]!!.strToBool()) {
        val currentAngle = clientState.angle()
        val position = me.position()
        val target = findTarget(position, currentAngle, false, curSettings["BONE_TRIGGER_FOV"].toString().toInt(), -2)
        if (target >= 0) {
            if ((keyReleased(curSettings["FIRE_KEY"].toString().toInt()) &&
                            curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool() &&
                            keyPressed(curSettings["BONE_TRIGGER_KEY"].toString().toInt()))
                    || (keyReleased(FIRE_KEY) && !curSettings["BONE_TRIGGER_ENABLE_KEY"]!!.strToBool())) {
                boneTrig = curSettings["AIM_ON_BONE_TRIGGER"]!!.strToBool()
                if (me.weapon().knife) {
                    val targetPos = target.absPosition()
                    val mePos = me.absPosition()
                    val dst = mePos.distanceTo(targetPos)
                    if (dst <= SwingDistance) {
                        val canStab = dst <= StabDistance
                        if (!isBehindMe(targetPos)) {
                            val imBehind = canBackStab(targetPos, target.direction())
                            val attackType: KnifeAttackType = if (canStab) {
                                val health = target.health()
                                val hasArmor = target.armor() > 0
                                val swingDmg =
                                        (if (me.weaponEntity().nextPrimaryAttack() + .4f < me.time()) KnifeAttackType.SWING else KnifeAttackType.SLASH).getDmg(imBehind, hasArmor)
                                val slashDmg = KnifeAttackType.SLASH.getDmg(imBehind, hasArmor)
                                val stabDmg = KnifeAttackType.STAB.getDmg(imBehind, hasArmor)

                                println("$health, ${target.armor()}, $swingDmg, $slashDmg, $stabDmg")

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
                                if (imBehind
                                        && Vector3.len2(me.velocity().x.toFloat(), me.velocity().y.toFloat(), me.velocity().z.toFloat()) > 0
                                        && me.absPosition().distanceTo(targetPos) > StabDistance) {
                                    //wait to get close enough to be able to back stab
                                    KnifeAttackType.NONE
                                } else {
                                    KnifeAttackType.SLASH
                                }
                            }
                            println("$attackType")
                            attackType.attack()
                        }
                    }
                } else {
                    boneTrigger()
                }
            }
        } else {
            boneTrig = false
        }
    } else {
        boneTrig = false
    }
}

private val delta = Vector3()

private fun isBehindMe(position: Vector): Boolean {
    delta.set(me.absPosition().x.toFloat(), me.absPosition().y.toFloat(), me.absPosition().z.toFloat())
            .sub(position.x.toFloat(), position.y.toFloat(), position.z.toFloat())
    delta.nor()
    return delta.dot(me.direction().x.toFloat(), me.direction().y.toFloat(), me.direction().z.toFloat()) > 0.475f
}

private fun canBackStab(position: Vector, direction: Vector): Boolean {
    delta.set(position.x.toFloat(), position.y.toFloat(), position.z.toFloat())
            .sub(me.absPosition().x.toFloat(), me.absPosition().y.toFloat(), me.absPosition().z.toFloat())
    delta.nor()
    return delta.dot(direction.x.toFloat(), direction.y.toFloat(), direction.z.toFloat()) > 0.475f
}

var BONE_TRIGGER_FORCE_VALUES = false

private val robot = Robot().apply { this.autoDelay = 16 }

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
            else -> boneTrigger()
        }
    }
}

fun boneTrigger() {
//    println("left click")
    if (BONE_TRIGGER_FORCE_VALUES) {
        clientDLL[dwForceAttack] = 6
    } else {
        robot.mousePress(MouseEvent.BUTTON1_MASK)
        robot.mouseRelease(MouseEvent.BUTTON1_MASK)
    }
}

fun rightClick() {
//    println("right click")
    if (BONE_TRIGGER_FORCE_VALUES) {
        clientDLL[dwForceAttack2] = 6
    } else {
        robot.mousePress(MouseEvent.BUTTON3_MASK)
        robot.mouseRelease(MouseEvent.BUTTON3_MASK)
    }
}
