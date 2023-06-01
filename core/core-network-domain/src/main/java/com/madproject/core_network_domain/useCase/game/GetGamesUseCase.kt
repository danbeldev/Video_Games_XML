package com.madproject.core_network_domain.useCase.game

import com.madproject.core_model.api.videoGame.VideoGame
import com.madproject.core_network_domain.repository.GamesRepository
import com.madproject.core_network_domain.response.BaseApiResponse
import com.madproject.core_network_domain.response.Result
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
):BaseApiResponse() {
    suspend operator fun invoke(
        page:Int,
        search:String? = null,
        platforms:String? = null,
        creators:String? = null,
        stores:String? = null
    ): Result<VideoGame> {
        return safeApiCall { gamesRepository.getGames(
            page = page,
            search = search,
            platforms = platforms,
            creators = creators,
            stores = stores
        ) }
    }
}