package rat.poison.game

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.Player
import rat.poison.settings.MAX_ENTITIES
import rat.poison.utils.index.ResizingArrayIndex

@Volatile
var me: Player = 0

@Volatile
var clientState: ClientState = 0

typealias EntityList = Object2ObjectArrayMap<EntityType, ResizingArrayIndex<EntityContext>>

var entitiesValues = arrayOfNulls<ResizingArrayIndex<EntityContext>>(MAX_ENTITIES)
var entitiesValuesCounter = 0

val entities: Object2ObjectMap<EntityType, ResizingArrayIndex<EntityContext>> = EntityList(EntityType.size).apply {
	for (type in EntityType.cachedValues) {
		val list = ResizingArrayIndex(EntityContext::class.java)
		put(type, list)
		entitiesValues[entitiesValuesCounter++] = list
	}
}

fun entityByType(type: EntityType): EntityContext? = entities[type]?.firstOrNull()

internal inline fun forEntities(
	vararg types: EntityType,
	crossinline body: (EntityContext) -> Unit
) {
	if (types.isEmpty()) {
		for (entType in EntityType.cachedValues) {
			val let = entities[entType] ?: continue
			try {
				for (entity in let.getValues()) {
					entity?.run(body)
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
					entity?.run(body)
				}
			} catch (e: Exception) {
				println("forEntities error, report in discord")
				println("$types")
				e.printStackTrace()
			}
		}
	}
}