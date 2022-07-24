package com.example.videogames.di

import com.example.core_network_data.api.*
import com.example.core_network_data.repository.*
import com.example.core_network_domain.repository.*
import com.example.videogames.common.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    @[Provides AppScope]
    fun providerTagRepository(
        tagApi: TagApi
    ):TagRepository = TagRepositoryImpl(
        tagApi = tagApi
    )

    @[Provides AppScope]
    fun providerTagApi(
        retrofit: Retrofit
    ):TagApi = retrofit.create(TagApi::class.java)

    @[Provides AppScope]
    fun providerStoreRepository(
        storeApi: StoreApi
    ):StoreRepository = StoreRepositoryImpl(
        storeApi = storeApi
    )

    @[Provides AppScope]
    fun providerStoreApi(
        retrofit: Retrofit
    ):StoreApi = retrofit.create(StoreApi::class.java)

    @[Provides AppScope]
    fun providerPlatformRepository(
        platformApi: PlatformApi
    ):PlatformRepository = PlatformRepositoryImpl(
        platformApi = platformApi
    )

    @[Provides AppScope]
    fun providerPlatformApi(
        retrofit: Retrofit
    ):PlatformApi = retrofit.create(PlatformApi::class.java)

    @[Provides AppScope]
    fun providerCreatorRepository(
        creatorApi: CreatorApi
    ):CreatorRepository = CreatorRepositoryImpl(
        creatorApi = creatorApi
    )

    @[Provides AppScope]
    fun providerCreatorApi(
        retrofit: Retrofit
    ):CreatorApi = retrofit.create(CreatorApi::class.java)

    @[Provides AppScope]
    fun providerVideoGamesRepository(
        gamesApi: GamesApi
    ):GamesRepository = GamesRepositoryImpl(
        gamesApi = gamesApi
    )

    @[Provides AppScope]
    fun providerVideoGamesApi(
        retrofit: Retrofit
    ):GamesApi = retrofit.create(GamesApi::class.java)

    @[Provides AppScope]
    fun providerRetrofit():Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
}