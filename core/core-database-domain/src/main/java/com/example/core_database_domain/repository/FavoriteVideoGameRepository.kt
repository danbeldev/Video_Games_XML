package com.example.core_database_domain.repository

import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGame
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGameDTO
import io.realm.notifications.ResultsChange
import io.realm.query.RealmScalarQuery
import kotlinx.coroutines.flow.Flow

interface FavoriteVideoGameRepository {

    suspend fun write(item: FavoriteVideoGame)

    fun getAll():Flow<List<FavoriteVideoGameDTO>>

    fun getCount(): RealmScalarQuery<Long>

    suspend fun delete(item: FavoriteVideoGame)
}