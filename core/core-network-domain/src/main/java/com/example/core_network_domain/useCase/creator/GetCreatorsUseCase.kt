package com.example.core_network_domain.useCase.creator

import com.example.core_model.api.Creator
import com.example.core_network_domain.repository.CreatorRepository
import com.example.core_network_domain.response.BaseApiResponse
import com.example.core_network_domain.response.Result
import javax.inject.Inject

class GetCreatorsUseCase @Inject constructor(
    private val creatorRepository: CreatorRepository
):BaseApiResponse() {
    suspend operator fun invoke(page:Int):Result<Creator> = safeApiCall { creatorRepository.getCreators(page) }
}