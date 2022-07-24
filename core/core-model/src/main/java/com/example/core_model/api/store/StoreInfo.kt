package com.example.core_model.api.store

import com.google.gson.annotations.SerializedName

data class StoreInfo(
    val id:Int,
    val name:String,
    val domain:String,
    val slug:String,
    @SerializedName("games_count")
    val gamesCount:Int,
    @SerializedName("image_background")
    val imageBackground:String,
    val description:String
)