package com.madproject.core_network_domain.useCase.game

import com.madproject.core_model.api.videoGame.Achievement
import com.madproject.core_network_domain.repository.GamesRepository
import com.madproject.core_network_domain.response.BaseApiResponse
import com.madproject.core_network_domain.response.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAchievementsUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
):BaseApiResponse(){

    operator fun invoke(id:Int):Flow<Result<Achievement>> = flow {
        emit( safeApiCall { gamesRepository.getAchievements(id) } )
    }
}