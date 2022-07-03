package com.example.core_model.api.store

data class Store(
    val count:Int,
    val results:List<StoreItem>
)

data class StoreItem(
    val id:Int,
    val name:String,
    val domain:String,
    val slug:String,
    val games_count:Int,
    val image_background:String,
    val games:List<StoreGame>
)

data class StoreGame(
    val id:Int,
    val slug:String,
    val name:String,
    val added:Int?
)