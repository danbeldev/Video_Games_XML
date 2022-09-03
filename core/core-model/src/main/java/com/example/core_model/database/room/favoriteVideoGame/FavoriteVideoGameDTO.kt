package com.example.core_model.database.room.favoriteVideoGame

import com.example.core_common.date.getUserDateDevice
import java.util.*

data class FavoriteVideoGameDTO(
    val id:Int,
    val name: String,
    val backgroundImage: String,
    val date:Date? = getUserDateDevice()
)