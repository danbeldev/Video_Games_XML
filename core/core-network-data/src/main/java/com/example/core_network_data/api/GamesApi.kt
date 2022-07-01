package com.example.core_network_data.api

import com.example.core_model.api.creator.Creator
import com.example.core_model.api.videoGame.*
import com.example.core_network_data.common.ConstantsUrl.GAMES_ACHIEVEMENTS_URL
import com.example.core_network_data.common.ConstantsUrl.GAMES_SCREENSHOT_URL
import com.example.core_network_data.common.ConstantsUrl.GAMES_URL
import com.example.core_network_data.common.ConstantsUrl.GAME_ADDITIONS_URL
import com.example.core_network_data.common.ConstantsUrl.GAME_DEVELOPER_TEAM_URL
import com.example.core_network_data.common.ConstantsUrl.GAME_SERIES_URL
import com.example.core_network_data.common.ConstantsUrl.GAME_TRAILER_URL
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
        @Query("page_size") page_size:Int = 20,
        @Query("search") search:String?,
        @Query("platforms") platforms:String?,
        @Query("creators") creators:String?
    ):Response<VideoGame>

    @GET("$GAMES_URL/{id}")
    suspend fun getGameInfo(
        @Path("id") id:Int,
        @Query("key") key:String = RAWQ_KEY
    ):Response<VideoGameInfo>

    @GET(GAMES_ACHIEVEMENTS_URL)
    suspend fun getAchievements(
        @Path("id") id: Int,
        @Query("key") key:String = RAWQ_KEY
    ):Response<Achievement>

    @GET(GAMES_SCREENSHOT_URL)
    suspend fun getScreenshots(
        @Path("game_pk") gamePk:String,
        @Query("page") page:Int,
        @Query("page_size") pageSize:Int = 20,
        @Query("key") key:String = RAWQ_KEY
    ):Response<Screenshot>

    @GET(GAME_DEVELOPER_TEAM_URL)
    suspend fun getDeveloperTeam(
        @Path("game_pk") gamePk: String,
        @Query("page") page:Int,
        @Query("page_size") pageSize: Int = 20,
        @Query("key") key: String = RAWQ_KEY
    ):Response<Creator>

    @GET(GAME_TRAILER_URL)
    suspend fun getTrailer(
        @Path("id") id:Int,
        @Query("key") key: String = RAWQ_KEY
    ):Response<Trailer>

    @GET(GAME_SERIES_URL)
    suspend fun getSeries(
        @Path("game_pk") gamePk: String,
        @Query("page") page:Int,
        @Query("page_size") pageSize: Int = 20,
        @Query("key") key: String = RAWQ_KEY
    ):Response<VideoGame>

    @GET(GAME_ADDITIONS_URL)
    suspend fun getAdditions(
        @Path("game_pk") gamePk: String,
        @Query("page") page:Int,
        @Query("page_size") pageSize: Int = 20,
        @Query("key") key: String = RAWQ_KEY
    ):Response<VideoGame>
}