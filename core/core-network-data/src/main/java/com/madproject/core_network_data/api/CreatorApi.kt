package com.madproject.core_network_data.api

import com.madproject.core_model.api.creator.Creator
import com.madproject.core_model.api.creator.CreatorInfo
import com.madproject.core_network_data.common.ConstantUrls.RAWQ_KEY
import com.madproject.core_network_data.common.ConstantUrls.CREATORS_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CreatorApi {

    @GET(CREATORS_URL)
    suspend fun getCreators(
        @Query("key") key:String = RAWQ_KEY,
        @Query("page") page:Int = 1,
        @Query("page_size") page_size:Int = 20
    ):Response<Creator>

    @GET("${CREATORS_URL}/{id}")
    suspend fun getCreatorById(
        @Path("id") id:Int,
        @Query("key") key:String = RAWQ_KEY
    ):Response<CreatorInfo>
}