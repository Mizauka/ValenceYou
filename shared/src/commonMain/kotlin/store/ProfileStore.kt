package app.valenceyou.shared.store

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.valenceyou.shared.ValenceYouDatabase
import app.valenceyou.shared.model.AffectBlock
import app.valenceyou.shared.model.StateProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

/**
 * SQLDelight-backed store for profiles and affect blocks.
 */
class ProfileStore(database: ValenceYouDatabase) {

    private val queries = database.valenceYouDatabaseQueries

    // ---- StateProfile ----

    suspend fun insertProfile(profile: StateProfile) = withContext(Dispatchers.IO) {
        queries.insertProfile(
            id = profile.id,
            name = profile.name,
            createdAt = profile.createdAt,
            updatedAt = profile.updatedAt,
            baselineValence = profile.baselineValence,
            baselineArousal = profile.baselineArousal
        )
    }

    fun allProfiles(): Flow<List<StateProfile>> =
        queries.selectAllProfiles()
            .asFlow()
            .mapToList(Dispatchers.IO)

    suspend fun profileById(id: String): StateProfile? = withContext(Dispatchers.IO) {
        queries.selectProfileById(id).executeAsOneOrNull()
    }

    suspend fun updateProfile(profile: StateProfile) = withContext(Dispatchers.IO) {
        queries.updateProfile(
            name = profile.name,
            updatedAt = profile.updatedAt,
            baselineValence = profile.baselineValence,
            baselineArousal = profile.baselineArousal,
            id = profile.id
        )
    }

    suspend fun deleteProfile(id: String) = withContext(Dispatchers.IO) {
        queries.deleteProfile(id)
    }

    // ---- AffectBlock ----

    suspend fun insertBlock(block: AffectBlock) = withContext(Dispatchers.IO) {
        queries.insertBlock(
            id = block.id,
            profileId = block.source ?: "",
            timestamp = block.timestamp,
            valence = block.valence,
            arousal = block.arousal,
            label = block.label
        )
    }

    fun blocksForProfile(profileId: String): Flow<List<AffectBlock>> =
        queries.selectBlocksByProfile(profileId)
            .asFlow()
            .mapToList(Dispatchers.IO)

    suspend fun deleteBlock(id: String) = withContext(Dispatchers.IO) {
        queries.deleteBlock(id)
    }
}
