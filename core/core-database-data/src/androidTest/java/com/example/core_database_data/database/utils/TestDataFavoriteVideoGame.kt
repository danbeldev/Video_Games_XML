package com.example.core_database_data.database.utils

import com.example.core_model.database.room.favoriteVideoGame.FavoriteVideoGameDTO

private const val FAVORITE_VIDEO_GAME_ID_1 = 1
private const val FAVORITE_VIDEO_GAME_ID_NAME_1 = "test_video_game_name_1"
private const val FAVORITE_VIDEO_GAME_ID_BACKGROUND_IMAGE_1 = "test_video_game_background_image"

internal fun createFavoriteVideoGameDTO():FavoriteVideoGameDTO {
    return FavoriteVideoGameDTO(
        id = FAVORITE_VIDEO_GAME_ID_1,
        name = FAVORITE_VIDEO_GAME_ID_NAME_1,
        backgroundImage = FAVORITE_VIDEO_GAME_ID_BACKGROUND_IMAGE_1
    )
}