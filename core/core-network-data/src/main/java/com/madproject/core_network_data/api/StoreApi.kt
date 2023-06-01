package com.madproject.core_network_data.api

import com.madproject.core_model.api.store.Store
import com.madproject.core_model.api.store.StoreInfo
import com.madproject.core_network_data.common.ConstantUrls
import com.madproject.core_network_data.common.ConstantUrls.STORES_URL
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreApi {

    @GET(STORES_URL)
    suspend fun getStores(
        @Query("key") key:String = ConstantUrls.RAWQ_KEY,
        @Query("page") page:Int = 1,
        @Query("page_size") page_size:Int = 20
    ):Response<Store>

    @GET("$STORES_URL/{id}")
    fun getStoreById(
        @Path("id") id:Int,
        @Query("key") key:String = ConstantUrls.RAWQ_KEY
    ):Single<StoreInfo>
}