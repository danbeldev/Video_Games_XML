package com.example.core_network_data.repository

import com.example.core_model.api.Achievement
import com.example.core_model.api.Screenshot
import com.example.core_model.api.VideoGame
import com.example.core_model.api.VideoGameInfo
import com.example.core_network_data.api.GamesApi
import com.example.core_network_domain.repository.GamesRepository
import retrofit2.Response
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val gamesApi:GamesApi
): GamesRepository {

    override suspend fun getGames(
        page:Int
    ): Response<VideoGame> {
        return gamesApi.getGames(
            page = page
        )
    }

    override suspend fun getGameInfo(id: Int): Response<VideoGameInfo> {
        return gamesApi.getGameInfo(id = id)
    }

    override suspend fun getAchievements(id: Int): Response<Achievement> {
        return gamesApi.getAchievements(id = id)
    }

    override suspend fun getScreenshots(gamePk: String, page:Int): Response<Screenshot> {
        return gamesApi.getScreenshots(gamePk = gamePk, page = page)
    }
}