package com.madproject.core_model.api.videoGame

data class Achievement(
    val count:Int,
    val next:String,
    val results:List<AchievementItem>
)

data class AchievementItem(
    val id:Int,
    val name:String,
    val description:String,
    val image:String,
    val percent:String
)