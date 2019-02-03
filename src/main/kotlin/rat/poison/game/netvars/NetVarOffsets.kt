package rat.poison.game.netvars

object NetVarOffsets {
	
	const val dwModel = 0x6C
	val iTeamNum by netVar("DT_BaseEntity")
	val bSpotted by netVar("DT_BaseEntity")
	val bSpottedByMask by netVar("DT_BaseEntity")
	val vecOrigin by netVar("DT_BaseEntity")
	
	val iCompetitiveRanking by netVar("DT_CSPlayerResource")
	
	val fFlags by netVar("DT_BasePlayer")
	val lifeState by netVar("DT_BasePlayer")

	val aimPunchAngle by netVar("DT_BasePlayer", "m_aimPunchAngle") //vecPunch
	val szLastPlaceName by netVar("DT_BasePlayer")
	val iHealth by netVar("DT_BasePlayer")
	val vecViewOffset by netVar("DT_BasePlayer", "m_vecViewOffset[0]")
	val vecVelocity by netVar("DT_BasePlayer", "m_vecVelocity[0]")
	val hActiveWeapon by netVar("DT_BasePlayer", "m_hActiveWeapon")
	val nTickBase by netVar("DT_BasePlayer")
	
	val flFlashMaxAlpha by netVar("DT_CSPlayer")
	val iCrossHairID by netVar("DT_CSPlayer", "m_bHasDefuser", 0x5C)
	val iShotsFired by netVar("DT_CSPlayer")
	val bIsScoped by netVar("DT_CSPlayer")
	val bHasDefuser by netVar("DT_CSPlayer", "m_bHasDefuser")
	val nSurvivalTeam by netVar("DT_CSPlayer")
	
	val flC4Blow by netVar("DT_PlantedC4")
	val bBombDefused by netVar("DT_PlantedC4")
	val hBombDefuser by netVar("DT_PlantedC4")
	val flDefuseCountDown by netVar("DT_PlantedC4")
	
	val hOwnerEntity by netVar("DT_BaseEntity")
	
	val dwBoneMatrix by netVar("DT_BaseAnimating", "m_nForceBone", 0x1C)
	
	val flNextPrimaryAttack by netVar("DT_BaseCombatWeapon")
	val iClip1 by netVar("DT_BaseCombatWeapon")
	val iClip2 by netVar("DT_BaseCombatWeapon")
	
	val iItemDefinitionIndex by netVar("DT_BaseCombatWeapon")

	val m_hViewModel by netVar("DT_BasePlayer", "m_hViewModel[0]") //[0] works for weapon, doesn't work otherwise

	val SurvivalGameRuleDecisionTypes by netVar("DT_CSGameRulesProxy")

	val m_totalHitsOnServer by netVar("DT_CSPlayer", "m_totalHitsOnServer")
}