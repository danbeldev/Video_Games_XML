package com.madproject.core_database_domain.userCase.favoriteVideoGame

import com.madproject.core_database_domain.repository.FavoriteVideoGameRepository
import com.madproject.core_model.database.room.favoriteVideoGame.FavoriteVideoGameDTO
import javax.inject.Inject

class AddFavoriteVideoGamesUseCase @Inject constructor(
    private val repository: FavoriteVideoGameRepository
) {
    suspend operator fun invoke(item: FavoriteVideoGameDTO) = repository.addItem(item)
}