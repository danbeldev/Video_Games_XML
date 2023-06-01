package com.madproject.videogames.di

import android.content.Context
import com.madproject.core_common.di.AppScope
import com.madproject.core_database_data.di.repository.FavoriteVideoGameRepositoryModule
import com.madproject.core_database_data.di.room.dao.FavoriteVideoGameDaoModule
import com.madproject.core_database_data.di.room.database.UserDatabaseModule
import com.madproject.core_database_domain.userCase.favoriteVideoGame.*
import com.madproject.core_network_data.di.RetrofitModule
import com.madproject.core_network_data.di.api.*
import com.madproject.core_network_domain.useCase.creator.GetCreatorByIdUseCase
import com.madproject.core_network_domain.useCase.creator.GetCreatorsUseCase
import com.madproject.core_network_domain.useCase.game.*
import com.madproject.core_network_domain.useCase.platform.GetPlatformByIdUseCase
import com.madproject.core_network_domain.useCase.platform.GetPlatformUseCase
import com.madproject.core_network_domain.useCase.store.GetStoreByIdUseCase
import com.madproject.core_network_domain.useCase.store.GetStoresUseCase
import com.madproject.core_network_domain.useCase.tag.GetTagsUseCase
import com.madproject.feature_creator_info.di.CreatorDeps
import com.madproject.feature_main.di.MainDeps
import com.madproject.feature_platform_info.di.PlatformDeps
import com.madproject.feature_store_info.di.StoreDeps
import com.madproject.feature_video_game_info.di.GameInfoDeps
import dagger.BindsInstance
import dagger.Component

@[AppScope Component(modules = [
    FavoriteVideoGameDaoModule::class,
    FavoriteVideoGameRepositoryModule::class,
    UserDatabaseModule::class,
    RetrofitModule::class,
    CreatorApiModule::class,
    GamesApiModule::class,
    PlatformApiModule::class,
    StoreApiModule::class,
    TagApiModule::class
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