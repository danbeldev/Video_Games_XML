package com.example.core_database_domain.userCase.favoriteVideoGame

import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteVideoGamesCountUseCase @Inject constructor(
    private val repository: FavoriteVideoGameRepository
) {
    operator fun invoke(): Flow<Int> = repository.getCount()
}