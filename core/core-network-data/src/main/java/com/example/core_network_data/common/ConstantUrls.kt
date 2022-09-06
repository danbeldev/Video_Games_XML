package com.example.core_network_data.common

internal object ConstantUrls {

    const val BASE_URL = "https://api.rawg.io"

    const val GAMES_URL = "/api/games"
    const val GAMES_ACHIEVEMENTS_URL = "/api/games/{id}/achievements"
    const val GAMES_SCREENSHOT_URL = "/api/games/{game_pk}/screenshots"
    const val GAME_DEVELOPER_TEAM_URL = "/api/games/{game_pk}/development-team"
    const val GAME_TRAILER_URL = "/api/games/{id}/movies"
    const val GAME_SERIES_URL = "/api/games/{game_pk}/game-series"
    const val GAME_ADDITIONS_URL = "/api/games/{game_pk}/additions"

    const val PLATFORMS_URL = "/api/platforms"

    const val CREATORS_URL = "/api/creators"

    const val STORES_URL = "/api/stores"

    const val TAGS_URL = "/api/tags"

    // 1. 1f7ab36e7bd446dc9452af86cd843e5c
    // 2. 1670241aa27c47fbbb7e1d234a4e207a
    const val RAWQ_KEY = "1f7ab36e7bd446dc9452af86cd843e5c"
}