package com.example.core_database_data.repository

import androidx.paging.PagingSource
import com.example.core_database_data.database.room.userDatabase.dao.FavoriteVideoGameDAO
import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import com.example.core_model.database.room.favoriteVideoGame.FavoriteVideoGameDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteVideoGameRepositoryImpl @Inject constructor(
    private val dao: FavoriteVideoGameDAO
): FavoriteVideoGameRepository {

    override suspend fun addItem(item: FavoriteVideoGameDTO) = dao.upsertItem(item)

    override fun getItems(search:String): PagingSource<Int, FavoriteVideoGameDTO> = dao.getItemsPagingSource(search)

    override fun getCount(): Flow<Int> = dao.getCount()

    override suspend fun ifVideoGameInDatabase(id: Int): Boolean {
        val videoGame = dao.getItem(id)
        return videoGame != null
    }

    override suspend fun deleteItem(id: Int) = dao.deleteItem(id)

    override suspend fun clear() = dao.clear()
}