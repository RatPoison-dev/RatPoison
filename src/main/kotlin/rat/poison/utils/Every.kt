package rat.poison.utils

import it.unimi.dsi.fastutil.objects.ObjectArrayList
import it.unimi.dsi.fastutil.objects.ObjectList
import rat.poison.haltProcess
import java.util.concurrent.Executors
import java.util.concurrent.Phaser
import java.util.concurrent.ThreadFactory
import java.util.concurrent.TimeUnit
import kotlin.system.measureNanoTime

@Volatile
var inBackground = false

@Volatile
var inGame = false

@Volatile
var shouldPostProcess = false

@Volatile
var inFullscreen = false

private const val debugSlowTasks = false

class Task(
	val body: Boss.(Task) -> Unit,
	val duration: Long,
	val continuous: Boolean,
	val inGameCheck: Boolean
) {
	@Volatile
	var tempDuration = duration
	
	@Volatile
	var executeIfTemped: (() -> Unit)? = null
	
	fun delayed(duration: Long, afterDelay: (() -> Unit)? = null) {
		tempDuration = duration
		executeIfTemped = afterDelay
	}
	
	val created = Exception("$duration")
	fun shouldRun() = (continuous || !inBackground) && ((inGameCheck && inGame) || !inGameCheck)
}

abstract class Boss(
	val threadGroupName: String,
	val workerThreadCount: Int,
	val threadPriority: Int,
	val slowTimeout: Long
) : Thread() {
	private var threadID = 0
	
	val workerThreadFactory =
		ThreadFactory { r -> Thread(r, "$threadGroupName-${threadID++}").apply { priority = threadPriority - 1 } }
	val workers = Executors.newFixedThreadPool(workerThreadCount, workerThreadFactory)
	
	val tasks: ObjectList<Task> = ObjectArrayList()
	lateinit var tasksArray: Array<Task>
	
	open fun every(
		duration: Long,
		continuous: Boolean = false,
		inGameCheck: Boolean = false,
		body: Boss.(Task) -> Unit
	) = tasks.add(Task(body, duration, continuous, inGameCheck))
	
	var cycles = 0L
	
	override fun run() {
		priority = threadPriority
		tasksArray = tasks.toTypedArray()
		tasks.clear()
		
		cycles++
		
		val phaser = Phaser(1)
		while (!interrupted() && !haltProcess) {
			val elapsed = TimeUnit.MILLISECONDS.convert(measureNanoTime {
				executeTasks(phaser)
				cycles++
			}, TimeUnit.NANOSECONDS)
			if (elapsed < 1) sleep(1L)
			else if (debugSlowTasks && cycles > 5000 && elapsed >= slowTimeout)
				println("WARNING: \"${threadGroupName}\" slow cycle (${elapsed}ms)")
		}
	}
	
	open fun executeTasks(phaser: Phaser) {
		for (task in tasksArray) {
			val duration = task.tempDuration
			if (duration != 1L && cycles % duration != 0L) continue
			if (task.shouldRun()) {
				phaser.register()
				if (duration != task.duration) {
					task.tempDuration = task.duration // reset duration back to original for next time!
					task.executeIfTemped?.invoke()
					task.executeIfTemped = null
				}
				workers.execute {
					val elapsed = TimeUnit.MILLISECONDS.convert(measureNanoTime {
						try {
							task.body(this, task)
						} catch (e: Exception) {
							e.printStackTrace()
						} finally {
							phaser.arriveAndDeregister()
						}
					}, TimeUnit.NANOSECONDS)
					if (debugSlowTasks && cycles > 5000 && elapsed > slowTimeout) {
						Exception("Task took long! ${elapsed}ms", task.created).printStackTrace()
					}
				}
			}
		}
		phaser.arriveAndAwaitAdvance()
	}
}

class IsolatedPriority(threadGroupName: String) : Boss(threadGroupName, 1, MAX_PRIORITY, 8) {
	override fun every(duration: Long, continuous: Boolean, inGameCheck: Boolean, body: Boss.(Task) -> Unit) =
		super.every(duration, continuous, inGameCheck, body).apply { this@IsolatedPriority.task = tasks[0]; start() }
	
	lateinit var task: Task
	
	override fun executeTasks(phaser: Phaser) = task.body(this, task)
}

object HighPriority : Boss("High", 2, MAX_PRIORITY - 2, 32)
object LowPriority : Boss("Low", 1, MIN_PRIORITY + 1, 64)
typealias MedPriority = LowPriority