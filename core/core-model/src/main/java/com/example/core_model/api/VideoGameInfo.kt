package com.example.core_model.api

data class VideoGameInfo(
    val id:Int,
    val slug:String,
    val name:String,
    val name_original:String,
    val description:String,
    val metacritic:String,
    val metacritic_platforms:List<MetacriticPlatform>,
    val released:String,
    val tba:Boolean,
    val updated:String,
    val background_image:String,
    val background_image_additional:String,
    val website:String,
    val rating:Int,
    val rating_top:Int,
    val ratings:List<VideoGameRating>,
//    val reactions:List<Int>,
    val added:Int,
    val added_by_status:VideoGameAddedStatus,
    val playtime:Int,
    val screenshots_count:Int,
    val movies_count:Int,
    val creators_count:Int,
    val achievements_count:Int,
    val parent_achievements_count:String,
    val reddit_url:String,
    val reddit_name:String,
    val reddit_description:String,
    val reddit_logo:String,
    val reddit_count:Int,
    val twitch_count:String,
    val youtube_count:String,
    val reviews_text_count:String,
    val ratings_count:Int,
    val suggestions_count:Int,
    val alternative_names:List<String>,
    val metacritic_url:String,
    val parents_count:Int,
    val additions_count:Int,
    val game_series_count:Int,
    val esrb_rating:VideGameEsrbRating,
    val platforms:VideoGamePlatform
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
    val yet:Int,
    val owned:Int,
    val beaten:Int,
    val toplay:Int,
    val dropped:Int,
    val playing:Int
)

data class VideGameEsrbRating(
    val id:Int,
    val slug:String,
    val name:String
)

data class VideoGamePlatform(
    val released_at:String,
    val platform:VideoGamePlatformItem,
    val requirements:VideGamePlatformRequirement
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