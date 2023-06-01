package com.madproject.core_database_data.di.room.dao

import com.madproject.core_common.di.AppScope
import com.madproject.core_database_data.database.room.userDatabase.dao.FavoriteVideoGameDAO
import com.madproject.core_database_data.database.room.userDatabase.UserDatabase
import dagger.Module
import dagger.Provides

@Module
class FavoriteVideoGameDaoModule {

    @[AppScope Provides]
    fun providerFavoriteVideoGameDao(
        userDatabase: UserDatabase
    ): FavoriteVideoGameDAO = userDatabase.favoriteVideoGameDAO()
}