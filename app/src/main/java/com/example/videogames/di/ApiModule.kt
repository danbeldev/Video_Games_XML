package com.example.videogames.di

import com.example.core_network_data.api.GamesApi
import com.example.core_network_data.repository.GamesRepositoryImpl
import com.example.core_network_domain.repository.GamesRepository
import com.example.videogames.common.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

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
        .build()
}