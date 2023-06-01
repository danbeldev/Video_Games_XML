package com.madproject.core_database_domain.userCase.favoriteVideoGame

import androidx.paging.PagingSource
import com.madproject.core_database_domain.repository.FavoriteVideoGameRepository
import com.madproject.core_model.database.room.favoriteVideoGame.FavoriteVideoGameDTO
import javax.inject.Inject

class GetFavoriteVideoGamesUseCase @Inject constructor(
    private val repository: FavoriteVideoGameRepository
) {
    operator fun invoke(search:String =""): PagingSource<Int, FavoriteVideoGameDTO> = repository.getItems(search)
}