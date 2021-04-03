package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.game.CSGO
import rat.poison.overlay.App
import rat.poison.scripts.aim.curWepCategory
import rat.poison.scripts.aim.curWepOverride
import rat.poison.scripts.aim.curWepSettings
import rat.poison.scripts.aim.meCurWep

fun drawDebug() = App {
    if (!dbg) return@App

    var txtString = ""

    txtString += "Weapon Override: $curWepOverride\n"

    if (curWepOverride) {
        txtString += "Current Override: $curWepCategory: ${meCurWep.name}\n"

        txtString += "Factor Recoil: ${curWepSettings.tFRecoil}\n"
        txtString += "On Shot: ${curWepSettings.tOnShot}\n"
        txtString += "Flat Aim: ${curWepSettings.tFlatAim}\n"
        txtString += "Path Aim: ${curWepSettings.tPathAim}\n"
        txtString += "Aim Bone: ${curWepSettings.tAimBone}\n"
        txtString += "Force Bone: ${curWepSettings.tForceBone}\n"
        txtString += "Fov: ${curWepSettings.tAimFov}\n"
        txtString += "Speed: ${curWepSettings.tAimSpeed}\n"
        txtString += "Smooth: ${curWepSettings.tAimSmooth}\n"
        txtString += "Perfect Aim: ${curWepSettings.tPerfectAim}\n"
        txtString += "Perfect Fov: ${curWepSettings.tPAimFov}\n"
        txtString += "Perfect Chance: ${curWepSettings.tPAimChance}\n"
        txtString += "Scoped Only: ${curWepSettings.tScopedOnly}\n"
        txtString += "Aim After #: ${curWepSettings.tAimAfterShots}\n"
        txtString += "\n"
        txtString += "Trigger: ${curWepSettings.tBoneTrig}\n"
        txtString += "Trigger Aimbot: ${curWepSettings.tBTrigAim}\n"
        txtString += "Trigger Backtrack: ${curWepSettings.tBTrigBacktrack}\n"
        txtString += "Trigger InCross: ${curWepSettings.tBTrigInCross}\n"
        txtString += "Trigger InFov: ${curWepSettings.tBTrigInFov}\n"
        txtString += "Trigger Fov: ${curWepSettings.tBTrigFov}\n"
        txtString += "Trigger Init Delay: ${curWepSettings.tBTrigInitDelay}\n"
        txtString += "Trigger Per Shot Delay: ${curWepSettings.tBTrigPerShotDelay}\n"
        txtString += "\n"
        txtString += "Backtrack: ${curWepSettings.tBacktrack}\n"
        txtString += "Backtrack MS: ${curWepSettings.tBTMS}\n"
    } else {
        txtString += "\n"
        txtString += "Factor Recoil: " + curSettings["FACTOR_RECOIL"] + "\n"
        txtString += "On Shot: " + curSettings["AIM_ONLY_ON_SHOT"] + "\n"
        txtString += "Flat Aim: " + curSettings["ENABLE_FLAT_AIM"] + "\n"
        txtString += "Path Aim: " + curSettings["ENABLE_PATH_AIM"] + "\n"
        txtString += "Aim Bone: " + curSettings["AIM_BONE"] + "\n"
        txtString += "Force Bone: " + curSettings["FORCE_AIM_BONE"] + "\n"
        txtString += "Fov: " + curSettings["AIM_FOV"] + "\n"
        txtString += "Speed: " + curSettings["AIM_SPEED"] + "\n"
        txtString += "Smooth: " + curSettings["AIM_SMOOTHNESS"] + "\n"
        txtString += "Perfect Aim: " + curSettings["PERFECT_AIM"] + "\n"
        txtString += "Perfect Fov: " + curSettings["PERFECT_AIM_FOV"] + "\n"
        txtString += "Perfect Chance: " + curSettings["PERFECT_AIM_CHANCE"] + "\n"
        txtString += "Scoped Only: " + curSettings["ENABLE_SCOPED_ONLY"] + "\n"
        txtString += "Aim After #: " + curSettings["AIM_AFTER_SHOTS"] + "\n"
        txtString += "\n"
        txtString += "Trigger: " + curSettings["${curWepCategory}_TRIGGER"] + "\n"
        txtString += "Trigger Aimbot: " + curSettings["${curWepCategory}_TRIGGER_AIMBOT"] + "\n"
        txtString += "Trigger Backtrack: " + curSettings["${curWepCategory}_TRIGGER_BACKTRACK"] + "\n"
        txtString += "Trigger InCross: " + curSettings["${curWepCategory}_TRIGGER_INCROSS"] + "\n"
        txtString += "Trigger InFov: " + curSettings["${curWepCategory}_TRIGGER_INFOV"] + "\n"
        txtString += "Trigger Fov: " + curSettings["${curWepCategory}_TRIGGER_FOV"] + "\n"
        txtString += "Trigger Init Delay: " + curSettings["${curWepCategory}_TRIGGER_INIT_SHOT_DELAY"] + "\n"
        txtString += "Trigger Per Shot Delay: " + curSettings["${curWepCategory}_TRIGGER_PER_SHOT_DELAY"] + "\n"
        txtString += "\n"
        txtString += "Backtrack: " + curSettings["${curWepCategory}_BACKTRACK"] + "\n"
        txtString += "Backtrack MS: " + curSettings["${curWepCategory}_BACKTRACK_MS"] + "\n"
    }

    if (shapeRenderer.isDrawing) {
        shapeRenderer.end()
    }

    shapeRenderer.begin()
    shapeRenderer.color = Color.BLACK
    shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
    shapeRenderer.rect(1f, CSGO.gameHeight/4f - 48f, 225f, 564f)
    shapeRenderer.set(ShapeRenderer.ShapeType.Line)
    shapeRenderer.end()

    if (sb.isDrawing) {
        sb.end()
    }

    sb.begin()

    textRenderer.color = Color.YELLOW
    textRenderer.draw(sb, txtString, 8f, CSGO.gameHeight/4f + 512)

    sb.end()
}