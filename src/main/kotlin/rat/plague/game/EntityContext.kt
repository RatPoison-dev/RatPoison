

package rat.plague.game

import rat.plague.game.entity.Entity
import rat.plague.game.entity.EntityType

data class EntityContext(var entity: Entity = -1, var glowAddress: Entity = -1,
                         var glowIndex: Int = -1, var type: EntityType = EntityType.NULL) {
	
	fun set(entity: Entity, glowAddress: Entity, glowIndex: Int, type: EntityType) = apply {
		this.entity = entity
		this.glowAddress = glowAddress
		this.glowIndex = glowIndex
		this.type = type
	}
	
}