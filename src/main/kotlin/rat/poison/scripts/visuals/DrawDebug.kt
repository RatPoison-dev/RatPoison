package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.overlay.App
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.aim.curWepCategory
import rat.poison.scripts.aim.curWepOverride
import rat.poison.scripts.aim.curWepSettings
import rat.poison.utils.generalUtil.strToBool

fun drawDebug() = App {
    if (!curSettings["DEBUG"].strToBool()) return@App

    val txtString = StringBuilder()

    txtString.appendln("Weapon Override: $curWepOverride")

    if (curWepOverride) {
        txtString.appendln("Current Override: $curWepCategory: ${meCurWep.name}")

        txtString.appendln("Factor Recoil: ${curWepSettings.factorRecoil}")
        txtString.appendln("On Shot: ${curWepSettings.onShot}")
        txtString.appendln("Flat Aim: ${curWepSettings.writeAngles}")
        txtString.appendln("Path Aim: ${curWepSettings.mouseMovements}")
        txtString.appendln("Aim Bone: ${curWepSettings.aimBone}")
        txtString.appendln("Force Bone: ${curWepSettings.aimForceBone}")
        txtString.appendln("Fov: ${curWepSettings.aimFOV}")
        txtString.appendln("Speed: ${curWepSettings.aimSpeed}")
        txtString.appendln("Smooth: ${curWepSettings.aimSmoothness}")
        txtString.appendln("Perfect Aim: ${curWepSettings.enablePerfectAim}")
        txtString.appendln("Perfect Fov: ${curWepSettings.perfectAimFov}")
        txtString.appendln("Perfect Chance: ${curWepSettings.perfectAimChance}")
        txtString.appendln("Scoped Only: ${curWepSettings.aimScopedOnly}")
        txtString.appendln("Aim After #: ${curWepSettings.aimAfterShoots}")
        txtString.appendln()
        txtString.appendln("Trigger: ${curWepSettings.enableTriggerBot}")
        txtString.appendln("Trigger Aimbot: ${curWepSettings.triggerAim}")
        txtString.appendln("Trigger Backtrack: ${curWepSettings.triggerShootBacktrack}")
        txtString.appendln("Trigger InCross: ${curWepSettings.triggerIsInCross}")
        txtString.appendln("Trigger InFov: ${curWepSettings.triggerIsInFOV}")
        txtString.appendln("Trigger Fov: ${curWepSettings.triggerFOV}")
        txtString.appendln("Trigger Init Delay: ${curWepSettings.triggerInitDelay}")
        txtString.appendln("Trigger Per Shot Delay: ${curWepSettings.triggerDelayBetweenShoots}")
        txtString.appendln()
        txtString.appendln("Backtrack: ${curWepSettings.enableBacktrack}")
        txtString.appendln("Backtrack MS: ${curWepSettings.backtrackMS}")
    } else {
        txtString.appendln()
        txtString.appendln("Factor Recoil: " + curSettings["FACTOR_RECOIL"])
        txtString.appendln("On Shot: " + curSettings["AIM_ONLY_ON_SHOT"])
        txtString.appendln("Flat Aim: " + curSettings["ENABLE_FLAT_AIM"])
        txtString.appendln("Path Aim: " + curSettings["ENABLE_PATH_AIM"])
        txtString.appendln("Aim Bone: " + curSettings["AIM_BONE"])
        txtString.appendln("Force Bone: " + curSettings["FORCE_AIM_BONE"])
        txtString.appendln("Fov: " + curSettings["AIM_FOV"])
        txtString.appendln("Speed: " + curSettings["AIM_SPEED"])
        txtString.appendln("Smooth: " + curSettings["AIM_SMOOTHNESS"])
        txtString.appendln("Perfect Aim: " + curSettings["PERFECT_AIM"])
        txtString.appendln("Perfect Fov: " + curSettings["PERFECT_AIM_FOV"])
        txtString.appendln("Perfect Chance: " + curSettings["PERFECT_AIM_CHANCE"])
        txtString.appendln("Scoped Only: " + curSettings["ENABLE_SCOPED_ONLY"])
        txtString.appendln("Aim After #: " + curSettings["AIM_AFTER_SHOTS"])
        txtString.appendln()
        txtString.appendln("Trigger: " + curSettings["${curWepCategory}_TRIGGER"])
        txtString.appendln("Trigger Aimbot: " + curSettings["${curWepCategory}_TRIGGER_AIMBOT"])
        txtString.appendln("Trigger Backtrack: " + curSettings["${curWepCategory}_TRIGGER_BACKTRACK"])
        txtString.appendln("Trigger InCross: " + curSettings["${curWepCategory}_TRIGGER_INCROSS"])
        txtString.appendln("Trigger InFov: " + curSettings["${curWepCategory}_TRIGGER_INFOV"])
        txtString.appendln("Trigger Fov: " + curSettings["${curWepCategory}_TRIGGER_FOV"])
        txtString.appendln("Trigger Init Delay: " + curSettings["${curWepCategory}_TRIGGER_INIT_SHOT_DELAY"])
        txtString.appendln("Trigger Per Shot Delay: " + curSettings["${curWepCategory}_TRIGGER_PER_SHOT_DELAY"])
        txtString.appendln()
        txtString.appendln("Backtrack: " + curSettings["${curWepCategory}_BACKTRACK"])
        txtString.appendln("Backtrack MS: " + curSettings["${curWepCategory}_BACKTRACK_MS"])
    }

    if (shapeRenderer.isDrawing) {
        shapeRenderer.end()
    }

    shapeRenderer.begin()
    shapeRenderer.color = Color.BLACK
    shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
    shapeRenderer.rect(1f, CSGO.gameHeight/4f+8f, 225f, 512f)
    shapeRenderer.end()

    if (sb.isDrawing) {
        sb.end()
    }

    sb.begin()

    textRenderer.setColor(Color.YELLOW)
    textRenderer.draw(sb, txtString, 8f, CSGO.gameHeight/4f + 512)

    sb.end()
}