package com.madproject.core_network_data.repository

import com.madproject.core_model.api.creator.Creator
import com.madproject.core_model.api.videoGame.*
import com.madproject.core_network_data.api.GamesApi
import com.madproject.core_network_domain.repository.GamesRepository
import retrofit2.Response
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val gamesApi:GamesApi
): GamesRepository {

    override suspend fun getGames(
        page: Int,
        search: String?,
        platforms:String?,
        creators:String?,
        stores:String?
    ): Response<VideoGame> {
        return gamesApi.getGames(
            page = page,
            search = search,
            platforms = platforms,
            creators = creators,
            stores = stores
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

    override suspend fun getDeveloperTeam(gamePk: String, page: Int): Response<Creator> {
        return gamesApi.getDeveloperTeam(gamePk = gamePk, page = page)
    }

    override suspend fun getTrailer(id: Int): Response<Trailer> {
        return gamesApi.getTrailer(id = id)
    }

    override suspend fun getSeries(gamePk: String, page: Int): Response<VideoGame> {
        return gamesApi.getSeries(gamePk = gamePk, page = page)
    }

    override suspend fun getAdditions(gamePk: String, page: Int): Response<VideoGame> {
        return gamesApi.getAdditions(gamePk,page)
    }
}