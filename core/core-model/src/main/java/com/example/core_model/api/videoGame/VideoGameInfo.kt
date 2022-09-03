package com.example.core_model.api.videoGame

data class VideoGameInfo(
    val id:Int = 0,
    val slug:String = "",
    val name:String = "",
    val name_original:String = "",
    val description:String = "",
    val metacritic:String = "",
    val metacritic_platforms:List<MetacriticPlatform> = emptyList(),
    val released:String = "",
    val tba:Boolean = false,
    val updated:String = "",
    val background_image:String = "",
    val background_image_additional:String = "",
    val website:String = "",
    val rating:Float = 0.0f,
    val rating_top:Int = 0,
    val ratings:List<VideoGameRating> = emptyList(),
//    val reactions:List<Int>,
    val added:Int = 0,
    val added_by_status: VideoGameAddedStatus = VideoGameAddedStatus(),
    val playtime:Int = 0,
    val screenshots_count:Int = 0,
    val movies_count:Int = 0,
    val creators_count:Int = 0,
    val achievements_count:Int = 0,
    val parent_achievements_count:String = "",
    val reddit_url:String = "",
    val reddit_name:String = "",
    val reddit_description:String = "",
    val reddit_logo:String = "",
    val reddit_count:Int = 0,
    val twitch_count:String = "",
    val youtube_count:String = "",
    val reviews_text_count:String = "",
    val ratings_count:Int = 0,
    val suggestions_count:Int = 0,
    val alternative_names:List<String> = emptyList(),
    val metacritic_url:String = "",
    val parents_count:Int = 0,
    val additions_count:Int = 0,
    val game_series_count:Int = 0,
    val esrb_rating: VideGameEsrbRating = VideGameEsrbRating(),
    val platforms:List<VideoGamePlatform> = emptyList()
)

data class MetacriticPlatform(
    val metascore:Int,
    val url:String
)

data class VideoGameRating(
    val id:Int,
    val title:String,
    val count:Int,
    val percent:Double
)

data class VideoGameAddedStatus(
    val yet:Int = 0,
    val owned:Int = 0,
    val beaten:Int = 0,
    val toplay:Int = 0,
    val dropped:Int = 0,
    val playing:Int = 0
)

data class VideGameEsrbRating(
    val id:Int = 0,
    val slug:String = "",
    val name:String = ""
)

data class VideoGamePlatform(
    val released_at:String,
    val platform: VideoGamePlatformItem,
    val requirements: VideGamePlatformRequirement
)

data class VideoGamePlatformItem(
    val id:Int,
    val slug:String,
    val name:String
)

data class VideGamePlatformRequirement(
    val minimum:String,
    val recommended:String
)