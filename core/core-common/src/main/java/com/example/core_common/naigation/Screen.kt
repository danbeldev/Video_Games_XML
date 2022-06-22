package com.example.core_common.naigation

private const val BASE_URL = "jetnavapp://"

sealed class Screen(val route:String){
    object Main:Screen("${BASE_URL}main")
    object VideoGameInfo:Screen("${BASE_URL}video_game_info{video_game_id}"){
        fun arguments(
            videoGameId:Int
        ):String = "${BASE_URL}video_game_info/$videoGameId"
    }
}