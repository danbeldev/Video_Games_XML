package com.madproject.core_database_data.di.room.database

import android.content.Context
import androidx.room.Room
import com.madproject.core_common.di.AppScope
import com.madproject.core_database_data.database.room.userDatabase.UserDatabase
import dagger.Module
import dagger.Provides

const val USER_DATABASE_NAME = "user_database"

@Module
class UserDatabaseModule {

    @[AppScope Provides]
    fun providerUserDatabase(
        context:Context
    ): UserDatabase = Room.databaseBuilder(
        context.applicationContext,
        UserDatabase::class.java,
        USER_DATABASE_NAME
    ).build()
}