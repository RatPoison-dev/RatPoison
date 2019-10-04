package rat.poison.game.netvars

object NetVarOffsets {
	
	const val dwModel = 0x6C
	val iTeamNum by netVar("DT_BaseEntity")
	val bSpotted by netVar("DT_BaseEntity")
	val bSpottedByMask by netVar("DT_BaseEntity")
	val vecOrigin by netVar("DT_BaseEntity")
	val angEyeAngles by netVar("DT_CSPlayer")

	val iCompetitiveRanking by netVar("DT_CSPlayerResource")
	
	val fFlags by netVar("DT_BasePlayer")
	val lifeState by netVar("DT_BasePlayer")

	val iFOV by netVar("DT_CSPlayer", "m_iFOV")

	val aimPunchAngle by netVar("DT_BasePlayer", "m_aimPunchAngle")
	val szLastPlaceName by netVar("DT_BasePlayer")
	val iHealth by netVar("DT_BasePlayer")
	val m_flHealthShotBoostExpirationTime by netVar("DT_CSPlayer", "m_flHealthShotBoostExpirationTim") //No E on end?
	val ArmorValue by netVar("DT_CSPlayer")
	val vecViewOffset by netVar("DT_BasePlayer", "m_vecViewOffset[0]")
	val vecVelocity by netVar("DT_BasePlayer", "m_vecVelocity[0]")
	val hActiveWeapon by netVar("DT_BasePlayer", "m_hActiveWeapon")
	val hMyWeapons by netVar("DT_BaseCombatCharacter", "m_hMyWeapons")
	val nTickBase by netVar("DT_BasePlayer")

	val flFlashMaxAlpha by netVar("DT_CSPlayer")
	val bGunGameImmunity by netVar("DT_CSPlayer")
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
	
	val iItemDefinitionIndex by netVar("DT_BaseCombatWeapon")

	val m_hViewModel by netVar("DT_BasePlayer", "m_hViewModel[0]")

	val SurvivalGameRuleDecisionTypes by netVar("DT_CSGameRulesProxy")

	val m_totalHitsOnServer by netVar("DT_CSPlayer", "m_totalHitsOnServer")

	val m_hObserverTarget by netVar("DT_BasePlayer", "m_hObserverTarget")
	val m_iObserverMode by netVar("DT_BasePlayer", "m_iObserverMode")
	val m_hOwnerEntity by netVar("DT_CSPlayer", "m_hOwnerEntity")
	val m_bShouldGlow by netVar("DT_DynamicProp", "m_bShouldGlow")
	val m_fAccuracyPenalty by netVar("DT_WeaponCSBase", "m_fAccuracyPenalty")
	val m_nModelIndex by netVar("DT_BaseViewModel", "m_nModelIndex")
	val m_iViewModelIndex by netVar("DT_BaseCombatWeapon", "m_iViewModelIndex")
	val m_iEntityQuality by netVar("DT_BaseAttributableItem", "m_iEntityQuality")
}
