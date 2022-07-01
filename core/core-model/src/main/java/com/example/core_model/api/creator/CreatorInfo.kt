package com.example.core_model.api.creator

data class CreatorInfo(
    val id:Int,
    val name:String,
    val slug:String,
    val image:String,
    val image_background:String,
    val description:String,
    val games_count:Int,
    val reviews_count:Int,
    val rating:String,
    val rating_top:Int,
    val updated:String,
    val positions:List<CreatorPositionItem>,
    val platforms:CreatorPlatform,
    val ratings:List<CreatorRating>,
    val timeline:List<CreatorTimeline>
)

data class CreatorPositionItem(
    val id:Int,
    val name:String,
    val slug:String
)

data class CreatorPlatform(
    val total:Int,
    val count:Int,
    val results:List<CreatorPlatformItem>
)

data class CreatorPlatformItem(
    val count:Int,
    val percent:Float,
    val platform: Platform
)

data class Platform(
    val id:Int,
    val name:String,
    val slug:String
)

data class CreatorRating(
    val id:Int,
    val title:String,
    val count:Int,
    val percent:Float
)

data class CreatorTimeline(
    val year:Int,
    val count:Int
)