package com.example.core_database_domain.repository

import androidx.paging.PagingSource
import com.example.core_model.database.room.favoriteVideoGame.FavoriteVideoGameDTO
import kotlinx.coroutines.flow.Flow

interface FavoriteVideoGameRepository {

    suspend fun addItem(item: FavoriteVideoGameDTO)

    fun getItems(search:String = ""): PagingSource<Int, FavoriteVideoGameDTO>

    fun getCount(): Flow<Int>

    // if the video game is in the database
    suspend fun ifVideoGameInDatabase(id:Int):Boolean

    suspend fun deleteItem(id:Int)

    suspend fun clear()
}