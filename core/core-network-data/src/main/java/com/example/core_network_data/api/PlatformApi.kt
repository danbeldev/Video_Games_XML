package com.example.core_network_data.api

import com.example.core_model.api.platform.Platform
import com.example.core_model.api.platform.PlatformInfo
import com.example.core_network_data.common.ConstantUrls.PLATFORMS_URL
import com.example.core_network_data.common.ConstantUrls.RAWQ_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlatformApi {

    @GET(PLATFORMS_URL)
    suspend fun getPlatforms(
        @Query("key") key:String = RAWQ_KEY,
        @Query("page") page:Int,
        @Query("page_size") page_size:Int
    ):Response<Platform>

    @GET("$PLATFORMS_URL/{id}")
    suspend fun getPlatformById(
        @Path("id") id:Int,
        @Query("key") key:String = RAWQ_KEY
    ):Response<PlatformInfo>
}