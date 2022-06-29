package com.example.feature_main.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.core_common.di.Feature
import com.example.core_network_domain.useCase.creator.GetCreatorsUseCase
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import com.example.core_network_domain.useCase.platform.GetPlatformUseCase
import com.example.feature_main.screens.mainScreen.MainFragment
import com.example.feature_main.screens.videoGamesScreen.VideoGamesFragment
import dagger.Component
import kotlin.properties.Delegates

@[Feature Component(dependencies = [MainDeps::class])]
interface MainComponent{

    fun injectMainFragment(fragment: MainFragment)

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
