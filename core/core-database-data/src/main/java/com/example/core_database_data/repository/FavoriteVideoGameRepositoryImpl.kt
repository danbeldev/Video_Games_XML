package com.example.core_database_data.repository

import com.example.core_database_data.database.FavoriteVideoGameDatabase
import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGame
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGameDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteVideoGameRepositoryImpl @Inject constructor(
    private val database: FavoriteVideoGameDatabase
): FavoriteVideoGameRepository {

    override suspend fun write(item: FavoriteVideoGame) = database.write(item)

    override fun getAll(): Flow<List<FavoriteVideoGameDTO>> = database.getAll()

    override fun getCount(): Flow<Long> = database.getCount()

    override fun getCheckVideoGameById(id: Int): Boolean = database.getCheckVideoGameById(id)

    override suspend fun delete(id: Int) = database.delete(id)
}