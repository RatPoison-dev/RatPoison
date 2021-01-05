package rat.poison.game

import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import it.unimi.dsi.fastutil.objects.ObjectArrayList
import it.unimi.dsi.fastutil.objects.ObjectList
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.Player
import rat.poison.settings.MAX_ENTITIES
import rat.poison.utils.index.ResizingArrayIndex

@Volatile
var me: Player = 0

@Volatile
var clientState: ClientState = 0

typealias EntityList = Object2ObjectOpenHashMap<EntityType, ResizingArrayIndex<EntityContext>>

var entitiesValues = arrayOfNulls<ResizingArrayIndex<EntityContext>>(MAX_ENTITIES)
var entitiesValuesCounter = 0

val entities: Object2ObjectMap<EntityType, ResizingArrayIndex<EntityContext>> = EntityList(EntityType.size).apply {
	for (type in EntityType.cachedValues) {
		val list = ResizingArrayIndex(EntityContext::class.java)
		put(type, list)
		entitiesValues[entitiesValuesCounter++] = list
	}
}

fun entityByType(type: EntityType): EntityContext? = entities[type]?.firstOrNullUnsafe()

internal inline fun forEntities(
	vararg types: EntityType,
	crossinline body: (EntityContext) -> Unit
) {
	val forEnts: ObjectList<EntityContext> = ObjectArrayList()
	
	if (types.isEmpty()) {
		for (entType in EntityType.cachedValues) {
			val let = entities[entType] ?: continue
			try {
				for (entity in let.getValues()) {
					forEnts.add(entity ?: continue)
				}
			} catch (e: Exception) {
				println("forEntities error, report in discord")
				println("$types")
				e.printStackTrace()
			}
		}
	} else {
		for (entType in types) {
			val let = entities[entType] ?: continue
			try {
				for (entity in let.getValues()) {
					forEnts.add(entity ?: continue)
				}
			} catch (e: Exception) {
				println("forEntities error, report in discord")
				println("$types")
				e.printStackTrace()
			}
		}
	}
	
	//iterator later
	try {
		val iterator = forEnts.listIterator()
		while (iterator.hasNext()) {
			iterator.next()?.run(body)
		}
	} catch (e: Exception) {
		println("forEntities error, report in discord")
		println("$types")
		e.printStackTrace()
	}
}