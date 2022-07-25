package com.example.core_database_domain.userCase.favoriteVideoGame

import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import io.realm.query.RealmScalarQuery
import javax.inject.Inject

class GetFavoriteVideoGamesCountUseCase @Inject constructor(
    private val repository: FavoriteVideoGameRepository
) {
    suspend operator fun invoke(): RealmScalarQuery<Long> = repository.getCount()
}