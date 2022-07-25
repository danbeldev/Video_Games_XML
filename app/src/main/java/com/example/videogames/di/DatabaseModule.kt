package com.example.videogames.di

import com.example.core_database_data.database.FavoriteVideoGameDatabase
import com.example.core_database_data.repository.FavoriteVideoGameRepositoryImpl
import com.example.core_database_domain.repository.FavoriteVideoGameRepository
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGame
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration

@Module
class DatabaseModule {

    @[Provides AppScope]
    fun providerRealmConfiguration(): RealmConfiguration = RealmConfiguration.with(
        schema = setOf(FavoriteVideoGame::class)
    )

    @[Provides AppScope]
    fun providerRealm(
        configuration: RealmConfiguration
    ):Realm = Realm.open(configuration = configuration)

    @[Provides AppScope]
    fun providerFavoriteVideoGameDatabase(
        realm:Realm
    ): FavoriteVideoGameDatabase = FavoriteVideoGameDatabase(
        realm = realm
    )

    @[Provides AppScope]
    fun providerFavoriteVideoGameRepository(
        database: FavoriteVideoGameDatabase
    ): FavoriteVideoGameRepository = FavoriteVideoGameRepositoryImpl(
        database = database
    )
}