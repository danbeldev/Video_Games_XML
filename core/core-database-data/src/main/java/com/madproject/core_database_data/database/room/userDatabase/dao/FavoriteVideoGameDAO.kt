package com.madproject.core_database_data.database.room.userDatabase.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.madproject.core_database_data.database.room.userDatabase.entities.FavoriteVideoGame
import com.madproject.core_model.database.room.favoriteVideoGame.FavoriteVideoGameDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteVideoGameDAO {

    @Upsert(entity = FavoriteVideoGame::class)
    suspend fun upsertItem(item: FavoriteVideoGameDTO)

    @Query("SELECT * FROM favorite_video_game WHERE name LIKE '%' || :search || '%' ORDER BY date DESC")
    fun getItemsPagingSource(search:String = ""): PagingSource<Int, FavoriteVideoGameDTO>

    @Query("SELECT * FROM favorite_video_game WHERE name LIKE '%' || :search || '%' ORDER BY date DESC")
    fun getItems(search: String = ""):Flow<List<FavoriteVideoGameDTO>>

    @Query("SELECT * FROM favorite_video_game WHERE id=:id")
    suspend fun getItem(id:Int):FavoriteVideoGameDTO?

    @Query("SELECT COUNT(id) FROM favorite_video_game")
    fun getCount():Flow<Int>

    @Query("DELETE FROM favorite_video_game WHERE id=:id")
    suspend fun deleteItem(id:Int)

    @Query("DELETE FROM favorite_video_game")
    suspend fun clear()
}