package com.example.videogames.di

import com.example.core_network_domain.useCase.creator.GetCreatorsUseCase
import com.example.core_network_domain.useCase.game.GetGamesUseCase
import com.example.feature_main.di.MainDeps
import dagger.Component
import javax.inject.Scope

@[AppScope Component(modules = [ApiModule::class])]
interface AppComponent: MainDeps {

    override val getGamesUseCase: GetGamesUseCase

    override val getCreatorsUseCase: GetCreatorsUseCase
}

@Scope
annotation class AppScope