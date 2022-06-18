package com.example.core_network_data.repository

import com.example.core_model.api.VideoGame
import com.example.core_network_data.api.GamesApi
import com.example.core_network_domain.repository.GamesRepository
import retrofit2.Response
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val gamesApi:GamesApi
): GamesRepository {

    override suspend fun getGames(): Response<VideoGame> {
        return gamesApi.getGames()
    }

}