package rat.poison.game.entity

import org.jire.kna.boolean
import org.jire.kna.int
import rat.poison.game.CSGO.csgoEXE
import rat.poison.game.netvars.NetVarOffsets
import rat.poison.scripts.gvars
import rat.poison.settings.SMOKE_EFFECT_TIME

typealias Smoke = Long

internal fun Smoke.didEffect(): Boolean = csgoEXE.boolean(this + NetVarOffsets.bDidSmokeEffect)

internal fun Smoke.tickBegin(): Int = csgoEXE.int(this + NetVarOffsets.nSmokeEffectTickBegin)

internal fun Smoke.timeLeftToDisappear(): Double = (SMOKE_EFFECT_TIME - ((gvars.tickCount - this.tickBegin()) * gvars.intervalPerTick)).toDouble()