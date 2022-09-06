package com.example.core_network_data.di.api

import com.example.core_common.di.AppScope
import com.example.core_network_data.api.TagApi
import com.example.core_network_data.repository.TagRepositoryImpl
import com.example.core_network_domain.repository.TagRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class TagApiModule {

    @[Provides AppScope]
    fun providerTagRepository(
        tagApi: TagApi
    ): TagRepository = TagRepositoryImpl(
        tagApi = tagApi
    )

    @[Provides AppScope]
    fun providerTagApi(
        retrofit: Retrofit
    ): TagApi = retrofit.create(TagApi::class.java)

}