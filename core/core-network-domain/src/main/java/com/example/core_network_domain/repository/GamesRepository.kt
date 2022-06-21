package com.example.core_network_domain.repository

import com.example.core_model.api.VideoGame
import retrofit2.Response

interface GamesRepository {

    suspend fun getGames(
        page:Int
    ):Response<VideoGame>

}