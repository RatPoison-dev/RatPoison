package rat.poison.scripts.visuals

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import rat.poison.curSettings
import rat.poison.dbg
import rat.poison.game.CSGO
import rat.poison.overlay.App
import rat.poison.scripts.aim.*
import rat.poison.settings.*

private val txtString = StringBuilder()
private val concatStringBuilder = StringBuilder()

//todo bruh

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

    concatStringBuilder.append("Factor Recoil: $FACTOR_RECOIL")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("On Shot: $AIM_ONLY_ON_SHOT")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Flat Aim: $ENABLE_FLAT_AIM")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Path Aim: $ENABLE_PATH_AIM")
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

    concatStringBuilder.append("Fov: $AIM_FOV")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Speed: $AIM_SPEED")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Smooth: $AIM_SMOOTHNESS")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Perfect Aim: $PERFECT_AIM")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Perfect Fov: $PERFECT_AIM_FOV")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Perfect Chance: $PERFECT_AIM_CHANCE")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Scoped Only: $ENABLE_SCOPED_ONLY")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Aim After #: $AIM_AFTER_SHOTS")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    txtString.appendLine()

    concatStringBuilder.append("Trigger: $TRIGGER_BOT")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger Aimbot: $TRIGGER_USE_AIMBOT")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger Backtrack: $TRIGGER_USE_BACKTRACK")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger InCross: $TRIGGER_USE_INCROSS")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger InFov: $TRIGGER_USE_FOV")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger Fov: $TRIGGER_FOV")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger Init Delay: $TRIGGER_INIT_SHOT_DELAY")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Trigger Per Shot Delay: $TRIGGER_PER_SHOT_DELAY")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    txtString.appendLine()

    concatStringBuilder.append("Backtrack: $BACKTRACK")
    txtString.appendLine(concatStringBuilder)
    concatStringBuilder.clear()

    concatStringBuilder.append("Backtrack MS: $BACKTRACK_MS")
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