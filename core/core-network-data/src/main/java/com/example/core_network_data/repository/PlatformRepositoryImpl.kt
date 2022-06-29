package com.example.core_network_data.repository

import com.example.core_model.api.platform.Platform
import com.example.core_model.api.platform.PlatformInfo
import com.example.core_network_data.api.PlatformApi
import com.example.core_network_domain.repository.PlatformRepository
import retrofit2.Response
import javax.inject.Inject

class PlatformRepositoryImpl @Inject constructor(
    private val platformApi: PlatformApi
): PlatformRepository {

    override suspend fun getPlatform(page: Int, pageSize: Int): Response<Platform> {
        return platformApi.getPlatforms(
            page = page,
            page_size = pageSize
        )
    }

    override suspend fun getPlatformById(id: Int): Response<PlatformInfo> {
        return platformApi.getPlatformById(id = id)
    }
}