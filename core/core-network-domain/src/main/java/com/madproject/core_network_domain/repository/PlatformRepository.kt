package com.madproject.core_network_domain.repository

import com.madproject.core_model.api.platform.Platform
import com.madproject.core_model.api.platform.PlatformInfo
import retrofit2.Response

interface PlatformRepository {

    suspend fun getPlatform(
        page:Int,
        pageSize:Int
    ):Response<Platform>

    suspend fun getPlatformById(
        id:Int
    ):Response<PlatformInfo>
}