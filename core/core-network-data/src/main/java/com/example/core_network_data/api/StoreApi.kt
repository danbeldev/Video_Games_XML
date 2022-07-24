package com.example.core_network_data.api

import com.example.core_model.api.store.Store
import com.example.core_model.api.store.StoreInfo
import com.example.core_network_data.common.ConstantsUrl
import com.example.core_network_data.common.ConstantsUrl.STORES_URL
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreApi {

    @GET(STORES_URL)
    suspend fun getStores(
        @Query("key") key:String = ConstantsUrl.RAWQ_KEY,
        @Query("page") page:Int = 1,
        @Query("page_size") page_size:Int = 20
    ):Response<Store>

    @GET("$STORES_URL/{id}")
    fun getStoreById(
        @Path("id") id:Int,
        @Query("key") key:String = ConstantsUrl.RAWQ_KEY
    ):Single<StoreInfo>
}