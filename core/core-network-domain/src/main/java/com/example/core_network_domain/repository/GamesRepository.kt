package com.example.core_network_domain.repository

import com.example.core_model.api.Achievement
import com.example.core_model.api.Screenshot
import com.example.core_model.api.VideoGame
import com.example.core_model.api.VideoGameInfo
import retrofit2.Response

interface GamesRepository {

    suspend fun getGames(
        page:Int
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
}