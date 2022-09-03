package com.example.videogames.di

import android.content.Context
import com.example.core_common.di.AppScope
import com.example.core_database_data.di.repository.FavoriteVideoGameRepositoryModule
import com.example.core_database_data.di.room.dao.FavoriteVideoGameDaoModule
import com.example.core_database_data.di.room.database.UserDatabaseModule
import com.example.core_database_domain.userCase.favoriteVideoGame.*
import com.example.core_network_domain.useCase.creator.GetCreatorByIdUseCase
import com.example.core_network_domain.useCase.creator.GetCreatorsUseCase
import com.example.core_network_domain.useCase.game.*
import com.example.core_network_domain.useCase.platform.GetPlatformByIdUseCase
import com.example.core_network_domain.useCase.platform.GetPlatformUseCase
import com.example.core_network_domain.useCase.store.GetStoreByIdUseCase
import com.example.core_network_domain.useCase.store.GetStoresUseCase
import com.example.core_network_domain.useCase.tag.GetTagsUseCase
import com.example.feature_creator_info.di.CreatorDeps
import com.example.feature_main.di.MainDeps
import com.example.feature_platform_info.di.PlatformDeps
import com.example.feature_store_info.di.StoreDeps
import com.example.feature_video_game_info.di.GameInfoDeps
import dagger.BindsInstance
import dagger.Component

@[AppScope Component(modules = [
    ApiModule::class,
    FavoriteVideoGameDaoModule::class,
    FavoriteVideoGameRepositoryModule::class,
    UserDatabaseModule::class
])]
interface AppComponent
    : MainDeps, GameInfoDeps, PlatformDeps, CreatorDeps, StoreDeps
{
    override val getGamesUseCase: GetGamesUseCase

    override val getCreatorsUseCase: GetCreatorsUseCase

    override val getGameInfoUseCase: GetGameInfoUseCase

    override val getAchievementsUseCase: GetAchievementsUseCase

    override val getScreenshotsUseCase: GetScreenshotsUseCase

    override val getDeveloperTeamUseCase: GetDeveloperTeamUseCase

    override val getTrailerUseCase: GetTrailerUseCase

    override val getPlatformUseCase: GetPlatformUseCase

    override val getStoresUseCase: GetStoresUseCase

    override val getTagsUseCase: GetTagsUseCase

    override val getFavoriteVideoGamesUseCase: GetFavoriteVideoGamesUseCase

    override val getPlatformByIdUseCase: GetPlatformByIdUseCase

    override val getSeriesUseCase: GetSeriesUseCase

    override val getAdditionsUseCase: GetAdditionsUseCase

    override val getCreatorByIdUseCase: GetCreatorByIdUseCase

    override val getStoreByIdUseCase: GetStoreByIdUseCase

    override val getVideoGamesUseCase: GetGamesUseCase

    override val getCheckVideoGameByIdUseCase: IfVideoGameInDatabaseUseCase

    override val getFavoriteVideoGamesCountUseCase: GetFavoriteVideoGamesCountUseCase

    override val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase

    override val writeFavoriteVideoGamesUseCase: AddFavoriteVideoGamesUseCase

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context):Builder

        fun build():AppComponent
    }
}