package com.example.core_model.api

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