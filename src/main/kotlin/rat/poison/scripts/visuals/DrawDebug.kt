package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.game.CSGO
import rat.poison.overlay.App
import rat.poison.scripts.aim.*

private val txtString = StringBuilder()
private val concatStringBuilder = StringBuilder()
fun drawDebug() = App {
    if (!dbg) return@App
    txtString.clear()
    concatStringBuilder.clear()
    concatStringBuilder.append("Weapon Override: ")
    concatStringBuilder.append(curWepOverride)

    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    if (curWepOverride) {
        concatStringBuilder.append("Current Override: ")
        concatStringBuilder.append(curWepCategory)
        concatStringBuilder.append(":")
        concatStringBuilder.append(meCurWep.name)
        txtString.appendLine(concatStringBuilder)
        concatStringBuilder.clear()
    }
    concatStringBuilder.append("Factor Recoil: ")
    concatStringBuilder.append(curSettings["FACTOR_RECOIL"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()
    concatStringBuilder.append("On Shot: ")
    concatStringBuilder.append(curSettings["AIM_ONLY_ON_SHOT"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Flat Aim: ")
    concatStringBuilder.append(curSettings["ENABLE_FLAT_AIM"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Path Aim: ")
    concatStringBuilder.append(curSettings["ENABLE_PATH_AIM"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Aim Bone: ")
    concatStringBuilder.append(curSettings["AIM_BONE"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Force Bone: ")
    concatStringBuilder.append(curSettings["FORCE_AIM_BONE"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Fov: ")
    concatStringBuilder.append(curSettings["AIM_FOV"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Speed: ")
    concatStringBuilder.append(curSettings["AIM_SPEED"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Smooth: ")
    concatStringBuilder.append(curSettings["AIM_SMOOTHNESS"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Perfect Aim: ")
    concatStringBuilder.append(curSettings["PERFECT_AIM"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Perfect Fov: ")
    concatStringBuilder.append(curSettings["PERFECT_AIM_FOV"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Perfect Chance: ")
    concatStringBuilder.append(curSettings["PERFECT_AIM_CHANCE"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Scoped Only: ")
    concatStringBuilder.append(curSettings["ENABLE_SCOPED_ONLY"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Aim After #: ")
    concatStringBuilder.append(curSettings["AIM_AFTER_SHOTS"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    txtString.appendLine()

    concatStringBuilder.append("Trigger: ")
    concatStringBuilder.append(curSettings["TRIGGER_BOT"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger Aimbot: ")
    concatStringBuilder.append(curSettings["TRIGGER_USE_AIMBOT"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger Backtrack: ")
    concatStringBuilder.append(curSettings["TRIGGER_USE_BACKTRACK"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger InCross: ")
    concatStringBuilder.append(curSettings["TRIGGER_USE_INCROSS"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger InFov: ")
    concatStringBuilder.append(curSettings["TRIGGER_USE_FOV"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger Fov: ")
    concatStringBuilder.append(curSettings["TRIGGER_FOV"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger Init Delay: ")
    concatStringBuilder.append(curSettings["TRIGGER_INIT_SHOT_DELAY"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger Per Shot Delay: ")
    concatStringBuilder.append(curSettings["TRIGGER_PER_SHOT_DELAY"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    txtString.appendLine()

    concatStringBuilder.append("Backtrack: ")
    concatStringBuilder.append(curSettings["BACKTRACK"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Backtrack MS: ")
    concatStringBuilder.append(curSettings["BACKTRACK_MS"])
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    if (!shapeRenderer.isDrawing) {
        shapeRenderer.begin()
    }

    shapeRenderer.color = Color.BLACK
    shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
    shapeRenderer.rect(1f, CSGO.gameHeight/4f+8f, 225f, 512f)
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