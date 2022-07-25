package com.example.core_database_data.repository

import com.example.core_database_data.database.FavoriteVideoGameDatabase
import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGame
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGameDTO
import io.realm.notifications.ResultsChange
import io.realm.query.RealmScalarQuery
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteVideoGameRepositoryImpl @Inject constructor(
    private val database: FavoriteVideoGameDatabase
): FavoriteVideoGameRepository {

    override suspend fun write(item: FavoriteVideoGame) = database.write(item)

    override fun getAll(): Flow<List<FavoriteVideoGameDTO>> = database.getAll()

    override fun getCount(): RealmScalarQuery<Long> = database.getCount()

    override suspend fun delete(item: FavoriteVideoGame) = database.delete(item)

}