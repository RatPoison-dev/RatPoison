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
        sbText.append("Choked packets: ").appendLine(chokedCommands)
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

        uiDebug.contentLabelLeft.setText(sbText)

        sbText.clear()

        sbText.appendLine("Weapon Override: ").appendLine(curWepOverride)

        if (curWepOverride) {
            sbText.append("Current Override: ").append(curWepCategory).append(meCurWep.name)
        }

        sbText.append("Factor Recoil: ").appendLine(FACTOR_RECOIL)
        sbText.append("On Shot: ").appendLine(AIM_ONLY_ON_SHOT)
        sbText.append("Flat Aim: ").appendLine(ENABLE_FLAT_AIM)
        sbText.append("Path Aim: ").appendLine(ENABLE_PATH_AIM)
        sbText.append("Aim Bone: ").appendLine(curSettings["AIM_BONE"])
        sbText.append("Force Bone: ").appendLine(curSettings["FORCE_AIM_BONE"])
        sbText.append("Fov: ").appendLine(AIM_FOV)
        sbText.append("Smooth: ").appendLine(AIM_SMOOTHNESS)
        sbText.append("Perfect Aim: ").appendLine(PERFECT_AIM)
        sbText.append("Perfect Fov: ").appendLine(PERFECT_AIM_FOV)
        sbText.append("Perfect Chance: ").appendLine(PERFECT_AIM_CHANCE)
        sbText.append("Scoped Only: ").appendLine(ENABLE_SCOPED_ONLY)
        sbText.append("Aim After #: ").appendLine(AIM_AFTER_SHOTS)
        sbText.append("Trigger: ").appendLine(TRIGGER_BOT)
        sbText.append("Trigger Aimbot: ").appendLine(TRIGGER_USE_AIMBOT)
        sbText.append("Trigger Backtrack: ").appendLine(TRIGGER_USE_BACKTRACK)
        sbText.append("Trigger InCross: ").appendLine(TRIGGER_USE_INCROSS)
        sbText.append("Trigger InFov: ").appendLine(TRIGGER_USE_FOV)
        sbText.append("Trigger Fov: ").appendLine(TRIGGER_FOV)
        sbText.append("Trigger Init Delay: ").appendLine(TRIGGER_INIT_SHOT_DELAY)
        sbText.append("Trigger Per Shot Delay: ").appendLine(TRIGGER_PER_SHOT_DELAY)
        sbText.append("Backtrack: ").appendLine(BACKTRACK)
        sbText.append("Backtrack MS: ").appendLine(BACKTRACK_MS)

        uiDebug.contentLabelRight.setText(sbText)

        timer = 0
    }
}

