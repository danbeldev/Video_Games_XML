package com.madproject.feature_main.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.madproject.core_common.di.Feature
import com.madproject.core_database_domain.userCase.favoriteVideoGame.DeleteFavoriteVideoGamesUseCase
import com.madproject.core_database_domain.userCase.favoriteVideoGame.GetFavoriteVideoGamesCountUseCase
import com.madproject.core_database_domain.userCase.favoriteVideoGame.GetFavoriteVideoGamesUseCase
import com.madproject.core_network_domain.useCase.creator.GetCreatorsUseCase
import com.madproject.core_network_domain.useCase.game.GetGamesUseCase
import com.madproject.core_network_domain.useCase.platform.GetPlatformUseCase
import com.madproject.core_network_domain.useCase.store.GetStoresUseCase
import com.madproject.core_network_domain.useCase.tag.GetTagsUseCase
import com.madproject.feature_main.screens.feedScreen.FeedFragment
import com.madproject.feature_main.screens.likesScreen.LikesFragment
import com.madproject.feature_main.screens.mainScreen.MainFragment
import com.madproject.feature_main.screens.videoGamesScreen.VideoGamesFragment
import dagger.Component
import kotlin.properties.Delegates

@[Feature Component(dependencies = [MainDeps::class])]
interface MainComponent{

    fun injectMainFragment(fragment: MainFragment)

    fun injectFeedFragment(fragment: FeedFragment)

    fun injectLikesFragment(fragment: LikesFragment)

    fun injectVideoGames(fragment: VideoGamesFragment)

    @Component.Builder
    interface Builder{

        fun mainDeps(mainDeps: MainDeps):Builder

        fun build():MainComponent
    }

}

interface MainDeps{
    val getGamesUseCase: GetGamesUseCase
    val getCreatorsUseCase:GetCreatorsUseCase
    val getPlatformUseCase: GetPlatformUseCase
    val getStoresUseCase: GetStoresUseCase
    val getTagsUseCase:GetTagsUseCase
    val getFavoriteVideoGamesUseCase: GetFavoriteVideoGamesUseCase
    val deleteFavoriteVideoGamesUseCase: DeleteFavoriteVideoGamesUseCase
    val getFavoriteVideoGamesCountUseCase: GetFavoriteVideoGamesCountUseCase
}

interface MainDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps: MainDeps

    companion object : MainDepsProvider by MainDepsStore
}

object MainDepsStore : MainDepsProvider {

    override var deps: MainDeps by Delegates.notNull()
}

internal class MainComponentViewModel : ViewModel() {

    val mainDetailsComponent =
        DaggerMainComponent.builder().mainDeps(MainDepsProvider.deps).build()
}
