package com.example.core_network_domain.useCase.creator

import com.example.core_model.api.Creator
import com.example.core_network_domain.repository.CreatorRepository
import javax.inject.Inject

class GetCreatorsUseCase @Inject constructor(
    private val creatorRepository: CreatorRepository
) {
    suspend operator fun invoke(page:Int):Creator = creatorRepository.getCreators(page)
}