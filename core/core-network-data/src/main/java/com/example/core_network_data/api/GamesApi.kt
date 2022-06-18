package com.example.core_network_data.api

import com.example.core_model.api.VideoGame
import com.example.core_network_data.common.ConstantsUrl.GAMES_URL
import com.example.core_network_data.common.ConstantsUrl.RAWQ_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApi {

    @GET(GAMES_URL)
    suspend fun getGames(
        @Query("key") key:String = RAWQ_KEY
    ):Response<VideoGame>
}