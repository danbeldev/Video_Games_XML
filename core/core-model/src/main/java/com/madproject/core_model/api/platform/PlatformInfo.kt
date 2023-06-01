package com.madproject.core_model.api.platform

data class PlatformInfo(
    val id:Int,
    val name:String,
    val slug:String,
    val games_count:Int,
    val image_background:String,
    val description:String?,
    val image:String?,
    val year_start:String?,
    val year_end:String?,
)