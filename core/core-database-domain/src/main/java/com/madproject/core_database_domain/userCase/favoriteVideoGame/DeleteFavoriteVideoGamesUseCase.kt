package com.madproject.core_database_domain.userCase.favoriteVideoGame

import com.madproject.core_database_domain.repository.FavoriteVideoGameRepository
import javax.inject.Inject

class DeleteFavoriteVideoGamesUseCase @Inject constructor(
    private val repository: FavoriteVideoGameRepository
) {
    suspend operator fun invoke(id:Int) = repository.deleteItem(id)
}