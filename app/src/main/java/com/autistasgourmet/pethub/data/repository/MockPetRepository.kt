package com.autistasgourmet.pethub.data.repository

import android.content.Context
import android.util.Log
import com.autistasgourmet.pethub.domain.model.Pet
import com.autistasgourmet.pethub.domain.repository.PetRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.InputStreamReader

class MockPetRepository(
    private val context: Context
) : PetRepository {

    private fun parsePetsFromJson(): List<Pet> {
        return try {
            val inputStream = context.assets.open("mock_pets.json")
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<List<Pet>>() {}.type
            Gson().fromJson(reader, type)
        } catch (e: Exception) {
            Log.e("MockPetRepository", "Error parsing mock_pets.json", e)
            emptyList()
        }
    }

    override fun getPets(): Flow<List<Pet>> = flow {
        emit(parsePetsFromJson())
    }

    override fun getPetsByOwner(ownerEmail: String): Flow<List<Pet>> = flow {
        emit(parsePetsFromJson().filter { it.ownerEmail == ownerEmail })
    }

    override suspend fun getPetById(id: String): Pet? {
        return parsePetsFromJson().find { it.id == id }
    }

    override suspend fun savePet(pet: Pet): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun deletePet(id: String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun savePetPhoto(base64: String): Result<String> {
        return Result.success("mock_photo_id")
    }

    override suspend fun getPetPhoto(photoId: String): Result<String> {
        return Result.success("")
    }
}
