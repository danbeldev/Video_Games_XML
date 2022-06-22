package com.example.feature_main.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.core_common.di.Feature
import com.example.core_network_domain.useCase.creator.GetCreatorsUseCase
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import com.example.feature_main.MainFragment
import dagger.Component
import kotlin.properties.Delegates

@[Feature Component(dependencies = [MainDeps::class])]
interface MainComponent{

    fun inject(fragment:MainFragment)

    @Component.Builder
    interface Builder{

        fun mainDeps(mainDeps: MainDeps):Builder

        fun build():MainComponent
    }

}

interface MainDeps{
    val getGamesUseCase: GetGamesUseCase
    val getCreatorsUseCase:GetCreatorsUseCase
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
