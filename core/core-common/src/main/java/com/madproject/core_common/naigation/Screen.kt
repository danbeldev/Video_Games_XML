package com.madproject.core_common.naigation

private const val BASE_URL = "jetnavapp://"

sealed class Screen(val route:String){
    object Main:Screen("${BASE_URL}main")
    object Compose:Screen("${BASE_URL}compose")
    object VideoGameInfo:Screen("${BASE_URL}video_game_info{video_game_id}"){
        fun arguments(
            videoGameId:Int
        ):String = "${BASE_URL}video_game_info/$videoGameId"
    }
    object PlatformInfo:Screen("${BASE_URL}platform/{platform_id}"){
        fun arguments(
            platformId:Int
        ):String = "${BASE_URL}platform/$platformId"
    }
    object CreatorInfo:Screen("${BASE_URL}creator_info/{creator_id}"){
        fun arguments(
            creatorId:Int
        ):String = "${BASE_URL}creator_info/$creatorId"
    }
    object StoreInfo:Screen("${BASE_URL}store_info/{store_id}"){
        fun arguments(
            storeId:Int
        ):String = "${BASE_URL}store_info/$storeId"
    }
}