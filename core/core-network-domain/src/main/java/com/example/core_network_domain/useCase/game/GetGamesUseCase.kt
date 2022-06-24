package com.example.core_network_domain.useCase.game

import com.example.core_model.api.VideoGame
import com.example.core_network_domain.repository.GamesRepository
import com.example.core_network_domain.response.BaseApiResponse
import com.example.core_network_domain.response.Result
import javax.inject.Inject

class GetGamesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
):BaseApiResponse() {
    suspend operator fun invoke(
        page:Int
    ): Result<VideoGame> {
        return safeApiCall { gamesRepository.getGames(page = page) }
    }
}