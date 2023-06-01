package com.madproject.core_network_domain.useCase.creator

import com.madproject.core_model.api.creator.CreatorInfo
import com.madproject.core_network_domain.repository.CreatorRepository
import com.madproject.core_network_domain.response.BaseApiResponse
import com.madproject.core_network_domain.response.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCreatorByIdUseCase @Inject constructor(
    private val creatorRepository: CreatorRepository
) : BaseApiResponse() {

    operator fun invoke(id:Int):Flow<Result<CreatorInfo>> = flow {
        emit( safeApiCall { creatorRepository.getCreatorById(id) } )
    }
}