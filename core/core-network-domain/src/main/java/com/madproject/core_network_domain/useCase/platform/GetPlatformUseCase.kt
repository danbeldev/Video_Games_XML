package com.madproject.core_network_domain.useCase.platform

import com.madproject.core_model.api.platform.Platform
import com.madproject.core_network_domain.repository.PlatformRepository
import com.madproject.core_network_domain.response.BaseApiResponse
import com.madproject.core_network_domain.response.Result
import javax.inject.Inject

class GetPlatformUseCase @Inject constructor(
    private val platformRepository: PlatformRepository
):BaseApiResponse() {

    suspend operator fun invoke(
        pageSize:Int = 20,
        page:Int
    ):Result<Platform>{
        return safeApiCall { platformRepository.getPlatform(page = page, pageSize = pageSize) }
    }

}