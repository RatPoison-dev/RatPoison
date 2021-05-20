package rat.poison.game

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.Player
import rat.poison.game.hooks.forceResetIteration
import rat.poison.settings.MAX_ENTITIES
import rat.poison.utils.lists.ForEntitiesList

@Volatile
var me: Player = 0
@Volatile
var clientState: ClientState = 0
@Volatile
var meTeam: Long = 0

typealias EntityList = Object2ObjectOpenHashMap<EntityType, ForEntitiesList>

var entitiesValues = arrayOfNulls<ForEntitiesList>(MAX_ENTITIES)
var entitiesValuesCounter = 0

val entities: EntityList = EntityList(EntityType.size).apply {
	for (type in EntityType.cachedValues) {
		val list = ForEntitiesList()
		put(type, list)
		entitiesValues[entitiesValuesCounter++] = list
	}
}

fun entityByType(type: EntityType): EntityContext? = entities[type]?.firstOrNull()

internal inline fun iterateByTypes(types: Array<EntityType>, crossinline body: (EntityContext) -> Unit) {
	for (element in types) {
		if (forceResetIteration) {
			break
		}
		val let = entities[element] ?: continue
		let.completeTasks()
		let.iterating = true
		for (entIdx in 0 until let.cachedValues.size) {
			if (forceResetIteration) {
				break
			}
			val ent = let.getOrNull(entIdx) ?: continue
			ent.run(body)
		}
		let.iterating = false
	}
}

internal inline fun forEntities(types: Array<EntityType>, iterateWeapons: Boolean = false, iterateGrenades: Boolean = false, crossinline body: (EntityContext) -> Unit) {
	iterateByTypes(types, body)
	if (iterateGrenades) {
		iterateByTypes(EntityType.grenadeTypes, body)
	}
	if (iterateWeapons) {
		iterateByTypes(EntityType.weaponsTypes, body)
	}
}