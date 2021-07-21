package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.curSettings
import rat.poison.game.CSGO
import rat.poison.overlay.App
import rat.poison.scripts.aim.curWepCategory
import rat.poison.scripts.aim.curWepOverride
import rat.poison.scripts.aim.curWepSettings
import rat.poison.scripts.aim.meCurWep
import rat.poison.utils.generalUtil.strToBool

fun drawDebug() = App {
    if (!curSettings["DEBUG"].strToBool()) return@App

    val txtString = StringBuilder()

    txtString.appendLine("Weapon Override: $curWepOverride")

    if (curWepOverride) {
        txtString.appendLine("Current Override: $curWepCategory: ${meCurWep.name}")

        txtString.appendLine("Factor Recoil: ${curWepSettings.tFRecoil}")
        txtString.appendLine("On Shot: ${curWepSettings.tOnShot}")
        txtString.appendLine("Flat Aim: ${curWepSettings.tFlatAim}")
        txtString.appendLine("Path Aim: ${curWepSettings.tPathAim}")
        txtString.appendLine("Aim Bone: ${curWepSettings.tAimBone}")
        txtString.appendLine("Force Bone: ${curWepSettings.tForceBone}")
        txtString.appendLine("Fov: ${curWepSettings.tAimFov}")
        txtString.appendLine("Speed: ${curWepSettings.tAimSpeed}")
        txtString.appendLine("Smooth: ${curWepSettings.tAimSmooth}")
        txtString.appendLine("Perfect Aim: ${curWepSettings.tPerfectAim}")
        txtString.appendLine("Perfect Fov: ${curWepSettings.tPAimFov}")
        txtString.appendLine("Perfect Chance: ${curWepSettings.tPAimChance}")
        txtString.appendLine("Scoped Only: ${curWepSettings.tScopedOnly}")
        txtString.appendLine("Aim After #: ${curWepSettings.tAimAfterShots}")
        txtString.appendLine()
        txtString.appendLine("Trigger: ${curWepSettings.tBoneTrig}")
        txtString.appendLine("Trigger Aimbot: ${curWepSettings.tBTrigAim}")
        txtString.appendLine("Trigger Backtrack: ${curWepSettings.tBTrigBacktrack}")
        txtString.appendLine("Trigger InCross: ${curWepSettings.tBTrigInCross}")
        txtString.appendLine("Trigger InFov: ${curWepSettings.tBTrigInFov}")
        txtString.appendLine("Trigger Fov: ${curWepSettings.tBTrigFov}")
        txtString.appendLine("Trigger Init Delay: ${curWepSettings.tBTrigInitDelay}")
        txtString.appendLine("Trigger Per Shot Delay: ${curWepSettings.tBTrigPerShotDelay}")
        txtString.appendLine()
        txtString.appendLine("Backtrack: ${curWepSettings.tBacktrack}")
        txtString.appendLine("Backtrack MS: ${curWepSettings.tBTMS}")
    } else {
        txtString.appendLine()
        txtString.appendLine("Factor Recoil: " + curSettings["FACTOR_RECOIL"])
        txtString.appendLine("On Shot: " + curSettings["AIM_ONLY_ON_SHOT"])
        txtString.appendLine("Flat Aim: " + curSettings["ENABLE_FLAT_AIM"])
        txtString.appendLine("Path Aim: " + curSettings["ENABLE_PATH_AIM"])
        txtString.appendLine("Aim Bone: " + curSettings["AIM_BONE"])
        txtString.appendLine("Force Bone: " + curSettings["FORCE_AIM_BONE"])
        txtString.appendLine("Fov: " + curSettings["AIM_FOV"])
        txtString.appendLine("Speed: " + curSettings["AIM_SPEED"])
        txtString.appendLine("Smooth: " + curSettings["AIM_SMOOTHNESS"])
        txtString.appendLine("Perfect Aim: " + curSettings["PERFECT_AIM"])
        txtString.appendLine("Perfect Fov: " + curSettings["PERFECT_AIM_FOV"])
        txtString.appendLine("Perfect Chance: " + curSettings["PERFECT_AIM_CHANCE"])
        txtString.appendLine("Scoped Only: " + curSettings["ENABLE_SCOPED_ONLY"])
        txtString.appendLine("Aim After #: " + curSettings["AIM_AFTER_SHOTS"])
        txtString.appendLine()
        txtString.appendLine("Trigger: " + curSettings["${curWepCategory}_TRIGGER"])
        txtString.appendLine("Trigger Aimbot: " + curSettings["${curWepCategory}_TRIGGER_AIMBOT"])
        txtString.appendLine("Trigger Backtrack: " + curSettings["${curWepCategory}_TRIGGER_BACKTRACK"])
        txtString.appendLine("Trigger InCross: " + curSettings["${curWepCategory}_TRIGGER_INCROSS"])
        txtString.appendLine("Trigger InFov: " + curSettings["${curWepCategory}_TRIGGER_INFOV"])
        txtString.appendLine("Trigger Fov: " + curSettings["${curWepCategory}_TRIGGER_FOV"])
        txtString.appendLine("Trigger Init Delay: " + curSettings["${curWepCategory}_TRIGGER_INIT_SHOT_DELAY"])
        txtString.appendLine("Trigger Per Shot Delay: " + curSettings["${curWepCategory}_TRIGGER_PER_SHOT_DELAY"])
        txtString.appendLine()
        txtString.appendLine("Backtrack: " + curSettings["${curWepCategory}_BACKTRACK"])
        txtString.appendLine("Backtrack MS: " + curSettings["${curWepCategory}_BACKTRACK_MS"])
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