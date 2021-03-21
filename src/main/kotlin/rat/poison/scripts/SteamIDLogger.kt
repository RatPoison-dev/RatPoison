package rat.poison.scripts

//fun steamIDLogger() = WebSocket(delay = 10000, precheck = { curSettings.bool["STEAM_ID_LOGGER"] }) {
//    var s = ",addSID,"
//    forEntities(EntityType.CCSPlayer) { c ->
//        val entity = c.entity
//        if (entity.dead() || entity <= 0 || entity.dormant() || entity == me) return@forEntities
//        val sid = entity.steamID()
//
//        if (!validateSteamId(sid)) return@forEntities
//
//        s += "${sid},"
//    }
//    it.send(s)
//}