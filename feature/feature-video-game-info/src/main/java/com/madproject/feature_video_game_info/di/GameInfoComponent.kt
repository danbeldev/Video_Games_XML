package com.madproject.feature_video_game_info.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.madproject.core_common.di.Feature
import com.madproject.core_database_domain.userCase.favoriteVideoGame.DeleteFavoriteVideoGamesUseCase
import com.madproject.core_database_domain.userCase.favoriteVideoGame.IfVideoGameInDatabaseUseCase
import com.madproject.core_database_domain.userCase.favoriteVideoGame.AddFavoriteVideoGamesUseCase
import com.madproject.core_network_domain.useCase.game.*
import com.madproject.feature_video_game_info.screens.achievementsScreen.AchievementsFragment
import com.madproject.feature_video_game_info.screens.videoGameInfo.VideoGameInfoFragment
import com.madproject.feature_video_game_info.screens.videoPlayer.VideoPlayerFragment
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
    val getCheckVideoGameByIdUseCase: IfVideoGameInDatabaseUseCase
    val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase
    val writeFavoriteVideoGamesUseCase: AddFavoriteVideoGamesUseCase
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