package com.example.core_database_data.di.repository

import com.example.core_common.di.AppScope
import com.example.core_database_data.database.room.userDatabase.dao.FavoriteVideoGameDAO
import com.example.core_database_data.repository.FavoriteVideoGameRepositoryImpl
import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import dagger.Module
import dagger.Provides

@Module
class FavoriteVideoGameRepositoryModule {

    @[Provides AppScope]
    fun providerFavoriteVideoGameRepository(
        dao:FavoriteVideoGameDAO
    ):FavoriteVideoGameRepository = FavoriteVideoGameRepositoryImpl(
        dao = dao
    )
}