package com.example.core_database_domain.userCase.favoriteVideoGame

import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGameDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteVideoGamesUseCase @Inject constructor(
    private val repository: FavoriteVideoGameRepository
) {
    operator fun invoke(): Flow<List<FavoriteVideoGameDTO>> = repository.getAll()
}