package com.madproject.core_database_data.database.room.userDatabase.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.madproject.core_common.date.getUserDateDevice
import com.madproject.core_model.database.room.favoriteVideoGame.FavoriteVideoGameDTO
import java.util.*

@Entity(
    tableName = "favorite_video_game"
)
data class FavoriteVideoGame(
    @PrimaryKey(autoGenerate = false) val id:Int,
    val name:String,
    val backgroundImage:String,
    val date: Date? = getUserDateDevice()
)

fun FavoriteVideoGame.mapDTO(): FavoriteVideoGameDTO = FavoriteVideoGameDTO(
    id = id,
    name = name,
    backgroundImage = backgroundImage,
    date = date
)