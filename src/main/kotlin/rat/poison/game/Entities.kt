package rat.poison.game

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.Player
import rat.poison.settings.MAX_ENTITIES

@Volatile
var me: Player = 0
@Volatile
var clientState: ClientState = 0

typealias EntityList = Object2ObjectArrayMap<EntityType, MutableList<EntityContext>>

var entitiesValues = arrayOfNulls<MutableList<EntityContext>>(MAX_ENTITIES)
var entitiesValuesCounter = 0

val entities: Object2ObjectMap<EntityType, MutableList<EntityContext>> = EntityList(EntityType.size).apply {
	for (type in EntityType.cachedValues) {
		val list = mutableListOf<EntityContext>()
		put(type, list)
		entitiesValues[entitiesValuesCounter++] = list
	}
}

fun entityByType(type: EntityType): EntityContext? = entities[type]?.firstOrNull()

internal inline fun forEntities(vararg types: EntityType, crossinline body: (EntityContext) -> Unit) {
	val forEnts: ArrayList<EntityContext?> = ArrayList()

	if (types.isEmpty()) {
		for (entType in EntityType.values()) {
			entities[entType]?.let { forEnts.addAll(it) }
		}
	} else {
		for (entType in types) {
			entities[entType]?.let { forEnts.addAll(it) }
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