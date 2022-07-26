package com.example.feature_video_game_info.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.core_common.di.Feature
import com.example.core_database_domain.userCase.favoriteVideoGame.DeleteFavoriteVideoGamesUseCase
import com.example.core_database_domain.userCase.favoriteVideoGame.GetCheckVideoGameByIdUseCase
import com.example.core_database_domain.userCase.favoriteVideoGame.WriteFavoriteVideoGamesUseCase
import com.example.core_network_domain.useCase.game.*
import com.example.feature_video_game_info.screens.achievementsScreen.AchievementsFragment
import com.example.feature_video_game_info.screens.videoGameInfo.VideoGameInfoFragment
import com.example.feature_video_game_info.screens.videoPlayer.VideoPlayerFragment
import dagger.Component
import kotlin.properties.Delegates

@[Feature Component(dependencies = [GameInfoDeps::class])]
interface GameInfoCompany {

    fun injectVideoGameInfo(fragment: VideoGameInfoFragment)

    fun injectVideoPlayer(fragment: VideoPlayerFragment)

    fun injectAchievements(fragment: AchievementsFragment)

    @Component.Builder
    interface Builder{

        fun deps(gameInfoDeps: GameInfoDeps):Builder

        fun build():GameInfoCompany
    }
}

interface GameInfoDeps{
    val getGameInfoUseCase: GetGameInfoUseCase
    val getAchievementsUseCase: GetAchievementsUseCase
    val getScreenshotsUseCase: GetScreenshotsUseCase
    val getDeveloperTeamUseCase:GetDeveloperTeamUseCase
    val getTrailerUseCase: GetTrailerUseCase
    val getSeriesUseCase: GetSeriesUseCase
    val getAdditionsUseCase:GetAdditionsUseCase
    val getCheckVideoGameByIdUseCase: GetCheckVideoGameByIdUseCase
    val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase
    val writeFavoriteVideoGamesUseCase: WriteFavoriteVideoGamesUseCase
}

interface GameInfoDepsProvider{

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps:GameInfoDeps

    companion object : GameInfoDepsProvider by GameInfoDepsStore
}

object GameInfoDepsStore:GameInfoDepsProvider{
    override var deps: GameInfoDeps by Delegates.notNull()
}

internal class GameInfoComponentViewModel:ViewModel(){

    val gameInfoDetailsComponent = DaggerGameInfoCompany.builder()
        .deps(GameInfoDepsProvider.deps)
        .build()

}