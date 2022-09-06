package com.example.core_model.api.platform

data class Platform(
    val count:Int,
    val next:String,
    val results:List<PlatformItem> = emptyList()
)

data class PlatformItem(
    val id:Int,
    val name:String,
    val slug:String,
    val games_count:String,
    val image_background:String,
    val image:String?,
    val year_start:String?,
    val year_end:String?,
    val games:List<PlatformGame>
)

data class PlatformGame(
    val id:Int,
    val slug:String,
    val name:String,
    val added:Int?
)
