package com.example.core_database_domain.userCase.favoriteVideoGame

import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import javax.inject.Inject

class GetCheckVideoGameByIdUseCase @Inject constructor(
    private val repository: FavoriteVideoGameRepository
) {
    operator fun invoke(id:Int):Boolean = repository.getCheckVideoGameById(id)
}