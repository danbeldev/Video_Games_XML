package com.example.core_network_domain.useCase.game

import com.example.core_model.api.VideoGameInfo
import com.example.core_network_domain.repository.GamesRepository
import com.example.core_network_domain.response.BaseApiResponse
import com.example.core_network_domain.response.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGameInfoUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
):BaseApiResponse() {

    operator fun invoke(id:Int):Flow<Result<VideoGameInfo>> = flow {
        emit( safeApiCall { gamesRepository.getGameInfo(id) } )
    }

}