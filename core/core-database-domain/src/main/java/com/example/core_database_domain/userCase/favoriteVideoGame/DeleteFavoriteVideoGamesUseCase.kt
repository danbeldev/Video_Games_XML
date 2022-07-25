package com.example.core_database_domain.userCase.favoriteVideoGame

import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGame
import javax.inject.Inject

class DeleteFavoriteVideoGamesUseCase @Inject constructor(
    private val repository: FavoriteVideoGameRepository
) {
    suspend operator fun invoke(item:FavoriteVideoGame) = repository.delete(item)
}