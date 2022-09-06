package com.example.core_network_data.di.api

import com.example.core_common.di.AppScope
import com.example.core_network_data.api.StoreApi
import com.example.core_network_data.repository.StoreRepositoryImpl
import com.example.core_network_domain.repository.StoreRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class StoreApiModule {

    @[Provides AppScope]
    fun providerStoreRepository(
        storeApi: StoreApi
    ): StoreRepository = StoreRepositoryImpl(
        storeApi = storeApi
    )

    @[Provides AppScope]
    fun providerStoreApi(
        retrofit: Retrofit
    ): StoreApi = retrofit.create(StoreApi::class.java)

}