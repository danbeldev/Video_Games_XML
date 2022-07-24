package com.example.core_model.api.tag

data class TagInfo(
    val id:Int,
    val name:String,
    val slug:String,
    val games_count:Int,
    val image_background:String,
    val description:String
)