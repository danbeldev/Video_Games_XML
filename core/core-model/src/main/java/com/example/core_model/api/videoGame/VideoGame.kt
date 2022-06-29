package com.example.core_model.api.videoGame

data class VideoGame(
    val count:Int = 0,
    val next:String = "",
    val previous:String = "",
    val results: List<VideoGameItem> = emptyList()
)

data class VideoGameItem(
    val id:Int,
    val slug:String,
    val name:String,
    val released:String,
    val tba:Boolean,
    val background_image:String,
    val rating:Float
)