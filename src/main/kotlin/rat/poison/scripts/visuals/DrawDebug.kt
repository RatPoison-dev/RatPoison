package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.Align
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

        txtString.appendln("Factor Recoil: ${curWepSettings.tFRecoil}")
        txtString.appendln("On Shot: ${curWepSettings.tOnShot}")
        txtString.appendln("Flat Aim: ${curWepSettings.tFlatAim}")
        txtString.appendln("Path Aim: ${curWepSettings.tPathAim}")
        txtString.appendln("Aim Bone: ${curWepSettings.tAimBone}")
        txtString.appendln("Force Bone: ${curWepSettings.tForceBone}")
        txtString.appendln("Fov: ${curWepSettings.tAimFov}")
        txtString.appendln("Speed: ${curWepSettings.tAimSpeed}")
        txtString.appendln("Smooth: ${curWepSettings.tAimSmooth}")
        txtString.appendln("Perfect Aim: ${curWepSettings.tPerfectAim}")
        txtString.appendln("Perfect Fov: ${curWepSettings.tPAimFov}")
        txtString.appendln("Perfect Chance: ${curWepSettings.tPAimChance}")
        txtString.appendln("Scoped Only: ${curWepSettings.tScopedOnly}")
        txtString.appendln("Aim After #: ${curWepSettings.tAimAfterShots}")
        txtString.appendln()
        txtString.appendln("Trigger: ${curWepSettings.tBoneTrig}")
        txtString.appendln("Trigger Aimbot: ${curWepSettings.tBTrigAim}")
        txtString.appendln("Trigger Backtrack: ${curWepSettings.tBTrigBacktrack}")
        txtString.appendln("Trigger InCross: ${curWepSettings.tBTrigInCross}")
        txtString.appendln("Trigger InFov: ${curWepSettings.tBTrigInFov}")
        txtString.appendln("Trigger Fov: ${curWepSettings.tBTrigFov}")
        txtString.appendln("Trigger Init Delay: ${curWepSettings.tBTrigInitDelay}")
        txtString.appendln("Trigger Per Shot Delay: ${curWepSettings.tBTrigPerShotDelay}")
        txtString.appendln()
        txtString.appendln("Backtrack: ${curWepSettings.tBacktrack}")
        txtString.appendln("Backtrack MS: ${curWepSettings.tBTMS}")
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