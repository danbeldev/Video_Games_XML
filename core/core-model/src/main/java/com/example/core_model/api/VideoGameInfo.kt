package com.example.core_model.api

data class VideoGameInfo(
    val id:Int,
    val slug:String,
    val name:String,
    val name_original:String,
    val description:String,
    val metacritic:String,
    val metacritic_platforms:List<MetacriticPlatform>,
    val released:String,
    val tba:Boolean,
    val updated:String,
    val background_image:String,
    val background_image_additional:String,
    val website:String,
    val rating:Int,
    val rating_top:Int,
    val ratings:List<VideoGameRating>
)

data class MetacriticPlatform(
    val metascore:Int,
    val url:String
)

data class VideoGameRating(
    val id:Int,
    val title:String,
    val count:Int,
    val percent:Double
)