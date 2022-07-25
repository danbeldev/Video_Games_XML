package com.example.core_database_data.database

import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGame
import com.example.core_model.database.favoriteVideoGame.FavoriteVideoGameDTO
import com.example.core_model.database.favoriteVideoGame.mapToDTO
import io.realm.Realm
import io.realm.query
import io.realm.query.RealmScalarQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class FavoriteVideoGameDatabase @Inject constructor(
    private val realm: Realm
) {
    suspend fun write(item:FavoriteVideoGame){ realm.write { copyToRealm(item) } }

    fun getAll(): Flow<List<FavoriteVideoGameDTO>> {
        return realm.query<FavoriteVideoGame>()
            .asFlow()
            .mapNotNull { it.list.map { item ->  item.mapToDTO() } }
    }

    fun getCount(): RealmScalarQuery<Long> = realm.query<FavoriteVideoGame>().count()

    suspend fun delete(item: FavoriteVideoGame) = realm.write { delete(item) }
}