package com.madproject.core_model.api.creator

data class Creator(
    val count:Int,
    val next:String,
    val previous:String,
    val results:List<CreatorItem>
)

data class CreatorItem(
    val id:Int,
    val name:String,
    val slug:String,
    val image:String,
    val image_background:String,
    val games_count:Int,
    val positions:List<CreatorItemPosition>,
    val games:List<CreatorGameItem>
)

data class CreatorItemPosition(
    val id: Int,
    val name:String,
    val slug: String
)

data class CreatorGameItem(
    val id:Int,
    val slug:String,
    val name:String,
    val added:Int?
)