package rat.poison.game

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap
import rat.poison.game.entity.EntityType
import rat.poison.game.entity.Player
import rat.poison.settings.MAX_ENTITIES

@Volatile
var me: Player = 0
@Volatile
var clientState: ClientState = 0
@Volatile
var meTeam: Long = 0

typealias EntityList = Object2ObjectArrayMap<EntityType, MutableList<EntityContext>>

var entitiesValues = arrayOfNulls<MutableList<EntityContext>>(MAX_ENTITIES)
var entitiesValuesCounter = 0

val entities: EntityList = EntityList(EntityType.size).apply {
	for (type in EntityType.cachedValues) {
		val list = mutableListOf<EntityContext>()
		put(type, list)
		entitiesValues[entitiesValuesCounter++] = list
	}
}

fun entityByType(type: EntityType): EntityContext? = entities[type]?.firstOrNull()
data class EntityCache(var created: Long, var ents: ArrayList<EntityContext>, var iterating: Boolean = false)
val entityCache = Object2ObjectArrayMap<String, EntityCache>()
//private const val emptyString = ""
internal inline fun forEntities(vararg types: EntityType, iterateWeapons: Boolean = false, iterateGrenades: Boolean = false, identifier: String = "", crossinline body: (EntityContext) -> Unit) {
	var get = entityCache[identifier]

	if (get == null || System.currentTimeMillis() - get.created > 2000) {
		if (get != null) {
			get.ents.clear()
			get.created = System.currentTimeMillis()
		}
		else {
			val tmpClass = EntityCache(System.currentTimeMillis(), ArrayList())
			get = tmpClass
			entityCache[identifier] = tmpClass
		}
		val col = if (types.isEmpty()) EntityType.cachedValues else types
		for (i in 0 until col.size) {
			val entType = col[i]
			val ents = entities[entType] ?: continue
			for (i1 in 0 until ents.size) {
				val ent = ents[i1]
				get.ents.add(ent)
				ent.run(body)
			}
		}
		if (iterateWeapons) {
			for (i in 0 until EntityType.weaponsTypes.size) {
				val entType = EntityType.weaponsTypes[i]
				val ents = entities[entType] ?: continue
				for (i1 in 0 until ents.size) {
					val ent = ents[i1]
					get.ents.add(ent)
					ent.run(body)
				}
			}
		}
		if (iterateGrenades) {
			for (i in 0 until EntityType.grenadeTypes.size) {
				val entType = EntityType.grenadeTypes[i]
				val ents = entities[entType] ?: continue
				for (i1 in 0 until ents.size) {
					val ent = ents[i1]
					get.ents.add(ent)
					ent.run(body)
				}
			}
		}
	}
	else {
		if (!get.iterating) {
			get.iterating = true
			for (i in 0 until get.ents.size) {
				get.ents[i].run(body)
			}
			get.iterating = false
		}
	}
}