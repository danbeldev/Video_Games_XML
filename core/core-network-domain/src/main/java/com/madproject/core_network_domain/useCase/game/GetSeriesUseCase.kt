package com.madproject.core_network_domain.useCase.game

import com.madproject.core_model.api.videoGame.VideoGame
import com.madproject.core_network_domain.repository.GamesRepository
import com.madproject.core_network_domain.response.BaseApiResponse
import com.madproject.core_network_domain.response.Result
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
):BaseApiResponse() {

    suspend operator fun invoke(
        gamePk:String,
        page:Int
    ):Result<VideoGame> = safeApiCall { gamesRepository.getSeries(gamePk, page) }
}