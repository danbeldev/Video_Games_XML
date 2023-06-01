package com.madproject.core_network_domain.repository

import com.madproject.core_model.api.creator.Creator
import com.madproject.core_model.api.videoGame.*
import retrofit2.Response

interface GamesRepository {

    suspend fun getGames(
        page:Int,
        search:String?,
        platforms:String?,
        creators:String?,
        stores:String?
    ):Response<VideoGame>

    suspend fun getGameInfo(
        id:Int
    ):Response<VideoGameInfo>

    suspend fun getAchievements(
        id: Int
    ):Response<Achievement>

    suspend fun getScreenshots(
        gamePk:String,
        page:Int
    ):Response<Screenshot>

    suspend fun getDeveloperTeam(
        gamePk:String,
        page:Int
    ):Response<Creator>

    suspend fun getTrailer(
        id: Int
    ):Response<Trailer>

    suspend fun getSeries(
        gamePk:String,
        page:Int
    ):Response<VideoGame>

    suspend fun getAdditions(
        gamePk: String,
        page:Int
    ):Response<VideoGame>
}