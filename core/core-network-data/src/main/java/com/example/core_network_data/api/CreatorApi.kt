package com.example.core_network_data.api

import com.example.core_model.api.creator.Creator
import com.example.core_network_data.common.ConstantsUrl.RAWQ_KEY
import com.example.core_network_data.common.ConstantsUrl.CREATORS_URL
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CreatorApi {

    @GET(CREATORS_URL)
    suspend fun getCreators(
        @Query("key") key:String = RAWQ_KEY,
        @Query("page") page:Int = 1,
        @Query("page_size") page_size:Int = 20
    ):Response<Creator>

}