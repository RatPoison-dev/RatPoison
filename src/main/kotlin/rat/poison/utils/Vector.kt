package rat.poison.utils

import it.unimi.dsi.fastutil.longs.*
import net.openhft.chronicle.core.OS
import kotlin.concurrent.thread
import kotlin.math.abs

inline class Vector(val value: Long) {
	constructor(x: Float = 0F, y: Float = 0F, z: Float = 0F, track: Boolean = true) : this(vectorLong(x, y, z, track))
	
	var x
		get() = OS.memory().readVolatileFloat(value)
		set(value) = OS.memory().writeVolatileFloat(this.value, value)
	var y
		get() = OS.memory().readVolatileFloat(value + 4)
		set(value) = OS.memory().writeVolatileFloat(this.value + 4, value)
	var z
		get() = OS.memory().readVolatileFloat(value + 8)
		set(value) = OS.memory().writeVolatileFloat(this.value + 8, value)
	
	fun release() {
		OS.memory().freeMemory(value, 12)
		if (trackedVectorsMillis != -1L) {
			trackedVectors.remove(value)
		}
	}
	
	inline fun use(crossinline use: (Vector) -> Unit) {
		try {
			use(this)
		} finally {
			release()
		}
	}
	
	fun set(x: Float, y: Float, z: Float = 0F) = apply {
		this.x = x
		this.y = y
		this.z = z
	}
	
	fun invalid() = x == 0F && y == 0F && z == 0F
	fun valid() = !invalid()
	
	fun isValid() = true
	
	fun normalize() = apply {
		if (x != x) x = 0F
		if (y != y) y = 0F
		
		if (x > 89) x = 89F
		if (x < -89) x = -89F
		
		while (y > 180) y -= 360
		while (y <= -180) y += 360
		
		if (y > 180) y = 180F
		if (y < -180F) y = -180F
		
		z = 0F
	}
	
	fun distanceTo(target: Vector) = abs(x - target.x) + abs(y - target.y) + abs(z - target.z)
	
	fun dot(x: Float, y: Float, z: Float) = dot(x, y, z, this.x, this.y, this.z)
	
	fun dst2(x: Float, y: Float, z: Float): Float {
		val a = x - this.x
		val b = y - this.y
		val c = z - this.z
		return a * a + b * b + c * c
	}
}

fun dot(x: Float, y: Float, z: Float, tx: Float, ty: Float, tz: Float): Float = tx * x + ty * y + tz * z

lateinit var trackedVectors: Long2ObjectMap<Pair<Long, Exception>>
var trackedVectorsMillis = -1L

fun trackVectors(warnMinimumMillis: Long = 2000L) {
	trackedVectors = Long2ObjectMaps.synchronize(Long2ObjectOpenHashMap(8192))
	trackedVectorsMillis = warnMinimumMillis
	thread(isDaemon = true) {
		while (!Thread.interrupted()) {
			try {
				val toRemove: LongList = LongArrayList()
				synchronized(trackedVectors) {
					for ((k, v) in trackedVectors.long2ObjectEntrySet()) {
						val (timeCreated, createdException) = v
						if (System.currentTimeMillis() - timeCreated >= warnMinimumMillis) {
							createdException.printStackTrace()
							toRemove.add(k)
						}
					}
				}
				val it = toRemove.listIterator()
				while (it.hasNext()) {
					val next = it.nextLong()
					trackedVectors.remove(next)
				}
			} catch (t: Throwable) {
				t.printStackTrace()
			}
			Thread.sleep(warnMinimumMillis)
		}
	}
}

fun vectorLong(x: Float = 0F, y: Float = 0F, z: Float = 0F, track: Boolean = true): Long {
	val address = OS.memory().allocate(12)
	OS.memory().writeVolatileFloat(address, x)
	OS.memory().writeVolatileFloat(address + 4, y)
	OS.memory().writeVolatileFloat(address + 8, z)
	if (trackedVectorsMillis != -1L && track) {
		trackedVectors[address] =
			System.currentTimeMillis() to Exception(
				"Vector@$address took too long (> ${trackedVectorsMillis}ms) to be released."
			)
	}
	return address
}

typealias Angle = Vector

fun angle(x: Float = 0F, y: Float = 0F, track: Boolean = true): Angle = Vector(x, y, track = track)

fun vector(x: Float = 0F, y: Float = 0F, z: Float = 0F, track: Boolean = true): Vector = Vector(vectorLong(x, y, z, track))