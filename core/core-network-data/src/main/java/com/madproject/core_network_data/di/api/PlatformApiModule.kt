package com.madproject.core_network_data.di.api

import com.madproject.core_common.di.AppScope
import com.madproject.core_network_data.api.PlatformApi
import com.madproject.core_network_data.repository.PlatformRepositoryImpl
import com.madproject.core_network_domain.repository.PlatformRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class PlatformApiModule {

    @[Provides AppScope]
    fun providerPlatformRepository(
        platformApi: PlatformApi
    ): PlatformRepository = PlatformRepositoryImpl(
        platformApi = platformApi
    )

    @[Provides AppScope]
    fun providerPlatformApi(
        retrofit: Retrofit
    ): PlatformApi = retrofit.create(PlatformApi::class.java)


}