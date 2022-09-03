package com.example.core_database_data.di.room.dao

import com.example.core_common.di.AppScope
import com.example.core_database_data.database.room.userDatabase.dao.FavoriteVideoGameDAO
import com.example.core_database_data.database.room.userDatabase.UserDatabase
import dagger.Module
import dagger.Provides

@Module
class FavoriteVideoGameDaoModule {

    @[AppScope Provides]
    fun providerFavoriteVideoGameDao(
        userDatabase: UserDatabase
    ): FavoriteVideoGameDAO = userDatabase.favoriteVideoGameDAO()
}