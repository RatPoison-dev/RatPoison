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

//todo bruh

fun drawDebug() = App {
    if (!dbg) return@App
    txtString.clear()
    txtString.append("Weapon Override: ")
    txtString.append(curWepOverride)


    if (curWepOverride) {
        txtString.append("Current Override: ")
        txtString.append(curWepCategory)
        txtString.append(":")
        txtString.append(meCurWep.name)
    }
    txtString.append("Factor Recoil: ").appendLine(FACTOR_RECOIL)

    txtString.append("On Shot:").appendLine(AIM_ONLY_ON_SHOT)

    txtString.append("Flat Aim:").appendLine(ENABLE_FLAT_AIM)

    txtString.append("Path Aim:").appendLine(ENABLE_PATH_AIM)

    txtString.append("Aim Bone: ")
    txtString.append(curSettings["AIM_BONE"])

    txtString.append("Force Bone: ")
    txtString.append(curSettings["FORCE_AIM_BONE"])

    txtString.append("Fov: ").appendLine(AIM_FOV)

    txtString.append("Speed: ").append(AIM_SPEED)

    txtString.append("Smooth: ").appendLine(AIM_SMOOTHNESS)

    txtString.append("Perfect Aim: ").appendLine(PERFECT_AIM)

    txtString.append("Perfect Fov: ").appendLine(PERFECT_AIM_FOV)

    txtString.append("Perfect Chance: ").appendLine(PERFECT_AIM_CHANCE)

    txtString.append("Scoped Only: ").appendLine(ENABLE_SCOPED_ONLY)

    txtString.append("Aim After #: ").appendLine(AIM_AFTER_SHOTS)

    txtString.appendLine()

    txtString.append("Trigger: ").appendLine(TRIGGER_BOT)

    txtString.append("Trigger Aimbot: ").appendLine(TRIGGER_USE_AIMBOT)

    txtString.append("Trigger Backtrack: ").appendLine(TRIGGER_USE_BACKTRACK)

    txtString.append("Trigger InCross: ").appendLine(TRIGGER_USE_INCROSS)

    txtString.append("Trigger InFov: ").appendLine(TRIGGER_USE_FOV)

    txtString.append("Trigger Fov: ").appendLine(TRIGGER_FOV)

    txtString.append("Trigger Init Delay: ").appendLine(TRIGGER_INIT_SHOT_DELAY)

    txtString.append("Trigger Per Shot Delay: ").appendLine(TRIGGER_PER_SHOT_DELAY)

    txtString.appendLine()

    txtString.append("Backtrack: ").appendLine(BACKTRACK)

    txtString.append("Backtrack MS: ").appendLine(BACKTRACK_MS)

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