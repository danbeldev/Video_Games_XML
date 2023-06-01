package com.madproject.core_network_domain.useCase.game

import com.madproject.core_model.api.creator.Creator
import com.madproject.core_network_domain.repository.GamesRepository
import com.madproject.core_network_domain.response.BaseApiResponse
import com.madproject.core_network_domain.response.Result
import javax.inject.Inject

class GetDeveloperTeamUseCase @Inject constructor(
    private val gamesRepository: GamesRepository
):BaseApiResponse() {

    suspend operator fun invoke(gamePk:String, page:Int):Result<Creator> {
        return safeApiCall { gamesRepository.getDeveloperTeam(gamePk = gamePk, page = page) }
    }

}