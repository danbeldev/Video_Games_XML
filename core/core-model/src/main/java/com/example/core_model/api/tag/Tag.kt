package com.example.core_model.api.tag

data class Tag(
    val count:Int,
    val next:String,
    val results:List<TagItem>
)

data class TagItem(
    val id:Int,
    val name:String,
    val slug:String,
    val games_count:Int,
    val image_background:String,
    val language:String,
    val games:List<TagVideoGame>
)

data class TagVideoGame(
    val id:Int,
    val name:String,
    val slug:String,
    val added:Int?
)