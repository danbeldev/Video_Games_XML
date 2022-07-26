package com.example.core_database_domain.repository

import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGame
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGameDTO
import kotlinx.coroutines.flow.Flow

interface FavoriteVideoGameRepository {

    suspend fun write(item: FavoriteVideoGame)

    fun getAll():Flow<List<FavoriteVideoGameDTO>>

    fun getCount(): Flow<Long>

    fun getCheckVideoGameById(id:Int):Boolean

    suspend fun delete(id:Int)
}