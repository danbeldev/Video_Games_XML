package com.madproject.core_model.api.videoGame

import com.madproject.core_model.api.platform.PlatformName

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
    val rating:Float,
    val metacritic:Int,
    val parent_platforms:List<ParentPlatform?>,
    val genres:List<Genre>,
    val stores:List<Store>
)

data class ParentPlatform(
    val platform:ParentPlatformInfo?
)

data class ParentPlatformInfo(
    val id:Int,
    val name: PlatformName?,
    val slug: String
)

data class Store(
    val store:StoreInfo
)

data class StoreInfo(
    val id:Int,
    val name:String,
    val slug:String
)

data class Genre(
    val id:Int,
    val name:String,
    val slug:String
)