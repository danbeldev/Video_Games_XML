package com.example.core_network_data.api

import com.example.core_model.api.VideoGame
import com.example.core_model.api.VideoGameInfo
import com.example.core_network_data.common.ConstantsUrl.GAMES_URL
import com.example.core_network_data.common.ConstantsUrl.RAWQ_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GamesApi {

    @GET(GAMES_URL)
    suspend fun getGames(
        @Query("key") key:String = RAWQ_KEY,
        @Query("page") page:Int,
        @Query("page_size") page_size:Int = 20
    ):Response<VideoGame>

    @GET("$GAMES_URL/{id}")
    suspend fun getGameInfo(
        @Query("key") key:String = RAWQ_KEY,
        @Path("id") id:Int
    ):Response<VideoGameInfo>
}