package com.madproject.core_network_data.di.api

import com.madproject.core_common.di.AppScope
import com.madproject.core_network_data.api.CreatorApi
import com.madproject.core_network_data.repository.CreatorRepositoryImpl
import com.madproject.core_network_domain.repository.CreatorRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class CreatorApiModule {

    @[Provides AppScope]
    fun providerCreatorRepository(
        creatorApi: CreatorApi
    ): CreatorRepository = CreatorRepositoryImpl(
        creatorApi = creatorApi
    )

    @[Provides AppScope]
    fun providerCreatorApi(
        retrofit: Retrofit
    ): CreatorApi = retrofit.create(CreatorApi::class.java)

}