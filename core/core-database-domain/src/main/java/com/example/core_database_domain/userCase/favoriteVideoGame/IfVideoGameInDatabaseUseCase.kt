package com.example.core_database_domain.userCase.favoriteVideoGame

import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IfVideoGameInDatabaseUseCase @Inject constructor(
    private val repository: FavoriteVideoGameRepository
) {
    suspend operator fun invoke(id:Int):Boolean = repository.ifVideoGameInDatabase(id)
}