package com.example.feature_creator_info.di

import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModel
import com.example.core_common.di.Feature
import com.example.core_network_domain.useCase.creator.GetCreatorByIdUseCase
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import com.example.feature_creator_info.screens.creatorInfo.CreatorInfoFragment
import dagger.Component
import kotlin.properties.Delegates

@[Feature Component(dependencies = [CreatorDeps::class])]
interface CreatorComponent {

    fun injectCreatorInfo(fragment:CreatorInfoFragment)

    @Component.Builder
    interface Builder {

        fun deps(deps: CreatorDeps):Builder

        fun build():CreatorComponent
    }
}

interface CreatorDeps {
    val getCreatorByIdUseCase:GetCreatorByIdUseCase
    val getGamesUseCase: GetGamesUseCase
}

interface CreatorDepsProvider {

    @get:RestrictTo(RestrictTo.Scope.LIBRARY)
    val deps:CreatorDeps

    companion object : CreatorDepsProvider by CreatorDepsStore
}

object CreatorDepsStore : CreatorDepsProvider {
    override var deps: CreatorDeps by Delegates.notNull()
}

internal class CreatorComponentViewModel : ViewModel() {

    val creatorDetailComponent = DaggerCreatorComponent
        .builder()
        .deps(CreatorDepsProvider.deps)
        .build()
}