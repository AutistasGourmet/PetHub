package com.autistasgourmet.pethub.data.repository

import com.autistasgourmet.pethub.data.mapper.toDomain
import com.autistasgourmet.pethub.data.mapper.toDto
import com.autistasgourmet.pethub.data.remote.dto.AdopterProfileDto
import com.autistasgourmet.pethub.domain.model.AdopterProfile
import com.autistasgourmet.pethub.domain.repository.AdopterProfileRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.TimeoutCancellationException

class AdopterProfileRepositoryImpl(
    private val firestore: FirebaseFirestore
) : AdopterProfileRepository {

    // adopt_profiles/{userId}
    private val profilesCollection = firestore.collection("adopt_profiles")

    override suspend fun getProfile(userId: String): AdopterProfile? {
        return try {
            profilesCollection.document(userId).get().await()
                .toObject(AdopterProfileDto::class.java)
                ?.toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun saveProfile(profile: AdopterProfile): Result<Unit> {
        return try {
            withTimeout(3000) {
                profilesCollection.document(profile.userId).set(profile.toDto()).await()
            }
            Result.success(Unit)
        } catch (e: TimeoutCancellationException) {
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProfile(userId: String): Result<Unit> {
        return try {
            profilesCollection.document(userId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
