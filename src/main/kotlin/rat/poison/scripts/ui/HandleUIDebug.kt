package rat.poison.scripts.ui

import rat.poison.curSettings
import rat.poison.game.entity.shotsFired
import rat.poison.game.me
import rat.poison.overlay.*
import rat.poison.scripts.aim.curWepCategory
import rat.poison.scripts.aim.curWepOverride
import rat.poison.scripts.aim.meCurWep
import rat.poison.scripts.userCmd.chokedCommands
import rat.poison.scripts.userCmd.meDead
import rat.poison.settings.*
import rat.poison.utils.common.inGame
import rat.poison.utils.common.shouldPostProcess
import rat.poison.utils.extensions.appendHumanReadableSize
import rat.poison.utils.extensions.roundNDecimals

private val sbText = StringBuilder()

private var timer = 0

fun handleUIDebug() = App {
    timer++

    if (timer >= curSettings.int["OPENGL_FPS"]) {
        val runtime = Runtime.getRuntime()

        val totalMem = runtime.totalMemory()
        val freeMem = runtime.freeMemory()
        val usedMem = totalMem - freeMem
        val totalPhysMem = osBean.totalMemorySize
        val freePhysMem = osBean.freeMemorySize
        val processLoad = osBean.processCpuLoad
        val systemLoad = osBean.cpuLoad

        sbText.clear()
        sbText.append("In Game: ")
        sbText.appendLine(inGame)
        sbText.append("ShouldPostProcess: ")
        sbText.appendLine(shouldPostProcess)
        sbText.append("Me: ")
        sbText.append(me)
        sbText.append(" Dead: ")
        sbText.appendLine(meDead)
        sbText.append("Danger Zone: ")
        sbText.appendLine(DANGER_ZONE)
        sbText.append("Shots fired: ")
        sbText.appendLine(me.shotsFired())
        sbText.appendLine("Choked packets: $chokedCommands")
        sbText.appendLine()
        sbText.append("Total physical mem: ")
        sbText.appendHumanReadableSize(totalPhysMem)
        sbText.appendLine()
        sbText.append("Free physical mem: ").appendHumanReadableSize(freePhysMem).appendLine()

        sbText.append("Total allocated mem: ").appendHumanReadableSize(totalMem).appendLine()

        sbText.append("Free allocated mem: ").appendHumanReadableSize(freeMem).appendLine()
        sbText.append("Used allocated mem: ").appendHumanReadableSize(usedMem).appendLine()

        sbText.append("\nProcess load: ").append((processLoad.toFloat() * 100F).roundNDecimals(2)).append("%")
        sbText.append("\nSystem load: ").append((systemLoad.toFloat() * 100F).roundNDecimals(2)).append("%")

        sbText.append("\n\nSync took: ").append((syncTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
        sbText.append("\nOverlay took: ").append((overlayTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
        sbText.append("\n   Menu took: ").append((menuTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
        sbText.append("\n   Apps took: ").append((appTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
        sbText.append("\n      Glow took: ").append((glowTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")
        //sbText.append("\n      Bsp Vis Check took: ").append((bspVisTime.toFloat() * 0.000001F).roundNDecimals(4)).append(" ms")

        uiDebug.contentLabelLeft.setText(sbText.toString())

        sbText.clear()

        sbText.appendLine("Weapon Override: $curWepOverride")

        if (curWepOverride) {
            sbText.appendLine("Current Override: $curWepCategory:${meCurWep.name}")
        }

        sbText.appendLine("Factor Recoil: $FACTOR_RECOIL")
        sbText.appendLine("On Shot: $AIM_ONLY_ON_SHOT")
        sbText.appendLine("Flat Aim: $ENABLE_FLAT_AIM")
        sbText.appendLine("Path Aim: $ENABLE_PATH_AIM")
        sbText.appendLine("Aim Bone: ${curSettings["AIM_BONE"]}")
        sbText.appendLine("Force Bone: ${curSettings["FORCE_AIM_BONE"]}")
        sbText.appendLine("Fov: $AIM_FOV")
        sbText.appendLine("Smooth: $AIM_SMOOTHNESS")
        sbText.appendLine("Perfect Aim: $PERFECT_AIM")
        sbText.appendLine("Perfect Fov: $PERFECT_AIM_FOV")
        sbText.appendLine("Perfect Chance: $PERFECT_AIM_CHANCE")
        sbText.appendLine("Scoped Only: $ENABLE_SCOPED_ONLY")
        sbText.appendLine("Aim After #: $AIM_AFTER_SHOTS")
        sbText.appendLine("Trigger: $TRIGGER_BOT")
        sbText.appendLine("Trigger Aimbot: $TRIGGER_USE_AIMBOT")
        sbText.appendLine("Trigger Backtrack: $TRIGGER_USE_BACKTRACK")
        sbText.appendLine("Trigger InCross: $TRIGGER_USE_INCROSS")
        sbText.appendLine("Trigger InFov: $TRIGGER_USE_FOV")
        sbText.appendLine("Trigger Fov: $TRIGGER_FOV")
        sbText.appendLine("Trigger Init Delay: $TRIGGER_INIT_SHOT_DELAY")
        sbText.appendLine("Trigger Per Shot Delay: $TRIGGER_PER_SHOT_DELAY")
        sbText.appendLine("Backtrack: $BACKTRACK")
        sbText.appendLine("Backtrack MS: $BACKTRACK_MS")

        uiDebug.contentLabelRight.setText(sbText.toString())

        timer = 0
    }
}

