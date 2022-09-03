package com.example.core_database_data.database.room.userDatabase

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.core_database_data.database.room.typeConverters.TimestampConverter
import com.example.core_database_data.database.room.userDatabase.dao.FavoriteVideoGameDAO
import com.example.core_database_data.database.room.userDatabase.entities.FavoriteVideoGame

@Database(
    entities = [FavoriteVideoGame::class],
    version = 2,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2
        )
    ]
)
@TypeConverters(value = [TimestampConverter::class])
abstract class UserDatabase:RoomDatabase() {

    abstract fun favoriteVideoGameDAO(): FavoriteVideoGameDAO
}